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

/**
 * This exception class signals the occurrence of a syntax error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UnexpectedException extends SyntaxException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

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
     * The field <tt>unexpected</tt> contains the ...
     */
    private String unexpected;

    /**
     * Creates a new object.
     * 
     * @param unexpected the unexpected id
     * @param line the current line up to the last character read
     * @param lineno the number of the current line
     */
    public UnexpectedException(String unexpected, CharSequence line, int lineno) {

        super();
        this.unexpected = unexpected;
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
            return MessageFormat.format(bundle.getString("Message"), //
                unexpected, Integer.toString(lineno), line);
        } catch (MissingResourceException e) {
            return super.getMessage();
        }
    }
}
