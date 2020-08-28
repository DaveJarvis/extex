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

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.outputStream.OutputStreamObserver;
import org.extex.core.count.Count;
import org.extex.engine.typesetter.page.PageImpl;
import org.extex.font.FontFactoryImpl;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.resource.InteractionIndicator;
import org.extex.resource.ResourceFinderFactory;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.page.Page;
import org.junit.Test;

/**
 * This is the test suite for the EPS writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EpsWriterTest {

    /**
     * Create and initialize a writer to be tested.
     * 
     * @param options the options
     * 
     * @return the writer to test
     */
    private EpsWriter makeWriter(DocumentWriterOptions options) {

        EpsWriter writer = new EpsWriter(options);
        FontFactoryImpl factory = new FontFactoryImpl();
        factory.configure(ConfigurationFactory
            .newInstance("test/font/test-fonts"));
        factory.setProperties(new Properties());
        factory.setResourceFinder(new ResourceFinderFactory()
            .createResourceFinder(
                ConfigurationFactory.newInstance("path/fontTestFileFinder"),
                Logger.getLogger("test"), new Properties(),
                new InteractionIndicator() {

                    public boolean isInteractive() {

                        return false;
                    }
                }));
        writer.setFontFactory(factory);
        return writer;
    }

    /**
     * The field <tt>pages</tt> contains the list of collected pages.
     */
    private List<ByteArrayOutputStream> pages =
            new ArrayList<ByteArrayOutputStream>();

    /**
     * The field <tt>osf</tt> contains the output stream factory.
     */
    private OutputStreamFactory osf = new OutputStreamFactory() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.backend.outputStream.OutputStreamFactory#getOutputStream(
         *      java.lang.String, java.lang.String)
         */
        public OutputStream getOutputStream(String name, String type)
                throws DocumentWriterException {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pages.add(out);
            return out;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.backend.outputStream.OutputStreamFactory#register(
         *      org.extex.backend.outputStream.OutputStreamObserver)
         */
        public void register(OutputStreamObserver observer) {


        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.backend.outputStream.OutputStreamFactory#setExtension(
         *      java.lang.String)
         */
        public void setExtension(String extension) {


        }

    };

    /**
     * 
     */
    @Test
    public final void testgetExtension() {

        assertEquals("eps", new EpsWriter(null).getExtension());
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout1() throws Exception {

        EpsWriter writer = new EpsWriter(null);
        assertEquals(0, writer.shipout(null));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout2() throws Exception {

        pages.clear();
        EpsWriter writer = makeWriter(null);
        writer.setOutputStreamFactory(osf);
        Page page = new PageImpl(new VerticalListNode(), new Count[10]);
        assertEquals(1, writer.shipout(page));
        pages.clear();
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testClose1() throws Exception {

        pages.clear();
        EpsWriter writer = makeWriter(null);
        writer.setOutputStreamFactory(osf);
        writer.shipout(new PageImpl(new VerticalListNode(), new Count[10]));
        writer.close();
        assertEquals(1, pages.size());
        String s = pages.get(0).toString().replaceAll("\r", "");

        assertEquals("%!PS-Adobe-2.0 EPSF-2.0\n"
                + "%%Creator: ExTeX-Backend-ps\n"
                + "%%CreationDate: ", s.substring(0, 68));
        assertEquals("%%Title: \n" + "%%BoundingBox: 0 0 0 0\n"
                + "%%HiResBoundingBox: 0.0 0.0 0.0 0.0\n"
                + "%%DocumentFonts:\n" + "%%EndComments\n"
                + "/TeXDict 300 dict def\n"
                + "%%BeginProcSet: Ps.java\n"
                + "TeXDict begin /eop{}def end\n"
                + "%%EndProcSet\n"
                + "TeXDict begin\n eop\n" + "end\n" + "%%EOF\n", s
            .substring(88));
        pages.clear();
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testClose2() throws Exception {

        pages.clear();
        EpsWriter writer = makeWriter(null);
        writer.setOutputStreamFactory(osf);
        writer.shipout(new PageImpl(new VerticalListNode(), new Count[10]));
        writer.shipout(new PageImpl(new VerticalListNode(), new Count[10]));
        writer.close();
        assertEquals(2, pages.size());
        String s = pages.get(0).toString().replaceAll("\r", "");
        assertEquals("%!PS-Adobe-2.0 EPSF-2.0\n"
                + "%%Creator: ExTeX-Backend-ps\n"
                + "%%CreationDate: ", s.substring(0, 68));
        assertEquals("%%Title: \n" + "%%BoundingBox: 0 0 0 0\n"
                + "%%HiResBoundingBox: 0.0 0.0 0.0 0.0\n"
                + "%%DocumentFonts:\n" + "%%EndComments\n"
                + "/TeXDict 300 dict def\n"
                + "%%BeginProcSet: Ps.java\n"
                + "TeXDict begin /eop{}def end\n"
                + "%%EndProcSet\n"
                + "TeXDict begin\n eop\n"
                + "end\n"
                + "%%EOF\n", s.substring(88));
        pages.clear();
    }

    // TODO implement more test cases
}
