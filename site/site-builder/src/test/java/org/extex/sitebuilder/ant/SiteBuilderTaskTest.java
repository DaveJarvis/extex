/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildFileTest;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * This is a test suite for the &epsilon;&chi;Bib ant task.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 8301 $
 */
public class SiteBuilderTaskTest extends BuildFileTest {

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public SiteBuilderTaskTest(String name) {

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
                    + "  <taskdef name=\"SiteBuilder\"\n"
                    + "           classname=\"org.extex.sitebuilder.ant.SiteBuilderTask\"\n"
                    + "           classpath=\"classes\" />\n"
                    + "  <target name=\"test.case\"\n"
                    + "          description=\"...\" >\n" + "");
            w.write(invocation);
            w.write("  </target>\n");
            w.write("</project>\n");
        } finally {
            w.close();
            build.deleteOnExit();
        }

        Locale.setDefault(Locale.ENGLISH);
        configureProject(build.toString());
        executeTarget("test.case");
        assertEquals("Message was logged but should not.", log, //
            getLog().replaceAll("\\r", ""));
    }

    /**
     * Test method for
     * {@link org.extex.SiteBuilder.ant.SiteBuilderTask#execute()}.
     */
    public final void t_est01() {

        configureProject("src/test/build.xml");
        Locale.setDefault(Locale.ENGLISH);
        executeTarget("test.case.1");
        // assertEquals("Message was logged but should not.",
        // "Missing aux file parameter.\n" + "(There was 1 error)\n",//
        // getLog().replaceAll("\\r", ""));
    }

    /**
     * Test method for
     * {@link org.extex.SiteBuilder.ant.SiteBuilderTask#execute()}.
     */
    public final void t_est02() {

        configureProject("src/test/resources/build.xml");
        Locale.setDefault(Locale.ENGLISH);
        executeTarget("test.case.2");
        assertEquals("Message was logged but should not.",
            "I couldn\'t open file file/which/does/not/exist.aux\n"
                    + "(There was 1 error)\n",//
            getLog().replaceAll("\\r", ""));
    }

    public final void test001() throws Exception {

        try {
            runTest("<SiteBuilder\n" //
                    + "  template=\"~/file/which/does/not/exist\">\n"
                    + "  <omit>RCS</omit>\n"
                    + "  <SiteMap output=\"site/map/file\" />\n"
                    + "  <SiteNews output=\"site/map/file\" max=\"3\"/>\n"
                    + "  <SiteBase template=\"t_e_m_p_l_a_t_e\" "
                    + "dir=\"site/map/file\"" + " />\n" + "</SiteBuilder>", //
                null, //
                "");
            assertFalse(true);
        } catch (BuildException e) {
            assertTrue(e.getCause().toString(),
                e.getCause() instanceof FileNotFoundException);
        }
    }

    /**
     * Test method for
     * {@link org.extex.SiteBuilder.ant.SiteBuilderTask#execute()}.
     * 
     * @throws Exception in case of an error
     */
    public final void testTemplate1() throws Exception {

        try {
            runTest("<SiteBuilder\n" //
                    + "  template=\"~/file/which/does/not/exist\"/>\n", //
                null, //
                "");
            assertTrue(false);
        } catch (BuildException e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof ResourceNotFoundException);
        }
    }
}
