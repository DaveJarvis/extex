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

package org.extex.exbib.core.bst.token.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.AbstractToken;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibMissingLiteralException;
import org.extex.exbib.core.io.Locator;

/**
 * This is a container for a sorted sequence of {@link Token Token}s. In
 * addition to the operation as container it also implements the interface
 * {@link Token Token} itself to act as one.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TokenList extends AbstractToken implements Token, Iterable<Token> {

    /**
     * The field <tt>value</tt> contains the internal representation upon
     * which the list is based.
     */
    private List<Token> value = null;

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     */
    public TokenList(Locator locator) {

        super(null, locator);
        value = new ArrayList<Token>();
    }

    /**
     * Add a token to the end of the token list.
     * 
     * @param t the token to add
     */
    public void add(Token t) {

        value.add(t);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.AbstractToken#execute(
     *      BstProcessor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        for (Token t : value) {
            t.execute(processor, entry, locator);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.AbstractToken#expand(
     *      org.extex.exbib.core.Processor)
     */
    @Override
    public String expand(Processor processor) {

        StringBuilder sb = new StringBuilder();

        for (Token t : value) {
            sb.append(t.expand(processor));
        }

        return sb.toString();
    }

    /**
     * Compute a printable representation of this object.
     * 
     * @return the printable representation
     */
    @Override
    protected String getString() {

        String sep = " ";
        StringBuffer sb = new StringBuffer();
        boolean first = true;

        for (Token t : value) {
            if (first) {
                first = false;
            } else {
                sb.append(sep);
            }
            sb.append(t.toString());
        }

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.AbstractToken#getValue()
     */
    @Override
    public String getValue() {

        StringBuilder sb = new StringBuilder();

        for (Token t : value) {
            sb.append(t.toString());
        }

        return sb.toString();
    }

    /**
     * Getter for the iterator.
     * 
     * @return the iterator
     */
    public Iterator<Token> iterator() {

        return value.iterator();
    }

    /**
     * Transform the TokenList into a {@link List List<String>} if the elements
     * are of type {@link TLiteral TLiteral} only. If some other elements are
     * found then an exception is thrown.
     * 
     * @return the StringList of the literal names
     * 
     * @throws ExBibMissingLiteralException if some other Token than a TLiteral
     *         is found
     */
    public List<String> toStringList() throws ExBibMissingLiteralException {

        List<String> list = new ArrayList<String>();
        Iterator<Token> iterator = value.iterator();

        while (iterator.hasNext()) {
            Token t = iterator.next();

            if (!(t instanceof TLiteral)) {
                throw new ExBibMissingLiteralException(t.toString(), null);
            }

            list.add(t.getValue());
        }

        return list;
    }

    /**
     * @see org.extex.exbib.core.bst.token.Token#visit(
     *      org.extex.exbib.core.bst.token.TokenVisitor)
     */
    @Override
    public void visit(TokenVisitor visitor) throws IOException {

        visitor.visitTokenList(this);
    }

}