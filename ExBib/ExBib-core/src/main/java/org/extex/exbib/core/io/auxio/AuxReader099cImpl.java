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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.exbib.core.ProcessorContainer;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.AbstractFileReader;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;

/**
 * This is the core of the aux file reading as performed by
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X.
 * <p>
 * The following macros are recognized:
 * </p>
 * <dl>
 * <dt>\citation{keys}</dt>
 * <dd>pass a list of comma separated citation keys to the default bibliography</dd>
 * <dt>\bibstyle{styles}</dt>
 * <dd>pass a list of comma separated bibliography styles to the default
 * bibliography</dd>
 * <dt>\bibdata{databases}</dt>
 * <dd>pass a comma separated database names to the default bibliography</dd>
 * <dt>\@include{auxfile}</dt>
 * <dd>process an additional aux file in the same way</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class AuxReader099cImpl extends AbstractFileReader implements AuxReader {

    /**
     * This is the handler for bibdata.
     */
    private static final class BibdataHandler implements AuxHandler {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.auxio.AuxHandler#invoke(java.lang.String,
         *      org.extex.exbib.core.ProcessorContainer, java.lang.String,
         *      org.extex.exbib.core.io.auxio.AuxReader)
         */
        public void invoke(String arg, ProcessorContainer bibliographies,
                String type, AuxReader engine)
                throws ConfigurationException,
                    ExBibException {

            try {
                bibliographies.findProcessor(type).addBibliographyDatabase(
                    arg.split(","));
            } catch (UnsupportedEncodingException e) {
                throw new ConfigurationWrapperException(e);
            } catch (CsfException e) {
                throw new ConfigurationWrapperException(e);
            } catch (IOException e) {
                throw new ConfigurationWrapperException(e);
            }
        }
    }

    /**
     * This is the handler for bibstyle.
     */
    private static final class BibstyleHandler implements AuxHandler {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.auxio.AuxHandler#invoke(java.lang.String,
         *      org.extex.exbib.core.ProcessorContainer, java.lang.String,
         *      org.extex.exbib.core.io.auxio.AuxReader)
         */
        public void invoke(String arg, ProcessorContainer bibliographies,
                String type, AuxReader engine)
                throws ConfigurationException,
                    ExBibException {

            try {
                bibliographies.findProcessor(type).addBibliographyStyle(
                    arg.split(","));
            } catch (UnsupportedEncodingException e) {
                throw new ConfigurationWrapperException(e);
            } catch (CsfException e) {
                throw new ConfigurationWrapperException(e);
            } catch (IOException e) {
                throw new ConfigurationWrapperException(e);
            }
        }
    }

    /**
     * This is the handler for citation.
     */
    private static final class CitationHandler implements AuxHandler {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.auxio.AuxHandler#invoke(java.lang.String,
         *      org.extex.exbib.core.ProcessorContainer, java.lang.String,
         *      org.extex.exbib.core.io.auxio.AuxReader)
         */
        public void invoke(String arg, ProcessorContainer bibliographies,
                String type, AuxReader engine)
                throws ConfigurationException,
                    ExBibException {

            String[] citations = arg.replaceAll("[ \t\f]", "").split(",");
            try {
                bibliographies.findProcessor(type).addCitation(citations);
            } catch (UnsupportedEncodingException e) {
                throw new ConfigurationWrapperException(e);
            } catch (CsfException e) {
                throw new ConfigurationWrapperException(e);
            } catch (IOException e) {
                throw new ConfigurationWrapperException(e);
            }
        }
    }

    /**
     * This is the handler for include.
     */
    private final class IncludeHandler implements AuxHandler {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.auxio.AuxHandler#invoke(java.lang.String,
         *      org.extex.exbib.core.ProcessorContainer, java.lang.String,
         *      org.extex.exbib.core.io.auxio.AuxReader)
         */
        public void invoke(String arg, ProcessorContainer bibliographies,
                String type, AuxReader engine)
                throws ConfigurationException,
                    IOException,
                    ExBibException {

            engine.load(bibliographies, arg, encoding);
        }
    }

    /**
     * The constant <tt>PATTERN</tt> contains the pattern for the recognized
     * macros.
     */
    protected static final Pattern PATTERN = Pattern
        .compile("^\\\\([@cb][a-z]+)\\{([^{}]*)\\}");

    /**
     * The constant <tt>DEFAULT_TYPE</tt> contains the default type.
     */
    protected static final String DEFAULT_TYPE = "bbl";

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
     * The field <tt>encoding</tt> contains the encoding for transporting it to
     * the registered handler.
     */
    private String encoding;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    public AuxReader099cImpl() throws ConfigurationException {

        handlerMap = new HashMap<String, AuxHandler>();
        register("citation", new CitationHandler());
        register("bibstyle", new BibstyleHandler());
        register("bibdata", new BibdataHandler());
        register("@include", new IncludeHandler());
    }

    /**
     * Get a handler for a given key.
     * 
     * @param key the key
     * 
     * @return the handler or <code>null</code>
     */
    protected AuxHandler getHandler(String key) {

        return handlerMap.get(key);
    }

    /**
     * Getter for observer.
     * 
     * @return the observer
     */
    protected ResourceObserver getObserver() {

        return observer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.auxio.AuxReader#load(org.extex.exbib.core.ProcessorContainer,
     *      java.lang.String, java.lang.String)
     */
    public void load(ProcessorContainer bibliographies, String resource,
            String enc)
            throws ConfigurationException,
                IOException,
                ExBibException {

        this.encoding = enc;
        LineNumberReader reader = open(resource, "aux", enc);
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
     * @see org.extex.exbib.core.io.auxio.AuxReader#register(org.extex.exbib.core.io.auxio.ResourceObserver)
     */
    public ResourceObserver register(ResourceObserver resourceObserver) {

        ResourceObserver obs = this.observer;
        this.observer = resourceObserver;
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

    /**
     * Setter for encoding.
     * 
     * @param encoding the encoding to set
     */
    protected void setEncoding(String encoding) {

        this.encoding = encoding;
    }

}
