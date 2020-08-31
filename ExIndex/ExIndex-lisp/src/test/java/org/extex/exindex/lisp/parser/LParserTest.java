/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.lisp.parser;

import org.extex.exindex.lisp.exception.SyntaxException;
import org.extex.exindex.lisp.type.value.LDouble;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LValue;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * This is a test suite for the L parser.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LParserTest {

  /**
   * Create a parser.
   *
   * @param in the content of the input stream
   * @return the parser
   */
  private LParser makeParser( String in ) {

    return new LParser( new InputStreamReader( new ByteArrayInputStream( in
                                                                             .getBytes() ) ),
                        "rsc" );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test(expected = NullPointerException.class)
  public final void testRead1() throws IOException {

    LParser parser = new LParser( null, "rsc" );
    parser.read();
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testRead2() throws IOException {

    LParser parser = makeParser( "" );
    assertNull( parser.read() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testRead3() throws IOException {

    LParser parser = makeParser( ";abc" );
    assertNull( parser.read() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testRead4() throws IOException {

    LParser parser = makeParser( ";abc\n   ; def" );
    assertNull( parser.read() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadFloat1() throws IOException {

    LParser parser = makeParser( ".12" );
    LValue n = parser.read();
    assertTrue( n instanceof LDouble );
    assertEquals( .12d, ((LDouble) n).getValue(), .00001 );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadFloat2() throws IOException {

    LParser parser = makeParser( "1.2" );
    LValue n = parser.read();
    assertTrue( n instanceof LDouble );
    assertEquals( 1.2d, ((LDouble) n).getValue(), .00001 );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadFloat3() throws IOException {

    LParser parser = makeParser( "123." );
    LValue n = parser.read();
    assertTrue( n instanceof LDouble );
    assertEquals( 123.d, ((LDouble) n).getValue(), .00001 );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadNil1() throws IOException {

    LParser parser = makeParser( "nil" );
    LValue n = parser.read();
    assertTrue( n instanceof LList );
    assertEquals( 0, ((LList) n).size() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadNil2() throws IOException {

    LParser parser = makeParser( "()" );
    LValue n = parser.read();
    assertTrue( n instanceof LList );
    assertEquals( 0, ((LList) n).size() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadNil3() throws IOException {

    LParser parser = makeParser( "(  )" );
    LValue n = parser.read();
    assertTrue( n instanceof LList );
    assertEquals( 0, ((LList) n).size() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadNumber1() throws IOException {

    LParser parser = makeParser( "0" );
    LValue n = parser.read();
    assertTrue( n instanceof LNumber );
    assertEquals( 0L, ((LNumber) n).getValue() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadNumber2() throws IOException {

    LParser parser = makeParser( "12" );
    LValue n = parser.read();
    assertTrue( n instanceof LNumber );
    assertEquals( 12L, ((LNumber) n).getValue() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadNumber3() throws IOException {

    LParser parser = makeParser( "123" );
    LValue n = parser.read();
    assertTrue( n instanceof LNumber );
    assertEquals( 123L, ((LNumber) n).getValue() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test
  public final void testReadNumber4() throws IOException {

    LParser parser = makeParser( "-12" );
    LValue n = parser.read();
    assertTrue( n instanceof LNumber );
    assertEquals( -12L, ((LNumber) n).getValue() );
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test(expected = SyntaxException.class)
  public final void testReadNumber5() throws IOException {

    LParser parser = makeParser( "-" );
    parser.read();
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test(expected = SyntaxException.class)
  public final void testReadNumber6() throws IOException {

    LParser parser = makeParser( "-." );
    parser.read();
  }

  /**
   * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
   *
   * @throws IOException in case of an error
   */
  @Test(expected = SyntaxException.class)
  public final void testReadNumber7() throws IOException {

    LParser parser = makeParser( "." );
    parser.read();
  }

}
