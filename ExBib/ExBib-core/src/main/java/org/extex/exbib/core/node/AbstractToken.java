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

package org.extex.exbib.core.node;

import java.io.IOException;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is the base class for all {@link Token Token}s. It is declared abstract
 * since it is not meant to be used as is but as a class from which other
 * classes are derived. It provides the basic functionality which has to be
 * completed in the derived classes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public abstract class AbstractToken implements Token {

    /**
     * The field <tt>locator</tt> contains the locator for the
     * {@link Token Token}.
     */
    private Locator locator;

    /**
     * The field <tt>stringCache</tt> contains the cached string representation
     * of the object.
     */
    private String stringCache = null;

    /**
     * The field <tt>value</tt> contains the String value of the
     * {@link Token Token}.
     */
    private String value;

    /**
     * The field <tt>isNull</tt> contains the indicator that the value has
     * been set to <code>null</code> initially.
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
     * This method can be overloaded in a derived class. The default behavior is
     * that nothing is done.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * The execute method is specific to any sub-type. Thus it is declared as
     * abstract which forces that it is defined in any derived class.
     * 
     * @see org.extex.exbib.core.bst.Code#execute(
     *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    public abstract void execute(Processor processor, Entry entry, Locator l)
            throws ExBibException;

    /**
     * {@inheritDoc}
     * 
     * The expansion of an AbstractToken is the value itself.
     * 
     * @see org.extex.exbib.core.node.Token#expand(
     *      org.extex.exbib.core.Processor)
     */
    public String expand(Processor processor) {

        return value;
    }

    /**
     * Getter for the {@link org.extex.exbib.core.io.Locator Locator}. The
     * locator is a means to get information about where the token came from
     * originally. If no such information is available then <code>null</code>
     * is returned.
     * 
     * @return the locator or <code>null</code>
     */
    public Locator getLocator() {

        return locator;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Code#getName()
     */
    public String getName() {

        return "";
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
     * @return <code>true</code> iff the value has been set to
     *         <code>null</code>
     */
    public boolean isNull() {

        return isNull;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Code#setName(java.lang.String)
     */
    public void setName(String name) {

        //
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

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.node.Token#visit(
     *      org.extex.exbib.core.node.TokenVisitor)
     */
    public abstract void visit(TokenVisitor visitor) throws IOException;

}
