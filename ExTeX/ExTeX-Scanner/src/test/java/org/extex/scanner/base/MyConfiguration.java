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

package org.extex.scanner.base;

import java.util.Iterator;
import java.util.List;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;

/**
 * This class is a dummy configuration which does nothing but deliver a single
 * attribute value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MyConfiguration implements Configuration {

    /**
     * The field <tt>attribute</tt> contains the value for the attribute
     * getter.
     */
    private String attribute = null;

    /**
     * Creates a new object.
     * 
     * @param attribute the value for the attribute
     */
    public MyConfiguration(String attribute) {

        super();
        this.attribute = attribute;
    }

    /**
     * Extract a sub-configuration with a given name.
     * 
     * @param key the tag name of the sub-configuration
     * 
     * @return the sub-configuration or <code>null</code> if none is found
     * 
     * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String)
     */
    public Configuration findConfiguration(String key) {

        return null;
    }

    /**
     * Extract a sub-configuration with a given name and a given attribute.
     * 
     * @param key the tag name of the sub-configuration
     * @param a the value of the attribute name
     * 
     * @return the sub-configuration
     * 
     * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String,
     *      java.lang.String)
     */
    public Configuration findConfiguration(String key, String a) {

        return null;
    }

    /**
     * Getter for an attribute with a given name.
     * 
     * @param name the tag name of the attribute
     * 
     * @return the value of the attribute or <code>null</code> if such an
     *         attribute is not present
     * 
     * @see org.extex.framework.configuration.Configuration#getAttribute(java.lang.String)
     */
    public String getAttribute(String name) {

        return attribute;
    }

    /**
     * Extract a sub-configuration with a given name.
     * 
     * @param key the tag name of the sub-configuration
     * 
     * @return the sub-configuration
     * 
     * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String)
     */
    public Configuration getConfiguration(String key) {

        return null;
    }

    /**
     * Extract a sub-configuration with a given name and a given attribute.
     * 
     * @param key the tag name of the sub-configuration
     * @param a the value of the attribute name
     * 
     * @return the sub-configuration
     * 
     * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String,
     *      java.lang.String)
     */
    public Configuration getConfiguration(String key, String a) {

        return null;
    }

    /**
     * Getter for the textual value of this configuration.
     * 
     * @return the text stored directly in this configuration
     * 
     * @see org.extex.framework.configuration.Configuration#getValue()
     */
    public String getValue() {

        return null;
    }

    /**
     * Retrieve a value from the configuration as <i>String</i>.
     * 
     * @param key the name of the desired value
     * 
     * @return the value of key or <code>null</code>
     * 
     * @see org.extex.framework.configuration.Configuration#getValue(java.lang.String)
     */
    public String getValue(String key) {

        return null;
    }

    /**
     * Retrieve a value from the configuration as <i>int</i>. If the value
     * could not be determined then a given default value is returned.
     * 
     * @param key the name of the desired value
     * @param defaultValue the default value
     * 
     * @return the value of key or the default value
     * 
     * @see org.extex.framework.configuration.Configuration#getValueAsInteger(java.lang.String,
     *      int)
     */
    public int getValueAsInteger(String key, int defaultValue) {

        return 0;
    }

    /**
     * Get the list of all values with the given tag name in the current
     * configuration.
     * 
     * @param key the name of the tags
     * 
     * @return the list of values
     * 
     * @see org.extex.framework.configuration.Configuration#getValues(java.lang.String)
     */
    public List<String> getValues(String key) {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getValues(
     *      java.util.List, java.lang.String)
     */
    public void getValues(List<String> list, String key) {

        // not needed
    }

    /**
     * Get an iterator for all sub-configurations.
     * 
     * @return an iterator for all sub-configurations
     * 
     * @see org.extex.framework.configuration.Configuration#iterator()
     */
    public Iterator<Configuration> iterator() {

        return null;
    }

    /**
     * Retrieve an iterator over all items of a sub-configuration.
     * 
     * @param key the name of the sub-configuration
     * 
     * @return the iterator
     * 
     * @see org.extex.framework.configuration.Configuration#iterator(java.lang.String)
     */
    public Iterator<Configuration> iterator(String key) {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(
     *      org.extex.framework.configuration.ConfigurationLoader)
     */
    public void setConfigurationLoader(ConfigurationLoader loader) {

        // unused
    }

}
