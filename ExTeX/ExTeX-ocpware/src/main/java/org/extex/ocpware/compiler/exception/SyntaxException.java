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

package org.extex.ocpware.compiler.exception;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.extex.ocpware.exception.OcpException;

/**
 * This exception class signals the occurrence of a syntax error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SyntaxException extends OcpException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>expected</tt> contains the expected character or a
     * negative value for undefined.
     */
    private int expected;

    /**
     * The field <tt>found</tt> contains the character found or a negative
     * value for undefined.
     */
    private int found;

    /**
     * The field <tt>line</tt> contains the current line up to the last
     * character read.
     */
    private CharSequence line;

    /**
     * The field <tt>lineno</tt> contains the number of the current line.
     */
    private int lineno;


    protected SyntaxException() {

        super("");
    }

    /**
     * Creates a new object.
     * 
     * @param line the current line up to the last character read
     * @param lineno the number of the current line
     */
    public SyntaxException(CharSequence line, int lineno) {

        super("");
        this.found = -1;
        this.expected = -1;
        this.line = line;
        this.lineno = lineno;
    }

    /**
     * Creates a new object.
     * 
     * @param found the character found; negative values indicate EOF
     * @param line the current line up to the last character read
     * @param lineno the number of the current line
     */
    public SyntaxException(int found, CharSequence line, int lineno) {

        super("");
        this.found = found;
        this.expected = -1;
        this.line = line;
        this.lineno = lineno;
    }

    /**
     * Creates a new object.
     * 
     * @param found the character found; negative values indicate EOF
     * @param expected the expected character
     * @param line the current line up to the last character read
     * @param lineno the number of the current line
     */
    public SyntaxException(int found, int expected, CharSequence line,
            int lineno) {

        super("");
        this.found = found;
        this.expected = expected;
        this.line = line;
        this.lineno = lineno;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        try {
            ResourceBundle bundle =
                    ResourceBundle.getBundle(getClass().getName());

            String[] x =
                    new String[]{"Message1", "Message2", "Message3", "Message4"};
            return MessageFormat
                .format(
                    bundle.getString(x[(found < 0 ? 0 : 2)
                            + (expected < 0 ? 0 : 1)]), //
                    (found < 0 ? "EOF" : Character.toString((char) found)),
                    (expected < 0 ? "EOF" : Character.toString((char) expected)),
                    Integer.toString(lineno), line);
        } catch (MissingResourceException e) {
            return super.getLocalizedMessage();
        }
    }
}
