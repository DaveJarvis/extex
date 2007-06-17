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

package org.extex.backend.documentWriter.dump;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.extex.core.count.Count;
import org.extex.engine.typesetter.page.PageImpl;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.page.Page;
import org.junit.Test;

/**
 * This document writer produces multi-page dumps.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DumpDocumentWriterTest extends TestCase {

    /**
     * 
     */
    @Test
    public final void testgetExtension() {

        assertEquals("out", new DumpDocumentWriter(null).getExtension());
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout1() throws Exception {

        DumpDocumentWriter writer = new DumpDocumentWriter(null);
        OutputStream out = new ByteArrayOutputStream();
        writer.setOutputStream(out);
        assertEquals(0, writer.shipout(null));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout2() throws Exception {

        DumpDocumentWriter writer = new DumpDocumentWriter(null);
        OutputStream out = new ByteArrayOutputStream();
        writer.setOutputStream(out);
        Page page = new PageImpl(new VerticalListNode(), new Count[10]);
        assertEquals(1, writer.shipout(page));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testClose1() throws Exception {

        DumpDocumentWriter writer = new DumpDocumentWriter(null);
        OutputStream out = new ByteArrayOutputStream();
        writer.setOutputStream(out);
        writer.shipout(new PageImpl(new VerticalListNode(), new Count[10]));
        writer.close();
        String s = out.toString();
        assertEquals("\\vbox(0.0pt+0.0pt)x0.0pt\n", s);
    }

    // TODO implement more test cases
}
