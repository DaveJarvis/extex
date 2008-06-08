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

import org.extex.exbib.core.io.Writer;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link BibPrinter BibPrinter}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</code>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * 
 * <pre>
 *   &lt;BibPrinter
 *       class="org.extex.exbib.core.io.bibio.BibPrinterImpl"/&gt;
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BibPrinterFactory extends AbstractFactory {

    /**
     * Creates a new object.
     * 
     * @param configuration the configuration
     */
    public BibPrinterFactory(Configuration configuration) {

        super(configuration);
    }

    /**
     * Create a new instance of a BibPrinter and return it. This BibPrinter is
     * set up to print to a given writer.
     * 
     * @param type the type
     * @param writer the writer to print to
     * 
     * @return the new BibPrinter
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public BibPrinter newInstance(String type, Writer writer)
            throws ConfigurationException {

        Configuration cfg = selectConfiguration(type);
        return (BibPrinter) createInstanceForConfiguration(cfg,
            BibPrinter.class, writer);
    }

}
