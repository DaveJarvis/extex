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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public LatexTaskTest(String name) {

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
            w
                .write("<project name=\"ant-test\">\n"
                        + "  <taskdef name=\"LaTeX\"\n"
                        + "           classname=\"org.extex.ant.latex.LatexTask\"\n"
                        + "           classpath=\"target/classes\" />\n"
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
        assertEquals("Message was logged but should not.", log, //
            getLog().replaceAll("\\r", ""));
        build.delete();
    }

    /**
     * Test method for {@link org.extex.exbib.ant.ExBibTask#execute()}.
     * 
     * @throws IOException in case of an I/O error
     */
    public final void test01() throws IOException {

        try {
            runTest("<LaTeX/>", null, "");
        } catch (BuildException e) {
            assertEquals("message", //
                "master file parameter missing", e.getMessage());
        }
    }

}
