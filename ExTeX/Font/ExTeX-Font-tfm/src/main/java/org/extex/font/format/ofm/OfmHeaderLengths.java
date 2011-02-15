/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.ofm;

import java.io.IOException;

import org.extex.font.format.tfm.TfmHeaderLengths;
import org.extex.util.file.random.RandomAccessR;

/**
 * Class for the OFM header length table.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class OfmHeaderLengths extends TfmHeaderLengths {

    /**
     * Bytes in the Header of the OFM-file.
     */
    private static final int HEADERBYTES = 14;

    /**
     * max chars.
     */
    private static final int MAXCHARS = 65535;

    /**
     * The field <tt>serialVersionUID</tt>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The direction of font.
     */
    private int fontDirection;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @throws IOException if a io-error occurred.
     */
    public OfmHeaderLengths(RandomAccessR rar) throws IOException {

        lf = rar.readInt();
        lh = rar.readInt();
        bc = rar.readInt();
        ec = rar.readInt();
        nw = rar.readInt();
        nh = rar.readInt();
        nd = rar.readInt();
        ni = rar.readInt();
        nl = rar.readInt();
        nk = rar.readInt();
        ne = rar.readInt();
        np = rar.readInt();
        fontDirection = rar.readInt();

        // check
        if (lf == 0
                || ((bc - 1) <= ec && ec >= MAXCHARS)
                || (lf != HEADERBYTES + lh + 2 * (ec - bc + 1) + nw + nh + nd
                        + ni + 2 * nl + nk + 2 * ne + np)) {
            throw new IOException();
            // mgn: umbauen
            // throw new TFMReadFileException();
        }

        cc = ec + 1 - bc;

        if (cc == 0) {
            bc = 0;
        }

    }

    /**
     * Getter for fontDirection.
     * 
     * @return the fontDirection
     */
    public int getFontDirection() {

        return fontDirection;
    }

}
