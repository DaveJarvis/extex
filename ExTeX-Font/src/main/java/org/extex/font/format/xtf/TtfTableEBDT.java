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

import org.extex.util.XMLConvertible;
import org.extex.util.file.random.RandomAccessR;
import org.jdom.Comment;
import org.jdom.Element;

/**
 * The 'EBDT' ... TODO incomplete
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class TtfTableEBDT extends AbstractXtfTable
        implements
            XtfTable,
            XMLConvertible {

    /**
     * Create a new object
     * 
     * @param tablemap the tablemap
     * @param de entry
     * @param rar input
     * @throws IOException if an IO-error occurs
     */
    TtfTableEBDT(XtfTableMap tablemap, XtfTableDirectory.Entry de,
            RandomAccessR rar) throws IOException {

        super(tablemap);
        rar.seek(de.getOffset());

        // incomplete
    }

    /**
     * Get the table type, as a table directory value.
     * 
     * @return Returns the table type
     */
    public int getType() {

        return XtfReader.EBDT;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.XtfTable#getShortcut()
     */
    public String getShortcut() {

        return "ebdt";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.XMLConvertible#toXML()
     */
    public Element toXML() {

        Element table = new Element("ebdt");
        table.setAttribute("id", "0x" + Integer.toHexString(getType()));
        Comment c = new Comment("incomplete");
        table.addContent(c);
        return table;
    }

}