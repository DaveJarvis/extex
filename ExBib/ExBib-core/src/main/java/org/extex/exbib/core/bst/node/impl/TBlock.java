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
 * This class represents a list of values.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TBlock extends AbstractToken implements Token {

    /**
     * The field <tt>theValue</tt> contains the list of Tokens in the block.
     */
    private TokenList theValue = null;

    /**
     * Creates a new object without any elements.
     * 
     * @param locator the locator
     */
    public TBlock(Locator locator) {

        super(locator);
        theValue = new TokenList(locator);
    }

    /**
     * Adds a Token to the end of the list of Tokens in this block.
     * 
     * @param token the Token to add
     */
    public void add(Token token) {

        theValue.add(token);
    }

    /**
     * To execute an object of this class the token list of the value is simply
     * pushed to the stack of the processor for further treatment.
     * 
     * @see org.extex.exbib.core.bst.Code#execute(org.extex.exbib.core.bst.Processor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

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

        return theValue.expand(processor);
    }

    /**
     * Getter for the TokenList contained in this block.
     * 
     * @return the contents
     */
    public TokenList getTokenList() {

        return theValue;
    }

    /**
     * Compute the string representation for this object. This value is used by
     * the method {@link #toString() toString()}.
     * 
     * @return the string representation
     */
    @Override
    protected String setString() {

        return "{" + theValue.toString() + "}";
    }

    /**
     * @see org.extex.exbib.core.bst.node.Token#visit(
     *      org.extex.exbib.core.bst.node.TokenVisitor)
     */
    @Override
    public void visit(TokenVisitor visitor) throws IOException {

        visitor.visitBlock(this);
    }

}
