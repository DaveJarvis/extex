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

package org.extex.unit.base.conditional;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \else}.
 * 
 * <p>The Primitive {@code \else}</p>
 * <p>
 * The primitive {@code \else} can appear in the context of a conditional. It
 * terminates the preceding branch and starts the next one.
 * </p>
 * <p>
 * The primitive {@code \else} can not be used alone. It always comes in
 * conjunction with a conditional. A isolated {@code \else} leads immediately
 * to an error.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;else&rang;
 *      &rarr; {@code \else}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \ifnum 1&lt;2\else no\fi  </pre>
 * 
 *
 * 
 * <strong>Note:</strong> This primitive is <em>not</em> expandable!
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Else extends AbstractCode implements PrefixCode, ExpandableCode {

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
    public Else(CodeToken token) {

        super(token);
    }

    /**
     * Executes the primitive.
     * <p>
     * This primitive can only be seen when a conditional has been opened before
     * for which the then branch is expanded. Thus the else branch has to be
     * skipped. Additionally the conditional stack has to be updated. If the
     * conditional stack is already empty then an exception is raised.
     * </p>
     * 
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Conditional cond = context.popConditional();

        if (cond == null
                || AbstractIf.skipToElseOrFi(context, source, getToken())) {
            throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                toText(context));
        }
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        Conditional cond = context.popConditional();

        if (cond == null
                || AbstractIf.skipToElseOrFi(context, source, getToken())) {
            throw new HelpingException(getLocalizer(), "TTP.ExtraOrElseFi",
                toText(context));
        }
    }

}
