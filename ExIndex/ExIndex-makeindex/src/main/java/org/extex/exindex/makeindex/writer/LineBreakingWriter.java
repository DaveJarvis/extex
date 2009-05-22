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
 * @version $Revision: 5432 $
 */
public class LineBreakingWriter extends Writer {

    /**
     * The field <tt>buffer</tt> contains the intermediary buffer.
     */
    private StringBuilder buffer = new StringBuilder();

    /**
     * The field <tt>w</tt> contains the target writer.
     */
    private Writer w;

    /**
     * The field <tt>column</tt> contains the current column.
     */
    private int column = 0;

    /**
     * The field <tt>sep</tt> contains the separator.
     */
    private String sep;

    /**
     * The field <tt>in</tt> contains the width of sep.
     */
    private int in;

    /**
     * The field <tt>lineLength</tt> contains the desired line length.
     */
    private int lineLength;

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

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Writer#close()
     */
    @Override
    public void close() throws IOException {

        if (column >= lineLength + 1) {
            w.write(sep);
            column = in + buffer.length();
        }
        flush();
        w.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Writer#flush()
     */
    @Override
    public void flush() throws IOException {

        propagate();
        w.flush();
    }

    private void propagate() throws IOException {

        int length = buffer.length();
        if (length != 0) {
            w.write(buffer.toString());
            buffer.delete(0, length);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Writer#write(char[], int, int)
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

        for (int i = off; i < off + len; i++) {
            char c = cbuf[i];
            buffer.append(c);
            switch (c) {
                case '\n':
                case '\r':
                    propagate();
                    column = 0;
                    break;
                case '\f':
                case '\t':
                case ' ':
                    column++;
                    if (column >= lineLength + 1) {
                        w.write(sep);
                        column = in + buffer.length();
                    }
                    propagate();
                    break;
                default:
                    column++;
            }
        }
    }

}
