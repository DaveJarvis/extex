/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.type.bool.exception;

import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.exception.helping.HelpingException;

/**
 * InterpreterException: no boolean value fond in the input.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class InterpreterNoBoolValueException extends HelpingException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Create a new object.
     */
    public InterpreterNoBoolValueException() {

        super(LocalizerFactory
            .getLocalizer(InterpreterNoBoolValueException.class), "Message", "");
    }

    /**
     * Create a new object.
     * 
     * @param message the message
     */
    public InterpreterNoBoolValueException(String message) {

        super(LocalizerFactory
            .getLocalizer(InterpreterNoBoolValueException.class), "Message",
            message);
    }
}
