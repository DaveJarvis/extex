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

package org.extex.exindex.core.exception;

import java.io.EOFException;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception signals that a character is missing and another character has
 * been encountered instead.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MissingException extends EOFException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>c</tt> contains the character encountered.
     */
    private char c;

    /**
     * The field <tt>ec</tt> contains the expected character.
     */
    private char ec;

    /**
     * Creates a new object.
     * 
     * @param line
     * @param resource
     * @param c the character found
     * @param ec the expected character
     */
    public MissingException(String resource, int line, char c, char ec) {

        super();
        this.c = c;
        this.ec = ec;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        return LocalizerFactory.getLocalizer(getClass()).format("Message",
            Character.toString(c), Character.toString(ec));
    }

}
