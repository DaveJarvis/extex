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

package org.extex.exbib.core.io.bibio;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationUnsupportedEncodingException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link BibPrinter BibPrinter}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</class>. This attribute holds the name of the class
 * to be instantiated. Consider the following example of a configuration file:
 * <pre>
 *   &lt;BibPrinter&gt;
 *     &lt;class&gt;org.extex.exbib.core.io.bibio.BibPrinterImpl&lt;/class&gt;
 *   &lt;/BibPrinter&gt;
 * </pre>
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BibPrinterFactory {

    /** the configuration information for this class */
    private Configuration config;

    /**
     * Creates a new object.
     * 
     * @param cfg the configuration
     */
    public BibPrinterFactory(Configuration cfg) {

        super();
        config = cfg;
    }

    /**
     * Create a new instance of a BibPrinter and return it. This BibPrinter is
     * set up to print to a given file.
     * 
     * @param file the file to print to
     * @param encoding the encoding for the file
     * 
     * @return the new BibPrinter
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     * @throws FileNotFoundException in case that the file could not be opened
     *         for writing
     */
    public synchronized BibPrinter newInstance(String file, String encoding)
            throws ConfigurationException,
                FileNotFoundException {

        BibPrinter bibPrinter;

        try {
            bibPrinter =
                    (BibPrinter) Class.forName(config.getAttribute("class"))
                        .newInstance();
            bibPrinter.setDestination(file, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ConfigurationUnsupportedEncodingException(e.getMessage(),
                "");
        } catch (Exception e) {
            throw new ConfigurationWrapperException(e);
        }

        return bibPrinter;
    }

    /**
     * Create a new instance of a BibPrinter and return it. This BibPrinter is
     * set up to print to a given writer.
     * 
     * @param writer the writer to print to
     * @param encoding the encoding for the file
     * 
     * @return the new BibPrinter
     * 
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public BibPrinter newInstance(Writer writer, String encoding)
            throws ConfigurationException {

        BibPrinter bibPrinter;

        try {
            bibPrinter =
                    (BibPrinter) Class.forName(config.getAttribute("class"))
                        .newInstance();
            bibPrinter.setDestination(writer);
        } catch (UnsupportedEncodingException e) {
            throw new ConfigurationUnsupportedEncodingException(e.getMessage(),
                "");
        } catch (Exception e) {
            throw new ConfigurationWrapperException(e);
        }

        return bibPrinter;
    }
}
