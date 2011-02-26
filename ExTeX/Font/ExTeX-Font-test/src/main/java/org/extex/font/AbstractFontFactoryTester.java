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

package org.extex.font;

import java.util.Properties;
import java.util.logging.Logger;

import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.PropertyAware;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * Test for the font factory.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractFontFactoryTester {

    /**
     * The resource finder.
     */
    protected static ResourceFinder finder;

    /**
     * Create the font factory.
     * 
     * @return the font factory.
     * @throws ConfigurationException from the configuration system.
     */
    protected CoreFontFactory makeFontFactory() throws ConfigurationException {

        return makeFontFactory(AbstractFontFactoryTester.class.getName());
    }

    /**
     * Create the font factory.
     * 
     * @param classname The class name for the configuration file.
     * @return the font factory.
     * @throws ConfigurationException from the configuration system.
     */
    protected CoreFontFactory makeFontFactory(String classname)
            throws ConfigurationException {

        CoreFontFactory factory = new FontFactoryImpl();

        Configuration config =
                ConfigurationFactory.newInstance(classname.replaceAll("\\.",
                    "/"));

        if (factory instanceof Configurable) {
            ((Configurable) factory)
                .configure(config.getConfiguration("Fonts"));
        }
        if (factory instanceof ResourceAware) {
            Logger logger = Logger.getLogger("Test");
            finder =
                    new ResourceFinderFactory().createResourceFinder(
                        config.getConfiguration("Resource"), logger,
                        new Properties(), null /* provider */);
            finder.enableTracing(true);

            ((ResourceAware) factory).setResourceFinder(finder);
        }
        if (factory instanceof PropertyAware) {
            ((PropertyAware) factory).setProperties(new Properties());
        }
        return factory;
    }
}
