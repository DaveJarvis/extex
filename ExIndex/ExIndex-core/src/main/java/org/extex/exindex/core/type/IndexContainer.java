/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.exindex.core.type;

import org.extex.exindex.core.exception.InconsistentLetterGroupException;
import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.exception.LetterGroupsClosedException;
import org.extex.exindex.core.type.alphabet.Alphabet;
import org.extex.exindex.core.type.alphabet.AlphabetContainer;
import org.extex.exindex.core.type.alphabet.LocationClass;
import org.extex.exindex.core.type.attribute.Attribute;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.core.type.rules.RuleSetContainer;
import org.extex.exindex.core.type.rules.SortRuleContainer;
import org.extex.exindex.core.type.rules.SortRules;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.type.value.LValue;

import java.io.PrintStream;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class represents a container for indices and the parameters needed to
 * process them.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IndexContainer
    implements
    LValue,
    MarkupContainer,
    RuleSetContainer,
    SortRuleContainer,
    FallbackContainer,
    AlphabetContainer,
    AttributesContainer,
    LocationClassContainer,
    Iterable<String> {

  /**
   * The constant {@code DEFAULT_INDEX} contains the name of the default
   * index.
   */
  public static final String DEFAULT_INDEX = "";

  /**
   * The field {@code map} contains the mapping from name to index.
   */
  private final Map<String, StructuredIndex> indexMap =
      new HashMap<String, StructuredIndex>();

  /**
   * The field {@code currentIndex} contains the current index.
   */
  private StructuredIndex currentIndex;

  /**
   * The field {@code rules} contains the mapping from names to rule sets.
   */
  private final Map<String, List<Rule>> rules = new HashMap<String,
      List<Rule>>();

  /**
   * The field {@code alphabetMap} contains the alphabets.
   */
  private final Map<String, Alphabet> alphabetMap = new HashMap<String,
      Alphabet>();

  /**
   * Creates a new object.
   *
   * @throws NoSuchMethodException in case that no method corresponding to the
   *                               argument specification could be found
   * @throws SecurityException     in case a security problem occurred
   */
  public IndexContainer() throws SecurityException, NoSuchMethodException {

    // the default index has no fallback
    currentIndex = new StructuredIndex( DEFAULT_INDEX, null );
    indexMap.put( null, currentIndex );
    indexMap.put( DEFAULT_INDEX, currentIndex );
  }

  /**
   * java.lang.String, org.extex.exindex.core.type.alphabet.Alphabet)
   */
  public void addAlphabet( String name, Alphabet alphabet ) {

    alphabetMap.put( name, alphabet );
  }

  /**
   * java.lang.String,
   * org.extex.exindex.core.type.alphabet.LocationClass)
   */
  public boolean addLocationClass( String name, LocationClass locationClass ) {

    return currentIndex.addLocationClass( name, locationClass );
  }

  /**
   * Add a merge rule.
   *
   * @param rule the rule
   */
  public void addMergeRule( Rule rule ) {

    currentIndex.addMergeRule( rule );
  }

  /**
   * java.lang.String, java.util.List)
   */
  public void addRule( String name, List<Rule> rule ) {

    rules.put( name, rule );
  }

  /**
   * Integer, org.extex.exindex.core.type.rules.Rule)
   */
  public void addSortRule( Integer level, Rule rule ) {

    currentIndex.addSortRule( level, rule );
  }

  /**
   * Integer, java.util.List)
   */
  public void addSortRules( Integer level, List<Rule> ruleList ) {

    currentIndex.addSortRules( level, ruleList );
  }

  /**
   * String)
   */
  public String applyMergeRuleFallback( String text ) {

    return get( DEFAULT_INDEX ).applyMergeRule( text );
  }

  /**
   * java.lang.String, int)
   */
  public String applySortRuleFallback( String text, int level ) {

    return get( DEFAULT_INDEX ).applySortRule( text, level );
  }

  /**
   * java.lang.String, org.extex.exindex.core.type.attribute.Attribute)
   */
  public void defineAttribute( String key, Attribute attribute ) {

    currentIndex.defineAttribute( key, attribute );
  }

  /**
   * Define or redefine an index. If an index with the given name already
   * exists then this index is returned. Otherwise a new one is created and
   * returned.
   *
   * @param name   the name of the index
   * @param suffix the suffix
   * @return the index found or created
   */
  public StructuredIndex defineIndex( String name, String suffix ) {

    StructuredIndex index = indexMap.get( name );
    if( index == null ) {
      index = new StructuredIndex( name, this );
      indexMap.put( name, index );
    }
    index.setSuffix( suffix );
    return index;
  }

  /**
   * Add links to all prefixes and return the equivalence class used in the
   * current index. The equivalence class is determined from the name and the
   * prefixes. If for one a letter group is known then this one is used.
   * Otherwise a new one is created.
   * <p>
   * If a prefix has already assigned to a different letter group then this is
   * reported as error.
   * </p>
   *
   * @param name the name
   * @param sa   the prefixes
   * @return the letter group for the whole set
   * @throws LetterGroupsClosedException      in case the method
   *
   *
   *
   *
   *
   *
   *
   *
   *                                  {@link LetterGroupContainer#sorted()}
   *                                  has been invoked before
   * @throws InconsistentLetterGroupException in case of an inconsistency in
   *                                          the definition of prefixes
   */
  public LetterGroup defineLetterGroup( String name, String... sa )
      throws LetterGroupsClosedException,
      InconsistentLetterGroupException {

    return currentIndex.defineLetterGroup( name, sa );
  }

  /**
   * Set the markup info associated to a certain name to a given object.
   *
   * @param name   the name
   * @param markup the markup
   */
  public void defineMarkup( String name, Markup markup ) {

    currentIndex.setMarkup( name, markup );
  }

  /**
   * Getter for a named index.
   *
   * @param name the name of the index.
   * @return the named index or {@code null}
   */
  public StructuredIndex get( String name ) {

    return indexMap.get( name );
  }

  /**
   * Getter for the current index.
   *
   * @return the current index
   */
  public StructuredIndex getCurrentIndex() {

    return currentIndex;
  }

  public Markup getFallbackMarkup( String name ) {

    return get( DEFAULT_INDEX ).getMarkup( name );
  }

  /**
   * Getter for the markup.
   *
   * @param name the name
   * @return the markup or {@code null} if it is not defined
   */
  public Markup getMarkup( String name ) {

    Markup markup = currentIndex.getMarkup( name );

    if( markup == null ) {
      MarkupContainer fallbackIndex = indexMap.get( DEFAULT_INDEX );
      if( currentIndex != fallbackIndex ) {
        markup = fallbackIndex.getMarkup( name );
      }
    }

    return markup;
  }

  /**
   * Get the number of indices.
   *
   * @return the number of indices present
   */
  public int getSize() {

    return indexMap.size() - 1;
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * The request is delegated to the current index.
   * </p>
   *
   * @see org.extex.exindex.core.type.attribute.AttributesContainer#isAttributeDefined(
   *java.lang.String)
   */
  public boolean isAttributeDefined( String name ) {

    return currentIndex.isAttributeDefined( name );
  }

  public Iterator<String> iterator() {

    Set<String> keySet = indexMap.keySet();
    keySet.remove( null );
    return keySet.iterator();
  }

  /**
   * java.lang.String)
   */
  public Alphabet lookupAlphabet( String name ) {

    return alphabetMap.get( name );

  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * The request is delegated to the current index.
   * </p>
   *
   * @see org.extex.exindex.core.type.attribute.AttributesContainer#lookupAttribute(
   *java.lang.String)
   */
  public Attribute lookupAttribute( String attribute ) {

    return currentIndex.lookupAttribute( attribute );
  }

  /**
   * java.lang.String)
   */
  public LocationClass lookupLocationClass( String name ) {

    return currentIndex.lookupLocationClass( name );
  }

  /**
   * Integer)
   */
  public SortRules lookupOrCreateSortRule( Integer level ) {

    return currentIndex.lookupOrCreateSortRule( level );
  }

  /**
   * java.lang.String)
   */
  public List<Rule> lookupRule( String name ) {

    return rules.get( name );
  }

  /**
   * Integer)
   */
  public SortRules lookupSortRules( Integer level ) {

    return currentIndex.lookupSortRules( level );
  }

  /**
   * java.lang.String, String)
   */
  public PageReference makePageReference( String encap, String page ) {

    return currentIndex.makePageReference( encap, page );
  }

  /**
   * java.lang.String[])
   */
  public void orderLocationClasses( String[] list ) throws LException {

    currentIndex.orderLocationClasses( list );
  }

  public void print( PrintStream stream ) {

    stream.print( "[index-container " );
    stream.print( "]" );
  }

  /**
   * Setter for the current index.
   *
   * @param name the name
   * @return {@code true} if the index is found and the set is
   * successful
   */
  public boolean setCurrentIndex( String name ) {

    StructuredIndex index = indexMap.get( name );
    if( index == null ) {
      return false;
    }
    currentIndex = index;
    return true;
  }

  /**
   * Setter for depth.
   *
   * <p>
   * The request is delegated to the current index.
   * </p>
   *
   * @param depth the depth to set
   */
  public void setDepth( int depth ) {

    currentIndex.setDepth( depth );
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * The request is delegated to the current index.
   * </p>
   *
   * @see org.extex.exindex.core.type.MarkupContainer#setMarkup(
   *java.lang.String, org.extex.exindex.core.type.markup.Markup)
   */
  public void setMarkup( String name, Markup m ) {

    currentIndex.setMarkup( name, m );
  }

  public int sortRuleSize() {

    return currentIndex.sortRuleSize();
  }

  /**
   * Store an index entry.
   *
   * @param entry  the entry to store
   * @param logger the logger
   * @throws IndexerException in case of an error
   */
  public void store( RawIndexentry entry, Logger logger )
      throws IndexerException {

    String name = entry.getIndex();
    if( name == null ) {
      name = DEFAULT_INDEX;
    }
    StructuredIndex index = indexMap.get( name );
    if( index != null ) {
      index.store( entry );
    }
  }

}
