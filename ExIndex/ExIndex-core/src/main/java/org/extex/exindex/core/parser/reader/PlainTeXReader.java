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
import java.util.HashMap;
import java.util.Map;

/**
 * This reader knows of certain plain<logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> control sequences and translates them to the appropriate
 * characters. It is assumed that the category codes have their normal meaning.
 * 
 * 
 * <pre>
 * \aa --> &aring;
 * \AA --> &Aring;
 * \ss --> &szlig;
 *
 * \"a --> &auml;
 * \" a --> &auml;
 * \"{a} --> &auml;
 * </pre>
 * 
 * 
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PlainTeXReader extends TeXReader {

    /**
     * The field <tt>MAP</tt> contains the mapping for special control
     * sequences.
     */
    private static final Map<String, String> MAP =
            new HashMap<String, String>();
    static {
        MAP.put("ss", "\u00DF");
        MAP.put("o", "\u00F8");
        MAP.put("O", "\u00D8");
        MAP.put("ae", "\u00E6");
        MAP.put("AE", "\u00C6");
        MAP.put("S", "\u00A7");
        MAP.put("aa", "\u00E5");
        MAP.put("AA", "\u00C5");
    }

    /**
     * The field <tt>buffer</tt> contains the line buffer.
     */
    private final StringBuilder buffer = new StringBuilder();

    /**
     * The field <tt>pointer</tt> contains the pointer to the next undelivered
     * buffer position.
     */
    private int pointer = 0;

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource for error messages
     * @param reader the reader
     */
    public PlainTeXReader(String resource, Reader reader) {

        super(resource, reader);
    }

    /**
     * Append a character to the line buffer.
     * 
     * @param c the character to append
     */
    protected void bufferAppend(char c) {

        buffer.append(c);
    }

    /**
     * Check that the buffer is not empty.
     * 
     * @return <code>true</code> iff the buffer is not empty
     */
    protected boolean bufferIsNotEmpty() {

        return (buffer.length() != 0);
    }

    /**
     * Fill the line buffer.
     * 
     * @return the EOF indicator
     * 
     * @throws IOException in case of an I/O error
     */
    protected boolean fillBuffer() throws IOException {

        resetBuffer();
        int c = super.read();
        while (c >= 0) {
            if (c == '\\') {
                c = fillEsc();
            } else {
                buffer.append((char) c);
                if (c == '\n' || c == '\r') {
                    return true;
                }
                c = super.read();
            }
        }
        return bufferIsNotEmpty();
    }

    /**
     * Continue filling the line buffer after a backslash has been encountered.
     * 
     * @return th next character
     * 
     * @throws IOException in case of an I/O error
     */
    protected int fillEsc() throws IOException {

        int cc = super.read();
        if (cc < 0) {
            buffer.append('\\');
            return super.read();
        }
        String a;
        String t;
        switch (cc) {
            case '"':
                a = "AEIOUaeouy";
                t = "\u00C4\u00CB\u00CF\u00D6\u00DC" 
                        + "\u00E4\u00EB\u00F6\u00FC\u00FF";
                break;
            case '\'':
                a = "AEIOUYaeouy";
                t = "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD" 
                        + "\u00E1\u00E9\u00F3\u00FA\u00FD";
                break;
            case '`':
                a = "AEIOUaeiou";
                t = "\u00C0\u00C8\u00CC\u00D2\u00D9" 
                        + "\u00E0\u00E8\u00ED\u00F2\u00F9";
                break;
            case '^':
                a = "AEIOUaeou";
                t = "\u00C2\u00CA\u00CE\u00D4\u00DB\u00E2\u00EA\u00F4\u00FB";
                break;
            case '~':
                a = "ANOano";
                t = "\u00C3\u00D1\u00D5\u00E3\u00F1\u00F5";
                break;
            default:
                StringBuilder sb = new StringBuilder();
                int c = cc;
                while (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
                    sb.append((char) c);
                    c = super.read();
                }
                String s = MAP.get(sb.toString());
                if (s != null) {
                    buffer.append(s);
                    while (c == ' ' || c == '\t') {
                        c = super.read();
                    }
                    if (c == '{') {
                        c = super.read();
                        if (c == '}') {
                            return super.read();
                        }
                        buffer.append('{');
                    }
                    return c;
                } else if ("c".equals(sb.toString())) {
                    a = "cC";
                    t = "\u00E7\u00C7";
                } else {
                    buffer.append('\\');
                    buffer.append(sb);
                    return c;
                }
        }

        int c;
        do {
            c = super.read();
        } while (c == ' ' || c == '\t');

        if (c == '{') {
            int d = super.read();
            if (d < 0) {
                buffer.append('\\');
                buffer.append((char) cc);
            } else {
                c = super.read();
                if (c == '}') {
                    int i = a.indexOf(d);
                    if (i < 0) {
                        buffer.append('\\');
                        buffer.append((char) cc);
                        buffer.append('{');
                        buffer.append((char) d);
                    } else {
                        c = t.charAt(i);
                    }
                } else {
                    buffer.append('\\');
                    buffer.append((char) cc);
                    buffer.append('{');
                    if (c < 0) {
                        c = d;
                    } else {
                        buffer.append((char) d);
                    }
                }
            }
        } else if (c >= 0) {
            int i = a.indexOf(c);
            if (i < 0) {
                buffer.append('\\');
                buffer.append((char) cc);
                if (cc == 'c') {
                    buffer.append(' ');
                }
            } else {
                c = t.charAt(i);
            }
        } else {
            buffer.append('\\');
            c = cc;
        }
        buffer.append((char) c);
        return super.read();
    }

    /**
     * Read a single character.
     * 
     * @return the character read or -1
     * 
     * @throws IOException in case of an error
     */
    protected int rawRead() throws IOException {

        return super.read();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.reader.TeXReader#read()
     */
    @Override
    public int read() throws IOException {

        return (pointer >= buffer.length() && !fillBuffer()) 
                ? -1
                : buffer.charAt(pointer++);
    }

    /**
     * Clear the buffer and reset the pointer to the beginning.
     */
    protected void resetBuffer() {

        pointer = 0;
        int len = buffer.length();
        if (len > 0) {
            buffer.delete(0, len);
        }
    }

}
