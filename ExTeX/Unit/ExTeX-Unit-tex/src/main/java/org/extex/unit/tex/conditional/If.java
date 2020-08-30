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

package org.extex.unit.tex.conditional;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive {@code \if}.
 * 
 * <p>The Primitive {@code \if}</p>
 * <p>
 * The primitive expands the tokens following it until two non-expandable tokens
 * are found. The conditional is true iff the character codes of the two tokens
 * agree.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;if&rang;
 *     &rarr; {@code \if} {@linkplain
 *       org.extex.interpreter.TokenSource#getToken(Context)
 *       &lang;token<sub>1</sub>&rang;} {@linkplain
 *       org.extex.interpreter.TokenSource#getToken(Context)
 *       &lang;token<sub>2</sub>&rang;} &lang;true text&rang; {@code \fi}
 *     | {@code \if} {@linkplain
 *       org.extex.interpreter.TokenSource#getToken(Context)
 *       &lang;token<sub>1</sub>&rang;} {@linkplain
 *       org.extex.interpreter.TokenSource#getToken(Context)
 *       &lang;token<sub>2</sub>&rang;} &lang;true text&rang; {@code \else} &lang;false text&rang; {@code \fi} </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \if\a\x ok \fi  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class If extends AbstractIf {

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
    public If(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t1 = source.scanToken(context);
        Token t2 = source.scanToken(context);

        if (t1 == null || t2 == null) {
            throw new EofException(toText());
        }

        UnicodeChar c1 = t1.getChar();
        UnicodeChar c2 = t2.getChar();
        return (c1 == null ? c2 == null : c1.equals(c2));
    }

}
