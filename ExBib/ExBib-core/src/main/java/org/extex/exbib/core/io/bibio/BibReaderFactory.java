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

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link BibReader BibReader}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</code>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * 
 * <pre>
 *   &lt;BibReader
 *       class="org.extex.exbib.core.io.bibio.BibReader099Impl"/&gt;
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BibReaderFactory extends AbstractFactory {

    /**
     * Creates a new object.
     * 
     * @param config the configuration
     * @param finder the resource finder
     */
    public BibReaderFactory(Configuration config, ResourceFinder finder) {

        super();
        configure(config);
        setResourceFinder(finder);
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

        BibReader bibReader = (BibReader) super.createInstance(BibReader.class);
        bibReader.setResourceFinder(getResourceFinder());
        bibReader.open(file);

        return bibReader;
    }

}
