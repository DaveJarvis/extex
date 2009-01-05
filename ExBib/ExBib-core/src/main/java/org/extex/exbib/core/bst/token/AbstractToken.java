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

package org.extex.exbib.core.bst.token;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This is the base class for all {@link Token Token}s. It is declared abstract
 * since it is not meant to be used as is but as a class from which other
 * classes are derived. It provides the basic functionality which has to be
 * completed in the derived classes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractToken extends AbstractCode implements Token {

    /**
     * The field <tt>locator</tt> contains the locator for the {@link Token
     * Token}.
     */
    private Locator locator;

    /**
     * The field <tt>stringCache</tt> contains the cached string representation
     * of the object.
     */
    private String stringCache = null;

    /**
     * The field <tt>value</tt> contains the String value of the {@link Token
     * Token}.
     */
    private String value;

    /**
     * The field <tt>isNull</tt> contains the indicator that the value has been
     * set to <code>null</code> initially.
     */
    private boolean isNull;

    /**
     * Creates a new object.
     * 
     * @param locator the {@link org.extex.exbib.core.io.Locator Locator}
     */
    public AbstractToken(Locator locator) {

        super();
        this.locator = locator;
        this.isNull = false;
        this.value = null;
    }

    /**
     * Creates a new object.
     * 
     * @param locator the {@link org.extex.exbib.core.io.Locator Locator}
     * @param value the value stored in the object
     */
    public AbstractToken(String value, Locator locator) {

        super();
        this.locator = locator;

        if (value == null) {
            this.value = "";
            this.isNull = true;
        } else {
            this.value = value;
            this.isNull = false;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * The expansion of an AbstractToken is the value itself.
     * 
     * @see org.extex.exbib.core.bst.token.Token#expand(org.extex.exbib.core.Processor)
     */
    public String expand(Processor processor) throws ExBibException {

        return value;
    }

    /**
     * Getter for the {@link org.extex.exbib.core.io.Locator Locator}. The
     * locator is a means to get information about where the token came from
     * originally. If no such information is available then <code>null</code> is
     * returned.
     * 
     * @return the locator or <code>null</code>
     */
    public Locator getLocator() {

        return locator;
    }

    /**
     * Compute the string representation for this object. This value is used by
     * the method {@link #toString() toString()}.
     * 
     * @return the string representation
     */
    protected String getString() {

        return value;
    }

    /**
     * Getter for the value as String.
     * 
     * @return the value
     */
    public String getValue() {

        return value;
    }

    /**
     * Check whether the value has been set to <code>null</code>. Usually the
     * value <code>null</code> is treated as empty string. This method is the
     * only way to distinguish these two cases.
     * 
     * @return <code>true</code> iff the value has been set to <code>null</code>
     */
    public boolean isNull() {

        return isNull;
    }

    /**
     * Compute a printable string representation for this object.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        if (stringCache == null) {
            stringCache = getString();
        }

        return stringCache;
    }

}
