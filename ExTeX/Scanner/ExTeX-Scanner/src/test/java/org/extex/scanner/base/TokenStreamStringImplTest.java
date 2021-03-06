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
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * Test cases for the string implementation of a token stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TokenStreamStringImplTest {

  /**
   * The constant {@code SPACE} contains the code point of space.
   */
  private static final int SPACE = 32;

  /**
   * The field {@code factory} contains the token factory to use.
   */
  protected static final TokenFactory FACTORY = new TokenFactoryImpl();

  /**
   * The field {@code tokenizer} contains the tokenizer to use for
   * categorizing characters.
   */
  protected static final Tokenizer TOKENIZER = new Tokenizer() {

    /**
     * Getter for the category code of a character.
     *
     * @param c the Unicode character to analyze
     *
     * @return the category code of a character
     *
     * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
     */
    @Override
    public Catcode getCatcode( UnicodeChar c ) {

      if( c.isLetter() ) {
        return Catcode.LETTER;
      }
      switch( c.getCodePoint() ) {
        case '$':
          return Catcode.MATHSHIFT;
        case '^':
          return Catcode.SUPMARK;
        case '_':
          return Catcode.SUBMARK;
        case '%':
          return Catcode.COMMENT;
        case '&':
          return Catcode.TABMARK;
        case '#':
          return Catcode.MACROPARAM;
        case '{':
          return Catcode.LEFTBRACE;
        case '}':
          return Catcode.RIGHTBRACE;
        case '\\':
          return Catcode.ESCAPE;
        case '~':
          return Catcode.ACTIVE;
        case '\r':
        case '\n':
          return Catcode.CR;
        case '\t':
        case ' ':
          return Catcode.SPACE;
        case '\0':
        case '\f':
          return Catcode.IGNORE;
        case '\b':
          return Catcode.INVALID;
        default:
          return Catcode.OTHER;
      }
    }

    /**
     * Getter for the name space.
     *
     * @return the name space
     *
     * @see org.extex.scanner.api.Tokenizer#getNamespace()
     */
    @Override
    public String getNamespace() {

      return "";
    }

  };

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( TokenStreamStringImplTest.class );
  }


  public TokenStreamStringImplTest() {

  }

  /**
   * Create a stream of tokens fed from a string.
   *
   * @param line the input string
   * @return the new token stream
   * @throws IOException in case of an error
   */
  protected TokenStream makeStream( String line ) throws IOException {

    return new TokenStreamImpl( null, null, new StringReader( line ),
                                Boolean.FALSE, "test" );
  }

  /**
   * The digit 1 is parsed as other character and nothing more.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    TokenStream stream = makeStream( "1" );
    assertEquals( "the character 1", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The digits 1 and 2 are parsed as other character and nothing more
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test12() throws Exception {

    TokenStream stream = makeStream( "12" );
    assertEquals( "the character 1", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "the character 2", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Test that ~ is recognized as active character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testActive() throws Exception {

    TokenStream stream = makeStream( "~" );
    assertEquals( "the active character ~",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character ^ is parsed as superscript character if followed by a
   * small number
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaret1() throws Exception {

    TokenStream stream = makeStream( "^1" );
    assertEquals( "superscript character ^", stream.get( FACTORY, TOKENIZER )
                                                   .toString() );
    assertEquals( "the character 1", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character ^^ is parsed as character escape if followed by a
   * sufficiently large number, e.g. 41 for the letter A.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA() throws Exception {

    TokenStream stream = makeStream( "^^41" );
    assertEquals( "the letter A", stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character ^^ is parsed as character escape if followed by a
   * small number, e.g. A for the character number 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA2() throws Exception {

    TokenStream stream = makeStream( "^^A" );
    assertEquals( "the character ^^A", stream.get( FACTORY, TOKENIZER )
                                             .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character ^^ is parsed as character escape if followed by a
   * small number, e.g. A for the character number 1. Thus works even when
   * other characters follow.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA3() throws Exception {

    TokenStream stream = makeStream( "^^A;" );
    assertEquals( "the character ^^A", stream.get( FACTORY, TOKENIZER )
                                             .toString() );
    assertEquals( "the character ;", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * This test case validates that ^^ followed by a number is parsed correctly
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA4() throws Exception {

    TokenStream stream = makeStream( "^^2;" );
    assertEquals( "the character ^^B", stream.get( FACTORY, TOKENIZER )
                                             .toString() );
    assertEquals( "the character ;", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * This test case validates that ^^ followed by a space is parsed correctly
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA5() throws Exception {

    TokenStream stream = makeStream( "^^ " );
    assertEquals( "the character `", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character ^ is parsed as superscript character if encountered at the
   * end of file
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretEnd() throws Exception {

    TokenStream stream = makeStream( "^" );
    assertEquals( "superscript character ^", stream.get( FACTORY, TOKENIZER )
                                                   .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character ^^ is parsed as character escape if followed by a
   * sufficiently large number, e.g. 4f for the letter O.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretO() throws Exception {

    TokenStream stream = makeStream( "^^4f" );
    assertEquals( "the letter O", stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Test that % acts as comment characters and eats up anything to EOF if no
   * newline is contained
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testComment1() throws Exception {

    TokenStream stream = makeStream( "%abc" );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Test that % acts as comment characters and eats up anything to EOF if no
   * newline is contained
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testComment2() throws Exception {

    TokenStream stream = makeStream( "%abc\nx" );
    Token t = stream.get( FACTORY, TOKENIZER );
    assertNotNull( t );
    assertEquals( 120, t.getChar().getCodePoint() );
  }

  /**
   * This test case validates that a single newline translates into a space
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCr1() throws Exception {

    TokenStream stream = makeStream( "x\nx" );
    assertEquals( "the letter x", stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the letter x", stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * A single newline character is translated into a space.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCR1() throws Exception {

    TokenStream stream = makeStream( "a\nc" );
    assertEquals( "the letter a",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token t = stream.get( FACTORY, TOKENIZER );
    assertEquals( "blank space  ", t.toString() );
    assertEquals( "the letter c",
                  stream.get( FACTORY, TOKENIZER ).toString() );
  }

  /**
   * ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testCr2() throws Exception {

    TokenStream stream = makeStream( "x\n\nx" );
    assertEquals( "the letter x", stream.get( FACTORY, TOKENIZER ).toString() );
    Token t = stream.get( FACTORY, TOKENIZER );
    assertEquals( SPACE, t.getChar().getCodePoint() );
    assertNotNull( t );
    assertEquals( "the control sequence \\par", t.toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * A single newline character is translated into a space. The
   * following space is absorbed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCR2() throws Exception {

    TokenStream stream = makeStream( "a\n c" );
    assertEquals( "the letter a",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token t = stream.get( FACTORY, TOKENIZER );
    assertEquals( "blank space  ", t.toString() );
    assertEquals( "the letter c",
                  stream.get( FACTORY, TOKENIZER ).toString() );
  }

  /**
   * ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCr3() throws Exception {

    TokenStream stream = makeStream( "\naaa\n  x" );
    assertEquals( "the control sequence \\par",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the letter a", stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the letter a", stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the letter a", stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the letter x", stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore("gene: the double \\par at the beginning is curious")
  public void testCr4() throws Exception {

    TokenStream stream = makeStream( "\n\nx" );
    assertEquals( "the control sequence \\par",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the control sequence \\par",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the letter x", stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * A single dollar is parsed as math shift character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDollar() throws Exception {

    TokenStream stream = makeStream( "$" );
    assertEquals( "math shift character $",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The empty string does not contain any characters
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty() throws Exception {

    TokenStream stream = makeStream( "" );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * \abc at the end of the file is recognized as control sequence
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscape0() throws Exception {

    TokenStream stream = makeStream( "\\abc" );
    assertEquals( "the control sequence \\abc",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * \abc followed by a space eats up the space.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscape1() throws Exception {

    TokenStream stream = makeStream( "\\abc " );
    assertEquals( "the control sequence \\abc",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * \ at the end of file parses the empty control sequence. It is
   * followed by an artificial \par.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscape2() throws Exception {

    TokenStream stream = makeStream( "\\" );
    assertEquals( "the control sequence \\",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the control sequence \\par", // TODO: is this correct?
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNull( token );
  }

  /**
   * \2 is parsed as a control sequence even when followed by a
   * digit.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscape3() throws Exception {

    TokenStream stream = makeStream( "\\23" );
    assertEquals( "the control sequence \\2",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the character 3",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Caret escapes work even when interwoven into control sequence
   * parsing.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscape4() throws Exception {

    TokenStream stream = makeStream( "\\a^^41c " );
    assertEquals( "the control sequence \\aAc",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Caret escapes work even when encountered at the beginning of
   * control sequence parsing.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscape5() throws Exception {

    TokenStream stream = makeStream( "\\^^41c " );
    assertEquals( "the control sequence \\Ac",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Caret escapes work even when encountered when reading the
   * escape character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscape6() throws Exception {

    TokenStream stream = makeStream( "^^5cc " );
    assertEquals( "the control sequence \\c",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The hash mark is parsed as macro parameter character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHash() throws Exception {

    TokenStream stream = makeStream( "#" );
    assertEquals( "macro parameter character #",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * An ignored character does not appear in the token stream.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIgnore() throws Exception {

    TokenStream stream = makeStream( "\f." );
    assertEquals( "the character .", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * An invalid character does not appear in the token stream.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInvalid() throws Exception {

    TokenStream stream = makeStream( "\b." );
    try {
      stream.get( FACTORY, TOKENIZER );
      assertFalse( true );
    } catch( ScannerException e ) {
      assertTrue( true );
    }
    assertEquals( "the character .", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIsEof1() throws Exception {

    TokenStream stream = makeStream( ". " );
    assertNotNull( stream.get( FACTORY, TOKENIZER ) );
    assertFalse( "something left", stream.isEof() );
    assertNotNull( stream.get( FACTORY, TOKENIZER ) );
    assertTrue( "nothing left", stream.isEof() );
  }

  /**
   * Test that isEof() reports {@code false} if something is left on the stream
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIsEof2() throws Exception {

    TokenStream stream = makeStream( "\\ab cd" );
    assertNotNull( stream.get( FACTORY, TOKENIZER ) );
    assertFalse( "something left", stream.isEof() );
    assertNotNull( stream.get( FACTORY, TOKENIZER ) );
    assertFalse( "more left", stream.isEof() );
  }

  /**
   * The method isEol() returns false if something is left to read.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIsEol1() throws Exception {

    TokenStream stream = makeStream( "abc" );
    assertNotNull( stream.get( FACTORY, TOKENIZER ) );
    assertFalse( stream.isEol() );
  }

  /**
   * The method isEol() returns true if nothing is left to read.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIsEol2() throws Exception {

    TokenStream stream = makeStream( "" );
    assertTrue( stream.isEol() );
  }

  /**
   * A { is parsed as left brace character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLeftBrace() throws Exception {

    TokenStream stream = makeStream( "{" );
    assertEquals( "begin-group character {",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * A letter is parsed as letter; e.g. the letter A.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter() throws Exception {

    TokenStream stream = makeStream( "A" );
    assertEquals( "the letter A", stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Test that getLocator() returns something sensible.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLocator() throws Exception {

    TokenStream stream = makeStream( "abc" );
    Locator locator = stream.getLocator();
    assertNotNull( locator );
    assertEquals( "test:0:", locator.toString() );
    assertNotNull( stream.get( FACTORY, TOKENIZER ) );
    assertEquals( "test:0:", locator.toString() );
  }

  /**
   * An embedded space between letters is treated correctly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMixed() throws Exception {

    TokenStream stream = makeStream( "12 34" );
    assertEquals( "the character 1", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "the character 2", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertEquals( "the character 3", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "the character 4", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Attempts to read past the end of file do no harm.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testReread() throws Exception {

    TokenStream stream = makeStream( "" );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * A } is parsed as left brace character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRightBrace() throws Exception {

    TokenStream stream = makeStream( "}" );
    assertEquals( "end-group character }",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * A single space at the beginning of the processing is skipped
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSpace() throws Exception {

    TokenStream stream = makeStream( " ." );
    assertEquals( "the character .", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character period and space in sequence are parsed into appropriate
   * tokens
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSpace2() throws Exception {

    TokenStream stream = makeStream( ". " );
    assertEquals( "the character .", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The character period and two spaces in sequence are parsed
   * into appropriate tokens. The two spaces are collapsed into one.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSpace3() throws Exception {

    TokenStream stream = makeStream( ".  " );
    assertEquals( "the character .", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * Two spaces at the beginning are ignored.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSpaces() throws Exception {

    TokenStream stream = makeStream( "  ." );
    assertEquals( "the character .", stream.get( FACTORY, TOKENIZER )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * An ampersand is pared as alignment tab character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTab() throws Exception {

    TokenStream stream = makeStream( "&" );
    assertEquals( "alignment tab character &",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

  /**
   * The method toString() retorts the file, the line and the
   * column.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testToString() throws Exception {

    TokenStream stream = makeStream( "abc" );
    assertEquals( "test:0[1]:", stream.toString() );
  }

  /**
   * An underscore is parsed as subscript character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnderscore() throws Exception {

    TokenStream stream = makeStream( "_" );
    assertEquals( "subscript character _",
                  stream.get( FACTORY, TOKENIZER ).toString() );
    Token token = stream.get( FACTORY, TOKENIZER );
    assertNotNull( token );
    assertEquals( SPACE, token.getChar().getCodePoint() );
    assertNull( stream.get( FACTORY, TOKENIZER ) );
  }

}
