/*
 * Copyright (C) 2003-2008 Gerd Neugebauer
 * This file is part of ExBib a BibTeX compatible database.
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.node.AbstractToken;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.TokenVisitor;
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
public class TokenList extends AbstractToken implements Token {

    /** The internal representation upon which the list is based. */
    private List<Token> theValue = null;

    /**
     * Creates a new object.
     * 
     * @param value a {@link List List} of {@link Token Token}s
     */
    public TokenList(List<Token> value) {

        super(null, null);
        theValue = value;
    }

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     */
    public TokenList(Locator locator) {

        super(null, locator);
        theValue = new ArrayList<Token>();
    }

    /**
     * Creates a new object.
     * 
     * @param locator the locator
     * @param value a {@link List List} of {@link Token Token}s
     */
    public TokenList(Locator locator, List<Token> value) {

        super(null, locator);
        theValue = new ArrayList<Token>(value);
    }

    /**
     * Add a token to the end of the token list.
     * 
     * @param t the token to add
     */
    public void add(Token t) {

        theValue.add(t);
    }

    /**
     * @see org.extex.exbib.core.bst.Code#execute(
     *      org.extex.exbib.core.bst.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        for (int i = 0; i < theValue.size(); i++) {
            ((theValue.get(i))).execute(processor, entry, locator);
        }
    }

    /**
     * @see org.extex.exbib.core.bst.node.Token#expand(
     *      org.extex.exbib.core.bst.Processor)
     */
    @Override
    public String expand(Processor processor) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < theValue.size(); i++) {
            sb.append(((theValue.get(i))).expand(processor));
        }

        return sb.toString();
    }

    /**
     * @see org.extex.exbib.core.bst.node.Token#getValue()
     */
    @Override
    public String getValue() {

        return toString();
    }

    /**
     * Getter for the iterator.
     * 
     * @return the iterator
     */
    public Iterator<Token> iterator() {

        return theValue.iterator();
    }

    /**
     * Compute a printable representation of this object.
     * 
     * @return the printable representation
     */
    @Override
    protected String setString() {

        String sep = " ";
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        Iterator<Token> iterator = theValue.iterator();

        while (iterator.hasNext()) {
            if (first) {
                first = false;
            } else {
                sb.append(sep);
            }

            Token t = iterator.next();
            sb.append(t.toString());
        }

        return sb.toString();
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
        Iterator<Token> iterator = theValue.iterator();

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
     * @see org.extex.exbib.core.bst.node.Token#visit(
     *      org.extex.exbib.core.bst.node.TokenVisitor)
     */
    @Override
    public void visit(TokenVisitor visitor) throws IOException {

        visitor.visitTokenList(this);
    }

}
