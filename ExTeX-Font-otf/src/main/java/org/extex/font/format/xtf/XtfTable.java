/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf;

import java.io.IOException;

/**
 * Interface for all TTFTables.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface XtfTable {

    /**
     * Returns the table-type.
     * @return Returns the table-type.
     */
    int getType();

    /**
     * Returns the table shortcut.
     * @return Returns the table shortcut.
     */
    String getShortcut();

    /**
     * Returns a order number to sort the tables for the init process.
     * @return Returns the order number.
     */
    int getInitOrder();

    /**
     * Returns the table map with all tables.
     * @return Returns the table map with all tables.
     */
    XtfTableMap getTableMap();

    /**
     * Init the table.
     *
     * @throws IOException TODO mgn
     */
    void init() throws IOException;

}