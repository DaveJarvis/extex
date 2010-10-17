/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.extex.exbib.bsf.BsfProcessor;
import org.extex.exbib.bst2groovy.parameters.Parameter;
import org.extex.exbib.bst2groovy.parameters.ParameterType;
import org.extex.exbib.core.ExBib;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;
import org.junit.Test;

/**
 * This is a test suite for the {@link BsfProcessor} with a Groovy
 * configuration. It compiles a bst into a groovy program and uses this groovy
 * program on xampl.bib. Finally it compares the result with the result produced
 * by BibTeX.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class XamplTest {

    /**
     * Read the contents of a file and translate LF to nothing.
     * 
     * @param name the name
     * 
     * @return the contents of the file
     * 
     * @throws IOException in case of an I/O error
     */
    public static String getFileContents(String name) throws IOException {

        StringBuilder buffer = new StringBuilder();
        FileReader r = new FileReader(name);

        try {
            for (int c = r.read(); c >= 0; c = r.read()) {
                if (c != '\r') {
                    buffer.append((char) c);
                }
            }
        } finally {
            r.close();
        }

        return buffer.toString();
    }

    /**
     * Create a file in the target directory and fill it with some content.
     * 
     * @param name the name of the file
     * @param postfix the string to append
     * @param content the contents
     * 
     * @return the file
     * 
     * @throws IOException in case of an I/O error
     */
    public static File makeFile(String name, String postfix, String content)
            throws IOException {

        File file = File.createTempFile(name, postfix, new File("target"));
        Writer w = new FileWriter(file);
        try {
            w.write(content);
        } finally {
            w.close();
        }
        return file;
    }

    /**
     * Run a test.
     * 
     * @param bib the bib file
     * @param aux the aux file
     * @param expectedErr the expected error output
     * @param expectedOut the expected output
     * 
     * @throws IOException in case of an I/O error
     */
    private static void run(File bib, File aux, String expectedErr,
            String expectedOut) throws IOException {

        Properties prop = new Properties();
        prop.put(ExBib.PROP_PROCESSOR, "groovy");
        prop.put(ExBib.PROP_FILE, aux.toString());
        prop.put(ExBib.PROP_OUTFILE, "target/xampl.out");

        ByteArrayOutputStream errStream = new ByteArrayOutputStream();

        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        Handler handler = new StreamHandler(errStream, new LogFormatter());
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);

        PrintStream err = System.err;
        try {
            System.setErr(new PrintStream(errStream));
            ExBib exBib = new ExBib(prop);
            exBib.setLogger(logger);
            boolean code = exBib.run();
            handler.flush();
            assertEquals("error stream", expectedErr, errStream.toString()
                .replaceAll("\r", ""));
            assertTrue("ExBib has signaled a failure", code);
            System.out.flush();
            assertEquals("output stream", expectedOut, //
                getFileContents(prop.get(ExBib.PROP_OUTFILE).toString()));
        } finally {
            logger.removeHandler(handler);
            System.setErr(err);
            if (aux.exists()) {
                assertTrue(aux.toString() + ": deletion failed", aux.delete());
            }
            File bbl = new File(aux.toString().replaceAll(".aux$", ".bbl"));
            if (bbl.exists()) {
                assertTrue(bbl.toString() + ": deletion failed", bbl.delete());
            }
        }
    }

    /**
     * Get the content of a resource.
     * 
     * @param name the name of the resource
     * 
     * @return the content of the resource
     * 
     * @throws IOException in case of an I/O error
     */
    private String getResource(String name) throws IOException {

        StringBuilder buffer = new StringBuilder();
        InputStream s = getClass().getClassLoader().getResourceAsStream(name);
        if (s == null) {
            throw new FileNotFoundException(name);
        }
        try {
            for (int c = s.read(); c >= 0; c = s.read()) {
                buffer.append((char) c);
            }
        } finally {
            s.close();
        }
        return buffer.toString();
    }

    /**
     * Run the test case.
     * 
     * @param name the name of the groovy file in target
     * 
     * @throws Exception in case of an error
     */
    private void run(String name) throws Exception {

        String fileName = "target/" + name.toLowerCase() + ".groovy";

        Logger logger = Logger.getLogger(getClass().getName());
        logger.setLevel(Level.SEVERE);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        try {
            ResourceFinder finder =
                    new ResourceFinderFactory().createResourceFinder(
                        ConfigurationFactory
                            .newInstance("config/path/testFinder"), logger,
                        System.getProperties(), null);
            Bst2Groovy bst2Groovy = new Bst2Groovy();
            // bst2Groovy.setParameter(ParameterType.OPTIMIZE, Parameter.FALSE);
            bst2Groovy.setResourceFinder(finder);
            bst2Groovy.setParameter(ParameterType.STYLE_NAME, //
                new Parameter(name));
            FileWriter w = new FileWriter(fileName);
            try {
                bst2Groovy.run(w, name.toLowerCase() + ".bst");
            } finally {
                w.close();
            }
        } finally {
            logger.removeHandler(handler);
        }

        File bib = new File("src/test/resources/xampl.bib");
        run(bib, makeFile("test1", ".aux", //
            "\\citation{*}\n" //
                    + "\\bibstyle{" + fileName + "}\n" //
                    + "\\bibdata{" + bib + "}\n"), //
            "empty author in whole-journal\n"
                    + "empty title in whole-journal\n", //
            getResource("xampl-" + name.toLowerCase() + ".bbl"));
    }

    /**
     * <test> Apply abbrv.groovy on xampl.bib </test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAbbrv() throws Exception {

        run("Abbrv");
    }

    /**
     * <test> Apply alpha.groovy on xampl.bib </test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAlpha() throws Exception {

        run("Alpha");
    }

    /**
     * <test> Apply plain.groovy on xampl.bib </test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlain() throws Exception {

        run("Plain");
    }

    /**
     * <test> Apply unsrt.groovy on xampl.bib </test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnsrt() throws Exception {

        run("Unsrt");
    }

}
