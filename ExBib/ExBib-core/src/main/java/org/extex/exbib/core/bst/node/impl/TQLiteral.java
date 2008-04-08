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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.node.AbstractToken;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class encapsulates a {@link TLiteral TLiteral} and protects it from
 * execution.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TQLiteral extends AbstractToken implements Token {

    /**
     * The field <tt>theValue</tt> contains the literal value.
     */
    private TLiteral theValue = null;

    /**
     * Create a new object.
     * 
     * @param value the value
     * @param locator the locator
     * 
     * @throws ExBibException in case of an error
     */
    public TQLiteral(String value, Locator locator) throws ExBibException {

        super(value, locator);
        theValue = new TLiteral(value, locator);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.AbstractToken#execute(
     *      org.extex.exbib.core.bst.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        processor.step(toString());
        processor.push(theValue);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.AbstractToken#expand(
     *      org.extex.exbib.core.bst.Processor)
     */
    @Override
    public String expand(Processor processor) {

        return getValue();
    }

    /**
     * Compute the string representation for this object. This value is used by
     * the method {@link #toString() toString()}.
     * 
     * @return the string representation
     * 
     * @see org.extex.exbib.core.bst.node.AbstractToken#setString()
     */
    @Override
    protected String setString() {

        return "'" + getValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.AbstractToken#visit(
     *      org.extex.exbib.core.bst.node.TokenVisitor)
     */
    @Override
    public void visit(TokenVisitor visitor) throws IOException {

        visitor.visitQLiteral(this);
    }

}
