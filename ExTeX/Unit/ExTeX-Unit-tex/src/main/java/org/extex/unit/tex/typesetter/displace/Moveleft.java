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

package org.extex.unit.tex.typesetter.displace;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.typesetter.box.AbstractBoxPrimitive;

/**
 * This class provides an implementation for the primitive
 * {@code \moveleft}.
 * 
 * <p>The Primitive {@code \moveleft}</p>
 * <p>
 * The primitive {@code \moveleft} takes a box and a length and moves it
 * leftwards by the amount specified by the length. If the length is negative
 * then the move is done rightwards.
 * </p>
 * <p>
 * The primitive {@code \moveleft} is the counterpart to
 * {@link org.extex.unit.tex.typesetter.displace.Moveright {@code \moveright}}.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;moveleft&rang;
 *      &rarr; {@code \moveleft} &lang;dimen&rang; &lang;box&rang;
 * </pre>
 * 
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \moveleft 2em \hbox{abc}  </pre>
 *  <pre class="TeXSample">
 *    \moveleft -1pt \hbox to 120pt {abc}  </pre>
 *  <pre class="TeXSample">
 *    \moveleft 2mm \hbox spread 12pt {abc}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Moveleft extends AbstractBoxPrimitive {

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
    public Moveleft(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public Box getBox(Context context, TokenSource source,
            Typesetter typesetter, Token insert)
            throws HelpingException,
                TypesetterException {

        Dimen move = source.parseDimen(context, source, typesetter);
        Box box = source.getBox(null, context, typesetter, insert);
        if (box != null && !box.isVoid()) {
            move.negate();
            move.add(box.getMove());
            box.setMove(move);
        }
        return box;
    }

}
