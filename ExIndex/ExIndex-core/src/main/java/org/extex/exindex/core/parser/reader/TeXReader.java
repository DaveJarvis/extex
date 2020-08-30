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
 * Reader which translates ^^ notation on the fly into characters.
 * <p>
 * TeX translates ^^ notation into characters at a very early
 * stage of parsing. This behavior is imitated in this reader. In contrast to
 * <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X<logo> no category codes are involved.
 * </p>
 * <p>
 * TeX defines the mapping of characters following ^^ according to
 * the following rules:
 * </p>
 * <ul>
 * <li>If the next one or two letters make up a hexadecimal number build from
 * digits or lower-case letters, then this number is the code point to use
 * instead.</li>
 * <li>If the next character has a code point less than 64, then 64 is added to
 * the code point and the new value used instead.</li>
 * <li>Otherwise 64 is subtracted from the code point and the new value is used
 * instead.</li>
 * </ul>
 * 
 * <p>
 * Examples:
 * </p>
 * <p>
 * The following tables shows some examples of the ^^ notation.
 * </p>
 * <table>
 * <tr>
 * <th>Input</th>
 * <th>Code point</th>
 * <th>Explanation</th>
 * </tr>
 * <tr>
 * <td><tt>^^A</tt></td>
 * <td>1</td>
 * <td>The letter A has the code point 65 which gets subtracted 64 leading to 1.
 * </td>
 * </tr>
 * <tr>
 * <td><tt>^^01</tt></td>
 * <td>1</td>
 * <td>01 is interpreted as hex number which is 1.</td>
 * </tr>
 * <tr>
 * <td><tt>^^.</tt></td>
 * <td>110</td>
 * <td>The character . has the code point 56. This is less than 64 and 64 is
 * added. This results in 110 which happens to be the letter n.</td>
 * </tr>
 * </table>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TeXReader extends ReaderLocator {

    /**
     * The field <tt>save</tt> contains the saved character or -1 for none.
     */
    private int save = -1;

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource currently read
     * @param reader the reader to use for input
     */
    public TeXReader(String resource, Reader reader) {

        super(resource, reader);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.FilterReader#read()
     */
    @Override
    public int read() throws IOException {

        int c;
        if (save < 0) {
            c = super.read();
        } else {
            c = save;
            save = -1;
        }
        if (c != '^') {
            return c;
        }
        c = super.read();
        if (c != '^') {
            save = c;
            return '^';
        }
        int ret = 0;
        c = super.read();
        if ('0' <= c && c <= '9') {
            ret = c - '0';
        } else if ('a' <= c && c <= 'f') {
            ret = c + 10 - 'a';
        } else {
            return (c < 64 ? c + 64 : c - 64);
        }
        c = super.read();
        if ('0' <= c && c <= '9') {
            ret = (ret << 4) + c - '0';
        } else if ('a' <= c && c <= 'f') {
            ret = (ret << 4) + c - 'a';
        } else {
            save = c;
        }

        return ret;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {

        int i = 0;
        for (; i < len; i++) {
            int c = read();
            if (c < 0) {
                return i == 0 ? -1 : i;
            }
            cbuf[off + i] = (char) c;
        }
        return i;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.FilterReader#skip(long)
     */
    @Override
    public long skip(long n) throws IOException {

        for (long i = 0; i < n; i++) {
            if (read() < 0) {
                return i;
            }
        }
        return n;
    }

}
