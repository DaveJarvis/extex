/*
 * Copyright (C) 2006 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.tokens.Tokens;

import de.dante.extex.typesetter.Typesetter;

/**
 * This is an interface for those primitives which are protected during
 * expansion of arguments of primitives like <tt>\edef</tt>,
 *  <tt>\xdef</tt>, <tt>\message</tt>, and others..
 *
 * @see TokenSource#scanUnprotectedTokens(Context, boolean, boolean, String)
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface CodeExpander {

    /**
     * Expand the first token and place the result in a token list. During
     * the expansion additional tokens might be used.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param tokens the target token list
     *
     * @throws InterpreterException in case of an error
     */
    void expandCode(Context context, TokenSource source, Typesetter typesetter,
            Tokens tokens) throws InterpreterException;

}
