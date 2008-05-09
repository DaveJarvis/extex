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

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.exbib.core.ProcessorContainer;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.AbstractFileReader;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is the core of the aux file reading. In addition to the one performed by
 * B<small>IB</small>T<sub>E</sub>X it supports multiple bibliographies via
 * optional arguments of the macros.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class AuxReaderImpl extends AbstractFileReader implements AuxReader {

    /**
     * The constant <tt>PATTERN</tt> contains the pattern for the recognized
     * macros.
     */
    private static final Pattern PATTERN =
            Pattern.compile("^\\\\([@cb][a-z]+)\\{([^{}]*)\\}");

    /**
     * The field <tt>OPT_PATTERN</tt> contains the pattern for the recognized
     * macros with optional arguments.
     */
    private static final Pattern OPT_PATTERN =
            Pattern.compile("^\\\\([cb][a-z]+)\\[([^]]*)\\]\\{([^{}]*)\\}");

    /**
     * The constant <tt>DEFAULT_TYPE</tt> contains the default type.
     */
    private static final String DEFAULT_TYPE = "bbl";

    /**
     * The field <tt>handlerMap</tt> contains the macro handlers for the aux
     * file.
     */
    private Map<String, AuxHandler> handlerMap;

    /**
     * The field <tt>observer</tt> contains the observer.
     */
    private ResourceObserver observer = null;

    /**
     * The field <tt>encoding</tt> contains the encoding for transporting it
     * to the registered handler.
     */
    private String encoding;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    public AuxReaderImpl() throws ConfigurationException {

        super();
        handlerMap = new HashMap<String, AuxHandler>();
        register("citation", new AuxHandler() {

            public void invoke(String arg, ProcessorContainer bibliographies,
                    String type, AuxReader engine)
                    throws ConfigurationException,
                        ExBibException {

                String[] citations = arg.replaceAll("[ \t\f]", "").split(",");
                bibliographies.findBibliography(type).addCitation(citations);
            }
        });
        register("bibstyle", new AuxHandler() {

            public void invoke(String arg, ProcessorContainer bibliographies,
                    String type, AuxReader engine)
                    throws ConfigurationException,
                        ExBibException {

                bibliographies.findBibliography(type).addBibliographyStyle(
                    arg.split(","));
            }
        });
        register("bibdata", new AuxHandler() {

            public void invoke(String arg, ProcessorContainer bibliographies,
                    String type, AuxReader engine)
                    throws ConfigurationException,
                        ExBibException {

                bibliographies.findBibliography(type).addBibliographyDatabase(
                    arg.split(","));
            }
        });
        register("@include", new AuxHandler() {

            public void invoke(String arg, ProcessorContainer bibliographies,
                    String type, AuxReader engine)
                    throws ConfigurationException,
                        IOException,
                        ExBibException {

                engine.load(bibliographies, arg, encoding);
            }
        });
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.auxio.AuxReader#load(
     *      org.extex.exbib.core.ProcessorContainer, java.lang.String,
     *      java.lang.String)
     */
    public void load(ProcessorContainer bibliographies, String resource,
            String encoding)
            throws ConfigurationException,
                IOException,
                ExBibException {

        this.encoding = encoding;
        LineNumberReader reader = open(resource, "aux", encoding);
        String name = getFilename();

        if (observer != null) {
            observer.observeOpen(resource, "aux", name);
        }

        try {
            for (String line = reader.readLine(); line != null; line =
                    reader.readLine()) {
                Matcher m = PATTERN.matcher(line);

                if (m.matches()) {
                    AuxHandler handler = handlerMap.get(m.group(1));
                    if (handler != null) {
                        handler.invoke(m.group(2), bibliographies,
                            DEFAULT_TYPE, this);
                    }
                    continue;
                }
                m = OPT_PATTERN.matcher(line);

                if (m.matches()) {
                    AuxHandler handler = handlerMap.get(m.group(1));
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

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.auxio.AuxReader#register(
     *      org.extex.exbib.core.io.auxio.ResourceObserver)
     */
    public ResourceObserver register(ResourceObserver observer) {

        ResourceObserver obs = this.observer;
        this.observer = observer;
        return obs;
    }

    /**
     * Register a handler for a macro in the aux file.
     * 
     * @param name the name
     * @param handler the handler
     * 
     * @return the old handler or <code>null</code> for none
     */
    public AuxHandler register(String name, AuxHandler handler) {

        return handlerMap.put(name, handler);
    }

}
