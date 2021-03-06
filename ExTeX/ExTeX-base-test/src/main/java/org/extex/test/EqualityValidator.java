/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Validator which compares for equality against a fixed string. The reference
 * value is passed in via the constructor.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class EqualityValidator implements Validator {

    /**
     * The field {@code expected} contains the expected value.
     */
    private final String expected;

    /**
     * The field {@code comment} contains the comment for the error message.
     */
    private final String comment;

    /**
     * Creates a new object.
     * 
     * @param comment the error message comment
     * @param expected the expected value or {@code null} to accept
     *        anything
     */
    public EqualityValidator(String comment, String expected) {

        this.expected = expected;
        this.comment = comment;
    }

    /**
     * Returns {@code true} if the given string is not {@code null}
     * and if it is equal to the given string. Otherwise an JUnit exception is
     * raised.
     * 
*/
    @Override
    public boolean validate(String s) {

        assertNotNull(comment, s);
        assertEquals(comment, expected, s);
        return true;
    }

}
