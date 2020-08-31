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
import org.extex.exbib.core.bst.code.impl.Empty;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code empty$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class EmptyTest {

  /**
   * The field {@code db} contains the database.
   */
  private DB db = null;

  /**
   * The field {@code entry} contains an entry.
   */
  private Entry entry;

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * Set-up method.
   *
   * @throws Exception in case of an error
   */
  @Before
  public void setUp() throws Exception {

    db = new DBImpl();
    entry = db.makeEntry( "book", "abc", null );
    entry.set( "author", new Value() );
    p = new BstInterpreter099c( db, new NullWriter(), null );
  }

  /**
   * Tear-down method.
   */
  @After
  public void tearDown() {

    p = null;
    db = null;
  }

  /**
   * Run one test for the function empty$.
   *
   * @param val the value of the argument
   * @param res the expected result
   * @throws Exception in case of an error
   */
  public void testEmpty( String val, int res ) throws Exception {

    p.push( new TString( val, null ) );
    new Empty( "empty$" ).execute( p, entry, null );
    assertEquals( res, p.popInteger( null ).getInt() );
    assertNull( p.popUnchecked() );
  }

  /**
   * The empty stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    new Empty( "empty$" ).execute( p, entry, null );
  }

  /**
   * A non-string (Integer) argument leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingStringException.class)
  public void testInteger() throws Exception {

    p.push( new TInteger( 123, null ) );
    new Empty( "empty$" ).execute( p, entry, null );
  }

  /**
   * A longer string ist not empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNo0() throws Exception {

    testEmpty( "title", 0 );
  }

  /**
   * A one-letter string is not empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNo1() throws Exception {

    testEmpty( "a", 0 );
  }

  /**
   * A one-character string is not empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNo2() throws Exception {

    testEmpty( ".", 0 );
  }

  /**
   * A one-digit string is not empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNo3() throws Exception {

    testEmpty( "1", 0 );
  }

  /**
   * A {@code null} is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testYes1() throws Exception {

    testEmpty( null, 1 );
  }

  /**
   * The empty string is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testYes2() throws Exception {

    testEmpty( "", 1 );
  }

  /**
   * A space-only string is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testYes3() throws Exception {

    testEmpty( " ", 1 );
  }

  /**
   * A tab-only string is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testYes4() throws Exception {

    testEmpty( "\t", 1 );
  }

  /**
   * A multi-space string is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testYes5() throws Exception {

    testEmpty( "  ", 1 );
  }

  /**
   * A multi-whitespace string is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testYes6() throws Exception {

    testEmpty( " \n", 1 );
  }

  /**
   * A multi-whitespace string is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testYes7() throws Exception {

    testEmpty( " \t", 1 );
  }

}
