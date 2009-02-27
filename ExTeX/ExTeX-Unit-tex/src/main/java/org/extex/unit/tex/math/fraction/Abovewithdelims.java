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
 * <code>\abovewithdelims</code>.
 * 
 * <doc name="abovewithdelims">
 * <h3>The Math Primitive <tt>\abovewithdelims</tt></h3>
 * <p>
 * The math primitive <tt>\abovewithdelims</tt> arranges that the material in
 * the math group before it is typeset above the material after the primitive.
 * The two parts are separated by a line of the width given. If the width is
 * less than 0pt then no rule is drawn but the given height is left blank. The
 * construction is enclosed in the delimiters given
 * </p>
 * <p>
 * If several primitives of type <tt>\above</tt>, <tt>\abovewithdelims</tt>,
 * <tt>\atop</tt>, <tt>\atopwithdelims</tt>, <tt>\over</tt>, or
 * <tt>\overwithdelims</tt> are encountered in the same math group then the
 * result is ambiguous and an error is raised.
 * </p>
 * <p>
 * If the primitive is used outside of math mode then an error is raised.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;abovewithdelims&rang;
 *       &rarr; &lang;math material&rang; <tt>\abovewithdelims</tt> ... {@linkplain
 *        org.extex.core.dimen#Dimen(org.extex.interpreter.context.Context,org.extex.interpreter.TokenSource)
 *        &lang;dimen&rang;} &lang;math material&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    {a\abovewithdelims\delimiter"123456\delimiter"123456 b} </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Abovewithdelims extends AbstractTeXDelimiter {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Abovewithdelims(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(
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
        Dimen d = source.parseDimen(context, source, typesetter);
        nc.switchToFraction(del1, del2, d, context.getTypesettingContext());
    }

}
