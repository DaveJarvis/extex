/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.pdf.api.action;

/**
 * This interface describes a visitor for actions as used on PDF nodes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface ActionVisitor {

    /**
     * Visit a Goto action with id.
     *
     * @param spec the specification
     *
     * @return any Object
     */
    Object visitGotoId(GotoIdActionSpec spec);

    /**
     * Visit a GotoPage action
     *
     * @param spec the specification
     *
     * @return any Object
     */
    Object visitGotoPage(GotoPageActionSpec spec);

    /**
     * Visit a Thread action.
     *
     * @param spec the specification
     *
     * @return any Object
     */
    Object visitThread(ThreadActionSpec spec);

    /**
     * Visit a User action.
     *
     * @param spec the specification
     *
     * @return any Object
     */
    Object visitUser(UserActionSpec spec);

}
