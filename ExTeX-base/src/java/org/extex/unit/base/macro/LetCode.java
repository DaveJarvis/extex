/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.base.macro;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;

/**
 * This code encapsulated a token which is used to trigger an expansion in the
 * interpreter at execution time. This can be used to access the code pieces for
 * special tokens which are not stores in the context.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LetCode extends AbstractCode {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 28012007L;

    /**
     * The field <tt>token</tt> contains the encapsulated token.
     */
    private Token token;

    /**
     * Creates a new object.
     *
     * @param codeName the name for debugging
     */
    public LetCode(final Token token) {

        super(token.toString());
        this.token = token;
    }

    /**
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        source.execute(token, context, typesetter);
    }

    /**
     * Getter for token.
     *
     * @return the token
     */
    public Token getToken() {

        return token;
    }

}
