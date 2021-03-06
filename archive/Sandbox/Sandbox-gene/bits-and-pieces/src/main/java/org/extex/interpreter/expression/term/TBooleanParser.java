/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.expression.term;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.ETypeParser;
import org.extex.interpreter.expression.Evaluator;
import org.extex.interpreter.expression.UnaryFunction;
import org.extex.interpreter.type.Code;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class implements the supporting functions for the date type
 * {@linkplain org.extex.interpreter.expression.term.TBoolean TBoolean} for the
 * expression evaluator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class TBooleanParser implements ETypeParser {


    public TBooleanParser() {

        super();
    }

    /**
     * Try to convert some code into a proper data type.
     * 
     * @param code the code to convert
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the converted value or {@code null} if the conversion could
     *         not be performed
     * 
     *         {@inheritDoc}
     * 
     * @see org.extex.interpreter.expression.ETypeParser#convert(org.extex.interpreter.type.Code,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public EType convert(Code code, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return null;
    }

    /**
     * Try to parse a proper value from the token source.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the element inquired or {@code null} if none could be parsed
     * 
     *         {@inheritDoc}
     * 
     * @see org.extex.interpreter.expression.ETypeParser#parse(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public EType parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (source.getKeyword(context, "true")) {
            return new TBoolean(true);
        } else if (source.getKeyword(context, "false")) {
            return new TBoolean(false);
        }

        return null;
    }

    /**
     * Inform the parser that it has been registered in an evaluator. In this
     * case the parser can register some functions in the evaluator.
     * 
     * @param evaluator the evaluator where the parser has been registered
     * 
     * @see org.extex.interpreter.expression.ETypeParser#registered(org.extex.interpreter.expression.Evaluator)
     */
    @Override
    public void registered(Evaluator evaluator) {

        evaluator.register("boolean", new UnaryFunction() {

        @Override
            public EType apply(EType accumulator) throws HelpingException {

                return new TBoolean().set(accumulator);
            }
        });

    }

}
