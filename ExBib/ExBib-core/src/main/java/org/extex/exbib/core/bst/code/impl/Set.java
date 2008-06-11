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
import org.extex.exbib.core.bst.Code;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TField;
import org.extex.exbib.core.bst.node.impl.TFieldInteger;
import org.extex.exbib.core.bst.node.impl.TFieldString;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibMissingLiteralException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function <code>set$</code>
 * <p>
 * This function assigns a value to a variable or field. It takes two arguments
 * from the stack. The first argument is the name of the target. In general it
 * needs to be quoted. The second argument is the appropriate value.
 * </p>
 * <img src="doc-files/set.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 *     { namesleft #0 &gt; }
 *     { 
 *       % some actions
 * 
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
 * <dd> Pops the top two literals and assigns to the first (which must be a
 * global or entry variable) the value of the second. </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Set extends AbstractCode {

    /**
     * Create a new object.
     */
    public Set() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Set(String name) {

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

        Token a = processor.pop(locator);

        if (!(a instanceof TLiteral)) {
            throw new ExBibMissingLiteralException(a.toString(), locator);
        }
        String var = a.getValue();
        Code code = processor.getFunction(var);

        if (code instanceof TFieldString) {
            entry.setLocal(var, processor.popString(locator).getValue());
        } else if (code instanceof TFieldInteger) {
            entry.setLocal(var, processor.popInteger(locator).getInt());
        } else if (code instanceof TField) {
            entry.set(var, processor.popString(locator).getValue());
        } else {
            processor.changeFunction(var, processor.pop(locator), locator);
        }
    }

}
