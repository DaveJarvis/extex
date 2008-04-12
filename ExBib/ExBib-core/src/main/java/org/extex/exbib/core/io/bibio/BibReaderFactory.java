/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import java.io.FileNotFoundException;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link BibReader BibReader}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</class>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * <pre>
 *   &lt;BibReader&gt;
 *     &lt;class&gt;org.extex.exbib.core.io.bibio.BibReader099Impl&lt;/class&gt;
 *   &lt;/BibReader&gt;
 * </pre>
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BibReaderFactory implements ResourceAware {

    /**
     * The field <tt>config</tt> contains the configuration.
     */
    private Configuration config;

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * Creates a new object.
     * 
     * @param config the configuration
     */
    public BibReaderFactory(Configuration config) {

        super();
        this.config = config;
    }

    /**
     * Get a new instance of a BibReader.
     * 
     * @param file the file name
     * 
     * @return a new instance of the desired class
     * 
     * @throws ConfigurationException in case of a configuration error
     * @throws FileNotFoundException in case the file could not be opened for
     *         reading
     */
    public BibReader newInstance(String file)
            throws ConfigurationException,
                FileNotFoundException {

        BibReader bibReader;
        try {
            bibReader =
                    (BibReader) Class.forName(config.getAttribute("class"))
                        .newInstance();
        } catch (Exception e) {
            throw new ConfigurationWrapperException(e);
        }
        bibReader.setResourceFinder(finder);
        bibReader.open(file);

        return bibReader;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;
    }

}
