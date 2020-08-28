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
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function <code>missing$</code>
 * <p>
 * This function determines whether a field is missing. It takes one argument
 * from the stack. If it refers to a missing field it pushes the integer 1 to
 * the stack. If it refers to a defined field it pushes 0.
 * </p>
 * <p>
 * If the stack is empty of the argument does not refer to a field then an error
 * is raised.
 * </p>
 * <img src="doc-files/missing.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *     crossref missing$
 *     { "author and editor" editor either.or.check }
 *     'skip$
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
 * <dd>Pops the top literal and pushes the integer 1 if it's a missing field, 0
 * otherwise.</dd>
 * </dl>
 * 
 * 
 * <p>
 * Missing items are represented as
 * {@link org.extex.exbib.core.bst.token.impl.TString}s with the value
 * <code>null</code>. Those can be distinguished from
 * {@link org.extex.exbib.core.bst.token.impl.TString}s with the value "". They
 * can be detected only by the special method
 * {@link org.extex.exbib.core.bst.token.impl.TString#isNull()}.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Missing extends AbstractCode {

    /**
     * Create a new object.
     */
    public Missing() {

    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Missing(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.Code#execute(BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        if (entry == null) {
            throw new ExBibMissingEntryException(null, locator);
        }

        boolean missing = processor.popString(locator).isNull();
        processor.push(missing ? TokenFactory.T_ONE : TokenFactory.T_ZERO);
    }

}
