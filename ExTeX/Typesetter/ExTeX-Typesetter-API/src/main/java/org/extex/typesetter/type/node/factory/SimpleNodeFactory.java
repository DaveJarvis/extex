/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.node.factory;

import org.extex.core.UnicodeChar;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.VirtualCharNode;

/**
 * This is a factory for {@link org.extex.typesetter.type.node.CharNode
 * CharNode}s and virtual chars.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class SimpleNodeFactory implements NodeFactory {


  public SimpleNodeFactory() {

  }

  /**
   * Create a new instance for the node. If the character is not defined in
   * the font given then {@code null} is returned instead.
   *
   * @param typesettingContext the typographic context for the node
   * @param uc                 the Unicode character
   * @return the new character node
   * @see org.extex.typesetter.type.node.factory.NodeFactory#getNode(org.extex.typesetter.tc.TypesettingContext,
   * org.extex.core.UnicodeChar)
   */
  public Node getNode( TypesettingContext typesettingContext, UnicodeChar uc ) {

    if( typesettingContext == null ) {
      return null;
    }
    Font font = typesettingContext.getFont();

    if( font == null || !font.hasGlyph( uc ) ) {
      return null;
    }

    return new CharNode( typesettingContext, uc );
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  public VirtualCharNode getVirtualCharNode(
      TypesettingContext typesettingContext, UnicodeChar uc ) {

    Font font = typesettingContext.getFont();

    if( !font.hasGlyph( uc ) ) {
      return null;
    }

    return new VirtualCharNode( typesettingContext, uc );
  }

}
