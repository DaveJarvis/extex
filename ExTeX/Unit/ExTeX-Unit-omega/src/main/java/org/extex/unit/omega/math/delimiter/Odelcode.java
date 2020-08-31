/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.math.delimiter;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.math.MathDelimiter;

/**
 * This class provides an implementation for the primitive
 * {@code \odelcode}.
 *
 * <p>The Math Primitive {@code \odelcode}</p>
 * <p>
 * The primitive {@code \odelcode} can be used to assign and query the
 * delimiter code for a character. The delimiter code determines, how a
 * character is typeset in math mode.
 * </p>
 * <p>
 * The TeX encoding interprets the number as 27 bit hex number:
 * {@code "csyylxx}. Here the digits have the following meaning:
 * </p>
 * <dl>
 * <dt>c</dt>
 * <dd>the math class of this delimiter. It has a range from 0 to 7.</dd>
 * <dt>l</dt>
 * <dd>the family for the large character. It has a range from 0 to 15.</dd>
 * <dt>xx</dt>
 * <dd>the character code of the large character.</dd>
 * <dt>s</dt>
 * <dd>the family for the small character. It has a range from 0 to 15.</dd>
 * <dt>yy</dt>
 * <dd>the character code of the small character.</dd>
 * </dl>
 *
 * <p>
 * The assigning a new value to a delimiter code acts in a group restricted way
 * unless declared differently. If the prefix {@code \global} is given then the
 * assignment is performed globally. The same effect can be achieved when the
 * count register {@code \globaldefs} is greater than 0.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;odelcode&rang;
 *      &rarr; &lang;prefix&rang; {@code \odelcode} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;8-bit&nbsp;number&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;8-bit&nbsp;number&rang;}
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       |  &lang;global&rang; </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \odelcode`x="123456  </pre>
 *
 * <pre class="TeXSample">
 *    \global\odelcode`x="123456  </pre>
 *
 * <p>Using as Count Register</p>
 *
 * <p>
 * The primitive {@code \odelcode} can be used like a count register. This
 * means you can use it wherever a number is expected. In addition the value can
 * be advanced, multiplied, and divided. In any case the delimiter code is
 * translated according to the TeX encoding and processed as number.
 * </p>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \count1=\odelcode`x  </pre>
 *
 * <pre class="TeXSample">
 *    \advance\odelcode`x by 42  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Odelcode extends AbstractAssignment
    implements
    CountConvertible,
    Advanceable,
    Divideable,
    Multiplyable,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Odelcode( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void advance( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    source.getKeyword( context, "by" );

    long value = source.parseInteger( context, source, null );
    MathDelimiter delcode = context.getDelcode( charCode );
    value += AbstractOmegaDelimiter.delimiterToLong( delcode );

    assign( prefix, context, source, charCode, value );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws ConfigurationException,
      HelpingException,
      TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    source.getOptionalEquals( context );
    MathDelimiter del =
        AbstractOmegaDelimiter.parseDelimiter( context, source,
                                               typesetter, getToken() );
    context.setDelcode( charCode, del, prefix.clearGlobal() );
  }

  /**
   * Perform an assignment of a delimiter code.
   *
   * @param prefix   the prefix indicator
   * @param context  the interpreter context
   * @param source   the token source
   * @param charCode the character to assign the delimiter code to
   * @param value    the delimiter code in TeX encoding
   * @throws HelpingException in case of an error
   */
  private void assign( Flags prefix, Context context, TokenSource source,
                       UnicodeChar charCode, long value )
      throws HelpingException {

    long globaldef = context.getCount( "globaldefs" ).getValue();
    if( globaldef != 0 ) {
      prefix.setGlobal( (globaldef > 0) );
    }

    context.setDelcode( charCode,
                        AbstractOmegaDelimiter.newMathDelimiter( value ),
                        prefix.clearGlobal() );

    Token afterassignment = context.getAfterassignment();
    if( afterassignment != null ) {
      context.setAfterassignment( null );
      source.push( afterassignment );
    }
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    MathDelimiter delcode = context.getDelcode( charCode );
    return AbstractOmegaDelimiter.delimiterToLong( delcode );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void divide( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    source.getKeyword( context, "by" );

    long value = source.parseInteger( context, source, null );
    MathDelimiter delcode = context.getDelcode( charCode );
    if( value == 0 ) {
      throw new ArithmeticOverflowException( toText( context ) );
    }

    value = AbstractOmegaDelimiter.delimiterToLong( delcode ) / value;
    assign( prefix, context, source, charCode, value );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void multiply( Flags prefix, Context context, TokenSource source,
                        Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    source.getKeyword( context, "by" );

    long value = source.parseInteger( context, source, null );
    MathDelimiter delcode = context.getDelcode( charCode );
    value *= AbstractOmegaDelimiter.delimiterToLong( delcode );
    assign( prefix, context, source, charCode, value );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws CatcodeException,
      HelpingException,
      TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    MathDelimiter delcode = context.getDelcode( charCode );
    long value = AbstractOmegaDelimiter.delimiterToLong( delcode );
    return context.getTokenFactory().toTokens( value );
  }

}
