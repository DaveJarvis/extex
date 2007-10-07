/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.typesetter.paragraph;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;

/**
 * This class provides an implementation for the primitive <code>\relax</code>.
 * 
 * <doc name="parshapelength">
 * <h3>The Primitive <tt>\parshapelength</tt></h3>
 * <p>
 * The primitive <tt>\parshapelength</tt> gives access to the settings for the
 * current paragraph shape. The primitive takes a number as parameter. If this
 * number is positive then the length of the line denoted by the parameter is
 * returned. The line numbering starts with 1. If the argument is less than 1
 * then 0 is returned.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;parshapelength&rang;
 *        &rarr; <tt>\parshapelength</tt> {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
 *        &lang;8-bit&nbsp;number&rang;} </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \dimen2=\parshapelength 3  </pre>
 *  <pre class="TeXSample">
 *    \dimen2=\parshapelength -3  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class Parshapelength extends AbstractCode
        implements
            CountConvertible,
            DimenConvertible,
            Theable {

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
    public Parshapelength(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return convertDimen(context, source, typesetter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.DimenConvertible#convertDimen(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long l = source.parseInteger(context, source, typesetter);
        int n = (l < Integer.MAX_VALUE ? (int) l : Integer.MAX_VALUE);
        ParagraphShape parshape = context.getParshape();
        return (parshape == null || n < 0 ? 0 : parshape.getLength(n)
            .getValue());
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     * 
     * @return the description of the primitive as list of Tokens
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        long l = source.parseInteger(context, source, typesetter);
        int n = (l < Integer.MAX_VALUE ? (int) l : Integer.MAX_VALUE);
        ParagraphShape parshape = context.getParshape();
        FixedDimen d = (parshape == null || n < 0) //
                ? Dimen.ZERO_PT
                : parshape.getLength(n);

        try {
            return context.getTokenFactory().toTokens(d.toString());
        } catch (GeneralException e) {
            throw new NoHelpException(e);
        }
    }

}
