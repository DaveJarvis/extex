/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.main;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Locale;

import org.junit.Test;

/**
 * This is a test suite for ExIndex.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6779 $
 */
public class MakeIndexTest {

    /**
     * The field <tt>DEFAULT_LOG</tt> contains the default logging output.
     */
    private static final String DEFAULT_LOG =
            "Starting the processing phase.\n" + "Reading <stdin>.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Generating output...Starting the markup phase.\n"
                    + "Output written.\n";

    /**
     * Run a test and compare the results. The expected output is empty.
     * 
     * @param args the command line arguments
     * @param exit the exit code
     * @param extectedErr the expected error stream
     * 
     * @throws Exception in case of an error
     */
    protected void runTest(String[] args, int exit, String extectedErr)
            throws Exception {

        runTest(args, exit, "", extectedErr);
    }

    /**
     * Run a test and compare the results.
     * 
     * @param args the command line arguments
     * @param exit the exit code
     * @param expectedOut the expected output
     * @param extectedErr the expected error stream
     * 
     * @throws Exception in case of an error
     */
    protected void runTest(String[] args, int exit, String expectedOut,
            String extectedErr) throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        PrintStream out = System.out;
        PrintStream err = System.err;
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("".getBytes()));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        try {
            int ex = ExIndex.mainFacade(args);
            if (expectedOut != null) {
                outStream.flush();
                assertEquals("stdout", expectedOut, //
                    outStream.toString());
            }
            if (extectedErr != null) {
                errStream.flush();
                assertEquals("stderr", extectedErr, errStream.toString()
                    .replaceAll("^This is ExIndex [0-9.]+ \\([0-9]*\\)\n", ""));
            }
            assertEquals(exit, ex);
        } finally {
            System.setErr(err);
            System.setOut(out);
            System.setIn(in);
        }
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        File ist = new File("target/test1.ist");
        Writer w = new FileWriter(ist);
        try {
            w.write("%\n"
                    + "% Makeindex style file\n" //
                    + "group_skip \"\"\n"
                    + "heading_prefix \"\\n \\\\indexGroup{\"\n"
                    + "heading_suffix \"}\"\n" + "headings_flag 1\n" + "\n"
                    + "delim_0 \"\\\\dotfill\"\n" + "delim_1 \"\\\\dotfill\"\n"
                    + "delim_2 \"\\\\dotfill\"\n");
        } finally {
            w.close();
        }
        File idx = new File("target/test1.idx");
        w = new FileWriter(idx);
        try {
            w
                .write("\\indexentry{Java|)hyperpage}{11}\n"
                        + "\\indexentry{TEXMF|(hyperpage}{11}\n"
                        + "\\indexentry{texmf|hyperpage}{11}\n"
                        + "\\indexentry{TeXlive@\\TeX Live|hyperpage}{11}\n"
                        + "\\indexentry{CTAN|hyperpage}{11}\n"
                        + "\\indexentry{ls-R@\\textsf  {ls-R}|hyperpage}{11}\n"
                        + "\\indexentry{TEXMF|)hyperpage}{11}\n"
                        + "\\indexentry{installer|hyperpage}{12}\n"
                        + "\\indexentry{ExBib-setup.jar@\\textsf  {ExBib-setup.jar}|hyperpage}{12}\n"
                        + "\\indexentry{ExBib-setup.jar@\\textsf  {ExBib-setup.jar}|hyperpage}{12}\n"
                        + "\\indexentry{installer|hyperpage}{12}\n"
                        + "\\indexentry{Windows|hyperpage}{12}\n"
                        + "\\indexentry{Java|hyperpage}{12}\n"
                        + "\\indexentry{ExBib-setup.jar@\\textsf  {ExBib-setup.jar}|hyperpage}{12}\n"
                        + "\\indexentry{Explorer|hyperpage}{12}\n"
                        + "\\indexentry{installer!language|hyperpage}{12}\n"
                        + "\\indexentry{language!installer|hyperpage}{12}\n"
                        + "\\indexentry{language|hyperpage}{13}\n"
                        + "\\indexentry{license|hyperpage}{13}\n"
                        + "\\indexentry{LGPL|hyperpage}{13}\n"
                        + "\\indexentry{license!LGPL|hyperpage}{13}\n"
                        + "\\indexentry{GPL|hyperpage}{13}\n"
                        + "\\indexentry{license!GPL|hyperpage}{13}\n"
                        + "\\indexentry{installation   directory|hyperpage}{13}\n"
                        + "\\indexentry{directory installation|hyperpage}{13}\n"
                        + "\\indexentry{Windows|hyperpage}{14}\n"
                        + "\\indexentry{Unix|hyperpage}{14}\n"
                        + "\\indexentry{exbib|hyperpage}{15}\n"
                        + "\\indexentry{exbib.bat|hyperpage}{15}\n"
                        + "\\indexentry{path|hyperpage}{15}\n"
                        + "\\indexentry{installation directory|(hyperpage}{15}\n"
                        + "\\indexentry{directory installation|(hyperpage}{15}\n"
                        + "\\indexentry{Uninstaller@\\textsf  {Uninstaller}|hyperpage}{16}\n"
                        + "\\indexentry{bin@\\textsf  {bin}|hyperpage}{16}\n"
                        + "\\indexentry{doc@\\textsf  {doc}|hyperpage}{16}\n"
                        + "\\indexentry{lib@\\textsf  {lib}|hyperpage}{16}\n"
                        + "\\indexentry{directory installation|)hyperpage}{16}\n"
                        + "\\indexentry{installation directory|)hyperpage}{16}\n"
                        + "\\indexentry{installer|hyperpage}{16}\n"
                        + "\\indexentry{installation script|hyperpage}{16}\n");
        } finally {
            w.close();
        }
        try {
            runTest(
                new String[]{"-style", "target/test1.ist", "-makeindex",
                        "target/test1.idx"},
                0, //
                "\\begin{theindex}\n"
                        + "\n"
                        + " \\indexGroup{B}\n"
                        + "  \\item \\textsf  {bin}\\dotfill\\hyperpage{16}\n"
                        + " \\indexGroup{C}\n"
                        + "  \\item CTAN\\dotfill\\hyperpage{11}\n"
                        + " \\indexGroup{D}\n"
                        + "  \\item directory installation\\dotfill\\hyperpage{13}, \n"
                        + "        \\hyperpage{15--16}\n"
                        + "  \\item \\textsf  {doc}\\dotfill\\hyperpage{16}\n"
                        + " \\indexGroup{E}\n"
                        + "  \\item exbib\\dotfill\\hyperpage{15}\n"
                        + "  \\item \\textsf  {ExBib-setup.jar}\\dotfill\\hyperpage{12}\n"
                        + "  \\item exbib.bat\\dotfill\\hyperpage{15}\n"
                        + "  \\item Explorer\\dotfill\\hyperpage{12}\n"
                        + " \\indexGroup{G}\n"
                        + "  \\item GPL\\dotfill\\hyperpage{13}\n"
                        + " \\indexGroup{I}\n"
                        + "  \\item installation   directory\\dotfill\\hyperpage{13}\n"
                        + "  \\item installation directory\\dotfill\\hyperpage{15--16}\n"
                        + "  \\item installation script\\dotfill\\hyperpage{16}\n"
                        + "  \\item installer\\dotfill\\hyperpage{12}, \\hyperpage{16}\n"
                        + "    \\subitem language\\dotfill\\hyperpage{12}\n"
                        + " \\indexGroup{J}\n"
                        + "  \\item Java\\dotfill\\hyperpage{11, 12}\n"
                        + " \\indexGroup{L}\n"
                        + "  \\item language\\dotfill\\hyperpage{13}\n"
                        + "    \\subitem installer\\dotfill\\hyperpage{12}\n"
                        + "  \\item LGPL\\dotfill\\hyperpage{13}\n"
                        + "  \\item \\textsf  {lib}\\dotfill\\hyperpage{16}\n"
                        + "  \\item license\\dotfill\\hyperpage{13}\n"
                        + "    \\subitem GPL\\dotfill\\hyperpage{13}\n"
                        + "    \\subitem LGPL\\dotfill\\hyperpage{13}\n"
                        + "  \\item \\textsf  {ls-R}\\dotfill\\hyperpage{11}\n"
                        + " \\indexGroup{P}\n"
                        + "  \\item path\\dotfill\\hyperpage{15}\n"
                        + " \\indexGroup{T}\n"
                        + "  \\item \\TeX Live\\dotfill\\hyperpage{11}\n"
                        + "  \\item TEXMF\\dotfill\\hyperpage{11}\n"
                        + "  \\item texmf\\dotfill\\hyperpage{11}\n"
                        + " \\indexGroup{U}\n"
                        + "  \\item \\textsf  {Uninstaller}\\dotfill\\hyperpage{16}\n"
                        + "  \\item Unix\\dotfill\\hyperpage{14}\n"
                        + " \\indexGroup{W}\n"
                        + "  \\item Windows\\dotfill\\hyperpage{12}, \\hyperpage{14}\n"
                        + "\n" + "\\end{theindex}\n",
                "Scanning style file target/test1.ist...done (7 attributes redefined, 0\n"
                        + "ignored).\n" + "Starting the processing phase.\n"
                        + "Reading target/test1.idx.\n"
                        + "Starting the pre-processing phase.\n"
                        + "Generating output...Starting the markup phase.\n"
                        + "Output written.\n"
                        + "Transcript written in target/test1.ilg.\n");
        } finally {
            ist.delete();
            idx.delete();
            new File("target/test1.ilg").delete();
        }
    }

}
