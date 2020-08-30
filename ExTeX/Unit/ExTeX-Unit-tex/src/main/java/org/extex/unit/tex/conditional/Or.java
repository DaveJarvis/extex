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

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive {@code \or}.
 * 
 * <p>The Primitive {@code \or}</p>
 * <p>
 * The primitive {@code \or} indicated the other branch in the context of a
 * {@code \if} primitive. If encountered outside of an if context an error is
 * raised.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;or&rang;
 *      &rarr; {@code \or}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \iffalse abc \or def \fi  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Or extends AbstractCode {

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
    public Or(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Conditional cond = context.popConditional();

        if (cond == null) {
            throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                toText(context));
        } else if (AbstractIf.skipToElseOrFi(context, source, getToken())) {
            // \else has been found; search for the \fi
            if (AbstractIf.skipToElseOrFi(context, source, getToken())) {
                // just another \else is too much
                throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                    context.esc("else"));
            }
        }
    }

    /**
     * This method takes the first token and expands it. The result is placed on
     * the stack. This means that expandable code does one step of expansion and
     * puts the result on the stack. To expand a token it might be necessary to
     * consume further tokens.
     * 
     * @param prefix the prefix flags controlling the expansion
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws ConfigurationException in case of an configuration error
     * @throws HelpingException in case of an error
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        // TODO gene: is \or expandable?

        Conditional cond = context.popConditional();

        if (cond == null) {
            throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                toText(context));
        } else if (AbstractIf.skipToElseOrFi(context, source, getToken())) {
            // \else has been found; search for the \fi
            if (AbstractIf.skipToElseOrFi(context, source, getToken())) {
                // just another \else is too much
                throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                    context.esc("else"));
            }
        }
    }

}
