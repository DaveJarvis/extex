/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.ExBib.ExBibDebug;
import org.extex.exbib.core.bst.code.StoringHandler;
import org.junit.Test;

/**
 * This is a test suite for {@link ExBib}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibTest {

    /**
     * Create a file and fill it with some content.
     * 
     * @param name the name of the file
     * @param content the contents
     * 
     * @throws IOException in case of an I/O error
     */
    public static void makeFile(String name, String content) throws IOException {

        Writer w = new PrintWriter(new FileWriter(name));
        try {
            w.write(content);
        } finally {
            w.close();
        }
    }

    /**
     * The field <tt>trace</tt> contains the tracing flag.
     */
    private boolean trace = false;

    /**
     * Make a test instance.
     * 
     * @param p the properties
     * 
     * @return the test instance
     * 
     * @throws IOException in case of an error
     */
    private ExBib makeTestInstance(Properties p) throws IOException {

        Logger logger = Logger.getLogger(getClass().getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.SEVERE);

        if (trace) {
            for (Handler h : logger.getHandlers()) {
                logger.removeHandler(h);
            }

            Handler h = new ConsoleHandler();
            h.setLevel(Level.ALL);
            logger.addHandler(h);
        }

        ExBib exBib = new ExBib(p);
        exBib.setLogger(logger);
        return exBib;
    }

    /**
     * <testcase> Run plain.bst on xampl.bib </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain.bst}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertTrue("ExBib.run() failed", exBib.run());
            assertNotNull(exBib.getDebug());
            assertEquals("[]", exBib.getDebug().toString());
            assertEquals("target/test.aux", exBib.getProperty(ExBib.PROP_FILE));
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
            String bbl = aux.replaceAll(".aux$", ".bbl");
            new File(bbl).delete();
            // assertTrue("Failed to delete " + bbl, new File(bbl).delete());
        }
    }

    /**
     * <testcase> Run plain on xampl and test the SEARCH flag. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test2() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            exBib.setDebug(ExBibDebug.SEARCH);
            assertTrue("ExBib.run() failed", exBib.run());
            assertNotNull(exBib.getDebug());
            assertEquals("[SEARCH]", exBib.getDebug().toString());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
            String bbl = aux.replaceAll(".aux$", ".bbl");
            new File(bbl).delete();
            // assertTrue("Failed to delete " + bbl, new File(bbl).delete());
        }
    }

    /**
     * <testcase> Run plain on xampl and test the SEARCH flag. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test3() throws Exception {

        ExBib exBib = new ExBib();
        assertEquals(System.getProperties(), exBib.getProperties());
    }

    /**
     * <testcase> No aux file leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testAux0() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertFalse(exBib.run());
    }

    /**
     * <testcase> An non-existent aux file leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testAux1() throws Exception {

        String aux = "target/test.aux";
        new File(aux).delete();
        Properties p = new Properties();
        p.setProperty(ExBib.PROP_FILE, aux);
        ExBib exBib = makeTestInstance(p);
        assertFalse(exBib.run());
    }

    /**
     * <testcase> A missing configuration leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testConfig1() throws Exception {

        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_CONFIG, "non-existent-configuration");
        ExBib exBib = makeTestInstance(properties);
        assertFalse(exBib.run());
    }

    /**
     * <testcase> Debug with an undefined value leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDebug1() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertFalse(exBib.setDebug(""));
    }

    /**
     * <testcase> Debug with "all" works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDebug2() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertTrue(exBib.setDebug("all"));
        for (ExBibDebug d : ExBibDebug.values()) {
            assertTrue(exBib.getDebug().contains(d));
        }
    }

    /**
     * <testcase> Debug with "none" works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDebug3() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertTrue(exBib.setDebug("none"));
        for (ExBibDebug d : ExBibDebug.values()) {
            assertFalse(d.toString(), exBib.getDebug().contains(d));
        }
    }

    /**
     * <testcase> Debug with "none" works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDebug4() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertTrue(exBib.setDebug("trace"));
        for (ExBibDebug d : ExBibDebug.values()) {
            if (d == ExBibDebug.TRACE) {
                assertTrue(d.toString(), exBib.getDebug().contains(d));
            } else {
                assertFalse(d.toString(), exBib.getDebug().contains(d));
            }
        }
    }

    /**
     * <testcase> An undefined bst file leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError2() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n" + "\\bibstyle{undefined/file.bst}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
            String bbl = aux.replaceAll(".aux$", ".bbl");
            new File(bbl).delete();
            // assertTrue("Failed to delete " + bbl, new File(bbl).delete());
        }
    }

    /**
     * <testcase> If a file is not found then run() fails. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testExBibUnknownFile1() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        exBib.setFile("non-existent-file-name");
        assertFalse(exBib.run());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLog1() throws Exception {

        ExBib exBib = new ExBib(new Properties());

        Logger logger = Logger.getLogger(getClass().getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.WARNING);
        for (Handler h : logger.getHandlers()) {
            logger.removeHandler(h);
        }
        StoringHandler h = new StoringHandler();
        h.setLevel(Level.ALL);
        logger.addHandler(h);

        try {
            exBib.setLogger(logger);
            assertFalse(exBib.log(Level.SEVERE, "xyzzy"));
            assertEquals("???xyzzy???", h.toString());
        } finally {
            logger.removeHandler(h);
        }
    }

    /**
     * <testcase> setFile() interacts with the properties. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testMinCrossref1() throws Exception {

        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_MIN_CROSSREF, "xxx");
        ExBib exBib = makeTestInstance(properties);
        assertFalse(exBib.run());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptions1() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n" //
                + "\\biboption{min.crossref=4}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            exBib.setDebug(ExBibDebug.TRACE);
            assertTrue("ExBib.run() failed", exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
            String bbl = aux.replaceAll(".aux$", ".bbl");
            new File(bbl).delete();
            // assertTrue("Failed to delete " + bbl, new File(bbl).delete());
        }
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptions2() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n" //
                + "\\biboption{sort=42}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            exBib.setDebug(ExBibDebug.TRACE);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptions3() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n" //
                + "\\biboption{sort=locale:en}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            exBib.setDebug(ExBibDebug.TRACE);
            assertTrue(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
            String bbl = aux.replaceAll(".aux$", ".bbl");
            new File(bbl).delete();
            // assertTrue("Failed to delete " + bbl, new File(bbl).delete());
        }
    }

    /**
     * <testcase> setFile() interacts with the properties. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSetFile0() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertFalse(exBib.setFile(""));
        assertNull(exBib.getProperty(ExBib.PROP_FILE));
    }

    /**
     * <testcase> setFile() interacts with the properties. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSetFile1() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertTrue(exBib.setFile("abc"));
        assertEquals("abc", exBib.getProperty(ExBib.PROP_FILE));
    }

    /**
     * <testcase> setFile() interacts with the properties. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSetFile2() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertTrue(exBib.setFile("abc"));
        assertFalse(exBib.setFile("def"));
        assertEquals("abc", exBib.getProperty(ExBib.PROP_FILE));
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLogger1() throws Exception {

        ExBib exBib = new ExBib(new Properties());
        exBib.setLogger(null);
    }

    /**
     * <testcase> Run plain on xampl and test the TRACE flag. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTrace1() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            exBib.setDebug(ExBibDebug.TRACE);
            assertTrue(exBib.run());
            assertNotNull(exBib.getDebug());
            assertEquals("[TRACE]", exBib.getDebug().toString());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
            String bbl = aux.replaceAll(".aux$", ".bbl");
            new File(bbl).delete();
            // assertTrue("Failed to delete " + bbl, new File(bbl).delete());
        }
    }

    /**
     * <testcase> Validation test: missing all special macros. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate0() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\relax\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     * <testcase> Validation test: missing \citation. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate1() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\bibstyle{src/test/resources/bibtex/base/plain.bst}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     * <testcase> Validation test: missing \bibstyle. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate2() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     * <testcase> Validation test: missing \bibdata. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate3() throws Exception {

        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain.bst}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, "target/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }
}
