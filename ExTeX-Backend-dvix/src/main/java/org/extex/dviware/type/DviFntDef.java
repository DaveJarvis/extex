/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.dviware.type;

import java.io.IOException;
import java.io.OutputStream;

import org.extex.dviware.Dvi;
import org.extex.interpreter.type.font.Font;

/**
 * This class represents the DVI instruction <tt>fnt_def</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4704 $
 */
public class DviFntDef extends AbstractDviCode {

    /**
     * The field <tt>font</tt> contains the associated font.
     */
    private Font font;

    /**
     * The field <tt>index</tt> contains the number of the font in the font
     * table.
     */
    private int index;

    /**
     * Creates a new object.
     *
     * @param index the number of the font in the font table
     * @param font the font
     */
    public DviFntDef(int index, Font font) {

        super("fnt_def" + variant(index));
        this.index = index;
        this.font = font;
    }

    /**
     * Write the code to the output stream.
     *
     * @param stream the target stream
     *
     * @return the number of bytes actually written
     *
     * @throws IOException in case of an error
     *
     * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
     */
    public int write(OutputStream stream) throws IOException {

        int n = opcode(Dvi.FNT_DEF1, index, stream);
        write4(stream, font.getCheckSum());
        write4(stream, (int) font.getActualSize().getValue());
        write4(stream, (int) font.getDesignSize().getValue());
        stream.write(0); // no directory part; just the name
        byte[] bytes = font.getFontName().getBytes();
        stream.write(bytes.length);
        stream.write(bytes);

        return n + bytes.length + 14;
    }

}
