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

package org.extex.exindex.core.type;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.transform.Upcase;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LString;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StructuredIndexTest {

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite1() throws Exception {

        StructuredIndex index = new StructuredIndex();
        index.defineLetterGroup("a");
        index.defineLetterGroup("b");

        StringWriter writer = new StringWriter();
        LInterpreter interpreter = new Indexer();
        interpreter.load("src/test/resources/xindy/makeidx.xdy");
        index.write(writer, interpreter);

        writer.flush();
        assertEquals("\\begin{theindex}\n" + // 
                "\n" + //
                "\n" + //
                "  \\indexspace\n" + // 
                "\n" + //
                "\n" + //
                "\\end{theindex}\n", writer.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite2() throws Exception {

        StructuredIndex index = new StructuredIndex();
        index.defineLetterGroup("a");
        index.defineLetterGroup("b");

        StringWriter writer = new StringWriter();
        LInterpreter interpreter = new Indexer();
        interpreter.load("src/test/resources/xindy/makeidx.xdy");
        interpreter.setq("markup:letter-group-a-open-head", new LString(">"));
        interpreter.setq("markup:letter-group-b-open-head", new LString(">>"));
        index.write(writer, interpreter);

        writer.flush();
        assertEquals("\\begin{theindex}\n" + // 
                ">a\n" + //
                "\n" + //
                "  \\indexspace\n" + // 
                ">>b\n" + //
                "\n" + //
                "\\end{theindex}\n", writer.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite3() throws Exception {

        StructuredIndex index = new StructuredIndex();
        index.defineLetterGroup("a");
        index.defineLetterGroup("b");

        StringWriter writer = new StringWriter();
        LInterpreter interpreter = new Indexer();
        interpreter.load("src/test/resources/xindy/makeidx.xdy");
        interpreter.setq("markup:letter-group-a-open-head", new LString(">"));
        interpreter.setq("markup:letter-group-b-open-head", new LString(">>"));
        interpreter.setq("markup:letter-group-a-transform", new Upcase());
        index.write(writer, interpreter);

        writer.flush();
        assertEquals("\\begin{theindex}\n" + // 
                ">A\n" + //
                "\n" + //
                "  \\indexspace\n" + // 
                ">>b\n" + //
                "\n" + //
                "\\end{theindex}\n", writer.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite4() throws Exception {

        StructuredIndex index = new StructuredIndex();
        index.defineLetterGroup("a");
        index.defineLetterGroup("b");
        Entry entry =
                new Entry(new String[]{"abc"}, "Abc", new PageReference() {

                    public String getEncap() {

                        return "b";
                    }

                    public String getPage() {

                        return "234";
                    }
                });

        StringWriter writer = new StringWriter();
        LInterpreter interpreter = new Indexer();
        interpreter.load("src/test/resources/xindy/makeidx.xdy");
        interpreter.setq("markup:letter-group-a-open-head", new LString(">"));
        interpreter.setq("markup:letter-group-b-open-head", new LString(">>"));
        interpreter.setq("markup:letter-group-a-transform", new Upcase());
        index.write(writer, interpreter);

        writer.flush();
        assertEquals("\\begin{theindex}\n" + // 
                ">A\n" + //
                "\n" + //
                "  \\indexspace\n" + // 
                ">>b\n" + //
                "\n" + //
                "\\end{theindex}\n", writer.toString());
    }

}
