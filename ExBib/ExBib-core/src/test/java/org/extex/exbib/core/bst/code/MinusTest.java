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
import org.extex.exbib.core.bst.code.impl.Minus;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code -}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MinusTest {

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * Run a test case.
   *
   * @param t1 the first integer
   * @param t2 the second integer
   * @throws Exception in case of an error
   */
  private void runTest( int t1, int t2 ) throws Exception {

    p.push( new TInteger( t2, null ) );
    p.push( new TInteger( t1, null ) );
    new Minus( "-" ).execute( p, null, null );
    assertEquals( t2 - t1, p.popInteger( null ).getInt() );
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
   * A short stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void test1Stack() throws Exception {

    p.push( new TInteger( 2, null ) );
    new Minus( "-" ).execute( p, null, null );
  }

  /**
   * An empty stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    new Minus( "-" ).execute( p, null, null );
  }

  /**
   * A type error in the first argument leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingNumberException.class)
  public void testTypeError1() throws Exception {

    p.push( new TString( "2", null ) );
    p.push( new TInteger( 2, null ) );
    new Minus( "-" ).execute( p, null, null );
  }

  /**
   * A type error in the second argument leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingNumberException.class)
  public void testTypeError2() throws Exception {

    p.push( new TString( "2", null ) );
    p.push( new TInteger( 2, null ) );
    new Minus( "-" ).execute( p, null, null );
  }

  /**
   * Test 0 - 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus00() throws Exception {

    runTest( 0, 0 );
  }

  /**
   * Test 1 - 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus10() throws Exception {

    runTest( 1, 0 );
  }

  /**
   * Test 1 - 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus11() throws Exception {

    runTest( 1, 1 );
  }

  /**
   * Test 1 - 2.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus12() throws Exception {

    runTest( 1, 2 );
  }

  /**
   * Test 123 - 123.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus123123() throws Exception {

    runTest( 123, 123 );
  }

  /**
   * Test 123 - -123.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus123Minus123() throws Exception {

    runTest( 123, -123 );
  }

  /**
   * Test 1 - -1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus1Minus1() throws Exception {

    runTest( 1, -1 );
  }

  /**
   * Test 2 - 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus21() throws Exception {

    runTest( 2, 1 );
  }

  /**
   * Test 2 - 2.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinus22() throws Exception {

    runTest( 2, 2 );
  }

  /**
   * Test -123 - 123.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinusMinus123123() throws Exception {

    runTest( -123, 123 );
  }

  /**
   * Test -1 - -2.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinusMinus1Minus2() throws Exception {

    runTest( -1, -2 );
  }

  /**
   * Test -2 - -1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void textMinusMinus2Minus1() throws Exception {

    runTest( -2, -1 );
  }

}
