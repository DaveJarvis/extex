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

package org.extex.core.dimen;

import org.extex.core.dimen.parser.LengthParser;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;

/**
 * This class implements a parser for a dimen value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
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
     * @throws InterpreterException in case of an error
     */
    public static Dimen parse(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return LengthParser.parse(context, source, typesetter);
    }

    /**
     * Creates a new object.
     */
    private DimenParser() {
    }

}