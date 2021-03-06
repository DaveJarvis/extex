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

package org.extex.scanner.base;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.framework.configuration.Configuration;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.api.exception.InvalidCharacterScannerException;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.stream.TokenStreamOptions;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeVisitor;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * This class contains an implementation of a token stream which is fed from a
 * Reader.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TokenStreamImpl extends TokenStreamBaseImpl
    implements TokenStream {

  /**
   * This is a type-safe class to represent state information.
   *
   * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
   */
  private static final class State {

    /**
     * The field {@code name} contains the print name of this state for
     * debugging.
     */
    private final String name;

    /**
     * Creates a new object.
     *
     * @param name the print name of the state
     */
    public State( String name ) {

      this.name = name;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

      return name;
    }

  }

  /**
   * The constant {@code CARET_LIMIT} contains the threshold for the ^
   * notation.
   */
  protected static final int CARET_LIMIT = 0100; // 0100 = 64

  /**
   * The constant {@code CR} contains the one and only CR character.
   */
  private static final UnicodeChar CR = UnicodeChar.get( 13 );

  /**
   * The constant {@code MID_LINE} contains the state for the processing in
   * the middle of a line.
   */
  protected static final State MID_LINE = new State( "mid line" );

  /**
   * The constant {@code NEW_LINE} contains the state for the processing at
   * the beginning of a new line.
   */
  protected static final State NEW_LINE = new State( "new line" );

  /**
   * The constant {@code SKIP_BLANKS} contains the state for the processing
   * when spaces are ignored.
   */
  protected static final State SKIP_BLANKS = new State( "skip blanks" );

  /**
   * The field {@code in} contains the buffered reader for lines.
   */
  private LineNumberReader in;

  /**
   * The field {@code line} contains the current line of input.
   */
  private String line = "";

  /**
   * The index in the buffer for the next character to consider. This is an
   * invariant: after a character is read this pointer has to be advanced.
   */
  private int pointer = 1;

  /**
   * The field {@code saveChar} contains the saved look-ahead character.
   */
  private UnicodeChar saveChar = null;

  /**
   * The field {@code source} contains the description of the source for
   * tokens.
   */
  private final String source;

  /**
   * The field {@code state} contains the current state of operation.
   */
  private State state = NEW_LINE;

  /**
   * The field {@code visitor} contains the visitor to separate the cases
   * according to the catcode.
   */
  private final CatcodeVisitor<Token, TokenFactory, Tokenizer, UnicodeChar> visitor =
      new CatcodeVisitor<Token, TokenFactory, Tokenizer, UnicodeChar>() {

        /**
         * This visit method is invoked on an active token. In
         * TeX this is e.g. ~.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitActive(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitActive( TokenFactory factory,
                                  Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.ACTIVE, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a comment token. In
         * TeX this normally is a %.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return {@code null}
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitComment(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitComment( TokenFactory factory,
                                   Tokenizer tokenizer, UnicodeChar uc ) {

          endLine();
          return null;
        }

        /**
         * This visit method is invoked on a cr token.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uchar the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitCr(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitCr( TokenFactory factory, Tokenizer tokenizer,
                              UnicodeChar uchar ) throws CatcodeException {

          Token t = null;

          if( state == MID_LINE ) {
            t = factory.createToken( Catcode.SPACE, ' ',
                                     tokenizer.getNamespace() );
          }
          else if( state == NEW_LINE ) {
            t = factory.createToken( Catcode.ESCAPE,
                                     UnicodeChar.get( '\\' ), "par",
                                     tokenizer.getNamespace() );
          }
          else {
            // drop the character
          }

          endLine();
          return t;
        }

        /**
         * This visit method is invoked on an escape token. In
         * TeX this normally means a control sequence.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uchar the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         * @throws ScannerException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitEscape(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitEscape( TokenFactory factory,
                                  Tokenizer tokenizer, UnicodeChar uchar )
            throws CatcodeException,
            ScannerException {

          String namespace = tokenizer.getNamespace();

          if( atEndofLine() ) {
            // empty control sequence; see "The TeXbook, Chapter 8,
            // p. 47"
            return factory.createToken( Catcode.ESCAPE, uchar, "",
                                        namespace );
          }

          UnicodeChar uc = getChar( tokenizer );

          if( uc == null ) {
            return factory.createToken( Catcode.ESCAPE, uchar, "",
                                        namespace );

          }
          else if( tokenizer.getCatcode( uc ) == Catcode.LETTER ) {
            StringBuilder sb = new StringBuilder();
            sb.append( (char) (uc.getCodePoint()) );
            state = SKIP_BLANKS;

            while( !atEndofLine() ) {
              uc = getChar( tokenizer );
              if( uc == null ) {
                break;
              }

              if( tokenizer.getCatcode( uc ) != Catcode.LETTER ) {
                ungetChar( uc );
                return factory.createToken( Catcode.ESCAPE,
                                            uchar, sb.toString(), namespace );
              }
              sb.append( (char) (uc.getCodePoint()) );
            }

            return factory.createToken( Catcode.ESCAPE, uchar,
                                        sb.toString(), namespace );

          }
          else {
            state = MID_LINE;
            return factory.createToken( Catcode.ESCAPE, uchar,
                                        Character.toString( (char) (uc.getCodePoint()) ),
                                        namespace );

          }
        }

        /**
         * This visit method is invoked on an ignore token.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitIgnore(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitIgnore( TokenFactory factory,
                                  Tokenizer tokenizer, UnicodeChar uc ) {

          return null;
        }

        /**
         * This visit method is invoked on an invalid token.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return nothing
         *
         * @throws InvalidCharacterScannerException in any case
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitInvalid(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitInvalid( TokenFactory factory,
                                   Tokenizer tokenizer, UnicodeChar uc )
            throws InvalidCharacterScannerException {

          state = MID_LINE;

          throw new InvalidCharacterScannerException( uc );
        }

        /**
         * This visit method is invoked on a left brace token.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitLeftBrace(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitLeftBrace( TokenFactory factory,
                                     Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.LEFTBRACE, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a letter token.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitLetter(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitLetter( TokenFactory factory,
                                  Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.LETTER, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a macro parameter token. In
         * TeX this normally is a #.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitMacroParam(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitMacroParam( TokenFactory factory,
                                      Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.MACROPARAM, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a math shift token. In
         * TeX this normally is a $.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitMathShift(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitMathShift( TokenFactory factory,
                                     Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.MATHSHIFT, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on an other token.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitOther(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitOther( TokenFactory factory,
                                 Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.OTHER, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a right brace token.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitRightBrace(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitRightBrace( TokenFactory factory,
                                      Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.RIGHTBRACE, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a space token.
         *
         * @param factory the first argument is the factory to use
         * @param tokenizer the second argument is the tokenizer to use
         * @param uc the third argument is the UnicodeCharacer
         *
         * @return a space token if in mid line mode or
         *         {@code null} otherwise
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitSpace(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         * @see "The TeXbook [Chapter 8, page 47]"
         */
        @Override
        public Token visitSpace( TokenFactory factory,
                                 Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          if( state == MID_LINE ) {
            state = SKIP_BLANKS;
            return factory.createToken( Catcode.SPACE, ' ',
                                        Namespace.DEFAULT_NAMESPACE );
          }

          return null;
        }

        /**
         * This visit method is invoked on a sub mark token. In
         * TeX this normally is a _.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitSubMark(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitSubMark( TokenFactory factory,
                                   Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.SUBMARK, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a sup mark token. In
         * TeX this normally is a ^.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitSupMark(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitSupMark( TokenFactory factory,
                                   Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.SUPMARK, uc,
                                      tokenizer.getNamespace() );
        }

        /**
         * This visit method is invoked on a tab mark token. In
         * TeX this normally is a &.
         *
         * @param factory the first argument to pass
         * @param tokenizer the second argument to pass
         * @param uc the third argument to pass
         *
         * @return some value
         *
         * @throws CatcodeException in case of an error
         *
         * @see org.extex.scanner.type.CatcodeVisitor#visitTabMark(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public Token visitTabMark( TokenFactory factory,
                                   Tokenizer tokenizer, UnicodeChar uc )
            throws CatcodeException {

          state = MID_LINE;

          return factory.createToken( Catcode.TABMARK, uc,
                                      tokenizer.getNamespace() );
        }

      };

  /**
   * Creates a new object.
   *
   * @param config    the configuration object for this instance; This
   *                  configuration is ignored in this implementation.
   * @param options   ignored here
   * @param reader    the reader
   * @param isFile    indicator for file streams
   * @param theSource the description of the input source
   * @throws IOException in case of an IO error
   */
  public TokenStreamImpl( Configuration config, TokenStreamOptions options,
                          Reader reader, Boolean isFile, String theSource )
      throws IOException {

    super( isFile.booleanValue() );
    this.in = new LineNumberReader( reader );
    this.source = theSource;
  }

  /**
   * Checks whether the pointer is at the end of line.
   *
   * @return {@code true} iff the next reading operation would try to
   * refill the line buffer
   */
  protected boolean atEndofLine() {

    return (pointer >= line.length());
  }

  /**
   * Handle double carets.
   *
   * @param caret1 the first caret
   * @param caret2 the second caret
   * @return the UnicodeChar to use instead
   */
  private UnicodeChar caretCaret( UnicodeChar caret1, UnicodeChar caret2 ) {

    UnicodeChar c = getRawChar();
    if( c == null ) {
      // this should be impossible since a space is added to the end of
      // the input stream
      return null; // ^^ at end is silently ignored
    }
    int hexHigh = hex2int( c.getCodePoint() );
    if( hexHigh < 0 ) {
      hexHigh = c.getCodePoint();
      return UnicodeChar.get( ((hexHigh < CARET_LIMIT)
          ? hexHigh + CARET_LIMIT
          : hexHigh - CARET_LIMIT) );
    }

    c = getRawChar();
    if( c == null ) {
      // this should be impossible since a space is added to the end of
      // the input stream
      return UnicodeChar.get( hexHigh );
    }
    int hexLow = hex2int( c.getCodePoint() );
    if( hexLow < 0 ) {
      ungetChar( c );
      return UnicodeChar.get( hexHigh );
    }

    return UnicodeChar.get( (hexHigh << 4) + hexLow );
  }

  /**
   * End the current line.
   */
  protected void endLine() {

    pointer = line.length() + 1;
  }

  /**
   * Return the next character to process. The pointer is advanced and points
   * to the character returned.
   * <p>
   * This operation might involve that an additional bunch of characters is
   * read in (with {@link #refill() refill()}).
   * </p>
   *
   * @param tokenizer the classifier for characters
   * @return the character or {@code null} if no character is available
   * @throws ScannerException in the rare case that an IO Exception has
   *                          occurred.
   */
  protected UnicodeChar getChar( Tokenizer tokenizer ) throws ScannerException {

    if( saveChar != null ) {
      UnicodeChar uc = saveChar;
      saveChar = null;
      return uc;
    }

    UnicodeChar uc = getRawChar();

    if( uc == null ) {
      do {
        if( !refill() ) {
          return null;
        }
        uc = getRawChar();

      } while( uc == null );

      state = NEW_LINE;
    }

    if( tokenizer.getCatcode( uc ) == Catcode.SUPMARK ) {

      int savePointer = pointer;
      UnicodeChar c = getRawChar();

      if( uc.equals( c ) ) {
        return caretCaret( uc, c );
      }
      pointer = savePointer;
    }

    return uc;
  }

  /**
   * Getter for the locator. The locator describes the place the tokens have
   * been read from in terms of the user. This information is meant for the
   * end user to track down problems.
   *
   * @return the locator
   * @see org.extex.scanner.api.TokenStream#getLocator()
   */
  @Override
  public Locator getLocator() {

    return new Locator( source, (in == null ? 0 : in.getLineNumber()), line,
                        pointer - 1 );
  }

  /**
   * Get the next token when the stack is empty. This method is meant to be
   * overloaded by derived classes.
   *
   * @param factory   the factory for new tokens
   * @param tokenizer the classifies for characters
   * @return the next Token or {@code null}
   * @throws ScannerException in case of an error
   * @see org.extex.scanner.base.TokenStreamBaseImpl#getNext(org.extex.scanner.type.token.TokenFactory,
   * org.extex.scanner.api.Tokenizer)
   */
  @Override
  protected Token getNext( TokenFactory factory, Tokenizer tokenizer )
      throws ScannerException {

    Token t = null;

    do {
      UnicodeChar uc = getChar( tokenizer );
      if( uc == null ) {
        return null;
      }

      try {
        t = (Token) tokenizer.getCatcode( uc ).visit( visitor,
                                                      factory, tokenizer, uc );
      } catch( Exception e ) {
        throw new ScannerException( e );
      }
    } while( t == null );

    return t;
  }

  /**
   * Get the next character from the input line.
   *
   * @return the next raw character or {@code null} if none is available.
   */
  protected UnicodeChar getRawChar() {

    if( line == null ) {
      return null;
    }

    if( pointer < line.length() ) {
      return UnicodeChar.get( line.charAt( pointer++ ) );
    }
    return (pointer++ > line.length() ? null : CR);
  }

  /**
   * Analyze a character and return its hex value. This means '0' to '9' are
   * mapped to 0 to 9 and 'a' to 'f' (case sensitive) are mapped to 10 to 15.
   *
   * @param c the character code to analyze
   * @return the integer value of a hex digit or -1 if no hex digit is given
   */
  protected int hex2int( int c ) {

    if( '0' <= c && c <= '9' ) {
      return c - '0';
    }
    else if( 'a' <= c && c <= 'f' ) {
      return c - 'a' + 10;
    }
    else {
      return -1;
    }
  }

  /**
   * Check to see if a further token can be acquired from the token stream.
   *
   * @return {@code true} if the stream is at its end
   * @throws ScannerException in case that an error has been encountered.
   *                          Especially if an IO exceptions occurs it is
   *                          delivered as chained
   *                          exception in a ScannerException.
   * @see org.extex.scanner.api.TokenStream#isEof()
   */
  @Override
  public boolean isEof() throws ScannerException {

    if( saveChar != null || !super.isEof() ) {
      return false;
    }

    try {
      do {
        if( pointer < line.length() ) {
          return false;
        }
      } while( refill() );
    } catch( ScannerException e ) {
      return true;
    }

    return true;
  }

  /**
   * Check to see if the token stream is currently at the end of line.
   *
   * @return {@code true} if the stream is at end of line
   * @see org.extex.scanner.api.TokenStream#isEol()
   */
  @Override
  public boolean isEol() {

    return pointer > line.length();
  }

  /**
   * Get the next line from the input reader to be processed.
   *
   * @return {@code true} iff the next line could be acquired.
   * @throws ScannerException in case of some kind of IO error
   */
  protected boolean refill() throws ScannerException {

    if( in == null ) {
      return false;
    }
    try {
      line = in.readLine();
      if( line == null ) {
        in.close();
        in = null;
        return false;
      }
    } catch( IOException e ) {
      throw new ScannerException( e );
    }
    pointer = 0;
    return true;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return source + ":" + in.getLineNumber() + "[" + pointer + "]:" + line;
  }

  /**
   * Save the look-ahead character.
   *
   * @param uc the character to save
   */
  private void ungetChar( UnicodeChar uc ) {

    saveChar = uc;
  }

}
