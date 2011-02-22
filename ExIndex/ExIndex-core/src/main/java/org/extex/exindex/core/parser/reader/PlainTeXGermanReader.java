/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser.reader;

import java.io.IOException;
import java.io.Reader;

/**
 * Read a file and map plain<logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> as well as double-quote sequences
 * defined in <tt>german.sty</tt> to characters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PlainTeXGermanReader extends PlainTeXReader {

    /**
     * The field <tt>MAP_FROM</tt> contains the source part of the mapping of
     * characters after ".
     */
    private static final String MAP_FROM = "aeiouAEIOUsz";

    /**
     * The field <tt>MAP_TO</tt> contains the target part of the mapping of
     * characters after ".
     */
    private static final String MAP_TO =
            "\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u00DF\u00DF";

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource for error messages
     * @param reader the reader
     */
    public PlainTeXGermanReader(String resource, Reader reader) {

        super(resource, reader);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.reader.PlainTeXReader#fillBuffer()
     */
    @Override
    protected boolean fillBuffer() throws IOException {

        resetBuffer();
        int c = rawRead();
        while (c >= 0) {
            if (c == '\\') {
                c = fillEsc();
            } else if (c == '"') {
                c = rawRead();
                if (c < 0) {
                    bufferAppend('"');
                    return true;
                }
                int i = MAP_FROM.indexOf(c);
                if (i >= 0) {
                    bufferAppend(MAP_TO.charAt(i));
                } else if (c == 'S') {
                    bufferAppend('S');
                    c = 'S';
                } else if (c == 'Z') {
                    bufferAppend('S');
                    c = 'Z';
                } else {
                    bufferAppend('"');
                    bufferAppend((char) c);
                }
                c = rawRead();
            } else {
                bufferAppend((char) c);
                if (c == '\n' || c == '\r') {
                    return true;
                }
                c = rawRead();
            }
        }
        return bufferIsNotEmpty();
    }

}
