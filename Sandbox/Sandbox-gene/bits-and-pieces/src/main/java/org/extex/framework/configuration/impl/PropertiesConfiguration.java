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

package org.extex.framework.configuration.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;

/*
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision:6459 $
 */
/*
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision$
 */
public class PropertiesConfiguration implements Configuration {

    /**
     * The field <tt>base</tt> contains the base string.
     */
    private String base = "";

    /*
     * The field <tt>loader</tt> contains the optional loader.
     * 
     * @uml.property name="loader"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="loader"
     * 
     * @uml.associationEnd
     */
    private ConfigurationLoader loader = null;

    /**
     * The field <tt>properties</tt> contains the encapsulated properties.
     */
    private Properties properties = new Properties();

    /**
     * The field <tt>resource</tt> contains the name of the resource.
     */
    private String resource;

    /**
     * Creates a new object.
     */
    public PropertiesConfiguration() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param stream the stream to read the content from
     * @param resource the name of the resource
     * 
     * @throws IOException in case of an I/O error
     */
    public PropertiesConfiguration(InputStream stream, String resource)
            throws IOException {

        super();
        properties.load(stream);
        this.resource = resource;
        this.base = "";
    }

    /**
     * Creates a new object. This method is for internal use only. Thus we can
     * assume that certain preconditions hold which have not to be checked.
     * 
     * @param p the properties to encapsulate
     * @param base the base for the resource
     * @param resource the name of the resource
     */
    private PropertiesConfiguration(Properties p, String base, String resource) {

        this.properties = p;
        this.base = base;
        this.resource = resource;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#findConfiguration(
     *      java.lang.String)
     */
    public Configuration findConfiguration(String key)
            throws ConfigurationInvalidResourceException,
                ConfigurationNotFoundException,
                ConfigurationSyntaxException,
                ConfigurationIOException {

        Properties p = new Properties();

        Set<Entry<Object, Object>> entrySet = properties.entrySet();
        for (Entry<Object, Object> entry : entrySet) {
            String k = entry.getKey().toString();
            if (k.startsWith(key)) {
                p.put(k, entry.getValue());
            }
        }
        return p.isEmpty() //
                ? null
                : new PropertiesConfiguration(p, base, resource);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#findConfiguration(
     *      java.lang.String, java.lang.String)
     */
    public Configuration findConfiguration(String key, String attribute)
            throws ConfigurationException {

        // TODO gene: findConfiguration unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getAttribute(
     *      java.lang.String)
     */
    public String getAttribute(String name) {

        return properties.getProperty(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getConfiguration(
     *      java.lang.String)
     */
    public Configuration getConfiguration(String key)
            throws ConfigurationException {

        Configuration cfg = findConfiguration(key);
        if (cfg == null) {
            throw new ConfigurationNotFoundException(key, null);
        }
        return cfg;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getConfiguration(
     *      java.lang.String, java.lang.String)
     */
    public Configuration getConfiguration(String key, String attribute)
            throws ConfigurationException {

        Configuration cfg = findConfiguration(key, attribute);
        if (cfg == null) {
            throw new ConfigurationNotFoundException(key, null);
        }
        return cfg;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getValue()
     */
    public String getValue() throws ConfigurationException {

        return "";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getValue(java.lang.String)
     */
    public String getValue(String key) throws ConfigurationException {

        return properties.getProperty(key);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getValueAsInteger(java.lang.String,
     *      int)
     */
    public int getValueAsInteger(String key, int defaultValue)
            throws ConfigurationException {

        // TODO gene: getValueAsInteger unimplemented
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getValues(java.util.List,
     *      java.lang.String)
     */
    public void getValues(List<String> list, String key) {

        // TODO gene: getValues unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#getValues(java.lang.String)
     */
    public List<String> getValues(String key) {

        // TODO gene: getValues unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#iterator()
     */
    public Iterator<Configuration> iterator() throws ConfigurationException {

        // TODO gene: iterator unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configuration#iterator(java.lang.String)
     */
    public Iterator<Configuration> iterator(String key)
            throws ConfigurationException {

        // TODO gene: iterator unimplemented
        return null;
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

    /**
     * Get the printable representation of this configuration. Something like an
     * XPath expression describing the configuration is produced for this
     * instance.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (resource != null) {
            sb.append("document(\"");
            sb.append(resource);
            sb.append("\")");
        }
        // toString(sb, root);
        return sb.toString();
    }

}
