/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

import java.io.IOException;

/**
 * This class represents a constant {@link ValueItem ValueItem} which has been
 * enclosed in matching braces initially.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{abc{def}ghi}</pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VBlock extends AbstractValueItem {

    /**
     * Creates a new object.
     * 
     * @param content the contents of the block
     */
    public VBlock(String content) {

        super(content);
    }

    /**
     * Return a String representation of the object suitable for a B<small>IB</small>T<sub>E</sub>X
     * file.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {

        return "{" + getContent() + "}";
    }

    /**
     * This is a method which is invoked when this object is visited.
     * 
     * @param visitor the visitor
     * @param db the database context
     * 
     * @throws IOException just in case
     */
    @Override
    public void visit(ValueVisitor visitor, DB db) throws IOException {

        visitor.visitBlock(this, db);
    }

}
