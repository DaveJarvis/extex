/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.scan;

import org.extex.core.Locator;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \scantokens}.
 *
 * <p>The Primitive {@code \scantokens}</p>
 * <p>
 * The primitive {@code \scantokens} takes an unexpanded list of tokens and
 * uses them as a new source for an input stream. For this purpose the tokens
 * are translated into a string which is used as if it where written to a file
 * and read back in.
 * </p>
 * <p>
 * The tokens from the tokens register {@code \everyeof} are inserted when the
 * stream has no more tokens to read.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;scantokens&rang;
 *      &rarr; {@code \scantokens} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context, TokenSource, Typesetter)
 *        &lang;tokens&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \scantokens{abc}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Scantokens extends AbstractCode implements ExpandableCode {

  /**
   * This class encapsulates a Token stream pretending that it is a file
   * stream.
   *
   * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
   */
  private static class TokenStreamProxy implements TokenStream {

    /**
     * The field {@code stream} contains the proxied token stream.
     */
    private final TokenStream stream;

    /**
     * Creates a new object.
     *
     * @param stream the stream
     */
    public TokenStreamProxy( TokenStream stream ) {

      this.stream = stream;
    }

    @Override
    public boolean closeFileStream() {

      stream.closeFileStream();
      return false;
    }

    /**
     * org.extex.scanner.api.Tokenizer)
     */
    @Override
    public Token get( TokenFactory factory, Tokenizer tokenizer )
        throws ScannerException {

      return stream.get( factory, tokenizer );
    }

    @Override
    public Locator getLocator() {

      return stream.getLocator();
    }

    @Override
    public boolean isEof() throws ScannerException {

      return stream.isEof();
    }

    @Override
    public boolean isEol() throws ScannerException {

      return stream.isEol();
    }

    @Override
    public boolean isFileStream() {

      return true;
    }

    @Override
    public void put( Token token ) {

      stream.put( token );
    }

  }

  /**
   * The field {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Scantokens( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Tokens tokens;
    try {
      tokens = source.getTokens( context, source, typesetter );
    } catch( EofException e ) {
      throw new EofInToksException( toText( context ) );
    }
    TokenStreamFactory factory = source.getTokenStreamFactory();
    String t = tokens.toText( context.escapechar() );
    source.addStream( new TokenStreamProxy( factory.getStream( t ) ) );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws ConfigurationException,
      HelpingException,
      TypesetterException {

    execute( prefix, context, source, typesetter );
  }

}
