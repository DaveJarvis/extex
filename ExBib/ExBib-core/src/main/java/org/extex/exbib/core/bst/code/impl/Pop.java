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

/**
 * B<small>IB</small>T<sub>E</sub>X built-in function <code>pop$</code>
 * <p>
 * This function pops a string from the stack and prints it as a message to the
 * output stream.
 * </p>
 * <img src="doc-files/pop.png"/>
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 * FUNCTION {output}
 * { duplicate$ empty$
 *     'pop$
 *     'output.nonnull
 *   if$
 * }
 * </pre>
 * 
 * <hr />
 * 
 * 
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:</dt>
 * <dd> Pops the top of the stack but doesn't print it; this gets rid of an
 * unwanted stack literal. </dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small>T<sub>E</sub>X documentation:</dt>
 * <dd> The <code>built_in</code> function <code>pop$</code> pops the top of
 * the stack but doesn't print it. </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Pop extends AbstractCode {

    /**
     * Create a new object.
     */
    public Pop() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Pop(String name) {

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

        processor.pop(locator);
    }

}
