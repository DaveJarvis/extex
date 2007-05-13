/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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
 */

package org.extex.font.format.texencoding;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Class for a encoding writer.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 * 
 */
public class EncWriter {

    /**
     * The encoding size.
     */
    private static final int ENCODINGSIZE = 256;

    /**
     * Use comments?
     */
    private boolean comments = true;

    /**
     * The encoding vector.
     */
    private String[] enc = new String[ENCODINGSIZE];

    /**
     * The encoding name.
     */
    private String encname = "";

    /**
     * The header comment.
     */
    private String headerComment = null;

    /**
     * Create a new object.
     */
    public EncWriter() {

        for (int i = 0; i < ENCODINGSIZE; i++) {
            enc[i] = "/.notdef";
        }
    }

    /**
     * Returns the encname.
     * 
     * @return Returns the encname.
     */
    public String getEncname() {

        return encname;
    }

    /**
     * Returns the comments.
     * 
     * @return Returns the comments.
     */
    public boolean isComments() {

        return comments;
    }

    /**
     * The comments to set.
     * 
     * @param c The comments to set.
     */
    public void setComments(boolean c) {

        comments = c;
    }

    /**
     * The encname to set.
     * 
     * @param name The encname to set.
     */
    public void setEncname(String name) {

        encname = name;
    }

    /**
     * Set a encoding value.
     * 
     * @param pos The position.
     * @param name The glyph name.
     */
    public void setEncoding(int pos, String name) {

        if (pos >= 0 && pos < ENCODINGSIZE) {
            enc[pos] = name;
        }
    }

    /**
     * Setter for headerComment.
     * 
     * @param headerComment the headerComment to set
     */
    public void setHeaderComment(String headerComment) {

        this.headerComment = headerComment;
    }

    /**
     * Write the encoding vector to a output stream.
     * 
     * @param out The output
     * @throws IOException if an IO-error occurred.
     */
    public void write(OutputStream out) throws IOException {

        BufferedWriter writer =
                new BufferedWriter(new OutputStreamWriter(out, "ASCII"));

        if (headerComment != null) {
            writer.write(headerComment);
        }

        writer.write("/");
        writer.write(encname);
        writer.write(" [\n");
        for (int i = 0; i < ENCODINGSIZE; i++) {
            if (comments) {
                writer
                    .write("% 0x" + Integer.toHexString(i) + " (" + i + ")\n");
            }
            writer.write(" ");
            writer.write(enc[i]);
            writer.write("\n");
        }

        writer.write("] def\n");
        writer.close();
    }
}
