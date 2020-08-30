/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.ant.latex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildFileTest;

/**
 * This is a test suite for the LaTeX ant task.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 7598 $
 */
public class LatexTaskTest extends BuildFileTest {

    private static final String DIR_TARGET = "build";

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public LatexTaskTest(String name) {

        super(name);
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void _test20() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName,
            "\\documentclass{article}\n"
                    + "\\begin{document}\n"
                    + "\\end{document}\n");
        try {
            runTest("<LaTeX master=\"" + fileName + "\"/>", "");
        } finally {
            f.delete();
        }
    }

    /**
     * Create a file and write some contents to it.
     * 
     * @param fileName the name of the file
     * @param text the contents
     * @return the file created
     * @throws IOException in case of an I/O error
     */
    private File mkfile(String fileName, String text) throws IOException {

        File f = new File(DIR_TARGET, fileName);
        Writer w = new BufferedWriter(new FileWriter(f));
        w.append(text);
        w.close();
        return f;
    }

    /**
     * Run a test.
     * 
     * @param invocation the invocation XML
     * @param log the contents of the log stream
     * 
     * @throws IOException in case of an I/O error during writing a temp file
     */
    private void runTest(String invocation, String log) throws IOException {

        File build = new File(DIR_TARGET + "/build.xml");
        FileWriter w = new FileWriter(build);
        try {
            w
                .write("<project name=\"ant-test\">\n"
                        + "  <taskdef name=\"LaTeX\"\n"
                        + "           classname=\"org.extex.ant.latex.LatexTask\"\n"
                        + "           classpath=\DIR_TARGET + "/classes\" />\n"
                        + "  <target name=\"test.case\"\n"
                        + "          description=\"...\" >\n" + "");
            w.write(invocation);
            w.write("  </target>\n");
            w.write("</project>\n");
        } finally {
            w.close();
        }

        Locale.setDefault(Locale.ENGLISH);
        configureProject(DIR_TARGET + "/build.xml");
        expectOutput("test.case", "");
        if (log != null) {
            assertEquals("Message was logged but should not.",
                log,
                getLog().replaceAll("\\r", ""));
        }
        build.delete();
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

        FileWriter w;

        if (aux != null) {
            w = new FileWriter(DIR_TARGET + "/test.aux");
            try {
                w.write(aux);
            } finally {
                w.close();
            }
        }
        runTest(invocation, log);
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void test01() throws IOException {

        try {
            runTest("<LaTeX/>", "");
            assertTrue("unexpected success", false);
        } catch (BuildException e) {
            assertEquals("message",
                "master file argument missing", e.getMessage());
        } finally {
            new File(DIR_TARGET, "abc.aux").delete();
            new File(DIR_TARGET, "abc.log").delete();
            new File(DIR_TARGET, "abc.bbl").delete();
            new File(DIR_TARGET, "abc.blg").delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void test02() throws IOException {

        String fileName = "file_does_not_exist";
        try {
            runTest("<LaTeX master=\"" + fileName + "\"/>", "");
            assertTrue("unexpected success", false);
        } catch (BuildException e) {
            assertEquals("message",
                "master file " + new File(DIR_TARGET, fileName).getAbsoluteFile()
                        + " not found", e.getMessage());
        } finally {
            new File(DIR_TARGET, "abc.aux").delete();
            new File(DIR_TARGET, "abc.log").delete();
            new File(DIR_TARGET, "abc.bbl").delete();
            new File(DIR_TARGET, "abc.blg").delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void test03() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName, "");
        try {
            runTest("<LaTeX master=\"" + fileName + "\">"
                    + "</LaTeX>", null);
            assertTrue("unexpected success", false);
        } catch (BuildException e) {
            assertTrue(true);
        } finally {
            f.delete();
            new File(DIR_TARGET, "abc.aux").delete();
            new File(DIR_TARGET, "abc.log").delete();
            new File(DIR_TARGET, "abc.bbl").delete();
            new File(DIR_TARGET, "abc.blg").delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void test04() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName,
            "\\documentclass{article}\n"
                    + "\\begin{document}\n"
                    + "\\end{document}\n");
        try {
            runTest("<LaTeX master=\"" + fileName + "\">"
                    + "<File/>"
                    + "</LaTeX>", null);
        } catch (BuildException e) {
            assertTrue("Exception: " + e.toString(),
                e.toString().contains("I found no \\citation commands"));
        } finally {
            f.delete();
            new File(DIR_TARGET, "abc.aux").delete();
            new File(DIR_TARGET, "abc.log").delete();
            new File(DIR_TARGET, "abc.bbl").delete();
            new File(DIR_TARGET, "abc.blg").delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void test05() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName,
            "\\documentclass{article}\n"
                    + "\\begin{document}\n"
                    + "\\bibliography{abc}"
                    + "\\bibliographystyle{plain}"
                    + "\\nocite{*}"
                    + "\\end{document}\n");
        try {
            runTest("<LaTeX master=\"" + fileName + "\">"
                    + "<File/>"
                    + "</LaTeX>", null);
        } catch (BuildException e) {
            assertTrue("Exception: " + e.toString(),
                e.toString().contains("I couldn't open database file "));
        } finally {
            f.delete();
            new File(DIR_TARGET, "abc.aux").delete();
            new File(DIR_TARGET, "abc.log").delete();
            new File(DIR_TARGET, "abc.bbl").delete();
            new File(DIR_TARGET, "abc.blg").delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void testDependency01() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName,
            "\\documentclass{article}\n"
                    + "\\begin{document}\n"
                    + "\\bibliography{abc}"
                    + "\\bibliographystyle{plain}"
                    + "\\nocite{*}"
                    + "\\end{document}\n");
        try {
            runTest("<LaTeX master=\"" + fileName + "\">"
                    + "<file name=\"xyz\"/>" + "</LaTeX>", null);
        } catch (BuildException e) {
            assertTrue("Exception: " + e.toString(),
                e.toString().contains("unknown output format"));
        } finally {
            f.delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void testProp01() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName, "");
        try {
            runTest("<LaTeX master=\"" + fileName + "\">"
                    + "<property/>"
                    + "</LaTeX>", null);
        } catch (BuildException e) {
            assertTrue("Exception: " + e.toString(),
                e.toString().contains("property without name"));
        } finally {
            f.delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void testProp02() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName, "");
        try {
            runTest("<LaTeX master=\"" + fileName + "\">"
                    + "<property name=\"abc\"/>"
                    + "</LaTeX>", null);
        } catch (BuildException e) {
            assertTrue("Exception: " + e.toString(),
                e.toString().contains("has no value"));
        } finally {
            f.delete();
        }
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void testProp03() throws IOException {

        String fileName = "abc.tex";
        File f = mkfile(fileName, "");
        try {
            runTest("<LaTeX master=\"" + fileName
                    + "\">"
                    + "<property name=\"latex.output.format\" value=\"xxx\"/>"
                    + "</LaTeX>", null);
        } catch (BuildException e) {
            assertTrue("Exception: " + e.toString(),
                e.toString().contains("unknown output format"));
        } finally {
            f.delete();
        }
    }

}
