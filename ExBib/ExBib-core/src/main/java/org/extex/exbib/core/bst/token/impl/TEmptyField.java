/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.exception.ExBibUndefinedFieldException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a field which does not exist. This class throws an
 * exception when expanded.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TEmptyField extends TString {

    /**
     * The field {@code entryName} contains the name of the entry.
     */
    private final String entryName;

    /**
     * The field {@code name} contains the name.
     */
    private final String name;

    /**
     * Creates a new object.
     * 
     * @param name the name of the field
     * @param entryName the name of the entry
     * @param locator the locator
     */
    public TEmptyField(String name, String entryName, Locator locator) {

        super(null, locator);
        this.name = name;
        this.entryName = entryName;
    }

    /**
     * Comparison is reduced to the comparison of the values.
     * 
     * @param that other string to compare to
     * 
     * @return {@code true} iff the values are equal
     */
    @Override
    public boolean equals(Object that) {

        return that instanceof TString
                && getValue().equals(((TString) that).getValue());
    }

@Override
    public String expand(Processor processor) throws ExBibException {

        throw new ExBibUndefinedFieldException(name, entryName, getLocator());
    }

@Override
    public int hashCode() {

        return super.hashCode();
    }

}
