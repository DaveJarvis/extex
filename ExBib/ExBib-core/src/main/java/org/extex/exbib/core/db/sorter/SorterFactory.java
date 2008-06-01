/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.db.sorter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.extex.exbib.core.ExBib;
import org.extex.exbib.core.exceptions.ExBibCsfNotFoundException;
import org.extex.exbib.core.exceptions.ExBibSorterNotFoundException;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.exbib.core.io.csf.CsfReader;
import org.extex.exbib.core.io.csf.CsfSorter;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.resource.PropertyAware;

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
    private Properties properties;

    /**
     * Creates a new object.
     * 
     * @param configuration the configuration for this factory
     */
    public SorterFactory(Configuration configuration) {

        super(configuration);
    }

    /**
     * Provides a {@link Sorter Sorter} implementation. The new
     * {@link Sorter Sorter} is configured with the same configuration as the
     * factory.
     * 
     * @param type the type
     * 
     * @return the new Sorter instance
     * 
     * @throws CsfException in case of an error
     * @throws ConfigurationException in case that something goes wrong
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException in case of an unsupported encoding
     * @throws ExBibCsfNotFoundException in case of a missing csf
     * @throws ExBibSorterNotFoundException in case of a missing sorter
     */
    public Sorter newInstance(String type)
            throws CsfException,
                ConfigurationException,
                UnsupportedEncodingException,
                IOException,
                ExBibCsfNotFoundException,
                ExBibSorterNotFoundException {

        if (type == null || "".equals(type)) {
            return null;
        } else if ("csf:".equals(type)) {
            return new CsfSorter();
        } else if (type.startsWith("csf:")) {
            InputStream is =
                    getResourceFinder().findResource(type.substring(4), "csf");
            if (is == null) {
                throw new ExBibCsfNotFoundException(type.substring(4));
            }
            try {
                String encoding =
                        properties.getProperty(ExBib.PROP_CSF_ENCODING);
                return new CsfReader().read(encoding == null
                        ? new InputStreamReader(is)
                        : new InputStreamReader(is, encoding));
            } finally {
                is.close();
            }
        }

        Sorter sorter;
        int i = type.indexOf(':');

        try {
            if (i < 0) {
                sorter = (Sorter) createInstance(type, Sorter.class);
            } else {
                sorter = (Sorter) createInstance(type.substring(0, i), //
                    Sorter.class, String.class, type.substring(i + 1));
            }
        } catch (ConfigurationNotFoundException e) {
            throw new ExBibSorterNotFoundException(i < 0 ? type : type
                .substring(0, i));
        }
        if (sorter instanceof PropertyAware) {
            ((PropertyAware) sorter).setProperties(properties);
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
