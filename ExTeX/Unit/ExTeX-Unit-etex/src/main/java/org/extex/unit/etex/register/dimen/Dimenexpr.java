/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.dimen;

import org.extex.base.parser.ConstantCountParser;
import org.extex.base.parser.ConstantDimenParser;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingNumberException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.Relax;

/**
 * This class provides an implementation for the primitive
 * {@code \dimenexpr}.
 * 
 * <p>The Primitive {@code \dimenexpr}</p>
 * <p>
 * The primitive {@code \dimenexpr} provides a means to use a inline way of
 * writing mathematical expressions to be evaluated. Mathematical expressions
 * can be evaluated in εχTeX using {@code \advance},
 * {@code \multiply}, and {@code \divide}. Nevertheless those primitives
 * result in an assignment. This is not the case for {@code \dimenexpr}.
 * Here the intermediate results are not stored in dimen registers but kept
 * internally. Also the application of {@code \afterassignment} and
 * {@code \tracingassigns} is suppressed.
 * </p>
 * <p>
 * The mathematical expression to be evaluated can be made up of the basic
 * operations addition (+), subtraction (-), multiplication (*) with numbers,
 * and division(/) by numbers. The unary minus can be used. Parentheses can be
 * used for grouping. Anything which looks like a length can be used as
 * argument. White-space can be used freely without any harm.
 * </p>
 * <p>
 * The expression is terminated at the first token which can not be part of an
 * expression. For instance a letter may signal the end of the expression. If
 * the expression should terminate without a proper token following it, the
 * token {@code \relax} can be used to signal the end of the expression. This
 * {@code \relax} token is silently consumed by {@code \dimenexpr}.
 * </p>
 * <p>
 * The primitive {@code \dimenexpr} can be used in any place where a dimen is
 * required. This includes assignments to dimen registers and comparisons.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;dimenexpr&rang;
 *      &rarr; {@code \dimenexpr} &lang;expr&rang; {@code \relax}
 *      |   {@code \dimenexpr} &lang;expr&rang;
 *
 *    &lang;expr&rang;
 *      &rarr; &lang;operand&rang;
 *      |   &lang;operand&rang; {@code +} &lang;expr&rang;
 *      |   &lang;operand&rang; {@code -} &lang;expr&rang;
 *
 *    &lang;operand&rang;
 *      &rarr; &lang;dimen&rang;
 *      |   &lang;operand&rang; {@code *} &lang;number&rang;
 *      |   &lang;number&rang; {@code *} &lang;operand&rang;
 *      |   &lang;operand&rang; {@code /} &lang;number&rang;
 *      |   {@code -} &lang;expr&rang;
 *      |   {@code (} &lang;expr&rang; {@code )}   </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   \count1=\dimenexpr 23pt \relax </pre>
 * <pre class="TeXSample">
 *   \count1=\dimenexpr 2 * 3pt \relax </pre>
 * <pre class="TeXSample">
 *   \count1=\dimenexpr 2pt*\count2  </pre>
 * <pre class="TeXSample">
 *   \count1=\dimenexpr 2*(1pt+3em)  </pre>
 * <pre class="TeXSample">
 *   \count1=\dimenexpr 2*-\dimen0  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Dimenexpr extends AbstractCode
        implements
            DimenConvertible,
            CountConvertible,
            Theable {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Dimenexpr(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long result = evalExpr(context, source, typesetter);
        Token t = source.getToken(context);
        if (!(t instanceof CodeToken)
                || !(context.getCode((CodeToken) t) instanceof Relax)) {
            source.push(t);
        }
        return result;
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertDimen(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long result = evalExpr(context, source, typesetter);
        Token t = source.getToken(context);
        if (!(t instanceof CodeToken)
                || !(context.getCode((CodeToken) t) instanceof Relax)) {
            source.push(t);
        }
        return result;
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
     * @throws ConfigurationException in case of an configuration error
     */
    private long evalExpr(Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        long val = evalOperand(context, source, typesetter);

        for (;;) {

            Token t = source.getNonSpace(context);
            if (t == null) {
                throw new EofException(toText());

            } else if (t.eq(Catcode.OTHER, '+')) {
                val += evalOperand(context, source, typesetter);
            } else if (t.eq(Catcode.OTHER, '-')) {
                val -= evalOperand(context, source, typesetter);

            } else {
                source.push(t);
                return val;
            }
        }
    }

    /**
     * Evaluate an operand.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the result
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * @throws ConfigurationException in case of an configuration error
     */
    private long evalOperand(Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        long val =
                evalTerminal(context, source, typesetter, source
                    .getNonSpace(context));

        for (Token t = source.getNonSpace(context); t != null; t =
                source.getNonSpace(context)) {

            if (t.eq(Catcode.OTHER, '*')) {
                val *= evalOperand(context, source, typesetter);
            } else if (t.eq(Catcode.OTHER, '/')) {
                long x = evalOperand(context, source, typesetter);
                if (x == 0) {
                    throw new ArithmeticOverflowException(toText());
                }
                val /= x;
            } else {
                source.push(t);
                return val;
            }
        }
        return val;
    }

    /**
     * Evaluate a terminal symbol.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param start the first token to start with
     * 
     * @return the value
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * @throws ConfigurationException in case of an configuration error
     */
    private long evalTerminal(Context context, TokenSource source,
            Typesetter typesetter, Token start)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        Token t = start;
        for (;;) {
            if (t instanceof OtherToken) {
                if (t.eq(Catcode.OTHER, '(')) {
                    long val = evalExpr(context, source, typesetter);
                    t = source.getToken(context);
                    if (t != null && t.eq(Catcode.OTHER, ')')) {
                        return val;
                    }
                    throw new HelpingException(getLocalizer(),
                        "MissingParenthesis", (t == null ? "null" : t
                            .toString()));
                } else if (t.eq(Catcode.OTHER, '-')) {
                    return -evalOperand(context, source, typesetter);
                } else if (t.eq(Catcode.OTHER, '.')
                        || t.eq(Catcode.OTHER, ',')) {
                    source.push(t);
                    return ConstantDimenParser
                        .scan(context, source, typesetter).getValue();
                }
                source.push(t);
                long pre = source.parseNumber(context, source, typesetter);
                t = source.getToken(context);
                if (t == null) {
                    return pre;
                } else if (t.eq(Catcode.OTHER, '.')
                        || t.eq(Catcode.OTHER, ',')) {
                    source.push(t);
                    try {
                        source.push(context.getTokenFactory().toTokens(pre));
                    } catch (CatcodeException e) {
                        throw new NoHelpException(e);
                    }
                    return ConstantDimenParser
                        .scan(context, source, typesetter).getValue();
                }
                source.push(t);
                t = source.getNonSpace(context);
                if (t == null) {
                    throw new MissingNumberException();

                } else if (t instanceof LetterToken) {
                    source.push(t);
                    try {
                        source.push(context.getTokenFactory().toTokens(pre));
                    } catch (CatcodeException e) {
                        throw new NoHelpException(e);
                    }
                    return ConstantDimenParser
                        .scan(context, source, typesetter).getValue();
                } else {
                    source.push(t);
                    try {
                        source.push(context.getTokenFactory().toTokens(pre));
                    } catch (CatcodeException e) {
                        throw new NoHelpException(e);
                    }
                    return ConstantCountParser.scanNumber(context, source,
                        typesetter);
                }

            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof DimenConvertible) {
                    return ((DimenConvertible) code).convertDimen(context,
                        source, typesetter);
                } else if (code instanceof CountConvertible) {
                    return ((CountConvertible) code).convertCount(context,
                        source, typesetter);
                } else if (code instanceof ExpandableCode) {
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);
                } else {
                    throw new MissingNumberException();
                }

            } else {
                throw new MissingNumberException();
            }
        }
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException,
                CatcodeException {

        Dimen d = new Dimen(convertDimen(context, source, typesetter));
        return context.getTokenFactory().toTokens(d.toString());
    }

}
