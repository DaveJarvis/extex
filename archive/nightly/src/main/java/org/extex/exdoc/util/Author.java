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

package org.extex.exdoc.util;

/**
 * This class is a container for an author with an email address.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Author {

    /**
     * The field {@code email} contains the email address.
     */
    private String email;

    /**
     * The field {@code name} contains the name of the author.
     */
    private String name;

    /**
     * Creates a new object.
     *
     * @param name the name
     * @param email the email address
     */
    public Author(String name, String email) {

        this.name = name;
        this.email = email;
    }

    /**
     * Getter for email.
     *
     * @return the email
     */
    public String getEmail() {

        return email;
    }

    /**
     * Getter for name.
     *
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Return a printable representation of the object.
     *
     * @return the author and email
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return name + " <" + email + ">";
    }

}
