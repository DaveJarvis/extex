/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildFileTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * This is a test suite for the <logo>&epsilon;&chi;Bib</logo> Ant task.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibTaskTest extends BuildFileTest {

    /**
     * Creates a new object.
     *
     * @param name the name
     */
    public ExBibTaskTest(String name) {

        super(name);
    }

    /**
     * Run a test.
     *
     * @param invocation the invocation XML
     * @param aux the contents of the aux file
     * @param log the contents of the log stream
     *
     * @throws IOException in case of an I/O error during writing a temp file
     */
    private void runTest(String invocation, String aux, String log)
            throws IOException {

        File build = new File("target/build.xml");
        FileWriter w = new FileWriter(build);
        try {
            w.write("<project name=\"ant-test\">\n"
                    + "  <taskdef name=\"ExBib\"\n"
                    + "           classname=\"org.extex.exbib.ant.ExBibTask\"\n"
                    + "           classpath=\"classes\" />\n"
                    + "  <target name=\"test.case\"\n"
                    + "          description=\"...\" >\n" + "");
            w.write(invocation);
            w.write("  </target>\n");
            w.write("</project>\n");
        } finally {
            w.close();
        }

        if (aux != null) {
            w = new FileWriter("target/test.aux");
            try {
                w.write(aux);
            } finally {
                w.close();
            }
        }
        Locale.setDefault(Locale.ENGLISH);
        configureProject("target/build.xml");
        executeTarget("test.case");
        assertEquals("Message was logged but should not.",
            log.replaceAll("\\n", ""),
            getLog().replaceAll("\\r", "").replaceAll("\\n", ""));
        build.deleteOnExit();
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     */
    public final void test01() {

        configureProject("src/test/resources/build.xml");
        Locale.setDefault(Locale.ENGLISH);
        executeTarget("test.case.1");
        assertEquals("Message was logged but should not.",
            "Missing aux file parameter."
                    + "(There was 1 error)",
            getLog().replaceAll("[\\r\\n]", ""));
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     */
    public final void test02() {

        configureProject("src/test/resources/build.xml");
        Locale.setDefault(Locale.ENGLISH);
        executeTarget("test.case.2");
        assertEquals("Message was logged but should not.",
                     "I couldn't open file file/which/does/not/exist.aux"
                    + "(There was 1 error)",
            getLog().replaceAll("[\\r\\n]", ""));
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     *
     * @throws Exception in case of an error
     */
    public final void test11() throws Exception {

        runTest("<ExBib\n"
                + "  file=\"file/which/does/not/exist.aux\" />\n",
            null,
                "I couldn't open file file/which/does/not/exist.aux\n"
                    + "(There was 1 error)\n");
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     *
     * @throws Exception in case of an error
     */
    public final void test12() throws Exception {

        runTest("<ExBib\n"
                + "  minCrossrefs=\"abc\" \n"
                + "  file=\"file/which/does/not/exist\"/>\n",
            null,
                "I found `abc' instead of the expected number\n"
                    + "(There was 1 error)\n");
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     *
     * @throws Exception in case of an error
     */
    public final void test13() throws Exception {

        runTest("<ExBib\n"
                + "  logfile=\"target/log.log\" \n"
                + "  file=\"file/which/does/not/exist\"/>\n",
            null,
                "I couldn't open file file/which/does/not/exist.aux\n"
                    + "(There was 1 error)\n");
        File log = new File("target/log.log");
        assertTrue(log.exists());
        log.deleteOnExit();
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     *
     * @throws Exception in case of an error
     */
    public final void test21() throws Exception {

        runTest(
            "<ExBib>\n"
                    + "  exbib.file=file/which/does/not/exist.aux\n"
                    + "</ExBib>\n",
            null,
            "I couldn't open file file/which/does/not/exist.aux\n"
                    + "(There was 1 error)\n");
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     *
     * @throws Exception in case of an error
     */
    public final void test22() throws Exception {

        try {
            runTest("<ExBib\n"
                    + "  load=\"file/which/does/not/exist\"/>\n",
                null,
                "");
          fail();
        } catch (BuildException e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof FileNotFoundException);
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     *
     * @throws Exception in case of an error
     */
    public final void test23() throws Exception {

        try {
            runTest("<ExBib\n"
                    + "  load=\"~/file/which/does/not/exist\"/>\n",
                null,
                "");
          fail();
        } catch (BuildException e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof FileNotFoundException);
        }
    }

}
