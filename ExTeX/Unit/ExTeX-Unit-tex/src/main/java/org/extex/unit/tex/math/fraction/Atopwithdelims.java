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
import org.extex.typesetter.type.math.MathDelimiter;
import org.extex.unit.tex.math.delimiter.AbstractTeXDelimiter;

/**
 * This class provides an implementation for the primitive
 * {@code \atopwithdelims}.
 * 
 * <p>The Math Primitive {@code \atopwithdelims}</p>
 * <p>
 * The math primitive {@code \atopwithdelims} arranges that the material in
 * the math group before it is typeset above the material after the primitive.
 * The two parts are not separated by a line. The construction is enclosed in
 * the delimiters given
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

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;atopwithdelims&rang;
 *       &rarr; &lang;math material&rang; {@code \atopwithdelims} ... &lang;math material&rang; </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    {a\atopwithdelims\delimiter"123456\delimiter"123456 b} </pre>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Atopwithdelims extends AbstractTeXDelimiter {

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
    public Atopwithdelims(CodeToken token) {

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
        MathDelimiter del1 =
                parseDelimiter(context, source, typesetter, getToken());
        MathDelimiter del2 =
                parseDelimiter(context, source, typesetter, getToken());

        nc.switchToFraction(del1, del2, Dimen.ZERO_PT, context
            .getTypesettingContext());
    }

}
