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

package org.extex.exbib.core.bst;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link Processor Processor}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</class>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * <pre>
 *   &lt;Processor&gt;
 *     &lt;class&gt;org.extex.exbib.core.bst.ProcessorImpl&lt;/class&gt;
 *   &lt;/Processor&gt;
 * </pre>
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class ProcessorFactory {

    /** The configuration for this factory */
    private Configuration config = null;

    /**
     * Creates a new object.
     * 
     * @param cfg the configuration to use
     */
    public ProcessorFactory(Configuration cfg) {

        super();
        this.config = cfg;
    }

    /**
     * Get an instance of a {@link Processor Processor}.
     * 
     * @param db the database
     * 
     * @return the {@link Processor Processor}
     * 
     * @throws ConfigurationException if the item <tt>class</tt> can not be
     *         found in the configuration or the class can not be instantiated.
     * @throws ExBibException in case of an error
     */
    public synchronized Processor newInstance(DB db)
            throws ConfigurationException,
                ExBibException {

        Processor processor;

        try {
            processor = (Processor) Class.forName(//
                config.getAttribute("class")).newInstance();
        } catch (Exception e) {
            throw new ConfigurationWrapperException(e);
        }

        processor.configure(config);
        processor.setDB(db);

        return processor;
    }
}
