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
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function <code>text.prefix$</code>
 * <p>
 * This function extracts a prefix of a certain length from a text. The length
 * is the number of character units. Special characters in braces are counted as
 * a single unit. If closing braces are missing the missing characters are
 * provided automatically. Thus no unbalanced braces are contained in the result
 * of this function.
 * </p>
 * <img src="doc-files/text.prefix.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *   key #3 text.prefix$
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X documentation:
 * <dt>
 * <dd>Pops the top two literals (the integer literal <i>len</i> and a string
 * literal, in that order). It pushes the substring of the (at most) <i>len</i>
 * consecutive text characters starting from the beginning of the string. This
 * function is similar to <code>substring</code>, but this one considers a
 * ``special character'', even if it's missing its matching right brace, to be a
 * single text character (rather than however many ASCII characters it actually
 * comprises), and this function doesn't consider braces to be text characters;
 * furthermore, this function appends any needed matching right braces.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TextPrefix extends AbstractCode {

    /**
     * Compute the text prefix like the B<small>IB</small><span
     * style="margin-left: -0.15em;" >T</span><span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
     * >e</span>X built-in function <code>text.prefix$</code>.
     * 
     * @param s the input string
     * @param length the length of the prefix
     * 
     * @return the prefix
     */
    private static String textPrefix(String s, int length) {

        int len = length;
        int level = 0;
        int sLength = s.length();
        int i;
        char c;

        for (i = 0; i < sLength && len > 0; i++) {
            c = s.charAt(i);
            if (c == '{') {
                level++;
                if (level == 1 && i < sLength - 1 && s.charAt(i + 1) == '\\') {
                    i += 2;
                    while (i < sLength && level > 0) {
                        c = s.charAt(++i);
                        if (c == '{') {
                            level++;
                        } else if (c == '}') {
                            level--;
                        }
                    }
                    len--;
                }

            } else if (c == '}') {
                level--;
            } else {
                len--;
            }
        }

        StringBuilder sb = new StringBuilder(s.substring(0, i));

        while (level-- > 0) {
            sb.append('}');
        }

        String value = sb.toString();
        return value;
    }


    public TextPrefix() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public TextPrefix(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        int len = processor.popInteger(locator).getInt();
        String s = processor.popString(locator).getValue();
        processor.push(new TString(textPrefix(s, len), locator));
    }

}
