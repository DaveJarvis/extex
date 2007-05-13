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

package org.extex.base.parser;

import java.io.Serializable;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.GlueComponent;
import org.extex.core.muskip.Mudimen;
import org.extex.core.muskip.Muskip;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides a skip value with a variable length of order 0. The
 * actual length is a multiple of math units (mu).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public class MuskipParser extends Mudimen implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object and fills it from a token stream.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the value parsed
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static Muskip parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t;
        for (;;) {
            t = source.getNonSpace(context);
            if (t == null) {
                throw new EofException("mu");
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof MuskipConvertible) {
                    return new Muskip(((MuskipConvertible) code).convertMuskip(
                        context, source, typesetter));
                } else if (code instanceof MudimenConvertible) {
                    long md =
                            ((MudimenConvertible) code).convertMudimen(context,
                                source, typesetter);
                    return new Muskip(md);
                } else if (code instanceof ExpandableCode) {
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        long value =
                ScaledNumberParser.scanFloat(context, source, typesetter, t);
        if (!source.getKeyword(context, "mu")) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(MuskipParser.class), "TTP.IllegalMu");
        }
        Dimen len = new Dimen(value);
        GlueComponent stretch = null;
        GlueComponent shrink = null;

        if (source.getKeyword(context, "plus")) {
            stretch = scanMuOrFill(context, source, typesetter);
        }
        if (source.getKeyword(context, "minus")) {
            shrink = scanMuOrFill(context, source, typesetter);
        }
        source.skipSpace();

        return new Muskip(len, stretch, shrink);
    }

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
    private static GlueComponent scanMuOrFill(Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        Token t = source.getToken(context);
        if (t == null) {
            throw new EofException("mu");
        }
        long value =
                ScaledNumberParser.scanFloat(context, source, typesetter, t);
        if (source.getKeyword(context, "mu")) {
            return new GlueComponent(value);
        } else if (source.getKeyword(context, "fillll")) {
            return new GlueComponent(value, 5);
        } else if (source.getKeyword(context, "filll")) {
            return new GlueComponent(value, 4);
        } else if (source.getKeyword(context, "fill")) {
            return new GlueComponent(value, 3);
        } else if (source.getKeyword(context, "fil")) {
            return new GlueComponent(value, 2);
        } else if (source.getKeyword(context, "fi")) {
            return new GlueComponent(value, 1);
        }
        throw new HelpingException(LocalizerFactory
            .getLocalizer(MuskipParser.class), "TTP.IllegalMu");

    }

}
