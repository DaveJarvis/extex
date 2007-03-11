/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.w2l;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.extex.util.framework.i18n.Localizer;
import org.extex.util.framework.i18n.LocalizerFactory;

import writer2latex.api.Converter;
import writer2latex.api.ConverterFactory;
import writer2latex.api.ConverterResult;
import writer2latex.util.Config;

/**
 * Converter w2l.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class W2lConverter {

    /**
     * w2l application.
     */
    private static final String APPLICATION = "application/x-latex";

    /**
     * Stream for the configuration.
     */
    private InputStream configStream;

    /**
     * The converter.
     */
    private Converter converter;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private Localizer localizer = LocalizerFactory
            .getLocalizer(W2lConverter.class);

    /**
     * Stream for Output
     */
    private OutputStream outputStream;

    /**
     * Stream for the sourceStream
     */
    private InputStream sourceStream;

    /**
     * Creates a new object.
     */
    public W2lConverter() {

        ConverterFactory factory = new ConverterFactory();
        converter = factory.createConverter(APPLICATION);

    }

    public void convert() throws IOException {

        if (configStream == null) {
            throw new IOException(localizer.format("W2lConverter.noConfig"));
        }
        if (sourceStream == null) {
            throw new IOException(localizer.format("W2lConverter.noSource"));
        }
        if (outputStream == null) {
            throw new IOException(localizer.format("W2lConverter.noOutput"));
        }

        // configuration
        Config config = new Config();
        config.read(configStream);
        converter.setConfig(config);

        ConverterResult result = converter.convert(sourceStream, "xxx");

        result.getMasterDocument().write(outputStream);

    }

    /**
     * Setter for configStream.
     *
     * @param config The configStream to set.
     */
    public void setConfigStream(final InputStream config) {

        configStream = config;
    }

    /**
     * Setter for outputStream.
     *
     * @param output The outputStream to set.
     */
    public void setOutput(final OutputStream output) {

        outputStream = output;
    }

    /**
     * Setter for sourceStream.
     *
     * @param source The sourceStream to set.
     */
    public void setSourceStream(final InputStream source) {

        sourceStream = source;
    }

}
