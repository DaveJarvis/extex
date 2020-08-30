/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.resource;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationClassNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationInstantiationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationNoSuchMethodException;
import org.extex.framework.logger.LogEnabled;

/**
 * This class provides a factory for ResourceFinders.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ResourceFinderFactory {

    /**
     * The constant {@code CLASS_ATTRIBUTE} contains the name of the
     * attribute containing the class name.
     */
    private static final String CLASS_ATTRIBUTE = "class";


    public ResourceFinderFactory() {

    }

    /**
     * Get an instance of a resource finder.
     * 
     * @param config the configuration to use
     * @param logger the logger to pass to the Resource finder elements
     * @param properties the properties to pass to the resource finder elements
     * @param provider the interaction provider
     * 
     * @return the resource finder
     * 
     * @throws ConfigurationException in case of an error in the configuration
     *         or during instantiation
     */
    public ResourceFinder createResourceFinder(Configuration config,
            Logger logger, Properties properties, InteractionIndicator provider)
            throws ConfigurationException {

        ResourceFinderList list = new ResourceFinderList();

        Iterator<Configuration> iterator = config.iterator("Finder");
        while (iterator.hasNext()) {
            Configuration cfg = iterator.next();
            String classname = cfg.getAttribute(CLASS_ATTRIBUTE);
            if (classname == null) {
                throw new ConfigurationMissingAttributeException(
                    CLASS_ATTRIBUTE, cfg);
            }

            ResourceFinder finder;

            try {
                finder =
                        (ResourceFinder) (Class.forName(classname)
                            .getConstructor(new Class[]{Configuration.class})
                            .newInstance(new Object[]{cfg}));
            } catch (IllegalArgumentException e) {
                throw new ConfigurationInstantiationException(e);
            } catch (SecurityException e) {
                throw new ConfigurationInstantiationException(e);
            } catch (InstantiationException e) {
                throw new ConfigurationInstantiationException(e);
            } catch (IllegalAccessException e) {
                throw new ConfigurationInstantiationException(e);
            } catch (InvocationTargetException e) {
                throw new ConfigurationInstantiationException(e);
            } catch (NoSuchMethodException e) {
                throw new ConfigurationNoSuchMethodException(classname + "("
                        + Configuration.class.getName() + ")", config);
            } catch (ClassNotFoundException e) {
                throw new ConfigurationClassNotFoundException(classname, config);
            }

            if (finder instanceof LogEnabled) {
                ((LogEnabled) finder).enableLogging(logger);
            }
            if (finder instanceof PropertyAware) {
                ((PropertyAware) finder).setProperties(properties);
            }
            if (finder instanceof InteractionAware) {
                ((InteractionAware) finder).setInteractionProvider(provider);
            }
            list.add(finder);
        }
        return list;
    }

}
