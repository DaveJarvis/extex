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

package org.extex.doctools;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UnknownArgumentException extends Exception {

    /**
     * The field <tt>serialVersionUID</tt> contains the ...
     */
    private static final long serialVersionUID = 2008L;

    /**
     * Creates a new object.
     * 
     * @param message
     */
    public UnknownArgumentException(String message) {

        super(message);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());

        return MessageFormat.format(bundle.getString("message"), super
            .getMessage());
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
