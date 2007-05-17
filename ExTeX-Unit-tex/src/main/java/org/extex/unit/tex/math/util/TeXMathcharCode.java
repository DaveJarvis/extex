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

package org.extex.unit.tex.math.util;

import org.extex.base.parser.CountConvertible;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingMathException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Showable;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.math.MathCode;
import org.extex.unit.tex.math.AbstractTeXMathCode;

/**
 * This class is used to dynamically define mathematical characters having the
 * TeX encoding into a count value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class TeXMathcharCode extends AbstractTeXMathCode
        implements
            MathCodeConvertible,
            CountConvertible,
            Showable,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The field <tt>mathchar</tt> contains the actual character in the form
     * of a MathCode which can immediately be passed to the typesetter.
     */
    private MathCode mathchar;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     * @param charCode the code of the math char
     */
    public TeXMathcharCode(String name, MathCode charCode) {

        super(name);
        mathchar = charCode;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.base.parser.CountConvertible#convertCount(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return mathCodeToLong(mathchar);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.tex.math.util.MathCodeConvertible#convertMathCode(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public MathCode convertMathCode(Context context, TokenSource source,
            Typesetter typesetter) throws TypesetterException {

        return mathchar;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws TypesetterException,
                MissingMathException {

        NoadConsumer nc = getListMaker(context, typesetter);
        nc.add(mathchar, context.getTypesettingContext());
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * 
     * @return the description of the primitive as list of Tokens
     * 
     * @see org.extex.interpreter.type.Showable#show(
     *      org.extex.interpreter.context.Context)
     */
    public Tokens show(Context context) throws HelpingException {

        try {
            return context.getTokenFactory().toTokens(
                context.esc("mathchar")
                        + "\""
                        + Long.toHexString(mathCodeToLong(mathchar))
                            .toUpperCase());
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     * 
     * @return the description of the primitive as list of Tokens
     * @throws CatcodeException in case of an error in token creation
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException, TypesetterException {

        return context.getTokenFactory().toTokens(mathCodeToLong(mathchar));
    }

}
