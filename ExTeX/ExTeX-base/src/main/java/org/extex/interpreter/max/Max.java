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

package org.extex.interpreter.max;

import org.extex.backend.outputStream.OutputStreamConsumer;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.Switch;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.*;
import org.extex.font.CoreFontFactory;
import org.extex.framework.Registrar;
import org.extex.framework.RegistrarObserver;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.*;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.SwitchObserver;
import org.extex.interpreter.context.observer.load.LoadedObservable;
import org.extex.interpreter.context.observer.load.LoadedObserver;
import org.extex.interpreter.exception.ErrorLimitException;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.loader.LoaderException;
import org.extex.interpreter.loader.SerialLoader;
import org.extex.interpreter.max.exception.NoTokenStreamFactoryException;
import org.extex.interpreter.max.exception.NoTypesetterException;
import org.extex.interpreter.observer.command.CommandObservable;
import org.extex.interpreter.observer.command.CommandObserver;
import org.extex.interpreter.observer.command.CommandObserverList;
import org.extex.interpreter.observer.error.ErrorObservable;
import org.extex.interpreter.observer.error.ErrorObserver;
import org.extex.interpreter.observer.error.ErrorObserverList;
import org.extex.interpreter.observer.expand.ExpandObservable;
import org.extex.interpreter.observer.expand.ExpandObserver;
import org.extex.interpreter.observer.expand.ExpandObserverList;
import org.extex.interpreter.observer.expandMacro.ExpandMacroObservable;
import org.extex.interpreter.observer.expandMacro.ExpandMacroObserver;
import org.extex.interpreter.observer.expandMacro.ExpandMacroObserverList;
import org.extex.interpreter.observer.load.LoadObservable;
import org.extex.interpreter.observer.load.LoadObserver;
import org.extex.interpreter.observer.load.LoadObserverList;
import org.extex.interpreter.observer.start.StartObservable;
import org.extex.interpreter.observer.start.StartObserver;
import org.extex.interpreter.observer.start.StartObserverList;
import org.extex.interpreter.observer.stop.StopObservable;
import org.extex.interpreter.observer.stop.StopObserver;
import org.extex.interpreter.observer.stop.StopObserverList;
import org.extex.interpreter.type.*;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.language.LanguageManager;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.*;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.TokenDelegateListMaker;
import org.extex.unit.base.register.count.IntegerCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * This is a reference implementation for a <b>MA</b>cro e<b>X</b>pander. The
 * macro expander is the core engine driving εχTeX.
 *
 * <p>The Count Parameter {@code \ignorevoid}</p>
 * <p>
 * The count register {@code \ignorevoid} determines how an undefined active
 * character or control sequence is encountered. If the value is greater than 0
 * then undefined code is ignored. Otherwise it leads to an error message.
 * </p>
 * <p>
 * This count parameter has been introduced by εχTeX.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * <pre class="syntax">
 *    &lang;ignorevoid&rang;
 *      &rarr; {@code \ignorevoid} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;number&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \ignorevoid=1  </pre>
 *
 *
 * <p>The Tokens Parameter {@code \everyjob}</p>
 * <p>
 * The tokens register {@code \everyjob} contains the tokens to be inserted at
 * the beginning of every job.
 * </p>
 *
 * <p>Syntax</p>
 *
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * <pre class="syntax">
 *    &lang;everyjob&rang;
 *       &rarr; {@code \everyjob} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context, TokenSource, Typesetter)
 *        &lang;tokens&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \everyjob={\message{Hello world.}}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public abstract class Max
    implements
    Interpreter,
    LogEnabled,
    CommandObservable,
    ExpandObservable,
    ExpandMacroObservable,
    ErrorObservable,
    LoadObservable,
    StartObservable,
    StopObservable,
    TokenVisitor<Object, Object>,
    OutputStreamConsumer {

  /**
   * The field {@code CONTEXT_TAG} contains the name of the tag for the
   * configuration of the context.
   */
  private static final String CONTEXT_TAG = "Context";

  /**
   * The field {@code LANGUAGE_TAG} contains the name of the tag for the
   * configuration of the language manager.
   */
  private static final String LANGUAGE_TAG = "Language";

  /**
   * The constant {@code MAX_ERRORS_DEFAULT} contains the default value for
   * maximal allowed number of errors after which the
   * εχTeX run is terminated automatically.
   */
  private static final int MAX_ERRORS_DEFAULT = 100;

  /**
   * The constant {@code MINUTES_PER_HOUR} contains the number of minutes per
   * hour.
   */
  private static final int MINUTES_PER_HOUR = 60;

  /**
   * The field {@code configuration} contains the configuration for this
   * interpreter.
   */
  private Configuration configuration;

  /**
   * The field {@code context} contains the processing context. Here nearly
   * all relevant information can be found.
   */
  private Context context;

  /**
   * The error handler is invoked whenever an error is detected. If none is
   * registered then the default behavior is shown.
   */
  private ErrorHandler errorHandler;

  /**
   * The field {@code localizer} contains the localizer to use.
   */
  private Localizer localizer;

  /**
   * The field {@code logger} contains the logger or {@code null} if
   * none has been set yet.
   */
  private Logger logger;

  /**
   * The field {@code maxErrors} contains the number of errors after which
   * the run is terminated. This value can be overwritten in the
   * configuration.
   */
  private IntegerCode maxErrors = new IntegerCode( null, MAX_ERRORS_DEFAULT );

  /**
   * This observer list is used for the observers which are registered to
   * receive a notification when a new token is about to be expanded. The
   * argument is the token to be executed.
   */
  private CommandObserver observersCommand;

  /**
   * This observer list is used for the observers which are registered to
   * receive a notification when an error occurs. The argument is the
   * exception encountered.
   */
  private ErrorObserver observersError;

  /**
   * This observer list is used for the observers which are registered to
   * receive a notification when a new token is about to be expanded. The
   * argument is the token to be expanded.
   */
  private ExpandObserver observersExpand;

  /**
   * The field {@code observersLoad} contains the observer list for the
   * observers which are registered to receive a notification when a format is
   * loaded.
   */
  private LoadObserver observersLoad;

  /**
   * This observer list is used for the observers which are registered to
   * receive a notification when a macro is expanded.
   */
  private ExpandMacroObserver observersMacro;

  /**
   * The field {@code observersStart} contains the observer list for the
   * observers which are registered to receive a notification when the
   * execution is started.
   */
  private StartObserver observersStart;

  /**
   * The field {@code observersStop} contains the observer list for the
   * observers which are registered to receive a notification when the
   * execution is finished.
   */
  private StopObserver observersStop;

  /**
   * The field {@code outFactory} contains the output factory.
   */
  private OutputStreamFactory outFactory;

  /**
   * This is the prefix for the next invocation.
   */
  private final Flags prefix;

  /**
   * The field {@code tokenExpander} contains the token visitor for
   * expansion.
   */
  private final TokenVisitor<Token, TokenSource> tokenExpander =
      new TokenVisitor<Token, TokenSource>() {

        /**
         * This visit method is invoked on an active token. In
         * TeX this is e.g. ~.
         *
         * @param token the active token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @throws Exception in case of an error
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitActive(org.extex.scanner.type.token.ActiveCharacterToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitActive( ActiveCharacterToken token,
                                  TokenSource arg ) throws Exception {

          Code code = context.getCode( token );
          if( code instanceof ExpandableCode ) {
            ((ExpandableCode) code).expand( Flags.NONE, context,
                                            arg, typesetter );
            return null;
          }
          else if( code == null ) {
            throw new UndefinedControlSequenceException(
                token.toText() );
          }
          else {
            return token;
          }
        }

        /**
         * This visit method is invoked on a cr token.
         *
         * @param token the cr token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitCr(org.extex.scanner.type.token.CrToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitCr( CrToken token, TokenSource arg ) {

          return token; // gene: correct?
        }

        /**
         * This visit method is invoked on an escape token. In
         * TeX this normally means a control sequence.
         *
         * @param token the control sequence token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @throws Exception in case of an error
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitEscape(org.extex.scanner.type.token.ControlSequenceToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitEscape( ControlSequenceToken token,
                                  TokenSource arg ) throws Exception {

          Code code = context.getCode( token );
          if( code instanceof ExpandableCode ) {
            ((ExpandableCode) code).expand( Flags.NONE, context,
                                            arg, typesetter );
            return null;
          }
          else if( code == null ) {
            throw new UndefinedControlSequenceException(
                token.toText() );
          }
          else {
            return token;
          }

        }

        /**
         * This visit method is invoked on a left brace token.
         *
         * @param token the left brace token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitLeftBrace(org.extex.scanner.type.token.LeftBraceToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitLeftBrace( LeftBraceToken token,
                                     TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a letter token.
         *
         * @param token the letter token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitLetter(org.extex.scanner.type.token.LetterToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitLetter( LetterToken token, TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a macro parameter token. In
         * TeX this normally is a #.
         *
         * @param token the macro param token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitMacroParam(org.extex.scanner.type.token.MacroParamToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitMacroParam( MacroParamToken token,
                                      TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a math shift token. In
         * TeX this normally is a $.
         *
         * @param token the math shift token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitMathShift(org.extex.scanner.type.token.MathShiftToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitMathShift( MathShiftToken token,
                                     TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on an other token.
         *
         * @param token the other token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitOther(org.extex.scanner.type.token.OtherToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitOther( OtherToken token, TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a right brace token.
         *
         * @param token the right brace token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitRightBrace(org.extex.scanner.type.token.RightBraceToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitRightBrace( RightBraceToken token,
                                      TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a space token.
         *
         * @param token the space token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitSpace(org.extex.scanner.type.token.SpaceToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitSpace( SpaceToken token, TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a sub mark token. In
         * TeX this normally is a _.
         *
         * @param token the sub mark token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitSubMark(org.extex.scanner.type.token.SubMarkToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitSubMark( SubMarkToken token, TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a sup mark token. In
         * TeX this normally is a ^.
         *
         * @param token the sup mark token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitSupMark(org.extex.scanner.type.token.SupMarkToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitSupMark( SupMarkToken token, TokenSource arg ) {

          return token;
        }

        /**
         * This visit method is invoked on a tab mark token. In
         * TeX this normally is a &.
         *
         * @param token the tab mark token to visit
         * @param arg the first argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.token.TokenVisitor#visitTabMark(org.extex.scanner.type.token.TabMarkToken,
         *      java.lang.Object)
         */
        @Override
        public Token visitTabMark( TabMarkToken token, TokenSource arg ) {

          return token;
        }

      };

  /**
   * The field {@code typesetter} contains the typesetter for handling
   * "left-over" material.
   */
  private Typesetter typesetter = null;


  public Max() {

    registerObserver( new StartObserver() {

      /**
       * @see org.extex.interpreter.observer.start.StartObserver#update(org.extex.interpreter.Interpreter)
       */
      @Override
      public void update( Interpreter interpreter ) throws HelpingException {

        try {
          Context c = getContext();
          CodeToken t =
              (CodeToken) c.getTokenFactory().createToken(
                  Catcode.ESCAPE, UnicodeChar.get( '\\' ),
                  "maxErrors", Namespace.SYSTEM_NAMESPACE );
          Code code = c.getCode( t );
          if( code instanceof IntegerCode ) {
            maxErrors = (IntegerCode) code;
          }
        } catch( CatcodeException e ) {
          throw new NoHelpException( e );
        }
      }
    } );
    prefix = new FlagsImpl(); // TODO: use factory
  }

  /**
   * Apply the configuration options found in the given configuration object.
   *
   * @param config the configuration object to consider.
   * @throws ConfigurationMissingException in case of a configuration error
   */
  public void configure( Configuration config )
      throws ConfigurationMissingException {

    if( config == null ) {
      throw new ConfigurationMissingException( "Interpreter" );
    }

    this.configuration = config;
  }

  /**
   * Setter for the logger.
   *
   * @param theLogger the new logger
   * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
   */
  @Override
  public void enableLogging( Logger theLogger ) {

    this.logger = theLogger;
  }

  /**
   * This method contains the main execution loop.
   *
   * @param onceMore switch to control the termination of the execution
   * @throws HelpingException in case of an error
   *                          <ul>
   *                          <li>ErrorLimitException in case that the number
   *                          of errors exceeds
   *                          the configured error limit</li>
   *                          </ul>
   */
  private void execute( Switch onceMore ) throws HelpingException {

    do {
      Token token = getToken( context );
      if( token == null ) {
        return;
      }
      else if( observersCommand != null ) {
        observersCommand.update( token );
      }
      try {

        token.visit( this, null );

      } catch( Exception e ) {
        handleException( token, context, e, typesetter );
      }

    } while( onceMore.isOn() );

  }

  /**
   * Context, Typesetter)
   */
  @Override
  public void execute( Token token, Context theContext,
                       Typesetter theTypesetter ) throws HelpingException {

    if( observersCommand != null ) {
      observersCommand.update( token );
    }
    try {

      token.visit( this, null );

    } catch( Exception e ) {
      handleException( token, theContext, e, theTypesetter );
    }
  }

  @Override
  public void executeGroup() throws HelpingException {

    Switch b = new Switch( true );
    context.afterGroup( new SwitchObserver( b, false ) );
    execute( b );
  }

  /**
   * Take the token given and expand it as possible. If the token is
   * expandable then the expansion is performed until an non-expandable token
   * has been found. This token is returned.
   *
   * @param token the token to expand
   * @return the next token
   * @throws HelpingException       in case of an error
   * @throws TypesetterException    in case of an error in the typesetter
   * @throws ConfigurationException in case of an configuration error
   */
  protected Token expand( Token token )
      throws HelpingException,
      ConfigurationException,
      TypesetterException {

    Token t = token;
    Code code;

    while( t instanceof CodeToken ) {
      if( observersExpand != null ) {
        observersExpand.update( t );
      }
      code = context.getCode( (CodeToken) t );
      if( code instanceof ExpandableCode ) {
        ((ExpandableCode) code).expand( prefix, context, this,
                                        typesetter );
        t = getToken( context );
      }
      else {
        return t;
      }
    }
    return t;
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.typesetter.Typesetter)
   */
  @Override
  public Token expand( Token token, Context context, Typesetter typesetter )
      throws HelpingException,
      ConfigurationException,
      TypesetterException {

    Token t = token;

    if( t instanceof CodeToken ) {
      if( observersExpand != null ) {
        observersExpand.update( t );
      }
      Code code = context.getCode( (CodeToken) t );
      if( code instanceof ExpandableCode ) {
        ((ExpandableCode) code).expand( prefix, context, this,
                                        typesetter );
        t = getToken( context );
      }
    }
    return t;
  }

  /**
   * Expand some tokens.
   *
   * @param tokens     the tokens to expand
   * @param typesetter the typesetter to use
   * @return the expanded tokens
   * @throws HelpingException       in case of an error
   * @throws InterpreterException   in case of an error
   * @throws ConfigurationException in case of an configuration problem
   * @see org.extex.interpreter.Interpreter#expand(org.extex.scanner.type.tokens.Tokens,
   * org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens expand( Tokens tokens, Typesetter typesetter )
      throws InterpreterException,
      HelpingException {

    Tokens result = new Tokens();
    StringSource source = new StringSource();
    source.setTokenStreamFactory( getTokenStreamFactory() );

    source.push( tokens );

    for( Token t = source.getToken( context ); t != null; t =
        source.getToken( context ) ) {
      try {
        t = (Token) t.visit( tokenExpander, source );
      } catch( HelpingException | InterpreterException e ) {
        throw e;
      } catch( Exception e ) {
        throw new InterpreterException( e );
      }

      if( t != null ) {
        result.add( t );
      }
    }
    return result;
  }

  /**
   * Take the token given and expand it as possible while honoring the
   * protected code. If the token is not protected and expandable then the
   * expansion is performed until an un-expandable token has been found. This
   * token is returned.
   *
   * @param token  the token to expand
   * @param tokens the token list to pass to an Expander
   * @return the next token
   * @throws HelpingException       in case of an error
   * @throws TypesetterException    in case of an error in the typesetter
   * @throws ConfigurationException in case of an configuration error
   */
  protected Token expandUnproteced( Token token, Tokens tokens )
      throws HelpingException,
      ConfigurationException,
      TypesetterException {

    Token t = token;
    Code code;

    while( t instanceof CodeToken ) {
      if( observersExpand != null ) {
        observersExpand.update( t );
      }
      code = context.getCode( (CodeToken) t );
      if( code instanceof ProtectedCode ) {
        return t;
      }
      else if( code instanceof CodeExpander ) {
        ((CodeExpander) code).expandCode( context, this, typesetter,
                                          tokens );
      }
      else if( code instanceof ExpandableCode ) {
        ((ExpandableCode) code).expand( prefix, context, this,
                                        typesetter );
      }
      else {
        return t;
      }
      t = getToken( context );
    }
    return t;
  }

  @Override
  public Context getContext() {

    return context;
  }

  /**
   * Getter for the error handler. The error handler might not be set. In this
   * case {@code null} is returned.
   *
   * @return the error handler currently registered
   */
  @Override
  public ErrorHandler getErrorHandler() {

    return errorHandler;
  }

  /**
   * Getter for the interaction mode.
   *
   * @return the interaction mode
   */
  @Override
  public Interaction getInteraction() {

    return context.getInteraction();
  }

  /**
   * Getter for localizer.
   *
   * @return the localizer.
   */
  protected Localizer getLocalizer() {

    if( localizer == null ) {
      localizer = LocalizerFactory.getLocalizer( getClass().getName() );
    }
    return localizer;
  }

  /**
   * Getter for logger.
   *
   * @return the logger
   */
  protected Logger getLogger() {

    return this.logger;
  }

  @Override
  public Typesetter getTypesetter() {

    return this.typesetter;
  }

  /**
   * This method represents the error handler invocation.
   *
   * @param token the current token
   * @param cx    the current context
   * @param e     the current exception
   * @param ts    the typesetter
   * @throws HelpingException in case of an error<br>
   *                          especially<br>
   *                          ErrorLimitException in case that the error
   *                          limit has been
   *                          exceeded.
   */
  private void handleException( Token token, Context cx, Exception e,
                                Typesetter ts ) throws HelpingException {

    if( observersError != null ) {
      observersError.update( e );
    }

    if( e instanceof RuntimeException ) {
      throw (RuntimeException) e;
    }
    else if( !(e instanceof GeneralException) ) {
      throw new NoHelpException( e );
    }

    GeneralException t;
    Throwable cause = e;

    do {
      t = (GeneralException) cause;

      if( t.isProcessed() ) {
        throw (e instanceof HelpingException
            ? (HelpingException) e
            : new NoHelpException( e ));
      }
      t.setProcessed( true );
      cause = t.getCause();
    } while( cause instanceof GeneralException );

    if( observersError != null ) {
      observersError.update( t );
    }

    if( cx.incrementErrorCount() > maxErrors.getValue() ) { // cf. TTP[82]
      throw new ErrorLimitException( maxErrors.getValue() );
    }
    else if( errorHandler != null &&
        errorHandler.handleError( t, token, this, cx ) ) {
      return;
    }

    if( t instanceof InterpreterException ) {
      t.setProcessed( true );
    }

    throw (e instanceof HelpingException
        ? (HelpingException) e
        : new NoHelpException( e ));
  }

  /**
   * Initialize the date and time related primitives.
   *
   * <p>The Count Parameter {@code \day}</p>
   * <p>
   * The count parameter {@code \day} is set automatically at the start of a
   * job to the day of the current date. Thus it always is initialized to a
   * value in the range of 1 to 31.
   * </p>
   * <p>
   * In the course of processing it can be used as any count register. This
   * means that assignments, comparisons, and arithmetical operations work as
   * for those.
   * </p>
   * <p>
   * The value is stored when a format file is written. Note however that this
   * value is overwritten when the format file is read back in.
   * </p>
   *
   * <p>Syntax</p>
   * The formal description of this primitive is the
   * following:
   *
   * <pre class="syntax">
   *    &lang;day&rang;
   *      &rarr; {@code \day} {@linkplain
   *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
   *        &lang;equals&rang;} {@linkplain
   *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
   *        &lang;number&rang;}  </pre>
   *
   * <p>Examples</p>
   *
   *
   * <pre class="TeXSample">
   *    \the\day  </pre>
   *
   *
   * <p>The Count Parameter {@code \month}</p>
   * <p>
   * The count parameter {@code \month} is set automatically at the start of
   * a job to the month of the current date. Thus it always is initialized to
   * a value in the range of 1 to 12.
   * </p>
   * <p>
   * In the course of processing it can be used as any count register. This
   * means that assignments, comparisons, and arithmetical operations work as
   * for those.
   * </p>
   * <p>
   * The value is stored when a format file is written. Note however that this
   * value is overwritten when the format file is read back in.
   * </p>
   *
   * <p>Syntax</p>
   * The formal description of this primitive is the
   * following:
   *
   * <pre class="syntax">
   *    &lang;month&rang;
   *      &rarr; {@code \month} {@linkplain
   *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
   *        &lang;equals&rang;} {@linkplain
   *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
   *        &lang;number&rang;}  </pre>
   *
   * <p>Examples</p>
   *
   *
   * <pre class="TeXSample">
   *    \the\month  </pre>
   *
   *
   * <p>The Count Parameter {@code \year}</p>
   * <p>
   * The count parameter {@code \year} is set automatically at the start of a
   * job to the year of the current date.
   * </p>
   * <p>
   * In the course of processing it can be used as any count register. This
   * means that assignments, comparisons, and arithmetical operations work as
   * for those.
   * </p>
   * <p>
   * The value is stored when a format file is written. Note however that this
   * value is overwritten when the format file is read back in.
   * </p>
   *
   * <p>Syntax</p>
   * The formal description of this primitive is the
   * following:
   *
   * <pre class="syntax">
   *    &lang;year&rang;
   *      &rarr; {@code \year} {@linkplain
   *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
   *        &lang;equals&rang;} {@linkplain
   *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
   *        &lang;number&rang;}  </pre>
   *
   * <p>Examples</p>
   *
   *
   * <pre class="TeXSample">
   *    \the\year  </pre>
   *
   *
   * <p>The Count Parameter {@code \time}</p>
   * <p>
   * The count parameter {@code \time} is set automatically at the start of a
   * job to the time of the current date. The time is the number of minutes
   * since 0:00. Thus you can extract the current hour by dividing it by 60
   * and the current minute by computing the remainder modulo 60.
   * </p>
   * <p>
   * In the course of processing it can be used as any count register. This
   * means that assignments, comparisons, and arithmetical operations work as
   * for those.
   * </p>
   * <p>
   * The value is stored when a format file is written. Note however that this
   * value is overwritten when the format file is read back in.
   * </p>
   *
   * <p>Syntax</p>
   * The formal description of this primitive is the
   * following:
   *
   * <pre class="syntax">
   *    &lang;time&rang;
   *      &rarr; {@code \time} {@linkplain
   *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
   *        &lang;equals&rang;} {@linkplain
   *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
   *        &lang;number&rang;}  </pre>
   *
   * <p>Examples</p>
   *
   *
   * <pre class="TeXSample">
   *    {\count0=\time
   *     \divide\count0 60
   *     \the\count0:<i>% here \count0 contains the hour</i>
   *     \multiply\count1 -60
   *     \advance\count0\time
   *     \the\count0<i>% here \count0 contains the minute</i>
   *    }<i>%</i>  </pre>
   *
   * @param calendar the date and time when TeX has been started
   * @throws HelpingException in case of an error
   */
  protected void initializeDate( Calendar calendar ) throws HelpingException {

    context.setCount( "day", calendar.get( Calendar.DAY_OF_MONTH ), true );
    context.setCount( "month", calendar.get( Calendar.MONTH ) + 1, true );
    context.setCount( "year", calendar.get( Calendar.YEAR ), true );
    context.setCount( "time", calendar.get( Calendar.HOUR_OF_DAY )
        * MINUTES_PER_HOUR + calendar.get( Calendar.MINUTE ), true );
  }

  /**
   * Load the format from an external source.
   *
   * @param stream       the stream to read the format information from
   * @param fmt          the name of the format to be loaded
   * @param contextType  the configuration name for the context
   * @param languageType the configuration name for the language manager
   * @throws LoaderException in case that a class could not be found on the
   *                         class path or a wrong class is contained in the
   *                         format
   * @throws IOException     in case that an IO error occurs during the
   * reading of
   *                         the format
   * @see org.extex.interpreter.Interpreter#loadFormat(java.io.InputStream,
   * java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public void loadFormat( InputStream stream, String fmt, String contextType,
                          String languageType )
      throws IOException, LoaderException {

    Context newContext;
    Object ref1 = Registrar.register( new RegistrarObserver() {

      /**
       * @see org.extex.framework.RegistrarObserver#reconnect(java.lang.Object)
       */
      @Override
      public Object reconnect( Object object ) {

        ((LogEnabled) object).enableLogging( logger );
        return object;
      }

    }, LogEnabled.class );

    try {

      newContext = new SerialLoader().load( stream );

    } catch( InvalidClassException e ) {
      throw new LoaderException( getLocalizer().format(
          "ClassLoaderIncompatibility", fmt, e.getMessage() ) );
    } finally {

      Registrar.unregister( ref1 );
    }

    try {
      if( newContext instanceof Configurable ) {
        ((Configurable) newContext).configure( configuration
                                                   .getConfiguration(
                                                       CONTEXT_TAG )
                                                   .getConfiguration(
                                                       contextType ) );
      }

      LanguageManager languageManager = newContext.getLanguageManager();
      if( languageManager instanceof Configurable ) {

        ((Configurable) languageManager).configure( configuration
                                                        .getConfiguration(
                                                            LANGUAGE_TAG )
                                                        .getConfiguration(
                                                            languageType ) );
      }
    } catch( ConfigurationException e ) {
      throw new LoaderException( e );
    }

    if( context != null ) {
      newContext.setFontFactory( context.getFontFactory() );
      newContext.setTokenFactory( context.getTokenFactory() );
      newContext.setStandardTokenStream( context.getStandardTokenStream() );
    }
    context = newContext;

    try {
      Iterator<UnitInfo> unitIterator = context.unitIterator();
      while( unitIterator.hasNext() ) {
        UnitInfo ui = unitIterator.next();
        if( ui instanceof LoadedObserver ) {
          ((LoadedObserver) ui).receiveLoaded( context, this,
                                               typesetter );
        }
      }

      if( context instanceof LoadedObservable ) {
        ((LoadedObservable) context).receiveLoad( this, typesetter );
      }

      if( observersLoad != null ) {
        observersLoad.update( context );
      }
    } catch( InterpreterException | HelpingException e ) {
      throw new LoaderException( e );
    }
  }

  /**
   * Load a unit.
   *
   * @param name           the name of the configuration
   * @param resourceFinder the resource finder
   * @throws ConfigurationException in case of an error
   * @see org.extex.interpreter.Interpreter#loadUnit(String, ResourceFinder)
   */
  @Override
  public void loadUnit( String name, ResourceFinder resourceFinder ) {

    Configuration unitConfig =
        ConfigurationFactory.newInstance( "unit/" + name );

    try {
      LoadUnit.loadUnit( unitConfig, getContext(), this, getTypesetter(),
                         getLogger(), outFactory, resourceFinder );
    } catch( GeneralException e ) {
      Throwable cause = e.getCause();
      if( cause instanceof ConfigurationException ) {
        throw (ConfigurationException) cause;
      }
      throw new ConfigurationWrapperException( e );
    }
  }

  /**
   * Add an observer for the expand event.
   *
   * @param observer the observer to add
   */
  @Override
  public void registerObserver( CommandObserver observer ) {

    observersCommand =
        CommandObserverList.register( observersCommand, observer );
  }

  /**
   * Add an observer for the error event.
   *
   * @param observer the observer to add
   */
  @Override
  public void registerObserver( ErrorObserver observer ) {

    observersError = ErrorObserverList.register( observersError, observer );
  }

  /**
   * Add an observer for the expand event.
   *
   * @param observer the observer to add
   */
  @Override
  public void registerObserver( ExpandMacroObserver observer ) {

    observersMacro =
        ExpandMacroObserverList.register( observersMacro, observer );
  }

  /**
   * Add an observer for the expand event.
   *
   * @param observer the observer to add
   */
  @Override
  public void registerObserver( ExpandObserver observer ) {

    observersExpand =
        ExpandObserverList.register( observersExpand, observer );
  }

  @Override
  public void registerObserver( LoadObserver observer ) {

    observersLoad = LoadObserverList.register( observersLoad, observer );
  }

  /**
   * Add an observer for the start event.
   *
   * @param observer the observer to add
   */
  @Override
  public void registerObserver( StartObserver observer ) {

    observersStart = StartObserverList.register( observersStart, observer );
  }

  /**
   * Add an observer for the stop event.
   *
   * @param observer the observer to add
   */
  @Override
  public void registerObserver( StopObserver observer ) {

    observersStop = StopObserverList.register( observersStop, observer );
  }

  /**
   * Report that a flag has not been used by a macro.
   *
   * @param token the macro which has been invoked
   * @throws HelpingException with the appropriate error message
   */
  private void reportDirtyFlag( Token token ) throws HelpingException {

    String cause = prefix.toText();
    prefix.clear();
    throw new UnusedPrefixException( context.esc( cause ), token.toString() );
  }

  @Override
  public void run() throws HelpingException, TypesetterException {

    if( typesetter == null ) {
      throw new NoTypesetterException( getClass().getName() + "#run()" );
    }

    if( getTokenStreamFactory() == null ) {
      throw new NoTokenStreamFactoryException( getClass().getName()
                                                   + "#run()" );
    }

    initializeDate( Calendar.getInstance() );

    if( observersStart != null ) {
      observersStart.update( this );
    }

    push( context.getToks( "everyjob" ) );

    execute( new Switch( true ) );

    typesetter.finish();

    // TTP [1335]
    long groupLevel = context.getGroupLevel();
    if( groupLevel != 0 ) {
      Localizer loc = getLocalizer();
      String endPrimitive = loc.format( "TTP.EndPrimitive" );
      HelpingException e =
          new HelpingException( loc,
                                "TTP.EndGroup",
                                context.esc( endPrimitive ),
                                Long.toString( groupLevel ) );
      if( observersError != null ) {
        observersError.update( e );
      }
      throw e;
    }
    Conditional cond = context.popConditional();
    if( cond != null ) {
      Localizer loc = getLocalizer();
      String endPrimitive = loc.format( "TTP.EndPrimitive" );
      HelpingException e = new HelpingException( loc, "TTP.EndIf",
                                                 context.esc( endPrimitive ),
                                                 cond.getPrimitiveToken()
                                                     .toText(),
                                                 cond.getLocator().toString() );
      if( observersError != null ) {
        observersError.update( e );
      }
      throw e;
    }

    if( observersStop != null ) {
      observersStop.update( this );
    }
  }

  @Override
  public void run( TokenStream stream )
      throws HelpingException,
      TypesetterException {

    addStream( stream );
    run();
  }

  @Override
  public Context setContext( Context context ) {

    Context c = this.context;
    this.context = context;
    return c;
  }

  /**
   * Setter for the error handler. The value of {@code null} can be used
   * to delete the error handler currently set.
   *
   * @param handler the new error handler
   * @see org.extex.interpreter.Interpreter#setErrorHandler(org.extex.interpreter.ErrorHandler)
   */
  @Override
  public void setErrorHandler( ErrorHandler handler ) {

    errorHandler = handler;
  }

  /**
   * Setter for the font factory.
   *
   * @param fontFactory the new font factory
   * @see org.extex.interpreter.Interpreter#setFontFactory(org.extex.font.CoreFontFactory)
   */
  @Override
  public void setFontFactory( CoreFontFactory fontFactory ) {

    context.setFontFactory( fontFactory );
  }

  /**
   * Setter for the interaction mode. The interaction mode is set globally.
   *
   * @param interaction the interaction mode
   * @throws GeneralException in case of an error
   * @see org.extex.interpreter.Interpreter#setInteraction(org.extex.interpreter.interaction.Interaction)
   */
  @Override
  public void setInteraction( Interaction interaction )
      throws GeneralException {

    context.setInteraction( interaction );
  }

  /**
   * Setter for the job name.
   *
   * @param jobname the new value for the job name
   * @throws GeneralException in case of an error
   * @see org.extex.interpreter.Interpreter#setJobname(java.lang.String)
   */
  @Override
  public void setJobname( String jobname ) throws GeneralException {

    context.setToks( "jobname",
                     context.getTokenFactory().toTokens( jobname ), true );
  }

  /**
   * This method takes an output stream factory for further use.
   *
   * @param factory the output stream factory to use
   * @see org.extex.backend.outputStream.OutputStreamConsumer#setOutputStreamFactory(org.extex.backend.outputStream.OutputStreamFactory)
   */
  @Override
  public void setOutputStreamFactory( OutputStreamFactory factory ) {

    this.outFactory = factory;
  }

  /**
   * Setter for the typesetter.
   *
   * @param typesetter the new typesetter
   * @see org.extex.interpreter.Interpreter#setTypesetter(org.extex.typesetter.Typesetter)
   */
  @Override
  public void setTypesetter( Typesetter typesetter ) {

    this.typesetter = typesetter;
  }

  /**
   * This visit method is invoked on an active token. In TeX this is e.g. ~.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception              in case of an error
   * @throws ConfigurationException in case of an configuration error
   * @see org.extex.scanner.type.token.TokenVisitor#visitActive(org.extex.scanner.type.token.ActiveCharacterToken,
   * java.lang.Object)
   */
  @Override
  public Object visitActive( ActiveCharacterToken token, Object ignore )
      throws Exception {

    Code code = context.getCode( token );
    if( code == null ) {
      Count ignoreVoid = context.getCount( "ignorevoid" );
      if( ignoreVoid.le( Count.ZERO ) ) {
        throw new UndefinedControlSequenceException( token.toText() );
      }
    }
    else {

      code.execute( prefix, context, this, typesetter );

      if( !(code instanceof ExpandableCode)
          && !(code instanceof PrefixCode) && prefix.isDirty() ) {
        reportDirtyFlag( token );
      }
    }
    return null;
  }

  /**
   * This visit method is invoked on a cr token.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception in case of an error
   * @see org.extex.scanner.type.token.TokenVisitor#visitCr(org.extex.scanner.type.token.CrToken,
   * java.lang.Object)
   */
  @Override
  public Object visitCr( CrToken token, Object ignore ) throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }
    ((TokenDelegateListMaker) typesetter).cr( context,
                                              context.getTypesettingContext(),
                                              token.getChar() );
    return null;
  }

  /**
   * This visit method is invoked on an escape token. In TeX this normally
   * means a control sequence.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception              in case of an error
   * @throws ConfigurationException in case of an configuration error
   */
  @Override
  public Object visitEscape( ControlSequenceToken token, Object ignore )
      throws Exception {

    Code code = context.getCode( token );
    if( observersMacro != null ) {
      observersMacro.update( token, code, getLocator() );
    }
    if( code == null ) {
      Count ignoreVoid = context.getCount( "ignorevoid" );
      if( ignoreVoid.le( Count.ZERO ) ) {
        throw new UndefinedControlSequenceException( token.toText() );
      }
    }
    else {

      code.execute( prefix, context, this, typesetter );

      if( !(code instanceof PrefixCode) && prefix.isDirty() ) {
        reportDirtyFlag( token );
      }
    }
    return null;
  }

  /**
   * This visit method is invoked on a left brace token.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception              in case of an error
   * @throws ConfigurationException in case of an configuration error
   */
  @Override
  public Object visitLeftBrace( LeftBraceToken token, Object ignore )
      throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }
    context.openGroup( GroupType.SIMPLE_GROUP, getLocator(), token );
    typesetter.leftBrace();

    return null;
  }

  /**
   * This visit method is invoked on a letter token.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception in case of an error
   */
  @Override
  public Object visitLetter( LetterToken token, Object ignore )
      throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }
    boolean letter =
        ((TokenDelegateListMaker) typesetter).letter( token.getChar(),
                                                      context.getTypesettingContext(),
                                                      context,
                                                      this,
                                                      getLocator() );
    if( letter && context.getCount( "tracinglostchars" ).gt( Count.ZERO ) ) {
      // FontUtil.charWarning(logger, context, context
      // .getTypesettingContext().getFont(), token.getChar());
      logger.info( getLocalizer().format( "TTP.MissingChar",
                                          token.getChar().toString(),
                                          context.getTypesettingContext()
                                                 .getFont()
                                                 .getFontName() ) );
    }

    return null;
  }

  /**
   * This visit method is invoked on a macro parameter token. In TeX this
   * normally is a {@code #}.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws GeneralException in case of an error
   */
  @Override
  public Object visitMacroParam( MacroParamToken token, Object ignore )
      throws GeneralException {

    throw new CantUseInException( token.toString(), typesetter.getMode()
                                                              .toString() );
  }

  /**
   * This visit method is invoked on a math shift token. In TeX this
   * normally is a {@code $}.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception              in case of an error
   * @throws ConfigurationException in case of an configuration error
   */
  @Override
  public Object visitMathShift( MathShiftToken token, Object ignore )
      throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }

    ((TokenDelegateListMaker) typesetter).mathShift( context, this, token );

    return null;
  }

  /**
   * This visit method is invoked on an other token.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception in case of an error
   * @see org.extex.scanner.type.token.TokenVisitor#visitOther(org.extex.scanner.type.token.OtherToken,
   * java.lang.Object)
   */
  @Override
  public Object visitOther( OtherToken token, Object ignore ) throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }

    ((TokenDelegateListMaker) typesetter).letter( token.getChar(),
                                                  context.getTypesettingContext(),
                                                  context, this, getLocator() );
    return null;
  }

  /**
   * This visit method is invoked on a right brace token.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception in case of an error
   */
  @Override
  public Object visitRightBrace( RightBraceToken token, Object ignore )
      throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }

    context.closeGroup( typesetter, this );
    typesetter.rightBrace();

    return null;
  }

  /**
   * This visit method is invoked on a space token.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws GeneralException       in case of an error
   * @throws ConfigurationException in case of an configuration error
   */
  @Override
  public Object visitSpace( SpaceToken token, Object ignore )
      throws GeneralException {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }

    typesetter.addSpace( context.getTypesettingContext(), null );

    return null;
  }

  /**
   * This visit method is invoked on a sub mark token. In TeX this normally
   * is a {@code _}.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception in case of an error
   * @see org.extex.scanner.type.token.TokenVisitor#visitSubMark(org.extex.scanner.type.token.SubMarkToken,
   * java.lang.Object)
   */
  @Override
  public Object visitSubMark( SubMarkToken token, Object ignore )
      throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }

    try {
      ((TokenDelegateListMaker) typesetter).subscriptMark( context, this,
                                                           typesetter, token );
    } catch( TypesetterException e ) {
      Throwable t = e.getCause();
      if( t instanceof InterpreterException ) {
        throw (InterpreterException) t;
      }
      throw new InterpreterException( e );
    }

    return null;
  }

  /**
   * This visit method is invoked on a sup mark token. In TeX this normally
   * is a {@code ^}.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception in case of an error
   */
  @Override
  public Object visitSupMark( SupMarkToken token, Object ignore )
      throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }

    try {
      ((TokenDelegateListMaker) typesetter).superscriptMark( context,
                                                             this,
                                                             typesetter,
                                                             token );
    } catch( TypesetterException e ) {
      Throwable t = e.getCause();
      if( t instanceof InterpreterException ) {
        throw (InterpreterException) t;
      }
      throw new InterpreterException( e );
    }

    return null;
  }

  /**
   * This visit method is invoked on a tab mark token. In TeX this normally
   * is a {@code &amp;}.
   *
   * @param token  the first argument to pass is the token to expand.
   * @param ignore the second argument is ignored
   * @return {@code null}
   * @throws Exception              in case of an error
   * @throws ConfigurationException in case of an configuration error
   */
  @Override
  public Object visitTabMark( TabMarkToken token, Object ignore )
      throws Exception {

    if( prefix.isDirty() ) {
      reportDirtyFlag( token );
    }
    ((TokenDelegateListMaker) typesetter).tab( context, this, token );

    return null;
  }

}
