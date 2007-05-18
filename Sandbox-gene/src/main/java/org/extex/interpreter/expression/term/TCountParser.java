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
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.ETypeParser;
import org.extex.interpreter.expression.Evaluator;
import org.extex.interpreter.expression.UnaryFunction;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class implements the supporting functions for the date type
 * {@linkplain org.extex.interpreter.expression.term.TCount TCount}
 * for the expression evaluator.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4787 $
 */
public final class TCountParser implements ETypeParser {

    /**
     * Creates a new object.
     */
    public TCountParser() {

        super();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.ETypeParser#convert(
     *      org.extex.interpreter.type.Code,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public EType convert(Code code, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        if (code instanceof CountConvertible) {
            return new TCount(((CountConvertible) code).convertCount(context,
                source, typesetter));
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.ETypeParser#parse(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public EType parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long n = 0;
        Token t = source.getNonSpace(context);
        Tokens save = new Tokens();
        int no;

        while (t != null) {

            save.add(t);

            if (t instanceof OtherToken) {
                int c = t.getChar().getCodePoint();
                switch (c) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        n = c - '0';

                        for (t = source.getToken(context); t instanceof OtherToken
                                && t.getChar().isDigit(); t =
                                source.getToken(context)) {
                            n = n * 10 + t.getChar().getCodePoint() - '0';
                            save.add(t);
                        }
                        if (t instanceof SpaceToken) {
                            source.skipSpace();
                        } else {
                            source.push(t);
                        }
                        return new TCount(n);

                    case '`':
                        t = source.getToken(context);
                        save.add(t);
                        if (t instanceof ControlSequenceToken) {
                            String s = ((ControlSequenceToken) t).getName();
                            n = ("".equals(s) ? 0 : s.charAt(0));
                            return new TCount(n);
                        } else if (t != null) {
                            n = t.getChar().getCodePoint();
                            return new TCount(n);
                        }
                        // fall through to error handling
                        break;

                    case '\'':
                        t = source.scanToken(context);
                        if (!(t instanceof OtherToken)) {
                            t = null;
                            break;
                        }
                        save.add(t);
                        n = t.getChar().getCodePoint() - '0';
                        if (n < 0 || n > 7) {
                            t = null;
                            break;
                        }
                        for (t = source.scanToken(context); t instanceof OtherToken; //
                        t = source.scanToken(context)) {
                            save.add(t);
                            no = t.getChar().getCodePoint() - '0';
                            if (no < 0 || no > 7) {
                                break;
                            }
                            n = n * 8 + no;
                        }
                        while (t instanceof SpaceToken) {
                            t = source.getToken(context);
                        }
                        source.push(t);
                        return new TCount(n);

                    case '"':

                        for (t = source.scanToken(context); //
                        t instanceof OtherToken || t instanceof LetterToken; //
                        t = source.scanToken(context)) {
                            save.add(t);
                            no = t.getChar().getCodePoint();
                            switch (no) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                    n = n * 16 + no - '0';
                                    break;
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                    n = n * 16 + no - 'a' + 10;
                                    break;
                                case 'A':
                                case 'B':
                                case 'C':
                                case 'D':
                                case 'E':
                                case 'F':
                                    n = n * 16 + no - 'A' + 10;
                                    break;
                                default:
                                    source.push(t);
                                    source.skipSpace();
                                    return new TCount(n);
                            }
                        }
                        while (t instanceof SpaceToken) {
                            t = source.getToken(context);
                        }
                        source.push(t);
                        return new TCount(n);

                    default:
                }
                break;
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code == null) {
                    break;

                } else if (code instanceof CountConvertible) {
                    n = ((CountConvertible) code).convertCount(context, //
                        source, typesetter);
                    return new TCount(n);
                } else if (code instanceof ExpandableCode) {
                    save.removeLast();
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);
                    t = source.getToken(context);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        source.push(save);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.ETypeParser#registered(
     *      org.extex.interpreter.expression.Evaluator)
     */
    public void registered(Evaluator evaluator) {

        evaluator.register("int", new UnaryFunction() {

            /**
             * {@inheritDoc}
             *
             * @see org.extex.interpreter.expression.UnaryFunction#apply(
             *      org.extex.interpreter.expression.EType)
             */
            public EType apply(EType accumulator)
                    throws HelpingException {

                return new TCount().set(accumulator);
            }
        });
    }

}
