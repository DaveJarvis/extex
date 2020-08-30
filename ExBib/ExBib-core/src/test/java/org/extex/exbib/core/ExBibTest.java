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
*/
public class ExBibTest {
    
    private final static String DIR_TARGET = "build";

    /**
     * Create a file and fill it with some content.
     * 
     * @param name the name of the file
     * @param content the contents
     * 
     * @throws IOException in case of an I/O error
     */
    public static void makeFile(String name, String content) throws IOException {

        try( Writer w = new PrintWriter( new FileWriter( name ) ) ) {
            w.write( content );
        }
    }

    /**
     * The field {@code trace} contains the tracing flag.
     */
    private final boolean trace = false;

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
     *  Run plain.bst on xampl.bib 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain.bst}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertTrue("ExBib.run() failed", exBib.run());
            assertNotNull(exBib.getDebug());
            assertEquals("[]", exBib.getDebug().toString());
            assertEquals(DIR_TARGET + "/test.aux", exBib.getProperty(ExBib.PROP_FILE));
        } finally {
            File faux = new File(aux);
            if (faux.exists()) {
                assertTrue("Failed to delete " + aux, faux.delete());
            }
            String bbl = aux.replaceAll(".aux$", ".bbl");
            File fbbl = new File(bbl);
            if (fbbl.exists()) {
                assertTrue("Failed to delete " + bbl, fbbl.delete());
            }
        }
    }

    /**
     *  Run plain on xampl and test the SEARCH flag. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test2() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
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
     *  Run plain on xampl and test the SEARCH flag. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test3() throws Exception {

        ExBib exBib = new ExBib();
        assertEquals(System.getProperties(), exBib.getProperties());
    }

    /**
     *  No aux file leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testAux0() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertFalse(exBib.run());
    }

    /**
     *  An non-existent aux file leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testAux1() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        new File(aux).delete();
        Properties p = new Properties();
        p.setProperty(ExBib.PROP_FILE, aux);
        ExBib exBib = makeTestInstance(p);
        assertFalse(exBib.run());
    }

    /**
     *  A missing configuration leads to an error. 
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
     *  Debug with an undefined value leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDebug1() throws Exception {

        ExBib exBib = makeTestInstance(new Properties());
        assertFalse(exBib.setDebug(""));
    }

    /**
     *  Debug with "all" works. 
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
     *  Debug with "none" works. 
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
     *  Debug with "none" works. 
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
     *  An undefined bst file leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError2() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n" + "\\bibstyle{undefined/file.bst}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
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
     *  If a file is not found then run() fails. 
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
     *  ... 
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
     *  setFile() interacts with the properties. 
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
     *  ... 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptions1() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\biboption{min.crossref=4}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
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
     *  ... 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptions2() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\biboption{sort=42}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
            ExBib exBib = makeTestInstance(p);
            exBib.setDebug(ExBibDebug.TRACE);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     *  ... 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptions3() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\biboption{sort=locale:en}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
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
     *  setFile() interacts with the properties. 
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
     *  setFile() interacts with the properties. 
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
     *  setFile() interacts with the properties. 
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
     *  ... 
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSetLogger1() throws Exception {

        ExBib exBib = new ExBib(new Properties());
        exBib.setLogger(null);
    }

    /**
     *  Run plain on xampl and test the TRACE flag. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTrace1() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
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
     *  Validation test: missing all special macros. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate0() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\relax\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     *  Validation test: missing \citation. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate1() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\bibstyle{src/test/resources/bibtex/base/plain.bst}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     *  Validation test: missing \bibstyle. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate2() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }

    /**
     *  Validation test: missing \bibdata. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testValidate3() throws Exception {

        String aux = DIR_TARGET + "/test.aux";
        makeFile(aux, "\\citation{*}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain.bst}\n");

        try {
            Properties p = new Properties();
            p.setProperty(ExBib.PROP_FILE, DIR_TARGET + "/test.aux");
            ExBib exBib = makeTestInstance(p);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            // assertTrue("Failed to delete " + aux, new File(aux).delete());
        }
    }
}
