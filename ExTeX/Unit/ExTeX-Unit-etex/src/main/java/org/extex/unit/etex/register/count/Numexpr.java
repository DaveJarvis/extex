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

package org.extex.unit.etex.register.count;

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.Relax;

/**
 * This class provides an implementation for the primitive {@code \numexpr}
 * .
 *
 * <p>The Primitive {@code \numexpr}</p>
 * <p>
 * The primitive {@code \numexpr} provides a means to use a inline way of
 * writing mathematical expressions to be evaluated. Mathematical expressions
 * can be evaluated in εχTeX using {@code \advance}, {@code \multiply}, and
 * {@code \divide}. Nevertheless those primitives result in an assignment. This
 * is not the case for {@code \numexpr}. Here the intermediate results are not
 * stored in count registers but kept internally. Also the application of
 * {@code \afterassignment} and {@code \tracingassigns} is suppressed.
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
 * token {@code \relax} can be used to signal the end of the expression. This
 * {@code \relax} token is silently consumed by {@code \numexpr}.
 * </p>
 * <p>
 * The primitive {@code \numexpr} can be used in any place where a number is
 * required. This includes assignments to count registers and comparisons.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;numexpr&rang;
 *      &rarr; {@code \numexpr} &lang;expr&rang; {@code \relax}
 *      |   {@code \numexpr} &lang;expr&rang;
 *
 *    &lang;expr&rang;
 *      &rarr; &lang;number&rang;
 *      |   &lang;operand&rang;
 *      |   &lang;expr&rang; {@code +} &lang;expr&rang;
 *      |   &lang;expr&rang; {@code -} &lang;expr&rang;
 *      |   &lang;expr&rang; {@code *} &lang;expr&rang;
 *      |   &lang;expr&rang; {@code /} &lang;expr&rang;
 *
 *    &lang;operand&rang;
 *      &rarr; &lang;number&rang;
 *      |   {@code -} &lang;expr&rang;
 *      |   {@code (} &lang;expr&rang; {@code )}   </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *   \count1=\numexpr 23 \relax </pre>
 *
 * <pre class="TeXSample">
 *   \count1=\numexpr 2 * 3 \relax </pre>
 *
 * <pre class="TeXSample">
 *   \count1=\numexpr 2*\count2  </pre>
 *
 * <pre class="TeXSample">
 *   \count1=\numexpr 2*(1+3)  </pre>
 *
 * <pre class="TeXSample">
 *   \count1=\numexpr 2*-\count0  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Numexpr extends AbstractCode implements CountConvertible, Theable {

  /**
   * This interface describes a binary operation on two longs.
   */
  private interface BinOp {

    /**
     * Apply the operation on the arguments.
     *
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @return the result
     */
    long apply( long arg1, long arg2 );
  }

  /**
   * This operation subtracts the second argument from the first one.
   */
  private static final class Minus implements BinOp {

    /**
     * long)
     */
    @Override
    public long apply( long arg1, long arg2 ) {

      return arg1 - arg2;
    }
  }

  /**
   * This operation adds the arguments.
   */
  private static final class Plus implements BinOp {

    /**
     * long)
     */
    @Override
    public long apply( long arg1, long arg2 ) {

      return arg1 + arg2;
    }
  }

  /**
   * This operation ignores the first argument and returns the second one.
   */
  private static final class Second implements BinOp {

    /**
     * long)
     */
    @Override
    public long apply( long arg1, long arg2 ) {

      return arg2;
    }
  }

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code SECOND} contains the operation to select the second
   * argument.
   */
  private static final BinOp SECOND = new Second();

  /**
   * The field {@code PLUS} contains the adder.
   */
  private static final BinOp PLUS = new Plus();

  /**
   * The field {@code MINUS} contains the subtractor.
   */
  private static final BinOp MINUS = new Minus();

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Numexpr( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long result = evalExpr( context, source, typesetter );
    Token t = source.getToken( context );
    if( !(t instanceof CodeToken)
        || !(context.getCode( (CodeToken) t ) instanceof Relax) ) {
      source.push( t );
    }
    return result;
  }

  /**
   * Evaluate an expression.
   *
   * @param context    the interpreter context
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @return the result
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   */
  private long evalExpr( Context context, TokenSource source,
                         Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long saveVal = 0;
    BinOp op = SECOND;
    long val = evalOperand( context, source, typesetter );

    for( ; ; ) {

      Token t = source.getNonSpace( context );
      if( t == null ) {
        throw new EofException( toText() );

      }
      else if( t.eq( Catcode.OTHER, '*' ) ) {
        val *= evalOperand( context, source, typesetter );

      }
      else if( t.eq( Catcode.OTHER, '/' ) ) {
        long x = evalOperand( context, source, typesetter );
        if( x == 0 ) {
          throw new ArithmeticOverflowException( toText() );
        }
        val /= x;

      }
      else if( t.eq( Catcode.OTHER, '+' ) ) {
        saveVal = op.apply( saveVal, val );
        val = evalOperand( context, source, typesetter );
        op = PLUS;

      }
      else if( t.eq( Catcode.OTHER, '-' ) ) {
        saveVal = op.apply( saveVal, val );
        val = evalOperand( context, source, typesetter );
        op = MINUS;

      }
      else {
        source.push( t );
        return op.apply( saveVal, val );
      }
    }
  }

  /**
   * Evaluate an operand.
   *
   * @param context    the interpreter context
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @return the result
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   */
  public long evalOperand( Context context, TokenSource source,
                           Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Token t = source.getNonSpace( context );
    if( t == null ) {
      throw new EofException( toText() );

    }
    else if( t.eq( Catcode.OTHER, '(' ) ) {
      long val = evalExpr( context, source, typesetter );
      t = source.getToken( context );
      if( t != null && t.eq( Catcode.OTHER, ')' ) ) {
        return val;
      }

      throw new HelpingException( getLocalizer(), "MissingParenthesis",
                                  (t == null ? "null" : t.toString()) );

    }
    else if( t.eq( Catcode.OTHER, '-' ) ) {
      long val = evalOperand( context, source, typesetter );
      return -val;

    }

    source.push( t );
    return source.parseNumber( context, source, typesetter );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws CatcodeException,
      HelpingException,
      TypesetterException {

    return context.getTokenFactory().toTokens(
        convertCount( context, source, typesetter ) );
  }

}
