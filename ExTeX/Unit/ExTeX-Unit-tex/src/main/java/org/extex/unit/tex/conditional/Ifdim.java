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

package org.extex.unit.tex.conditional;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive {@code \ifdim}.
 * 
 * <p>The Primitive {@code \ifdim}</p>
 * <p>
 * The primitive {@code \ifdim} provides a conditional which compares two
 * dimen values. The comparison for equality, greater, and less are possible.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ifdim&rang;
 *      &rarr; {@code \ifdim} &lang;dimen&rang; &lang;op&rang; &lang;dimen&rang; &lang;true text&rang; {@code \fi}
 *      | {@code \ifdim} &lang;dimen&rang; &lang;op&rang; &lang;dimen&rang; &lang;true text&rang; {@code \else} &lang;false text&rang; {@code \fi}
 *
 *    &lang;op&rang;
 *      &rarr; [&lt;]
 *      | [=]
 *      | [&gt;]  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Ifdim extends AbstractIf {

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
    public Ifdim(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long x = source.parseDimen(context, source, typesetter).getValue();
        Token rel = source.getToken(context);
        if (rel == null) {
            throw new EofException(toText());
        }
        if (rel.getCatcode() == Catcode.OTHER) {
            switch (rel.getChar().getCodePoint()) {
                case '<':
                    return (x < source.parseDimen(context, source, typesetter)
                        .getValue());
                case '=':
                    return (x == source.parseDimen(context, source, typesetter)
                        .getValue());
                case '>':
                    return (x > source.parseDimen(context, source, typesetter)
                        .getValue());
                default:
                    // Fall through to error handling
            }
        }

        throw new HelpingException(getLocalizer(), "TTP.IllegalIfnumOp",
            toText());
    }

}
