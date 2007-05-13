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

package org.extex.base.parser;

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingNumberException;
import org.extex.core.scaled.ScaledNumber;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides a fixed point number.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ScaledNumberParser {

    /**
     * This interface describes a binary operation on two longs.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private interface BinOp {

        /**
         * Apply the operation on the arguments.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * 
         * @return the result
         */
        long apply(long arg1, long arg2);
    }

    /**
     * The constant <tt>FLOAT_DIGITS</tt> contains the number of digits to
     * consider when producing a string representation of this type.
     * 
     * Attention: Do not change this value unless you have read and understood
     * <logo>TeX</logo> the program!
     */
    private static final int FLOAT_DIGITS = 17;

    /**
     * The field <tt>MINUS</tt> contains the subtractor.
     */
    private static final BinOp MINUS = new BinOp() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.base.parser.ScaledNumberParser.BinOp#apply(long, long)
         */
        public long apply(long arg1, long arg2) {

            return arg1 - arg2;
        }
    };

    /**
     * The field <tt>PLUS</tt> contains the adder.
     */
    private static final BinOp PLUS = new BinOp() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.base.parser.ScaledNumberParser.BinOp#apply(long, long)
         */
        public long apply(long arg1, long arg2) {

            return arg1 + arg2;
        }
    };

    /**
     * The field <tt>SECOND</tt> contains the operation to select the second
     * argument.
     */
    private static final BinOp SECOND = new BinOp() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.base.parser.ScaledNumberParser.BinOp#apply(long, long)
         */
        public long apply(long arg1, long arg2) {

            return arg2;
        }

    };

    /**
     * Evaluate an expression.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private static long evalExpr(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long saveVal = 0;
        BinOp op = SECOND;
        long val = parse(context, source, typesetter);

        for (;;) {

            Token t = source.getNonSpace(context);
            if (t == null) {
                throw new EofException();

            } else if (t.equals(Catcode.OTHER, '*')) {
                val *= parse(context, source, typesetter);
                val /= ScaledNumber.ONE;

            } else if (t.equals(Catcode.OTHER, '/')) {
                long x = parse(context, source, typesetter);
                if (x == 0) {
                    throw new ArithmeticOverflowException("");
                }
                val *= ScaledNumber.ONE;
                val /= x;

            } else if (t.equals(Catcode.OTHER, '+')) {
                saveVal = op.apply(saveVal, val);
                val = parse(context, source, typesetter);
                op = PLUS;

            } else if (t.equals(Catcode.OTHER, '-')) {
                saveVal = op.apply(saveVal, val);
                val = parse(context, source, typesetter);
                op = MINUS;

            } else {
                source.push(t);
                return op.apply(saveVal, val);
            }
        }
    }

    /**
     * Evaluate an expression.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static long parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        for (;;) {
            Token t = source.getNonSpace(context);
            if (t == null) {
                throw new EofException();

            } else if (t instanceof OtherToken) {
                if (t.equals(Catcode.OTHER, '(')) {
                    long val = evalExpr(context, source, typesetter);
                    t = source.getToken(context);
                    if (t != null && t.equals(Catcode.OTHER, ')')) {
                        source.skipSpace();
                        return val;
                    }

                    throw new HelpingException(LocalizerFactory
                        .getLocalizer(ScaledNumberParser.class),
                        "MissingParenthesis", (t == null ? "null" : t
                            .toString()));

                } else if (t.equals(Catcode.OTHER, '-')) {
                    return -parse(context, source, typesetter);
                } else {
                    return scanFloat(context, source, typesetter, t);
                }

            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof ScaledConvertible) {
                    long val =
                            ((ScaledConvertible) code).convertScaled(context,
                                source, typesetter);
                    source.skipSpace();
                    return val;

                } else if (code instanceof CountConvertible) {
                    long val =
                            ((CountConvertible) code).convertCount(context,
                                source, typesetter)
                                    * ScaledNumber.ONE;
                    source.skipSpace();
                    return val;

                } else if (code instanceof ExpandableCode) {
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);
                } else {
                    source.push(t);
                    break;
                }
            } else if (t instanceof LetterToken) {
                source.push(t);
                if (source.getKeyword(context, "min")) {
                    // TODO

                } else if (source.getKeyword(context, "max")) {
                    // TODO

                } else if (source.getKeyword(context, "sin")) {
                    // TODO

                }

                break;

            } else {
                break;
            }
        }

        throw new MissingNumberException();
    }

    /**
     * Parses a token stream for a float and returns it as fixed point number.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param start the initial token to start with
     * 
     * @return the fixed point representation of the floating number in units of
     *         2<sup>-16</sup>.
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static long scanFloat(Context context, TokenSource source,
            Typesetter typesetter, Token start)
            throws HelpingException,
                TypesetterException {

        boolean neg = false;
        long val = 0;
        int post = 0;
        Token t = start;
        if (t == null) {
            t = source.scanNonSpace(context);
        }

        while (t != null) {
            if (t.equals(Catcode.OTHER, '-')) {
                neg = !neg;
            } else if (!t.equals(Catcode.OTHER, '+')) {
                break;
            }
            t = source.scanNonSpace(context);
        }
        if (t != null && !t.equals(Catcode.OTHER, ".")
                && !t.equals(Catcode.OTHER, ",")) {
            val = CountParser.scanNumber(context, source, typesetter, t);
            t = source.getToken(context);
        }
        if (t != null
                && (t.equals(Catcode.OTHER, '.') || t
                    .equals(Catcode.OTHER, ','))) {
            // @see "TeX -- The Program [102]"
            int[] dig = new int[FLOAT_DIGITS];
            int k = 0;
            for (t = source.getToken(context); t instanceof OtherToken
                    && t.getChar().isDigit(); t = source.getToken(context)) {
                if (k < FLOAT_DIGITS) {
                    dig[k++] = t.getChar().getCodePoint() - '0';
                }
            }
            if (k < FLOAT_DIGITS) {
                k = FLOAT_DIGITS;
            }
            post = 0;
            while (k-- > 0) {
                post = (post + dig[k] * (1 << FLOAT_DIGITS)) / 10;
            }
            post = (post + 1) / 2;
        }
        source.push(t);
        source.skipSpace();
        val = val << 16 | post;
        return (neg ? -val : val);
    }

    /**
     * Parses a token stream for a float and returns it as fixed point number.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the fixed point representation of the floating point number
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static ScaledNumber scanScaledNumber(Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        return new ScaledNumber(parse(context, source, typesetter));
    }

    /**
     * Creates a new object.
     */
    private ScaledNumberParser() {

        // impossible
    }

}
