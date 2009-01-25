/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bblio;

import java.io.IOException;

import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This writer performs line breaking. For this purpose some characters are
 * inserted into the output stream.
 * 
 * <p>
 * The line breaking tries its best to achieve the given line length.
 * Nevertheless it is is not too eager. Thus a line might be longer of no proper
 * breaking point could be found. Especially it does <em>not</em> emulate the
 * B<small>IB</small>T<sub>E</sub>X behavior of inserting percent signs in the
 * output just to force a line break.
 * </p>
 * 
 * <p>
 * In the course of line breaking all whitespace characters except the newline
 * character are replaced by simple spaces. Trailing spaces are omitted and
 * multiple spaces collapsed into one.
 * </p>
 * 
 * <p>
 * This class buffers the output to find a possible place to break the lines.
 * The buffered characters are sent to the underlying writer as soon as
 * possible.
 * </p>
 * 
 * <p>
 * This class is configurable. See {@link #configure(Configuration)} for the
 * available configuration options.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BblWriter implements Writer, Configurable {

    /**
     * The field <tt>DEFAULT_LINE_LENGTH</tt> contains the default line length.
     */
    public static final int DEFAULT_LINE_LENGTH = 79;

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
     * The field <tt>collapseSpaces</tt> contains the flag to collapse spaces.
     */
    private boolean collapseSpaces = false;

    /**
     * Creates a new object.
     * 
     * @param writer the target writer; it can not be <code>null</code>
     */
    public BblWriter(Writer writer) {

        if (writer == null) {
            throw new IllegalArgumentException(getClass().getName() + "(null)");
        }
        this.writer = writer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#close()
     */
    public void close() throws IOException {

        if (writer != null) {
            flush();
            writer.close();
            writer = null;
        }
    }

    /**
     * This method extracts some configuration parameters from a given
     * configuration. The following parameters are used:
     * <dl>
     * <dt>indent</dt>
     * <dd>This string argument contains the prefix to be used for indentation
     * of continuation lines. The default is a string with two spaces.</dd>
     * <dt>lineLength</dt>
     * <dd>This numeric argument contains the line length after which line
     * breaking should be tried. The default is the value of
     * {@link #DEFAULT_LINE_LENGTH}. It is used when the parameter is not given
     * or the parameter contains no number. A number less than 1 is silently
     * ignored.</dd>
     * </dl>
     * <p>
     * The following example shows how a configuration may look like.
     * </p>
     * 
     * <pre>
     *   &lt;bblWriter&gt;
     *     &lt;indent&gt;  &lt;/indent&gt;
     *     &lt;lineLength&gt;79&lt;/lineLength&gt;
     *   &lt;/bblWriter&gt;
     * </pre>
     * 
     * @param cfg the configuration object to consider
     * 
     * @throws ConfigurationException in case that something went wrong
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration cfg) throws ConfigurationException {

        setIndent(cfg.getValue("indent"));
        setLineLength(cfg.getValueAsInteger("lineLength", DEFAULT_LINE_LENGTH));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() throws IOException {

        writer.print(buffer.toString());
        buffer.delete(0, buffer.length());
        space = -2;
        writer.flush();
    }

    /**
     * Insert a newline near a column. Trailing spaces are omitted.
     * 
     * @param i the target index
     * 
     * @throws IOException in case of an I/O error
     */
    private void foldAt(int i) throws IOException {

        int endPointer = i;

        while (endPointer > 0
                && Character.isWhitespace(buffer.charAt(endPointer - 1))) {
            endPointer--;
        }

        writer.println(buffer.substring(0, endPointer));
        buffer.delete(0, i + 1);
        space = -2;
    }

    /**
     * Write a string to the output buffer and keep track of lines. Long lines
     * are broken at whitespace. Multiple whitespace characters are translated
     * to a single space. Complete lines are shipped to the output writer.
     * 
     * @throws IOException in case of an I/O problem
     */
    private void linebreaking() throws IOException {

        char c;

        for (int i = (space < -1 ? 0 : space + 1); i < buffer.length(); i++) {
            c = buffer.charAt(i);

            if (c == '\n') {
                foldAt(i);
                i = 0;
            } else {
                if (Character.isWhitespace(c)) {
                    if (space == i - 1 && collapseSpaces) {
                        buffer.deleteCharAt(i--);
                    } else if (c != ' ') {
                        buffer.replace(i, i + 1, " ");
                    }
                    space = i;
                }

                if (i >= lineLength && space >= 0) {
                    foldAt(space);
                    buffer.insert(0, indent);
                    i = 0;
                }
            }
        }
    }

    /**
     * Print a newline.
     * 
     * @throws IOException in case of an I/O error
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String[])
     */
    public void newline() throws IOException {

        foldAt(buffer.length());
    }

    /**
     * Write some strings to the output buffer and keep track of lines. Long
     * lines are broken at whitespace. Complete lines are shipped to the output
     * writer.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String[])
     */
    public void print(String... args) throws IOException {

        for (String s : args) {
            buffer.append(s);
            linebreaking();
        }
    }

    /**
     * Write a string to the output writer.
     * <p>
     * Note, that this is a work-around for a problem with the Groovy extension
     * language.
     * </p>
     * 
     * @param arg the argument to print
     * 
     * @throws IOException in case of an I/O error
     */
    public void print(String arg) throws IOException {

        buffer.append(arg);
        linebreaking();
    }

    /**
     * Print a newline.
     * 
     * @throws IOException in case of an I/O error
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String[])
     */
    public void println() throws IOException {

        foldAt(buffer.length());
    }

    /**
     * Write some strings to the output buffer and keep track of lines. Long
     * lines are broken at whitespace. Complete lines are shipped to the output
     * writer. Finally a newline is written.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String[])
     */
    public void println(String... args) throws IOException {

        print(args);
        println();
    }

    /**
     * Write a string followed by a newline to the output writer.
     * <p>
     * Note, that this is a work-around for a problem with the Groovy extension
     * language.
     * </p>
     * 
     * @param arg the argument to print
     * 
     * @throws IOException in case of an I/O error
     */
    public void println(String arg) throws IOException {

        print(arg);
        println();
    }

    /**
     * Setter for the collapseSpaces.
     * 
     * @param collapseSpaces the collapseSpaces to set
     */
    public void setCollapseSpaces(boolean collapseSpaces) {

        this.collapseSpaces = collapseSpaces;
    }

    /**
     * Setter for indent.
     * 
     * @param indent the indent to set; a <code>null</code> value is silently
     *        ignored
     */
    public void setIndent(String indent) {

        if (indent != null) {
            this.indent = indent;
        }
    }

    /**
     * Setter for the line length.
     * 
     * @param lineLength the line length to set; a value less than 1 is silently
     *        ignored
     */
    public void setLineLength(int lineLength) {

        if (lineLength > 0) {
            this.lineLength = lineLength;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return Integer.toString(lineLength) + " >" + buffer.toString() + "<";
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

    /**
     * Write some strings to the output writer.
     * <p>
     * Note, that this is a work-around for a problem with the Groovy extension
     * language.
     * </p>
     * 
     * @param arg the arguments to print
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(String[] arg) throws IOException {

        print(arg);
    }

    /**
     * Write some strings followed by a newline to the output writer.
     * <p>
     * Note, that this is a work-around for a problem with the Groovy extension
     * language.
     * </p>
     * 
     * @param arg the arguments to print
     * 
     * @throws IOException in case of an I/O error
     */
    public void writeln(String[] arg) throws IOException {

        println(arg);
    }

}
