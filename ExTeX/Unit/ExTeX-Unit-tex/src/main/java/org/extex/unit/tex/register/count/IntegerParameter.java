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

package org.extex.unit.tex.register.count;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.InitializableCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the count valued primitives like
 * {@code \day}. It sets the named count register to the value given, and
 * as a side effect all prefixes are zeroed.
 *
 * <p>
 * Example
 * </p>
 *
 * <pre>
 *  \day=345
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 */
public class IntegerParameter extends CountPrimitive
    implements
    InitializableCode,
    Configurable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code key} contains the key.
   */
  private String key;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public IntegerParameter( CodeToken token ) {

    super( token );
    this.key = token.getName();
  }

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   * @param key   the key
   */
  public IntegerParameter( CodeToken token, String key ) {

    super( token );
    this.key = key;
  }

  /**
   * Configure an object according to a given Configuration.
   *
   * @param config the configuration object to consider
   * @throws ConfigurationException in case that something went wrong
   * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
   */
  @Override
  public void configure( Configuration config ) throws ConfigurationException {

    String k = config.getAttribute( "key" );
    if( k != null ) {
      key = k;
    }
  }

  /**
   * Return the key (the name of the primitive) for the numbered count
   * register.
   *
   * @param context    the interpreter context to use
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @return the key for the current register
   * @see org.extex.unit.tex.register.count.AbstractCount#getKey(org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  protected String getKey( Context context, TokenSource source,
                           Typesetter typesetter ) {

    return key;
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void init( Context context, TokenSource source, Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    if( source != null ) {
      Token t = source.getToken( context );
      if( t != null ) {
        source.push( t );
        long value = source.parseInteger( context, source, typesetter );
        context.setCount( getKey( context, source, typesetter ), value,
                          true );
      }
    }
  }

}
