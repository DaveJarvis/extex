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

package org.extex.exbib.bsf.jacl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.extex.exbib.bsf.BsfProcessor;
import org.extex.exbib.bsf.LogFormatter;
import org.extex.exbib.core.ExBib;
import org.junit.Test;

/**
 * This is a test suite for the {@link BsfProcessor} with a Jacl configuration.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class JaclTest {

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
     * <test> trivial test case </test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    // @Ignore
    public void test1() throws Exception {

        String tcl = "target/test.tcl";
        makeFile(tcl, "package require java\n" //
                + "puts \"DB:\"\n" //
                + "puts [bsf bibDB getEntries ]\n");
        // TODO: this seems not to work yet
        String bib = "target/test.bib";
        makeFile(bib,
            "@book{abc,author={Donald E. Knuth, title={The {\\TeX}book}}}\n");
        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n\\bibstyle{" + tcl
                + "}\n\\bibdata{target/test.bib}\n");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        Logger logger = Logger.getLogger(getClass().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        Handler handler = new StreamHandler(errStream, new LogFormatter());
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        ConsoleHandler handler2 = new ConsoleHandler();
        handler2.setFormatter(new LogFormatter());
        logger.addHandler(handler2);
        PrintStream out = System.out;
        try {
            System.setOut(new PrintStream(outStream));
            Properties prop = new Properties();
            prop.put(ExBib.PROP_PROCESSOR, "jacl");
            prop.put(ExBib.PROP_FILE, aux);
            ExBib exBib = new ExBib(prop);
            exBib.setLogger(logger);
            boolean code = exBib.run();
            System.err.flush();
            assertEquals("", errStream.toString().replaceAll("\r", ""));
            assertTrue("ExBib.run() failed", code);
            assertEquals("[@book{abc,...}]\n",
                outStream.toString().replaceAll("\r", ""));
        } finally {
            System.setOut(out);
            new File(aux).delete();
            new File(aux.replaceAll(".aux$", ".bbl")).delete();
            new File(tcl).delete();
            new File(bib).delete();
        }
    }
}
