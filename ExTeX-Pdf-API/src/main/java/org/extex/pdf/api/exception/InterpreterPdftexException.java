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

package org.extex.pdf.api.exception;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception is raised when a <logo>pdfTeX</logo> primitive is used
 * without being in PDF mode.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class InterpreterPdftexException extends HelpingException {

    /**
     * The field <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     */
    public InterpreterPdftexException() {

        this(LocalizerFactory.getLocalizer(InterpreterPdftexException.class),
            "Text", "?");
    }

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the primitive in action
     */
    public InterpreterPdftexException(String primitive) {

        this(LocalizerFactory.getLocalizer(InterpreterPdftexException.class),
            "Text", primitive); // TODO
    }

    /**
     * Creates a new object with three variable arguments.
     *
     * @param messageTag the message
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param theLocalizer the localizer to use
     */
    protected InterpreterPdftexException(Localizer theLocalizer,
            String messageTag, String a1, String a2, String a3) {

        super(theLocalizer, messageTag, a1, a2, a3);
    }

    /**
     * Creates a new object with two variable arguments.
     *
     * @param messageTag the message
     * @param a1 the first argument
     * @param a2 the second argument
     * @param theLocalizer the localizer to use
     */
    protected InterpreterPdftexException(Localizer theLocalizer,
            String messageTag, String a1, String a2) {

        super(theLocalizer, messageTag, a1, a2);
    }

    /**
     * Creates a new object with two variable arguments.
     *
     * @param messageTag the message
     * @param a1 the first argument
     * @param theLocalizer the localizer to use
     */
    protected InterpreterPdftexException(Localizer theLocalizer,
            String messageTag, String a1) {

        super(theLocalizer, messageTag, a1);
    }

    /**
     * Creates a new object with two variable arguments.
     *
     * @param messageTag the message
     * @param theLocalizer the localizer to use
     */
    protected InterpreterPdftexException(Localizer theLocalizer,
            String messageTag) {

        super(theLocalizer, messageTag);
    }

}
