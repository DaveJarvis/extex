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

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.outputStream.OutputStreamObserver;
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
public class MultiDumpDocumentWriterTest extends TestCase {

    /**
     * The field <tt>stream</tt> contains the output stream.
     */
    private static ByteArrayOutputStream stream;

    /**
     * The field <tt>factory</tt> contains the global factory.
     */
    protected static final OutputStreamFactory FACTORY =
            new OutputStreamFactory() {

                public OutputStream getOutputStream(String name, String type)
                        throws DocumentWriterException {

                    stream = new ByteArrayOutputStream();
                    return stream;
                }

                public void register(OutputStreamObserver observer) {

                    // not needed
                }

                public void setExtension(String extension) {

                    // not needed
                }

            };

    /**
     * 
     */
    @Test
    public final void testgetExtension() {

        assertEquals("out", new MultiDumpDocumentWriter(null).getExtension());
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout1() throws Exception {

        MultiDumpDocumentWriter writer = new MultiDumpDocumentWriter(null);
        assertEquals(0, writer.shipout(null));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout2() throws Exception {

        MultiDumpDocumentWriter writer = new MultiDumpDocumentWriter(null);
        writer.setOutputStreamFactory(FACTORY);
        Page page = new PageImpl(new VerticalListNode(), new Count[10]);
        assertEquals(1, writer.shipout(page));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testClose1() throws Exception {

        MultiDumpDocumentWriter writer = new MultiDumpDocumentWriter(null);
        writer.setOutputStreamFactory(FACTORY);
        writer.shipout(new PageImpl(new VerticalListNode(), new Count[10]));
        writer.close();
        String s = stream.toString();
        assertEquals("\\vbox(0.0pt+0.0pt)x0.0pt\n", s);
    }

    // TODO implement more test cases
}
