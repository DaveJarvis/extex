/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.framework;

import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;

/**
 * This is an abstract base class for all classes carrying a configuration.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AbstractConfigurable implements Configurable {

    /**
     * The constant {@code DEFAULT_ATTRIBUTE} contains the name of the
     * attribute used to get the default configuration.
     */
    protected static final String DEFAULT_ATTRIBUTE = "default";

    /**
     * Configure an instance if this instance supports configuration. If
     * configuration is not supported then nothing is done.
     * 
     * @param instance the instance to configure
     * @param configuration the configuration to use. If this parameter is
     *        {@code null} then it is not passed to the instance.
     * 
     * @throws ConfigurationException in case of an error
     */
    public static void configure(Object instance, Configuration configuration)
            throws ConfigurationException {

        if (configuration != null && instance instanceof Configurable) {
            ((Configurable) instance).configure(configuration);
        }
    }

    /**
     * The field {@code configuration} contains the configuration of the
     * factory which is also passed to the new instances.
     */
    private Configuration configuration;

    /**
     * Configure an object according to a given Configuration.
     * 
     * @param conf the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    @Override
    public void configure(Configuration conf) throws ConfigurationException {

        this.configuration = conf;
    }

    /**
     * Getter for configuration.
     * 
     * @return the configuration.
     */
    public final Configuration getConfiguration() {

        return this.configuration;
    }

    /**
     * Select a sub-configuration with a given name. If this does not exist then
     * the attribute {@code default} is used to find an alternative.
     * 
     * @param type the tag name for the sub-configuration to find
     * 
     * @return the desired sub-configuration
     * 
     * @throws ConfigurationException in case of an error
     */
    protected Configuration selectConfiguration(String type)
            throws ConfigurationException {

        if (this.configuration == null) {
            throw new ConfigurationMissingException(this.getClass().getName());
        }

        String base = this.configuration.getAttribute("base");
        if (base != null) {
            String t = type;
            if (t == null || t.equals("")) {
                t = this.configuration.getAttribute(DEFAULT_ATTRIBUTE);
                if (t == null) {
                    throw new ConfigurationMissingAttributeException(
                        DEFAULT_ATTRIBUTE, this.configuration);
                }
            }

            return ConfigurationFactory.newInstance(base + t + ".xml");
        }

        Configuration config = this.configuration.findConfiguration(type);
        if (config == null) {
            String fallback =
                    this.configuration.getAttribute(DEFAULT_ATTRIBUTE);
            if (fallback == null) {
                throw new ConfigurationMissingAttributeException(
                    DEFAULT_ATTRIBUTE, configuration);
            }
            config = configuration.findConfiguration(fallback);
            if (config == null) {
                throw new ConfigurationMissingAttributeException(fallback,
                    this.configuration);
            }
        }
        return config;
    }

}
