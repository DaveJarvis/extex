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

package org.extex.exindex.core;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.extex.exindex.core.parser.exindex.ExIndexParserFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Ignore;
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
    private static final class MyFinder implements ResourceFinder {

        public void enableTracing(boolean flag) {

            // no effect
        }

        public NamedInputStream findResource(String name, String type)
                throws ConfigurationException {

            String content = FILES.get(name);
            return content == null ? null : new NamedInputStream(
                new ByteArrayInputStream(content.getBytes()), "");
        }
    }

    /**
     * The field <tt>FILES</tt> contains the prerecorded resources.
     */
    private static final Map<String, String> FILES =
        new HashMap<>();

    static {
        FILES.put("T10.raw", "");
        FILES.put("T11.raw", "(indexentry :key (\"abc\") :locref \"IV\")");
        FILES.put("T111.raw",
            "(indexentry :tkey (\"abc\") :xref (\"IV\") :attr \"see\")");
        FILES
            .put(
                "T112.raw",
                "(indexentry :tkey (\"abc\") :xref (\"IV\") :attr \"see\")"
                        + "(indexentry :tkey (\"abc\") :xref (\"VI\") :attr \"see\")");
        FILES.put("style11",
            "(markup-index :open \"\\begin{index}\" :close \"\\end{index}\")"
                    + "(define-crossref-class \"see\" :unverified)"
                    + "(define-letter-group \"a\")");
        FILES.put("style12",
            "(markup-index :open \"\\begin{index}\" :close \"\\end{index}\")"
                    + "(markup-letter-group :group \"a\" :open-head \"->\")"
                    + "(define-letter-group \"a\")");
        FILES.put("style13",
            "(markup-index :open \"\\begin{index}\" :close \"\\end{index}\")"
                    + "(markup-letter-group :group \"a\" :open-head \"->\")"
                    + "(define-crossref-class \"see\" :unverified)"
                    + "(define-letter-group \"a\")");
    }

    /**
     * Create a List of Strings and fill it with some values.
     * 
     * @param args the varargs of the values
     * 
     * @return the List constructed. This is never <code>null</code>
     */
    public static List<String> makeList(String... args) {

        List<String> list = new ArrayList<>();
        Collections.addAll( list, args );
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
        logger.setLevel(Level.INFO);
        logger.addHandler(handler);

        Indexer indexer = new Indexer();
        ResourceFinder finder = new MyFinder();
        indexer.setResourceFinder(finder);
        ExIndexParserFactory factory = new ExIndexParserFactory();
        factory.setResourceFinder(finder);
        indexer.setParserFactory(factory);

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
            assertEquals("out", expectedOut, s);
        }
    }

    /**
     * <testcase> Check that null values as parameters are accepted. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        runTest(null, null, null, "Starting the startup phase.\n"
                + "No styles to load.\n"
                + "Preparing index ...\n"
                + "No resources to load.\n"
                + "Starting the markup phase.\n");
    }

    /**
     * <testcase> Check that empty list for styles and resources as parameters
     * are accepted. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test02() throws Exception {

        runTest(makeList(), makeList(), null,
            "Starting the startup phase.\n"
                    + "No styles to load.\n"
                    + "Preparing index ...\n"
                    + "No resources to load.\n"
                    + "Starting the markup phase.\n");
    }

    /**
     * <testcase> Check that empty input produces empty output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test10() throws Exception {

        runTest(makeList(), makeList("T10.raw"), "",
            "Starting the startup phase.\n"
                    + "No styles to load.\n"
                    + "Preparing index ...\n"
                    + "Starting the processing phase.\n"
                    + "Reading T10.raw.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Starting the markup phase.\n");
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test11() throws Exception {

        runTest(makeList("style11"), makeList(), "\\begin{index}\\end{index}",
            "Starting the startup phase.\n"
                    + "Preparing index ...\n"
                    + "No resources to load.\n"
                    + "Starting the markup phase.\n");
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test12() throws Exception {

        runTest(makeList("style12"), makeList("T11.raw"),
            "\\begin{index}->aabcIV\\end{index}", null);
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void test20() throws Exception {

        runTest(makeList("style11"), makeList("T11.raw"),
            "\\begin{index}aabcIV\\end{index}", null);
    }

    /**
     * <testcase> Check that an undefined style is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = FileNotFoundException.class)
    public final void testNoResource() throws Exception {

        runTest(makeList("xyz"), makeList(), null, "");
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void testX111() throws Exception {

        runTest(makeList("style11"), makeList("T111.raw"),
            "\\begin{index}aabcIV\\end{index}", "Starting the startup phase.\n"
                    + "Preparing index ...\n"
                    + "Starting the processing phase.\n"
                    + "Reading T111.raw.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Starting the markup phase.\n");
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void testX112() throws Exception {

        runTest(makeList("style11"), makeList("T112.raw"),
            "\\begin{index}aabcIVVI\\end{index}",
            "Starting the startup phase.\n"
                    + "Preparing index ...\n"
                    + "Starting the processing phase.\n"
                    + "Reading T112.raw.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Starting the markup phase.\n");
    }

    /**
     * <testcase> Check that simple input produces simple output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void testX113() throws Exception {

        runTest(makeList("style13"), makeList("T112.raw"),
            "\\begin{index}->aabcIVVI\\end{index}",
            "Starting the startup phase.\n"
                    + "Preparing index ...\n"
                    + "Starting the processing phase.\n"
                    + "Reading T112.raw.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Starting the markup phase.\n");
    }

}
