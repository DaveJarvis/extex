/*
 * Copyright (C) 2004-2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.exception;

import org.extex.framework.configuration.Configuration;

/**
 * This exception is thrown when a dynamically loaded class does not provide the
 * expected method.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationNoSuchMethodException extends ConfigurationException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    private static final long serialVersionUID = 2010L;

    /**
     * The field <tt>methodName</tt> contains the name of the class which could
     * not be found.
     */
    private String methodName = null;

    /**
     * Creates a new object.
     * 
     * @param methodName the name of the method which could not be found
     * @param config the configuration in which the exception occurred.
     */
    public ConfigurationNoSuchMethodException(String methodName,
            Configuration config) {

        super(null, config.toString());
        this.methodName = methodName;
    }

    /**
     * Creates a new object.
     * 
     * @param cause the next Throwable in the list
     */
    public ConfigurationNoSuchMethodException(Throwable cause) {

        super(null, cause);
    }

    /**
     * Getter for the text prefix of this ConfigurationException. The text is
     * taken from the resource bundle <tt>ConfigurationEception</tt> under the
     * key <tt>ConfigurationNoSuchMethodException.Text</tt>. The argument {0} is
     * replaced by the name of the missing method as passed to the constructor.
     * 
     * @return the text
     */
    @Override
    protected String getText() {

        return getLocalizer().format(
            "ConfigurationNoSuchMethodException.Text",
            (methodName != null
                    ? methodName
                    : getCause() != null
                            ? getCause().getLocalizedMessage()
                            : ""));
    }

}
