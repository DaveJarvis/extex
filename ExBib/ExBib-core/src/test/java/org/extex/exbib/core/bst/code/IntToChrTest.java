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
import org.extex.exbib.core.bst.code.impl.IntToChr;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
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
 * Test suite for {@code int.to.chr$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IntToChrTest {

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * Create an object to be tested.
   *
   * @return the test object
   */
  protected Code makeTestInstance() {

    return new IntToChr( "int.to.chr$" );
  }

  /**
   * Run a test case.
   *
   * @param t1 the argument
   * @throws Exception in case of an error
   */
  private void runTest( int t1 ) throws Exception {

    p.push( new TInteger( t1, null ) );
    makeTestInstance().execute( p, null, null );
    assertEquals( String.valueOf( (char) t1 ), p.popString( null ).getValue() );
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
   * An empty stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    makeTestInstance().execute( p, null, null );
  }

  /**
   * Test the character 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIntToChr0() throws Exception {

    runTest( 0 );
  }

  /**
   * Test the character 123.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIntToChr123() throws Exception {

    runTest( 123 );
  }

  /**
   * Test the character 32.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIntToChr32() throws Exception {

    runTest( 32 );
  }

  /**
   * -1 is no character and leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibIllegalValueException.class)
  public void testIntToChrMinus1() throws Exception {

    runTest( -1 );
  }

  /**
   * A string argument leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingNumberException.class)
  public void testTypeError1() throws Exception {

    p.push( new TString( "abc", null ) );
    makeTestInstance().execute( p, null, null );
  }

}
