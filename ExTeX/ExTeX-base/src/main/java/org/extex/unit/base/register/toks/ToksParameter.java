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

package org.extex.unit.base.register.toks;

import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.InitializableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \toks}.
 * It sets the numbered toks register to the value given, and as a side effect
 * all prefixes are zeroed.
 * <p>
 * Example:
 *
 * <pre>
 *     \toks12{123}
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 */
public class ToksParameter extends AbstractToks
    implements
    TokensConvertible,
    Theable,
    InitializableCode,
    Configurable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * Return the key for a named toks register.
   *
   * @param name    the name of the register
   * @param context the interpreter context to use
   * @return the key for the toks register
   */
  public static String getKey( String name, Context context ) {

    if( Namespace.SUPPORT_NAMESPACE_TOKS ) {
      return context.getNamespace() + "\b" + name;
    }
    return name;
  }

  /**
   * The field {@code key} contains the key.
   */
  private String key;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public ToksParameter( CodeToken token ) {

    super( token );
    key = token.getName();
  }

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   * @param key   the key
   */
  public ToksParameter( CodeToken token, String key ) {

    super( token );
    this.key = key;
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String k = getKey( context, source, typesetter );
    source.getOptionalEquals( context );
    Tokens tokens;
    try {
      tokens = source.getTokens( context, source, typesetter );
    } catch( EofException e ) {
      throw new EofInToksException( toText() );
    }
    context.setToks( k, tokens, prefix.clearGlobal() );
  }

  /**
   * Configure an object according to a given Configuration.
   *
   * @param config the configuration object to consider
   * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
   */
  @Override
  public void configure( Configuration config ) {

    String k = config.getAttribute( "key" );
    if( k != null ) {
      key = k;
    }
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens convertTokens( Context context, TokenSource source,
                               Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String k = getKey( context, source, typesetter );
    return context.getToks( k );
  }

  /**
   * Scan the tokens between {@code {} and {@code }} and store them
   * in the named tokens register.
   *
   * @param prefix     the prefix flags
   * @param context    the interpreter context
   * @param source     the token source
   * @param typesetter the typesetter
   * @param k          the key
   * @throws GeneralException in case of an error
   */
  protected void expand( Flags prefix, Context context, TokenSource source,
                         Typesetter typesetter, String k )
      throws GeneralException {

    Tokens toks = source.getTokens( context, source, typesetter );
    context.setToks( k, toks, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  protected String getKey( Context context, TokenSource source,
                           Typesetter typesetter ) throws TypesetterException {

    if( Namespace.SUPPORT_NAMESPACE_TOKS ) {
      return context.getNamespace() + "\b" + key;
    }
    return key;
  }

  /**
   * TokenSource, Typesetter)
   */
  @Override
  public void init( Context context, TokenSource source, Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    if( source != null ) {
      Tokens toks = new Tokens();
      for( Token t = source.getToken( context ); t != null; t =
          source.getToken( context ) ) {
        toks.add( t );
      }
      context.setToks( getKey( context, null, typesetter ),
                       toks, true );
    }
  }

  /**
   * Return the register value as {@code Tokens} for {@code \the}.
   * <p>
   * org.extex.interpreter.TokenSource, Typesetter)
   */
  @Override
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    return context.getToks( getKey( context, source, typesetter ) );
  }

}
