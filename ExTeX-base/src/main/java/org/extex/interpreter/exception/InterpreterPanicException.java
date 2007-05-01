/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.exception;

import org.extex.framework.i18n.Localizer;

/**
 * This exception signals the error handler that a continuation is not
 * desirable.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class InterpreterPanicException extends InterpreterException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field <tt>arg</tt> contains the additional argument for printing.
     */
    private String arg;

    /**
     * The field <tt>tag</tt> contains the tag for the localizer.
     */
    private String tag;

    /**
     * Creates a new object.
     *
     * @param localizer the localizer
     * @param tag the name of the format for the localizer
     */
    public InterpreterPanicException(Localizer localizer, String tag) {

        super(localizer);
        this.tag = tag;
        this.arg = "?";
    }

    /**
     * Creates a new object.
     *
     * @param localizer the localizer
     * @param tag the name of the format for the localizer
     * @param arg the argument
     */
    public InterpreterPanicException(Localizer localizer,
            String tag, String arg) {

        super(localizer);
        this.tag = tag;
        this.arg = arg;
    }

    /**
     * Creates a localized description of this throwable.
     * Subclasses may override this method in order to produce a
     * locale-specific message.  For subclasses that do not override this
     * method, the default implementation returns the same result as
     * <code>getMessage()</code>.
     *
     * @return  The localized description of this throwable.
     *
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage() {

        return getLocalizer().format(tag, arg);
    }

}
