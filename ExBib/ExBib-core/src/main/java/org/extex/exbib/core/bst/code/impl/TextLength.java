/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code text.length$}
 * <p>
 * This function computes the length of a text. The length is the number of text
 * characters. Whitespace braces and brackets do not count as text characters. A
 * TeX control sequence counts as one character &ndash; no matter
 * how long the name may be.
 * </p>
 * <img src="doc-files/text.length.png" alt="text.length">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *   preamble$ text.length$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>Pops the top (string) literal, and pushes the number of text characters
 * it contains, where an accented character (more precisely, a ``special
 * character'', defined in Section&nbsp;4) counts as a single text character,
 * even if it's missing its matching right brace, and where braces don't count
 * as text characters.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TextLength extends AbstractCode {

  /**
   * Compute the text length like the B<small>IB</small><span
   * style="margin-left: -0.15em;" >T</span><span style=
   * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
   * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
   * >e</span>X built-in function {@code text.length$}.
   *
   * @param s the input string
   * @return the length
   */
  public static int textLength( String s ) {

    int result = 0;

    for( int i = 0; i < s.length(); i++ ) {
      switch( s.charAt( i ) ) {
        case ' ':
        case '\t':
        case '\n':
        case '[':
        case ']':
        case '{':
        case '}':
          break;
        case '\\':

          if( ++i < s.length() && Character.isLetter( s.charAt( i ) ) ) {
            do {
              i++;
            } while( i < s.length()
                && Character.isLetter( s.charAt( i ) ) );

            result++;
          }

          break;
        default:
          result++;
      }
    }
    return result;
  }

  /**
   * Create a new object.
   */
  public TextLength() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public TextLength( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  @Override
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    String s = processor.pop( locator ).getValue();
    processor.push( new TInteger( textLength( s ), locator ) );
  }
}
