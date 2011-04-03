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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildFileTest;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * This is a test suite for the sitebuilder Ant task.
 * 
 * <p>
 * <b>Note:</b> the run() method creates a temporary Ant build file in target/.
 * Thus all path names are relative to this directory!
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
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
     * Test method for
     * {@link org.extex.SiteBuilder.ant.SiteBuilderTask#execute()}.
     * 
     * @throws Exception in case of an error
     */
    public final void _testTemplate1() throws Exception {

        try {
            runTest("<SiteBuilder\n" //
                    + "  template=\"~/file/which/does/not/exist\"/>\n", //
                "");
            assertTrue(false);
        } catch (BuildException e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof ResourceNotFoundException);
        }
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
        if (log != null) {
            assertEquals("Message was logged but should not.", log, //
                getLog().replaceAll("\\r", ""));
        }
    }

    /**
     * <testcase> An invocation with all possible flags succeeds. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public final void test01() throws Exception {

        runTest("<SiteBuilder"
                + "    output=\"test-site\""
                + "    logLevel=\"WARNING\" >\n" //
                + "  <omit>RCS</omit>\n"
                + "  <lib>org/extex/sitebuilder/macros.vm</lib>\n"
                + "  <News output=\"test-site/rss/2.0/news.rss\" "
                + "        template=\"org/extex/sitebuilder/news.vm\"\n" //
                + "        max=\"3\"/>\n"
                + "  <Tree dir=\"../src/test/resources/empty-site\""
                + "        template=\"org/extex/sitebuilder/site.vm\"/>\n" //
                + "  <Tree dir=\"../src/test/resources/test-site-1\""
                + "        processHtml=\"true\"/>\n" //
                + "  <Sitemap output=\"test-site/sitemap.html\""
                + "        template=\"org/extex/sitebuilder/sitemap.vm\"/>\n"
                + "</SiteBuilder>", //
            null);
        assertTrue(new File("target/test-site").exists());
        assertFalse(new File("target/test-site/RCS").exists());
        assertTrue(new File("target/test-site/index.html").exists());
        assertTrue(new File("target/test-site/sitemap.html").exists());
        assertTrue(new File("target/test-site/robots.txt").exists());
    }

    /**
     * <testcase> An empty sitebuilder specification does no harm. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public final void test02() throws Exception {

        runTest("<SiteBuilder/>\n", "");
    }

    /**
     * <testcase> An illegal base dir in a tree leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public final void testTree01() throws Exception {

        try {
            runTest("<SiteBuilder>\n" //
                    + "  <Tree dir=\"site/base/dir\" />\n" //
                    + "</SiteBuilder>", //
                "");
            assertTrue("Unexpected success", false);
        } catch (Exception e) {
            assertTrue(e.getMessage(),
                e.getMessage().contains("java.io.FileNotFoundException"));
        }
    }

    /**
     * <testcase> a missing base dir for a tree leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public final void testTree02() throws Exception {

        try {
            runTest("<SiteBuilder>\n" //
                    + "  <Tree />\n" //
                    + "</SiteBuilder>", //
                null);
        } catch (BuildException e) {
            assertTrue(e.getCause().toString(), e.getCause().toString()
                .contains("Missing base"));
        }
    }
}
