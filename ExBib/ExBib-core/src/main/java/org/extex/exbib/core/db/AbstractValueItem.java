/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.db;

import java.io.IOException;

/**
 * This is the abstract base class for all value items.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public abstract class AbstractValueItem implements ValueItem {

    /**
     * The field <tt>content</tt> contains the content of the value.
     */
    private String content;

    /**
     * Creates a new object.
     * 
     * @param content the initial value
     */
    public AbstractValueItem(String content) {

        super();
        this.content = content;
    }

    /**
     * This is the default implementation of an expansion. It simply expands the
     * content of the object.
     * 
     * @param sb the target StringBuffer
     * @param db the database context
     */
    public void expand(StringBuffer sb, DB db) {

        sb.append(content);
    }

    /**
     * Getter for the value of the object.
     * 
     * @return the value
     */
    public String getContent() {

        return content;
    }

    /**
     * Setter for the value
     * 
     * @param content the new value
     */
    public void setContents(String content) {

        this.content = content;
    }

    /**
     * Return a String representation of the object suitable for a BibT<sub>E</sub>X
     * file.
     * 
     * @return the string representation
     */
    @Override
    public abstract String toString();

    /**
     * The visitor pattern is used to distinguish the different subtypes. Thus
     * the derived classes are forced to overwrite this method.
     * 
     * @param visitor the visitor
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    public abstract void visit(ValueVisitor visitor, DB db) throws IOException;
}