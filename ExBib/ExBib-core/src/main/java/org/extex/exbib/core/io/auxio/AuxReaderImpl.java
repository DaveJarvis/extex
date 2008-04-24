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

import org.extex.exbib.core.bst.Bibliography;
import org.extex.exbib.core.io.AbstractFileReader;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is the core of the aux file reading as performed by BibTeX.
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
            Pattern.compile("^\\\\([@a-z]+)\\{([^{}]*)\\}");

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

            public void invoke(String arg, Bibliography bibliography,
                    int[] count, AuxReader engine) {

                String[] citations = arg.replaceAll("[ \t\f]", "").split(",");
                bibliography.addCitation(citations);
                count[2] += citations.length;
            }
        });
        register("bibstyle", new AuxHandler() {

            public void invoke(String arg, Bibliography bibliography,
                    int[] count, AuxReader engine) {

                bibliography.addBibliographyStyle(arg.split(","));
                count[1]++;
            }
        });
        register("bibdata", new AuxHandler() {

            public void invoke(String arg, Bibliography bibliography,
                    int[] count, AuxReader engine) {

                bibliography.addBibliographyDatabase(arg.split(","));
                count[0]++;
            }
        });
        register("@include", new AuxHandler() {

            public void invoke(String arg, Bibliography bibliography,
                    int[] count, AuxReader engine)
                    throws ConfigurationException,
                        IOException {

                engine.process(bibliography, arg, encoding);
            }
        });
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.auxio.AuxReader#process(
     *      org.extex.exbib.core.bst.Bibliography, java.lang.String, String)
     */
    public int[] process(Bibliography bibliography, String resource,
            String encoding) throws ConfigurationException, IOException {

        this.encoding = encoding;
        LineNumberReader reader = open(resource, "aux", encoding);
        String name = getFilename();

        if (observer != null) {
            observer.observeOpen(resource, "aux", name);
        }

        int[] count = new int[]{0, 0, 0};

        try {
            for (String line = reader.readLine(); line != null; line =
                    reader.readLine()) {
                Matcher m = PATTERN.matcher(line);

                if (m.matches()) {
                    AuxHandler handler = handlerMap.get(m.group(1));
                    if (handler != null) {
                        handler.invoke(m.group(2), bibliography, count, this);
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
        return count;
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
