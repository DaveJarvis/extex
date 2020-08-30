/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.db;

import java.io.IOException;

/**
 * A {@link ValueItem} is an object stored as a part of a {@link Value}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface ValueItem {

    /**
     * This method expands the ValueItem and appends the expansion to the given
     * StringBuilder. Macros are looked up in the database given and their
     * values are inserted.
     * 
     * @param sb the target StringBuilder
     * @param db the database context
     */
    void expand(StringBuilder sb, DB db);

    /**
     * The getter for the content.
     * 
     * @return the content
     */
    String getContent();

    /**
     * Setter for the content.
     * 
     * @param value the content
     */
    void setContent(String value);

    /**
     * This is a method which is invoked when this object is visited.
     * 
     * @param visitor the visitor
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    void visit(ValueVisitor visitor, DB db) throws IOException;

}
