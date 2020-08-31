/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.noad;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathSpacing;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.KernNode;

import java.util.logging.Logger;

/**
 * This noad contains a node which is passed through the math apparatus.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class NodeNoad implements Noad {

  /**
   * The field {@code node} contains the encapsulated node.
   */
  private final Node node;

  /**
   * The field {@code spacingClass} contains the spacing class.
   */
  private MathSpacing spacingClass = MathSpacing.ORD; // gene: correct?

  /**
   * Creates a new object.
   *
   * @param node the node to
   */
  public NodeNoad( Node node ) {

    this.node = node;
  }

  /**
   * Getter for spacing class.
   *
   * @return the spacing class
   * @see org.extex.typesetter.type.noad.Noad#getSpacingClass()
   */
  @Override
  public MathSpacing getSpacingClass() {

    return spacingClass;
  }

  /**
   * Getter for the subscript.
   *
   * @return the subscript.
   * @see org.extex.typesetter.type.noad.Noad#getSubscript()
   */
  @Override
  public Noad getSubscript() {

    return null;
  }

  /**
   * Getter for the superscript.
   *
   * @return the superscript.
   * @see org.extex.typesetter.type.noad.Noad#getSuperscript()
   */
  @Override
  public Noad getSuperscript() {

    return null;
  }

  @Override
  public void setSpacingClass( MathSpacing spacingClass ) {

    this.spacingClass = spacingClass;
  }

  /**
   * Setter for the subscript. This operation is not supported and leads to an
   * exception.
   *
   * @param subscript the subscript to set.
   * @see org.extex.typesetter.type.noad.Noad#setSubscript(org.extex.typesetter.type.noad.Noad)
   */
  @Override
  public void setSubscript( Noad subscript ) {

    throw new UnsupportedOperationException();
  }

  /**
   * Setter for the superscript. This operation is not supported and leads to
   * an exception.
   *
   * @param superscript the superscript to set.
   * @see org.extex.typesetter.type.noad.Noad#setSuperscript(org.extex.typesetter.type.noad.Noad)
   */
  @Override
  public void setSuperscript( Noad superscript ) {

    throw new UnsupportedOperationException();
  }

  /**
   * Produce a printable representation of the noad in a StringBuilder.
   *
   * @param sb the string buffer
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder)
   */
  @Override
  public void toString( StringBuilder sb ) {

    node.toString( sb, "", Integer.MAX_VALUE, Integer.MAX_VALUE );
  }

  /**
   * Produce a printable representation to a certain depth of the noad.
   *
   * @param sb    the string buffer
   * @param depth the depth to which the full information should be given
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder, int)
   */
  @Override
  public void toString( StringBuilder sb, int depth ) {

    node.toString( sb, "", Integer.MAX_VALUE, Integer.MAX_VALUE );
  }

  /**
   * Translate a Noad into a NodeList.
   *
   * @param previousNoad the previous noad
   * @param noads        the list of noads currently processed
   * @param index        the index of the current node in the list
   * @param list         the list to add the nodes to. This list contains
   *                     the Nodes
   *                     previously typeset. Thus it can be used to look back
   * @param mathContext  the context to consider
   * @param logger       the logger for debugging and tracing information
   * @throws TypesetterException    in case of a problem
   * @throws ConfigurationException in case of a configuration problem
   * @see org.extex.typesetter.type.noad.Noad#typeset(org.extex.typesetter.type.noad.Noad,
   * org.extex.typesetter.type.noad.NoadList, int,
   * org.extex.typesetter.type.NodeList,
   * org.extex.typesetter.type.noad.util.MathContext,
   * java.util.logging.Logger)
   */
  @Override
  public void typeset( Noad previousNoad, NoadList noads, int index,
                       NodeList list, MathContext mathContext, Logger logger )
      throws TypesetterException,
      ConfigurationException {

    if( node instanceof GlueNode || node instanceof KernNode ) {
      if( previousNoad instanceof GlueNoad
          && ((GlueNoad) previousNoad).isKill() ) {
        StyleNoad style = mathContext.getStyle();
        if( style == StyleNoad.SCRIPTSTYLE
            || style == StyleNoad.SCRIPTSCRIPTSTYLE ) {
          return;
        }
      }
    }

    getSpacingClass().addClearance(
        (previousNoad != null ? previousNoad.getSpacingClass() : null),
        list, mathContext );

    list.add( node );
  }

}
