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

package org.extex.exbib.core.io.auxio;

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link AuxReader AuxReader}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</class>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * <pre>
 *   &lt;AuxReader&gt;
 *     &lt;class&gt;org.extex.exbib.core.io.auxio.AuxReaderImpl&lt;/class&gt;
 *   &lt;/AuxReader&gt;
 * </pre>
 * </p>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class AuxReaderFactory extends AbstractFactory {

    /**
     * Creates a new object.
     * 
     * @param config the configuration
     */
    public AuxReaderFactory(Configuration config) {

        super();
        configure(config);
    }

    /**
     * Create a new instance of an AuxReader.
     * 
     * @param finder the resource finder
     * 
     * @return the new instance of AuxReader
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    public AuxReader newInstance(ResourceFinder finder)
            throws ConfigurationException {

        AuxReader engine = (AuxReader) createInstance(AuxReader.class);
        engine.setResourceFinder(finder);
        return engine;
    }

}