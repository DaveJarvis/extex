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

package org.extex.unit.tex.register;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.InvalidCodeException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \catcode}
 * .
 *
 * <p>The Primitive {@code \catcode}</p>
 * <p>
 * The primitive {@code \catcode} can be used to influence the tokenizer of
 * εχTeX. This is done by assigning category codes to single
 * characters. Whenever characters are read tokens are generated and passed on.
 * Those tokens carry the category code into the interpreter. The interpreter
 * considers always tokens, i.e. characters and category codes. Thus the same
 * character can be treated differently depending on the catcode of the token.
 * </p>
 * <p>
 * The assignment of a catcode for a character is controlled by the prefix
 * primitive {@code \global} and the count parameter {@code \globaldefs}.
 * Usually the assignment is acting on the current group only. If the count
 * parameter {@code \globaldefs} is greater than 0 or the prefix
 * {@code \global} is given then the assignment is applied to all groups.
 * </p>
 * <p>
 * The following table contains the category codes with their meaning and the
 * mapping to numerical values.
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td>ESCAPE</td>
 * <td>0</td>
 * <td>This character code signals the beginning of an escape sequence. The
 * following letters are absorbed into the name. If the following token is not a
 * letter then only this single character is absorbed.</td>
 * </tr>
 * <tr>
 * <td>LEFTBRACE</td>
 * <td>1</td>
 * <td>This character is a left brace. It is used for grouping.</td>
 * </tr>
 * <tr>
 * <td>RIGHTBRACE</td>
 * <td>2</td>
 * <td>This character is a right brace. It is used for grouping.</td>
 * </tr>
 * <tr>
 * <td>MATHSHIFT</td>
 * <td>3</td>
 * <td>This character is used to switch to math mode. A single one switches to
 * inline math mode and a double one to display math mode.</td>
 * </tr>
 * <tr>
 * <td>TABMARK</td>
 * <td>4</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>CR</td>
 * <td>5</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>MACROPARAM</td>
 * <td>6</td>
 * <td>This character is a macro parameter. It is meaningful under certain
 * circumstances only.</td>
 * </tr>
 * <tr>
 * <td>SUPMARK</td>
 * <td>7</td>
 * <td>This character is an indicator the the following material should be
 * typeset as superscript. It is meaningful in math mode only. This character
 * class is used to parse characters from their code point as well.</td>
 * </tr>
 * <tr>
 * <td>SUBMARK</td>
 * <td>8</td>
 * <td>This character is an indicator the the following material should be
 * typeset as subscript. It is meaningful in math mode only.</td>
 * </tr>
 * <tr>
 * <td>IGNORE</td>
 * <td>9</td>
 * <td>This character is ignored during paring. It is dropped silently.</td>
 * </tr>
 * <tr>
 * <td>SPACE</td>
 * <td>10</td>
 * <td>This character is a white-space character. It is treated as if a simple
 * space has been found. Under some circumstances it is ignored.</td>
 * </tr>
 * <tr>
 * <td>LETTER</td>
 * <td>11</td>
 * <td>This character is a letter. As such it can be part of an escape sequence.
 * </td>
 * </tr>
 * <tr>
 * <td>OTHER</td>
 * <td>12</td>
 * <td>This character is another simple character which is not a letter.</td>
 * </tr>
 * <tr>
 * <td>ACTIVE</td>
 * <td>13</td>
 * <td>This character is an active character. This means that some code could be
 * bound to it. Active characters do not need the leading escape symbol like
 * escape sequences.</td>
 * </tr>
 * <tr>
 * <td>COMMENT</td>
 * <td>14</td>
 * <td>This character signals the beginning of a comment. The comment reaches to
 * the end of the current line.</td>
 * </tr>
 * <tr>
 * <td>INVALID</td>
 * <td>15</td>
 * <td>This character is invalid and will be dropped.</td>
 * </tr>
 * </table>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;catcode&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \catcode} {@linkplain
 *          org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *          &lang;8-bit&nbsp;number&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *          &lang;equals&rang;} {@linkplain
 *          org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *          &lang;4-bit&nbsp;number&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  &lang;global&rang; &lang;optional prefix&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \catcode `\%=12  </pre>
 *
 * <pre class="TeXSample">
 *    \global\catcode `\%=11  </pre>
 *
 * <p>{@code \catcode} as a Count Value</p>
 *
 *
 * <p>
 * {@code \catcode} can be used wherever a count value is required.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class CatcodePrimitive extends AbstractAssignment
    implements
    CountConvertible,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public CatcodePrimitive( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    source.getOptionalEquals( context );
    long ccNumber = source.parseInteger( context, source, typesetter );

    try {
      context.setCatcode( charCode, Catcode.valueOf( (int) ccNumber ),
                          prefix.clearGlobal() );
    } catch( CatcodeException e ) {
      throw new InvalidCodeException( Long.toString( ccNumber ),
                                      Integer.toString( Catcode.getCatcodeMax() ) );
    }

  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );

    return context.getCatcode( charCode ).getCode();
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
