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
import org.extex.exbib.core.bst.code.impl.Write;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.*;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.StringBufferWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code write$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class WriteTest {

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * The field {@code out} contains the output buffer.
   */
  private StringBuffer out = null;

  /**
   * Set-up method.
   *
   * @throws Exception in case of an error
   */
  @Before
  public void setUp() throws Exception {

    out = new StringBuffer();
    p = new BstInterpreter099c( new DBImpl(),
                                new StringBufferWriter( out ), null );
  }

  /**
   * Tear-down method.
   */
  @After
  public void tearDown() {

    p = null;
  }

  /**
   * write$ needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    new Write( "write$" ).execute( p, null, null );
  }

  /**
   * write$ can write a block.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWriteBlock1() throws Exception {

    TBlock block = new TBlock( null );
    block.add( new TString( "abc", null ) );
    p.push( block );
    new Write( "write$" ).execute( p, null, null );
    assertEquals( "abc", out.toString() );
    assertNull( p.popUnchecked() );
  }

  /**
   * write$ can write a character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWriteChar1() throws Exception {

    p.push( new TChar( 'a', null ) );
    new Write( "write$" ).execute( p, null, null );
    assertEquals( "a", out.toString() );
    assertNull( p.popUnchecked() );
  }

  /**
   * write$ can write a number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWriteNumber1() throws Exception {

    p.push( new TInteger( 123, null ) );
    new Write( "write$" ).execute( p, null, null );
    assertEquals( "123", out.toString() );
    assertNull( p.popUnchecked() );
  }

  /**
   * write$ can write a QLiteral.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWriteQLiteral1() throws Exception {

    p.push( new TQLiteral( "abc", null ) );
    new Write( "write$" ).execute( p, null, null );
    assertEquals( "abc", out.toString() );
    assertNull( p.popUnchecked() );
  }

  /**
   * write$ can write a string.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWriteString1() throws Exception {

    p.push( new TString( "abc", null ) );
    new Write( "write$" ).execute( p, null, null );
    assertEquals( "abc", out.toString() );
    assertNull( p.popUnchecked() );
  }

}
