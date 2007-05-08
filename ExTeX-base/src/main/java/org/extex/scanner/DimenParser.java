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

package org.extex.scanner;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.parser.LengthParser;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class implements a parser for a dimen value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5417 $
 */
public final class DimenParser {

    /**
     * Creates a new object from a token stream.
     *
     * <doc type="syntax" name="dimen">
     * This method parses the following syntactic entity:
     *
     *  <pre class="syntax">
     *    &lang;dimen&rang;
     *      &rarr; &lang;float&rang; &lang;dimen unit&rang;
     *       |  &lang;float&rang; <tt>true</tt> &lang;dimen unit&rang;
     *       |  &lang;dimen variable&rang;
     *
     *    &lang;float&rang;
     *      &rarr; [+-]? [0-9]+
     *      |  [+-]? [0-9]+[.][0-9]*
     *      |  [+-]? [.][0-9]+
     *
     *    &lang;dimen unit&rang;
     *      &rarr; pt | in | sp
     *      |  mm | cm | dm | km
     *      |  dd | cc | bp  </pre>
     * </doc>
     *
     * @param context the interpreter context
     * @param source the source for next tokens
     * @param typesetter the typesetter
     *
     * @return a new instance with the value acquired
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static Dimen parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException,TypesetterException {

        return LengthParser.parse(context, source, typesetter);
    }

    /**
     * Creates a new object.
     */
    private DimenParser() {
        // never used
    }

}