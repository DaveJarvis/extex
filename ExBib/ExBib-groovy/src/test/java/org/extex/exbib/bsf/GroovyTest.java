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

package org.extex.exbib.bsf;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.ExBib;
import org.junit.Test;

/**
 * This is a test suite for the {@link BsfProcessor} with a Groovy
 * configuration.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GroovyTest {

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
     * <test> TODO gene: missing JavaDoc </test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        String gy = "target/test.gy";
        makeFile(gy, "println(exbibDB.getEntries())\n");
        String bib = "target/test.bib";
        makeFile(bib,
            "@book{abc,author={Donald E. Knuth, title={The {\\TeX}book}}}\n");
        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n\\bibstyle{" + gy
                + "}\n\\bibdata{target/test.bib}\n");
        try {
            Properties prop = new Properties();
            prop.put(ExBib.PROP_PROCESSOR, "groovy");
            prop.put(ExBib.PROP_FILE, aux);
            ExBib exBib = new ExBib(prop);
            Logger logger = Logger.getLogger("test");
            logger.setLevel(Level.WARNING);
            exBib.setLogger(logger);
            assertTrue(exBib.run());
        } finally {
            new File(aux).delete();
            new File(gy).delete();
            new File(bib).delete();
        }
    }
}
