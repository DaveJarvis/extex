/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            ExBib exBib = new ExBib(p);
            Logger logger = Logger.getLogger("test");
            logger.setLevel(Level.SEVERE);
            exBib.setLogger(logger);
            assertTrue(exBib.run());
        } finally {
            assertTrue(new File(aux).delete());
            assertTrue(new File(aux.replaceAll(".aux$", ".bbl")).delete());
        }
    }

    /**
     * <testcase> An undefined aux file leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError1() throws Exception {

        String aux = "target/test.aux";
        new File(aux).delete();
        Properties p = new Properties();
        p.setProperty(ExBib.PROP_FILE, aux);
        ExBib exBib = new ExBib(p);
        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.SEVERE);
        exBib.setLogger(logger);
        assertFalse(exBib.run());
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
            ExBib exBib = new ExBib(p);
            Logger logger = Logger.getLogger("test");
            logger.setLevel(Level.SEVERE);
            exBib.setLogger(logger);
            assertFalse(exBib.run());
        } finally {
            new File(aux).delete();
            new File(aux.replaceAll(".aux$", ".bbl")).delete();
        }
    }
}
