/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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

/**
 * This Exception is thrown when a configuration could not be found.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ConfigurationNotFoundException extends ConfigurationException {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    private static final long serialVersionUID = 2010L;

    /**
     * The field {@code configName} contains the name of the missing
     * configuration.
     */
    private String configName;

    /**
     * Create a new object.
     * 
     * @param name the name of the missing configuration
     * @param source the the name of the file for which this exception occurred
     */
    public ConfigurationNotFoundException(String name, String source) {

        super(null, source);
        this.configName = name;
    }

    /**
     * Getter for configName.
     * 
     * @return the configName
     */
    public String getConfigName() {

        return configName;
    }

    /**
     * Getter for the text prefix of this
     * {@link org.extex.framework.configuration.exception.ConfigurationException
     * ConfigurationException}. The text is taken from the resource bundle
     * {@code ConfigurationEception} under the key
     * {@code ConfigurationNotFoundException.Text}. The argument {0} is
     * replaced by the name of the missing configuration as passed to the
     * constructor.
     * 
     * @return the text
     */
    @Override
    protected String getText() {

        return getLocalizer().format("ConfigurationNotFoundException.Text",
            configName == null ? "" : configName);
    }

}
