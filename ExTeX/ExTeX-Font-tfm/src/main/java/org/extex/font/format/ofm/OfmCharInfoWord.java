/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

import org.extex.font.format.tfm.TfmCharInfoWord;
import org.extex.util.file.random.RandomAccessR;

/**
 * Class for OFM char info word.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class OfmCharInfoWord extends TfmCharInfoWord {

    /**
     * Create a new object.
     * 
     * @param rar the input
     * @param id the id
     * @throws IOException if an IO-error occurs.
     */
    public OfmCharInfoWord(RandomAccessR rar, int id) throws IOException {

        charid = id;
        widthindex = (short) rar.readByteAsInt();
        // short heightdepthindex = (short) rar.readByteAsInt();
        // heightindex =
        // (short) (heightdepthindex >> TfmConstants.CONST_4 &
        // TfmConstants.CONST_X0F);
        // depthindex = (short) (heightdepthindex & TfmConstants.CONST_X0F);
        // short italicindextag = (short) rar.readByteAsInt();
        // italicindex = (short) (italicindextag >> 2 & TfmConstants.CONST_X3F);
        // tag = (short) (italicindextag & TfmConstants.CONST_X03);
        // remainder = (short) rar.readByteAsInt();
        //
        // switch (tag) {
        // case TAG0:
        // tagT = NO_TAG;
        // break;
        // case TAG1:
        // tagT = LIG_TAG;
        // break;
        // case TAG2:
        // tagT = LIST_TAG;
        // break;
        // case TAG3:
        // tagT = EXT_TAG;
        // break;
        // default:
        // // not defined: use no_tag
        // tagT = NO_TAG;
        // }

    }

    /**
     * The field <tt>serialVersionUID</tt>.
     */
    private static final long serialVersionUID = 1L;

}
