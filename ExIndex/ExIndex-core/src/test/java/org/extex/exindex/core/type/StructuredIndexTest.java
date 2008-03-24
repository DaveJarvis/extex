/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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
import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.type.markup.MarkupTransform;
import org.extex.exindex.core.type.raw.LocationReference;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.core.type.transform.Capitalize;
import org.extex.exindex.core.type.transform.Upcase;
import org.junit.Test;

/**
 * This is a test suite for {@link StructuredIndex}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StructuredIndexTest {

    /**
     * Add an item to an index.
     * 
     * @param index the index
     * @param arg the name of the item to add
     * 
     * @throws IndexerException in case of an error
     */
    private void fillLetterGroup(StructuredIndex index, String arg)
            throws IndexerException {

        RawIndexentry ie =
                new RawIndexentry("", new String[]{arg}, new String[]{arg},
                    new LocationReference("", "123"));
        ie.setSortKey(new String[]{arg});
        index.store(ie);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter, boolean)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite1() throws Exception {

        Indexer indexer = new Indexer();
        indexer.load("src/test/resources/xindy/makeidx.xdy");

        StructuredIndex index = indexer.getContainer().getCurrentIndex();
        index.defineLetterGroup("a", "a");
        fillLetterGroup(index, "a");
        index.defineLetterGroup("b", "b");
        fillLetterGroup(index, "b");

        StringWriter writer = new StringWriter();
        index.write(writer, indexer, false);
        writer.flush();

        assertEquals("\\begin{theindex}\n\n" + "  \\item a123\n" + "\n"
                + "  \\indexspace\n\n" + "  \\item b123\n" + "\n"
                + "\\end{theindex}\n" + "", writer.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter, boolean)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite2() throws Exception {

        Indexer indexer = new Indexer();
        indexer.load("src/test/resources/xindy/makeidx.xdy");

        StructuredIndex index = indexer.getContainer().getCurrentIndex();
        index.defineLetterGroup("a", "a");
        fillLetterGroup(index, "a");
        index.defineLetterGroup("b", "b");
        fillLetterGroup(index, "b");

        MarkupTransform mt = new MarkupTransform("LETTER-GROUP");
        mt.set("a", "", "", ">", "", false);
        index.setMarkup("markup-letter-group", mt);
        mt.set("b", "", "", ">>", "", false);
        index.setMarkup("markup-letter-group", mt);

        StringWriter writer = new StringWriter();
        index.write(writer, indexer, false);
        writer.flush();

        assertEquals("\\begin{theindex}\n" //
                + ">a\n" //
                + "  \\item a123\n" //
                + "\n" + "  \\indexspace\n" //
                + ">>b\n" //
                + "  \\item b123\n" //
                + "\n" //
                + "\\end{theindex}\n", writer.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter, boolean)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite3() throws Exception {

        Indexer indexer = new Indexer();
        indexer.load("src/test/resources/xindy/makeidx.xdy");

        StructuredIndex index = indexer.getContainer().getCurrentIndex();
        index.defineLetterGroup("a", "a");
        fillLetterGroup(index, "a");
        index.defineLetterGroup("b", "b");
        fillLetterGroup(index, "b");

        MarkupTransform mt = new MarkupTransform("LETTER-GROUP");
        mt.set("a", "", "", ">", "", false);
        mt.setTransform("a", new Upcase());
        index.setMarkup("markup-letter-group", mt);
        mt.set("b", "", "", ">>", "", false);
        index.setMarkup("markup-letter-group", mt);

        StringWriter writer = new StringWriter();
        index.write(writer, indexer, false);
        writer.flush();

        assertEquals("\\begin{theindex}\n" //
                + ">A\n" //
                + "  \\item a123\n" //
                + "\n" + "  \\indexspace\n" //
                + ">>b\n" //
                + "  \\item b123\n" //
                + "\n" //
                + "\\end{theindex}\n", writer.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.StructuredIndex#write(java.io.Writer, org.extex.exindex.lisp.LInterpreter, boolean)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testWrite4() throws Exception {

        Indexer indexer = new Indexer();
        indexer.load("src/test/resources/xindy/makeidx.xdy");

        StructuredIndex index = indexer.getContainer().getCurrentIndex();
        index.defineLetterGroup("a", "a");
        fillLetterGroup(index, "a");
        index.defineLetterGroup("b", "b");
        fillLetterGroup(index, "b");

        MarkupTransform mt = new MarkupTransform("LETTER-GROUP");
        mt.set("a", "", "", ">", "", false);
        mt.setTransform("a", new Capitalize());
        index.setMarkup("markup-letter-group", mt);
        mt.set("b", "", "", ">>", "", false);
        index.setMarkup("markup-letter-group", mt);

        StringWriter writer = new StringWriter();
        index.write(writer, indexer, false);

        writer.flush();
        assertEquals("\\begin{theindex}\n" //
                + ">A\n" //
                + "  \\item a123\n" //
                + "\n" + "  \\indexspace\n" //
                + ">>b\n" //
                + "  \\item b123\n" //
                + "\n" //
                + "\\end{theindex}\n", writer.toString());
    }

}
