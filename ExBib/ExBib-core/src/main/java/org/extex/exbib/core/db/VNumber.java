/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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
 * This class represents a numeric constant {@link ValueItem ValueItem}.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>1234</pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class VNumber extends AbstractValueItem {

    /**
     * Creates a new object.
     * 
     * @param content the content of the object
     */
    public VNumber(String content) {

        super(content);
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

        visitor.visitNumber(this, db);
    }

}
