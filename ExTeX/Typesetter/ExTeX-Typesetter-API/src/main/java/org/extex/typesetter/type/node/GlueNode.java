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
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.Discardable;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This node represents a TeX "glue" node.
 * <p>
 * For the document writer it acts like a kerning node. The width contains the
 * distance to add.
 * </p>
 * <p>
 * The stretchability is adjusted by the typesetter and the width is adjusted
 * accordingly.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class GlueNode extends AbstractExpandableNode
    implements
    SkipNode,
    Discardable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object. The size is used to determine the width in
   * horizontal mode and the height in vertical mode.
   *
   * @param size       the actual size
   * @param horizontal indicator that the glue is used in horizontal mode
   */
  public GlueNode( FixedDimen size, boolean horizontal ) {

    super( size, horizontal );
  }

  /**
   * Creates a new object. The size is used to determine the width in
   * horizontal mode and the height in vertical mode.
   *
   * @param size       the actual size
   * @param horizontal indicator that the glue is used in horizontal mode
   */
  public GlueNode( FixedGlue size, boolean horizontal ) {

    super( size, horizontal );
  }

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a short form only as it is used in error
   * messages to the user.
   *
   * @param sb      the output string buffer
   * @param prefix  the prefix string inserted at the beginning of each line
   * @param breadth the breadth (ignored)
   * @param depth   the depth (ignored)
   * @see org.extex.typesetter.type.Node#toString(java.lang.StringBuilder,
   * java.lang.String, int, int)
   */
  @Override
  public void toString( StringBuilder sb, String prefix, int breadth,
                        int depth ) {

    sb.append( getLocalizer().format( "String.Format", getSize().toString() ) );
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

    sb.append( getLocalizer().format( "Text.Format", getSize().toString() ) );
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

    return visitor.visitGlue( this, value );
  }

}
