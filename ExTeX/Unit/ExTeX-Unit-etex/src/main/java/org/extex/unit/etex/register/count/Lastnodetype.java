/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.count;

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;
import org.extex.unit.tex.register.count.AbstractReadonlyCount;

/**
 * This class provides an implementation for the primitive
 * {@code \lastnodetype}.
 *
 * <p>The Primitive {@code \lastnodetype}</p>
 * <p>
 * The primitive {@code \lastnodetype} inspects the last node in the
 * typesetter and returns a count value according to the node type found. The
 * following table shows the values returned:
 * </p>
 * <table> <caption>TBD</caption>
 * <tr><td>-1</td><td>no node; i.e. the list is empty</td></tr>
 * <tr><td>0</td><td>char node</td></tr>
 * <tr><td>1</td><td>hlist node</td></tr>
 * <tr><td>2</td><td>vlist node</td></tr>
 * <tr><td>3</td><td>rule node</td></tr>
 * <tr><td>4</td><td>insertion node</td></tr>
 * <tr><td>5</td><td>mark node</td></tr>
 * <tr><td>6</td><td>adjust node</td></tr>
 * <tr><td>7</td><td>ligature node</td></tr>
 * <tr><td>8</td><td>discretionary node</td></tr>
 * <tr><td>9</td><td>whatsit node</td></tr>
 * <tr><td>10</td><td>math node</td></tr>
 * <tr><td>11</td><td>glue node</td></tr>
 * <tr><td>12</td><td>kern node</td></tr>
 * <tr><td>13</td><td>penalty node</td></tr>
 * <tr><td>14</td><td>unset node (not used in ExTeX)</td></tr>
 * <tr><td>15</td><td>math mode nodes</td></tr>
 * </table>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;lastnodetype&rang;
 *       &rarr; {@code \lastnodetype}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \count42=\lastnodetype  </pre>
 *  <pre class="TeXSample">
 *    Test\the\lastnodetype  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 */

public class Lastnodetype extends AbstractReadonlyCount {

  /**
   * A class for getting the type number of an node.
   */
  static class NodetypeReader implements NodeVisitor<Integer, Object> {

    /**
     * Type number for an empty list.
     */
    private static final int NODE_TYPE_EMPTY_LIST = -1;

    /**
     * Type number for adjust nodes.
     */
    private static final Integer NODETYPE_ADJUST = Integer.valueOf( 6 );

    /**
     * Type number for aftermath nodes.
     */
    private static final Integer NODETYPE_AFTERMATH = Integer.valueOf( 10 );

    /**
     * Type number for aligned leaders nodes.
     */
    // TODO
    // private static final int NODETYPE_ALIGNEDLEADERS = 4444;
    /**
     * Type number for before math nodes.
     */
    private static final Integer NODETYPE_BEFOREMATH = Integer.valueOf( 10 );

    /**
     * Type number for centered leaders nodes.
     */
    // TODO
    // private static final int NODETYPE_CENTEREDLEADERS = 4444;
    /**
     * Type number for char nodes.
     */
    private static final Integer NODETYPE_CHAR = Integer.valueOf( 0 );

    /**
     * Type number for discretionary nodes.
     */
    private static final Integer NODETYPE_DISCRETIONARY =
        Integer.valueOf( 8 );

    /**
     * Type number for expanded leaders nodes.
     */
    // TODO
    // private static final int NODETYPE_EXPANDEDLEADERS = 4444;
    /**
     * Type number for glue nodes.
     */
    private static final Integer NODETYPE_GLUE = Integer.valueOf( 11 );

    /**
     * Type number for horizontal list nodes.
     */
    private static final Integer NODETYPE_HORIZONTALLIST =
        Integer.valueOf( 1 );

    /**
     * Type number for insertion nodes.
     */
    private static final Integer NODETYPE_INSERTION = Integer.valueOf( 4 );

    /**
     * Type number for kern nodes.
     */
    private static final Integer NODETYPE_KERN = Integer.valueOf( 12 );

    /**
     * Type number for ligature nodes.
     */
    private static final Integer NODETYPE_LIGATURE = Integer.valueOf( 7 );

    /**
     * Type number for mark nodes.
     */
    private static final Integer NODETYPE_MARK = Integer.valueOf( 5 );

    /**
     * Type number for penalty nodes.
     */
    private static final Integer NODETYPE_PENALTY = Integer.valueOf( 13 );

    /**
     * Type number for rule nodes.
     */
    private static final Integer NODETYPE_RULE = Integer.valueOf( 3 );

    /**
     * Type number for vertical list nodes.
     */
    private static final Integer NODETYPE_VERTICALLIST = Integer.valueOf( 2 );

    /**
     * Type number for whatsit nodes.
     */
    private static final Integer NODETYPE_WHATSIT = Integer.valueOf( 9 );

    /**
     * Returns the Node for an specified node.
     *
     * @param node a {@code Node} value
     * @return the type of the node
     * @throws HelpingException if an error occurs
     */
    public int getNodetype( Node node ) throws HelpingException {

      if( node == null ) {
        return NODE_TYPE_EMPTY_LIST;
      }

      try {
        return ((Integer) node.visit( this, null )).intValue();
      } catch( GeneralException e ) {
        // this should not happen
        throw new NoHelpException( e );
      }
    }

    /**
     * Return type number for adjust nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(AdjustNode,
     * java.lang.Object)
     */
    public Integer visitAdjust( AdjustNode node, Object arg ) {

      return NODETYPE_ADJUST;
    }

    /**
     * Return type number for aftermath nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(AfterMathNode,
     * java.lang.Object)
     */
    public Integer visitAfterMath( AfterMathNode node, Object arg ) {

      return NODETYPE_AFTERMATH;
    }

    /**
     * Return type number for aligned leaders nodes. Both arguments are not
     * used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(AlignedLeadersNode,
     * java.lang.Object)
     */
    public Integer visitAlignedLeaders( AlignedLeadersNode node,
                                        Object arg ) {

      // TODO unimplemented
      throw new RuntimeException( "unimplemented" );
      // return new Integer(NODETYPE_ALIGNEDLEADERS);
    }

    /**
     * Return type number for before math nodes. Both arguments are not
     * used.
     * <p>
     * org.extex.typesetter.type.node.BeforeMathNode, java.lang.Object)
     */
    public Integer visitBeforeMath( BeforeMathNode node, Object arg ) {

      // not relevant since you can't get your hand in here
      return NODETYPE_BEFOREMATH;
    }

    /**
     * Return type number for centered leaders nodes. Both arguments are not
     * used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(CenteredLeadersNode,
     * java.lang.Object)
     */
    public Integer visitCenteredLeaders( CenteredLeadersNode node,
                                         Object arg ) {

      // TODO unimplemented
      throw new RuntimeException( "unimplemented" );
      // return new Integer(NODETYPE_CENTEREDLEADERS);
    }

    /**
     * Return type number for char nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitChar(CharNode,
     * java.lang.Object)
     */
    public Integer visitChar( CharNode node, Object arg ) {

      return NODETYPE_CHAR;
    }

    /**
     * Return type number for discretionary nodes. Both arguments are not
     * used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(DiscretionaryNode,
     * java.lang.Object)
     */
    public Integer visitDiscretionary( DiscretionaryNode node, Object arg ) {

      return NODETYPE_DISCRETIONARY;
    }

    /**
     * Return type number for expanded leaders nodes. Both arguments are not
     * used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(ExpandedLeadersNode,
     * java.lang.Object)
     */
    public Integer visitExpandedLeaders( ExpandedLeadersNode node,
                                         Object arg ) {

      // TODO unimplemented
      throw new RuntimeException( "unimplemented" );
      // return new Integer(NODETYPE_EXPANDEDLEADERS);
    }

    /**
     * Return type number for glue nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitGlue(GlueNode,
     * java.lang.Object)
     */
    public Integer visitGlue( GlueNode node, Object arg ) {

      return NODETYPE_GLUE;
    }

    /**
     * Return type number for horizontal list nodes. Both arguments are not
     * used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(HorizontalListNode,
     * java.lang.Object)
     */
    public Integer visitHorizontalList( HorizontalListNode node,
                                        Object arg ) {

      return NODETYPE_HORIZONTALLIST;
    }

    /**
     * Return type number for insertion nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(InsertionNode,
     * java.lang.Object)
     */
    public Integer visitInsertion( InsertionNode node, Object arg ) {

      return NODETYPE_INSERTION;
    }

    /**
     * Return type number for kern nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitKern(KernNode,
     * java.lang.Object)
     */
    public Integer visitKern( KernNode node, Object arg ) {

      return NODETYPE_KERN;
    }

    /**
     * Return type number for ligature nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitLigature(LigatureNode,
     * java.lang.Object)
     */
    public Integer visitLigature( LigatureNode node, Object arg ) {

      return NODETYPE_LIGATURE;
    }

    /**
     * Return type number for mark nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitMark(MarkNode,
     * java.lang.Object)
     */
    public Integer visitMark( MarkNode node, Object arg ) {

      return NODETYPE_MARK;
    }

    /**
     * Return type number for penalty nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(PenaltyNode,
     * java.lang.Object)
     */
    public Integer visitPenalty( PenaltyNode node, Object arg ) {

      return NODETYPE_PENALTY;
    }

    /**
     * Return type number for rule nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitRule(RuleNode,
     * java.lang.Object)
     */
    public Integer visitRule( RuleNode node, Object arg ) {

      return NODETYPE_RULE;
    }

    /**
     * Return type number for SPACE nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitSpace(SpaceNode,
     * java.lang.Object)
     */
    public Integer visitSpace( SpaceNode node, Object arg ) {

      return NODETYPE_GLUE;
    }

    /**
     * Return type number for vertical list nodes. Both arguments are not
     * used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
     *VerticalListNode, java.lang.Object)
     */
    public Integer visitVerticalList( VerticalListNode node, Object arg ) {

      return NODETYPE_VERTICALLIST;
    }

    /**
     * org.extex.typesetter.type.node.VirtualCharNode,
     * java.lang.Object)
     */
    public Integer visitVirtualChar( VirtualCharNode node, Object value ) {

      return NODETYPE_CHAR;
    }

    /**
     * Return type number for whatsit nodes. Both arguments are not used.
     *
     * @param node the visited node
     * @param arg  null
     * @return type number of node as {@code Integer}
     * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(WhatsItNode,
     * java.lang.Object)
     */
    public Integer visitWhatsIt( WhatsItNode node, Object arg ) {

      return NODETYPE_WHATSIT;
    }
  }

  /**
   * Class for getting the type of a node.
   */
  private static final NodetypeReader NODETYPE_READER = new NodetypeReader();

  // type 14 (unset) is missing (TE)
  // it is not used in ExTeX (gene)

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  private static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Lastnodetype( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Node lastNode = typesetter.getLastNode();

    return NODETYPE_READER.getNodetype( lastNode );
  }

}
