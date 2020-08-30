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

package org.extex.unit.tex.math.symbol;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.math.MathCode;
import org.extex.unit.tex.math.AbstractTeXMathCode;
import org.extex.unit.tex.math.util.MathCodeConvertible;

/**
 * This class provides an implementation for the primitive
 * {@code \mathchar}.
 * 
 * <p>The Math Primitive {@code \mathchar}</p>
 * <p>
 * The primitive {@code \mathchar} inserts a mathematical character
 * consisting of a math class and a character code into the current math list.
 * This is supposed to work in math mode only.
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mathchar&rang;
 *       &rarr; {@code \mathchar ...}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \mathchar"041  </pre>
 *  <pre class="TeXSample">
 *    \mathchar{ordinary 0 `A}  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Mathchar extends AbstractTeXMathCode
        implements
            MathCodeConvertible {

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
    public Mathchar(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public MathCode convertMathCode(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return parseMathCode(context, source, typesetter, getToken());
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        NoadConsumer nc = getListMaker(context, typesetter);
        MathCode mc = parseMathCode(context, source, typesetter, getToken());

        nc.add(mc, context.getTypesettingContext());
    }

}
