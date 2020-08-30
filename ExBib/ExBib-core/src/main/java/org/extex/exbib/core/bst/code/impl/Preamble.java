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
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.ValueItem;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code preamble$}
 * <p>
 * This function takes the collected preamble from the database and pushes it as
 * string onto the stack.
 * </p>
 * <img src="doc-files/preamble.png" alt="preamble">
 * 
 * <pre>
 *   preamble$
 * </pre>
 * 
 * <hr>
 * 
 * <dl>
 * <dt>BibTeX documentation:</dt>
 * <dd>Pushes onto the stack the concatenation of all the
 * {@code {@literal @PREAMBLE}} strings read from the database files.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Preamble extends AbstractCode {

    /**
     * Create a new object.
     */
    public Preamble() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Preamble(String name) {

        super(name);
    }

    /**
*      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        StringBuilder sb = new StringBuilder();
        for (ValueItem item : processor.getDB().getPreamble()) {
            item.expand(sb, processor.getDB());
        }

        processor.push(new TString(sb.toString(), locator));
    }
}
