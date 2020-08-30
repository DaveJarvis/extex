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

package org.extex.unit.tex.macro;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \futurelet}.
 * 
 * <p>The Primitive {@code \futurelet}</p>
 * <p>
 * The primitive {@code \futurelet} lets a token to a control sequence while
 * keeping the token between the two out of sight. This means that this token is
 * stored away and reinserted after the let is complete.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;futurelet&rang;
 *      &rarr; {@code \futurelet} {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;} {@linkplain
 *       org.extex.interpreter.TokenSource#getToken(Context)
 *       &lang;token&rang;} {@linkplain
 *       org.extex.interpreter.TokenSource#getToken(Context)
 *       &lang;token&rang;}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \futurelet \aa\bb x  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Futurelet extends Let {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Futurelet(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        Token t1 = source.getToken(context);
        Token t2 = source.getToken(context);
        if (t1 == null || t2 == null) {
            throw new EofException(toText());
        }
        let(prefix, context, cs, t2);
        source.push(source.scanToken(context));
        source.push(t2);
        source.push(t1);
    }

}
