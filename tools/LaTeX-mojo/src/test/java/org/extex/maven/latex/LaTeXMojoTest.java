/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.maven.latex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for {@link LaTeXMojo}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LaTeXMojoTest extends AbstractMojoTestCase {

    /**
     * The field <tt>basedir</tt> contains the base directory.
     */
    private String basedir = getBasedir().replace('\\', '/');

    /**
     * Test method for {@link org.extex.maven.latex.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void _test2() throws Exception {

        rmdir("target/doc");
        LaTeXMojo mojo = new LaTeXMojo();
        mojo.setFile(new File("src/test/resources/document2.tex"));
        StringBuilder buffer = new StringBuilder();
        mojo.setLog(new TLog(buffer));
        mojo.execute();
    }

    /**
     * Test method for {@link org.extex.maven.latex.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void _test3() throws Exception {

        rmdir("target/doc");
        LaTeXMojo mojo = new LaTeXMojo();
        mojo.setFile(new File("src/test/resources/document3.tex"));
        StringBuilder buffer = new StringBuilder();
        mojo.setLog(new TLog(buffer));
        mojo.execute();
    }

    /**
     * Create a mojo to be tested an feed it with a configuration.
     * 
     * @param pom the name of the POM
     * 
     * @return the mojo to be tested
     * 
     * @throws Exception in case of an error
     */
    private LaTeXMojo makeMojo(File pom) throws Exception {

        rmdir("target/doc");
        PlexusConfiguration configuration =
                extractPluginConfiguration("maven-latex-plugin", pom);
        assertNotNull(configuration);
        LaTeXMojo mojo = new LaTeXMojo();
        configureMojo(mojo, configuration);
        assertNotNull(mojo);
        return mojo;
    }

    /**
     * Create a mojo to be tested an feed it with a configuration.
     * 
     * @param pom the name of the POM
     * @param content the content of the POM
     * 
     * @return the mojo to be tested
     * 
     * @throws Exception in case of an error
     */
    private LaTeXMojo makeMojo(String pom, String content) throws Exception {

        File testPom = new File(pom);
        Writer w = new BufferedWriter(new FileWriter(testPom));
        try {
            w.write("<project>\n" //
                    + "  <build>\n"
                    + "    <plugins>\n"
                    + "      <plugin>\n"
                    + "        <groupId>org.extex</groupId>\n"
                    + "        <artifactId>maven-latex-plugin</artifactId>\n"
                    + "        <configuration>\n");
            w.write(content);
            w.write("        </configuration>\n" //
                    + "        <executions>\n"
                    + "          <execution>\n"
                    + "            <goals>\n"
                    + "              <goal>latex</goal>\n"
                    + "            </goals>\n"
                    + "          </execution>\n"
                    + "        </executions>\n"
                    + "      </plugin>\n"
                    + "    </plugins>\n" + "  </build>\n" + "</project>\n");
        } finally {
            w.close();
        }
        LaTeXMojo mojo;
        try {
            mojo = makeMojo(testPom);
        } finally {
            testPom.delete();
        }
        return mojo;
    }

    /**
     * Remove a directory with its contents.
     * 
     * @param directory the directory
     */
    private void rmdir(String directory) {

        File dir = new File(directory);
        if (!dir.exists()) {
            return;
        }
        for (String f : dir.list()) {
            new File(dir, f).delete();
        }
        dir.delete();
    }

    /**
     * Make and execute a mojo.
     * 
     * @param pom the name of the POM
     * @param content the content of the POM
     * @param log the expected log content or <code>null</code> if no comparison
     *        should be performed
     * 
     * @throws Exception in case of an error
     */
    private void runMojo(String pom, String content, String log)
            throws Exception {

        LaTeXMojo mojo = makeMojo(pom, content);
        StringBuilder buffer = new StringBuilder();
        mojo.setLog(new TLog(buffer));
        mojo.execute();
        if (log != null) {
            assertEquals(log, //
                buffer.toString().replace('\\', '/'));
        }
    }

    /**
     * Required for mojo lookups to work.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    @Before
    protected void setUp() throws Exception {

        super.setUp();
    }

    /**
     * Test method for {@link org.extex.maven.latex.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        runMojo(
            "target/latex-1.xml",
            "<file>src/test/resources/document1.tex</file>\n"
                    + "<latexCommand>pdflatex</latexCommand>\n",
            "[info] Building [1] "
                    + basedir
                    + "/target/doc/document1.pdf\n"
                    + "[info] Looking after "
                    + basedir
                    + "/target/doc/document1.aux\n"
                    + "[info] document1.aux does not exist\n"
                    + "[info] -> pdflatex -output-directory=target/doc -nonstopmode "
                    + basedir
                    + "/src/test/resources/document1.tex\n"
                    + "[info] document1.tex is up to date\n"
                    + "[info] -> pdflatex -output-directory=target/doc -nonstopmode "
                    + basedir + "/src/test/resources/document1.tex\n"
                    + "[info] Building [2] " + basedir
                    + "/target/doc/document1.pdf\n"
                    + "[info] document1.tex is not up to date\n"
                    + "[info] document1.aux is not up to date\n"
                    + "[info] document1.pdf is up to date\n");
    }

    /**
     * <testcase> The format must be one of the defined formats. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testInvalidFormat() throws Exception {

        try {
            runMojo("target/latex-1.xml",
                "<file>src/test/resources/document1.tex</file>\n"
                        + "<format>xyzzy</format>\n", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(true);
            assertEquals("Illegal target format: xyzzy", e.getMessage());
        }
    }

    /**
     * <testcase> A master file needs to be given or an exception is raised.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNoFile() throws Exception {

        try {
            runMojo("target/latex-1.xml", "", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(true);
            assertEquals("Missing master file", e.getMessage());
        }
    }

    /**
     * <testcase> an illegal latexCommand leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNoLaTeX() throws Exception {

        try {
            runMojo("target/latex-1.xml",
                "<file>src/test/resources/document1.tex</file>\n"
                        + "<latexCommand>xyzzy</latexCommand>\n", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link org.extex.maven.latex.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUndefinedFile() throws Exception {

        try {
            runMojo("target/latex-1.xml",
                "<file>src/test/resources/xyzzy</file>\n", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().startsWith("File not found"));
        }
    }

}
