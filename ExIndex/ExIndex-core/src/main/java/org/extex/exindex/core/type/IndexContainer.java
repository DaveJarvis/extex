/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.exindex.core.exception.IndexerException;
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
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a container for indices and the parameters needed to
 * process them.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IndexContainer
        implements
            LValue,
            AlphabetContainer,
            AttributesContainer,
            CrossrefClassContainer,
            LocationClassContainer,
            MarkupContainer,
            RuleSetContainer,
            SortRuleContainer,
            FallbackContainer {

    /**
     * The constant <tt>DEFAULT_INDEX</tt> contains the name of the default
     * index.
     */
    public static final String DEFAULT_INDEX = "";

    /**
     * The field <tt>map</tt> contains the mapping from name to index.
     */
    private Map<String, StructuredIndex> indexMap =
            new HashMap<String, StructuredIndex>();

    /**
     * The field <tt>currentIndex</tt> contains the current index.
     */
    private StructuredIndex currentIndex;

    /**
     * The field <tt>rules</tt> contains the mapping from names to rule sets.
     */
    private Map<String, List<Rule>> rules = new HashMap<String, List<Rule>>();

    /**
     * The field <tt>alphabetMap</tt> contains the alphabets.
     */
    private Map<String, Alphabet> alphabetMap = new HashMap<String, Alphabet>();

    /**
     * The field <tt>map</tt> contains the mapping from name to the boolean
     * indicator.
     */
    private Map<String, Boolean> crossrefClassMap =
            new HashMap<String, Boolean>();

    /**
     * The field <tt>locationClassMap</tt> contains the mapping from names to
     * location classes.
     */
    private Map<String, LocationClass> locationClassMap =
            new HashMap<String, LocationClass>();

    /**
     * The field <tt>locationClasses</tt> contains the list of classes already
     * defined.
     */
    private List<LocationClass> locationClasses =
            new ArrayList<LocationClass>();

    /**
     * Creates a new object.
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public IndexContainer() throws SecurityException, NoSuchMethodException {

        super();
        // the default index has no fallback
        currentIndex = new StructuredIndex(null);
        indexMap.put(null, currentIndex);
        indexMap.put(DEFAULT_INDEX, currentIndex);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.alphabet.AlphabetContainer#addAlphabet(
     *      java.lang.String, org.extex.exindex.core.type.alphabet.Alphabet)
     */
    public void addAlphabet(String name, Alphabet alphabet) {

        alphabetMap.put(name, alphabet);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.LocationClassContainer#addLocationClass(
     *      java.lang.String,
     *      org.extex.exindex.core.type.alphabet.LocationClass)
     */
    public boolean addLocationClass(String name, LocationClass locationClass) {

        LocationClass lc = locationClassMap.get(name);
        if (lc != null) {
            locationClassMap.put(name, locationClass);
            locationClasses.remove(lc);
            locationClasses.add(locationClass);
            return false;
        }

        locationClassMap.put(name, locationClass);
        locationClasses.add(locationClass);
        return true;
    }

    /**
     * Add a merge rule.
     * 
     * @param rule the rule
     */
    public void addMergeRule(Rule rule) {

        currentIndex.addMergeRule(rule);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.rules.RuleSetContainer#addRule(
     *      java.lang.String, java.util.List)
     */
    public void addRule(String name, List<Rule> rule) {

        rules.put(name, rule);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.rules.SortRuleContainer#addSortRule(
     *      Integer, org.extex.exindex.core.type.rules.Rule)
     */
    public void addSortRule(Integer level, Rule rule) {

        currentIndex.addSortRule(level, rule);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.rules.SortRuleContainer#addSortRules(
     *      Integer, java.util.List)
     */
    public void addSortRules(Integer level, List<Rule> ruleList) {

        currentIndex.addSortRules(level, ruleList);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.FallbackContainer#applyMergeRuleFallback(
     *      String)
     */
    public String applyMergeRuleFallback(String text) {

        return get(DEFAULT_INDEX).applyMergeRule(text);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.FallbackContainer#applySortRuleFallback(
     *      java.lang.String, int)
     */
    public String applySortRuleFallback(String text, int level) {

        return get(DEFAULT_INDEX).applySortRule(text, level);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.attribute.AttributesContainer#defineAttribute(
     *      java.lang.String, org.extex.exindex.core.type.attribute.Attribute)
     */
    public void defineAttribute(String key, Attribute attribute) {

        currentIndex.defineAttribute(key, attribute);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.CrossrefClassContainer#defineCrossrefClass(
     *      java.lang.String, java.lang.Boolean)
     */
    public void defineCrossrefClass(String name, Boolean verified) {

        crossrefClassMap.put(name, verified);
    }

    /**
     * define or redefine an index. If an index with the given name already
     * exists then this index is returned. Otherwise a new one is created and
     * returned.
     * 
     * @param name the name of the index
     * 
     * @return the index found or created
     */
    public StructuredIndex defineIndex(String name) {

        StructuredIndex index = indexMap.get(name);
        if (index == null) {
            index = new StructuredIndex(this);
            indexMap.put(name, index);
        }
        return index;
    }

    /**
     * Search for a letter group in the current index and define it if it is not
     * defined already.
     * 
     * @param name the name of the letter group to get
     * 
     * @return the letter group for name
     */
    public LetterGroup defineLetterGroup(String name) {

        return currentIndex.defineLetterGroup(name);
    }

    /**
     * Set the markup info associated to a certain name to a given object.
     * 
     * @param name the name
     * @param markup the markup
     */
    public void defineMarkup(String name, Markup markup) {

        currentIndex.setMarkup(name, markup);
    }

    /**
     * Getter for a named index.
     * 
     * @param name the name of the index.
     * 
     * @return the named index or <code>null</code>
     */
    public StructuredIndex get(String name) {

        return indexMap.get(name);
    }

    /**
     * Getter for the current index.
     * 
     * @return the current index
     */
    public StructuredIndex getCurrentIndex() {

        return currentIndex;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.FallbackContainer#getFallbackMarkup(String)
     */
    public Markup getFallbackMarkup(String name) {

        return get(DEFAULT_INDEX).getMarkup(name);
    }

    /**
     * Getter for the markup.
     * 
     * @param name the name
     * 
     * @return the markup or <code>null</code> if it is not defined
     */
    public Markup getMarkup(String name) {

        Markup markup = currentIndex.getMarkup(name);

        if (markup == null) {
            MarkupContainer fallbackIndex = indexMap.get(DEFAULT_INDEX);
            if (currentIndex != fallbackIndex) {
                markup = fallbackIndex.getMarkup(name);
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
     *      java.lang.String)
     */
    public boolean isAttributeDefined(String name) {

        return currentIndex.isAttributeDefined(name);
    }

    /**
     * Add links to all prefixes and return the equivalence class used in the
     * current index. The equivalence class is determined from the name and the
     * prefixes. If for one a letter group is known then this one is used.
     * Otherwise a new one is created.
     * <p>
     * If a prefix has already assigned a different letter group then this is
     * reported as error.
     * </p>
     * 
     * @param name the name
     * @param prefixes the prefixes
     * 
     * @return the letter group for the whole set
     * @throws LException in case of an error
     */
    public LetterGroup linkPrefixes(String name, LList prefixes)
            throws LException {

        return currentIndex.linkPrefixes(name, prefixes);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.alphabet.AlphabetContainer#lookupAlphabet(
     *      java.lang.String)
     */
    public Alphabet lookupAlphabet(String name) {

        return alphabetMap.get(name);

    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * The request is delegated to the current index.
     * </p>
     * 
     * @see org.extex.exindex.core.type.attribute.AttributesContainer#lookupAttribute(
     *      java.lang.String)
     */
    public Attribute lookupAttribute(String attribute) {

        return currentIndex.lookupAttribute(attribute);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.CrossrefClassContainer#lookupCrossrefClass(
     *      java.lang.String)
     */
    public Boolean lookupCrossrefClass(String name) {

        return crossrefClassMap.get(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.LocationClassContainer#lookupLocationClass(
     *      java.lang.String)
     */
    public LocationClass lookupLocationClass(String name) {

        return locationClassMap.get(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.rules.SortRuleContainer#lookupOrCreateSortRule(
     *      Integer)
     */
    public SortRules lookupOrCreateSortRule(Integer level) {

        return currentIndex.lookupOrCreateSortRule(level);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.rules.RuleSetContainer#lookupRule(
     *      java.lang.String)
     */
    public List<Rule> lookupRule(String name) {

        return rules.get(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.rules.SortRuleContainer#lookupSortRules(Integer)
     */
    public SortRules lookupSortRules(Integer level) {

        return currentIndex.lookupSortRules(level);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.LocationClassContainer#makePageReference(
     *      java.lang.String, java.lang.String)
     */
    public PageReference makePageReference(String encap, String s) {

        for (LocationClass lc : locationClasses) {
            PageReference pr = lc.match(encap, s);
            if (pr != null) {
                return pr;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.LocationClassContainer#orderLocationClasses(
     *      java.lang.String[])
     */
    public void orderLocationClasses(String[] list) throws LException {

        List<LocationClass> newClasses = new ArrayList<LocationClass>();

        for (String s : list) {
            LocationClass lc = locationClassMap.get(s);
            int i = locationClasses.indexOf(lc);
            if (i < 0) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("UnknownClass", s));
            }
            newClasses.add(lc);
            locationClasses.remove(i);
        }

        newClasses.addAll(locationClasses);
        locationClasses = newClasses;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print("[index-container ");
        stream.print("]");
    }

    /**
     * Setter for the current index.
     * 
     * @param name the name
     */
    public void setCurrentIndex(String name) {

        StructuredIndex index = indexMap.get(name);
        if (index == null) {
            // TODO error handling
            return;
        }
        currentIndex = index;
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
    public void setDepth(int depth) {

        currentIndex.setDepth(depth);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * The request is delegated to the current index.
     * </p>
     * 
     * @see org.extex.exindex.core.type.MarkupContainer#setMarkup(
     *      java.lang.String, org.extex.exindex.core.type.markup.Markup)
     */
    public void setMarkup(String name, Markup m) {

        currentIndex.setMarkup(name, m);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.rules.SortRuleContainer#sortRuleSize()
     */
    public int sortRuleSize() {

        return currentIndex.sortRuleSize();
    }

    /**
     * Store an index entry.
     * 
     * @param entry the entry to store
     * @param logger the logger
     * 
     * @throws IndexerException in case of an error
     */
    public void store(RawIndexentry entry, Logger logger)
            throws IndexerException {

        String name = entry.getIndex();
        if (name == null) {
            name = DEFAULT_INDEX;
        }
        StructuredIndex index = indexMap.get(name);
        if (index == null) {
            return;
        }
        index.store(entry);
    }

}
