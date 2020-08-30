/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.interpreter.primitives.register.scaled;

import org.extex.base.parser.ScaledNumberParser;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.scaled.ScaledNumber;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.primitives.register.scaled.util.ScaledCode;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \scaled}.
 * 
 * <p>The Primitive {@code \scaled}</p>
 * <p>
 * The primitive {@code \scaled} can be used to define a control sequence as
 * alias for a scaled register. The control sequence can be used wherever a
 * scaled is expected afterwards.
 * </p>
 * <p>
 * The primitive {@code \scaled} is an assignment. Thus the settings of
 * {@code \afterassignment} and {@code \globaldefs} are applied.
 * </p>
 * <p>
 * The prefix {@code \global} can be used to make the assignment to the new
 * control sequence global instead of the group-local assignment which is the
 * default.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;scaled&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \scaled} {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} ...
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang;  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \scaled\abc=45  </pre>
 *  <pre class="TeXSample">
 *    \scaled\abc 33  </pre>
 *  <pre class="TeXSample">
 *    \scaled\abc=-1.2  </pre>
 * 
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Scaled extends AbstractAssignment {

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
    public Scaled(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        ScaledNumber scaled =
                ScaledNumberParser
                    .scanScaledNumber(context, source, typesetter);
        context.setCode(cs, new ScaledCode(cs, scaled), prefix
            .clearGlobal());
    }

}
