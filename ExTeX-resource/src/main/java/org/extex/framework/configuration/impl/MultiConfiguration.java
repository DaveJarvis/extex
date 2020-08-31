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

package org.extex.framework.configuration.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;

/**
 * Container for several {@link org.extex.framework.configuration.Configuration
 * Configuration} objects. They can be treated as if they where contained in one
 * configuration.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MultiConfiguration implements Configuration {

    /**
     * The field {@code configurations} contains the internal array of
     * configurations.
     */
    private final List<Configuration> configurations;

    /**
     * The field {@code loader} contains the optional loader.
     */
    // private ConfigurationLoader loader = null;
    /**
     * Creates a new object.
     * 
     * @param parts the configs to treat jointly
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public MultiConfiguration(List<Configuration> parts)
            throws ConfigurationException {

        this.configurations = new ArrayList<Configuration>(parts);
    }

    /**
     * Extract a sub-configuration with a given name.
     * 
     * @param key the tag name of the sub-configuration
     * 
     * @return the sub-configuration or {@code null} if none is found
     * 
     * @throws ConfigurationInvalidResourceException in case that the given
     *         resource name is {@code null} or empty
     * @throws ConfigurationNotFoundException in case that the named path does
     *         not lead to a resource
     * @throws ConfigurationSyntaxException in case that the resource contains
     *         syntax errors
     * @throws ConfigurationIOException in case of an IO exception while reading
     *         the resource
     * 
     * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String)
     */
    public Configuration findConfiguration(String key)
            throws ConfigurationInvalidResourceException,
                ConfigurationNotFoundException,
                ConfigurationSyntaxException,
                ConfigurationIOException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (Configuration c : configurations) {
            Configuration cfg = c.findConfiguration(key);
            if (cfg != null) {
                v.add(cfg);
            }
        }

        switch (v.size()) {
            case 0:
                return null;
            case 1:
                return v.get(0);
            default:
                try {
                    return new MultiConfiguration(v);
                } catch (ConfigurationException e) {
                    throw new RuntimeException("unexpected exception", e);
                }
        }
    }

    /**
*      java.lang.String)
     */
    public Configuration findConfiguration(String key, String attribute)
            throws ConfigurationException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (Configuration c : configurations) {
            Configuration cfg = c.findConfiguration(key, attribute);
            if (cfg != null) {
                v.add(cfg);
            }
        }

        switch (v.size()) {
            case 0:
                return null;
            case 1:
                return v.get(0);
            default:
                return new MultiConfiguration(v);
        }
    }

    /**
     * Getter for an attribute with a given name. The first attribute in a
     * sub-configuration is returned.
     * 
     * @param name the tag name of the attribute
     * 
     * @return the value of the attribute or {@code null} if such an
     *         attribute is not present
     * 
     * @see org.extex.framework.configuration.Configuration#getAttribute(java.lang.String)
     */
    public String getAttribute(String name) {

        for (Configuration c : configurations) {
            String att = c.getAttribute(name);
            if (att != null) {
                return att;
            }
        }
        return null;
    }

public Configuration getConfiguration(String key)
            throws ConfigurationException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (Configuration c : configurations) {
            Configuration cfg = c.findConfiguration(key);
            if (cfg != null) {
                v.add(cfg);
            }
        }

        switch (v.size()) {
            case 0:
                return null;
            case 1:
                return v.get(0);
            default:
                return new MultiConfiguration(v);
        }
    }

    /**
*      java.lang.String)
     */
    public Configuration getConfiguration(String key, String attribute)
            throws ConfigurationException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (Configuration c : configurations) {
            Configuration cfg = c.findConfiguration(key, attribute);
            if (cfg != null) {
                v.add(cfg);
            }
        }

        switch (v.size()) {
            case 0:
                throw new ConfigurationNotFoundException(null, key + "["
                        + attribute + "]");
            case 1:
                return v.get(0);
            default:
                return new MultiConfiguration(v);
        }
    }

    /**
     * Getter for the textual value of this configuration.
     * 
     * @return the text stored directly in this configuration
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configuration#getValue()
     */
    public String getValue() throws ConfigurationException {

        StringBuilder sb = new StringBuilder();

        for (Configuration c : configurations) {
            String val = c.getValue();
            if (val != null) {
                sb.append(val);
            }
        }

        return sb.toString();
    }

public String getValue(String key) throws ConfigurationException {

        for (Configuration c : configurations) {
            String val = c.getValue(key);

            if (val != null) {
                return val;
            }
        }

        return null;
    }

    /**
*      int)
     */
    public int getValueAsInteger(String key, int defaultValue)
            throws ConfigurationException {

        for (Configuration c : configurations) {
            int val = c.getValueAsInteger(key, defaultValue);

            if (val != defaultValue) {
                return val;
            }
        }

        return defaultValue;
    }

    /**
*      java.lang.String)
     */
    public void getValues(List<String> list, String key) {

        for (Configuration cfg : configurations) {
            cfg.getValues(list, key);
        }
    }

public List<String> getValues(String key) {

        List<String> result = new ArrayList<String>();
        getValues(result, key);
        return result;
    }

public Iterator<Configuration> iterator() throws ConfigurationException {

        return iterator(null);
    }

    /**
     * Retrieve an iterator over all items of a sub-configuration.
     * 
     * @param key the name of the sub-configuration
     * 
     * @return the iterator
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configuration#iterator(java.lang.String)
     */
    public Iterator<Configuration> iterator(String key)
            throws ConfigurationException {

        Configuration[] cfg = new Configuration[configurations.size()];
        int i = 0;
        for (Configuration c : configurations) {
            cfg[i++] = c;
        }
        return new MultiConfigurationIterator(cfg, key);
    }

public void setConfigurationLoader(ConfigurationLoader loader) {

        // this.loader = loader;
    }

}
