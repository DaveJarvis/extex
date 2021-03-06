/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.language.word;

import org.extex.core.UnicodeCharList;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;

import java.io.Serializable;

/**
 * This interface describes the contract for a tokenizer which is able to split
 * a list of nodes into words.
 * This kind of tokenizer might be language specific.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface WordTokenizer extends Serializable {

  /**
   * Extract a word from a node list.
   *
   * @param nodes the nodes to extract the word from
   * @param start the start index
   * @param word  the target list for the letters of the word
   * @return the index of the first node beyond the word
   * @throws HyphenationException in case of an error
   */
  int findWord( NodeList nodes, int start, UnicodeCharList word )
      throws HyphenationException;

  /**
   * Insert hyphenation points into a list of nodes.
   *
   * @param nodes          the node list to modify
   * @param insertionPoint the index to insert something into the nodes
   * @param spec           the specification where to insert hyphenation 
   *                       marks.
   *                       If {@code spec[i]} is {@code true} then a hyphen
   *                       needs to be
   *                       inserted before the i<sup>th</sup> character at 
   *                       or after insertionPoint
   *                       in nodes
   * @param hyphenNode     the hyphen as node
   * @throws HyphenationException in case of an error
   */
  void insertShy( NodeList nodes, int insertionPoint, boolean[] spec,
                  CharNode hyphenNode ) throws HyphenationException;

  /**
   * Normalize a word for the lookup.
   *
   * @param word    the word to normalize
   * @param options the options to use
   * @return the normalized word
   * @throws HyphenationException in case of an error
   */
  UnicodeCharList normalize( UnicodeCharList word, TypesetterOptions options )
      throws HyphenationException;

}
