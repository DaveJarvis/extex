/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.xparser.makeindex;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.exception.EofException;
import org.extex.exindex.core.exception.MissingException;
import org.extex.exindex.core.normalizer.Collator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MakeindexFilter extends Reader {

    /**
     * The field <tt>in</tt> contains the input.
     */
    private LineNumberReader in;

    /**
     * The field <tt>buffer</tt> contains the intermediate buffer.
     */
    private StringBuilder buffer = new StringBuilder();

    private String keyword = "\\indexentry";

    private char argOpen = '{';

    private char argClose = '}';

    private char escape = '"';

    private char quote = '\\';

    private char encap = '|';

    private char level = '!';

    private char actual = '@';

    /**
     * Creates a new object.
     * 
     * @param in the input reader
     */
    public MakeindexFilter(Reader in) {

        super();
        this.in = new LineNumberReader(in);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#close()
     */
    @Override
    public void close() throws IOException {

        in.close();
        in = null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param r the reader
     * @param resource the name of the resource
     * @param ec the expected character
     * 
     * @throws IOException in case of an error
     */
    private void expect(LineNumberReader r, String resource, char ec)
            throws IOException {

        int c = r.read();
        if (c != ec) {
            throw new MissingException(resource, r.getLineNumber(), (char) c,
                ec);
        }
    }

    private boolean fillBuffer(String resource, Collator collator)
            throws IOException {

        char k0 = keyword.charAt(0);

        for (int c = in.read(); c >= 0; c = in.read()) {
            if (c == k0 && scanKeyword(in, keyword)) {
                String arg = scanArgument(resource);
                String p = scanArgument(resource);
                String enc = null;
                int x = arg.lastIndexOf(encap);
                if (x >= 0) {
                    enc = arg.substring(x + 1);
                    arg = arg.substring(0, x);
                }
                x = arg.indexOf(actual);
                String a;
                if (x >= 0) {
                    a = arg.substring(x + 1);
                    arg = arg.substring(0, x);
                } else {
                    a = arg;
                }
                if (collator != null) {
                    a = collator.collate(a);
                }
                store(arg, p, enc, a);
                return false;
            }
        }
        in.close();
        in = null;
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {

        if (buffer.length() <= 0) {
            if (in == null || fillBuffer(null, null)) {
                return -1;
            }
        }

        int length = buffer.length();
        int bp = 0;
        int i;
        for (i = 0; i < len && bp < length; i++) {
            cbuf[off + i] = buffer.charAt(bp++);
        }

        buffer.delete(0, i);
        return i;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param resource the name of the resource
     * @return the argument found
     * 
     * @throws IOException in case of an error
     */
    private String scanArgument(String resource) throws IOException {

        expect(in, resource, argOpen);
        StringBuilder sb = new StringBuilder();
        int level = 1;

        for (;;) {
            int c = in.read();
            if (c < 0) {
                throw new EofException(resource, in.getLineNumber());
            } else if (c == argOpen) {
                level++;
            } else if (c == argClose) {
                level--;
                if (level <= 0) {
                    return sb.toString();
                }
            } else if (c == quote) {
                int l = sb.length();
                if (l <= 0 || sb.charAt(l - 1) != escape) {
                    c = in.read();
                    if (c < 0) {
                        throw new EofException(resource, in.getLineNumber());
                    }
                }
            }
            sb.append((char) c);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param r the reader
     * @param keyword the keyword to read
     * 
     * @return <code>true</code> iff the keyword has been found
     * 
     * @throws IOException in case of an I/O error
     */
    private boolean scanKeyword(Reader r, String keyword) throws IOException {

        int length = keyword.length();
        for (int i = 1; i < length; i++) {
            int c = r.read();
            if (c != keyword.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param a1
     * @param p the page
     * @param encap the page encapsulator or <code>null</code> for none
     * @param display the display representation
     */
    private void store(String a1, String p, String encap, String display) {

        String key = a1;
        List<String> list = new ArrayList<String>();
        int a = 0;
        for (int i = key.indexOf(level, a); i >= 0; i = key.indexOf(level, a)) {
            list.add(key.substring(a, i));
            a = i + 1;
        }
        list.add(key);

        buffer.append("(indexentry :key (");
        for (String k : list) {
            buffer.append('"');
            buffer.append(k.replaceAll("[\"\\]", "\\\\\\1").replaceAll("\n",
                "\\n"));
            buffer.append('"');
            buffer.append(' ');
        }
        buffer.append(") :print (");
        // TODO
        buffer.append(") ");
        if (encap != null) {
            buffer.append(":attr \"");
            buffer.append(encap.replaceAll("[\"\\]", "\\\\\\1").replaceAll(
                "\n", "\\n"));
            buffer.append("\" ");
        }
        buffer.append(":locref ");

        buffer.append("\"");
        buffer.append("\"");

        buffer.append(")");
        // TODO
    }

}
