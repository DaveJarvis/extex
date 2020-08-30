/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This is the base class for assignments. The assignments are implemented like
 * any Code with the exception that the method {@code assign} is used instead
 * of the method {@code execute}.
 *
 * <p>
 * This abstract class takes care of the treatment of the
 * {@code \afterassignment} token and the {@code \globaldefs} declaration.
 * </p>
 *
 *
 * <p>The Count Parameter {@code \globaldefs}</p>
 * <p>
 * The count register {@code \globaldefs} contains the indicator that an
 * assignment should be performed globally. If its value is greater than zero
 * then all assignments are global. Otherwise the grouping is honored. In this
 * sense setting {@code \globaldefs} to a positive value implicitly prefixes
 * all assignments with {@code \global}.
 * </p>
 *
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;globaldefs&rang;
 *       &rarr; {@code \globaldefs} &lang;equals&rang; &lang;number&rang;
 * </pre>
 *
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \globaldefs=1
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractAssignment extends AbstractCode {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * Creates a new object.
     *
     * @param token the initial token for the primitive
     */
    public AbstractAssignment(CodeToken token) {

        super(token);
    }

    /**
     * The method {@code assign} is the core of the functionality of
     * {@link #execute(Flags, Context, TokenSource, Typesetter) execute()}. This
     * method is preferable to {@code execute()} since the {@code execute()}
     * method provided in this class takes care of {@code \afterassignment} and
     * {@code \globaldefs} as well.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws ConfigurationException in case of an configuration error
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public abstract void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException;

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.Code#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public final void execute(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        long globaldef = context.getCount("globaldefs").getValue();
        if (globaldef != 0) {
            prefix.setGlobal((globaldef > 0));
        }

        assign(prefix, context, source, typesetter);

        Token afterassignment = context.getAfterassignment();
        if (afterassignment != null) {
            context.setAfterassignment(null);
            source.push(afterassignment);
        }
    }

}
