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

package org.extex.exbib.core.exceptions;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This Exception is thrown when some misconfiguration has been detected in a
 * component. This means usually that a parameter is not initielized properly.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class InitializationException extends ConfigurationException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field <tt>method</tt> contains the method.
     */
    private String method;

    /**
     * The field <tt>parameter</tt> contains the parameter.
     */
    private String parameter;

    /**
     * Creates a new object.
     * 
     * @param method the method
     * @param parameter the parameter
     */
    public InitializationException(String method, String parameter) {

        super(null);
        this.method = method;
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.exception.ConfigurationException#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        return MessageFormat.format(bundle.getString("Message"), method,
            parameter);
    }

}
