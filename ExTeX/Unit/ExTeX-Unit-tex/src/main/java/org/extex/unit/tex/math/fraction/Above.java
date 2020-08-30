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

package org.extex.unit.tex.math.fraction;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.unit.tex.math.AbstractMathCode;

/**
 * This class provides an implementation for the primitive {@code \above}.
 * 
 * <p>The Math Primitive {@code \above}</p>
 * <p>
 * The math primitive {@code \above} arranges that the material in the math
 * group before it is typeset above the material after the primitive. The two
 * parts are separated by a line of the width given. If the width is less than
 * 0pt then no rule is drawn but the given height is left blank.
 * </p>
 * <p>
 * If several primitives of type {@code \above}, {@code \abovewithdelims},
 * {@code \atop}, {@code \atopwithdelims}, {@code \over}, or
 * {@code \overwithdelims} are encountered in the same math group then the
 * result is ambiguous and an error is raised.
 * </p>
 * <p>
 * If the primitive is used outside of math mode then an error is raised.
 * </p>
 * 
 * <p>Syntax</p>
 *
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;above&rang;
 *       &rarr; &lang;math material&rang; {@code \above} &lang;dimen&rang; &lang;math material&rang;
 * </pre>
 * 
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    {a \above.1pt b}  </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Above extends AbstractMathCode {

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
    public Above(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        NoadConsumer nc = getListMaker(context, typesetter);
        Dimen d = source.parseDimen(context, source, typesetter);

        nc.switchToFraction(null, null, d, context.getTypesettingContext());
    }

}
