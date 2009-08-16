/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationUnsupportedEncodingException;

/**
 * This class provides some methods to get a new instance of some kind of
 * writer.
 *
 * <h3>Configuration</h3>
 * <p>
 * The configuration of the factory is passed on to the writer created. Thus is
 * can be used to influence their behavior.
 * </p>
 * <p>
 * The configuration can contain some attributes which are used by the factory
 * itself.
 * </p>
 * <dl>
 * <dt>encoding</dt>
 * <dd>The encoding attribute can name the encoding to be used for the writers.
 * If the attribute is not present then the default encoding of the current
 * platform is used.</dd>
 * </dl>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class WriterFactory extends AbstractFactory {

    /**
     * The field <tt>encoding</tt> contains the encoding to use for writing.
     */
    private String encoding = null;

    /**
     * Creates a new object.
     *
     * @param config the configuration
     *
     * @throws UnsupportedEncodingException in case of an undefined encoding
     */
    public WriterFactory(Configuration config)
            throws UnsupportedEncodingException {

        super(config);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.AbstractFactory#configure(org.extex.framework.configuration.Configuration)
     */
    @Override
    public void configure(Configuration configuration)
            throws ConfigurationException {

        super.configure(configuration);
        try {
            setEncoding(configuration.getAttribute("encoding"));
        } catch (UnsupportedEncodingException e) {
            throw new ConfigurationUnsupportedEncodingException(e.getMessage(),
                configuration.toString());
        }
    }

    /**
     * Getter for a new writer without an argument.
     *
     * @return a new NullWriter
     *
     * @throws ConfigurationException in case of an configuration error
     */
    public Writer newInstance() throws ConfigurationException {

        Writer writer = new NullWriter();
        if (writer instanceof Configurable) {
            ((Configurable) writer).configure(getConfiguration());
        }
        return writer;
    }

    /**
     * Getter for a new writer from a PrintStream.
     *
     * @param stream the PrintStream to write to
     *
     * @return a new {@link StreamWriter} or a new {@link NullWriter} if the
     *         stream is <code>null</code>
     *
     * @throws UnsupportedEncodingException in case of an unknown encoding
     * @throws ConfigurationException in case of an configuration error
     */
    public Writer newInstance(PrintStream stream)
            throws UnsupportedEncodingException,
                ConfigurationException {

        Writer writer =
                (stream == null
                        ? (Writer) new NullWriter()
                        : (Writer) new StreamWriter(stream, encoding));
        if (writer instanceof Configurable) {
            ((Configurable) writer).configure(getConfiguration());
        }
        return writer;
    }

    /**
     * Getter for a new writer from a file.
     *
     * @param file the name of the file to print to
     *
     * @return a new {@link StreamWriter} or a new {@link NullWriter} if the
     *         file is <code>null</code>
     *
     * @throws IOException in case that the file could not be opened
     * @throws UnsupportedEncodingException in case of an unknown encoding
     * @throws ConfigurationException in case of an configuration error
     */
    public Writer newInstance(String file)
            throws IOException,
                UnsupportedEncodingException,
                ConfigurationException {

        if (file == null) {
            return new NullWriter();
        }

        Writer writer = new StreamWriter(file, encoding);
        if (writer instanceof Configurable) {
            ((Configurable) writer).configure(getConfiguration());
        }
        return writer;
    }

    /**
     * Getter for a new writer with a StringBuffer
     *
     * @param buffer the StringBuffer to fill
     *
     * @return a new StringBufferWriter
     *
     * @throws ConfigurationException in case of an configuration error
     */
    public Writer newInstance(StringBuffer buffer)
            throws ConfigurationException {

        Writer writer = new StringBufferWriter(buffer);
        if (writer instanceof Configurable) {
            ((Configurable) writer).configure(getConfiguration());
        }
        return writer;
    }

    /**
     * Getter for a new writer composed or two others.
     *
     * @param a the first writer
     * @param b the second writer
     *
     * @return a new multi writer if both writers are not <code>null</code>; one
     *         of the writers if the other one is <code>null</code>; a new
     *         {@link NullWriter} if both are <code>null</code>
     *
     * @throws UnsupportedEncodingException in case of an unknown encoding
     * @throws ConfigurationException in case of an configuration error
     */
    public Writer newInstance(Writer a, Writer b)
            throws UnsupportedEncodingException,
                ConfigurationException {

        if (a == null) {
            return (b == null ? new NullWriter() : b);
        } else if (b == null) {
            return a;
        }

        MultiWriter writer = new MultiWriter(a, b);
        writer.configure(getConfiguration());
        return writer;
    }

    /**
     * Setter for the encoding. The encoding is one of the supported charset
     * names of the Java platform. At least the following values are guaranteed
     * to be supported:
     * <dl>
     * <dt>US-ASCII</dt>
     * <dd>Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of
     * the Unicode character set</dd>
     * <dt>ISO-8859-1</dt>
     * <dd>ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1</dd>
     * <dt>UTF-8</dt>
     * <dd>Eight-bit UCS Transformation Format</dd>
     * <dt>UTF-16BE</dt>
     * <dd>Sixteen-bit UCS Transformation Format, big-endian byte order</dd>
     * <dt>UTF-16LE</dt>
     * <dd>Sixteen-bit UCS Transformation Format, little-endian byte order</dd>
     * <dt>UTF-16</dt>
     * <dd>Sixteen-bit UCS Transformation Format, byte order identified by an
     * optional byte-order mark</dd>
     * </dl>
     *
     * @param encoding the encoding to set; it can be <code>null</code> to
     *        indicate the platform default encoding
     *
     * @throws UnsupportedEncodingException in case of an unsupported encoding
     *         name
     */
    public void setEncoding(String encoding)
            throws UnsupportedEncodingException {

        if (encoding != null && !Charset.isSupported(encoding)) {
            throw new UnsupportedEncodingException(encoding);
        }
        this.encoding = encoding;
    }

}
