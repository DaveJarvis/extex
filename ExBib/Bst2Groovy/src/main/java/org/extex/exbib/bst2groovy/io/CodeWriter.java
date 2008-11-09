/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.io;

import java.io.IOException;
import java.io.Writer;

/**
 * This is a writer which keeps track of the current column.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CodeWriter extends Writer {

    /**
     * The field <tt>writer</tt> contains the wrapped writer.
     */
    private Writer writer;

    /**
     * The field <tt>col</tt> contains the counter for the current column.
     */
    private int column = 0;

    /**
     * Creates a new object.
     * 
     * @param writer the writer
     */
    public CodeWriter(Writer writer) {

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
     * 
     * @throws IOException in case of an I/O error
     */
    public int getColumn() throws IOException {

        flush();
        return column;
    }

    /**
     * Write a newline and indent to a certain column.
     * 
     * @param n the target column
     * 
     * @throws IOException in case of an I/O error
     */
    public void nl(int n) throws IOException {

        writer.write('\n');
        for (int i = 0; i < n; i++) {
            writer.write(' ');
        }
        column = n;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Writer#write(char[], int, int)
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

        int i = off;
        for (int no = len; no > 0; no--) {
            char c = cbuf[i++];
            switch (c) {
                case '\n':
                    column = 0;
                    break;
                case '\t':
                    column = ((column + 8) / 8) * 8;
                    break;
                default:
                    column++;
            }
        }
        writer.write(cbuf, off, len);
    }

    /**
     * Write some strings.
     * 
     * @param arg the arguments
     * 
     * @throws IOException in case of an I/O error
     */
    @Override
    public void write(String arg) throws IOException {

        super.write(arg);
    }

    /**
     * Write some strings.
     * 
     * @param args the arguments
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(String... args) throws IOException {

        for (String arg : args) {
            super.write(arg);
        }
    }

}
