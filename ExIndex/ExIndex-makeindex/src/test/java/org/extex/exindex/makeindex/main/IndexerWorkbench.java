/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import org.extex.exindex.core.Indexer;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for the {@link Indexer}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IndexerWorkbench {

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
        public NamedInputStream findResource(String name, String type)
                throws ConfigurationException {

            String s = FILES.get(name);
            return s == null ? null : new NamedInputStream(
                new ByteArrayInputStream(s.getBytes()), "");
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
        FILES.put("T111.raw",
            "(indexentry :tkey (\"abc\") :xref (\"IV\") :attr \"see\")");
        FILES.put("style11",
            "(markup-index :open \"\\begin{index}\" :close \"\\end{index}\")"
                    // + "(markup-trace :on :open \"~t<\" :close \">~n\")"
                    + "(define-crossref-class \"see\" :unverified)"
                    + "(define-letter-group \"a\")");
        FILES.put("style12",
            "(markup-index :open \"\\begin{index}\" :close \"\\end{index}\")"
                    + "(define-letter-group \"A\")");
    }

    /**
     * Create a List of Strings and fill it with some values.
     * 
     * @param args the varargs of the values
     * 
     * @return the List constructed. This is never <code>null</code>
     */
    public static List<String> makeList(String... args) {

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
    private void runTest(List<String> styles, List<String> resources,
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
        StringWriter writer = (expectedOut == null ? null : new StringWriter());
        indexer.run(styles, resources, writer, logger);

        handler.close();
        assertEquals(expectedLog, log.toString());

        if (writer != null) {
            String s = writer.toString();
            assertEquals(expectedOut, s);
        }
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testX111() throws Exception {

        runTest(makeList("style11"), makeList("T111.raw"),
            "\\begin{index}abcIV\\end{index}", "");
    }

}