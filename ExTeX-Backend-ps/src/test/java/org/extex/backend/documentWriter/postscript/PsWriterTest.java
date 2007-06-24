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
import java.util.Properties;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.engine.typesetter.page.PageImpl;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontFactoryImpl;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.resource.InteractionIndicator;
import org.extex.resource.ResourceFinderFactory;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.impl.FontImpl;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.HorizontalListNode;
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
     * Create and initialize a writer to be tested.
     * 
     * @param options the options
     * @param ff the font factory
     * 
     * @return the writer to test
     */
    private PsWriter makeWriter(DocumentWriterOptions options,
            CoreFontFactory ff) {

        PsWriter writer = new PsWriter(options);
        writer.setFontFactory(ff);
        return writer;
    }

    /**
     * Make the test font factory.
     * 
     * @return the font factory
     */
    private CoreFontFactory makeFontactory() {

        FontFactoryImpl factory = new FontFactoryImpl();
        factory.configure(ConfigurationFactory
            .newInstance("test/font/test-fonts"));
        factory.setProperties(new Properties());
        factory.setResourceFinder(new ResourceFinderFactory()
            .createResourceFinder(//
                ConfigurationFactory.newInstance("path/fontTestFileFinder"), //
                Logger.getLogger("test"), new Properties(), //
                new InteractionIndicator() {

                    public boolean isInteractive() {

                        return false;
                    }
                }));
        return factory;
    }

    /**
     * The constant <tt>OPTIONS</tt> contains the document writer options for
     * the tests.
     */
    private static final DocumentWriterOptions OPTIONS =
            new DocumentWriterOptions() {

                public FixedCount getCountOption(String name) {

                    return null;
                }

                public FixedDimen getDimenOption(String name) {

                    return null;
                }

                public long getMagnification() {

                    return 1000;
                }

                public String getTokensOption(String name) {

                    return null;
                }
            };

    /**
     * 
     */
    @Test
    public final void testgetExtension() {

        assertEquals("ps", new PsWriter(OPTIONS).getExtension());
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout1() throws Exception {

        PsWriter writer = new PsWriter(OPTIONS);
        assertEquals(0, writer.shipout(null));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testShipout2() throws Exception {

        PsWriter writer = new PsWriter(OPTIONS);
        Page page = new PageImpl(new VerticalListNode(), new Count[10]);
        assertEquals(1, writer.shipout(page));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testClose1() throws Exception {

        PsWriter writer = makeWriter(OPTIONS, makeFontactory());

        OutputStream out = new ByteArrayOutputStream();
        writer.setOutputStream(out);
        writer.shipout(new PageImpl(new VerticalListNode(), new Count[10]));
        writer.close();
        String s = out.toString().replaceAll("\r", "");
        assertEquals("%!PS-Adobe-2.0\n" //
                + "%%Creator: ExTeX-Backend-ps\n" //
                + "%%CreationDate: ", s.substring(0, 59));
        assertEquals("%%Title: \n" //
                + "%%Pages: 1\n" //
                + "%%PageOrder: Ascend\n" //
                + "%%BoundingBox: 0 0 596 842\n" //
                + "%%DocumentFonts:\n" //
                + "%%EndComments\n" //
                + "/TeXDict 300 dict def\n"
                + "%%BeginProcSet: Ps.java\n" //
                + "TeXDict begin /eop{}def end\n" //
                + "%%EndProcSet\n" //
                + "%%Page: 1 1\n" //
                + "TeXDict begin\n eop\nend\n" + "showpage\n" //
                + "%%EOF\n", //
            s.substring(79));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testClose3() throws Exception {

        CoreFontFactory fontFactory = makeFontactory();
        PsWriter writer = makeWriter(OPTIONS, fontFactory);

        OutputStream out = new ByteArrayOutputStream();
        writer.setOutputStream(out);
        VerticalListNode vlist = new VerticalListNode();
        HorizontalListNode hlist = new HorizontalListNode();
        vlist.add(hlist);
        TypesettingContextFactory tcf = new TypesettingContextFactory();
        tcf.configure(ConfigurationFactory.newInstance("test/tc/tc"));
        TypesettingContext tc =
                tcf.newInstance(tcf.initial(), new FontImpl(fontFactory
                    .getInstance(fontFactory.getFontKey("cmr10"))));
        hlist.add(new CharNode(tc, UnicodeChar.get('A')));

        writer.shipout(new PageImpl(vlist, new Count[10]));
        writer.close();

        String s = out.toString().replaceAll("\r", "");
        assertEquals("%!PS-Adobe-2.0\n" //
                + "%%Creator: ExTeX-Backend-ps\n" //
                + "%%CreationDate: ", s.substring(0, 59));
        assertEquals(
            "%%Title: \n" //
                    + "%%Pages: 1\n" //
                    + "%%PageOrder: Ascend\n" //
                    + "%%BoundingBox: 0 0 596 842\n" //
                    + "%%DocumentFonts:\n" //
                    + "%%EndComments\n" //
                    + "/TeXDict 300 dict def\n"
                    + "%%BeginProcSet: Ps.java\n" //
                    + "TeXDict begin /Cg{setgray}def /eop{}def end\n"
                    + "%%EndProcSet\n" //
                    + "%%Page: 1 1\n" //
                    + "TeXDict begin\n" //
                    + " 0.0 Cg A\n" //
                    + "end\n" + "showpage\n" + "%%EOF\n", s.substring(79));
    }

    // TODO implement more test cases
}
