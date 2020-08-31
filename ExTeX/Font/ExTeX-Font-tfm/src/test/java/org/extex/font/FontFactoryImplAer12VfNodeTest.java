/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
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
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.factory.SimpleNodeFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test for the font factory (vf with creation nodes).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
@SuppressWarnings("OctalInteger")
public class FontFactoryImplAer12VfNodeTest extends AbstractFontFactoryTester {

  /**
   * The factory.
   */
  private static CoreFontFactory factory;

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
   * @throws FontException          if a font error occurred.
   */
  public FontFactoryImplAer12VfNodeTest()
      throws ConfigurationException,
      FontException {

    if( factory == null ) {
      factory = makeFontFactory();
      key = factory.getFontKey( "aer12" );
      font = factory.getInstance( key );
    }
  }

  /**
   * Test for the font key.
   */
  @Test
  public void test01() {

    assertNotNull( font );
    assertNotNull( key );
  }

  /**
   * Test for the CharNodeBuilder.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void test2() throws Exception {

    assertNotNull( font );
    assertNotNull( key );
    assertTrue( font instanceof CharNodeBuilder );

    TypesettingContextFactory tcFactory = new TypesettingContextFactory();
    tcFactory.configure( ConfigurationFactory.newInstance( "tc.xml" ) );
    TypesettingContext tc = tcFactory.initial();
    Node node =
        ((CharNodeBuilder) font).buildCharNode( UnicodeChar.get( 0xffff ),
                                                tc,
                                                new SimpleNodeFactory(),
                                                tcFactory );

    assertNull( "node", node );
  }

  /**
   * Test for the CharNodeBuilder.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  @Ignore
  public void test3() throws Exception {

    assertNotNull( font );
    assertNotNull( key );
    assertTrue( font instanceof CharNodeBuilder );

    FontImpl tcfont = new FontImpl( font );
    TypesettingContextFactory tcFactory = new TypesettingContextFactory();
    tcFactory.configure( ConfigurationFactory.newInstance( "tc.xml" ) );
    TypesettingContext tc = tcFactory.initial();

    tc = tcFactory.newInstance( tc, tcfont );

    Node node =
        ((CharNodeBuilder) font).buildCharNode( UnicodeChar.get( 0 ),
                                                tc,
                                                new SimpleNodeFactory(),
                                                tcFactory );

    assertNotNull( "node", node );
    assertTrue( "node class", node instanceof CharNode );
    assertTrue( "node class", node instanceof VirtualCharNode );

    VirtualCharNode vnode = (VirtualCharNode) node;
    assertEquals( 1, vnode.getNodes().size() );

    Node cn = vnode.get( 0 );
    assertNotNull( cn );
    assertTrue( cn instanceof CharNode );

    // test aer12 Char 0: Width=384560, Height=541452, Depth=0, IC=0
    // -> cmr12 18d
    assertEquals( 384560, vnode.getWidth().getValue() );
    assertEquals( 541452, vnode.getHeight().getValue() );
    assertEquals( 0, vnode.getDepth().getValue() );

    CharNode charnode = (CharNode) cn;
    assertNotNull( charnode );

    UnicodeChar uc = charnode.getCharacter();
    assertNotNull( uc );
    // # 96 Grave accent [18]
    // 0060=12

    assertEquals( 96, uc.getCodePoint() );
  }

  /**
   * Test for the CharNodeBuilder.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  @Ignore
  public void test4() throws Exception {

    assertNotNull( font );
    assertNotNull( key );
    assertTrue( font instanceof CharNodeBuilder );

    FontImpl tcfont = new FontImpl( font );
    TypesettingContextFactory tcFactory = new TypesettingContextFactory();
    tcFactory.configure( ConfigurationFactory.newInstance( "tc.xml" ) );
    TypesettingContext tc = tcFactory.initial();

    tc = tcFactory.newInstance( tc, tcfont );

    Node node =
        ((CharNodeBuilder) font).buildCharNode( UnicodeChar.get( 6 ),
                                                tc,
                                                new SimpleNodeFactory(),
                                                tcFactory );

    assertNotNull( "node", node );
    assertTrue( "node class", node instanceof CharNode );
    assertTrue( "node class", node instanceof VirtualCharNode );

    VirtualCharNode vnode = (VirtualCharNode) node;
    assertEquals( 1, vnode.getNodes().size() );

    // test aer12 Char 6: Width=0, Height=541452, Depth=0, IC=0
    assertEquals( 0, vnode.getWidth().getValue() );
    assertEquals( 541452, vnode.getHeight().getValue() );
    assertEquals( 0, vnode.getDepth().getValue() );

    Node xn = vnode.get( 0 );
    assertNotNull( xn );
    assertTrue( xn instanceof HorizontalListNode );

    HorizontalListNode hbox = (HorizontalListNode) xn;
    assertEquals( 1, hbox.size() );
    assertEquals( TfmFixWord.toDimen( font.getDesignSize(), -384821 )
                            .toString(), hbox.getMove().toString() );

    assertEquals( TfmFixWord.toDimen( font.getDesignSize(), -384821 )
                            .toString(), hbox.getMove().toString() );

    // cmr12
    FontKey cmrkey = factory.getFontKey( "cmr12", new Dimen( Dimen.ONE * 12 ) );
    assertNotNull( cmrkey );
    ExtexFont cmrfont = factory.getInstance( cmrkey );
    assertNotNull( cmrfont );
    FixedGlue w = cmrfont.getWidth( UnicodeChar.get( Unicode.OFFSET + 23 ) );
    assertNotNull( w );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 577257 ).eq( w ) );

    assertEquals( w.getLength().getValue(), hbox.getWidth().getValue() );

  }

  /**
   * Test for the CharNodeBuilder.
   */
  @Test
  @Ignore
  public void test5() {

    assertNotNull( font );
    assertNotNull( key );
    assertTrue( font instanceof CharNodeBuilder );

    FontImpl tcfont = new FontImpl( font );
    TypesettingContextFactory tcFactory = new TypesettingContextFactory();
    tcFactory.configure( ConfigurationFactory.newInstance( "tc.xml" ) );
    TypesettingContext tc = tcFactory.initial();

    tc = tcFactory.newInstance( tc, tcfont );

    Node node =
        ((CharNodeBuilder) font).buildCharNode(
            UnicodeChar.get( 014/* oct */ ), tc, new SimpleNodeFactory(),
            tcFactory );

    assertNotNull( "node", node );
    assertTrue( "node class", node instanceof CharNode );
    assertTrue( "node class", node instanceof VirtualCharNode );

    VirtualCharNode vnode = (VirtualCharNode) node;
    assertEquals( 1, vnode.getNodes().size() );

    // test aer12 Char 12 (14o): Width=169863, Height=0, Depth=152559, IC=0
    assertEquals( 169863, vnode.getWidth().getValue() );
    assertEquals( 0, vnode.getHeight().getValue() );
    assertEquals( 152559, vnode.getDepth().getValue() );

    Node xn = vnode.get( 0 );
    assertNotNull( xn );
    assertTrue( xn instanceof HorizontalListNode );
    HorizontalListNode hbox = (HorizontalListNode) xn;
    assertEquals( TfmFixWord.toDimen( font.getDesignSize(), 379585 )
                            .toString(), hbox.getShift().toString() );

    assertEquals( 1, hbox.size() );

    Node cn = hbox.get( 0 );
    assertTrue( cn instanceof CharNode );
    CharNode charnode = (CharNode) cn;
    FontKey actualFontKey =
        charnode.getTypesettingContext().getFont().getActualFontKey();
    assertEquals( "cmmi12", actualFontKey.getName() );
    assertEquals( "799", actualFontKey.getCount( FontKey.SCALE ).toString() );

  }

  /**
   * Test for the CharNodeBuilder.
   */
  @Test
  @Ignore
  public void test6() {

    assertNotNull( font );
    assertNotNull( key );
    assertTrue( font instanceof CharNodeBuilder );

    FontImpl tcfont = new FontImpl( font );
    TypesettingContextFactory tcFactory = new TypesettingContextFactory();
    tcFactory.configure( ConfigurationFactory.newInstance( "tc.xml" ) );
    TypesettingContext tc = tcFactory.initial();

    tc = tcFactory.newInstance( tc, tcfont );

    Node node =
        ((CharNodeBuilder) font).buildCharNode(
            UnicodeChar.get( 040/* oct */ ), tc, new SimpleNodeFactory(),
            tcFactory );

    assertNotNull( "node", node );
    assertTrue( "node class", node instanceof CharNode );
    assertTrue( "node class", node instanceof VirtualCharNode );

    VirtualCharNode vnode = (VirtualCharNode) node;
    assertEquals( 3, vnode.getNodes().size() );

    // test aer12 Char 32: Width=456128, Height=0, Depth=157280, IC=0
    assertEquals( 456128, vnode.getWidth().getValue() );
    assertEquals( 0, vnode.getHeight().getValue() );
    assertEquals( 157280, vnode.getDepth().getValue() );

    Node box1 = vnode.get( 0 );
    assertNotNull( box1 );
    assertTrue( box1 instanceof HorizontalListNode );
    HorizontalListNode hbox1 = (HorizontalListNode) box1;

    // (MOVEDOWN R 0.199992) * 12pt = 2.399904 pt
    // (MOVERIGHT R 0.049998) * 12pt = 0.599976 pt
    // (SETRULE R 0.199992 R 0.039998) = h 2.399904pt x w 0.479976pt
    // (SETRULE R 0.039998 R 0.399994) = h 0.479976pt x w 4.799928pt
    // (SETRULE R 0.199992 R 0.039998) = h 2.399904pt x w 0.479976pt
    // (MOVERIGHT R 0.049998)
    // (MOVEDOWN R -0.199992)

    assertEquals( new Dimen( (long) (Dimen.ONE * 2.399904) ).getValue(), hbox1
        .getShift().getValue() );
    assertEquals( new Dimen( (long) (Dimen.ONE * 0.599976) ).getValue(), hbox1
        .getMove().getValue() );
    assertEquals( new Dimen( (long) (Dimen.ONE * 0.479976) ).getValue(), hbox1
        .getWidth().getValue() );
    assertEquals( new Dimen( (long) (Dimen.ONE * 2.399904) ).getValue(), hbox1
        .getHeight().getValue() );

    assertEquals(
        "\\hbox(2.3999pt+0.0pt)x0.47997pt, shifted 2.3999pt, moved 0.59998pt\n"
            + ".\\rule2.3999pt+0.0ptx0.47997pt",
        box1.toString() );

    Node box2 = vnode.get( 1 );
    assertNotNull( box2 );
    assertTrue( box2 instanceof HorizontalListNode );

    assertEquals(
        "\\hbox(0.47997pt+0.0pt)x4.79993pt, shifted 2.3999pt, moved 0.59999pt\n"
            + ".\\rule0.47997pt+0.0ptx4.79993pt",
        box2.toString() );

    Node box3 = vnode.get( 2 );
    assertNotNull( box3 );
    assertTrue( box3 instanceof HorizontalListNode );

    assertEquals(
        "\\hbox(2.3999pt+0.0pt)x0.47997pt, shifted 2.3999pt, moved 0.59999pt\n"
            + ".\\rule2.3999pt+0.0ptx0.47997pt",
        box3.toString() );

  }

  /**
   * Test for the CharNodeBuilder.
   */
  @Test
  @Ignore
  public void test7() {

    assertNotNull( font );
    assertNotNull( key );
    assertTrue( font instanceof CharNodeBuilder );

    FontImpl tcfont = new FontImpl( font );
    TypesettingContextFactory tcFactory = new TypesettingContextFactory();
    tcFactory.configure( ConfigurationFactory.newInstance( "tc.xml" ) );
    TypesettingContext tc = tcFactory.initial();

    tc = tcFactory.newInstance( tc, tcfont );

    Node node =
        ((CharNodeBuilder) font).buildCharNode(
            UnicodeChar.get( 0200/* oct */ ), tc,
            new SimpleNodeFactory(), tcFactory );

    assertNotNull( "node", node );
    assertTrue( "node class", node instanceof CharNode );
    assertTrue( "node class", node instanceof VirtualCharNode );

    VirtualCharNode vnode = (VirtualCharNode) node;
    assertEquals( 2, vnode.getNodes().size() );

    // test aer12 Char 128: Width=577239, Height=744744, Depth=0, IC=0
    assertEquals( 577239, vnode.getWidth().getValue() );
    assertEquals( 744744, vnode.getHeight().getValue() );
    assertEquals( 0, vnode.getDepth().getValue() );

    Node box1 = vnode.get( 0 );
    assertNotNull( box1 );
    assertTrue( box1 instanceof HorizontalListNode );
    HorizontalListNode hbox1 = (HorizontalListNode) box1;

    // (PUSH)
    // (MOVEDOWN R -0.252991) = -3.035892pt
    // (MOVERIGHT R 0.122996) = 1.475952pt
    // (SETCHAR O 25) (21d)
    // test cmr12 Char 21: Width=385020 (5.87494pt) , Height=546132
    // (8.33331pt), Depth=0, IC=0
    // (POP)
    // (SETCHAR C A) (65d)
    // test cmr12 Char 65: Width=577257 (8.80824pt), Height=537395 (8.2pt),
    // Depth=0, IC=0

    assertEquals(
        "\\hbox(8.33331pt+0.0pt)x5.87494pt, shifted -3.03589pt, moved 1" +
            ".47595pt\n.\025",
        hbox1.toString() );

    Node box2 = vnode.get( 1 );
    assertNotNull( box2 );
    assertTrue( box2 instanceof HorizontalListNode );
    HorizontalListNode hbox2 = (HorizontalListNode) box2;

    // TODO mgn: A 65 A 913 ???
    assertEquals(
        "\\hbox(8.2pt+0.0pt)x8.80824pt, moved -5.87494pt\n" + ".A",
        hbox2.toString() );

  }

  /**
   * Test for the CharNodeBuilder.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  @Ignore
  public void test8() throws Exception {

    assertNotNull( font );
    assertNotNull( key );
    assertTrue( font instanceof CharNodeBuilder );

    FontImpl tcfont = new FontImpl( font );
    TypesettingContextFactory tcFactory = new TypesettingContextFactory();
    tcFactory.configure( ConfigurationFactory.newInstance( "tc.xml" ) );
    TypesettingContext tc = tcFactory.initial();

    tc = tcFactory.newInstance( tc, tcfont );

    Node node =
        ((CharNodeBuilder) font).buildCharNode(
            UnicodeChar.get( 0201/* oct */ ), tc,
            new SimpleNodeFactory(), tcFactory );

    assertNotNull( "node", node );
    assertTrue( "node class", node instanceof CharNode );
    assertTrue( "node class", node instanceof VirtualCharNode );

    VirtualCharNode vnode = (VirtualCharNode) node;
    assertEquals( 2, vnode.getNodes().size() );

    // test aer12 Char 129: Width=577239, Height=541452, Depth=152559,
    // IC=0
    assertEquals( 577239, vnode.getWidth().getValue() );
    assertEquals( 541452, vnode.getHeight().getValue() );
    assertEquals( 152559, vnode.getDepth().getValue() );

    Node box1 = vnode.get( 0 );
    assertNotNull( box1 );
    assertTrue( box1 instanceof HorizontalListNode );
    HorizontalListNode hbox1 = (HorizontalListNode) box1;

    // (PUSH)
    // (MOVERIGHT R 0.478993) = 5.747916pt
    // (MOVEDOWN R 0.362) = 4.344pt
    // (SELECTFONT D 1) (cmmi12)
    // (SETCHAR O 54) 44d
    // test cmmi12 scaled 799 Char 44: Width=170906 2.60782pt, Height=284942
    // 4.34787pt,
    // Depth=-29237 -0.44612pt, IC=0
    // (POP)
    // (SELECTFONT D 0) (cmr12)
    // (SETCHAR C A) 65d
    // test cmr12 Char 65: Width=577257, Height=537395, Depth=0, IC=0

    // cmmi12 --------------------
    Map<String, Count> map = new HashMap<>();
    map.put( FontKey.SCALE, new Count( 799 ) );
    FontKey xkey =
        factory.getFontKey( "cmmi12", new Dimen( Dimen.ONE * 12 ), map );
    ExtexFont xfont = factory.getInstance( xkey );
    FixedGlue h = xfont.getHeight( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    FixedGlue w = xfont.getWidth( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    FixedGlue d = xfont.getDepth( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    FixedDimen i =
        xfont.getItalicCorrection( UnicodeChar.get( Unicode.OFFSET + 44 ) );
    assertNotNull( w );
    assertNotNull( h );
    assertNotNull( d );
    assertNotNull( i );
    assertTrue( Long.toString( w.getLength().getValue() ),
                new Glue( 170906 ).eq( w ) );
    assertTrue( Long.toString( h.getLength().getValue() ),
                new Glue( 284942 ).eq( h ) );
    assertTrue( Long.toString( d.getLength().getValue() ),
                new Glue( -29237 ).eq( d ) );
    assertTrue( Long.toString( i.getValue() ), new Dimen( 0 ).eq( i ) );
    // cmmi12 --------------------

    CharNode[] chars = hbox1.getChars();
    assertNotNull( chars );
    assertEquals( 1, chars.length );
    FixedDimen cw = chars[ 0 ].getWidth();
    FixedDimen ch = chars[ 0 ].getHeight();
    FixedDimen cd = chars[ 0 ].getDepth();

    assertTrue( Long.toString( cw.getValue() ), new Dimen( 170906 ).eq( cw ) );
    assertTrue( Long.toString( ch.getValue() ), new Dimen( 284942 ).eq( ch ) );
    assertTrue( Long.toString( cd.getValue() ), new Dimen( -29237 ).eq( cd ) );

    assertEquals( "hbox: h+d+w",
                  "\\hbox(4.34787pt+0.0pt)x2.60782pt, shifted 4.344pt, moved " +
                      "5.74791pt\n"
                      + ".,",
                  hbox1.toString() );

    // (PUSH)
    // (MOVERIGHT R 0.478993) = 5.747916pt
    // (MOVEDOWN R 0.362) = 4.344pt
    // (SELECTFONT D 1) (cmmi12)
    // (SETCHAR O 54) 44d
    // test cmmi12 scaled 799 Char 44: Width=170906 2.60782pt, Height=284942
    // 4.34787pt,
    // Depth=-29237 -0.44612pt, IC=0
    // (POP)
    // (SELECTFONT D 0) (cmr12)
    // (SETCHAR C A) 65d
    // test cmr12 Char 65: Width=577257 (8.80824pt), Height=537395 (8.2pt),
    // Depth=0, IC=0

    Node box2 = vnode.get( 1 );
    assertNotNull( box2 );
    assertTrue( box2 instanceof HorizontalListNode );
    HorizontalListNode hbox2 = (HorizontalListNode) box2;

    // TODO mgn: A 65 A 913 ???
    assertEquals( "hbox: h+d+w",
                  "\\hbox(8.2pt+0.0pt)x8.80824pt, moved -2.60782pt\n" + ".A",
                  hbox2.toString() );

  }
}
