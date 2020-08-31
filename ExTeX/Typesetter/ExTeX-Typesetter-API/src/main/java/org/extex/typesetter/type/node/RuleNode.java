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
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.OrientedNode;

/**
 * The rule node represents a rectangular area on the page filled with some
 * color.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class RuleNode extends AbstractNode implements OrientedNode {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code context} the typesetting context.
   */
  private final TypesettingContext context;

  /**
   * The field {@code horizontal} contains the indicator that this is a
   * horizontal rule; otherwise it is a vertical rule.
   */
  private final boolean horizontal;

  /**
   * Creates a new object.
   *
   * @param width      the width of the rule
   * @param height     the height of the rule
   * @param depth      the depth of the rule
   * @param theContext the typesetting context
   * @param horizontal the indicator that this is a horizontal rule; otherwise
   *                   it is a vertical rule
   */
  public RuleNode( FixedDimen width, FixedDimen height, FixedDimen depth,
                   TypesettingContext theContext, boolean horizontal ) {

    super( width, height, depth );
    this.context = theContext;
    this.horizontal = horizontal;
  }

  /**
   * Getter for the typesetting context.
   *
   * @return the typesetting context.
   */
  public TypesettingContext getTypesettingContext() {

    return context;
  }

  /**
   * Getter for horizontal.
   *
   * @return the horizontal
   */
  @Override
  public boolean isHorizontal() {

    return this.horizontal;
  }

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a short form only as it is used in error
   * messages to the user.
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

    FixedDimen x = getHeight();
    String h = (x == null ? "*" : x.toString());
    x = getDepth();
    String d = (x == null ? "*" : x.toString());
    x = getWidth();
    String w = (x == null ? "*" : x.toString());
    sb.append( getLocalizer().format( "String.Format", h, d, w ) );
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

    FixedDimen x = getHeight();
    String h = (x == null ? "*" : x.toString());
    x = getDepth();
    String d = (x == null ? "*" : x.toString());
    x = getWidth();
    String w = (x == null ? "*" : x.toString());
    sb.append( getLocalizer().format( "Text.Format", h, d, w ) );
  }

  /**
   * This method provides an entry point for the visitor pattern.
   *
   * @param visitor the visitor to apply
   * @param value   the argument for the visitor
   * @return the result of the method invocation of the visitor
   * @throws GeneralException in case of an error
   * @see org.extex.typesetter.type.Node#visit(org.extex.typesetter.type.NodeVisitor,
   * java.lang.Object)
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Object visit( NodeVisitor visitor, Object value )
      throws GeneralException {

    return visitor.visitRule( this, value );
  }

}
