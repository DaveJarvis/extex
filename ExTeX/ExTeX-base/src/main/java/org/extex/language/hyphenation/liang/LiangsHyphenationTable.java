/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.language.hyphenation.liang;

import org.extex.core.UnicodeChar;
import org.extex.core.UnicodeCharList;
import org.extex.language.hyphenation.base.BaseHyphenationTable;
import org.extex.language.hyphenation.exception.*;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;

import java.util.logging.Logger;

/**
 * This class stores the values for hyphenations and hyphenates words. It uses
 * Liang's algorithm as described in the TeX book.
 *
 * <p>Liang's Algorithm</p>
 *
 * <p>
 * The hyphenation in TeX is based on Liang's thesis. This algorithm is based on
 * patterns which consist of characters or a special marker for the beginning
 * and the end of the word. For each pattern it is characterized how desirable
 * or undesirable it would be to hyphenate before, between, or after it.
 * </p>
 * <p>
 * This weighted hyphenation codes cna be represented by integers. The even
 * integers denote the undesirable positions and the odd numbers denote the
 * optional hyphenation points.
 * </p>
 * <p>
 * Let us consider the pattern {@code hyph} this pattern has associated to it
 * the code {@code 00300}. The first number corresponds to the position before
 * the letter h, the second number to the position before the letter p, and so
 * on. Thus this pattern indicates that a hyphenation point can be inserted
 * between y and p. This leads to {@code hy\-ph} if written explicitly in
 * TeX.
 * </p>
 * <p>
 * The following table shows some more examples taken from the original
 * hyphenation patterns of TeX for English. The character . denotes the
 * beginning or the
 * end of a word. In the TeX patterns the word pattern and the hyphenation
 * codes are
 * intermixed and the hyphenation codes 0 are left out.
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td>Word pattern</td>
 * <td>Codes</td>
 * <td>TeX Pattern</td>
 * </tr>
 * <tr>
 * <td>ader.</td>
 * <td>005000</td>
 * <td>{@code ad5er. }</td>
 * </tr>
 * <tr>
 * <td>.ach</td>
 * <td>00004</td>
 * <td>{@code .ach4  }</td>
 * </tr>
 * <tr>
 * <td>sub</td>
 * <td>0043</td>
 * <td>{@code su4b3  }</td>
 * </tr>
 * <tr>
 * <td>ty</td>
 * <td>100</td>
 * <td>{@code 1ty    }</td>
 * </tr>
 * <tr>
 * <td>type</td>
 * <td>00003</td>
 * <td>{@code type3  }</td>
 * </tr>
 * <tr>
 * <td>pe.</td>
 * <td>4000</td>
 * <td>{@code pe.    }</td>
 * </tr>
 * </table>
 *
 * <p>
 * To find all hyphenation points in a word all matching patterns have to be
 * superimposed. During this superposition the higher hyphenation codes overrule
 * the lower ones.
 * </p>
 * <p>
 * In the following figure the patterns for the word ``subtype'' are shown.
 * </p>
 *
 * <pre>{@code
 *  <sub> </sub>s<sub> </sub>u<sub> </sub>b<sub> </sub>t<sub> </sub>y<sub> </sub>p<sub> </sub>e
 *  <sub>0</sub>s<sub>0</sub>u<sub>4</sub>b<sub>3</sub>
 *  <sub> </sub> <sub> </sub> <sub> </sub> <sub>1</sub>t<sub>0</sub>y<sub>0</sub>
 *  <sub> </sub> <sub> </sub> <sub> </sub> <sub>0</sub>t<sub>0</sub>y<sub>0</sub>p<sub>0</sub>e<sub>3</sub>
 *  <sub> </sub> <sub> </sub> <sub> </sub> <sub> </sub> <sub> </sub> <sub>4</sub>p<sub>0</sub>e<sub>3</sub>.
 *  ---------------
 *  <sub>0</sub>s<sub>0</sub>u<sub>4</sub>b<sub>3</sub>t<sub>0</sub>y<sub>4</sub>p<sub>0</sub>e<sub>3</sub>
 * }</pre>
 * <p>
 * The superposition of all patterns leads to the result {@code sub\-type\-}.
 * Here two additional parameters come into play. {@code \lefthyphenmin}
 * denotes the minimal number of characters before a hyphenation at the
 * beginning of a word and {@code \righthyphenmin} the corresponding length at
 * the end of a word. {@code \lefthyphenmin} is set to 2 and
 * {@code \righthyphenmin} to 3 for English in TeX. Thus the final hyphen is
 * not considered.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LiangsHyphenationTable extends BaseHyphenationTable {

  /**
   * The field {@code BORDER} contains the Unicode character internally used
   * as a marker for the beginning and the end of the word.
   */
  private static final UnicodeChar BORDER = null;

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 20060305L;

  /**
   * The field {@code compressed} contains the indicator that the hyphenation
   * table has been compressed. A compressed table can not be modified any
   * more.
   */
  private boolean compressed = false;

  /**
   * The field {@code patterns} contains the tree of hyphenation patterns.
   */
  private final HyphenTree patterns = new HyphenTree( new char[ 0 ] );


  public LiangsHyphenationTable() {

  }

  /**
   * This methods allows the caller to add another pattern.
   *
   * @param pattern a sequence of tokens alternatively of type other and
   *                letter. The other tokens must be numbers. The letter
   *                tokens period
   *                (.) are interpreted as beginning of word or end of word
   *                marker.
   * @throws IllegalValueHyphenationException in case that an other token does
   *                                          not carry a digit
   * @throws IllegalTokenHyphenationException in case that an illegal token
   *                                          has been detected in the pattern
   * @throws DuplicateHyphenationException    in case that a hyphenation
   * pattern
   *                                          is tried to be added a second
   *                                          time
   * @throws ImmutableHyphenationException    in case that the hyphenation
   * table
   *                                          is immutable; i.e. the
   *                                          compressed flag is set
   * @see org.extex.language.Language#addPattern(Tokens)
   */
  @Override
  public void addPattern( Tokens pattern )
      throws IllegalValueHyphenationException,
      IllegalTokenHyphenationException,
      DuplicateHyphenationException,
      ImmutableHyphenationException {

    if( compressed ) {
      throw new ImmutableHyphenationException( null );
    }

    int length = pattern.length();

    if( length == 0 ) {
      return;
    }

    char[] code = new char[ (length + 1) / 2 ];
    int codeIndex = 0;
    HyphenTree tree = patterns;
    boolean expectLetter = false;

    for( int i = 0; i < length; i++ ) {
      Token t = pattern.get( i );
      UnicodeChar c = t.getChar();
      if( t instanceof OtherToken ) {
        int hyphenCode = c.getCodePoint();
        if( expectLetter ) {
          throw new IllegalTokenHyphenationException( t.toString() );
        }
        else if( hyphenCode < '0' || hyphenCode > '9' ) {
          throw new IllegalValueHyphenationException( t.toString() );
        }
        code[ codeIndex++ ] = (char) hyphenCode;
        expectLetter = true;
      }
      else if( t instanceof LetterToken ) {
        if( !expectLetter ) {
          throw new IllegalTokenHyphenationException( t.toString() );
        }
        tree = tree.insert( c, null );
        HyphenTree.superimpose( code, 0, tree.getHyphenationCode() );
        expectLetter = false;
      }
      else {
        throw new IllegalTokenHyphenationException( t.toString() );
      }
    }
    tree.setHyphenationCode( code );
    tree.superimposeAll( code );
  }

  /**
   * Write the tree to a logger.
   *
   * @param logger the target logger
   */
  public void dump( Logger logger ) {

    patterns.dump( logger, "" );
  }

  /**
   * Getter for patterns. This method is meant for testing purposes only.
   *
   * @return the patterns
   */
  protected HyphenTree getPatterns() {

    return this.patterns;
  }

  /**
   * org.extex.typesetter.TypesetterOptions, org.extex.core.UnicodeChar,
   * int, boolean, org.extex.typesetter.type.node.factory.NodeFactory)
   */
  @Override
  public boolean hyphenate( NodeList nodelist, TypesetterOptions context,
                            UnicodeChar hyphen, int start, boolean forall,
                            NodeFactory nodeFactory )
      throws HyphenationException {

    if( hyphen == null || !isHyphenating() || nodelist.size() < 2 ) {
      return false;
    }

    Node hn = nodeFactory.getNode( context.getTypesettingContext(), hyphen );
    if( !(hn instanceof CharNode) ) {
      return false;
    }
    CharNode hyphenNode = (CharNode) hn;

    UnicodeCharList word = new UnicodeCharList();
    int next = findWord( nodelist, start, word );
    boolean modified =
        hyphenateOne( nodelist, context, start, word, hyphenNode );

    if( forall ) {
      for( int i = next; i < nodelist.size(); i = next ) {
        word.clear();
        next = findWord( nodelist, i, word );
        modified =
            (hyphenateOne( nodelist, context, start, word,
                           hyphenNode ) || modified);
      }
    }

    return modified;
  }

  /**
   * org.extex.typesetter.TypesetterOptions, int,
   * org.extex.core.UnicodeCharList,
   * org.extex.typesetter.type.node.CharNode)
   */
  @Override
  public boolean hyphenateOne( NodeList nodelist, TypesetterOptions context,
                               int start, UnicodeCharList word,
                               CharNode hyphenNode )
      throws HyphenationException {

    int len = word.size();
    if( len <= 1 ) {
      return false;
    }

    if( super.hyphenateOne( nodelist, context, start, word, hyphenNode ) ) {
      return true;
    }

    int leftHyphenMin = (int) getLeftHyphenMin();
    int rightHyphenMin = (int) getRightHyphenMin();
    if( len < leftHyphenMin + rightHyphenMin ) {
      return false;
    }

    char[] hyph = new char[ len + 2 ];
    UnicodeChar[] chars = new UnicodeChar[ len + 2 ];
    int idx = 0; // pointer into hyph; in sync with the current char
    chars[ idx++ ] = BORDER;
    for( int i = 0; i < len; i++ ) {
      chars[ idx++ ] = word.get( i );
    }
    chars[ idx ] = BORDER;

    for( int i = 0; i < len; i++ ) {
      HyphenTree.superimpose( hyph, i, patterns.get( chars, i ) );
    }

    for( int i = 0; i < leftHyphenMin; i++ ) {
      hyph[ i ] = '0';
    }
    for( int i = 0; i < rightHyphenMin; i++ ) {
      hyph[ hyph.length - i - 1 ] = '0';
    }

    boolean hasNoHyphen = true;
    boolean[] isHyph = new boolean[ hyph.length ];

    for( int i = 0; i < hyph.length; i++ ) {
      if( ((hyph[ i ]) & 1) != 0 ) {
        isHyph[ i ] = true;
        hasNoHyphen = false;
      }
      else {
        isHyph[ i ] = false;
      }
    }
    if( hasNoHyphen ) {
      return false;
    }

    insertShy( nodelist, start, isHyph, hyphenNode );

    return true;
  }

  /**
   * Getter for compressed.
   *
   * @return the compressed
   */
  protected boolean isCompressed() {

    return this.compressed;
  }

  /**
   * Setter for compressed.
   */
  protected void setCompressed() {

    this.compressed = true;
  }

}
