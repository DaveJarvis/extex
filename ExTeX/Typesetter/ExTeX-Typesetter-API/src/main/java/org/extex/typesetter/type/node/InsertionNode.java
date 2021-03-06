/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.GeneralException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This node is meant to record an insertion.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 */
public class InsertionNode extends AbstractNode implements Node {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code nodes} contains the vertical list to be inserted.
   */
  private final NodeList nodes;

  /**
   * The field {@code floatCost} contains the penalty which is used when the
   * insertion floats to the following page.
   */
  private final long floatCost = 0;

  /**
   * The field {@code subtype} contains the register number for the
   * associated registers.
   */
  private long subtype = 0;

  /**
   * Creates a new object.
   *
   * @param subtype the register number for the associated registers
   * @param nodes   the nodes to be inserted
   */
  public InsertionNode( long subtype, NodeList nodes ) {

    this.subtype = subtype;
    this.nodes = nodes;
    if( nodes != null ) {
      setWidth( nodes.getWidth() );
      setHeight( nodes.getHeight() );
      setDepth( nodes.getDepth() );
    }
  }

  /**
   * This method returns the printable representation. This is meant to
   * produce a exhaustive form as it is used in tracing output to the log
   * file.
   *
   * @param sb      the output string buffer
   * @param prefix  the prefix string inserted at the beginning of each line
   * @param breadth the breadth
   * @param depth   the depth
   * @see org.extex.typesetter.type.Node#toString(java.lang.StringBuilder,
   * java.lang.String, int, int)
   */
  @Override
  public void toString( StringBuilder sb, String prefix, int breadth,
                        int depth ) {

    sb.append( getLocalizer().format( "String.Format",
                                      Long.toString( subtype ),
                                      getHeight().toString(),
                                      "???",
                                      getDepth().toString(),
                                      Long.toString( floatCost ) ) );
    nodes.toString( sb, prefix, Integer.MAX_VALUE, Integer.MAX_VALUE );
  }

  /**
   * java.lang.Object)
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Object visit( NodeVisitor visitor, Object value )
      throws GeneralException {

    return visitor.visitInsertion( this, value );
  }

}
