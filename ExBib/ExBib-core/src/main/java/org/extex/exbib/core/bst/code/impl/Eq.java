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
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.node.TokenFactory;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function <code>=</code>
 * 
 * <p>
 * <i> Pops the top two (integer) literals, compares them, and pushes the
 * integer 1 they're equal, 0 otherwise. </i>
 * </p>
 * 
 * The <code>built_in</code> function <code>=<code> pops the top two
 * (integer or string) literals, compares them, and pushes the integer 1
 * if they're equal, 0 otherwise.  If they're not either both string or
 * both integer, it complains and pushes the integer 0.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Eq extends AbstractCode {

    /**
     * Create a new object.
     */
    public Eq() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Eq(String name) {

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

        String a = processor.pop(locator).getValue();
        String b = processor.pop(locator).getValue();
        processor.push(a.equals(b) ? TokenFactory.T_ONE : TokenFactory.T_ZERO);
    }

}
