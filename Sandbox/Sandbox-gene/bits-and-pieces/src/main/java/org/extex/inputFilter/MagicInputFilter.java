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

import org.extex.core.UnicodeChar;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.Catcode;

/*
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision:6459 $
 */
/*
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision$
 */
public class MagicInputFilter extends InputStream {

    /*
     * The field <tt>context</tt> contains the interpreter context.
     * 
     * @uml.property name="context"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="context"
     * 
     * @uml.associationEnd
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
     * @param stream the input stream
     * @param context the interpreter context
     */
    public MagicInputFilter(InputStream stream, Context context) {

        super();
        this.context = context;
        this.stream = stream;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {

        if (!start) {
            return stream.read();
        }
        start = false;
        int c = stream.read();
        if (c < 0 || context.getCatcode(UnicodeChar.get(c)) != Catcode.COMMENT) {
            return c;
        }
        StringBuilder sb = new StringBuilder();
        for (int cc = stream.read(); cc != 0 && cc != '\n'; cc = stream.read()) {
            sb.append((char) cc);
        }
        Pattern p = Pattern.compile("[ \t]*&([^ \t]+)");
        Matcher m = p.matcher(sb);
        if (m.lookingAt()) {
            String format = sb.substring(m.start(1), m.end(1));
//            System. err.println(format);
        }

        return stream.read();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#available()
     */
    @Override
    public int available() throws IOException {

        return stream.available();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#close()
     */
    @Override
    public void close() throws IOException {

        stream.close();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#mark(int)
     */
    @Override
    public void mark(int readlimit) {

        stream.mark(readlimit);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#markSupported()
     */
    @Override
    public boolean markSupported() {

        return stream.markSupported();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(byte[] b, int off, int len)
            throws IOException {

        return stream.read(b, off, len);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#read(byte[])
     */
    @Override
    public int read(byte[] b) throws IOException {

        return stream.read(b);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#reset()
     */
    @Override
    public void reset() throws IOException {

        stream.reset();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.io.InputStream#skip(long)
     */
    @Override
    public long skip(long n) throws IOException {

        return stream.skip(n);
    }

}
