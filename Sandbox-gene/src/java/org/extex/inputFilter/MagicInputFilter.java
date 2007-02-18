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

package org.extex.inputFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.interpreter.context.Context;
import org.extex.scanner.type.Catcode;
import org.extex.type.UnicodeChar;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MagicInputFilter extends InputStream {

    /**
     * The field <tt>context</tt> contains the interpreter context.
     */
    private Context context;

    /**
     * The field <tt>start</tt> contains the indicator that no character has
     * been read yet.
     */
    private boolean start = true;

    /**
     * The field <tt>stream</tt> contains the input stream to read from.
     */
    private InputStream stream;

    /**
     * Creates a new object.
     *
     * @param stream ...
     * @param context the interpreter context
     */
    public MagicInputFilter(final InputStream stream, final Context context) {

        super();
        this.context = context;
        this.stream = stream;
    }

    /**
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {

        if (!start) {
            return stream.read();
        }
        start = false;
        int c = stream.read();
        if (c < 0 || context.getCatcode(UnicodeChar.get(c)) != Catcode.COMMENT) {
            return c;
        }
        StringBuffer sb = new StringBuffer();
        for (int cc = stream.read(); cc != 0 && cc != '\n'; cc = stream.read()) {
            sb.append((char) cc);
        }
        Pattern p = Pattern.compile("[ \t]*&([^ \t]+)");
        Matcher m = p.matcher(sb);
        if (m.lookingAt()) {
            String format = sb.substring(m.start(1), m.end(1));
            System.err.println(format);
        }

        return stream.read();
    }

    /**
     * @see java.io.InputStream#available()
     */
    public int available() throws IOException {

        return stream.available();
    }

    /**
     * @see java.io.InputStream#close()
     */
    public void close() throws IOException {

        stream.close();
    }

    /**
     * @see java.io.InputStream#mark(int)
     */
    public void mark(final int readlimit) {

        stream.mark(readlimit);
    }

    /**
     * @see java.io.InputStream#markSupported()
     */
    public boolean markSupported() {

        return stream.markSupported();
    }

    /**
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public int read(final byte[] b, final int off, final int len)
            throws IOException {

        return stream.read(b, off, len);
    }

    /**
     * @see java.io.InputStream#read(byte[])
     */
    public int read(final byte[] b) throws IOException {

        return stream.read(b);
    }

    /**
     * @see java.io.InputStream#reset()
     */
    public void reset() throws IOException {

        stream.reset();
    }

    /**
     * @see java.io.InputStream#skip(long)
     */
    public long skip(final long n) throws IOException {

        return stream.skip(n);
    }

}
