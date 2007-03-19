/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.expression.exception;

import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.exception.helping.HelpingException;

/**
 * This exception signals an unsupported operation in the evaluator.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4733 $
 */
public class UnsupportedException extends HelpingException {

    /**
     * The field <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060628L;

    /**
     * Creates a new object.
     */
    public UnsupportedException() {

        super();
    }

    /**
     * Creates a new object.
     *
     * @param op the operation
     * @param arg the argument
     */
    public UnsupportedException(final String op, final String arg) {

        super(LocalizerFactory.getLocalizer(UnsupportedException.class),
                "UnsupportedException", op, arg);
    }

}
