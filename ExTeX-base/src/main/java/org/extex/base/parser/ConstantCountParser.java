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

import org.extex.core.count.Count;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingNumberException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.CountParser;
import org.extex.interpreter.parser.Parser;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class represents a long integer value. It is used for instance as count
 * register.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4399 $
 */
public final class ConstantCountParser implements Parser<Count>, CountParser {

    /**
     * Creates a new object.
     */
    public ConstantCountParser() {

        super();
    }

    /**
     * This interface describes a binary operation on two longs.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4399 $
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
     * This operation subtracts the second argument from the first one.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4399 $
     */
    private static final class Minus implements BinOp {

        /**
         * Apply the operation on the arguments.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * 
         * @return the result
         * 
         * @see org.extex.base.parser.ConstantCountParser.BinOp#apply(long,
         *      long)
         */
        public long apply(long arg1, long arg2) {

            return arg1 - arg2;
        }
    }

    /**
     * This operation adds the arguments.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4399 $
     */
    private static final class Plus implements BinOp {

        /**
         * Apply the operation on the arguments.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * 
         * @return the result
         * 
         * @see org.extex.base.parser.ConstantCountParser.BinOp#apply(long,
         *      long)
         */
        public long apply(long arg1, long arg2) {

            return arg1 + arg2;
        }
    }

    /**
     * This operation ignores the first argument and returns the second one.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4399 $
     */
    private static final class Second implements BinOp {

        /**
         * Apply the operation on the arguments.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * 
         * @return the result
         * 
         * @see org.extex.base.parser.ConstantCountParser.BinOp#apply(long,
         *      long)
         */
        public long apply(long arg1, long arg2) {

            return arg2;
        }
    }

    /**
     * The field <tt>MINUS</tt> contains the subtractor.
     */
    private static final BinOp MINUS = new Minus();

    /**
     * The field <tt>PLUS</tt> contains the adder.
     */
    private static final BinOp PLUS = new Plus();

    /**
     * The field <tt>SECOND</tt> contains the operation to select the second
     * argument.
     */
    private static final BinOp SECOND = new Second();

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
        long val = scanInteger(context, source, typesetter);

        for (;;) {

            Token t = source.getNonSpace(context);
            if (t == null) {
                throw new MissingNumberException();

            } else if (t.equals(Catcode.OTHER, '*')) {
                val *= scanInteger(context, source, typesetter);

            } else if (t.equals(Catcode.OTHER, '/')) {
                long x = scanInteger(context, source, typesetter);
                if (x == 0) {
                    throw new ArithmeticOverflowException("");
                }
                val /= x;

            } else if (t.equals(Catcode.OTHER, '+')) {
                saveVal = op.apply(saveVal, val);
                val = scanInteger(context, source, typesetter);
                op = PLUS;

            } else if (t.equals(Catcode.OTHER, '-')) {
                saveVal = op.apply(saveVal, val);
                val = scanInteger(context, source, typesetter);
                op = MINUS;

            } else {
                source.push(t);
                return op.apply(saveVal, val);
            }
        }
    }

    /**
     * Scan the input stream for tokens making up an integer, this is a number
     * optionally preceded by a sign (+ or -). The number can be preceded by
     * optional white space. White space is also ignored between the sign and
     * the number. All non-whitespace characters must have the category code
     * OTHER.
     * 
     * <p>
     * This method parses the following syntactic entity:
     * </p>
     * 
     * <doc type="syntax" name="integer">
     * <h3>A Number</h3>
     * 
     * <pre class="syntax">
     *    &amp;langnumber&amp;rang
     * </pre>
     * 
     * <p>
     * A number consists of a non-empty sequence of digits with category code
     * {@link org.extex.scanner.type.Catcode#OTHER OTHER}. The number is
     * optionally preceded by white space and a sign <tt>+</tt> or <tt>-</tt>.
     * </p>
     * <p>
     * Tokens are expanded while gathering the requested values.
     * </p>
     * 
     * </doc>
     * 
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use for conversion
     * 
     * @return the value of the count
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static long scanInteger(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        for (;;) {
            Token t = source.getNonSpace(context);
            if (t == null) {
                throw new MissingNumberException();

            } else if (t.equals(Catcode.OTHER, '(')) {
                long val = evalExpr(context, source, typesetter);
                t = source.getToken(context);
                if (t != null && t.equals(Catcode.OTHER, ')')) {
                    return val;
                }

                throw new HelpingException(LocalizerFactory
                    .getLocalizer(ConstantCountParser.class),
                    "MissingParenthesis", (t == null ? "null" : t.toString()));

            } else if (t.equals(Catcode.OTHER, '-')) {
                return -scanInteger(context, source, typesetter);

            } else if (t.equals(Catcode.OTHER, '+')) {
                // continue

            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof CountConvertible) {
                    return ((CountConvertible) code).convertCount(context,
                        source, typesetter);
                } else if (code instanceof ExpandableCode) {
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);
                } else {
                    throw new MissingNumberException();
                }
            } else {
                return scanNumber(context, source, typesetter, t);
            }
        }

    }

    /**
     * Scan the input stream for tokens making up a number. A number is a
     * sequence of digits with category code OTHER. The number can be preceded
     * by optional white space.
     * <p>
     * This method implements the generalization of several syntactic
     * definitions from <logo>TeX</logo>:
     * </p>
     * 
     * <doc type="syntax" name="number">
     * <h3>A Number</h3>
     * 
     * <pre class="syntax">
     *    &amp;langnumber&amp;rang
     * </pre>
     * 
     * <p>
     * A number consists of a non-empty sequence of digits with category code
     * {@link org.extex.scanner.type.Catcode#OTHER OTHER}.
     * </p>
     * 
     * </doc>
     * 
     * 
     * Scan the input stream for tokens making up a number, this is a sequence
     * of digits with category code <tt>OTHER</tt>. The number can be
     * preceded by optional white space. Alternate representations for an
     * integer exist.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the value of the integer scanned
     * 
     * @throws HelpingException in case that no number is found or the end of
     *         file has been reached before an integer could be acquired
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static long scanNumber(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return scanNumber(context, source, typesetter, //
            source.getNonSpace(context));
    }

    /**
     * Scan the input stream for tokens making up a number, this is a sequence
     * of digits with category code <tt>OTHER</tt>. The number can be
     * preceded by optional white space. Alternate representations for an
     * integer exist.
     * 
     * @param context the processor context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param token the first token
     * 
     * @return the value of the integer scanned
     * 
     * @throws HelpingException in case that no number is found or the end of
     *         file has been reached before an integer could be acquired
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static long scanNumber(Context context, TokenSource source,
            Typesetter typesetter, Token token)
            throws HelpingException,
                TypesetterException {

        long n = 0;
        Token t = token;
        int no;

        while (t != null) {

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

                        for (;;) {
                            for (t = source.getToken(context); t instanceof OtherToken
                                    && t.getChar().isDigit(); t =
                                    source.getToken(context)) {
                                n = n * 10 + t.getChar().getCodePoint() - '0';
                            }
                            if (t instanceof CodeToken) {
                                Code code = context.getCode((CodeToken) t);
                                if (code instanceof CountConvertible) {
                                    // ignored on purpose
                                } else if (code instanceof ExpandableCode) {
                                    ((ExpandableCode) code).expand(Flags.NONE,
                                        context, source, typesetter);
                                    continue;
                                }
                            }
                            break;
                        }

                        if (t instanceof SpaceToken) {
                            source.skipSpace();
                        } else {
                            source.push(t);
                        }
                        return n;

                    case '`':
                        t = source.getToken(context);

                        if (t instanceof ControlSequenceToken) {
                            String s = ((ControlSequenceToken) t).getName();
                            return ("".equals(s) ? 0 : s.charAt(0));
                        } else if (t != null) {
                            return t.getChar().getCodePoint();
                        }
                        // fall through to error handling
                        break;

                    case '\'':
                        t = source.scanToken(context);
                        if (!(t instanceof OtherToken)) {
                            throw new MissingNumberException();
                        }
                        n = t.getChar().getCodePoint() - '0';
                        if (n < 0 || n > 7) {
                            throw new MissingNumberException();
                        }
                        for (t = source.scanToken(context); t instanceof OtherToken; //
                        t = source.scanToken(context)) {
                            no = t.getChar().getCodePoint() - '0';
                            if (no < 0 || no > 7) {
                                break;
                            }
                            n = n * 8 + no;
                        }

                        if (t instanceof SpaceToken) {
                            source.skipSpace();
                        } else {
                            source.push(t);
                        }
                        return n;

                    case '"':

                        for (t = source.scanToken(context); //
                        t instanceof OtherToken || t instanceof LetterToken; //
                        t = source.scanToken(context)) {
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
                                    return n;
                            }
                        }

                        if (t instanceof SpaceToken) {
                            source.skipSpace();
                        } else {
                            source.push(t);
                        }
                        return n;

                    default:
                }
                break;
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof CountConvertible) {
                    return ((CountConvertible) code).convertCount(context,
                        source, typesetter);
                } else if (code instanceof ExpandableCode) {
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

        throw new MissingNumberException();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.Parser#parse(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Count parse(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return new Count(scanInteger(context, source, typesetter));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountParser#parseInteger(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long parseInteger(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return scanInteger(context, source, typesetter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountParser#parseNumber(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long parseNumber(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return scanNumber(context, source, typesetter);
    }

}
