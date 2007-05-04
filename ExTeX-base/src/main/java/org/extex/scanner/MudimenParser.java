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

package org.extex.scanner;

import java.io.Serializable;

import org.extex.core.muskip.Mudimen;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides a parser for math units (mu).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public class MudimenParser implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060605L;

    /**
     * Scan a math unit.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the number of scaled points for the mu
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected static long scanMu(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t = source.getToken(context);
        if (t == null) {
            throw new EofException("mu");
        } else if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code instanceof MudimenConvertible) {
                return ((MudimenConvertible) code).convertMudimen(context,
                    source, null);
            }
        }
        long value =
                ScaledNumberParser.scanFloat(context, source, typesetter, t);
        if (!source.getKeyword(context, "mu")) {
            throw new HelpingException(
                //
                LocalizerFactory.getLocalizer(MudimenParser.class),
                "TTP.IllegalMu");
        }
        return value;
    }

    /**
     * Creates a new object and fills it from a token stream.
     * 
     * <doc type="syntax" name="mudimen"> This method parses the following
     * syntactic entity:
     * 
     * <pre class="syntax">
     *    &lang;mudimen&rang;
     *      &rarr; &lang;float&rang; <tt>mu</tt>
     *       |  &lang;mudimen variable&rang;
     * </pre>
     * 
     * The value of &lang;mudimen&rang; is either a floating point number
     * followed by the unit <tt>mu</tt> or a variable value resulting in a
     * mudimen value. </doc>
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the new object
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static Mudimen parseMudimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return new Mudimen(scanMu(context, source, typesetter));
    }

}
