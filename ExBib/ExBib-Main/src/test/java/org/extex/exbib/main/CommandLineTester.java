/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * This is the base class for test cases running a command on the command line.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public abstract class CommandLineTester {

    /**
     * Creates a new object.
     */
    public CommandLineTester() {

        super();
    }

    /**
     * Compare the contents of two files.
     * 
     * @param a the first file to compare
     * @param b the second file to compare
     * 
     * @return <code>true</code> iff the two files differ
     * 
     * @throws Exception in case of an error
     */
    private boolean compare(File a, File b) throws Exception {

        BufferedReader inA = new LineNumberReader(new FileReader(a));
        BufferedReader inB = new LineNumberReader(new FileReader(b));

        String l1;
        String l2;

        do {
            l1 = inA.readLine();
            l2 = inB.readLine();

            if (l1 == null) {
                if (l2 == null) {
                    return true;
                }

                System.err.println(" >EOF");
                System.err.println(" <" + l2);
                return false;
            } else if (l2 == null) {
                System.err.println(" >" + l1);
                System.err.println(" <EOF");
                return false;
            }
        } while (l1.equals(l2));

        System.err.println(" >" + l1);
        System.err.println(" <" + l2);

        return false;
    }

    /**
     * Perform a test.
     * 
     * @param cwd the current working directory
     * @param file the base file to process
     * @param verbose act verbosely
     * @param clear if set clear the intermediate files upon success
     * 
     * @throws Exception in case of some kind of problem
     */
    protected void runTest(String cwd, String file, boolean verbose,
            boolean clear) throws Exception {

        String[] argv =
                {"java", "-classpath",
                        (new File("target/classes").getAbsolutePath()),
                        "org.extex.exbib.main.ExBib", "-strict", file};
        File result = new File(cwd + "/" + file + ".result");
        File bbl = new File(cwd + "/" + file + ".bbl");
        File wd = new File(cwd);
        File aux = new File(cwd + "/" + file + ".aux");

        setupAux(aux, file);

        if (!result.exists()) {
            if (verbose) {
                System.err.println("Creating " + file + ".result");
            }

            Process proc =
                    Runtime.getRuntime().exec(("bibtex " + file).split(" "),
                        (String[]) null, wd);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(proc
                        .getErrorStream()));
            char[] buffer = new char[4096];

            if (verbose) {
                while (br.read(buffer) >= 0) {
                    System.out.println(buffer);
                }
            } else {
                while (br.read(buffer) >= 0) {
                    //
                }
            }

            proc.waitFor();

            bbl.renameTo(result);
        }

        Process proc = Runtime.getRuntime().exec(argv, (String[]) null, wd);
        BufferedReader br =
                new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        char[] buffer = new char[4096];

        if (verbose) {
            while (br.read(buffer) >= 0) {
                System.out.println(buffer);
            }
        } else {
            while (br.read(buffer) >= 0) {
                //
            }
        }

        int status = proc.waitFor();
        boolean cmp = (status == 0) && compare(bbl, result);

        if (clear && cmp) {
            aux.delete();
            new File(cwd, file + ".blg").delete();
            new File(cwd, file + ".bbl").delete();
        }
        assertEquals("Unexpected return status", 0, status);
        assertTrue("Comparison of the results failed", cmp);
    }

    /**
     * Prepare the contents of the aux file.
     * 
     * @param aux the aux file
     * @param name the base file to process
     * 
     * @throws IOException in case of an IO error
     */
    protected abstract void setupAux(File aux, String name) throws IOException;

}
