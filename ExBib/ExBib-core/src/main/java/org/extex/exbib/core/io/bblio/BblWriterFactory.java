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

package org.extex.exbib.core.io.bblio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.WriterFactory;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This factory class can be used to get an implementation for the interface
 * {@link BblWriter}.
 * <p>
 * The factory is controlled by a configuration. This configuration contains an
 * attribute <code>class</code>. This attribute holds the name of the class to
 * be instantiated. Consider the following example of a configuration file:
 * 
 * <pre>
 *   &lt;BblWriter
 *       class="org.extex.exbib.core.io.bblio.BblWriterImpl"/&gt;
 * </pre>
 * 
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BblWriterFactory extends AbstractFactory {

    /**
     * The field <tt>writerFactory</tt> contains the writer factory.
     */
    private WriterFactory writerFactory;

    /**
     * Creates a new object.
     * 
     * @param configuration the configuration
     * @param encoding the encoding for writing
     * 
     * @throws ConfigurationException in case of a configuration error
     * @throws UnsupportedEncodingException in case of an invalid encoding
     */
    public BblWriterFactory(Configuration configuration, String encoding)
            throws ConfigurationException,
                UnsupportedEncodingException {

        super(configuration);
        writerFactory = new WriterFactory(configuration);
        if (encoding != null) {
            writerFactory.setEncoding(encoding);
        }
    }

    /**
     * Callback method to be used when the output is discarded.
     * 
     */
    protected void infoDiscarted() {

        //
    }

    /**
     * Callback method to be used when the output is sent to a file.
     * 
     * @param file the name of the file
     */
    protected void infoOutput(String file) {

        //
    }

    /**
     * Callback method to be used when the output is sent to stdout.
     * 
     */
    protected void infoStdout() {

        //
    }

    /**
     * Allocates a new instance of a BblWriter.
     * 
     * @param file the name of the file
     * 
     * @return a new instance of a BblWriter
     * 
     * @throws IOException in case that the file could not be opened
     * @throws UnsupportedEncodingException in case of an unknown encoding
     * @throws ConfigurationException in case of an configuration error
     */
    public synchronized Writer newInstance(String file)
            throws ConfigurationException,
                UnsupportedEncodingException,
                IOException {

        Writer writer;

        if (file == null || file.equals("")) {
            writer = writerFactory.newInstance();
            infoDiscarted();
        } else if (file.equals("-")) {
            writer = writerFactory.newInstance(System.out);
            infoStdout();
        } else {
            writer = writerFactory.newInstance(file);
            infoOutput(file);
        }

        return (BblWriter) createInstance(BblWriter.class, writer);
    }

}
