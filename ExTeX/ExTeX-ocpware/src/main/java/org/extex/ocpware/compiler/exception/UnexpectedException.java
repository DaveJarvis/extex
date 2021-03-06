/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
*/
public class UnexpectedException extends SyntaxException {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field {@code line} contains the current line up to the last
     * character read.
     */
    private final CharSequence line;

    /**
     * The field {@code lineno} contains the number of the current line.
     */
    private final int lineno;

    /**
     * The field {@code unexpected} contains the unexpected item.
     */
    private final String unexpected;

    /**
     * Creates a new object.
     * 
     * @param unexpected the unexpected id
     * @param line the current line up to the last character read
     * @param lineno the number of the current line
     */
    public UnexpectedException(String unexpected, CharSequence line, int lineno) {

        this.unexpected = unexpected;
        this.line = line;
        this.lineno = lineno;
    }

@Override
    public String getLocalizedMessage() {

        try {
            ResourceBundle bundle =
                    ResourceBundle.getBundle(getClass().getName());
            return MessageFormat.format(bundle.getString("Message"),
                unexpected, Integer.toString(lineno), line);
        } catch (MissingResourceException e) {
            return super.getMessage();
        }
    }
}
