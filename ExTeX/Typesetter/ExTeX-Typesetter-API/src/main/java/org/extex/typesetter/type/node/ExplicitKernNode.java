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

import org.extex.core.dimen.FixedDimen;
import org.extex.typesetter.Discardable;

/**
 * This class represents an explicit kerning node for the typesetter.
 * <p>
 * From The TeXbook
 * <p>
 * A kern_node has a width field to specify a (normally negative) amount of
 * spacing. This spacing correction appears in horizontal lists between letters
 * like A and V when the font designer said that it looks better to move them
 * closer together or further apart. A kern node can also appear in a vertical
 * list, when its `width' denotes additional spacing in the vertical direction.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @see org.extex.typesetter.type.node.ImplicitKernNode
 */
public class ExplicitKernNode extends AbstractKernNode implements Discardable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 20060419L;

  /**
   * Creates a new object.
   *
   * @param kern       the natural size
   * @param horizontal the indicator that the kern works horizontally
   */
  public ExplicitKernNode( FixedDimen kern, boolean horizontal ) {

    super( kern, horizontal );
  }

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a exhaustive form as it is used in tracing
   * output to the log file.
   *
   * @param sb      the output string buffer
   * @param prefix  the prefix string inserted at the beginning of each line
   * @param breadth the breadth of the nodes to display
   * @param depth   the depth of the nodes to display
   * @see org.extex.typesetter.type.Node#toString(java.lang.StringBuilder,
   * java.lang.String, int, int)
   */
  @Override
  public void toString( StringBuilder sb, String prefix, int breadth,
                        int depth ) {

    sb.append( getLocalizer().format( "String.Format",
                                      getWidth().toString() ) );
  }

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a short form only as it is used in error
   * messages to the user.
   *
   * @param sb     the output string buffer
   * @param prefix the prefix string inserted at the beginning of each line
   * @see org.extex.typesetter.type.Node#toText(StringBuilder,
   * java.lang.String)
   */
  @Override
  public void toText( StringBuilder sb, String prefix ) {

    sb.append( getLocalizer().format( "Text.Format", getWidth().toString() ) );
  }

}
