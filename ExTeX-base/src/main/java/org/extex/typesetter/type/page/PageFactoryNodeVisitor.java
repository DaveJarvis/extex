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

package org.extex.typesetter.type.page;

import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This interface describes a
 * {@link org.extex.typesetter.type.NodeVisitor NodeVisitor} which is able to
 * take a {@link org.extex.typesetter.type.page.Page Page}, a
 * {@link org.extex.interpreter.context.Context Context}, and a
 * {@link org.extex.typesetter.Typesetter Typesetter}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface PageFactoryNodeVisitor extends NodeVisitor<Node, Boolean> {

    /**
     * Setter for the context.
     * 
     * @param context the context
     */
    void setContext(Context context);

    /**
     * Setter for the page.
     * 
     * @param page the page
     */
    void setPage(Page page);

    /**
     * Setter for the typesetter.
     * 
     * @param typsetter the typesetter
     */
    void setTypesetter(Typesetter typsetter);

}
