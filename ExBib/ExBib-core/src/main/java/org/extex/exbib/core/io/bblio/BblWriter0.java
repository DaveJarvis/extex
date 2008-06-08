/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bblio;

import java.io.IOException;

import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is a writer for bbl files.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class BblWriter0 implements Writer {

    /**
     * The field <tt>DEFAULT_LINE_LENGTH</tt> contains the default line
     * length.
     */
    private static final int DEFAULT_LINE_LENGTH = 79;

    /**
     * The field <tt>indent</tt> contains the string inserted at the place
     * where line breaking is performed.
     */
    private String indent = "\n  ";

    /**
     * The field <tt>writer</tt> contains the output writer.
     */
    private Writer writer = null;

    /**
     * The field <tt>col</tt> contains the current column.
     */
    private int col = 0;

    /**
     * The field <tt>lineLength</tt> contains the desired line length.
     */
    private int lineLength = DEFAULT_LINE_LENGTH;

    /**
     * Creates a new object.
     * 
     * @param writer the target writer
     */
    public BblWriter0(Writer writer) {

        super();
        this.writer = writer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#close()
     */
    public void close() throws IOException {

        writer.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration cfg) throws ConfigurationException {

        String in = cfg.getValue("indent");

        if (in != null) {
            indent = "\n" + in;
        }

        lineLength = cfg.getValueAsInteger("lineLength", DEFAULT_LINE_LENGTH);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() throws IOException {

        writer.flush();
    }

    /**
     * Write a string to the output stream and keep track of lines. Long lines
     * are broken at whitespace.
     * 
     * @param s the String to write to the output stream
     * 
     * @throws IOException in case of an I/O error
     */
    private void linebreaking(String s) throws IOException {

        StringBuffer stb = new StringBuffer(s);
        int br = -1;
        int col2 = col;

        for (int i = 0; i < stb.length(); i++) {
            switch (stb.charAt(i)) {
                case ' ':
                    col2 = 0;
                    br = i;
                    break;
                case '\t':
                    stb.setCharAt(i, ' ');
                    col2 = 0;
                    br = i;
                    break;
                case '\n':
                    col = -1;
                    col2 = -1;
                default: // keep going
            }

            if (++col > lineLength && br >= 0) {
                stb.deleteCharAt(br);
                stb.insert(br, indent);
                col = col2++ + indent.length();
                br = -1;
            } else {
                col2++;
            }
        }

        writer.print(stb.toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String[])
     */
    public void print(String... args) throws IOException {

        for (String s : args) {
            linebreaking(s);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String[])
     */
    public void println(String... args) throws IOException {

        for (String s : args) {
            linebreaking(s);
        }
        writer.println();
        col = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#write(int)
     */
    public void write(int c) throws IOException {

        if (++col >= lineLength && c == '\n') {
            writer.println();
            col = 0;
        } else {
            writer.write(c);
        }
    }

}
