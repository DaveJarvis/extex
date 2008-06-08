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

package org.extex.exbib.core;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link Processor Processor}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</code>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * 
 * <pre>
 *   &lt;Processor
 *     class="org.extex.exbib.core.bst.BstProcessor"/&gt;
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class ProcessorFactory extends AbstractFactory {

    /**
     * Creates a new object.
     * 
     * @param configuration the configuration to use
     */
    public ProcessorFactory(Configuration configuration) {

        super(configuration);
    }

    /**
     * Get an instance of a {@link Processor Processor}.
     * 
     * @param db the database
     * @param type the type for lookup in the configuration; the value
     *        <code>null</code> signals the use of the default configuration
     * 
     * @return the {@link Processor Processor}
     * 
     * @throws ConfigurationException if the item <tt>class</tt> can not be
     *         found in the configuration or the class can not be instantiated.
     * @throws ExBibException in case of an error
     */
    public Processor newInstance(DB db, String type)
            throws ConfigurationException,
                ExBibException {

        Configuration cfg = selectConfiguration(type);
        Processor processor =
                (Processor) createInstanceForConfiguration(cfg, Processor.class);
        processor.setDB(db);
        return processor;
    }
}
