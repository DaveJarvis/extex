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
import java.io.PrintWriter;

import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This writer performs line breaking. For this purpose some characters are
 * inserted into the output stream.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class BblWriter implements Writer {

    /**
     * The field <tt>DEFAULT_LINE_LENGTH</tt> contains the default line
     * length.
     */
    private static final int DEFAULT_LINE_LENGTH = 80;

    /**
     * The field <tt>indent</tt> contains the string which is inserted at the
     * beginning of a continuation line.
     */
    private String indent = "  ";

    /**
     * The field <tt>sb</tt> contains the temporary memory before shipping the
     * line to the writer.
     */
    private StringBuffer sb = new StringBuffer();

    /**
     * The field <tt>writer</tt> contains the output writer.
     */
    private Writer writer = null;

    /** The desired line length */
    /**
     * The field <tt>lineLength</tt> contains the desired line length.
     */
    private int lineLength = DEFAULT_LINE_LENGTH;

    /**
     * The field <tt>space</tt> contains the position of the last space
     * character.
     */
    private int space = -1;

    /**
     * Creates a new object.
     * 
     * @param writer the target writer
     */
    public BblWriter(Writer writer) {

        super();
        this.writer = writer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#close()
     */
    public void close() throws IOException {

        flush();
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
            indent = in;
        }

        lineLength = cfg.getValueAsInteger("lineLength", DEFAULT_LINE_LENGTH);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() throws IOException {

        writer.print(sb.toString());
        writer.flush();
        sb.setLength(0);
        space = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#getPrintWriter()
     */
    public PrintWriter getPrintWriter() {

        return null;
    }

    /**
     * Write a string to the output buffer and keep track of lines. Long lines
     * are broken at whitespace. Multiple whitespace characters are translated
     * to a single space. Complete lines are shipped to the output writer.
     * 
     * @throws IOException in case of an IO problem
     */
    private void linebreaking() throws IOException {

        char c;

        for (int i = space + 1; i < sb.length(); i++) {
            c = sb.charAt(i);

            if (c == '\n') {
                int sp = i;

                while (sp > 0 && Character.isWhitespace(sb.charAt(sp - 1))) {
                    sp--;
                }

                writer.println(sb.substring(0, sp));
                sb.delete(0, i + 1);
                space = -1;
                i = 0;
            } else if (Character.isWhitespace(c)) {
                if (c != ' ') {
                    if (space == i - 1) {
                        sb.deleteCharAt(i--);
                    } else {
                        sb.replace(i, i + 1, " ");
                    }
                }

                space = i;
            }

            if (i >= lineLength && space >= 0) {
                int sp = space;

                while (sp >= 0 && Character.isWhitespace(sb.charAt(sp))) {
                    sp--;
                }

                writer.println(sb.substring(0, sp + 1));
                sb.delete(0, space + 1);
                sb.insert(0, indent);
                space = -1;
                i = 0;
            }
        }
    }

    /**
     * Write a string to the output buffer and keep track of lines. Long lines
     * are broken at whitespace. Complete lines are shipped to the output
     * writer.
     * 
     * @param s the String to write to the output stream
     * 
     * @throws IOException in case of an IO problem
     */
    private void linebreaking(String s) throws IOException {

        sb.append(s);
        linebreaking();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String)
     */
    public void print(String s) throws IOException {

        linebreaking(s);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String,
     *      java.lang.String)
     */
    public void print(String s1, String s2) throws IOException {

        linebreaking(s1);
        linebreaking(s2);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void print(String s1, String s2, String s3) throws IOException {

        linebreaking(s1);
        linebreaking(s2);
        linebreaking(s3);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println()
     */
    public void println() throws IOException {

        // delete trailing spaces
        for (int i = sb.length() - 1; i >= 0
                && Character.isWhitespace(sb.charAt(i)); i--) {
            sb.deleteCharAt(i);
        }

        writer.println(sb.toString());
        sb.setLength(0);
        space = -1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String)
     */
    public void println(String s) throws IOException {

        linebreaking(s);
        println();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String,
     *      java.lang.String)
     */
    public void println(String s1, String s2) throws IOException {

        linebreaking(s1);
        linebreaking(s2);
        println();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void println(String s1, String s2, String s3) throws IOException {

        linebreaking(s1);
        linebreaking(s2);
        linebreaking(s3);
        println();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#write(int)
     */
    public void write(int c) throws IOException {

        sb.append((char) c);
        linebreaking();
    }

}
