/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function
 * <code>text.length$</code>
 * <p>
 * This function computes the length of a text. The length is the number of text
 * characters. Whitespace braces and brackets do not count as text characters. A
 * T<sub>E</sub>X control sequence counts as one character &ndash; no matter
 * how long the name may be.
 * </p>
 * <img src="doc-files/text.length.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *   preamble$ text.length$
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top (string) literal, and pushes the number of text characters
 * it contains, where an accented character (more precisely, a ``special
 * character'', defined in Section&nbsp;4) counts as a single text character,
 * even if it's missing its matching right brace, and where braces don't count
 * as text characters. </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TextLength extends AbstractCode {

    /**
     * Create a new object.
     */
    public TextLength() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public TextLength(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
     *      BstProcessor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        String s = processor.pop(locator).getValue();
        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '[':
                case ']':
                case '{':
                case '}':
                    break;
                case '\\':

                    if (i++ < s.length() && Character.isLetter(s.charAt(i))) {
                        while ((++i < s.length())
                                && Character.isLetter(s.charAt(i))) {
                            //
                        }

                        result++;
                    }

                    break;
                default:
                    result++;
            }
        }

        processor.push(new TInteger(result, locator));
    }
}
