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

package org.extex.core.glue;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;

/**
 * This class provides ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4399 $
 */
public final class GlueParser {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object by parsing a token source.
     *
     * <doc type="syntax" name="glue">
     *
     * <pre class="syntax">
     *   &lang;glue&rang;
     *     &rarr; &lang;component&rang;
     *      |  &lang;component&rang; plus &lang;component&rang;
     *      |  &lang;component&rang; minus &lang;component&rang;
     *      |  &lang;component&rang; plus &lang;component&rang; minus &lang;component&rang;
     *
     *   &lang;component&rang;
     *     &rarr; &lang;dimen;&rang;
     *       |  &lang;float&rang; &lang;unit&rang;
     *
     *   &lang;unit&rang;
     *     &rarr; fi | fil | fill | filll    </pre>
     * <p>
     *  TODO gene: documentation incomplete
     * </p>
     *
     * </doc>
     *
     *
     * @param source the source to read new tokens from
     * @param context the processing context
     * @param typesetter the typesetter
     *
     * @return a new instance of a Glue
     *
     * @throws InterpreterException in case of an error
     */
    public static Glue parse(TokenSource source, Context context,
            Typesetter typesetter) throws InterpreterException {

        GlueComponent length;
        GlueComponent shrink;
        GlueComponent stretch;
        Token t = source.getToken(context);
        if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code instanceof GlueConvertible) {
                Glue g =
                        ((GlueConvertible) code).convertGlue(context, source,
                            null);
                length = g.getLength().copy();
                shrink = g.getShrink().copy();
                stretch = g.getStretch().copy();
                return new Glue(length, stretch, shrink);
            } else if (code == null) {
                throw new UndefinedControlSequenceException(AbstractCode
                    .printable(context, t));
            }
        }
        source.push(t);
        length = GlueComponentParser.parse(context, source, typesetter, false);
        if (source.getKeyword(context, "plus")) {
            stretch =
                    GlueComponentParser
                        .parse(context, source, typesetter, true);
        } else {
            stretch = new GlueComponent(0);
        }
        if (source.getKeyword(context, "minus")) {
            shrink =
                    GlueComponentParser
                        .parse(context, source, typesetter, true);
        } else {
            shrink = new GlueComponent(0);
        }
        return new Glue(length, stretch, shrink);
    }

    /**
     * Creates a new object.
     */
    private GlueParser() {
        // never used
    }

}
