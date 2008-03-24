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

package org.extex.exbib.core.engine;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link Engine Engine}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</class>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * <pre>
 *   &lt;Engine&gt;
 *     &lt;class&gt;org.extex.exbib.core.io.auxio.AuxReaderImpl&lt;/class&gt;
 *   &lt;/Engine&gt;
 * </pre>
 * </p>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class EngineFactory {

    /**
     * The field <tt>config</tt> contains the configuration of the factory.
     */
    private Configuration config;

    /**
     * Creates a new object.
     * 
     * @param config the configuration
     */
    public EngineFactory(Configuration config) {

        super();
        this.config = config;
    }

    /**
     * Create a new instance of an Engine.
     * 
     * @return the new instance of Engine
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    public synchronized Engine newInstance() throws ConfigurationException {

        Engine engine;

        try {
            engine = (Engine) Class.forName(//
                config.getAttribute("class")).newInstance();
        } catch (Exception e) {
            throw new ConfigurationWrapperException(e);
        }

        engine.configure(config);

        return engine;
    }

}
