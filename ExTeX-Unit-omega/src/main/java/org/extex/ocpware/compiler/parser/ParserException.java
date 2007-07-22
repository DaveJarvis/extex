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

package org.extex.ocpware.compiler.parser;

import java.io.IOException;

/**
 * This exception class signals the occurrence of a syntax error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ParserException extends IOException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>found</tt> contains the ...
     */
    private int found;

    /**
     * The field <tt>expected</tt> contains the ...
     */
    private int expected;

    /**
     * The field <tt>line</tt> contains the current line up to the last
     * character read.
     */
    private CharSequence line;

    /**
     * The field <tt>lineno</tt> contains the number of the current line.
     */
    private int lineno;

    /**
     * Creates a new object.
     * 
     * @param found the character found; negative values indicate EOF
     * @param expected the expected character
     * @param line the current line up to the last character read
     * @param lineno the number of the current line
     */
    public ParserException(int found, int expected, CharSequence line,
            int lineno) {

        super();
        this.found = found;
        this.expected = expected;
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
    public ParserException(int found, CharSequence line, int lineno) {

        super();
        this.found = found;
        this.expected = -1;
        this.line = line;
        this.lineno = lineno;
    }

    /**
     * Creates a new object.
     * 
     * @param line the current line up to the last character read
     * @param lineno the number of the current line
     */
    public ParserException(CharSequence line, int lineno) {

        super();
        this.found = -1;
        this.expected = -1;
        this.line = line;
        this.lineno = lineno;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {

        String s =
                "unexpected "
                        + (found < 0 ? "EOF" : Character.toString((char) found));
        if (expected >= 0) {
            s += " instead of " + Character.toString((char) expected);
        }
        return s + "\n" + Integer.toString(lineno) + ":" + line;
    }

}
