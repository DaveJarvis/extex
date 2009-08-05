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

package org.extex.interpreter.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingNumberException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.expression.exception.CastException;
import org.extex.interpreter.expression.exception.UnsupportedException;
import org.extex.interpreter.expression.term.Accumulator;
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

/*
 * This class provides some static methods to parse an expression and return its
 * value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision: 4787 $
 */
/*
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision$
 */
public class Evaluator {

    /*
     * The field <tt>ASSIGN</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="aSSIGN"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="aSSIGN"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction ASSIGN = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2) {

            return arg2;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return ":=";
        }

    };

    /*
     * The field <tt>EQ</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="eQ"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="eQ"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction EQ = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.eq(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "==";
        }

    };

    /*
     * The field <tt>GE</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="gE"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="gE"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction GE = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.ge(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return ">=";
        }

    };

    /*
     * The field <tt>GT</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="gT"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="gT"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction GT = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.gt(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return ">";
        }

    };

    /*
     * The field <tt>LAND</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="lAND"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="lAND"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction LAND = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.and(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "&&";
        }

    };

    /*
     * The field <tt>LE</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="lE"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="lE"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction LE = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.le(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "<=";
        }

    };

    /*
     * The field <tt>LOR</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="lOR"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="lOR"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction LOR = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.or(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "||";
        }

    };

    /*
     * The field <tt>LT</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="lT"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="lT"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction LT = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.lt(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "<";
        }

    };

    /*
     * The field <tt>MINUS</tt> contains the subtractor.
     * 
     * @uml.property name="mINUS"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="mINUS"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction MINUS = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2) throws HelpingException {

            return arg1.subtract(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "-";
        }

    };

    /*
     * The field <tt>LAND</tt> contains the operation to assign the second
     * argument to the first one.
     * 
     * @uml.property name="nE"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="nE"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction NE = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2)
                throws CastException,
                    UnsupportedException {

            return arg1.ne(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "!=";
        }

    };

    /*
     * The field <tt>PLUS</tt> contains the adder.
     * 
     * @uml.property name="pLUS"
     * 
     * @uml.associationEnd
     */
    /*
     * @uml.property name="pLUS"
     * 
     * @uml.associationEnd
     */
    private static final BinaryFunction PLUS = new BinaryFunction() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.expression.BinaryFunction#apply(
         *      org.extex.interpreter.expression.EType,
         *      org.extex.interpreter.expression.EType)
         */
        public EType apply(EType arg1, EType arg2) throws HelpingException {

            return arg1.add(arg2);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "+";
        }

    };

    /**
     * The field <tt>UC_AND</tt> contains the Unicode code point for the
     * logical and.
     */
    private static final int UC_AND = 0x2227;

    /**
     * The field <tt>UC_GE</tt> contains the Unicode code point for the
     * greater or equal sign.
     */
    private static final int UC_GE = 0x2265;

    /**
     * The field <tt>UC_LE</tt> contains the Unicode code point for the less
     * or equal sign.
     */
    private static final int UC_LE = 0x2264;

    /**
     * The field <tt>UC_NE</tt> contains the Unicode code point for the not
     * equal sign.
     */
    private static final int UC_NE = 0x2260;

    /**
     * The field <tt>UC_NOT</tt> contains the Unicode code point for the
     * logical not sign.
     */
    private static final int UC_NOT = 0xac;

    /**
     * The field <tt>UC_OR</tt> contains the Unicode code point for the
     * logical or.
     */
    private static final int UC_OR = 0x2228;

    /**
     * Find the next comma after any white-space and discard it and the
     * white-space afterwards.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * 
     * @throws HelpingException in case of an error
     */
    protected static void skipComma(Context context, TokenSource source)
            throws HelpingException {

        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new EofException();
        } else if (!t.eq(Catcode.OTHER, ',')) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(Evaluator.class), "MissingComma", t.toString());
        }
        source.skipSpace();
    }

    /**
     * The field <tt>functions</tt> contains the function object attached to a
     * function name.
     */
    private Map<String, Object> functions = new HashMap<String, Object>();

    /**
     * The field <tt>parsers</tt> contains the list of registered parsers.
     */
    private List<ETypeParser> parsers = new ArrayList<ETypeParser>();

    /**
     * Creates a new object.
     */
    public Evaluator() {

        super();
    }

    /**
     * Creates a new object from a token stream.
     * 
     * @param term the terminal to store the result in
     * @param context the Helping context
     * @param source the source for next tokens
     * @param typesetter the typesetter
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public void eval(EType term, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        term.set(evalLogicExpressionOrFunctionalExpression(context, source,
            typesetter));
        source.skipSpace();
        return;
    }

    /**
     * Evaluate an expression.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private EType evalExpression(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return evalExpression(evalTerm(context, source, typesetter), //
            context, source, typesetter);
    }

    /**
     * Evaluate an expression.
     * 
     * @param start ...
     * @param context the Helping context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private EType evalExpression(EType start, Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        EType accumulator = null;
        EType savedValue = start;
        BinaryFunction op = ASSIGN;

        for (Token t = source.getNonSpace(context); t != null; t =
                source.getNonSpace(context)) {

            int c = (t instanceof OtherToken //
                    ? t.getChar().getCodePoint()//
                    : '\0');

            switch (c) {
                case '+':
                    accumulator = op.apply(accumulator, savedValue);
                    savedValue = evalTerm(context, source, typesetter);
                    op = PLUS;
                    break;
                case '-':
                    accumulator = op.apply(accumulator, savedValue);
                    savedValue = evalTerm(context, source, typesetter);
                    op = MINUS;
                    break;
                case '*':
                    savedValue =
                            savedValue.multiply(evalTerm(context, source,
                                typesetter));
                    break;
                case '/':
                    savedValue =
                            savedValue.divide(evalTerm(context, source,
                                typesetter));
                    break;
                default:
                    source.push(t);
                    return op.apply(accumulator, savedValue);
            }
        }

        throw new MissingNumberException();
    }

    /**
     * Evaluate some expression in parentheses.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private EType evalGroup(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        EType a = evalTerm(context, source, typesetter);
        Token t = source.getNonSpace(context);
        if (t != null && t.eq(Catcode.OTHER, ')')) {
            return a;
        }
        source.push(t);
        if (t instanceof OtherToken) {
            int c = t.getChar().getCodePoint();
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                a = evalExpression(a, context, source, typesetter);
                t = source.getNonSpace(context);
                if (t != null && t.eq(Catcode.OTHER, ')')) {
                    return a;
                }
            }
            BinaryFunction op = getOp(context, source);
            if (op != null) {
                a = op.apply(a, evalExpression(context, source, typesetter));
                t = source.getNonSpace(context);
                if (t != null && t.eq(Catcode.OTHER, ')')) {
                    return a;
                }
                source.push(t);
            }
            op = getJunctor(context, source);
            if (op != null) {
                a = evalJunction(a, op, context, source, typesetter);
                t = source.getNonSpace(context);
                if (t != null && t.eq(Catcode.OTHER, ')')) {
                    return a;
                }
                source.push(t);
            }
        }

        throw new HelpingException(LocalizerFactory
            .getLocalizer(Evaluator.class), "MissingParenthesis", //
            (t == null ? "null" : t.toString()));
    }

    /**
     * Evaluate a logical junction expression.
     * 
     * @param start the value of the first junction element
     * @param junctor the junctor, i.e. && or ||
     * @param context the Helping context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private EType evalJunction(EType start, BinaryFunction junctor,
            Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        EType a = start;

        for (;;) {
            a = junctor.apply(a, evalLogicTerm(context, source, typesetter));
            if (!getJunctor(junctor, context, source)) {
                return a;
            }
        }
    }

    /**
     * Evaluate a logical expression.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private EType evalLogicTerm(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new MissingNumberException();

        } else if (t instanceof OtherToken) {
            switch (t.getChar().getCodePoint()) {
                case '(':
                    // TODO gene: unimplemented
                    throw new RuntimeException("unimplemented");
                case '!':
                case UC_NOT:
                    EType ac =
                            evalLogicExpressionOrFunctionalExpression(context,
                                source, typesetter);
                    ac.not();
                    return ac;
                default:
                    // ...
            }
        }

        source.push(t);
        EType accumulator = evalTerm(context, source, typesetter);

        BinaryFunction op = getOp(context, source);
        if (op != null) {
            return op.apply(accumulator, evalTerm(context, source, typesetter));
        }
        // TODO gene: unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * Evaluate a logical expression.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private EType evalLogicExpressionOrFunctionalExpression(Context context,
            TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new MissingNumberException();

        } else if (t instanceof OtherToken) {
            switch (t.getChar().getCodePoint()) {
                case '!':
                case UC_NOT:
                    EType ac =
                            evalLogicExpressionOrFunctionalExpression(context,
                                source, typesetter);
                    ac.not();
                    return ac;
                default:
                    // ...
            }
        }

        source.push(t);
        EType accumulator = evalTerm(context, source, typesetter);

        BinaryFunction op = getOp(context, source);
        if (op != null) {
            return op.apply(accumulator, evalTerm(context, source, typesetter));
        }
        op = getJunctor(context, source);
        if (op != null) {
            return op.apply(accumulator,
                evalLogicExpressionOrFunctionalExpression(context, source,
                    typesetter));
        }
        return accumulator;
    }

    /**
     * Evaluate a terminal.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private EType evalTerm(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        for (Token t = source.getNonSpace(context); t != null; t =
                source.getNonSpace(context)) {

            if (t instanceof OtherToken) {
                switch (t.getChar().getCodePoint()) {
                    case '+':
                        // continue
                        break;
                    case '-':
                        EType accumulator =
                                evalTerm(context, source, typesetter);
                        accumulator = accumulator.negate();
                        return accumulator;
                    case '(':
                        return evalGroup(context, source, typesetter);

                    default:
                        source.push(t);
                        for (int i = 0; i < parsers.size(); i++) {
                            EType term =
                                    parsers.get(i).parse(context, source,
                                        typesetter);
                            if (term != null) {
                                return term;
                            }
                        }
                }
                break;

            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);

                for (int i = 0; i < parsers.size(); i++) {
                    EType term =
                            parsers.get(i).convert(code, context, source,
                                typesetter);
                    if (term != null) {
                        return term;
                    }
                }

                if (code instanceof ExpandableCode) {
                    try {
                        ((ExpandableCode) code).expand(Flags.NONE, context,
                            source, typesetter);
                    } catch (TypesetterException e) {
                        throw new NoHelpException(e);
                    }
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
                    break;
                }
                if (f instanceof ConstantFunction) {
                    return ((ConstantFunction) f).apply();
                }
                if (f instanceof UnaryFunction) {
                    return ((UnaryFunction) f).apply(evalTerm(context, source,
                        typesetter));
                }
                t = source.getNonSpace(context);
                if (t == null) {
                    throw new EofException();
                } else if (!t.eq(Catcode.OTHER, '(')) {
                    throw new HelpingException(LocalizerFactory
                        .getLocalizer(Evaluator.class),
                        "MissingOpenParenthesis", name, t.toString());
                }
                EType accumulator = new Accumulator();
                if (f instanceof BinaryFunction) {
                    EType arg1 = evalExpression(context, source, typesetter);
                    skipComma(context, source);
                    EType arg2 = evalExpression(context, source, typesetter);
                    accumulator = ((BinaryFunction) f).apply(arg1, arg2);
                } else if (f instanceof ParsingFunction) {
                    accumulator =
                            ((ParsingFunction) f).apply(context, source,
                                typesetter);
                } else {
                    break;
                }

                t = source.getNonSpace(context);
                if (t == null) {
                    throw new EofException();
                } else if (!t.eq(Catcode.OTHER, ')')) {
                    throw new HelpingException(LocalizerFactory
                        .getLocalizer(Evaluator.class), "MissingParenthesis", t
                        .toString());
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
     * Get an logical junction operator.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * 
     * @return the function constant associated to the operator found or
     *         <code>null</code> if none was found
     * 
     * @throws HelpingException in case of an error
     */
    private BinaryFunction getJunctor(Context context, TokenSource source)
            throws HelpingException {

        Token t = source.getNonSpace(context);
        Token t2;

        if (t instanceof OtherToken) {
            switch (t.getChar().getCodePoint()) {
                case UC_AND:
                    return LAND;
                case UC_OR:
                    return LOR;
                case '&':
                    t2 = source.getToken(context);
                    if (t2 != null && t2.eq(Catcode.OTHER, '&')) {
                        return LAND;
                    }
                    source.push(t2);
                    break;
                case '|':
                    t2 = source.getToken(context);
                    if (t2 == null || t2.eq(Catcode.OTHER, '|')) {
                        return LOR;
                    }
                    source.push(t2);
                    break;
                default:
                    // fall-through to report nothing
            }
        }
        source.push(t);
        return null;
    }

    /**
     * Get a certain junctor from the token stream.
     * 
     * @param junctor the junctor to look for
     * @param context the Helping context
     * @param source the source for new tokens
     * 
     * @return <code>true</code> iff the junctor has been found
     * 
     * @throws HelpingException in case of an error
     */
    private boolean getJunctor(BinaryFunction junctor, Context context,
            TokenSource source) throws HelpingException {

        Token t = source.getNonSpace(context);

        if (junctor == LAND) {

            if (t instanceof OtherToken) {
                switch (t.getChar().getCodePoint()) {
                    case UC_AND:
                        return true;
                    case '&':
                        Token t2 = source.getToken(context);
                        if (t2 != null && t2.eq(Catcode.OTHER, '&')) {
                            return true;
                        }
                        source.push(t2);
                        break;
                    default:
                        // fall-through to report nothing
                }
            }
            source.push(t);

        } else if (junctor == LOR) {

            if (t instanceof OtherToken) {
                switch (t.getChar().getCodePoint()) {
                    case UC_OR:
                        return true;
                    case '|':
                        Token t2 = source.getToken(context);
                        if (t2 != null && t2.eq(Catcode.OTHER, '|')) {
                            return true;
                        }
                        source.push(t2);
                        break;
                    default:
                        // fall-through to report nothing
                }
            }
            source.push(t);

        } else {
            // TODO gene: unimplemented
            throw new RuntimeException("unimplemented");
        }

        return false;
    }

    /**
     * Get an comparison operator.
     * 
     * @param context the Helping context
     * @param source the source for new tokens
     * 
     * @return the function constant associated to the operator found or
     *         <code>null</code> if none was found
     * 
     * @throws HelpingException in case of an error
     */
    private BinaryFunction getOp(Context context, TokenSource source)
            throws HelpingException {

        Token t = source.getNonSpace(context);

        if (t instanceof OtherToken) {
            switch (t.getChar().getCodePoint()) {
                case UC_LE:
                    return LE;
                case UC_GE:
                    return GE;
                case UC_NE:
                    return NE;
                case '!':
                    t = source.getToken(context);
                    if (t != null && t.eq(Catcode.OTHER, '=')) {
                        return NE;
                    }
                    break;
                case '=':
                    t = source.getToken(context);
                    if (t == null || !t.eq(Catcode.OTHER, '=')) {
                        source.push(t);
                    }
                    return EQ;
                case '<':
                    t = source.getToken(context);
                    if (t == null || !t.eq(Catcode.OTHER, '=')) {
                        source.push(t);
                        return LT;
                    }
                    return LE;
                case '>':
                    t = source.getToken(context);
                    if (t == null || !t.eq(Catcode.OTHER, '=')) {
                        source.push(t);
                        return GT;
                    }
                    return GE;
                default:
                    // fall-through to report nothing
            }
        }
        source.push(t);
        return null;
    }

    /**
     * Register a Terminal for usage. the registered instance is used to access
     * the parser and converter methods.
     * 
     * @param parser the terminal parser instance
     */
    public void register(ETypeParser parser) {

        parsers.add(parser);
        parser.registered(this);
    }

    /**
     * Register a function in the evaluator.
     * 
     * @param name the name of the function in the expression
     * @param function the function object
     */
    public void register(String name, ParsingFunction function) {

        functions.put(name, function);
    }

    /**
     * Register a constant in the evaluator.
     * 
     * @param name the name of the function in the expression
     * @param function the function object
     */
    public void register(String name, ConstantFunction function) {

        functions.put(name, function);
    }

    /**
     * Register a unary function in the evaluator.
     * 
     * @param name the name of the function in the expression
     * @param function the function object
     */
    public void register(String name, UnaryFunction function) {

        functions.put(name, function);
    }

    /**
     * Register a binary function in the evaluator.
     * 
     * @param name the name of the function in the expression
     * @param function the function object
     */
    public void register(String name, BinaryFunction function) {

        functions.put(name, function);
    }

}
