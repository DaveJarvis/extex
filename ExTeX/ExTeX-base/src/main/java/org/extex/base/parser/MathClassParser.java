/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.Parser;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.math.MathClass;

/**
 * This class provides the parser for math classes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public class MathClassParser implements Parser<MathClass>, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;


    public MathClassParser() {

    }

    /**
     * Parse a math class.
     * 
     * <pre>
     *  open 22 `[ 1 `(
     * </pre>
     * 
     * @param context the interpreter context
     * @param source the token source to read from
     * @param typesetter the typesetter
     * 
     * @return the MathDelimiter acquired
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * 
     * @see org.extex.interpreter.parser.Parser#parse(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public MathClass parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t = source.getToken(context);

        if (t instanceof OtherToken) {
            source.push(t);
            // try {
            long n = source.parseNumber(context, source, typesetter);
            try {
                return MathClass.getMathClass((int) n);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new HelpingException(LocalizerFactory
                    .getLocalizer(MathClassParser.class), "TTP.MissingDelim");
            }

            // } catch (MissingNumberException e) {
            // throw new HelpingException(LocalizerFactory
            // .getLocalizer(MathClassParser.class), "TTP.MissingDelim");
            // }
        } else if (t instanceof LetterToken) {
            source.push(t);
            switch (t.getChar().getCodePoint()) {
                case 'b':
                    if (source.getKeyword(context, "bin")) {
                        return MathClass.BINARY;
                    }
                    break;
                case 'c':
                    if (source.getKeyword(context, "close")) {
                        return MathClass.CLOSING;
                    }
                    break;
                case 'l':
                    if (source.getKeyword(context, "large")) {
                        return MathClass.LARGE;
                    }
                    break;
                case 'o':
                    if (source.getKeyword(context, "open")) {
                        return MathClass.OPENING;
                    } else if (source.getKeyword(context, "ord")) {
                        return MathClass.ORDINARY;
                    }
                    break;
                case 'p':
                    if (source.getKeyword(context, "punct")) {
                        return MathClass.PUNCTATION;
                    }
                    break;
                case 'r':
                    if (source.getKeyword(context, "rel")) {
                        return MathClass.RELATION;
                    }
                    break;
                case 'v':
                    if (source.getKeyword(context, "var")) {
                        return MathClass.VARIABLE;
                    }
                    break;
                default:
            }
            throw new HelpingException(LocalizerFactory
                .getLocalizer(MathClassParser.class), "TTP.MissingDelim");
        }

        throw new EofException();
    }

}
