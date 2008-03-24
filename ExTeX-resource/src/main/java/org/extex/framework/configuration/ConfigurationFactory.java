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

package org.extex.framework.configuration;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.extex.framework.configuration.exception.ConfigurationClassNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationInstantiationException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.impl.XmlConfiguration;

/**
 * This is the factory for configurations.
 * <p>
 * The class to be used for the configuration can be set with the
 * <tt>System.property</tt> named <tt>Util.Configuration.class</tt>. If
 * this property is not set then the fallback class
 * {@link org.extex.framework.configuration.impl.XmlConfiguration XmlConfiguration}
 * is used instead.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationFactory {

    /**
     * The field <tt>classloader</tt> contains the class loader.
     */
    private static ClassLoader classloader =
            new ConfigurationFactory().getClass().getClassLoader();

    /**
     * The field <tt>ext</tt> contains extensions to use when searching for
     * configuration files.
     */
    private static final String[] XML_EXTENSIONS = new String[]{".xml", ""};

    /**
     * The field <tt>path</tt> contains the path to use when searching for
     * configuration files.
     */
    private static final String[] PATHS = {"config/", ""};

    /**
     * Delivers a new
     * {@link org.extex.framework.configuration.Configuration Configuration}
     * object which is initialized from a named source. This source is usually a
     * file name but can be anything else, like a URL or a reference to a
     * database &ndash; depending on the underlying implementation.
     * <p>
     * The implementation can be specified in the system property
     * <tt>Util.Configuration.class</tt>. The content is expected to be a
     * fully qualified class name. This class has to implement the interface
     * {@link Configuration Configuration}.
     * </p>
     * <p>
     * The default implementation is
     * {@link org.extex.framework.configuration.impl.XmlConfiguration XmlConfiguration}
     * which uses an XML file located on the classpath.
     * </p>
     * 
     * @param source the source of the configuration
     * 
     * @return a new Configuration object
     * 
     * @throws ConfigurationException in case of an error. Especially
     *         <ul>
     *         <li>ConfigurationInvalidNameException in case that the source is
     *         <code>null</code></li>
     *         <li>ConfigurationInstantiationException in case of some kind of
     *         error during instantiation</li>
     *         <li>ConfigurationClassNotFoundException in case that the class
     *         could not be found</li>
     *         <li>ConfigurationException in case that the creation of the
     *         Configuration fails</li>
     *         </ul>
     */
    public static Configuration newInstance(String source)
            throws ConfigurationException {

        if (source == null || "".equals(source)) {
            throw new ConfigurationInvalidResourceException();
        }

        String classname = System.getProperty("Util.Configuration.class");

        if (classname == null) {

            for (String p : PATHS) {
                for (String ext : XML_EXTENSIONS) {
                    String fullName = p + source + ext;
                    InputStream stream =
                            classloader.getResourceAsStream(fullName);
                    if (stream != null) {
                        return new XmlConfiguration(stream, fullName);
                    }
                }
            }

            throw new ConfigurationNotFoundException(source, null);
        }

        try {
            return (Configuration) (Class.forName(classname).getConstructor(
                new Class[]{String.class}).newInstance(new Object[]{source}));
        } catch (IllegalArgumentException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (SecurityException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InvocationTargetException e) {
            Throwable c = e.getCause();
            if (c != null && c instanceof ConfigurationException) {
                throw (ConfigurationException) c;
            }
            throw new ConfigurationInstantiationException(e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationClassNotFoundException(classname);
        }
    }

    /**
     * Creates a new object. Not used for this utility class.
     */
    private ConfigurationFactory() {

        // unused
    }

}
