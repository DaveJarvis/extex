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
import java.util.List;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.bst.node.AbstractToken;
import org.extex.exbib.core.bst.node.Name;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a <tt>String</tt> token, i.e. a value enclosed in
 * double quotes.
 * 
 * <p>
 * You can try to store the <code>null</code> value in a TString. This is used
 * to indicate a missing field. The reported value is the empty string.
 * Nevertheless the method {@link #isNull() isNull} can be used to distinguish
 * between those two cases.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TString extends AbstractToken implements Token {

    /**
     * The field <tt>names</tt> contains the list of names which might act as
     * a cache.
     */
    private List<Name> names = null;

    // TODO test whether caching of names is useful

    /**
     * Creates a new object.
     * 
     * @param value the value
     * @param locator the locator
     */
    public TString(String value, Locator locator) {

        super(value, locator);
    }

    /**
     * Comparison is reduced to the comparison of the values.
     * 
     * @param that other string to compare to
     * 
     * @return <code>true</code> iff the values are equal
     */
    @Override
    public boolean equals(Object that) {

        return that instanceof TString
                && getValue().equals(((TString) that).getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.AbstractToken#execute(
     *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        processor.step(toString());
        processor.push(this);
    }

    /**
     * Getter for the name list associated with this object. The name list can
     * be used to store the result of parsing the value to names. Thus it works
     * as cache.
     * 
     * @return the names or <code>null</code>
     * 
     * @throws ExBibImpossibleException in case of an error
     * @throws ExBibNoNameException in case of an error
     * @throws ExBibSyntaxException in case of an error
     */
    public List<Name> getNames()
            throws ExBibSyntaxException,
                ExBibNoNameException,
                ExBibImpossibleException {

        if (names == null) {
            names = Name.parse(getValue(), getLocator());
        }

        return names;
    }

    /**
     * Compute the string representation for this object. This value is used by
     * the method {@link #toString() toString()}.
     * 
     * @return the string representation
     */
    @Override
    protected String getString() {

        return "\"" + getValue() + "\"";
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return getValue().hashCode();
    }

    /**
     * Setter for the names property.
     * 
     * @param list the new value
     * 
     * @see #getNames()
     */
    public void setNames(List<Name> list) {

        names = list;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.AbstractToken#visit(
     *      org.extex.exbib.core.bst.node.TokenVisitor)
     */
    @Override
    public void visit(TokenVisitor visitor) throws IOException {

        visitor.visitString(this);
    }

}
