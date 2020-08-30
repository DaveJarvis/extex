/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.skip;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \skipdef}
 * .
 * 
 * <p>The Primitive {@code \skipdef}</p>
 * <p>
 * The primitive {@code \skipdef} can be used to define a control sequence as
 * alias for a skip register. The control sequence can be used wherever a skip
 * register is expected afterwards.
 * </p>
 * <p>
 * The primitive {@code \skipdef} is an assignment. Thus the settings of
 * {@code \afterassignment} and {@code \globaldefs} are applied.
 * </p>
 * <p>
 * The prefix {@code \global} can be used to make the assignment to the new
 * control sequence global instead of the group-local assignment which is the
 * default.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;skipdef&rang;
 *      &rarr; &lang;modifier&rang; {@code \skipdef} {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context,TokenSource,Typesetter,CodeToken)
 *        &lang;register name&rang;}
 *
 *    &lang;modifier&rang;
 *      &rarr;
 *       |  {@code \global}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \skipdef\abc=45  </pre>
 * 
 * <pre class="TeXSample">
 *    \skipdef\abc 33  </pre>
 * 
 * <pre class="TeXSample">
 *    \skipdef\abc={xyz}  </pre>
 * 
 * <pre class="TeXSample">
 *    \skipdef\abc={xyz\the\count0}  </pre>
 * 
 * <p>Differences to TeX and Friends</p>

 * <p>
 * In TeX the register name could consist of an integer in the range
 * from 0 to 255. In  Omega this restriction has been relaxed to
 * allow integers from 0 to 32767. In εχTeX the restriction to integers has been relaxed. The register
 * name can either be a number &ndash; positive or not and of any value &ndash;
 * or alternatively any token sequence enclosed in braces.
 * </p>
 * <p>
 * Note that the extended register names and the maximal number acceptable as
 * register names are a feature of εχTeX which is configurable via the count register
 * {@code \max.register}. This means that the feature can be disabled in the
 * compatibility modes.
 * </p>
 *
 * 
 * To protect the built-in registers one might consider to use the key
 * "#<i>name</i>" or "skip#<i>name</i>".
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Skipdef extends AbstractSkip {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Skipdef(CodeToken token) {

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
        source.getOptionalEquals(context);
        String key = getKey(context, source, typesetter);
        context.setCode(cs, new SkipParameter(cs, key), prefix.clearGlobal());
    }

}
