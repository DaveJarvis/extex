/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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
 * This is a writer which keeps track of the current column. Tab characters are
 * replaced by an appropriate number of spaces on the fly.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CodeWriter extends Writer {

    /**
     * The field {@code writer} contains the wrapped writer.
     */
    private final Writer writer;

    /**
     * The field {@code tabSize} contains the number of spaces for indentation.
     */
    private int tabSize = 2;

    /**
     * The field {@code column} contains the counter for the current column.
     */
    private int column = 0;

    /**
     * The field {@code inLine} contains the number of newline characters
     * already encountered. Initially it is 3 to suppress leading newlines.
     */
    private int inLine = 3;

    /**
     * Creates a new object.
     * 
     * @param writer the writer
     */
    public CodeWriter(Writer writer) {

        this.writer = writer;
    }

@Override
    public void close() throws IOException {

        writer.close();
    }

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

        if (inLine < 2) {
            writer.write('\n');
        }
        for (int i = 0; i < n; i++) {
            writer.write(' ');
        }
        column = n;
        inLine = 0;
    }

    /**
     * Setter for the tab size.
     * 
     * @param tabSize the tab size to set
     */
    public void setTabSize(int tabSize) {

        this.tabSize = tabSize;
    }

@Override
    public void write(char[] cbuf, int offset, int length) throws IOException {

        int off = offset;
        int len = length;
        int i = off;
        for (int no = length; no > 0; no--) {
            char c = cbuf[i++];
            switch (c) {
                case '\n':
                    if (inLine >= 2) {
                        int n = i - off - 1;
                        if (n > 0) {
                            writer.write(cbuf, off, n);
                        }
                        off = i;
                        len -= n + 1;
                    }
                    column = 0;
                    inLine++;
                    break;
                case '\t':
                    if (tabSize > 0) {
                        int n = i - off - 1;
                        if (n > 0) {
                            writer.write(cbuf, off, n);
                        }
                        off = i;
                        len -= n + 1;
                        int x = ((column + tabSize) / tabSize) * tabSize;
                        while (column < x) {
                            writer.write(' ');
                            column++;
                        }
                    }
                    inLine = 0;
                    break;
                case '\r':
                    break;
                default:
                    column++;
                    inLine = 0;
            }
        }
        if (len > 0) {
            writer.write(cbuf, off, len);
        }
    }

    /**
     * Write some strings.
     * 
     * @param arg the first arguments
     * @param arg2 the second arguments
     * @param args the remaining arguments
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(String arg, String arg2, String... args)
            throws IOException {

        super.write(arg);
        super.write(arg2);
        for (String a : args) {
            super.write(a);
        }
    }

}
