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

package org.extex.sitebuilder.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Test;

/**
 * This is a test suite for the {@link SiteBuilderMain}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SiteBuilderMainTest {

    /**
     * Run the news builder with a given command line.
     * 
     * @param args the command line arguments
     * @param expected the output expected on stderr or {@code null} to
     *        suppress the comparison
     * 
     * @return the exit code
     */
    private static final int run(String[] args, String expected) {

        PrintStream err = System.err;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setErr(new PrintStream(out, true));
        try {
            int exit = SiteBuilderMain.run(args);
            if (expected != null) {
                assertEquals(expected, out.toString().replaceAll("\r", ""));
            }
            return exit;
        } finally {
            System.setErr(err);
        }
    }

    /**
     *  TODO
     * 
     */
    @Test
    public void test01() {

        File dir = new File(DIR_TARGET + "/test-site");
        dir.mkdirs();
        File indexHtml = new File(dir, "index.html");
        if (indexHtml.exists()) {
            indexHtml.delete();
        }
        File sitemapHtml = new File(DIR_TARGET + "/test-site/sitemap.html");
        if (sitemapHtml.exists()) {
            sitemapHtml.delete();
        }

        assertEquals(0,
            run(new String[]{"-base", "src/test/resources/empty-site"}, null));
        assertTrue("Missing index.html", indexHtml.exists());
        assertTrue("Missing sitemap.html", sitemapHtml.exists());
    }

    /**
     * A missing argument for the option {@code -baseDirectory} is recognized
* 
     */
    @Test
    public void testBase01() {

        assertEquals(-1,
            run(new String[]{"-base"}, "*** Missing argument for -base\n"));
    }

    /**
     * A missing argument for the option {@code -baseDirectory} is recognized
* 
     */
    @Test
    public void testBase02() {

        assertEquals(-1,
            run(new String[]{"--base"}, "*** Missing argument for -base\n"));
    }

    /**
     *  An error is raised if base is not a file.
     * 
     */
    @Test
    public void testBase03() {

        assertEquals(-1, run(new String[]{"-base", DIR_TARGET + "/xyzzy"}, null));
    }

    /**
     *  A unknown option leads to the help message.
     * 
     */
    @Test
    public void testHelp01() {

        assertEquals(-111,
            run(new String[]{"--help"}, SiteBuilderMain.HELP_INFO + "\n"));
    }

    /**
     * A missing argument for the option {@code -library} is recognized
* 
     */
    @Test
    public void testLibrary01() {

        assertEquals(
            -1,
            run(new String[]{"-library"}, "*** Missing argument for -library\n"));
    }

    /**
     * A missing argument for the option {@code -omit} is recognized
* 
     */
    @Test
    public void testOmit01() {

        assertEquals(-1,
            run(new String[]{"-omit"}, "*** Missing argument for -omit\n"));
    }

    /**
     * A missing argument for the option {@code -output} is recognized
* 
     */
    @Test
    public void testOutput01() {

        assertEquals(-1,
            run(new String[]{"-output"}, "*** Missing argument for -output\n"));
    }

    /**
     * A missing argument for the option {@code -sitemap} is recognized
* 
     */
    @Test
    public void testSitemap01() {

        assertEquals(
            -1,
            run(new String[]{"-sitemap"}, "*** Missing argument for -sitemap\n"));
    }

    /**
     *  A unknown option leads to the help message.
     * 
     */
    @Test
    public void testUnknown01() {

        assertEquals(-2,
            run(new String[]{"-xyzzy"}, "*** Unknown option: -xyzzy\n"));
    }

}
