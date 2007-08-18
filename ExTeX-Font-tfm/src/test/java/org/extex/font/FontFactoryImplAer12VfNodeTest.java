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

import org.extex.core.UnicodeChar;
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
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("aer12");

            font = factory.getInstance(key);
        }
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
