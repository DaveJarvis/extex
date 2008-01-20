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

package org.extex.exindex.core;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.extex.exindex.core.parser.exindex.ExIndexParserFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;

/**
 * This is a test driver for the {@link Indexer}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractIndexerTester {

    /**
     * Dummy implementation for testing purposes.
     */
    private final class MyFinder implements ResourceFinder {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
         */
        public void enableTracing(boolean flag) {

            // no effect
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
         *      java.lang.String)
         */
        public InputStream findResource(String name, String type)
                throws ConfigurationException {

            String s = FILES.get(name);
            return s == null ? null : new ByteArrayInputStream(s.getBytes());
        }
    }

    /**
     * The field <tt>FILES</tt> contains the prerecorded resources.
     */
    private static final Map<String, String> FILES =
            new HashMap<String, String>();

    /**
     * The field <tt>FINDER</tt> contains the resource finder.
     */
    private static final ResourceFinder FINDER = new ResourceFinder() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
         */
        public void enableTracing(boolean flag) {

            // nay
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
         *      java.lang.String)
         */
        public InputStream findResource(String name, String type)
                throws ConfigurationException {

            String contents = FILES.get(name);
            return contents == null ? null : new ByteArrayInputStream(contents
                .getBytes());
        }

    };

    /**
     * Add a resource
     * 
     * @param name the name
     * @param contents the contents
     */
    public static void register(String name, String contents) {

        FILES.put(name, contents);
    }

    /**
     * Create a List of Strings and fill it with some values.
     * 
     * @param args the varargs of the values
     * 
     * @return the List constructed. This is never <code>null</code>
     */
    public List<String> makeList(String... args) {

        ArrayList<String> list = new ArrayList<String>();
        for (String s : args) {
            list.add(s);
        }
        return list;
    }

    /**
     * Run a test and compare the output and error stream results.
     * 
     * @param styles the styles to pass in
     * @param resources the resources to read
     * @param expectedOut the expected output
     * @param expectedLog the expected logging output
     * 
     * @throws Exception in case of an error
     */
    protected void runTest(List<String> styles, List<String> resources,
            String expectedOut, String expectedLog) throws Exception {

        Locale.setDefault(Locale.ENGLISH);

        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);
        ByteArrayOutputStream log = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(log, new LogFormatter());
        handler.setLevel(Level.WARNING);
        logger.addHandler(handler);

        Indexer indexer = new Indexer();
        ResourceFinder finder = new MyFinder();
        indexer.setResourceFinder(finder);
        ExIndexParserFactory parserFactory = new ExIndexParserFactory();
        parserFactory.setResourceFinder(FINDER);
        indexer.setParserFactory(parserFactory);
        StringWriter writer = (expectedOut == null ? null : new StringWriter());
        indexer.run(styles, resources, writer, logger);

        if (expectedLog != null) {
            log.close();
            handler.flush();
            handler.close();
            assertEquals("log", expectedLog, log.toString());
        }

        if (writer != null) {
            String s = writer.toString();
            assertEquals(expectedOut, s);
        }
    }

}
