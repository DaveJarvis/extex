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

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function
 * <code>text.prefix$</code>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top two literals (the integer literal <i>len</i> and a string
 * literal, in that order). It pushes the substring of the (at most) <i>len</i>
 * consecutive text characters starting from the beginning of the string. This
 * function is similar to <code>substring</code>, but this one considers a
 * ``special character'', even if it's missing its matching right brace, to be a
 * single text character (rather than however many ASCII characters it actually
 * comprises), and this function doesn't consider braces to be text characters;
 * furthermore, this function appends any needed matching right braces. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X web documentation:</dt>
 * <dd>
 * 
 * </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TextPrefix extends AbstractCode {

    /**
     * Creates a new object.
     */
    public TextPrefix() {

        super();
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
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
     *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        int len = processor.popInteger(locator).getInt();
        String s = processor.popString(locator).getValue();
        int level = 0;
        int length = s.length();
        int i;
        char c;

        for (i = 0; i < length && len > 0; i++) {
            c = s.charAt(i);
            if (c == '{') {
                level++;
                if (level == 1 && i < length - 1 && s.charAt(i + 1) == '\\') {
                    i += 2;
                    while (i < length && level > 0) {
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

        StringBuffer sb = new StringBuffer(s.substring(0, i));

        while (level-- > 0) {
            sb.append('}');
        }

        processor.push(new TString(sb.toString(), locator));
    }
}
