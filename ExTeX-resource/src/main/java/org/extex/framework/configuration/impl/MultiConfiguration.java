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
 * Container for several
 * {@link org.extex.framework.configuration.Configuration Configuration}
 * objects. They can be treated as if they where contained in one configuration.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MultiConfiguration implements Configuration {

    /**
     * The field <tt>configurations</tt> contains the internal array of
     * configurations.
     */
    private Configuration[] configurations;

    /**
     * The field <tt>loader</tt> contains the optional loader.
     */
    private ConfigurationLoader loader = null;

    /**
     * Creates a new object.
     * 
     * @param parts the configs to treat jointly
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public MultiConfiguration(Configuration[] parts)
            throws ConfigurationException {

        super();
        this.configurations = parts.clone();
    }

    /**
     * Extract a sub-configuration with a given name.
     * 
     * @param key the tag name of the sub-configuration
     * 
     * @return the sub-configuration or <code>null</code> if none is found
     * 
     * @throws ConfigurationInvalidResourceException in case that the given
     *         resource name is <code>null</code> or empty
     * @throws ConfigurationNotFoundException in case that the named path does
     *         not lead to a resource
     * @throws ConfigurationSyntaxException in case that the resource contains
     *         syntax errors
     * @throws ConfigurationIOException in case of an IO exception while reading
     *         the resource
     * 
     * @see org.extex.framework.configuration.Configuration#findConfiguration(
     *      java.lang.String)
     */
    public Configuration findConfiguration(String key)
            throws ConfigurationInvalidResourceException,
                ConfigurationNotFoundException,
                ConfigurationSyntaxException,
                ConfigurationIOException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (int i = 0; i < configurations.length; i++) {
            Configuration cfg = configurations[i].findConfiguration(key);
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
                    return new MultiConfiguration((Configuration[]) v.toArray());
                } catch (ConfigurationException e) {
                    throw new RuntimeException("unexpected exception", e);
                }
        }
    }

    /**
     * @see org.extex.framework.configuration.Configuration#findConfiguration(
     *      java.lang.String, java.lang.String)
     */
    public Configuration findConfiguration(String key, String attribute)
            throws ConfigurationException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (int i = 0; i < configurations.length; i++) {
            Configuration cfg =
                    configurations[i].findConfiguration(key, attribute);
            if (cfg != null) {
                v.add(cfg);
            }
        }

        if (v.size() == 0) {
            return null;
        }

        if (v.size() == 1) {
            return v.get(0);
        }

        return new MultiConfiguration((Configuration[]) v.toArray());
    }

    /**
     * Getter for an attribute with a given name. The first attribute in a
     * sub-configuration is returned.
     * 
     * @param name the tag name of the attribute
     * 
     * @return the value of the attribute or <code>null</code> if such an
     *         attribute is not present
     * 
     * @see org.extex.framework.configuration.Configuration#getAttribute(
     *      java.lang.String)
     */
    public String getAttribute(String name) {

        for (int i = 0; i < configurations.length; i++) {
            String att = configurations[i].getAttribute(name);
            if (att != null) {
                return att;
            }
        }
        return null;
    }

    /**
     * @see org.extex.framework.configuration.Configuration#getConfiguration(
     *      java.lang.String)
     */
    public Configuration getConfiguration(String key)
            throws ConfigurationException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (int i = 0; i < configurations.length; i++) {
            Configuration cfg = configurations[i].findConfiguration(key);
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
                return new MultiConfiguration((Configuration[]) v.toArray());
        }
    }

    /**
     * @see org.extex.framework.configuration.Configuration#getConfiguration(
     *      java.lang.String, java.lang.String)
     */
    public Configuration getConfiguration(String key, String attribute)
            throws ConfigurationException {

        List<Configuration> v = new ArrayList<Configuration>();

        for (int i = 0; i < configurations.length; i++) {
            Configuration cfg =
                    configurations[i].findConfiguration(key, attribute);
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
                return new MultiConfiguration((Configuration[]) v.toArray());
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

        for (int i = 0; i < configurations.length; i++) {
            String val = configurations[i].getValue();
            if (val != null) {
                sb.append(val);
            }
        }

        return sb.toString();
    }

    /**
     * @see org.extex.framework.configuration.Configuration#getValue(java.lang.String)
     */
    public String getValue(String key) throws ConfigurationException {

        for (int i = 0; i < configurations.length; i++) {
            String val = configurations[i].getValue(key);

            if (val != null) {
                return val;
            }
        }

        return null;
    }

    /**
     * @see org.extex.framework.configuration.Configuration#getValueAsInteger(
     *      java.lang.String, int)
     */
    public int getValueAsInteger(String key, int defaultValue)
            throws ConfigurationException {

        for (int i = 0; i < configurations.length; i++) {
            int val = configurations[i].getValueAsInteger(key, defaultValue);

            if (val != defaultValue) {
                return val;
            }
        }

        return defaultValue;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getValues(
     *      java.util.List, java.lang.String)
     */
    public void getValues(List<String> list, String key) {

        for (Configuration cfg : configurations) {
            cfg.getValues(list, key);
        }
    }

    /**
     * @see org.extex.framework.configuration.Configuration#getValues(java.lang.String)
     */
    public List<String> getValues(String key) {

        List<String> result = new ArrayList<String>();
        getValues(result, key);
        return result;
    }

    /**
     * @see org.extex.framework.configuration.Configuration#iterator()
     */
    public Iterator<Configuration> iterator() throws ConfigurationException {

        return new MultiConfigurationIterator(configurations, null);
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

        return new MultiConfigurationIterator(configurations, key);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(
     *      org.extex.framework.configuration.ConfigurationLoader)
     */
    public void setConfigurationLoader(ConfigurationLoader loader) {

        this.loader = loader;
    }

}