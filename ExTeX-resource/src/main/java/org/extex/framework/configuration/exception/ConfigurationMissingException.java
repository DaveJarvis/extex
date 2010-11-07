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
 * This Exception is thrown when a configuration is requested with the path
 * <code>null</code> or the empty string. Alternatively it can be used when some
 * other kind of configuration information is missing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationMissingException extends ConfigurationException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    private static final long serialVersionUID = 2010L;

    /**
     * The field <tt>item</tt> contains the name of the missing item.
     */
    private String item;

    /**
     * Create a new object.
     * 
     * @param message the message string
     */
    public ConfigurationMissingException(String message) {

        super(message, (String) null);
        this.item = null;
    }

    /**
     * Create a new object.
     * 
     * @param item the message string
     * @param location the location of the missing configuration item
     */
    public ConfigurationMissingException(String item, String location) {

        super(null, location);
        this.item = item;
    }

    /**
     * Getter for the text prefix of this ConfigException. The text is taken
     * from the resource bundle <tt>ConfigurationEception</tt> under the key
     * <tt>ConfigurationMissingException.Text</tt> or
     * <tt>ConfigurationMissingException.Text0</tt>. The latter is used if the
     * item is <code>null<code>.
     * 
     * @return the text
     */
    @Override
    protected String getText() {

        return getLocalizer().format(
            item == null
                    ? "ConfigurationMissingException.Text0"
                    : "ConfigurationMissingException.Text", item);
    }
}
