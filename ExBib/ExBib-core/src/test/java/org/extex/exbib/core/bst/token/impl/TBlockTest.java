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

package org.extex.exbib.core.bst.token.impl;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link TBlock}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TBlockTest {

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

    p = new BstInterpreter099c( new DBImpl(), new NullWriter(), null );
    p.addFunction( "abc", TokenFactory.T_ONE, null );
  }

  /**
   * Tear-down method.
   */
  @After
  public void tearDown() {

    p = null;
  }

  /**
   * A Literal executes to itself.
   *
   * @throws Exception in case of an error
   */
  public void testExecute2() throws Exception {

    TLiteral l = new TLiteral( "abc", null );
    TBlock t = new TBlock( null );
    t.add( l );
    t.execute( p, null, null );

    Token x = p.pop( null );
    assertNull( p.popUnchecked() );
    assertTrue( x instanceof TInteger );
    assertEquals( "1", x.getValue() );
  }

  /**
   * An empty block expands to the empty string.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand1() throws Exception {

    TBlock t = new TBlock( null );
    String s = t.expand( p );

    assertNull( p.popUnchecked() );
    assertEquals( "", s );
  }

  /**
   * toString() works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testToString() throws Exception {

    assertEquals( "{}", new TBlock( null ).toString() );
  }

  /**
   * The token visitor invokes the correct method.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit() throws Exception {

    TBlock t = new TBlock( null );
    RecordingTokenVisitor tv = new RecordingTokenVisitor();
    t.visit( tv );
    assertEquals( t, tv.getVisited() );
  }

}
