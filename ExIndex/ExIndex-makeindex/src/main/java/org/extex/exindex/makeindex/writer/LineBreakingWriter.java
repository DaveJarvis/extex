/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.writer;

import java.io.IOException;
import java.io.Writer;

/**
 * This is a writer which tries to achieve a certain line length by breaking
 * longer lines.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LineBreakingWriter extends Writer {

    /**
     * The field {@code buffer} contains the intermediary buffer.
     */
    private final StringBuilder buffer = new StringBuilder();

    /**
     * The field {@code w} contains the target writer.
     */
    private final Writer w;

    /**
     * The field {@code column} contains the current column.
     */
    private int column = 0;

    /**
     * The field {@code sep} contains the separator.
     */
    private final String sep;

    /**
     * The field {@code in} contains the width of sep.
     */
    private final int in;

    /**
     * The field {@code lineLength} contains the desired line length.
     */
    private final int lineLength;

    /**
     * Creates a new object.
     * 
     * @param w the target writer
     * @param lineLength the line length
     * @param separator the separator
     * @param indent the indentation
     */
    public LineBreakingWriter(Writer w, int lineLength, String separator,
            int indent) {

        this.w = w;
        this.lineLength = lineLength;
        this.sep = "\n" + separator;
        this.in = indent;
    }

@Override
    public void close() throws IOException {

        propagate();
        w.flush();
        w.close();
    }

@Override
    public void flush() throws IOException {

        int length = buffer.length();
        if (length != 0) {
            w.write(buffer.toString());
            buffer.delete(0, length);
        }
        w.flush();
    }

    /**
     * Pass the buffer contents to the encapsulated writer.
     * 
     * @throws IOException in case of an I/O error
     */
    private void propagate() throws IOException {

        if (column >= lineLength + 2) {
            w.write(sep);
            column = in + buffer.length();
        }
        int length = buffer.length();
        if (length != 0) {
            w.write(buffer.toString());
            buffer.delete(0, length);
        }
    }

@Override
    public void write(char[] cbuf, int off, int len) throws IOException {

        for (int i = off; i < off + len; i++) {
            buffer.append(cbuf[i]);
            switch (cbuf[i]) {
                case '\n':
                case '\r':
                    propagate();
                    column = 0;
                    break;
                case '\f':
                case '\t':
                case ' ':
                    column++;
                    propagate();
                    break;
                default:
                    column++;
                    // nothing else to do
            }
        }
    }

}
