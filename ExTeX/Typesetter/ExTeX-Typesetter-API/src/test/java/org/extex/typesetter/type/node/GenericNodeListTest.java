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

package org.extex.typesetter.type.node;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.glue.GlueComponent;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the test suite for a horizontal list node.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
@SuppressWarnings("RedundantThrows")
public class GenericNodeListTest extends AbstractNodeListTester {

  /**
   * The constant {@code VISITOR} contains the a node visitor which return a
   * horizontal list node only. The other nodes are mapped to
   * {@code null}.
   */
  private static final NodeVisitor<Object, Object> VISITOR =
      new NodeVisitor<Object, Object>() {

        @Override
        public Object visitAdjust( AdjustNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitAfterMath( AfterMathNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitAlignedLeaders( AlignedLeadersNode node,
                                           Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitBeforeMath( BeforeMathNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitCenteredLeaders( CenteredLeadersNode node,
                                            Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitChar( CharNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitDiscretionary( DiscretionaryNode node,
                                          Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitExpandedLeaders( ExpandedLeadersNode node,
                                            Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitGlue( GlueNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitHorizontalList( HorizontalListNode node,
                                           Object value )
            throws GeneralException {

          return node;
        }

        @Override
        public Object visitInsertion( InsertionNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitKern( KernNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitLigature( LigatureNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitMark( MarkNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitPenalty( PenaltyNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitRule( RuleNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitSpace( SpaceNode node, Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitVerticalList( VerticalListNode node,
                                         Object value )
            throws GeneralException {

          return null;
        }

        @Override
        public Object visitVirtualChar( VirtualCharNode node,
                                        Object value ) throws GeneralException {

          return null;
        }

        @Override
        public Object visitWhatsIt( WhatsItNode node, Object value )
            throws GeneralException {

          return null;
        }
      };

  @Override
  protected NodeList makeList() {

    return new GenericNodeList();
  }

  @Override
  protected NodeList makeList( Node node ) {

    GenericNodeList list = new GenericNodeList();
    list.add( node );
    return list;
  }

  @Override
  protected NodeVisitor<Node, Boolean> makeVisitor() {

    return null;
  }

  /**
   * Adding a rule node puts the node into the list.
   */
  @Test
  public void testAddNode111() {

    NodeList list = makeList();
    list.add( new RuleNode( new Dimen( 2 * Dimen.ONE ),
                            new Dimen( 3 * Dimen.ONE ),
                            new Dimen( 4 * Dimen.ONE ),
                            null,
                            true ) );
    assertList( list, 1, 0, 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#addSkip(org.extex.core.glue.FixedGlue)}
   * .
   */
  @Test
  public final void testAddSkip0() {

    try {
      makeList().addSkip( FixedGlue.ZERO );
      fail();
    } catch( Exception e ) {
      assertTrue( true );
    }
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode()}
   * .
   */
  @Test
  public final void testHorizontalListNode() {

    NodeList list = makeList();
    assertTrue( list.isEmpty() );
    assertList( list, 0, 0, 0, 0, 0, 0 );
    assertEquals( 0L, list.getVerticalSize().getValue() );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.core.dimen.FixedDimen)}
   * .
   */
  @Test
  public final void testHorizontalListNodeFixedDimen() {

    HorizontalListNode list = new HorizontalListNode( Dimen.ONE_SP );
    assertList( list, 0, Dimen.ONE_SP.getValue(), 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.typesetter.type.Node)}
   * .
   */
  @Test
  public final void testHorizontalListNodeNode1() {

    HorizontalListNode list = new HorizontalListNode( new PenaltyNode( 123 ) );
    assertList( list, 1, 0, 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.typesetter.type.Node)}
   * .
   */
  @Test
  public final void testHorizontalListNodeNode2() {

    HorizontalListNode list =
        new HorizontalListNode( new RuleNode( new Dimen( 1 ), new Dimen( 2 ),
                                              new Dimen( 3 ), TC, true ) );
    assertList( list, 1, 1, 2, 3, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.typesetter.type.Node, org.extex.typesetter.type.Node)}
   * .
   */
  @Test
  public final void testHorizontalListNodeNodeNode() {

    HorizontalListNode list =
        new HorizontalListNode( new PenaltyNode( 123 ), new PenaltyNode(
            456 ) );
    assertFalse( list.isEmpty() );
    assertList( list, 2, 0, 0, 0, 0, 0 );
  }

  // TODO gene more test cases for hpack()

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack()}.
   */
  @Test
  public final void testHpack0() {

    HorizontalListNode list = new HorizontalListNode();
    list.hpack();
    assertList( list, 0, 0, 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
   * .
   */
  @Test
  public final void testHpackFixedDimen0() {

    HorizontalListNode list = new HorizontalListNode();
    list.hpack( null );
    assertList( list, 0, 0, 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
   * .
   */
  @Test
  public final void testHpackFixedDimen1() {

    HorizontalListNode list = new HorizontalListNode();
    list.hpack( Dimen.ZERO_PT );
    assertList( list, 0, 0, 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
   * .
   */
  @Test
  public final void testHpackFixedDimen2() {

    HorizontalListNode list = new HorizontalListNode();
    list.hpack( Dimen.ONE_PT );
    assertList( list, 0, Dimen.ONE, 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
   * .
   */
  @Test
  public final void testHpackFixedDimen3() {

    HorizontalListNode list = new HorizontalListNode();
    list.add( new PenaltyNode( 123 ) );
    list.hpack( Dimen.ZERO_PT );
    assertList( list, 1, 0, 0, 0, 0, 0 );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
   * .
   */
  @Test
  public final void testHpackFixedDimen4() {

    HorizontalListNode list = new HorizontalListNode();
    GlueNode n =
        new GlueNode( new Glue( Dimen.ONE_INCH, GlueComponent.ONE_FIL,
                                GlueComponent.ONE_FIL ), true );
    list.add( n );
    list.hpack( Dimen.ZERO_PT );
    assertList( list, 1, 0, 0, 0, 0, 0 );
    assertEquals( 0L, n.getWidth().getValue() );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
   * .
   */
  @Test
  public final void testHpackFixedDimen5() {

    HorizontalListNode list = new HorizontalListNode();
    GlueNode n =
        new GlueNode( new Glue( Dimen.ONE_INCH, GlueComponent.ONE_FIL,
                                GlueComponent.ONE_FIL ), true );
    list.add( n );
    Dimen twoInch = new Dimen( 2 * Dimen.ONE_INCH.getValue() );
    list.hpack( twoInch );
    assertList( list, 1, twoInch.getValue(), 0, 0, 0, 0 );
    assertEquals( twoInch.getValue(), n.getWidth().getValue() );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#spreadWidth(org.extex.core.dimen.FixedDimen, org.extex.core.glue.FixedGlueComponent)}
   * .
   */
  @Test
  public final void testSpreadWidth() {

    HorizontalListNode list = new HorizontalListNode();
    list.spreadWidth( Dimen.ONE_SP, GlueComponent.ONE_FI );
    assertList( list, 0, 0, 0, 0, 0, 0 );
  }

  /**
   *
   */
  @Test
  public final void testToString1() {

    NodeList list = makeList();
    assertEquals( "(0.0pt+0.0pt)x0.0pt", list.toString() );
  }

  /**
   *
   */
  @Test
  public final void testToString2() {

    NodeList list = makeList();
    list.setShift( Dimen.ONE_PT );
    assertEquals( "(0.0pt+0.0pt)x0.0pt, shifted 1.0pt", list.toString() );
  }

  /**
   *
   */
  @Test
  public final void testToString3() {

    NodeList list = makeList();
    list.setMove( Dimen.ONE_PT );
    assertEquals( "(0.0pt+0.0pt)x0.0pt, moved 1.0pt", list.toString() );
  }

  /**
   *
   */
  @Test
  public final void testToString4() {

    NodeList list = makeList();
    list.setShift( Dimen.ONE_PT );
    list.setMove( Dimen.ONE_PT );
    assertEquals( "(0.0pt+0.0pt)x0.0pt, shifted 1.0pt, moved 1.0pt",
                  list.toString() );
  }

  /**
   *
   */
  @Test
  public void testToString5() {

    NodeList list = makeList( new CharNode( TC, UnicodeChar.get( 'A' ) ) );
    list.setMove( Dimen.ONE_PT );
    assertEquals( "(0.0pt+0.0pt)x0.0pt, moved 1.0pt\n.A", list.toString() );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#toString(StringBuilder, java.lang.String, int, int)}
   * .
   */
  @Test
  public final void testToStringStringBuilderStringIntInt() {

    NodeList list = makeList( new CharNode( TC, UnicodeChar.get( 'A' ) ) );

    StringBuilder sb = new StringBuilder();
    list.toString( sb, ":", 0, 0 );
    assertEquals( "(0.0pt+0.0pt)x0.0pt", sb.toString() );
  }

  /**
   *
   */
  @Test
  public final void testToText1() {

    NodeList list = makeList( new CharNode( TC, UnicodeChar.get( 'A' ) ) );
    list.setMove( Dimen.ONE_PT );
    assertEquals( "\\test A)", list.toText() );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#toText(StringBuilder, java.lang.String)}
   * .
   */
  @Test
  public final void testToTextStringBuilderrString0() {

    NodeList list = makeList();
    StringBuilder sb = new StringBuilder();
    list.toText( sb, ".." );
    assertEquals( ")", sb.toString() );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#toText(StringBuilder, java.lang.String)}
   * .
   */
  @Test
  public final void testToTextStringBuilderString1() {

    NodeList list = makeList( new PenaltyNode( 123 ) );
    StringBuilder sb = new StringBuilder();
    list.toText( sb, ".." );
    assertEquals( "[])", sb.toString() );
  }

  /**
   * Test method for
   * {@link org.extex.typesetter.type.node.HorizontalListNode#visit(NodeVisitor, Object)
   * )} .
   *
   * @throws GeneralException in case of an error
   */
  @Test(expected = ImpossibleException.class)
  public final void testVisit1() throws GeneralException {

    makeList().visit( VISITOR, null );
  }

}
