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

package org.extex.unit.tex.math.symbol;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.noad.Noad;
import org.extex.typesetter.type.noad.RelationNoad;
import org.extex.typesetter.type.noad.util.MathSpacing;
import org.extex.unit.tex.math.AbstractMathCode;

/**
 * This class provides an implementation for the primitive <code>\mathrel</code>.
 * 
 * <doc name="mathrel">
 * <h3>The Math Primitive <tt>\mathrel</tt></h3>
 * <p>
 * The primitive <tt>\mathrel</tt> takes an argument and treats it as a
 * relation symbol. It works in math mode only. The argument can either be a
 * single letter of a math expression enclosed in braces.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mathrel&rang;
 *       &rarr; <tt>\mathrel</tt> &lang;formula&rang;
 *
 *    &lang;formula&rang;
 *       &rarr;  &lang;letter&rang;
 *         |  <tt>{</tt> &lang;math material&rang; <tt>}</tt>   </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \mathrel x </pre>
 * 
 * <pre class="TeXSample">
 *    \mathrel\mathchar"1234  </pre>
 * 
 * <pre class="TeXSample">
 *    \mathrel {abc} </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Mathrel extends AbstractMathCode {

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
    public Mathrel(CodeToken token) {

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
        Noad noad = nc.scanNoad(prefix, context, source, typesetter, //
            getToken(), GroupType.MATH_GROUP);

        if (noad != null) {
            noad.setSpacingClass(MathSpacing.BIN);
            nc.add(noad);
        } else {
            nc.add(new RelationNoad(noad, context.getTypesettingContext()));
        }
    }

}
