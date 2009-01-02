/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.token.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibNoNumberException;
import org.extex.exbib.core.bst.token.AbstractToken;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents an integer token, i.e. a number which is marked as one.
 * Note that a string token can also contain a numeric value but it can not be
 * used in places where a integer token is required. E.g. only TIntegers can be
 * added.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TInteger extends AbstractToken {

    /**
     * The integer value is stored separately to avoid repeated conversion to
     * and from String
     */
    private int value;

    /**
     * Creates a new TInteger object.
     * 
     * @param locator the locator
     * @param value the value as int
     */
    public TInteger(int value, Locator locator) {

        super(Integer.toString(value), locator);
        this.value = value;
    }

    /**
     * Create a new object.
     * 
     * @param locator the locator
     * @param value the value in a String
     * 
     * @throws ExBibIllegalValueException in case that the string does not
     *         contain a valid number
     */
    public TInteger(String value, Locator locator)
            throws ExBibIllegalValueException {

        super(value, locator);

        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ExBibNoNumberException(value, locator);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.AbstractToken#execute(BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    public void execute(BstProcessor processor, Entry entry, Locator locator) {

        processor.step(toString());
        processor.push(this);
    }

    /**
     * Getter for the integer value.
     * 
     * @return the int value
     */
    public int getInt() {

        return value;
    }

    /**
     * Compute the string representation for this object. This value is used by
     * the method {@link #toString() toString()}.
     * 
     * @return the string representation
     */
    @Override
    protected String getString() {

        return "#" + getValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.Token#visit(org.extex.exbib.core.bst.token.TokenVisitor,
     *      java.lang.Object[])
     */
    public void visit(TokenVisitor visitor, Object... args)
            throws ExBibException {

        visitor.visitInteger(this, args);
    }

}
