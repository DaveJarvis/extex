/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.exbib.core.ProcessorContainer;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is the core of the aux file reading. In addition to the one performed by
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X it supports multiple bibliographies via optional arguments of the
 * macros.
 * <p>
 * The following macros are recognized in addition to those of the super class:
 * </p>
 * <dl>
 * <dt>\citation[type]{keys}</dt>
 * <dd>pass a list of comma separated citation keys to the bibliography "type"</dd>
 * <dt>\bibstyle[type]{styles}</dt>
 * <dd>pass a list of comma separated bibliography styles to the bibliography
 * "type"</dd>
 * <dt>\bibdata[type]{databases}</dt>
 * <dd>pass a comma separated database names to the bibliography "type"</dd>
 * <dt>\biboption{option}</dt>
 * <dd>pass an option to the bibliography processor.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AuxReaderImpl extends AuxReader099cImpl {

    /**
     * This is the handler for biboption.
     */
    private static final class BiboptionHandler implements AuxHandler {

        /**
    *      org.extex.exbib.core.ProcessorContainer, java.lang.String,
         *      org.extex.exbib.core.io.auxio.AuxReader)
         */
        public void invoke(String arg, ProcessorContainer processors,
                String type, AuxReader engine)
                throws ConfigurationException,
                    ExBibException {

            processors.setOption(type, arg);
        }
    }

    /**
     * The constant {@code PATTERN} contains the pattern for the recognized
     * macros.
     */
    protected static final Pattern PATTERN = Pattern
        .compile("^\\\\([@cb][a-z]+)\\{([^{}]*)\\}");

    /**
     * The field {@code OPT_PATTERN} contains the pattern for the recognized
     * macros with optional arguments.
     */
    private static final Pattern OPT_PATTERN = Pattern
        .compile("^\\\\([cb][a-z]+)\\[([^]]*)\\]\\{([^{}]*)\\}");

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    public AuxReaderImpl() throws ConfigurationException {

        register("biboption", new BiboptionHandler());
    }

    /**
*      java.lang.String, java.lang.String)
     */
    @Override
    public void load(ProcessorContainer bibliographies, String resource,
            String encoding)
            throws ConfigurationException,
                IOException,
                ExBibException {

        setEncoding(encoding);
        LineNumberReader reader = open(resource, "aux", encoding);
        String name = getFilename();

        ResourceObserver observer = getObserver();
        if (observer != null) {
            observer.observeOpen(resource, "aux", name);
        }

        try {
            for (String line = reader.readLine(); line != null; line =
                    reader.readLine()) {
                Matcher m = PATTERN.matcher(line);

                if (m.matches()) {
                    AuxHandler handler = getHandler(m.group(1));
                    if (handler != null) {
                        handler.invoke(m.group(2), bibliographies,
                            DEFAULT_TYPE, this);
                    }
                    continue;
                }
                m = OPT_PATTERN.matcher(line);

                if (m.matches()) {
                    AuxHandler handler = getHandler(m.group(1));
                    if (handler != null) {
                        handler.invoke(m.group(3), bibliographies, m.group(2),
                            this);
                    }
                    continue;
                }
            }
            if (observer != null) {
                observer.observeClose(resource, "aux", name);
            }
        } finally {
            reader.close();
            close();
        }
    }

}
