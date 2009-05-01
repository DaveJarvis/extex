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
 * This writer keeps track of the current column.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class ColumnCountingWriter extends Writer {

    /**
     * The field <tt>tabSize</tt> contains the width of a tab.
     */
    private int tabSize = 8;

    /**
     * The field <tt>writer</tt> contains the next writer.
     */
    private Writer writer;

    /**
     * The field <tt>column</tt> contains the current column.
     */
    private int column;

    /**
     * Creates a new object.
     * 
     * @param writer the next writer
     */
    public ColumnCountingWriter(Writer writer) {

        this.writer = writer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Writer#close()
     */
    @Override
    public void close() throws IOException {

        writer.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Writer#flush()
     */
    @Override
    public void flush() throws IOException {

        writer.flush();
    }

    /**
     * Getter for the column.
     * 
     * @return the column
     */
    public int getColumn() {

        return column;
    }

    /**
     * Setter for column.
     * 
     * @param column the column to set
     */
    public void setColumn(int column) {

        this.column = column;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Writer#write(char[], int, int)
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

        for (int i = off; i < off + len; i++) {
            switch (cbuf[i]) {
                case '\n':
                case '\r':
                    column = 0;
                    break;
                case '\t':
                    column += tabSize * ((column + 8) % 8);
                    break;
                default:
                    column++;
            }
        }
        writer.write(cbuf, off, len);
    }

}
