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
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * BibT<sub>E</sub>X built-in function <code>int.to.str$</code>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top (integer) literal, interpreted as the ASCII integer value
 * of a single character, converts it to the corresponding single-character
 * string, and pushes this string. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X web documentation:</dt>
 * <dd> The <code>built_in</code> function <code>int.to.str$</code> pops the
 * top (integer) literal, converts it to its (unique) string equivalent, and
 * pushes this string. If the literal isn't an integer, it complains and pushes
 * the null string. </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class IntToStr extends AbstractCode {

    /**
     * Create a new object.
     */
    public IntToStr() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public IntToStr(String name) {

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

        int i = processor.popInteger(locator).getInt();
        processor.push(new TString(String.valueOf(i)));
    }

}
