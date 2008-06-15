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
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function <code>while$</code>
 * <p>
 * This function is a loop construction. It takes two arguments form the stack.
 * Both of them are code segments. The first argument is code to be executed
 * repeatedly. The second argument is a conditional. The conditional is
 * executed. If the topmost element of the stack is numeric and not 0 then the
 * first argument is executed and the process is repeated.
 * </p>
 * <p>
 * If the stack has less than two elements, the elements are of the wrong type,
 * or the conditional does not leave a number on the stack then an error is
 * raised.
 * </p>
 * <img src="doc-files/while.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *     { namesleft #0 &gt; }
 *     { s nameptr "{ff~}{vv~}{ll}{, jj}" format.name$ 't :=
 * 
 *       % some more action
 * 
 *       nameptr #1 + 'nameptr :=
 *       namesleft #1 - 'namesleft :=
 *     }
 *   while$
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:
 * <dt>
 * <dd> Pops the top two (function) literals, and keeps executing the second as
 * long as the (integer) literal left on the stack by executing the first is
 * greater than 0. </dd>
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
public class While extends AbstractCode {

    /**
     * Create a new object.
     */
    public While() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public While(String name) {

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

        Token body = processor.pop(locator);
        Token cond = processor.pop(locator);

        for (cond.execute(processor, entry, locator); processor.popInteger(
            locator).getInt() > 0; cond.execute(processor, entry, locator)) {
            body.execute(processor, entry, locator);
        }
    }
}
