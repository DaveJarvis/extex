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

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.Discardable;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This node represents a TeX "math" node with the subtype "before".
 * <p>
 * For the document writer it acts like a glue or kerning node. The width
 * contains the distance to add.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 */
public class BeforeMathNode extends AbstractNode implements Discardable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param mathsurround the width to add after the math
   */
  public BeforeMathNode( FixedDimen mathsurround ) {

    super( mathsurround );
  }

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a exhaustive form as it is used in tracing
   * output to the log file.
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

    FixedDimen width = getWidth();

    if( width.eq( Dimen.ZERO_PT ) ) {
      sb.append( getLocalizer().format( "String.Format" ) );
    }
    else {
      sb.append( getLocalizer().format( "StringSurrounded.Format",
                                        width.toString() ) );
    }
  }

  /**
   * java.lang.Object)
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Object visit( NodeVisitor visitor, Object value )
      throws GeneralException {

    return visitor.visitBeforeMath( this, value );
  }

}
