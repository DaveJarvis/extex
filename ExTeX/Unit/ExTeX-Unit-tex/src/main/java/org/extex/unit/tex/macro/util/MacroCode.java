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

package org.extex.unit.tex.macro.util;

import org.extex.core.Locator;
import org.extex.core.count.Count;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.*;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.*;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.macro.LetCode;
import org.extex.unit.tex.macro.exceptions.EofInDefException;

import java.util.logging.Logger;

/**
 * This class provides an implementation for any macro code bound to a control
 * sequence or active character.
 *
 * <p>The Macro Code</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MacroCode extends AbstractCode
    implements
    PrefixCode,
    ExpandableCode,
    ComparableCode,
    Showable,
    LogEnabled {

  /**
   * This inner class provides the tokens of a macro as a token stream.
   */
  private static class MacroTokenStream implements TokenStream {

    /**
     * The field {@code locator} contains the locator.
     */
    private final Locator locator;

    /**
     * The field {@code name} contains the name of the primitive.
     */
    private final String name;

    /**
     * The field {@code tokens} contains the tokens.
     */
    private final Tokens tokens;

    /**
     * Creates a new object.
     *
     * @param tokens  the tokens
     * @param locator the locator of the invocation
     * @param name    the name of the primitive
     */
    public MacroTokenStream( Tokens tokens, Locator locator, String name ) {

      this.tokens = tokens;
      this.locator = locator;
      this.name = name;
    }

    /**
     * Close this stream if it is a file stream.
     *
     * @return {@code true} if the closing was successful
     * @see org.extex.scanner.api.TokenStream#closeFileStream()
     */
    @Override
    public boolean closeFileStream() {

      return false;
    }

    /**
     * Get the next token from the token stream. If tokens are on the
     * push-back stack then those are delivered otherwise new tokens might
     * be extracted utilizing the token factory and the tokenizer.
     *
     * @param factory   the token factory
     * @param tokenizer the tokenizer
     * @return the next Token or {@code null} if no more tokens are
     * available
     * @throws ScannerException in case of an error
     * @see org.extex.scanner.api.TokenStream#get(org.extex.scanner.type.token.TokenFactory,
     * org.extex.scanner.api.Tokenizer)
     */
    @Override
    public Token get( TokenFactory factory, Tokenizer tokenizer )
        throws ScannerException {

      return tokens.pop();
    }

    /**
     * Getter for the locator. The locator describes the place the tokens
     * have been read from in terms of the user. This information is meant
     * for the end user to track down problems.
     *
     * @return the locator
     * @see org.extex.scanner.api.TokenStream#getLocator()
     */
    @Override
    public Locator getLocator() {

      Locator loc = new Locator( name, -1, null, -1 );
      loc.setCause( locator );
      return loc;
    }

    /**
     * Check to see if a further token can be acquired from the token
     * stream.
     *
     * @return {@code true} if the stream is at its end
     * @throws ScannerException in case that an error has been encountered.
     *                          Especially if an IO exceptions occurs it is 
     *                          delivered as
     *                          chained exception in a ScannerException.
     * @see org.extex.scanner.api.TokenStream#isEof()
     */
    @Override
    public boolean isEof() throws ScannerException {

      return tokens.length() == 0;
    }

    /**
     * Check to see if the token stream is currently at the end of line.
     *
     * @return {@code true} if the stream is at end of line
     * @throws ScannerException in case that an error has been encountered.
     *                          Especially if an IO exceptions occurs it is 
     *                          delivered as
     *                          chained exception in a ScannerException.
     * @see org.extex.scanner.api.TokenStream#isEol()
     */
    @Override
    public boolean isEol() throws ScannerException {

      return false;
    }

    /**
     * Check whether the current stream is associated with a file to read
     * from.
     *
     * @return {@code true} if the stream is a file stream
     * @see org.extex.scanner.api.TokenStream#isFileStream()
     */
    @Override
    public boolean isFileStream() {

      return false;
    }

    /**
     * Push back a token into the stream. If the token is {@code null}
     * then nothing happens: a {@code null} token is not pushed!
     * <p>
     * Note that it is up to the implementation to accept tokens not
     * produced with the token factory for push back. In general the
     * behavior in such a case is not defined and should be avoided.
     * </p>
     *
     * @param token the token to push back
     * @see org.extex.scanner.api.TokenStream#put(org.extex.scanner.type.token.Token)
     */
    @Override
    public void put( Token token ) {

      tokens.push( token );
    }

    @Override
    public String toString() {

      return name + " -> " + locator.toString();
    }
  }

  /**
   * The constant {@code NO_TOKENS} contains the empty tokens array.
   */
  private static final Tokens[] NO_TOKENS = new Tokens[ 0 ];

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2011L;

  /**
   * The field {@code body} contains the tokens of the macro expansion text.
   */
  private final Tokens body;

  /**
   * The field {@code logger} contains the logger for debugging.
   */
  private transient Logger logger;

  /**
   * The field {@code notLong} contains the negated {@code \long} flag. This
   * field indicates that no macros {@code \par} are allowed in macro
   * parameter values.
   */
  private final boolean notLong;

  /**
   * The field {@code outerP} contains the indicator for outer definitions.
   */
  private final boolean outerP;

  /**
   * The field {@code pattern} contains the specification for the argument
   * matching. A value of {@code null} means that no argument are
   * expected.
   */
  private final MacroPattern pattern;

  /**
   * The field {@code tracer} contains the observer for the argument parsing.
   */
  private final transient ArgumentMatchingObserver tracer =
      new ArgumentMatchingObserver() {

        @Override
        public void observeArgument( int index, Tokens value,
                                     CodeToken cs ) {

          // // see [TTP; 400]
          StringBuilder sb = new StringBuilder( "#" );
          sb.append( index + 1 );
          sb.append( "<-" );
          sb.append( value.toText() );
          sb.append( '\n' );
          logger.info( sb.toString() );
        }

      };

  /**
   * Creates a new object.
   *
   * @param token   the initial token for the macro
   * @param flags   the flags controlling the behavior of the macro
   * @param notLong inverted indicator for the long macros
   * @param pattern the pattern for the acquiring of the arguments. The value
   *                of {@code null} means that no arguments are expected
   * @param body    the expansion text
   */
  public MacroCode( CodeToken token, Flags flags, boolean notLong,
                    MacroPattern pattern, Tokens body ) {

    super( token );
    this.body = body;
    this.outerP = flags.clearOuter();
    this.notLong = notLong;
    if( pattern == null || pattern.length() == 0 ) {
      this.pattern = null;
    }
    else {
      this.pattern = pattern;
    }
  }

  /**
   * Compare two patterns. This involves the treatment of {@code null}
   * values which are equivalent to empty patterns.
   *
   * @param p1 the first pattern
   * @param p2 the second pattern
   * @return {@code true} iff the two patterns are equivalent
   */
  private boolean compare( MacroPattern p1, MacroPattern p2 ) {

    if( p1 == null ) {
      return (p2 == null || p2.length() == 0);
    }
    else if( p2 == null ) {
      return p1.length() == 0;
    }
    return p1.equals( p2 );
  }

  /**
   * org.extex.interpreter.context.Context)
   */
  @Override
  public boolean compare( Token token, Context context )
      throws HelpingException {

    if( !(token instanceof CodeToken) ) {
      return false;
    }

    Code code = context.getCode( (CodeToken) token );

    if( !(code instanceof MacroCode) ) {
      return false;
    }
    MacroCode macro = (MacroCode) code;
    return (notLong == macro.notLong
        && outerP == macro.outerP
        && compare( pattern, macro.pattern )
        && body.equals( macro.body ));
  }

  @Override
  public void enableLogging( Logger log ) {

    this.logger = log;
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Tokens[] args;
    if( pattern == null ) {
      args = NO_TOKENS;
    }
    else {
      Count tracingmaros = context.getCount( "tracingmacros" );
      ArgumentMatchingObserver observer =
          (tracingmaros != null && tracingmaros.gt( Count.ZERO )
              ? tracer
              : null);
      args =
          pattern.match( context, source, typesetter, notLong,
                         observer );
    }
    Tokens toks = new Tokens();
    int len = body.length();
    int no = 1;

    for( int i = 0; i < len; i++ ) {
      Token t = body.get( i );

      if( t instanceof CodeToken ) {
        Code code = context.getCode( (CodeToken) t );
        if( code instanceof LetCode ) {
          t = ((LetCode) code).getLetToken();
        }
      }

      if( t instanceof MacroParamToken ) {
        t = body.get( ++i );
        if( t == null ) {
          throw new EofInDefException( toText() );
        }
        else if( t instanceof MacroParamToken ) {
          toks.add( t );
        }
        else if( t instanceof OtherToken && t.getChar().isDigit() ) {
          no = t.getChar().getCodePoint() - '1';
          if( args[ no ] == null ) {
            throw new ImpossibleException( "MacroCode:NullArg" );
          }
          toks.add( args[ no ] );
        }
        else {
          throw new ImpossibleException( "MacroCode:IllegalArg" );
        }
      }
      else {
        toks.add( t );
      }
    }

    source.addStream( new MacroTokenStream( toks, source.getLocator(),
                                            toText() ) );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    execute( prefix, context, source, typesetter );
  }

  /**
   * Getter for localizer.
   *
   * @return the localizer.
   * @see org.extex.interpreter.type.AbstractCode#getLocalizer()
   */
  @Override
  protected Localizer getLocalizer() {

    Localizer localizer = super.getLocalizer();
    return (localizer != null ? localizer : LocalizerFactory
        .getLocalizer( MacroCode.class ));
  }

  /**
   * Getter for the outer flag.
   *
   * @return {@code true} iff the code is defined outer.
   * @see org.extex.interpreter.type.Code#isOuter()
   */
  @Override
  public boolean isOuter() {

    return outerP;
  }

  @Override
  public Tokens show( Context context ) throws HelpingException {

    try {
      StringBuilder sb = new StringBuilder();
      boolean sep = false;
      if( !notLong ) {
        sb.append( context.esc( getLocalizer().format( "TTP.long" ) ) );
        sep = true;
      }
      if( outerP ) {
        sb.append( context.esc( getLocalizer().format( "TTP.outer" ) ) );
        sep = true;
      }
      if( sep ) {
        sb.append( " " );
      }
      sb.append( getLocalizer().format( "TTP.macro" ) ).append( ":\n" );
      Tokens toks = context.getTokenFactory().toTokens( sb );
      if( pattern != null ) {
        show( pattern, context, toks );
      }
      toks.add( context.getTokenFactory().toTokens( "->" ) );
      show( body, context, toks );
      return toks;
    } catch( GeneralException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * Determine the printable representation of the object and append it to a
   * list of Tokens.
   *
   * @param tokens  the tokens to add
   * @param context the processor context
   * @param toks    the tokens to add to
   * @throws CatcodeException in case of an error
   */
  private void show( Tokens tokens, Context context, Tokens toks )
      throws CatcodeException {

    TokenFactory factory = context.getTokenFactory();
    long esc = context.getCount( "escapechar" ).getValue();
    Token t;

    for( int i = 0; i < tokens.length(); i++ ) {
      t = tokens.get( i );
      if( t instanceof ControlSequenceToken ) {
        if( esc >= 0 ) {
          toks.add( factory.createToken( Catcode.OTHER, (char) (esc),
                                         Namespace.DEFAULT_NAMESPACE ) );
        }
        toks.add( factory.toTokens( ((ControlSequenceToken) t).getName() ) );
      }
      else {
        toks.add( factory.createToken( Catcode.OTHER, t.getChar(),
                                       Namespace.DEFAULT_NAMESPACE ) );
      }
    }
  }

}
