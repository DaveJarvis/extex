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
import org.extex.exbib.core.bst.code.impl.Preamble;
import org.extex.exbib.core.db.*;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code preamble$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PreambleTest {

  /**
   * The field {@code db} contains the database.
   */
  private DB db = null;

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
   * preamble$ can extract a block preamble.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testBlock() throws Exception {

    db.storePreamble( new Value( new VBlock( "123" ) ) );
    new Preamble( "preamble$" ).execute( p, null, null );
    assertEquals( "123", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

  /**
   * initially the preamble is empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty() throws Exception {

    new Preamble( "preamble$" ).execute( p, null, null );
    assertEquals( "", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

  /**
   * preamble$ can extract a numeric preamble.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNumber() throws Exception {

    db.storePreamble( new Value( new VNumber( 123, "123" ) ) );
    new Preamble( "preamble$" ).execute( p, null, null );
    assertEquals( "123", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

  /**
   * preamble$ can extract a string preamble.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testString() throws Exception {

    db.storePreamble( new Value( new VString( "abc" ) ) );
    new Preamble( "preamble$" ).execute( p, null, null );
    assertEquals( "abc", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

  /**
   * preamble$ can extract a combined preamble.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testValue() throws Exception {

    Value v = new Value();
    v.add( new VBlock( "1234" ) );
    v.add( new VString( " # " ) );
    v.add( new VNumber( 123, "123" ) );
    db.storePreamble( v );
    new Preamble( "preamble$" ).execute( p, null, null );
    assertEquals( "1234 # 123", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

}
