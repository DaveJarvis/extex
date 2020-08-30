/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.base.parser.dimen;

import java.util.HashMap;
import java.util.Map;

import org.extex.base.parser.GlueComponentParser;
import org.extex.base.parser.ScaledNumberParser;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingNumberException;
import org.extex.core.glue.GlueComponent;
import org.extex.core.scaled.ScaledNumber;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.parser.ScaledConvertible;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides some static methods to parse an expression and return its
 * value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class LengthParser {

    /**
     * The field {@code ASSIGN} contains the operation to assign the second
     * argument to the first one and return it. The way this class is used
     * ensures that the first argument is always {@code null}. Thus simply
     * a copy of the second argument is returned.
     */
    private static final Function2 ASSIGN = new Function2() {

        /**
         * @see org.extex.base.parser.dimen.Function2#apply(org.extex.base.parser.dimen.Accumulator,
         *      org.extex.base.parser.dimen.Accumulator)
         */
        @Override
        public Accumulator apply(Accumulator arg1, Accumulator arg2) {

            return new Accumulator(arg2);
        }
    };

    /**
     * The field {@code functions} contains the function object attached to a
     * function name.
     */
    private static Map<String, Object> functions =
            new HashMap<String, Object>();

    /**
     * The field {@code MINUS} contains the subtracter. The way this class is
     * used ensures that both arguments are never {@code null}.
     */
    private static final Function2 MINUS = new Function2() {

        /**
         * @see org.extex.base.parser.dimen.Function2#apply(org.extex.base.parser.dimen.Accumulator,
         *      org.extex.base.parser.dimen.Accumulator)
         */
        @Override
        public Accumulator apply(Accumulator arg1, Accumulator arg2)
                throws HelpingException {

            if (arg1.sp != arg2.sp) {
                throw new HelpingException(
                    LocalizerFactory.getLocalizer(LengthParser.class),
                    "IncompatibleUnit", "-", arg1.ordToString(),
                    arg2.ordToString());
            }
            arg1.value -= arg2.value;
            return arg1;
        }
    };

    /**
     * The field {@code PLUS} contains the adder. The way this class is used
     * ensures that both arguments are never {@code null}.
     */
    private static final Function2 PLUS = new Function2() {

        /**
         * @see org.extex.base.parser.dimen.Function2#apply(org.extex.base.parser.dimen.Accumulator,
         *      org.extex.base.parser.dimen.Accumulator)
         */
        @Override
        public Accumulator apply(Accumulator arg1, Accumulator arg2)
                throws HelpingException {

            if (arg1.sp != arg2.sp) {
                throw new HelpingException(
                    LocalizerFactory.getLocalizer(LengthParser.class),
                    "IncompatibleUnit", "+", arg1.ordToString(),
                    arg2.ordToString());
            }
            arg1.value += arg2.value;
            return arg1;
        }
    };

    static {
        functions.put("abs", new Function1() {

            /**
             * Compute the absolute value by eliminating the sign if present.
             * 
             * @see org.extex.base.parser.dimen.Function1#apply(org.extex.base.parser.dimen.Accumulator)
             */
            @Override
            public Accumulator apply(Accumulator accumulator)
                    throws HelpingException {

                if (accumulator.value < 0) {
                    accumulator.value = -accumulator.value;
                }
                return accumulator;
            }
        });

        functions.put("max", new Function() {

            /**
             * Compute the maximum of an arbitrary number of arguments.
             * 
             * @param context the interpreter context
             * @param source the source for new tokens
             * @param typesetter the typesetter
             */
            @Override
            public Accumulator apply(Context context, TokenSource source,
                    Typesetter typesetter)
                    throws HelpingException,
                        TypesetterException {

                Accumulator accumulator = evalExpr(context, source, typesetter);
                Token t;
                for (t = source.getNonSpace(context); t != null
                        && t.eq(Catcode.OTHER, ','); t =
                        source.getNonSpace(context)) {
                    Accumulator x = evalExpr(context, source, typesetter);
                    if (accumulator.sp != x.sp) {
                        throw new HelpingException(LocalizerFactory
                            .getLocalizer(LengthParser.class),
                            "IncompatibleUnit", "max", accumulator
                                .ordToString(), x.ordToString());
                    }
                    if (accumulator.value < x.value) {
                        accumulator.value = x.value;
                    }
                }
                source.push(t);
                return accumulator;
            }
        });

        functions.put("min", new Function() {

            /**
             * Compute the minimum of an arbitrary number of arguments.
             * 
        *      org.extex.interpreter.TokenSource,
             *      org.extex.typesetter.Typesetter)
             */
            @Override
            public Accumulator apply(Context context, TokenSource source,
                    Typesetter typesetter)
                    throws HelpingException,
                        TypesetterException {

                Accumulator accumulator = evalExpr(context, source, typesetter);
                Token t;
                for (t = source.getNonSpace(context); t != null
                        && t.eq(Catcode.OTHER, ','); t =
                        source.getNonSpace(context)) {
                    Accumulator x = evalExpr(context, source, typesetter);
                    if (accumulator.sp != x.sp) {
                        throw new HelpingException(LocalizerFactory
                            .getLocalizer(LengthParser.class),
                            "IncompatibleUnit", "min", accumulator
                                .ordToString(), x.ordToString());
                    }
                    if (accumulator.value > x.value) {
                        accumulator.value = x.value;
                    }
                }
                source.push(t);
                return accumulator;
            }
        });

        functions.put("sgn", new Function1() {

            /**
             * Compute the sign i.e. return 1 for positive values, 0 for zero
             * values and -1 for negative values.
             * 
             * @see org.extex.base.parser.dimen.Function1#apply(org.extex.base.parser.dimen.Accumulator)
             */
            @Override
            public Accumulator apply(Accumulator accumulator)
                    throws HelpingException {

                if (accumulator.value > 0) {
                    accumulator.value = 1;
                } else if (accumulator.value < 0) {
                    accumulator.value = -1;
                } else {
                    accumulator.value = 0;
                }
                accumulator.sp = 0;
                return accumulator;
            }
        });

        functions.put("cos", new Function1() {

            /**
             * Compute the cos value.
             * 
             * @see org.extex.base.parser.dimen.Function1#apply(org.extex.base.parser.dimen.Accumulator)
             */
            @Override
            public Accumulator apply(Accumulator accumulator)
                    throws HelpingException {

                if (accumulator.sp != 0) {
                    throw new HelpingException(LocalizerFactory
                        .getLocalizer(LengthParser.class), "NonScalar", "cos",
                        accumulator.toString());
                }
                accumulator.value =
                        (long) (Math.cos(((double) accumulator.value)
                                / ScaledNumber.ONE) * ScaledNumber.ONE);
                return accumulator;
            }
        });

        functions.put("sin", new Function1() {

            /**
             * Compute the sin value.
             * 
             * @see org.extex.base.parser.dimen.Function1#apply(org.extex.base.parser.dimen.Accumulator)
             */
            @Override
            public Accumulator apply(Accumulator accumulator)
                    throws HelpingException {

                if (accumulator.sp != 0) {
                    throw new HelpingException(LocalizerFactory
                        .getLocalizer(LengthParser.class), "NonScalar", "sin",
                        accumulator.toString());
                }
                accumulator.value =
                        (long) (Math.sin(((double) accumulator.value)
                                / ScaledNumber.ONE) * ScaledNumber.ONE);
                return accumulator;
            }
        });

        functions.put("tan", new Function1() {

            /**
             * Compute the tan value.
             * 
             * @see org.extex.base.parser.dimen.Function1#apply(org.extex.base.parser.dimen.Accumulator)
             */
            @Override
            public Accumulator apply(Accumulator accumulator)
                    throws HelpingException {

                if (accumulator.sp != 0) {
                    throw new HelpingException(LocalizerFactory
                        .getLocalizer(LengthParser.class), "NonScalar", "tan",
                        accumulator.toString());
                }
                accumulator.value =
                        (long) (Math.tan(((double) accumulator.value)
                                / ScaledNumber.ONE) * ScaledNumber.ONE);
                return accumulator;
            }
        });

        functions.put("pi", new Function0() {

            /**
             * Compute the value of pi.
             */
            @Override
            public Accumulator apply() throws HelpingException {

                return new Accumulator(205887);
            }
        });
    }

    /**
     * Evaluate an expression.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the accumulator
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private static Accumulator evalExpr(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Accumulator accumulator = null;
        Function2 op = ASSIGN;
        Accumulator acc = evalTerminal(context, source, typesetter);

        for (Token t = source.getNonSpace(context); t != null; t =
                source.getNonSpace(context)) {

            if (t.eq(Catcode.OTHER, '+')) {
                accumulator = op.apply(accumulator, acc);
                acc = evalTerminal(context, source, typesetter);
                op = PLUS;

            } else if (t.eq(Catcode.OTHER, '-')) {
                accumulator = op.apply(accumulator, acc);
                acc = evalTerminal(context, source, typesetter);
                op = MINUS;

            } else if (t.eq(Catcode.OTHER, '*')) {
                Accumulator x = evalTerminal(context, source, typesetter);
                acc.scale(x.value, ScaledNumber.ONE, x.sp);

            } else if (t.eq(Catcode.OTHER, '/')) {
                Accumulator x = evalTerminal(context, source, typesetter);
                if (x.value == 0) {
                    throw new ArithmeticOverflowException("");
                }
                acc.scale(ScaledNumber.ONE, x.value, -x.sp);

            } else {
                source.push(t);
                GlueComponent gc =
                        GlueComponentParser.attachUnit(acc.value, context,
                            source, typesetter, false);
                if (gc != null) {
                    acc.value = gc.getValue();
                    acc.sp++;
                    return acc;
                }
                accumulator = op.apply(accumulator, acc);
                return accumulator;
            }
        }

        throw new EofException();
    }

    /**
     * Evaluate a terminal.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the accumulator the accumulator to receive the result or
     *         {@code null} for an undefined value
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static Accumulator evalTerminal(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        for (Token t = source.getNonSpace(context); t != null; t =
                source.getNonSpace(context)) {

            if (t instanceof OtherToken) {
                if (t.eq(Catcode.OTHER, '(')) {
                    Accumulator accumulator =
                            evalExpr(context, source, typesetter);
                    t = source.getNonSpace(context);
                    if (t == null || !t.eq(Catcode.OTHER, ')')) {
                        throw new HelpingException(
                            LocalizerFactory.getLocalizer(LengthParser.class),
                            "MissingParenthesis", (t == null
                                    ? "null"
                                    : t.toString()));
                    }
                    return accumulator;

                } else if (t.eq(Catcode.OTHER, '-')) {
                    return evalTerminal(context, source, typesetter).negate();

                } else if (t.eq(Catcode.OTHER, '+')) {
                    // continue
                } else {
                    long value =
                            ScaledNumberParser.scanFloat(context, source,
                                typesetter, t, false);
                    GlueComponent gc =
                            GlueComponentParser.attachUnit(value, context,
                                source, typesetter, false);
                    return gc == null
                            ? new Accumulator(value)
                            : new Accumulator(gc.getValue(), 1);
                }
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof DimenConvertible) {
                    return new Accumulator(
                        ((DimenConvertible) code).convertDimen(context, source,
                            typesetter), 1);

                } else if (code instanceof ScaledConvertible) {
                    return new Accumulator(
                        ((ScaledConvertible) code).convertScaled(context,
                            source, typesetter));

                } else if (code instanceof CountConvertible) {
                    return new Accumulator(
                        ((CountConvertible) code).convertCount(context, source,
                            typesetter));

                } else if (code instanceof ExpandableCode) {
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);

                } else {
                    break;
                }
            } else if (t instanceof LetterToken) {
                Tokens tokens = new Tokens(t);
                for (t = source.getToken(context); t != null
                        && t instanceof LetterToken; t =
                        source.getToken(context)) {
                    tokens.add(t);
                }
                source.push(t);
                String name = tokens.toText();

                Object f = functions.get(name);
                if (f == null) {
                    source.push(tokens);
                    GlueComponent gc =
                            GlueComponentParser.attachUnit(ScaledNumber.ONE,
                                context, source, typesetter, false);
                    if (gc == null) {
                        throw new HelpingException(
                            LocalizerFactory.getLocalizer(LengthParser.class),
                            "SyntaxError", t.toString());
                    }
                    return new Accumulator(gc.getValue(), 1);
                }
                if (f instanceof Function0) {
                    return ((Function0) f).apply();
                }
                if (f instanceof Function1) {
                    return ((Function1) f).apply(evalTerminal(context, source,
                        typesetter));
                }
                t = source.getNonSpace(context);
                if (t == null) {
                    throw new EofException();
                } else if (!t.eq(Catcode.OTHER, '(')) {
                    throw new HelpingException(
                        LocalizerFactory.getLocalizer(LengthParser.class),
                        "MissingOpenParenthesis", name, t.toString());
                }
                Accumulator accumulator = null;
                if (f instanceof Function2) {
                    accumulator = evalExpr(context, source, typesetter);
                    expectComma(context, source);
                    Accumulator arg2 = evalExpr(context, source, typesetter);
                    accumulator = ((Function2) f).apply(accumulator, arg2);
                } else if (f instanceof Function) {
                    accumulator =
                            ((Function) f).apply(context, source, typesetter);
                } else {
                    break;
                }

                t = source.getNonSpace(context);
                if (t == null) {
                    throw new EofException();
                } else if (!t.eq(Catcode.OTHER, ')')) {
                    throw new HelpingException(
                        LocalizerFactory.getLocalizer(LengthParser.class),
                        "MissingParenthesis", t.toString());
                }
                source.skipSpace();
                return accumulator;

            } else {
                break;
            }
        }

        throw new MissingNumberException();
    }

    /**
     * Find the next comma after any white-space and discard it and the
     * white-space afterwards.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * 
     * @throws HelpingException in case of an error
     */
    private static void expectComma(Context context, TokenSource source)
            throws HelpingException {

        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new EofException();
        } else if (!t.eq(Catcode.OTHER, ',')) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(LengthParser.class),
                "MissingComma", t.toString());
        }
        source.skipSpace();
    }

    /**
     * Creates a new object from a token stream.
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
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Accumulator accumulator = evalTerminal(context, source, typesetter);

        if (accumulator.sp != 1) {
            if (accumulator.sp == 0) {
                GlueComponent gc =
                        GlueComponentParser.attachUnit(accumulator.value
                                * ScaledNumber.ONE, context, source,
                            typesetter, false);
                if (gc != null) {
                    source.skipSpace();
                    return new Dimen(gc.getValue());
                }
                throw new HelpingException(
                    LocalizerFactory.getLocalizer(LengthParser.class),
                    "MissingUnit", accumulator.ordToString());
            }
            throw new HelpingException(
                LocalizerFactory.getLocalizer(LengthParser.class),
                "IllegalUnit", accumulator.ordToString());
        }
        source.skipSpace();
        return new Dimen(accumulator.value);
    }

    /**
     * Register a function.
     * 
     * @param name the name of the function
     * @param function the function
     * 
     * @return the previously registered function or {@code null} for none
     */
    public static Object register(String name, Function function) {

        return functions.put(name, function);
    }

    /**
     * Register a function of arity 0 alas a constant.
     * 
     * @param name the name of the function
     * @param function the function
     * 
     * @return the previously registered function or {@code null} for none
     */
    public static Object register(String name, Function0 function) {

        return functions.put(name, function);
    }

    /**
     * Register a function of arity 1.
     * 
     * @param name the name of the function
     * @param function the function
     * 
     * @return the previously registered function or {@code null} for none
     */
    public static Object register(String name, Function1 function) {

        return functions.put(name, function);
    }

    /**
     * Register a function of arity 2.
     * 
     * @param name the name of the function
     * @param function the function
     * 
     * @return the previously registered function or {@code null} for none
     */
    public static Object register(String name, Function2 function) {

        return functions.put(name, function);
    }

    /**
     * Unregister a function.
     * 
     * @param name the name of the function
     * 
     * @return the previously registered function or {@code null} for none
     */
    public static Object unregister(String name) {

        return functions.remove(name);
    }


    private LengthParser() {

    }

}
