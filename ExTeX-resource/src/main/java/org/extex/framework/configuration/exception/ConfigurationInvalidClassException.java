/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
 * This exception is thrown when a dynamically loaded class does not implement
 * an expected interface.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationInvalidClassException extends ConfigurationException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field <tt>className</tt> contains the name of the class which
     * could not be found.
     */
    private String className;

    /**
     * The field <tt>interfaceName</tt> contains the name of the expected
     * interface.
     */
    private String interfaceName;

    /**
     * Creates a new object.
     *
     * @param className the name of the class
     * @param interfaceName the name of the interface expected to be
     *  implemented by the class
     * @param config the configuration in which the problem occurred or
     * <code>null</code>
     */
    public ConfigurationInvalidClassException(String className,
            String interfaceName, Configuration config) {

        super(null, config.toString());
        this.className = className;
        this.interfaceName = interfaceName;
    }

    /**
     * Getter for the text prefix of this ConfigException.
     * The text is taken from the resource bundle <tt>ConfigurationEception</tt>
     * under the key <tt>ConfigurationInvalidClassException.Text</tt>. The
     * argument {0} is replaced by the name of the missing class as passed
     * to the constructor, or the message of the cause if no class name is
     * present, or the empty string as final fallback.
     *
     * @return the text
     */
    @Override
    protected String getText() {

        return getLocalizer().format(
            "ConfigurationInvalidClassException.Text",
            (className != null //
                    ? className //
                    : getCause() != null
                            ? getCause().getLocalizedMessage()
                            : ""), interfaceName);
    }

}