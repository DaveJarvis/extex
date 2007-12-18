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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.junit.Test;

/**
 * This is a test suite for the {@link Indexer}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IndexerTest {

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

    static {
        FILES.put("T10.raw", "");
        FILES.put("T11.raw", "(indexentry :key (\"abc\") :locref \"IV\")");
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
    private void runTest(ArrayList<String> styles, ArrayList<String> resources,
            String expectedOut, String expectedLog) throws Exception {

        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, new LogFormatter());
        handler.setLevel(Level.WARNING);
        logger.addHandler(handler);

        Indexer indexer = new Indexer();
        ResourceFinder finder = new MyFinder();
        indexer.setResourceFinder(finder);
        StringWriter writer = (expectedOut == null ? null : new StringWriter());
        indexer.run(styles, resources, writer, logger);

        if (writer != null) {
            assertEquals(expectedOut, writer.toString());
        }
        handler.close();
        assertEquals(expectedLog, out.toString());
    }

    /**
     * <testcase> Check that null values as parameters are accepted. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        runTest(null, null, null, "No styles to load.\nNo resources to load.\n");
    }

    /**
     * <testcase> Check that empty list for styles and resources as parameters
     * are accepted. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test02() throws Exception {

        runTest(new ArrayList<String>(), new ArrayList<String>(), null,
            "No styles to load.\nNo resources to load.\n");
    }

    /**
     * <testcase> Check that empty input produces empty output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test10() throws Exception {

        ArrayList<String> files = new ArrayList<String>();
        files.add("T10.raw");
        runTest(new ArrayList<String>(), files, "", "No styles to load.\n");
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test11() throws Exception {

        ArrayList<String> files = new ArrayList<String>();
        files.add("T11.raw");
        runTest(new ArrayList<String>(), files, "xxx", "No styles to load.\n");
    }

    /**
     * <testcase> Check that an undefined style is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = FileNotFoundException.class)
    public final void testNoResource() throws Exception {

        ArrayList<String> styles = new ArrayList<String>();
        styles.add("xyz");
        runTest(styles, new ArrayList<String>(), null, "");
    }

}
