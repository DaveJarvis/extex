/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.language.word.impl;

import org.extex.core.UnicodeChar;
import org.extex.core.UnicodeCharList;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.language.word.WordTokenizer;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.*;

/**
 * This class tokenizes a list of nodes according to the rules of TeX.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TeXWords extends ExTeXWords implements WordTokenizer {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2006L;


  public TeXWords() {

  }

  /**
   * Add the characters extracted from a char node to the word container.
   *
   * @param node the character node to add to the word
   * @param word the container to add the node to
   */
  private void addWord( CharNode node, UnicodeCharList word ) {

    if( node instanceof LigatureNode ) {
      LigatureNode ln = (LigatureNode) node;
      CharNode[] chars = ln.getChars();
      for( int j = 0; j < chars.length; j++ ) {
        word.add( chars[ j ].getCharacter() );
      }

    }
    else {
      word.add( (node).getCharacter() );
    }
  }

  /**
   * Collect all characters form a node list that make up a word.
   *
   * @param nodes the nodes to process
   * @param word  the word with hyphenation marks
   * @param start the start index
   * @param lang  the language in effect
   * @return the index of the first node past the ones processed
   * @throws HyphenationException in case of an error
   */
  private int collectWord( NodeList nodes, UnicodeCharList word, int start,
                           Language lang ) throws HyphenationException {

    int i = start;
    int size = nodes.size();

    Node n;
    CharNode cn;

    for( ; i < size; i++ ) {
      n = nodes.get( i );
      if( n instanceof CharNode ) {
        cn = (CharNode) n;

        if( cn.getTypesettingContext().getLanguage() != lang ) {
          return i;
        }

        addWord( cn, word );

      }
      else if( n instanceof WhatsItNode ) {
        // ignored
      }
      else if( n instanceof DiscretionaryNode ) {
        i = collectWord( nodes, word, i + 1, lang );
        return findWord( nodes, i, word );

      }
      else if( n instanceof KernNode ) {
        if( n instanceof ExplicitKernNode ) {
          break;
        }
      }
      else {
        break;
      }
    }

    return i;
  }

  /**
   * int, org.extex.core.UnicodeCharList)
   */
  @Override
  public int findWord( NodeList nodes, int start, UnicodeCharList word )
      throws HyphenationException {

    int i = start;
    int size = nodes.size();
    word.clear();
    Node n;

    do {
      if( i >= size ) {
        return i;
      }
      n = nodes.get( i++ );
    } while( !(n instanceof CharNode) );

    addWord( (CharNode) n, word );

    return collectWord( nodes, word, i, ((CharNode) n)
        .getTypesettingContext().getLanguage() );
  }

  /**
   * org.extex.typesetter.TypesetterOptions)
   */
  @Override
  public UnicodeCharList normalize( UnicodeCharList word,
                                    TypesetterOptions options )
      throws HyphenationException {

    UnicodeCharList list = new UnicodeCharList();
    int size = word.size();
    UnicodeChar c;

    for( int i = 0; i < size; i++ ) {
      UnicodeChar uc = word.get( i );
      c = options.getLccode( uc );
      list.add( c == null ? uc : c );
    }

    return list;
  }

}
