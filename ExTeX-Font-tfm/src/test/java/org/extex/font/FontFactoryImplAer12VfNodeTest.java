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

package org.extex.font;

import java.util.HashMap;
import java.util.Map;

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.exception.FontException;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.CharNodeBuilder;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.impl.FontImpl;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.KernNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.factory.SimpleNodeFactory;

/**
 * Test for the font factory (vf with creation nodes).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplAer12VfNodeTest extends AbstractFontFactoryTester {

    /**
     * The font.
     */
    private static ExtexFont font;

    /**
     * The font key.
     */
    private static FontKey key;

    private CoreFontFactory factory;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws FontException if a font error occurred.
     */
    public FontFactoryImplAer12VfNodeTest()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            factory = makeFontFactory();
            key = factory.getFontKey("aer12");

            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the CharNodeBuilder.
     * 
     * @throws Exception if an error occurred.
     */
    public void test5() throws Exception {

        assertNotNull(font);
        assertNotNull(key);
        assertTrue(font instanceof CharNodeBuilder);

        FontImpl tcfont = new FontImpl(font);
        TypesettingContextFactory tcFactory = new TypesettingContextFactory();
        tcFactory.configure(ConfigurationFactory.newInstance("tc.xml"));
        TypesettingContext tc = tcFactory.initial();

        tc = tcFactory.newInstance(tc, tcfont);

        Node node =
                ((CharNodeBuilder) font)
                    .buildCharNode(UnicodeChar.get(014/* oct */), tc,
                        new SimpleNodeFactory(), tcFactory);

        assertNotNull("node", node);
        assertTrue("node class", node instanceof CharNode);
        assertTrue("node class", node instanceof VirtualCharNode);

        VirtualCharNode vnode = (VirtualCharNode) node;
        assertEquals(1, vnode.getNodes().size());

        // test aer12 Char 12 (14o): Width=169863, Height=0, Depth=152559, IC=0
        assertEquals(169863, vnode.getWidth().getValue());
        assertEquals(0, vnode.getHeight().getValue());
        assertEquals(152559, vnode.getDepth().getValue());

        Node xn = vnode.get(0);
        assertNotNull(xn);
        assertTrue(xn instanceof HorizontalListNode);
        HorizontalListNode hbox = (HorizontalListNode) xn;
        assertEquals(TfmFixWord.toDimen(font.getDesignSize(), 379585)
            .toString(), hbox.getShift().toString());

        assertEquals(1, hbox.size());

        Node cn = hbox.get(0);
        assertTrue(cn instanceof CharNode);
        CharNode charnode = (CharNode) cn;
        FontKey actualFontKey =
                charnode.getTypesettingContext().getFont().getActualFontKey();
        assertEquals("cmmi12", actualFontKey.getName());
        assertEquals("799", actualFontKey.getCount(FontKey.SCALE).toString());

        // cmmi
        Map<String, Count> map = new HashMap<String, Count>();
        map.put(FontKey.SCALE, new Count(799));
        FontKey cmmikey =
                factory.getFontKey("cmmi12", new Dimen(Dimen.ONE * 12), map);
        assertNotNull(cmmikey);
        ExtexFont cmmifont = factory.getInstance(cmmikey);
        assertNotNull(cmmifont);
        FixedGlue w = cmmifont.getWidth(UnicodeChar.get(Unicode.OFFSET + 44));
        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(170906)
            .eq(w));

        // test cmmi12 scaled 799 Char 44: Width=170906, Height=284942,
        // Depth=-29237, IC=0
        assertEquals(w.getLength().getValue(), charnode.getWidth().getValue());

    }

    /**
     * Test for the font key.
     * 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(font);
        assertNotNull(key);
    }

    /**
     * Test for the CharNodeBuilder.
     * 
     * @throws Exception if an error occurred.
     */
    public void test2() throws Exception {

        assertNotNull(font);
        assertNotNull(key);
        assertTrue(font instanceof CharNodeBuilder);

        TypesettingContextFactory tcFactory = new TypesettingContextFactory();
        tcFactory.configure(ConfigurationFactory.newInstance("tc.xml"));
        TypesettingContext tc = tcFactory.initial();
        Node node =
                ((CharNodeBuilder) font).buildCharNode(UnicodeChar.get(0xffff),
                    tc, new SimpleNodeFactory(), tcFactory);

        assertNull("node", node);
    }

    /**
     * Test for the CharNodeBuilder.
     * 
     * @throws Exception if an error occurred.
     */
    public void test3() throws Exception {

        assertNotNull(font);
        assertNotNull(key);
        assertTrue(font instanceof CharNodeBuilder);

        FontImpl tcfont = new FontImpl(font);
        TypesettingContextFactory tcFactory = new TypesettingContextFactory();
        tcFactory.configure(ConfigurationFactory.newInstance("tc.xml"));
        TypesettingContext tc = tcFactory.initial();

        tc = tcFactory.newInstance(tc, tcfont);

        Node node =
                ((CharNodeBuilder) font).buildCharNode(UnicodeChar.get(0), tc,
                    new SimpleNodeFactory(), tcFactory);

        assertNotNull("node", node);
        assertTrue("node class", node instanceof CharNode);
        assertTrue("node class", node instanceof VirtualCharNode);

        VirtualCharNode vnode = (VirtualCharNode) node;
        assertEquals(1, vnode.getNodes().size());

        Node cn = vnode.get(0);
        assertNotNull(cn);
        assertTrue(cn instanceof CharNode);

        // test aer12 Char 0: Width=384560, Height=541452, Depth=0, IC=0
        // -> cmr12 18d
        assertEquals(384560, vnode.getWidth().getValue());
        assertEquals(541452, vnode.getHeight().getValue());
        assertEquals(0, vnode.getDepth().getValue());

        CharNode charnode = (CharNode) cn;
        assertNotNull(charnode);

        UnicodeChar uc = charnode.getCharacter();
        assertNotNull(uc);
        // # 96 Grave accent [18]
        // 0060=12

        assertEquals(96, uc.getCodePoint());
    }

    /**
     * Test for the CharNodeBuilder.
     * 
     * @throws Exception if an error occurred.
     */
    public void test4() throws Exception {

        assertNotNull(font);
        assertNotNull(key);
        assertTrue(font instanceof CharNodeBuilder);

        FontImpl tcfont = new FontImpl(font);
        TypesettingContextFactory tcFactory = new TypesettingContextFactory();
        tcFactory.configure(ConfigurationFactory.newInstance("tc.xml"));
        TypesettingContext tc = tcFactory.initial();

        tc = tcFactory.newInstance(tc, tcfont);

        Node node =
                ((CharNodeBuilder) font).buildCharNode(UnicodeChar.get(6), tc,
                    new SimpleNodeFactory(), tcFactory);

        assertNotNull("node", node);
        assertTrue("node class", node instanceof CharNode);
        assertTrue("node class", node instanceof VirtualCharNode);

        VirtualCharNode vnode = (VirtualCharNode) node;
        assertEquals(3, vnode.getNodes().size());

        // test aer12 Char 6: Width=0, Height=541452, Depth=0, IC=0
        assertEquals(0, vnode.getWidth().getValue());
        assertEquals(541452, vnode.getHeight().getValue());
        assertEquals(0, vnode.getDepth().getValue());

        Node xn = vnode.get(0);
        assertNotNull(xn);
        assertTrue(xn instanceof KernNode);
        KernNode kn = (KernNode) xn;
        assertEquals(TfmFixWord.toDimen(font.getDesignSize(), -384821)
            .toString(), kn.getWidth().toString());

        xn = vnode.get(1);
        assertNotNull(xn);
        assertTrue(xn instanceof CharNode);

        CharNode cn = (CharNode) xn;
        UnicodeChar uc = cn.getCharacter();
        assertNotNull(uc);
        // vf 6 -> 27o / 23d
        assertEquals(23, uc.getCodePoint());

        xn = vnode.get(2);
        assertNotNull(xn);
        assertTrue(xn instanceof KernNode);
        kn = (KernNode) xn;
        assertEquals(TfmFixWord.toDimen(font.getDesignSize(), -384821)
            .toString(), kn.getWidth().toString());

    }

}
