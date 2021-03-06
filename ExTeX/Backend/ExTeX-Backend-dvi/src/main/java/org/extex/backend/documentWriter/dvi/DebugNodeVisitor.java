/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

// created: 2004-07-30

package org.extex.backend.documentWriter.dvi;

import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.type.InspectableNodeVisitor;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.*;

import java.io.PrintStream;

/**
 * This is a implementation of a NodeVisitor for debugging.
 *
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 */
public class DebugNodeVisitor implements InspectableNodeVisitor {

  /**
   * Visitor for debugging.
   */
  private InspectableNodeVisitor nodeVisitor = null;

  /**
   * The field {@code printStream} contains the print stream.
   */
  private final PrintStream printStream = System.out;

  /**
   * Creates a new instance.
   *
   * @param visitor {@code InspectableNodeVisitor} to inspect
   */
  public DebugNodeVisitor( InspectableNodeVisitor visitor ) {

    this.nodeVisitor = visitor;
    visitor.setVisitor( this );
  }

  /**
   * Append information about a value to a {@code StringBuilder}.
   *
   * @param buffer for appending information
   * @param value  more information is added if this is a Node-object
   */
  private void appendNodeInformation( StringBuilder buffer, Object value ) {

    if( value instanceof Node ) {
      Node node = (Node) value;

      buffer.append( " (wd=" );
      buffer.append( convertDimen( node.getWidth() ) );
      buffer.append( "  ht=" );
      buffer.append( convertDimen( node.getHeight() ) );
      buffer.append( "  dp=" );
      buffer.append( convertDimen( node.getDepth() ) );
      buffer.append( ")" );
    }
    else {
      buffer.append( " [no node]" );
    }

  }

  /**
   * Convert dimen to String.
   *
   * @param dimen this value is not modified
   * @return a string representing dimen
   */
  private String convertDimen( FixedDimen dimen ) {

    return dimen.getValue() + "sp";
  }

  /**
   * Write the debug message.
   *
   * @param mesg  a {@code String}
   * @param node  value for additional information
   * @param value value for additional information
   */
  private void debugMessage( String mesg, Node node, Object value ) {

    StringBuilder buffer = new StringBuilder( "DEBUG: " );

    buffer.append( mesg );
    appendNodeInformation( buffer, node );
    appendNodeInformation( buffer, value );

    // TODO: better the visitor knows where the Node is (TE)
    printStream.println( buffer );
  }

  /**
   * Visitor for nested nodes.
   *
   * @param visitor a {@code NodeVisitor} value
   * @see InspectableNodeVisitor#setVisitor(org.extex.typesetter.type.NodeVisitor)
   */
  @Override
  public void setVisitor( NodeVisitor<Object, Object> visitor ) {

    nodeVisitor.setVisitor( visitor );
  }

  /*
   * the visit methods
   */

  /**
   * Call the {@code visitAdjust} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(AdjustNode,
   * Object)
   */
  @Override
  public Object visitAdjust( AdjustNode node, Object value )
      throws GeneralException {

    debugMessage( "visitAdjust", node, value );
    return nodeVisitor.visitAdjust( node, value );
  }

  /**
   * Call the {@code visitAfterMath} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(AfterMathNode,
   * Object)
   */
  @Override
  public Object visitAfterMath( AfterMathNode node, Object value )
      throws GeneralException {

    debugMessage( "visitAfterMath", node, value );
    return nodeVisitor.visitAfterMath( node, value );
  }

  /**
   * Call the {@code visitAlignedLeaders} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(AlignedLeadersNode,
   * Object)
   */
  @Override
  public Object visitAlignedLeaders( AlignedLeadersNode node, Object value )
      throws GeneralException {

    debugMessage( "visitAlignedLeaders", node, value );
    return nodeVisitor.visitAlignedLeaders( node, value );
  }

  /**
   * Call the {@code visitBeforeMath} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(BeforeMathNode,
   * Object)
   */
  @Override
  public Object visitBeforeMath( BeforeMathNode node, Object value )
      throws GeneralException {

    debugMessage( "visitBeforeMath", node, value );
    return nodeVisitor.visitBeforeMath( node, value );
  }

  /**
   * Call the {@code visitCenteredLeaders} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(CenteredLeadersNode,
   * Object)
   */
  @Override
  public Object visitCenteredLeaders( CenteredLeadersNode node, Object value )
      throws GeneralException {

    debugMessage( "visitCenteredLeaders", node, value );
    return nodeVisitor.visitCenteredLeaders( node, value );
  }

  /**
   * Call the {@code visitChar} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitChar(CharNode, Object)
   */
  @Override
  public Object visitChar( CharNode node, Object value )
      throws GeneralException {

    debugMessage( "visitChar", node, value );
    return nodeVisitor.visitChar( node, value );
  }

  /**
   * Call the {@code visitDiscretionary} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(DiscretionaryNode,
   * Object)
   */
  @Override
  public Object visitDiscretionary( DiscretionaryNode node, Object value )
      throws GeneralException {

    debugMessage( "visitDiscretionary", node, value );
    return nodeVisitor.visitDiscretionary( node, value );
  }

  /**
   * Call the {@code visitExpandedLeaders} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(ExpandedLeadersNode,
   * Object)
   */
  @Override
  public Object visitExpandedLeaders( ExpandedLeadersNode node, Object value )
      throws GeneralException {

    debugMessage( "visitExpandedLeaders", node, value );
    return nodeVisitor.visitExpandedLeaders( node, value );
  }

  /**
   * Call the {@code visitGlue} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitGlue(GlueNode, Object)
   */
  @Override
  public Object visitGlue( GlueNode node, Object value )
      throws GeneralException {

    debugMessage( "visitGlue", node, value );
    return nodeVisitor.visitGlue( node, value );
  }

  /**
   * Call the {@code visitHorizontalList} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(HorizontalListNode,
   * Object)
   */
  @Override
  public Object visitHorizontalList( HorizontalListNode node, Object value )
      throws GeneralException {

    debugMessage( "visitHorizontalList", node, value );
    return nodeVisitor.visitHorizontalList( node, value );
  }

  /**
   * Call the {@code visitInsertion} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(InsertionNode,
   * Object)
   */
  @Override
  public Object visitInsertion( InsertionNode node, Object value )
      throws GeneralException {

    debugMessage( "visitInsertion", node, value );
    return nodeVisitor.visitInsertion( node, value );
  }

  /**
   * Call the {@code visitKern} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitKern(KernNode, Object)
   */
  @Override
  public Object visitKern( KernNode node, Object value )
      throws GeneralException {

    debugMessage( "visitKern", node, value );
    return nodeVisitor.visitKern( node, value );
  }

  /**
   * Call the {@code visitLigature} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitLigature(LigatureNode,
   * Object)
   */
  @Override
  public Object visitLigature( LigatureNode node, Object value )
      throws GeneralException {

    debugMessage( "visitLigature", node, value );
    return nodeVisitor.visitLigature( node, value );
  }

  /**
   * Call the {@code visitMark} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitMark(MarkNode, Object)
   */
  @Override
  public Object visitMark( MarkNode node, Object value )
      throws GeneralException {

    debugMessage( "visitMark", node, value );
    return nodeVisitor.visitMark( node, value );
  }

  /**
   * Call the {@code visitPenalty} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(PenaltyNode,
   * Object)
   */
  @Override
  public Object visitPenalty( PenaltyNode node, Object value )
      throws GeneralException {

    debugMessage( "visitPenalty", node, value );
    return nodeVisitor.visitPenalty( node, value );
  }

  /**
   * Call the {@code visitRule} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitRule(RuleNode, Object)
   */
  @Override
  public Object visitRule( RuleNode node, Object value )
      throws GeneralException {

    debugMessage( "visitRule", node, value );
    return nodeVisitor.visitRule( node, value );
  }

  /**
   * Call the {@code visitSpace} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitSpace(SpaceNode, Object)
   */
  @Override
  public Object visitSpace( SpaceNode node, Object value )
      throws GeneralException {

    debugMessage( "visitSpace", node, value );
    return nodeVisitor.visitSpace( node, value );
  }

  /**
   * Call the {@code visitVerticalList} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(VerticalListNode,
   * Object)
   */
  @Override
  public Object visitVerticalList( VerticalListNode node, Object value )
      throws GeneralException {

    debugMessage( "visitVerticalList", node, value );
    return nodeVisitor.visitVerticalList( node, value );
  }

  /**
   * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(org.extex.typesetter.type.node.VirtualCharNode,
   * java.lang.Object)
   */
  @Override
  public Object visitVirtualChar( VirtualCharNode node, Object value )
      throws GeneralException {

    // TODO visitVirtualChar unimplemented
    return null;
  }

  /**
   * Call the {@code visitWhatsIt} to inspect.
   *
   * @param node  the first parameter for the visitor
   * @param value the second parameter for the visitor
   * @return the visitor specific value of inspecting visitor
   * @throws GeneralException if an error occurs
   * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(WhatsItNode,
   * Object)
   */
  @Override
  public Object visitWhatsIt( WhatsItNode node, Object value )
      throws GeneralException {

    debugMessage( "visitWhatsIt", node, value );
    return nodeVisitor.visitWhatsIt( node, value );
  }
}
