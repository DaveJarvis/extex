/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.test;

/**
 * Validator which compares for equality against a fixed string. The reference
 * value is passed in via the constructor.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EqualityValidator implements Validator {

    /**
     * The field <tt>expected</tt> contains the expected value.
     */
    private String expected;

    /**
     * Creates a new object.
     * 
     * @param expected the expected value or <code>null</code> to accept
     *        anything
     */
    public EqualityValidator(String expected) {

        this.expected = expected;
    }

    /**
     * Returns <code>true</code> if the expected result is <code>null</code>
     * or it is equal to the given string.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.test.Validator#validate(java.lang.String)
     */
    public boolean validate(String s) {

        return expected == null || expected.equals(s);
    }

}
