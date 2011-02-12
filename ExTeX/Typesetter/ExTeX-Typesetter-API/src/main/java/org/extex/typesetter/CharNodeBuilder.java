/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter;

import org.extex.core.UnicodeChar;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This interface describes the ability to convert a Unicode character into a
 * CharNode.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface CharNodeBuilder {

    /**
     * Create a node for a Unicode character. Note that the result can either be
     * a simple {@link CharNode} or a
     * {@link org.extex.typesetter.type.node.VirtualCharNode VirtualCharNode}.
     * In the latter case a composition of several components can be contained.
     * 
     * @param uc the Unicode character
     * @param tc the default typesetting context to use
     * @param factory the factory to acquire new nodes from
     * @param tcFactory the factor for new typesetting contexts
     * 
     * @return the node for the character or <code>null</code> if none can be
     *         determined
     */
    CharNode buildCharNode(UnicodeChar uc, TypesettingContext tc,
            NodeFactory factory, TypesettingContextFactory tcFactory);

}
