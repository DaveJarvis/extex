/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.cff;

import java.io.IOException;

import org.extex.util.xml.XMLStreamWriter;

/**
 * rmoveto |- dx1 dy1 rmoveto (21) |
 *
 * <p>
 * moves the current point to a position at the
 * relative coordinates (dx1, dy1).
 * </p>
 * <p>
 * Note: The first stack-clearing operator, which must be one of hstem,
 * hstemhm, vstem, vstemhm, cntrmask, hintmask, hmoveto, vmoveto,
 * rmoveto, or endchar, takes an additional argument the width
 * (as described earlier), which may be expressed as zero or one
 * numeric argument.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2RMOVETO extends T2PathConstruction {

    /**
     * Create a new obejct.
     */
    public T2RMOVETO() {

        super();
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    public short[] getBytes() {

        return null;
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    public String getName() {

        return "rmoveto";
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    public Object getValue() {

        // TODO mgn: getValue unimplemented
        return null;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(final XMLStreamWriter writer) throws IOException {

        // MGN incomplete
    }
}