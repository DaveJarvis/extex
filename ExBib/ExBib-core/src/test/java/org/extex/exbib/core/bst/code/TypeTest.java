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
import org.extex.exbib.core.bst.code.impl.Type;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code type$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TypeTest {

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
   * A lowercase type is taken as is.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test0() throws Exception {

    Entry e = new Entry( null );
    e.setType( "book" );
    new Type( "type$" ).execute( p, e, null );
    assertEquals( "book", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

  /**
   * A cased type is taken as is.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    Entry e = new Entry( null );
    e.setType( "Book" );
    new Type( "type$" ).execute( p, e, null );
    assertEquals( "book", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

  /**
   * An uppercase type is taken as is.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    Entry e = new Entry( null );
    e.setType( "BOOK" );
    new Type( "type$" ).execute( p, e, null );
    assertEquals( "book", p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

}
