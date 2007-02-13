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

import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.unicode.Unicode;

/**
 * This is the factory for
 * {@link org.extex.typesetter.type.node.CharNode CharNode}s
 * and virtual chars.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4763 $
 */
public class SimpleUnicodeNodeFactory extends SimpleNodeFactory {

    /**
     * Creates a new object.
     */
    public SimpleUnicodeNodeFactory() {

        super();
    }

    /**
     * Create a new instance for the node.
     * If the character is not defined in the font given then <code>null</code>
     * is returned instead.
     *
     * @param typesettingContext the typographic context for the node
     * @param uc the Unicode character
     *
     * @return the new character node
     *
     * @see org.extex.typesetter.type.node.factory.NodeFactory#getNode(
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      org.extex.type.UnicodeChar)
     */
    public Node getNode(final TypesettingContext typesettingContext,
            final UnicodeChar uc) {

        if (uc.equals(Unicode.SHY)) {
            UnicodeChar hyphen = typesettingContext.getFont().getHyphenChar();
            if (hyphen == null) {
                return null;
            }
            Node node = super.getNode(typesettingContext, hyphen);
            if (node == null) {
                return null;
            }
            return new DiscretionaryNode(null, new HorizontalListNode(node),
                null);
        }

        return super.getNode(typesettingContext, uc);
    }

}
