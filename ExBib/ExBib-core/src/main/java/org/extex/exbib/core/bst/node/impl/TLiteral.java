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

package org.extex.exbib.core.bst.node.impl;

import java.io.IOException;

import org.extex.exbib.core.bst.Code;
import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.exception.ExBibEmptyFunctionNameException;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.node.AbstractToken;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFunctionUndefinedException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a literal token which corresponds to a macro.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TLiteral extends AbstractToken implements Token {

    /**
     * Creates a new object.
     * 
     * @param value the literal value
     * 
     * @throws ExBibIllegalValueException in case that the given value is
     *         <code>null</code> or the empty string
     */
    public TLiteral(String value) throws ExBibIllegalValueException {

        super(value, null);

        if (value == null || value.equals("")) {
            throw new ExBibEmptyFunctionNameException(getLocator());
        }
    }

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     * @param value the literal value
     * 
     * @throws ExBibException in case of an error
     * @throws ExBibIllegalValueException in case that the given value is
     *         <code>null</code> or the empty string
     */
    public TLiteral(String value, Locator locator) throws ExBibException {

        super(value, locator);

        if (value == null || value.equals("")) {
            throw new ExBibEmptyFunctionNameException(locator);
        }
    }

    /**
     * The definition of the literal as function is sought and the respective
     * code is executed. If no function definition is found then an Exception is
     * thrown.
     * 
     * @param processor the processor context
     * @param entry the current entry or <code>null</code>
     * @param locator the locator
     * 
     * @throws ExBibException in case that something goes wrong
     * @throws ExBibFunctionUndefinedException in case that the function named
     *         in this object is not defined
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        String name = getValue();
        processor.step(name);

        Code code = processor.getFunction(name);

        if (code == null) {
            throw new ExBibFunctionUndefinedException(name, locator);
        }

        code.execute(processor, entry, locator);
    }

    /**
     * The expansion of a Literal is the value of the macro stored under this
     * name. If the macro is not defined then it expands to the empty string.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.Token#expand(
     *      org.extex.exbib.core.bst.Processor)
     */
    @Override
    public String expand(Processor processor) {

        String macro = processor.getMacro(getValue());

        return (macro != null ? macro : "");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.AbstractToken#visit(
     *      org.extex.exbib.core.bst.node.TokenVisitor)
     */
    @Override
    public void visit(TokenVisitor visitor) throws IOException {

        visitor.visitLiteral(this);
    }

}
