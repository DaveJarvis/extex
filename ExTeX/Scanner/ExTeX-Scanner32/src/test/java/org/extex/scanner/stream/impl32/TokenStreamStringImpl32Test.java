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

package org.extex.scanner.stream.impl32;

import org.extex.core.UnicodeChar;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.stream.exception.ScannerNoUnicodeNameException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.junit.Before;
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
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TokenStreamStringImpl32Test {

  /**
   * const 32
   */
  private static final int C32 = 32;

  /**
   * The field {@code factory} contains the token factory to use.
   */
  private static TokenFactory factory;

  /**
   * The field {@code tokenizer} contains the tokenizer to use for
   * categorizing characters.
   */
  private static Tokenizer tokenizer;

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( TokenStreamStringImpl32Test.class );
  }


  public TokenStreamStringImpl32Test() {

  }

  /**
   * Create a stream of tokens fed from a string.
   *
   * @param line the input string
   * @return the new token stream
   * @throws IOException in case of an error
   */
  private TokenStream makeStream( String line ) throws IOException {

    return new TokenStreamImpl32( null, null, new StringReader( line ),
                                  Boolean.FALSE, "" );
  }

  /**
   * Sets up the fixture, for example, open a network connection. This method
   * is called before a test is executed.
   *
   * @throws Exception in case of an error
   */
  @Before
  public void setUp() throws Exception {

    factory = new TokenFactoryImpl();
    tokenizer = new Tokenizer() {

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
          case '\t':
          case '\r':
          case '\n':
          case ' ':
            return Catcode.SPACE;
          case '\0':
          case '\f':
            return Catcode.IGNORE;
          default:
            return Catcode.OTHER;
        }
      }

      @Override
      public String getNamespace() {

        return "";
      }

    };
  }

  /**
   * The digit 1 is parsed as other character and nothing more.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    TokenStream stream = makeStream( "1" );
    assertEquals( "the character 1", stream.get( factory, tokenizer )
                                           .toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * The digits 1 and 2 are parsed as other character and nothing more
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test12() throws Exception {

    TokenStream stream = makeStream( "12" );
    assertEquals( "the character 1", stream.get( factory, tokenizer )
                                           .toString() );
    assertEquals( "the character 2", stream.get( factory, tokenizer )
                                           .toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaret1() throws Exception {

    TokenStream stream = makeStream( "^1" );
    assertEquals( "superscript character ^", stream.get( factory, tokenizer )
                                                   .toString() );
    assertEquals( "the character 1", stream.get( factory, tokenizer )
                                           .toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA() throws Exception {

    TokenStream stream = makeStream( "^^41" );
    assertEquals( "the letter A", stream.get( factory, tokenizer ).toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA2() throws Exception {

    TokenStream stream = makeStream( "^^A" );
    assertEquals( "the character ^^A", stream.get( factory, tokenizer )
                                             .toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretA3() throws Exception {

    TokenStream stream = makeStream( "^^A;" );
    assertEquals( "the character ^^A", stream.get( factory, tokenizer )
                                             .toString() );
    assertEquals( "the character ;", stream.get( factory, tokenizer )
                                           .toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCaretEnd() throws Exception {

    TokenStream stream = makeStream( "^" );
    assertEquals( "superscript character ^", stream.get( factory, tokenizer )
                                                   .toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCr1() throws Exception {

    TokenStream stream = makeStream( "x\nx" );
    assertEquals( "the letter x", stream.get( factory, tokenizer ).toString() );
    assertEquals( "blank space  ",
                  stream.get( factory, tokenizer ).toString() );
    assertEquals( "the letter x", stream.get( factory, tokenizer ).toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testCr2() throws Exception {

    TokenStream stream = makeStream( "x\n\n" );
    assertEquals( "the letter x", stream.get( factory, tokenizer ).toString() );
    Token t = stream.get( factory, tokenizer );
    assertEquals( C32, t.getChar().getCodePoint() );
    assertNotNull( t );
    assertEquals( "the control sequence \\par", t.toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCr3() throws Exception {

    TokenStream stream = makeStream( "\naaa\n  x" );
    assertEquals( "the letter a", stream.get( factory, tokenizer ).toString() );
    assertEquals( "the letter a", stream.get( factory, tokenizer ).toString() );
    assertEquals( "the letter a", stream.get( factory, tokenizer ).toString() );
    assertEquals( "blank space  ",
                  stream.get( factory, tokenizer ).toString() );
    assertEquals( "the letter x", stream.get( factory, tokenizer ).toString() );
    assertEquals( "blank space  ",
                  stream.get( factory, tokenizer ).toString() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testCr4() throws Exception {

    TokenStream stream = makeStream( "\n\nx" );
    assertEquals( "the control sequence \\par",
                  stream.get( factory, tokenizer ).toString() );
    assertEquals( "the control sequence \\par",
                  stream.get( factory, tokenizer ).toString() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * The empty string does not contain any characters
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty() throws Exception {

    TokenStream stream = makeStream( "" );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * Test hex notation with '^^^^'
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHex1() throws Exception {

    TokenStream stream = makeStream( "^^^^41 " );
    assertEquals( "the letter A", stream.get( factory, tokenizer ).toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );

  }

  /**
   * Test hex notation with '^^^^'
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHex2() throws Exception {

    TokenStream stream = makeStream( "^^^^fffe" );
    Token t = stream.get( factory, tokenizer );
    assertTrue( t instanceof OtherToken );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * Test hex notation with '^^^^'
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHex3() throws Exception {

    TokenStream stream = makeStream( "^^^^016e " );
    Token to = stream.get( factory, tokenizer );
    assertEquals( 366, to.getChar().getCodePoint() );

  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIgnore() throws Exception {

    TokenStream stream = makeStream( "\f." );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( "the character .", token.toString() );
    assertEquals( "blank space  ",
                  stream.get( factory, tokenizer ).toString() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter() throws Exception {

    TokenStream stream = makeStream( "A" );
    assertEquals( "the letter A", stream.get( factory, tokenizer ).toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * TODO
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMixed() throws Exception {

    TokenStream stream = makeStream( "12 34" );
    assertEquals( "the character 1", stream.get( factory, tokenizer )
                                           .toString() );
    assertEquals( "the character 2", stream.get( factory, tokenizer )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( factory, tokenizer ).toString() );
    assertEquals( "the character 3", stream.get( factory, tokenizer )
                                           .toString() );
    assertEquals( "the character 4", stream.get( factory, tokenizer )
                                           .toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * A single space at the beginning of the processing is skipped
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSpace() throws Exception {

    TokenStream stream = makeStream( " " );
    assertNull( stream.get( factory, tokenizer ) );
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
    assertEquals( "the character .", stream.get( factory, tokenizer )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( factory, tokenizer ).toString() );
    assertNull( stream.get( factory, tokenizer ) );
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
    assertEquals( "the character .", stream.get( factory, tokenizer )
                                           .toString() );
    assertEquals( "blank space  ",
                  stream.get( factory, tokenizer ).toString() );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * Two spaces at the beginning are ignored.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSpaces() throws Exception {

    TokenStream stream = makeStream( "  " );
    assertNull( stream.get( factory, tokenizer ) );
  }

  /**
   * Test one Unicode name 'a'
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnicodeName1() throws Exception {

    TokenStream stream = makeStream( "^^^LATIN SMALL LETTER A;" );
    assertEquals( "the letter a", stream.get( factory, tokenizer ).toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );

  }

  /**
   * Test one Unicode name 'A'
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnicodeName2() throws Exception {

    TokenStream stream = makeStream( "^^^LATIN CAPITAL LETTER A;" );
    assertEquals( "the letter A", stream.get( factory, tokenizer ).toString() );
    Token token = stream.get( factory, tokenizer );
    assertNotNull( token );
    assertEquals( C32, token.getChar().getCodePoint() );
    assertNull( stream.get( factory, tokenizer ) );

  }

  /**
   * Test one Unicode name 'error'
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnicodeName3() throws Exception {

    TokenStream stream = makeStream( "^^^LATIN ERROR LETTER A;" );
    try {
      assertEquals( "the letter A", stream.get( factory, tokenizer )
                                          .toString() );
      assertTrue( false );
    } catch( ScannerNoUnicodeNameException e ) {
      assertTrue( true );
    }
  }

}
