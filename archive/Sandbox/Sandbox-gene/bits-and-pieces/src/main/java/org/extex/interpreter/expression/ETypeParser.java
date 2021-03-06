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

package org.extex.interpreter.expression;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This interface describes a parser which can be registered in the evaluator.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface ETypeParser {

    /**
     * Try to convert some code into a proper data type.
     *
     * @param code the code to convert
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the converted value or {@code null} if the conversion
     *  could not be performed
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    EType convert(Code code, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException;

    /**
     * Try to parse a proper value from the token source.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the element inquired or {@code null} if none could be
     *  parsed
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    EType parse(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException;

    /**
     * Inform the parser that it has been registered in an evaluator.
     * In this case the parser can register some functions in the evaluator.
     *
     * @param evaluator the evaluator where the parser has been registered
     */
    void registered(Evaluator evaluator);

}
