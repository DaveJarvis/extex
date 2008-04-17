/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.junit.Test;

/**
 * This is a test suite for {@link ExBib}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBib2Test extends BibTester {

    /**
     * <testcase> Test that an aux file contained in an aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux01() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        Writer w = new FileWriter(aux2);
        try {
            w.write("\\relax\n");
        } finally {
            w.close();
        }

        try {
            runTest(
                "test",
                "\\citation{*}\n" //
                        + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                        + "\\@include{test2}\n"
                        + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                0,
                Check.START,
                BANNER //
                        + "The output file: test.bbl\n"
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "The style file src/test/resources/bibtex/base/plain.bst\n"
                        + "Database file #1: src/test/resources/bibtex/base/xampl\n"
                        + "Warning: empty author in whole-journal\n"
                        + "Warning: empty title in whole-journal\n"
                        + "(There were 2 warnings)\n", "-v", "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                assertTrue(aux2.toString() + ": deletion failed", false);
            }
        }
    }

    /**
     * <testcase> Test that an aux file contained in an aux file twice is
     * reported twice. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux02() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        Writer w = new FileWriter(aux2);
        try {
            w.write("\\relax\n");
        } finally {
            w.close();
        }

        try {
            runTest(
                "test",
                "\\citation{*}\n" //
                        + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                        + "\\@include{test2}\n"
                        + "\\@include{test2}\n"
                        + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                0,
                Check.START,
                BANNER //
                        + "The output file: test.bbl\n"
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "The style file src/test/resources/bibtex/base/plain.bst\n"
                        + "Database file #1: src/test/resources/bibtex/base/xampl\n"
                        + "Warning: empty author in whole-journal\n"
                        + "Warning: empty title in whole-journal\n"
                        + "(There were 2 warnings)\n", "-v", "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                assertTrue(aux2.toString() + ": deletion failed", false);
            }
        }
    }

    /**
     * <testcase> Test that an aux file contained in an aux file thrice is
     * reported thrice. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux03() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        Writer w = new FileWriter(aux2);
        try {
            w.write("\\relax\n");
        } finally {
            w.close();
        }

        try {
            runTest(
                "test",
                "\\citation{*}\n" //
                        + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                        + "\\@include{test2}\n"
                        + "\\@include{test2}\n"
                        + "\\@include{test2}\n"
                        + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                0,
                Check.START,
                BANNER //
                        + "The output file: test.bbl\n"
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "The style file src/test/resources/bibtex/base/plain.bst\n"
                        + "Database file #1: src/test/resources/bibtex/base/xampl\n"
                        + "Warning: empty author in whole-journal\n"
                        + "Warning: empty title in whole-journal\n"
                        + "(There were 2 warnings)\n", "-v", "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                assertTrue(aux2.toString() + ": deletion failed", false);
            }
        }
    }

    /**
     * <testcase> Test that a non-existing aux file contained in an aux file is
     * reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux04() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        Writer w = new FileWriter(aux2);
        try {
            w.write("\\relax\n");
            w.write("\\@include{xyzzy}\n");
        } finally {
            w.close();
        }

        try {
            runTest("test", "\\citation{*}\n" //
                    + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                    + "\\@include{test2}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n", 1,
                Check.EQ, BANNER //
                        + "The output file: test.bbl\n"
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n" + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n" + "I couldn\'t open file xyzzy.aux\n", "-v",
                "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                assertTrue(aux2.toString() + ": deletion failed", false);
            }
        }
    }

}
