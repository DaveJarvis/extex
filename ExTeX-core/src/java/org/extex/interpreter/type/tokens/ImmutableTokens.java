/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.tokens;

import org.extex.scanner.type.token.Token;

/**
 * This class presents a
 * {@link org.extex.interpreter.type.tokens.Tokens Tokens} register where
 * all setters are disabled. This means that its value can not be altered once
 * it has been created.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ImmutableTokens extends Tokens {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * The new object has no items in it.
     */
    public ImmutableTokens() {

        super();
    }

    /**
     * Add another token to the end of the Tokens.
     * This operation is unsupported and leads to an exception.
     *
     * @param t The token to add
     *
     * @see org.extex.interpreter.type.tokens.Tokens#add(
     *      org.extex.scanner.type.token.Token)
     */
    public void add(final Token t) {

        throw new UnsupportedOperationException(
            "Unable to add to an immutable object");
    }

    /**
     * Add another token list to the end of the Tokens.
     * This operation is unsupported and leads to an exception.
     *
     * @param toks the tokens to add
     *
     * @see org.extex.interpreter.type.tokens.Tokens#add(
     *      org.extex.interpreter.type.tokens.Tokens)
     */
    public void add(final Tokens toks) {

        throw new UnsupportedOperationException(
            "Unable to add to an immutable object");
    }

}
