/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.doctools.exceptions;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * This exception signals a syntax error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SyntaxException extends Exception {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field <tt>resource</tt> contains the name of the resource.
     */
    private String resource;

    /**
     * Creates a new object.
     * 
     * @param resource the resource
     * @param message the message
     */
    public SyntaxException(String resource, String message) {

        super(message);
        this.resource = resource;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());

        return MessageFormat.format(bundle.getString("message"), resource,
            super.getMessage());
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {

        return super.getMessage();
    }

    /**
     * Getter for the resource.
     * 
     * @return the resource
     */
    protected String getResource() {

        return resource;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {

        return getLocalizedMessage();
    }

}
