/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type;

import org.extex.exindex.core.command.type.LMarkup;

/**
 * This interface describes a container for markup information.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface MarkupContainer {

    /**
     * Getter for a named markup.
     * 
     * @param name the make of the markup
     * 
     * @return the markup for the name or <code>null</code>
     */
    LMarkup getMarkup(String name);

    /**
     * Setter for a named markup.
     * 
     * @param name the make of the markup
     * @param m the markup to add
     */
    void setMarkup(String name, LMarkup m);

}
