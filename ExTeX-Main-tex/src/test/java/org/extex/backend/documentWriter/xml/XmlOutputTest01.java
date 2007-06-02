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

package org.extex.backend.documentWriter.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Properties;

import junit.framework.TestCase;

import org.extex.core.exception.helping.HelpingException;
import org.extex.main.tex.TeX;

/**
 * Test for XMLDocumentWriter 01.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XmlOutputTest01 extends TestCase {

    public void test01() throws Exception {

        String[] args = {"-fmt=../texmf/target/texmf/tex", "-out", "xml"};
        System.setIn(new ByteArrayInputStream(("\\relax\n hugo\n\\end\n")
            .getBytes()));

        String result = runTest(args, makeProperties());
        // System.out.println(result);

        // delete log file
        delete("texput.log");
        delete("texput.dvi");
        delete("texput.xml");

    }

    /**
     * Delete tmp files.
     * 
     * @param name The name of the file.
     */
    private void delete(String name) {

        File file = new File(name);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Run a test through the command line.
     * 
     * @param args the array of command line arguments
     * @param properties the properties to use
     * 
     * @return the result on the error stream
     * 
     * @throws HelpingException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    private static String runTest(String[] args, Properties properties)
            throws HelpingException,
                IOException {

        Locale.setDefault(new Locale("en"));
        properties.setProperty("extex.config", "extex.xml");

        TeX tex;
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        String result = null;
        try {
            System.setOut(new PrintStream(outBuffer));
            System.setErr(new PrintStream(errBuffer));

            tex = new TeX(properties, null);
            int status = tex.run(args);

            assertEquals(0, status);
            result = errBuffer.toString();

        } finally {
            outBuffer.close();
            System.setOut(stdout);
            errBuffer.close();
            System.setErr(stderr);
        }
        return result;
    }

    /**
     * Create a new instance of properties pre-filled with the java.version.
     * 
     * @return the new properties
     */
    private static Properties makeProperties() {

        Properties properties = new Properties();
        properties.put("extex.fonts", "../texmf/src/texmf/fonts/tfm/public/cm");
        return properties;
    }

}
