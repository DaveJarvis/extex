/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst.code.impl;

import java.util.Iterator;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.ValueItem;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * BibT<sub>E</sub>X built-in function <code>preamble$</code>
 * 
 * <dl>
 * <dt>BibT<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pushes onto the stack the concatenation of all the
 * <code>@PREAMBLE</code> strings read from the database files. </dd>
 *           </dl>
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
public class Preamble extends AbstractCode {

    /**
     * Create a new object.
     */
    public Preamble() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Preamble(String name) {

        super();
        setName(name);
    }

    /**
     * @see org.extex.exbib.core.bst.Code#execute(org.extex.exbib.core.bst.Processor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        StringBuffer sb = new StringBuffer();
        Iterator<ValueItem> iterator =
                processor.getDB().getPreamble().iterator();

        while (iterator.hasNext()) {
            iterator.next().expand(sb, processor.getDB());
        }

        processor.push(new TString(sb.toString()));
    }
}