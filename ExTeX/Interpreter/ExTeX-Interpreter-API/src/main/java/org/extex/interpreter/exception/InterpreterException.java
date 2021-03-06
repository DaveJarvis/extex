/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.GeneralException;
import org.extex.framework.i18n.Localizer;

/**
 * This is the base class for all exceptions of the interpreter.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class InterpreterException extends GeneralException {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field {@code localizer} contains the localizer.
     */
    private Localizer localizer = null;


    public InterpreterException() {

    }

    /**
     * Creates a new object.
     *
     * @param localizer the localizer
     */
    public InterpreterException(Localizer localizer) {

        this.localizer = localizer;
    }

    /**
     * Creates a new object.
     *
     * @param message the message field
     */
    public InterpreterException(String message) {

        super(message);
    }

    /**
     * Creates a new object.
     *
     * @param message the message field
     * @param cause the root of all evil
     */
    public InterpreterException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * Creates a new object.
     *
     * @param cause the root of all evil
     */
    public InterpreterException(Throwable cause) {

        super(cause);
    }

    /**
     * Getter for localizer.
     * If no localizer is stored within the current instance than the localizer
     * is created with the class name as key.
     *
     * @return the localizer
     */
    @Override
    public Localizer getLocalizer() {

        return (this.localizer != null ? this.localizer : super.getLocalizer());
    }

}
