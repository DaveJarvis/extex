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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * BibT<sub>E</sub>X built-in function <code>if$</code>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top three literals (they are two function literals and an
 * integer literal, in that order); if the integer is greater than 0, it
 * executes the second literal, else it executes the first. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X web documentation:</dt>
 * <dd>
 * 
 * </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class If extends AbstractCode {

    /**
     * Creates a new object.
     */
    public If() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public If(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
     *      org.extex.exbib.core.bst.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        Token elseToken = processor.pop(locator);
        Token thenToken = processor.pop(locator);
        int cond = processor.popInteger(locator).getInt();

        if (cond > 0) {
            thenToken.execute(processor, entry, locator);
        } else {
            elseToken.execute(processor, entry, locator);
        }
    }
}
