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

package org.extex.backend.documentWriter.postscript;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.extex.core.count.Count;
import org.extex.engine.typesetter.page.PageImpl;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.page.Page;
import org.junit.Test;

/**
 * This is the test suite for the PS writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PsWriterTest extends TestCase {

    /**
     * 
     */
    @Test
    public final void testgetExtension() {

        assertEquals("ps", new PsWriter(null).getExtension());
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout1() throws Exception {

        PsWriter writer = new PsWriter(null);
        assertEquals(0, writer.shipout(null));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout2() throws Exception {

        PsWriter writer = new PsWriter(null);
        Page page = new PageImpl(new VerticalListNode(), new Count[10]);
        assertEquals(1, writer.shipout(page));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testClose1() throws Exception {

        PsWriter writer = new PsWriter(null);
        OutputStream out = new ByteArrayOutputStream();
        writer.setOutputStream(out);
        writer.shipout(new PageImpl(new VerticalListNode(), new Count[10]));
        writer.close();
        String s = out.toString();
        assertEquals("%!PS-Adobe-3.0\n" //
                + "%%Creator: ExTeX.psWriter\n" //
                + "%%CreationDate: ", s.substring(0, 57));
        assertEquals("%%Title: <title>\n" //
                + "%%Pages: 1\n" //
                + "%%PageOrder: Ascend\n" //
                + "%%BoundingBox: 0 0 596 842\n" //
                + "%%DocumentFonts:\n" //
                + "%%EndComments\n" //
                + "TeXDict begin\n" //
                + "end\n" //
                + "%%Page: 1 1\n" //
                + "TeXDict begin\n" + "end\n" + "showpage\n" + "%%EOF\n", s
            .substring(77));
    }

    // TODO implement more test cases
}
