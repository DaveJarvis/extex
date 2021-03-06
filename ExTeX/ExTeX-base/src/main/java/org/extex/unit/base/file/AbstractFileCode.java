/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.base.file;

import org.extex.core.exception.helping.BadFileNumberException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.token.*;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.Relax;

/**
 * This abstract class provides some common methods for primitives dealing with
 * files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractFileCode extends AbstractCode
    implements
    Configurable {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2009L;

  /**
   * The field {@code DEFAULT_ENCODING} contains the default encoding if
   * nothing else is found.
   */
  private static final String DEFAULT_ENCODING = "ISO8859-1";

  /**
   * The constant {@code MAX_OUT_FILE_NO} contains the maximum number of
   * input files.
   */
  public static final int MAX_IN_FILE_NO = 15;

  /**
   * The constant {@code MAX_OUT_FILE_NO} contains the maximal number of
   * output files.
   */
  public static final int MAX_OUT_FILE_NO = 15;

  /**
   * Scan the input source for some tokens making up the key for an infile
   * register. Currently only numbers in a certain range are allowed.
   *
   *
   * <p>The Infile Name</p>
   * <p>
   * The infile name is a symbolic key to reference an input file. This is a
   * number in the range from 0 to 15.
   * </p>
   *
   * <p>Syntax</p>
   * The formal description of this primitive is the
   * following:
   *
   * <pre class="syntax">
   *    &lang;infile&nbsp;name&rang;
   *      &rarr; {@linkplain
   *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
   *        &lang;number&rang;} </pre>
   *
   * @param context    the interpreter context
   * @param source     the token source to read from
   * @param typesetter the typesetter
   * @return the key read in the form of a String
   * @throws HelpingException    in case of a failure
   * @throws TypesetterException in case of an error in the typesetter
   */
  public static String scanInFileKey( Context context, TokenSource source,
                                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long no = source.parseNumber( context, source, typesetter );
    String key = Long.toString( no );

    if( no < 0 || no > MAX_IN_FILE_NO ) {
      throw new BadFileNumberException( key,
                                        "0",
                                        Integer.toString( MAX_IN_FILE_NO ) );
    }

    return key;
  }

  /**
   * Scan the input source for some tokens making up the key for an outfile
   * register. Currently only numbers are allowed.
   *
   * <p>The Outfile Name</p>
   * <p>
   * The outfile name is a symbolic key to reference an output file. This is a
   * number in the range from 0 to 15.
   * </p>
   *
   * <p>Syntax</p>
   * The formal description of this primitive is the
   * following:
   *
   * <pre class="syntax">
   *    &lang;infile&nbsp;name&rang;
   *      &rarr; {@linkplain
   *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
   *        &lang;number&rang;} </pre>
   *
   * @param context    the interpreter context
   * @param source     the token source to read from
   * @param typesetter the typesetter
   * @return the key read in the form of a String
   * @throws HelpingException    in case of a failure
   * @throws TypesetterException in case of an error in the typesetter
   */
  public static String scanOutFileKey( Context context, TokenSource source,
                                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long no = source.parseNumber( context, source, typesetter );
    return Long.toString( no );
  }

  /**
   * The field {@code strictTeX} contains the boolean indicating whether or
   * not to adhere strictly to the rules of TeX for file name parsing.
   */
  private boolean strictTeX = false;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public AbstractFileCode( CodeToken token ) {

    super( token );
  }

  @Override
  public void configure( Configuration config ) {

    String strict = config.getAttribute( "strict" );
    strictTeX = (strict != null && Boolean.valueOf( strict ).booleanValue());
  }

  /**
   * Return the encoding for the AbstractFileCode file.
   * <p>
   * First of all, {@code \fileencoding} is used, if there is no value,
   * then the property {@code extex.encoding} is used, or
   * {@code ISO8859-1}, if no entry exists.
   *
   * @param context the context
   * @return the encoding for the AbstractFileCodefile
   */
  protected String getEncoding( Context context ) {

    String encoding = context.getToks( "fileencoding" ).toText().trim();
    if( encoding.length() == 0 ) {
      String enc = System.getProperty( "extex.encoding" );
      encoding = (enc != null ? enc : DEFAULT_ENCODING);
    }
    return encoding;
  }

  /**
   * Scan the file name.
   * <p>
   * This method parses the following
   * syntactic entity:
   *
   * <pre class="syntax">
   *   &lang;file name&rang; </pre>
   * <p>
   * The scanning is performed in one of two ways:
   * <ul>
   * <li>If the first token is a left brace then a block is read until the
   * matching right brace is found. On the way the tokens are expanded.</li>
   * <li>Otherwise tokens are read until a space token is encountered.</li>
   * </ul>
   *
   * @param context the processing context
   * @param source  the source for new tokens
   * @return the file name as string
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   */
  protected String scanFileName( Context context, TokenSource source )
      throws HelpingException,
      TypesetterException {

    Typesetter typesetter = null;
    Token t = source.scanNonSpace( context );

    if( t == null ) {
      throw new EofException( toText( context ) );
      // } else if (t.isa(Catcode.LEFTBRACE)) {
      // source.push(t);
      // String name =
      // source.scanTokensAsString(context,
      // printableControlSequence(context));
      // return name;
    }

    int depth = 0;
    StringBuilder sb = new StringBuilder();

    for( ; t != null && !(t instanceof SpaceToken); t =
        source.getToken( context ) ) {

      if( t instanceof CodeToken ) {
        Code code = context.getCode( (CodeToken) t );
        if( code instanceof ExpandableCode ) {
          ((ExpandableCode) code).expand( Flags.NONE, context, source,
                                          typesetter );
        }
        else {
          if( !(code instanceof Relax) ) {
            source.push( t );
          }
          break;
        }
      }
      else if( t instanceof LeftBraceToken ) {
        depth++;
      }
      else if( t instanceof RightBraceToken ) {
        depth--;
        if( depth <= 0 ) {
          if( depth < 0 ) {
            source.push( t );
            break;
          }
          if( sb.length() != 0 ) {
            break;
          }
        }
      }
      else {
        sb.append( t.toText() );
      }
    }

    return sb.toString();
  }
}
