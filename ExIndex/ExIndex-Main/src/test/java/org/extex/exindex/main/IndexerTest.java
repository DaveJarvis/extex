/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.main;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IndexerTest {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return the indexer
     * 
     * @throws IOException in case of an error
     */
    protected Indexer makeIndexer() throws IOException {

        return new Indexer();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param in
     * @param out
     * @param log
     * @param exit
     * 
     * @throws IOException in case of an error
     */
    protected void run(String in, String out, String log, int exit)
            throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayOutputStream es = new ByteArrayOutputStream();
        int ret = run(new String[]{"-q"}, in, os, es);
        assertEquals("return code", exit, ret);
        assertEquals("output", out, os.toString());
        assertEquals("log", log, es.toString());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args
     * @param in
     * @param os
     * @param es
     * 
     * @return
     * @throws IOException
     */
    private int run(String[] args, String in, ByteArrayOutputStream os,
            ByteArrayOutputStream es) throws IOException {

        InputStream inStream = System.in;
        PrintStream outStream = System.out;
        PrintStream errStream = System.err;
        try {
            System.setIn(new ByteArrayInputStream(in.getBytes()));
            System.setOut(new PrintStream(os));
            System.setErr(new PrintStream(es));
            Indexer indexer = makeIndexer();
            return indexer.run(args);
        } finally {
            System.setIn(inStream);
            System.setOut(outStream);
            System.setErr(errStream);
        }
    }

    /**
     * Test method for {@link org.extex.exindex.main.Indexer#Indexer()}.
     * 
     * @throws IOException in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void test01() throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayOutputStream es = new ByteArrayOutputStream();
        run(null, "", os, es);
    }

    /**
     * Test method for {@link org.extex.exindex.main.Indexer#Indexer()}.
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test02() throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayOutputStream es = new ByteArrayOutputStream();
        int ret = run(new String[]{}, "", os, es);

    }

    @Test
    public final void test10() throws IOException {

        run("", "\\begin{theindex}\n\n\n\\end{theindex}\n", "", 0);
    }

    @Test
    public final void test11() throws IOException {

        run("\\indexentry{abc}{12}",
            "\\begin{theindex}\n\n\\subitem abc, 12\n\n\\end{theindex}\n", "",
            0);
    }

    @Test
    public final void test12() throws IOException {

        run(
            "\\indexentry{def}{12} " + "\\indexentry{abc}{34}",
            "\\begin{theindex}\n\n\\subitem abc, 34\n\\\\subitem def, 12\\n\n\\end{theindex}\n",
            "", 0);
    }

}
