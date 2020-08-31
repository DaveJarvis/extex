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
import org.extex.exbib.core.bst.code.impl.Concat;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code concat$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ConcatTest {

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * Apply {@code concat$} on two strings and check the result.
   *
   * @param s1     the first string to concatenate
   * @param s2     the second string to concatenate
   * @param result the expected result
   * @throws Exception in case of an error
   */
  private void runTest( String s1, String s2, String result ) throws Exception {

    p.push( new TString( s1, null ) );
    p.push( new TString( s2, null ) );
    new Concat( "*" ).execute( p, null, null );
    assertEquals( result, p.popUnchecked().getValue() );
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
   * An short stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void test1Stack() throws Exception {

    p.push( new TString( "abc", null ) );
    new Concat( "*" ).execute( p, null, null );
  }

  /**
   * "ab" + "cd" = "abcd"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConcat() throws Exception {

    runTest( "ab", "cd", "abcd" );
  }

  /**
   * "" + "" = ""
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConcatEmpty() throws Exception {

    runTest( "", "", "" );
  }

  /**
   * "" + "abc" = "abc"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConcatEmptyLeft() throws Exception {

    runTest( "", "abc", "abc" );
  }

  /**
   * "abc" + "" = "abc"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConcatEmptyRight() throws Exception {

    runTest( "abc", "", "abc" );
  }

  /**
   * An empty stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    new Concat( "*" ).execute( p, null, null );
  }

  /**
   * The first argument needs to be a string.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingStringException.class)
  public void testTypeError1() throws Exception {

    p.push( new TInteger( 123, null ) );
    new Concat( "*" ).execute( p, null, null );
  }

  /**
   * The second argument needs to be a string.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingStringException.class)
  public void testTypeError2() throws Exception {

    p.push( new TInteger( 123, null ) );
    p.push( new TString( "123", null ) );
    new Concat( "*" ).execute( p, null, null );
  }

}
