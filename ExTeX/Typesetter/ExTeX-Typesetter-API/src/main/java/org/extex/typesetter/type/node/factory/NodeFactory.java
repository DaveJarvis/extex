/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.VirtualCharNode;

/**
 * This interface describes a node factory.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface NodeFactory {

  /**
   * Create a new instance of a character node. If the character is not
   * defined in the font given then {@code null} is returned instead.
   * <p>
   * If the character has some special meaning in Unicode then another node
   * might be returned.
   * </p>
   *
   * @param typesettingContext the typographic context for the node
   * @param uc                 the Unicode character
   * @return the new character node
   */
  Node getNode( TypesettingContext typesettingContext, UnicodeChar uc );

  /**
   * Create a new instance of a virtual character node. If the character is
   * not defined in the font given then {@code null} is returned
   * instead.
   * <p>
   * If the character has some special meaning in Unicode then another node
   * might be returned.
   * </p>
   *
   * @param typesettingContext the typographic context for the node
   * @param uc                 the Unicode character
   * @return the new character node
   */
  VirtualCharNode getVirtualCharNode( TypesettingContext typesettingContext,
                                      UnicodeChar uc );

}
