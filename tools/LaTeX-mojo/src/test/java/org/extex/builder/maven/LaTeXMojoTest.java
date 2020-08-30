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

package org.extex.builder.maven;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.apache.maven.plugin.Mojo;
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
*/
public class LaTeXMojoTest extends AbstractMojoTestCase {

    /**
     * The field {@code basedir} contains the base directory.
     */
    private String basedir = getBasedir().replace('\\', '/');

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
    private Mojo makeMojo(String pom, String content) throws Exception {

        File testPom = new File(pom);
        Writer w = new BufferedWriter(new FileWriter(testPom));
        try {
            w.write("<project>\n"
                    + "  <build>\n"
                    + "    <plugins>\n"
                    + "      <plugin>\n"
                    + "        <groupId>org.extex</groupId>\n"
                    + "        <artifactId>maven-latex-plugin</artifactId>\n"
                    + "        <configuration>\n");
            w.write(content);
            w.write("        </configuration>\n"
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
        rmdir(DIR_TARGET + "/doc");
        Mojo mojo;
        try {
            PlexusConfiguration configuration =
                    extractPluginConfiguration("maven-latex-plugin", testPom);
            assertNotNull(configuration);
            mojo = new LaTeXMojo();
            configureMojo(mojo, configuration);
            assertNotNull(mojo);
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
     * @param log the expected log content or {@code null} if no comparison
     *        should be performed
     * 
     * @throws Exception in case of an error
     */
    private void runMojo(String pom, String content, String log)
            throws Exception {

        Mojo mojo = makeMojo(pom, content);
        StringBuilder buffer = new StringBuilder();
        mojo.setLog(new TLog(buffer));
        mojo.execute();
        if (log != null) {
            assertEquals(log,
                buffer.toString().replace('\\', '/'));
        }
    }

    /**
     * Required for mojo lookups to work.
     * 
     * @throws Exception in case of an error
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    @Before
    protected void setUp() throws Exception {

        super.setUp();
    }

    /**
     * Test method for {@link org.extex.builder.maven.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void t_est3() throws Exception {

        runMojo(DIR_TARGET + "/latex-3.xml",
            "<file>src/test/resources/document3.tex</file>\n", null);
    }

    /**
     * Test method for {@link org.extex.builder.maven.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        runMojo(
            DIR_TARGET + "/latex-1.xml",
            "<file>src/test/resources/document1.tex</file>\n"
                    + "<latexCommand>pdflatex</latexCommand>\n",
            ("[info] Building [1] {0}/target/doc/document1.pdf\n"
                    + "[info] Looking after {0}/target/doc/document1.aux\n"
                    + "[info] document1.aux does not exist\n"
                    + "[info] -> pdflatex -output-directory=target/doc -nonstopmode {0}/src/test/resources/document1.tex\n"
                    + "[info] document1.tex is up to date\n"
                    + "[info] -> pdflatex -output-directory=target/doc -nonstopmode {0}/src/test/resources/document1.tex\n"
                    + "[info] Building [2] {0}/target/doc/document1.pdf\n"
                    + "[info] document1.tex is not up to date\n"
                    + "[info] document1.aux is not up to date\n"
                    + "[info] document1.pdf is up to date\n").replaceAll(
                "\\{0\\}", basedir));
    }

    /**
     * Test method for {@link org.extex.builder.maven.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test2() throws Exception {

        runMojo(
            DIR_TARGET + "/latex-2.xml",
            "<file>src/test/resources/document2.tex</file>\n",
            ("[info] Building [1] {0}/target/doc/document2.pdf\n"
                    + "[info] Looking after {0}/target/doc/document2.aux\n"
                    + "[info] document2.aux does not exist\n"
                    + "[info] -> pdflatex -output-directory=target/doc -nonstopmode {0}/src/test/resources/document2.tex\n"
                    + "[info] Looking after {0}/target/doc/document2.ind\n"
                    + "[info] -> makeindex -o {0}/target/doc/document2.ind -t {0}/target/doc/document2.ilg {0}/src/test/resources/document2.tex\n"
                    + "[info] document2.tex is up to date\n"
                    + "[info] -> pdflatex -output-directory=target/doc -nonstopmode {0}/src/test/resources/document2.tex\n"
                    + "[info] Building [2] {0}/target/doc/document2.pdf\n"
                    + "[info] -> makeindex -o {0}/target/doc/document2.ind -t {0}/target/doc/document2.ilg {0}/src/test/resources/document2.tex\n"
                    + "[info] document2.tex is not up to date\n"
                    + "[info] document2.aux is not up to date\n"
                    + "[info] document2.ind is up to date\n"
                    + "[info] document2.pdf has new content\n"
                    + "[info] document2.pdf is up to date\n").replaceAll(
                "\\{0\\}", basedir));
    }

    /**
     *  The format must be one of the defined formats. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testInvalidFormat() throws Exception {

        try {
            runMojo(DIR_TARGET + "/latex-1.xml",
                "<file>src/test/resources/document1.tex</file>\n"
                        + "<format>xyzzy</format>\n", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(true);
            assertEquals("Illegal target format: xyzzy", e.getMessage());
        }
    }

    /**
     *  A master file needs to be given or an exception is raised.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNoFile() throws Exception {

        try {
            runMojo(DIR_TARGET + "/latex-1.xml", "", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(true);
            assertEquals("Missing master file", e.getMessage());
        }
    }

    /**
     *  an illegal latexCommand leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNoLaTeX() throws Exception {

        try {
            runMojo(DIR_TARGET + "/latex-1.xml",
                "<file>src/test/resources/document1.tex</file>\n"
                        + "<latexCommand>xyzzy</latexCommand>\n", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link org.extex.builder.maven.LaTeXMojo#execute()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUndefinedFile() throws Exception {

        try {
            runMojo(DIR_TARGET + "/latex-1.xml",
                "<file>src/test/resources/xyzzy</file>\n", null);
            assertTrue(false);
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().startsWith("File not found"));
        }
    }

}
