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
    private static final int DEFAULT_LINE_LENGTH = 79;

    /**
     * The field <tt>indent</tt> contains the string which is inserted at the
     * beginning of a continuation line.
     */
    private String indent = "  ";

    /**
     * The field <tt>buffer</tt> contains the temporary memory before shipping
     * the line to the writer.
     */
    private StringBuilder buffer = new StringBuilder();

    /**
     * The field <tt>writer</tt> contains the output writer.
     */
    private Writer writer = null;

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

        writer.print(buffer.toString());
        writer.flush();
        buffer.setLength(0);
        space = 0;
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

        for (int i = space + 1; i < buffer.length(); i++) {
            c = buffer.charAt(i);

            if (c == '\n') {
                int sp = i;

                while (sp > 0 && Character.isWhitespace(buffer.charAt(sp - 1))) {
                    sp--;
                }

                writer.println(buffer.substring(0, sp));
                buffer.delete(0, i + 1);
                space = -1;
                i = 0;
            } else if (Character.isWhitespace(c)) {
                if (c != ' ') {
                    if (space == i - 1) {
                        buffer.deleteCharAt(i--);
                    } else {
                        buffer.replace(i, i + 1, " ");
                    }
                }

                space = i;
            }

            if (i >= lineLength && space >= 0) {
                int sp = space;

                while (sp >= 0 && Character.isWhitespace(buffer.charAt(sp))) {
                    sp--;
                }

                writer.println(buffer.substring(0, sp + 1));
                buffer.delete(0, space + 1);
                buffer.insert(0, indent);
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

        buffer.append(s);
        linebreaking();
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

        // delete trailing spaces
        for (int i = buffer.length() - 1; i >= 0
                && Character.isWhitespace(buffer.charAt(i)); i--) {
            buffer.deleteCharAt(i);
        }

        writer.println(buffer.toString());
        buffer.setLength(0);
        space = -1;
    }

    /**
     * Setter for indent.
     * 
     * @param indent the indent to set
     */
    public void setIndent(String indent) {

        this.indent = indent;
    }

    /**
     * Setter for lineLength.
     * 
     * @param lineLength the lineLength to set
     */
    public void setLineLength(int lineLength) {

        this.lineLength = lineLength;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#write(int)
     */
    public void write(int c) throws IOException {

        buffer.append((char) c);
        linebreaking();
    }

}
