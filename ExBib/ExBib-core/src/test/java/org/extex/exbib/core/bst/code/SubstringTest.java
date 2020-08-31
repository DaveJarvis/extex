/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.code;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.Substring;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code substring$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class SubstringTest {

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * Run a test case.
   *
   * @param s    the string
   * @param from the start index
   * @param len  the length
   * @param res  the expected result
   * @throws Exception in case of an error
   */
  private void runTest( String s, int from, int len, String res )
      throws Exception {

    p.push( new TString( s, null ) );
    p.push( new TInteger( from, null ) );
    p.push( new TInteger( len, null ) );
    new Substring( "substring$" ).execute( p, null, null );
    assertEquals( res, p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

  /**
   * Set-up method.
   *
   * @throws Exception in case of an error
   */
  @Before
  public void setUp() throws Exception {

    p = new BstInterpreter099c( new DBImpl(), new NullWriter(), null );
  }

  /**
   * Tear-down method.
   */
  @After
  public void tearDown() {

    p = null;
  }

  /**
   * The empty stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    new Substring( "substring$" ).execute( p, null, null );
  }

  /**
   * A short stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testShortStack1() throws Exception {

    p.push( TokenFactory.T_ZERO );
    new Substring( "substring$" ).execute( p, null, null );
  }

  /**
   * A short stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testShortStack2() throws Exception {

    p.push( TokenFactory.T_ZERO );
    p.push( TokenFactory.T_ZERO );
    new Substring( "substring$" ).execute( p, null, null );
  }

  /**
   * substring$("abcd", 0, 0) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringFour1() throws Exception {

    runTest( "abcd", 0, 0, "" );
  }

  /**
   * substring$("abcd", 1, 1) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringFour2() throws Exception {

    runTest( "abcd", 1, 1, "a" );
  }

  /**
   * substring$("abcd", -1, 1) = "d"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringFour3() throws Exception {

    runTest( "abcd", -1, 1, "d" );
  }

  /**
   * substring$("abcd", 2, 1) = "b"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringFour4() throws Exception {

    runTest( "abcd", 2, 1, "b" );
  }

  /**
   * substring$("abcd", -2, 1) = "c"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringFour5() throws Exception {

    runTest( "abcd", -2, 1, "c" );
  }

  /**
   * substring$("abcd", 1, 2) = "ab"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringFour6() throws Exception {

    runTest( "abcd", 1, 2, "ab" );
  }

  /**
   * substring$("abcd", -1, 2) = "cd"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringFour7() throws Exception {

    runTest( "abcd", -1, 2, "cd" );
  }

  /**
   * substring$("a", 0, 0) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne1() throws Exception {

    runTest( "a", 0, 0, "" );
  }

  /**
   * substring$("a", -2, -4) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne10() throws Exception {

    runTest( "a", -2, -4, "" );
  }

  /**
   * substring$("a", -3, -6) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne11() throws Exception {

    runTest( "a", -3, -6, "" );
  }

  /**
   * substring$("a", 1, 1) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne2() throws Exception {

    runTest( "a", 1, 1, "a" );
  }

  /**
   * substring$("a", -1, 1) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne3() throws Exception {

    runTest( "a", -1, 1, "a" );
  }

  /**
   * substring$("a", 2, 1) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne4() throws Exception {

    runTest( "a", 2, 1, "" );
  }

  /**
   * substring$("a", -2, 1) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne5() throws Exception {

    runTest( "a", -2, 1, "" );
  }

  /**
   * substring$("a", 1, 2) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne6() throws Exception {

    runTest( "a", 1, 2, "a" );
  }

  /**
   * substring$("a", -1, 2) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne7() throws Exception {

    runTest( "a", -1, 2, "a" );
  }

  /**
   * substring$("a", -2, -2) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne8() throws Exception {

    runTest( "a", -2, -2, "" );
  }

  /**
   * substring$("a", -2, -3) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringOne9() throws Exception {

    runTest( "a", -2, -3, "" );
  }

  /**
   * substring$("abc", 0, 0) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringThree1() throws Exception {

    runTest( "abc", 0, 0, "" );
  }

  /**
   * substring$("abc", 1, 1) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringThree2() throws Exception {

    runTest( "abc", 1, 1, "a" );
  }

  /**
   * substring$("abc", -1, 1) = "c"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringThree3() throws Exception {

    runTest( "abc", -1, 1, "c" );
  }

  /**
   * substring$("abc", 2, 1) = "b"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringThree4() throws Exception {

    runTest( "abc", 2, 1, "b" );
  }

  /**
   * substring$("abc", -2, 1) = "b"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringThree5() throws Exception {

    runTest( "abc", -2, 1, "b" );
  }

  /**
   * substring$("abc", 1, 2) = "ab"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringThree6() throws Exception {

    runTest( "abc", 1, 2, "ab" );
  }

  /**
   * substring$("abc", -1, 2) = "bc"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringThree7() throws Exception {

    runTest( "abc", -1, 2, "bc" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", 0, 2) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix1() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", 0, 2, "" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", -26, 2) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix10() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", -26, 2, "a" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", -27, 2) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix11() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", -27, 2, "" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", 1, 2) = "ab"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix2() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", 1, 2, "ab" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", 2, 2) = "bc"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix3() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", 2, 2, "bc" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", 25, 2) = "yz"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix4() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", 25, 2, "yz" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", 26, 2) = "z"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix5() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", 26, 2, "z" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", 27, 2) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix6() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", 27, 2, "" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", -1, 2) = "yz"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix7() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", -1, 2, "yz" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", -2, 2) = "xy"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix8() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", -2, 2, "xy" );
  }

  /**
   * substring$("abcdefghijklmnopqrstuvwxyz", -25, 2) = "ab"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwentysix9() throws Exception {

    runTest( "abcdefghijklmnopqrstuvwxyz", -25, 2, "ab" );
  }

  /**
   * substring$("ab", 0, 0) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwo1() throws Exception {

    runTest( "ab", 0, 0, "" );
  }

  /**
   * substring$("ab", 1, 1) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwo2() throws Exception {

    runTest( "ab", 1, 1, "a" );
  }

  /**
   * substring$("ab", -1, 1) = "b"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwo3() throws Exception {

    runTest( "ab", -1, 1, "b" );
  }

  /**
   * substring$("ab", 2, 1) = "b"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwo4() throws Exception {

    runTest( "ab", 2, 1, "b" );
  }

  /**
   * substring$("ab", -2, 1) = "a"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwo5() throws Exception {

    runTest( "ab", -2, 1, "a" );
  }

  /**
   * substring$("ab", 1, 2) = "ab"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwo6() throws Exception {

    runTest( "ab", 1, 2, "ab" );
  }

  /**
   * substring$("ab", -1, 2) = "ab"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringTwo7() throws Exception {

    runTest( "ab", -1, 2, "ab" );
  }

  /**
   * substring$("", 0, 0) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringZero1() throws Exception {

    runTest( "", 0, 0, "" );
  }

  /**
   * substring$("", 1, 1) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringZero2() throws Exception {

    runTest( "", 1, 1, "" );
  }

  /**
   * substring$("", -1, 1) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringZero3() throws Exception {

    runTest( "", -1, 1, "" );
  }

  /**
   * substring$("", 2, 1) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringZero4() throws Exception {

    runTest( "", 2, 1, "" );
  }

  /**
   * substring$("", -2, 1) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringZero5() throws Exception {

    runTest( "", -2, 1, "" );
  }

  /**
   * substring$("", 1, 2) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringZero6() throws Exception {

    runTest( "", 1, 2, "" );
  }

  /**
   * substring$("", -1, 2) = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstringZero7() throws Exception {

    runTest( "", -1, 2, "" );
  }

}
