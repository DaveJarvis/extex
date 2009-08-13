/*
 * Copyright (C) 2004-2009 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.primitives.register.real;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * Abstract class for math primitives. E.g. sin, cos
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractMath extends AbstractCode
        implements
            Theable,
            RealConvertible,
            CountConvertible {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * Creates a new object.
     *
     * @param token the initial token for the primitive
     */
    public AbstractMath(CodeToken token) {

        super(token);
    }

    /**
     * Calculate
     *
     * @param context the context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @return the real value
     *
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected abstract Real calculate(Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException;

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        return calculate(context, source, typesetter).getLong();
    }

    /**
     * @see org.extex.interpreter.primitives.register.real.RealConvertible#convertReal(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Real convertReal(Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        return calculate(context, source, typesetter);
    }

    /**
     * execute
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Real real = calculate(context, source, typesetter);
        try {
            source.push(context.getTokenFactory().toTokens(real.toString()));
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
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws ConfigurationException,
                CatcodeException,
                HelpingException,
                TypesetterException {

        Real real = calculate(context, source, typesetter);
        return context.getTokenFactory().toTokens(real.toString());
    }

}
