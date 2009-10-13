/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

import org.junit.Test;

/**
 * This is a test suite for {@link Makeindex}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MakeindexTest extends AbstractTester {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.makeindex.main.AbstractTester#makeInstance()
     */
    @Override
    protected Makeindex makeInstance() {

        return new Makeindex();
    }

    /**
     * <testcase> The argument to run can not be <code>null</code>. In this case
     * an {@link IllegalArgumentException} is thrown. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public void test00() throws IOException {

        PrintStream out = System.out;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            System.setErr(new PrintStream(outStream));
            makeInstance().run(null);
        } finally {
            System.setErr(out);
        }
    }

    /**
     * <testcase> An index can be sorted. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test1() throws IOException {

        String f = "target/t1.idx";
        Locale.setDefault(Locale.ENGLISH);
        runOnFile(
            new String[]{f},
            f,
            "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n", //
            BANNER
                    + "Scanning input file ./target/t1.idx...done (2 entries accepted, 0 rejected)\n"
                    + "Sorting...done (1 comparisons).\n"
                    + "Generating output file ./target/t1.ind...done (0 entries written, 0 warnings).\n"
                    + "Output written in ./target/t1.ind.\n"
                    + "Transcript written in target/t1.ilg.\n", //
            "\\begin{theindex}\n" //
                    + "\n" //
                    + "  \\item aaa, 3\n" //
                    + "  \\item bbb, 2\n" //
                    + "\n" //
                    + "\\end{theindex}\n");
    }

    /**
     * <testcase> An index can be sorted with option -g. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test1g() throws IOException {

        String f = "target/t1.idx";
        Locale.setDefault(Locale.ENGLISH);
        runOnFile(
            new String[]{"-g", f},
            f,
            "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n", //
            BANNER
                    + "Scanning input file ./target/t1.idx...done (2 entries accepted, 0 rejected)\n"
                    + "Sorting...done (1 comparisons).\n"
                    + "Generating output file ./target/t1.ind...done (0 entries written, 0 warnings).\n"
                    + "Output written in ./target/t1.ind.\n"
                    + "Transcript written in target/t1.ilg.\n", //
            "\\begin{theindex}\n" //
                    + "\n" //
                    + "  \\item aaa, 3\n" //
                    + "  \\item bbb, 2\n" //
                    + "\n" //
                    + "\\end{theindex}\n");
    }

    /**
     * <testcase> An index can be sorted with option -l. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test1l() throws IOException {

        String f = "target/t1.idx";
        Locale.setDefault(Locale.ENGLISH);
        runOnFile(
            new String[]{"-l", f},
            f,
            "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n", //
            BANNER
                    + "Scanning input file ./target/t1.idx...done (2 entries accepted, 0 rejected)\n"
                    + "Sorting...done (1 comparisons).\n"
                    + "Generating output file ./target/t1.ind...done (0 entries written, 0 warnings).\n"
                    + "Output written in ./target/t1.ind.\n"
                    + "Transcript written in target/t1.ilg.\n", //
            "\\begin{theindex}\n" //
                    + "\n" //
                    + "  \\item aaa, 3\n" //
                    + "  \\item bbb, 2\n" //
                    + "\n" //
                    + "\\end{theindex}\n");
    }

    /**
     * <testcase> An index (omitting the suffix) can be sorted. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test2() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        runOnFile(new String[]{"target/t1"},
            "target/t1.idx",
            "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n", //
            BANNER
                    + "Scanning input file ./target/t1.idx...done (2 entries accepted, 0 rejected)\n"
                    + "Sorting...done (1 comparisons).\n"
                    + "Generating output file ./target/t1.ind...done (0 entries written, 0 warnings).\n"
                    + "Output written in ./target/t1.ind.\n"
                    + "Transcript written in target/t1.ilg.\n", //
            "\\begin{theindex}\n" //
                    + "\n" //
                    + "  \\item aaa, 3\n" //
                    + "  \\item bbb, 2\n" //
                    + "\n" //
                    + "\\end{theindex}\n");
    }

    /**
     * <testcase> An index with colliding keys can be sorted. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test3() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        runOnFile(new String[]{"target/t1.idx"},
            "target/t1.idx",
            "\\indexentry{bbb}{2}\n" //
                    + "\\indexentry{bbb}{4}\n" //
                    + "\\indexentry{aaa}{3}\n", //
            BANNER
                    + "Scanning input file ./target/t1.idx...done (3 entries accepted, 0 rejected)\n"
                    + "Sorting...done (3 comparisons).\n"
                    + "Generating output file ./target/t1.ind...done (0 entries written, 0 warnings).\n"
                    + "Output written in ./target/t1.ind.\n"
                    + "Transcript written in target/t1.ilg.\n", //
            "\\begin{theindex}\n" //
                    + "\n" //
                    + "  \\item aaa, 3\n" //
                    + "  \\item bbb, 2, 4\n" //
                    + "\n" //
                    + "\\end{theindex}\n");
    }

    /**
     * <testcase> The argument to run can be the empty string array. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testEmptyArguments1() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(
            new String[]{}, //
            BANNER
                    + "Scanning input file stdin...done (0 entries accepted, 0 rejected)\n" //
                    + "Sorting...done (0 comparisons).\n" //
                    + "Generating output...done (0 entries written, 0 warnings).\n" //
                    + "Output written in stdout.\n", //
            "\\begin{theindex}\n\n\n\\end{theindex}\n", 0);
    }

    /**
     * <testcase> The empty string argument is ignored. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testEmptyArguments2() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(
            new String[]{""}, //
            BANNER
                    + "Scanning input file stdin...done (0 entries accepted, 0 rejected)\n" //
                    + "Sorting...done (0 comparisons).\n" //
                    + "Generating output...done (0 entries written, 0 warnings).\n" //
                    + "Output written in stdout.\n", //
            "\\begin{theindex}\n\n\n\\end{theindex}\n", 0);
    }

    /**
     * <testcase> Help is provided upon request. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testHelp1() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(
            new String[]{"--help"}, //
            BANNER
                    + "Usage: makeindex [-ilqrcg] [-s sty] [-o ind] [-t log] [-p num] [idx0 idx1 ...]\n", //
            "", 1);
    }

    /**
     * <testcase> A missing argument for -style is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testStyle00() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-s"}, //
            BANNER + "Missing argument for option -s\n", //
            "", -1);
    }

    /**
     * <testcase> An empty argument for -style is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testStyle01() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-s", ""}, //
            BANNER + "Empty style is not permitted.\n", //
            "", -1);
    }

    /**
     * <testcase> A null argument for -style is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testStyle02() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-s", null}, //
            BANNER + "Empty style is not permitted.\n", //
            "", -1);
    }

    /**
     * <testcase> A missing argument for -style is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testStyle1() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-s", "xyzzy"}, //
            BANNER + "Input style file xyzzy not found.\n", //
            "", -1);
    }

    /**
     * <testcase> A empty style is consumed. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testStyle2() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(
            new String[]{"-s", "src/test/resources/makeindex/empty.ist"}, //
            BANNER
                    + "Scanning style file src/test/resources/makeindex/empty.ist...done (0 attributes\n"
                    + "redefined, 0 ignored).\n"
                    + "Scanning input file stdin...done (0 entries accepted,\n0 rejected)\n"
                    + "Sorting...done (0 comparisons).\n"
                    + "Generating output...done (0 entries written, 0 warnings).\n"
                    + "Output written in stdout.\n", //
            "\\begin{theindex}\n\n\n\\end{theindex}\n", 0);
    }

    /**
     * <testcase> A empty style is consumed. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testStyle3() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(
            new String[]{"-style=src/test/resources/makeindex/empty.ist"}, //
            BANNER
                    + "Scanning style file src/test/resources/makeindex/empty.ist...done (0 attributes\n"
                    + "redefined, 0 ignored).\n"
                    + "Scanning input file stdin...done (0 entries accepted,\n0 rejected)\n"
                    + "Sorting...done (0 comparisons).\n"
                    + "Generating output...done (0 entries written, 0 warnings).\n"
                    + "Output written in stdout.\n", //
            "\\begin{theindex}\n\n\n\\end{theindex}\n", 0);
    }

    /**
     * <testcase> An unknown file argument is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testTranscript1() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(
            new String[]{"-t", "target/xyz.ilog"}, //
            BANNER
                    + "Scanning input file stdin...done (0 entries accepted, 0 rejected)\n" //
                    + "Sorting...done (0 comparisons).\n" //
                    + "Generating output...done (0 entries written, 0 warnings).\n" //
                    + "Output written in stdout.\n"
                    + "Transcript written in target/xyz.ilog.\n", //
            "\\begin{theindex}\n\n\n\\end{theindex}\n", 0);
        File t = new File("target/xyz.ilog");
        assertTrue(t.exists());
        t.delete();
    }

    /**
     * <testcase> An unknown file argument is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testTranscript2() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(
            new String[]{"-transcript=target/xyz.ilog"}, //
            BANNER
                    + "Scanning input file stdin...done (0 entries accepted, 0 rejected)\n" //
                    + "Sorting...done (0 comparisons).\n" //
                    + "Generating output...done (0 entries written, 0 warnings).\n" //
                    + "Output written in stdout.\n"
                    + "Transcript written in target/xyz.ilog.\n", //
            "\\begin{theindex}\n\n\n\\end{theindex}\n", 0);
        File t = new File("target/xyz.ilog");
        assertTrue(t.exists());
        t.delete();
    }

    /**
     * <testcase> An unknown argument is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testUnknownArgument1() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-xyzzy"}, //
            BANNER + "Unknown option -xyzzy.\n", //
            "", -1);
    }

    /**
     * <testcase> An unknown file argument is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testUnknownFile1() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"target/xyzzy"}, //
            BANNER + "Input index file target/xyzzy not found.\n", //
            "", -1);
    }

    /**
     * <testcase> An unknown file argument is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testUnknownFile2() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-", "target/xyzzy"}, //
            BANNER + "Input index file target/xyzzy not found.\n", //
            "", -1);
    }

    /**
     * <testcase> An unknown file argument is reported. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testUnknownFile3() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"--", "target/xyzzy"}, //
            BANNER + "Input index file target/xyzzy not found.\n", //
            "", -1);
    }

    /**
     * <testcase> The version is reported upon request. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testVersion1() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-V"}, BANNER, "", 1);
    }

    /**
     * <testcase> The version is reported upon request. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testVersion2() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"--V"}, BANNER, "", 1);
    }

    /**
     * <testcase> The version is reported upon request. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testVersion3() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"-Version"}, BANNER, "", 1);
    }

    /**
     * <testcase> The version is reported upon request. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testVersion4() throws IOException {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"--Version"}, BANNER, "", 1);
    }

}
