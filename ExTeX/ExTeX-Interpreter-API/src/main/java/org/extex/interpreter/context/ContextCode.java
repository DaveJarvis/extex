/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.context;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;

/**
 * This interface describes the container for code of an interpreter
 * context.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface ContextCode {

    /**
     * Convenience method to get the code assigned to a Token.
     * If the Token is a ControlSequenceToken then the macro is returned.
     * If the Token is a ActiveCharacterToken then the active value is returned.
     *
     * @param t the Token to differentiate on
     *
     * @return the code for the token
     *
     * @throws HelpingException in case of an error
     *
     * @see #setCode(CodeToken, Code, boolean)
     */
    Code getCode(CodeToken t) throws HelpingException;

    /**
     * Setter for the code assigned to a Token.
     * The Token has to be either a
     * {@link org.extex.scanner.type.token.ActiveCharacterToken ActiveCharacterToken}
     * or a
     * {@link org.extex.scanner.type.token.ControlSequenceToken ControlSequenceToken}.
     *
     * @param t the Token to set the code for
     * @param code the code for the token
     * @param global the indicator for the scope; <code>true</code> means all
     *            groups; otherwise the current group is affected only
     *
     * @throws HelpingException in case of an error
     *
     * @see #getCode(CodeToken)
     */
    void setCode(CodeToken t, Code code, boolean global)
            throws HelpingException;

}
