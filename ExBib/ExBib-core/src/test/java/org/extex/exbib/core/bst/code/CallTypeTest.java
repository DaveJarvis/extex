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
import org.extex.exbib.core.bst.code.impl.CallType;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test suite for {@code call.type$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CallTypeTest implements Code {

  /**
   * The field {@code executed} contains the executed flag.
   */
  private boolean executed = false;

  /**
   * The field {@code db} contains the database.
   */
  private DB db = null;

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    executed = true;
  }

  public String getName() {

    return "";
  }

  public void setName( String name ) {

    //
  }

  /**
   * Set-up method.
   *
   * @throws Exception in case of an error
   */
  @Before
  public void setUp() throws Exception {

    db = new DBImpl();
    p = new BstInterpreter099c( db, new NullWriter(), null );
    p.addFunction( "book", this, null );
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
   * call.type$ invokes an appropriate function.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test0() throws Exception {

    Entry e = new Entry( null );
    e.setType( "book" );
    executed = false;
    new CallType( "call.type$" ).execute( p, e, null );
    assertNull( p.popUnchecked() );
    assertTrue( executed );
  }

  /**
   * call.type$ invokes an appropriate function ignoring case.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    Entry e = new Entry( null );
    e.setType( "Book" );
    executed = false;
    new CallType( "call.type$" ).execute( p, e, null );
    assertNull( p.popUnchecked() );
    assertTrue( executed );
  }

  /**
   * call.type$ invokes an appropriate function ignoring case.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    Entry e = new Entry( null );
    e.setType( "BOOK" );
    executed = false;
    new CallType( "call.type$" ).execute( p, e, null );
    assertNull( p.popUnchecked() );
    assertTrue( executed );
  }

  /**
   * call.type$ invokes an appropriate function.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    Entry e = new Entry( null );
    e.setType( "article" );
    executed = false;
    new CallType( "call.type$" ).execute( p, e, null );
    assertNull( p.popUnchecked() );
    assertTrue( !executed );

    p.addFunction( "article", this, null );
    new CallType( "call.type$" ).execute( p, e, null );
    assertNull( p.popUnchecked() );
    assertTrue( executed );
  }

  /**
   * call.type$ invokes an appropriate function.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    Entry e = new Entry( null );
    e.setType( "misc" );
    executed = false;
    new CallType( "call.type$" ).execute( p, e, null );
    assertNull( p.popUnchecked() );
    assertTrue( !executed );

    p.addFunction( "default.type", this, null );
    new CallType( "call.type$" ).execute( p, e, null );
    assertNull( p.popUnchecked() );
    assertTrue( executed );
  }

}
