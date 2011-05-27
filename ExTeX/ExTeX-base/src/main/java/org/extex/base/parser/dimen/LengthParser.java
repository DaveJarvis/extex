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
 * @version $Revision: 4726 $
 */
public final class LengthParser {

    /**
     * The field <tt>ASSIGN</tt> contains the operation to assign the second
     * argument to the first one.
     */
    private static final Function2 ASSIGN = new Function2() {

        /**
         * @see org.extex.base.parser.dimen.Function2#apply(org.extex.base.parser.dimen.Accumulator,
         *      org.extex.base.parser.dimen.Accumulator)
         */
        @Override
        public Accumulator apply(Accumulator arg1, Accumulator arg2) {

            if (arg1 == null) {
                return new Accumulator(arg2);
            }
            arg1.value = arg2.value;
            arg1.sp = arg2.sp;
            return arg1;
        }
    };

    /**
     * The field <tt>functions</tt> contains the function object attached to a
     * function name.
     */
    private static Map<String, Object> functions =
            new HashMap<String, Object>();

    /**
     * The field <tt>MINUS</tt> contains the subtractor.
     */
    private static final Function2 MINUS = new Function2() {

        /**
         * @see org.extex.base.parser.dimen.Function2#apply(org.extex.base.parser.dimen.Accumulator,
         *      org.extex.base.parser.dimen.Accumulator)
         */
        @Override
        public Accumulator apply(Accumulator arg1, Accumulator arg2)
                throws HelpingException {

            if (arg1 == null) {
                return arg2;
            }
            if (arg1.sp != arg2.sp) {
                throw new HelpingException(
                    LocalizerFactory.getLocalizer(LengthParser.class),
                    "IncompatibleUnit", "-", arg1.ordToString(),
                    arg2.ordToString());
            }
            arg1.value -= arg2.value;
            return arg1;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "minus";
        }

    };

    /**
     * The field <tt>PLUS</tt> contains the adder.
     */
    private static final Function2 PLUS = new Function2() {

        /**
         * @see org.extex.base.parser.dimen.Function2#apply(org.extex.base.parser.dimen.Accumulator,
         *      org.extex.base.parser.dimen.Accumulator)
         */
        @Override
        public Accumulator apply(Accumulator arg1, Accumulator arg2)
                throws HelpingException {

            if (arg1 == null) {
                return arg2;
            }
            if (arg1.sp != arg2.sp) {
                throw new HelpingException(
                    LocalizerFactory.getLocalizer(LengthParser.class),
                    "IncompatibleUnit", "+", arg1.ordToString(),
                    arg2.ordToString());
            }
            arg1.value += arg2.value;
            return arg1;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "plus";
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
             * @param accumulator the accumulator to receive the result
             * @param context the interpreter context
             * @param source the source for new tokens
             * @param typesetter the typesetter
             */
            @Override
            public Accumulator apply(Accumulator accumulator, Context context,
                    TokenSource source, Typesetter typesetter)
                    throws HelpingException,
                        TypesetterException {

                Token t;
                accumulator =
                        evalExpr(accumulator, context, source, typesetter);
                for (t = source.getNonSpace(context); t != null
                        && t.eq(Catcode.OTHER, ','); t =
                        source.getNonSpace(context)) {
                    Accumulator x = evalExpr(null, context, source, typesetter);
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
             * {@inheritDoc}
             * 
             * @see org.extex.base.parser.dimen.Function#apply(org.extex.base.parser.dimen.Accumulator,
             *      org.extex.interpreter.context.Context,
             *      org.extex.interpreter.TokenSource,
             *      org.extex.typesetter.Typesetter)
             */
            @Override
            public Accumulator apply(Accumulator accumulator, Context context,
                    TokenSource source, Typesetter typesetter)
                    throws HelpingException,
                        TypesetterException {

                Token t;
                accumulator =
                        evalExpr(accumulator, context, source, typesetter);
                for (t = source.getNonSpace(context); t != null
                        && t.eq(Catcode.OTHER, ','); t =
                        source.getNonSpace(context)) {
                    Accumulator x = evalExpr(null, context, source, typesetter);
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
                double x = ((double) accumulator.value) / ScaledNumber.ONE;
                accumulator.value = (long) (ScaledNumber.ONE * Math.cos(x));
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
                double x = ((double) accumulator.value) / ScaledNumber.ONE;
                accumulator.value = (long) (ScaledNumber.ONE * Math.sin(x));
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
                double x = ((double) accumulator.value) / ScaledNumber.ONE;
                accumulator.value = (long) (ScaledNumber.ONE * Math.tan(x));
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
     * @param accumulator the accumulator to receive the result
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the accumulator
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private static Accumulator evalExpr(Accumulator accumulator,
            Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        Token t;
        Function2 op = ASSIGN;
        Accumulator acc = evalTerminal(context, source, typesetter);

        for (t = source.getNonSpace(context); t != null; t =
                source.getNonSpace(context)) {

            if (t.eq(Catcode.OTHER, '*')) {
                Accumulator x = evalTerminal(context, source, typesetter);
                acc.value *= x.value;
                if (x.sp == 0) {
                    acc.value /= ScaledNumber.ONE;
                } else {
                    acc.value /= Dimen.ONE;
                    acc.sp += x.sp;
                }

            } else if (t.eq(Catcode.OTHER, '/')) {
                Accumulator x = evalTerminal(context, source, typesetter);
                if (x.value == 0) {
                    throw new ArithmeticOverflowException("");
                }
                if (x.sp == 0) {
                    acc.value *= ScaledNumber.ONE;
                } else {
                    acc.value *= Dimen.ONE;
                    acc.sp -= x.sp;
                }
                acc.value /= x.value;

            } else if (t.eq(Catcode.OTHER, '+')) {
                accumulator = op.apply(accumulator, acc);
                acc = evalTerminal(context, source, typesetter);
                op = PLUS;

            } else if (t.eq(Catcode.OTHER, '-')) {
                accumulator = op.apply(accumulator, acc);
                acc = evalTerminal(context, source, typesetter);
                op = MINUS;

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

        throw new MissingNumberException();
    }

    /**
     * Evaluate a terminal.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the accumulator the accumulator to receive the result or
     *         <code>null</code> for an undefined value
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
                    Accumulator accumulator = null;
                    accumulator =
                            evalExpr(accumulator, context, source, typesetter);
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
                    Accumulator accumulator = new Accumulator(ScaledNumber.ONE);
                    if (gc == null) {
                        accumulator.value *= value;
                        accumulator.value /= ScaledNumber.ONE;
                        accumulator.sp = 0;
                    } else {
                        accumulator.value *= gc.getValue();
                        accumulator.value /= ScaledNumber.ONE;
                        accumulator.sp = 1;
                    }

                    return accumulator;
                }
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof DimenConvertible) {
                    Accumulator accumulator = new Accumulator(ScaledNumber.ONE);
                    accumulator.value =
                            ((DimenConvertible) code).convertDimen(context,
                                source, typesetter);
                    accumulator.sp += 1;
                    return accumulator;

                } else if (code instanceof ScaledConvertible) {
                    Accumulator accumulator = new Accumulator(ScaledNumber.ONE);
                    accumulator.value =
                            ((ScaledConvertible) code).convertScaled(context,
                                source, typesetter);
                    // accumulator.value /= ScaledNumber.ONE;

                } else if (code instanceof CountConvertible) {
                    Accumulator accumulator = new Accumulator(ScaledNumber.ONE);
                    accumulator.value =
                            ((CountConvertible) code).convertCount(context,
                                source, typesetter);

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
                    Accumulator accumulator = new Accumulator();
                    GlueComponent gc =
                            GlueComponentParser.attachUnit(accumulator.value,
                                context, source, typesetter, false);
                    if (gc == null) {
                        throw new HelpingException(
                            LocalizerFactory.getLocalizer(LengthParser.class),
                            "SyntaxError", t.toString());
                    }
                    accumulator.value = gc.getValue() * ScaledNumber.ONE;
                    accumulator.sp++;
                    return accumulator;
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
                    accumulator = evalExpr(null, context, source, typesetter);
                    skipComma(context, source);
                    Accumulator arg2 =
                            evalExpr(null, context, source, typesetter);
                    accumulator = ((Function2) f).apply(accumulator, arg2);
                } else if (f instanceof Function) {
                    accumulator =
                            ((Function) f).apply(null, context, source,
                                typesetter);
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
            throw new HelpingException(
                LocalizerFactory.getLocalizer(LengthParser.class),
                (accumulator.sp == 0 ? "MissingUnit" : "IllegalUnit"),
                accumulator.ordToString());
        }
        source.skipSpace();
        return new Dimen(accumulator.value);
    }

    /**
     * Register a function.
     * 
     * @param name the name of the function
     * @param function the function
     */
    public static void register(String name, Function function) {

        functions.put(name, function);
    }

    /**
     * Register a function of arity 0 alas a constant.
     * 
     * @param name the name of the function
     * @param function the function
     */
    public static void register(String name, Function0 function) {

        functions.put(name, function);
    }

    /**
     * Register a function of arity 1.
     * 
     * @param name the name of the function
     * @param function the function
     */
    public static void register(String name, Function1 function) {

        functions.put(name, function);
    }

    /**
     * Register a function of arity 2.
     * 
     * @param name the name of the function
     * @param function the function
     */
    public static void register(String name, Function2 function) {

        functions.put(name, function);
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
    private static void skipComma(Context context, TokenSource source)
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
     * Creates a new object.
     */
    private LengthParser() {

    }

}
