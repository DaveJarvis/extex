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

package org.extex.interpreter.observer.start;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Interpreter;

/**
 * This interface describes the ability to receive a notification just after
 * the engine has been started.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface StartObserver {

    /**
     * This method is meant to be invoked just after the start has occurred.
     *
     * @param interpreter the interpreter to be started
     * @throws HelpingException in case of an error
     */
    void update(Interpreter interpreter) throws HelpingException;

}
