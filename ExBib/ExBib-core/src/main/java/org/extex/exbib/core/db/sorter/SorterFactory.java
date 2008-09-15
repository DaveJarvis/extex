/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.db.sorter;

import java.io.IOException;
import java.util.Properties;

import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibSorterNotFoundException;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.resource.PropertyAware;
import org.extex.resource.ResourceAware;

/**
 * This is a factory to deliver sorters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class SorterFactory extends AbstractFactory implements PropertyAware {

    /**
     * The field <tt>properties</tt> contains the properties.
     */
    private Properties properties = null;

    /**
     * Creates a new object.
     * 
     * @param configuration the configuration for this factory
     */
    public SorterFactory(Configuration configuration) {

        super(configuration);
    }

    /**
     * Provides a {@link Sorter Sorter} implementation. The new {@link Sorter
     * Sorter} is configured with the same configuration as the factory.
     * 
     * @param type the type
     * 
     * @return the new Sorter instance
     * 
     * @throws ExBibException in case of an error
     * @throws ConfigurationException in case that something goes wrong
     * @throws IOException in case of an I/O error
     */
    public Sorter newInstance(String type)
            throws ExBibException,
                ConfigurationException,
                IOException {

        if (type == null || "".equals(type)) {
            return null;
        }

        Sorter sorter;
        String t;
        String arg;
        int i = type.indexOf(':');
        if (i < 0) {
            t = type;
            arg = null;
        } else {
            t = type.substring(0, i);
            arg = type.substring(i + 1);
        }

        if ("reverse".equals(t)) {
            sorter = newInstance(arg);
            return sorter == null ? null : new Reverser(sorter);
        }

        try {
            if (arg == null) {
                sorter = (Sorter) createInstance(t, Sorter.class);
            } else {
                sorter = (Sorter) createInstance(t, //
                    Sorter.class, String.class, arg);
            }
        } catch (ConfigurationNotFoundException e) {
            throw new ExBibSorterNotFoundException(t);
        }
        if (sorter instanceof ResourceAware) {
            ((ResourceAware) sorter).setResourceFinder(getResourceFinder());
        }
        if (sorter instanceof PropertyAware) {
            ((PropertyAware) sorter).setProperties(properties);
        }
        if (sorter instanceof Startable) {
            ((Startable) sorter).start();
        }

        return sorter;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.PropertyAware#setProperties(java.util.Properties)
     */
    public void setProperties(Properties properties) {

        this.properties = properties;
    }

}
