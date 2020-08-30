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
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code *}
 * <p>
 * This function takes two string arguments from the stack and pushes the
 * concatenated string back to the stack. If not enough arguments are on the
 * stack or the arguments are no strings then an error is raised.
 * </p>
 * <img src="doc-files/concat.png" alt="concat">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 * 
 * <pre>
 *   "there's a month but no year in " cite$ * warning$
 * </pre>
 * 
 * <hr>
 * 
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>
 * TBD
 * </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Concat extends AbstractCode {

    /**
     * Create a new object.
     */
    public Concat() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Concat(String name) {

        super(name);
    }

    /**
*      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        String a = processor.popString(locator).getValue();
        String b = processor.popString(locator).getValue();
        processor.push(new TString(b + a, locator));
    }

}
