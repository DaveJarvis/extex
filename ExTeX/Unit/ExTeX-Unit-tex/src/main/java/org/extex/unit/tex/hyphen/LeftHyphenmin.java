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

package org.extex.unit.tex.hyphen;

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \lefthyphenmin}.
 *
 * <p>The Primitive {@code \lefthyphenmin}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 *
 *
 * <pre class="syntax">
 *    &lang;lefthyphenmin&rang;
 *      &rarr; {@code \lefthyphenmin} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;number&rang;}  </pre>
 *
 * <p>Example:</p>
 *
 *
 * <pre class="TeXSample">
 *   \lefthyphenmin=3 </pre>
 * <p>
 * <p>
 * <p>
 * The value are stored in the {@code HyphernationTable}. Each
 * {@code HyphernationTable} are based on {@code \language} and have
 * its own {@code \lefthyphenmin} value (different to original TeX).
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class LeftHyphenmin extends AbstractHyphenationCode
    implements
    CountConvertible,
    DimenConvertible,
    Advanceable,
    Multiplyable,
    Divideable,
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
  public LeftHyphenmin( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void advance( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long globaldef = context.getCount( "globaldefs" ).getValue();
    if( globaldef > 0 ) {
      prefix.setGlobal( true );
    }

    Language language = getHyphenationTable( context );
    source.getKeyword( context, "by" );
    long lefthyphenmin;
    try {
      lefthyphenmin = language.getLeftHyphenMin();
    } catch( HyphenationException e ) {
      throw new NoHelpException( e );
    }
    lefthyphenmin += source.parseInteger( context, source, typesetter );

    try {
      language.setLeftHyphenMin( lefthyphenmin );
    } catch( HyphenationException e ) {
      if( e.getCause() instanceof ConfigurationException ) {
        throw (ConfigurationException) e.getCause();
      }
      throw new NoHelpException( e );
    }

    Token afterassignment = context.getAfterassignment();
    if( afterassignment != null ) {
      context.setAfterassignment( null );
      source.push( afterassignment );
    }
    prefix.clearGlobal(); // gene: not really useful but a little bit of
    // compatibility
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    try {
      return getHyphenationTable( context ).getLeftHyphenMin();
    } catch( HyphenationException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertDimen( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    try {
      return getHyphenationTable( context ).getLeftHyphenMin();
    } catch( HyphenationException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void divide( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long globaldef = context.getCount( "globaldefs" ).getValue();
    if( globaldef > 0 ) {
      prefix.setGlobal( true );
    }

    Language language = getHyphenationTable( context );
    source.getKeyword( context, "by" );
    long lefthyphenmin;
    try {
      lefthyphenmin = language.getLeftHyphenMin();
    } catch( HyphenationException e ) {
      throw new NoHelpException( e );
    }
    long arg = source.parseInteger( context, source, typesetter );
    if( arg == 0 ) {
      throw new ArithmeticOverflowException( toText( context ) );
    }
    lefthyphenmin /= arg;

    try {
      language.setLeftHyphenMin( lefthyphenmin );
    } catch( HyphenationException e ) {
      if( e.getCause() instanceof ConfigurationException ) {
        throw (ConfigurationException) e.getCause();
      }
      throw new NoHelpException( e );
    }

    Token afterassignment = context.getAfterassignment();
    if( afterassignment != null ) {
      context.setAfterassignment( null );
      source.push( afterassignment );
    }
    prefix.clearGlobal(); // gene: not really useful but a little bit of
    // compatibility
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long globaldef = context.getCount( "globaldefs" ).getValue();
    if( globaldef > 0 ) {
      prefix.setGlobal( true );
    }

    Language language = getHyphenationTable( context );
    source.getOptionalEquals( context );
    long lefthyphenmin = source.parseInteger( context, source, typesetter );

    try {
      language.setLeftHyphenMin( lefthyphenmin );
    } catch( HyphenationException e ) {
      if( e.getCause() instanceof ConfigurationException ) {
        throw (ConfigurationException) e.getCause();
      }
      throw new NoHelpException( e );
    }

    Token afterassignment = context.getAfterassignment();
    if( afterassignment != null ) {
      context.setAfterassignment( null );
      source.push( afterassignment );
    }
    prefix.clearGlobal(); // gene: not really useful but a little bit of
    // compatibility
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void multiply( Flags prefix, Context context, TokenSource source,
                        Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long globaldef = context.getCount( "globaldefs" ).getValue();
    if( globaldef > 0 ) {
      prefix.setGlobal( true );
    }

    Language language = getHyphenationTable( context );
    source.getKeyword( context, "by" );
    try {
      long lefthyphenmin = language.getLeftHyphenMin();
      lefthyphenmin *= source.parseInteger( context, source, typesetter );
      language.setLeftHyphenMin( lefthyphenmin );
    } catch( HyphenationException e ) {
      if( e.getCause() instanceof ConfigurationException ) {
        throw (ConfigurationException) e.getCause();
      }
      throw new NoHelpException( e );
    }

    Token afterassignment = context.getAfterassignment();
    if( afterassignment != null ) {
      context.setAfterassignment( null );
      source.push( afterassignment );
    }
    prefix.clearGlobal(); // gene: not really useful but a little bit of
    // compatibility
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

    Language language = getHyphenationTable( context );
    try {
      return context.getTokenFactory().toTokens(
          language.getLeftHyphenMin() );
    } catch( HyphenationException e ) {
      if( e.getCause() instanceof ConfigurationException ) {
        throw (ConfigurationException) e.getCause();
      }
      throw new NoHelpException( e );
    }
  }

}
