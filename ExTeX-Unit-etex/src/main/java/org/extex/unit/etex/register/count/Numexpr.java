/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.count;

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.Relax;

/**
 * This class provides an implementation for the primitive <code>\numexpr</code>.
 * 
 * <doc name="numexpr">
 * <h3>The Primitive <tt>\numexpr</tt></h3>
 * <p>
 * The primitive <tt>\numexpr</tt> provides a means to use a inline way of
 * writing mathematical expressions to be evaluated. Mathematical expressions
 * can be evaluated in <logo>ExTeX</logo> using <tt>\advance</tt>,
 * <tt>\multiply</tt>, and <tt>\divide</tt>. Nevertheless those primitives
 * result in an assignment. This is not the case for <tt>\numexpr</tt>. Here
 * the intermediate results are not stored in count registers but kept
 * internally. Also the application of <tt>\afterassignment</tt> and
 * <tt>\tracingassigns</tt> is suppressed.
 * </p>
 * <p>
 * The mathematical expression to be evaluated can be made up of the basic
 * operations addition (+), subtraction (-), multiplication (*), and
 * division(/). The unary minus can be used. Parentheses can be used for
 * grouping. Anything which looks like a number can be used as argument.
 * White-space can be used freely without any harm.
 * </p>
 * <p>
 * The expression is terminated at the first token which can not be part of an
 * expression. For instance a letter may signal the end of the expression. If
 * the expression should terminate without a proper token following it, the
 * token <tt>\relax</tt> can be used to signal the end of the expression. This
 * <tt>\relax</tt> token is silently consumed by <tt>\numexpr</tt>.
 * </p>
 * <p>
 * The primitive <tt>\numexpr</tt> can be used in any place where a number is
 * required. This includes assignments to count registers and comparisons.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;numexpr&rang;
 *      &rarr; <tt>\numexpr</tt> &lang;expr&rang; <tt>\relax</tt>
 *      |   <tt>\numexpr</tt> &lang;expr&rang;
 *
 *    &lang;expr&rang;
 *      &rarr; &lang;number&rang;
 *      |   &lang;operand&rang;
 *      |   &lang;expr&rang; <tt>+</tt> &lang;expr&rang;
 *      |   &lang;expr&rang; <tt>-</tt> &lang;expr&rang;
 *      |   &lang;expr&rang; <tt>*</tt> &lang;expr&rang;
 *      |   &lang;expr&rang; <tt>/</tt> &lang;expr&rang;
 *
 *    &lang;operand&rang;
 *      &rarr; &lang;number&rang;
 *      |   <tt>-</tt> &lang;expr&rang;
 *      |   <tt>(</tt> &lang;expr&rang; <tt>)</tt>   </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *   \count1=\numexpr 23 \relax </pre>
 * <pre class="TeXSample">
 *   \count1=\numexpr 2 * 3 \relax </pre>
 * <pre class="TeXSample">
 *   \count1=\numexpr 2*\count2  </pre>
 * <pre class="TeXSample">
 *   \count1=\numexpr 2*(1+3)  </pre>
 * <pre class="TeXSample">
 *   \count1=\numexpr 2*-\count0  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Numexpr extends AbstractCode implements CountConvertible, Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * This interface describes a binary operation on two longs.
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
     * This operation ignores the first argument and returns the second one.
     */
    private static final class Second implements BinOp {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.unit.etex.register.count.Numexpr.BinOp#apply(long,
         *      long)
         */
        public long apply(long arg1, long arg2) {

            return arg2;
        }
    }

    /**
     * This operation adds the arguments.
     */
    private static final class Plus implements BinOp {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.unit.etex.register.count.Numexpr.BinOp#apply(long,
         *      long)
         */
        public long apply(long arg1, long arg2) {

            return arg1 + arg2;
        }
    }

    /**
     * This operation subtracts the second argument from the first one.
     */
    private static final class Minus implements BinOp {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.unit.etex.register.count.Numexpr.BinOp#apply(long,
         *      long)
         */
        public long apply(long arg1, long arg2) {

            return arg1 - arg2;
        }
    }

    /**
     * The field <tt>SECOND</tt> contains the operation to select the second
     * argument.
     */
    private static final BinOp SECOND = new Second();

    /**
     * The field <tt>PLUS</tt> contains the adder.
     */
    private static final BinOp PLUS = new Plus();

    /**
     * The field <tt>MINUS</tt> contains the subtractor.
     */
    private static final BinOp MINUS = new Minus();

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Numexpr(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(org.extex.interpreter.context.Context,
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
    private long evalExpr(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long saveVal = 0;
        BinOp op = SECOND;
        long val = evalOperand(context, source, typesetter);

        for (;;) {

            Token t = source.getNonSpace(context);
            if (t == null) {
                throw new EofException(getName());

            } else if (t.equals(Catcode.OTHER, '*')) {
                val *= evalOperand(context, source, typesetter);

            } else if (t.equals(Catcode.OTHER, '/')) {
                long x = evalOperand(context, source, typesetter);
                if (x == 0) {
                    throw new ArithmeticOverflowException(getName());
                }
                val /= x;

            } else if (t.equals(Catcode.OTHER, '+')) {
                saveVal = op.apply(saveVal, val);
                val = evalOperand(context, source, typesetter);
                op = PLUS;

            } else if (t.equals(Catcode.OTHER, '-')) {
                saveVal = op.apply(saveVal, val);
                val = evalOperand(context, source, typesetter);
                op = MINUS;

            } else {
                source.push(t);
                return op.apply(saveVal, val);
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
     */
    public long evalOperand(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new EofException(getName());

        } else if (t.equals(Catcode.OTHER, '(')) {
            long val = evalExpr(context, source, typesetter);
            t = source.getToken(context);
            if (t != null && t.equals(Catcode.OTHER, ')')) {
                return val;
            }

            throw new HelpingException(getLocalizer(), "MissingParenthesis",
                (t == null ? "null" : t.toString()));

        } else if (t.equals(Catcode.OTHER, '-')) {
            long val = evalOperand(context, source, typesetter);
            return -val;

        }

        source.push(t);
        return source.parseNumber(context, source, typesetter);
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     * 
     * @return the description of the primitive as list of Tokens
     * @throws CatcodeException in case of an error in token creation
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        return context.getTokenFactory().toTokens( //
            convertCount(context, source, typesetter));
    }
}
