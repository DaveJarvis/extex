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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function <code>empty$</code>
 * <p>
 * This function pops a literal from the stack. If the argument is a string it
 * checks whether it contains only whitespace characters. If the argument is a
 * field reference it checks whether the field is missing in the current entry.
 * It pushes the integer 1 in case it succeeds and 0 if it fails.
 * </p>
 * <img src="doc-files/empty.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *   preamble$ empty$
 *     'skip$
 *     { preamble$ write$ newline$ }
 *   if$
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
 * <dd>Pops the top literal and pushes the integer 1 if it's a missing field or
 * a string having no non-white-space characters, 0 otherwise.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Empty extends AbstractCode {

    /**
     * Create a new object.
     */
    public Empty() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Empty(String name) {

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

        String arg = processor.popString(locator).getValue();

        processor.push(arg.matches("\\s*")
                ? TokenFactory.T_ONE
                : TokenFactory.T_ZERO);
    }

}
