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
import org.extex.exbib.core.bst.code.impl.Width;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code width$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class WidthTest {

  /**
   * The field {@code p} contains the processor.
   */
  private BstProcessor p = null;

  /**
   * The field {@code err} contains the error output.
   */
  private final StringBuffer err = new StringBuffer();

  /**
   * The field {@code out} contains the standard output.
   */
  private final StringBuffer out = new StringBuffer();

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
   * Run a test.
   *
   * @param s the string to be tested
   * @param w the expected width
   * @throws Exception in case of an error
   */
  private void testWidth( String s, int w ) throws Exception {

    p.push( new TString( s, null ) );
    new Width( "width$" ).execute( p, null, null );
    assertEquals( "", err.toString() );
    assertEquals( "", out.toString() );
    assertEquals( w, p.popInteger( null ).getInt() );
    assertNull( p.popUnchecked() );
  }

  /**
   * Test "ae".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0110() throws Exception {

    testWidth( "ae", 944 );
  }

  /**
   * Test "\AE".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0111() throws Exception {

    testWidth( "\\AE", 1931 );
  }

  /**
   * Test "oe".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0112() throws Exception {

    testWidth( "oe", 944 );
  }

  /**
   * Test "\OE".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0113() throws Exception {

    testWidth( "\\OE", 1959 );
  }

  /**
   * Test "\singleletter".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0114() throws Exception {

    testWidth( "\\singleletter", 5286 );
  }

  /**
   * Test ".\singleletter.".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0115() throws Exception {

    testWidth( ".\\singleletter.", 5842 );
  }

  /**
   * Test "OX{\\singleletter{stoc}}83b".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0116() throws Exception {

    testWidth( "OX{\\singleletter{stoc}}83b", 4811 );
  }

  /**
   * Test "OX{\\singleletter{stoc}}83b".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0117() throws Exception {

    testWidth( "OX{\\singleletter  {stoc}}83b", 4811 );
  }

  /**
   * Test "OXstoc83b".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0118() throws Exception {

    testWidth( "OXstoc83b", 4811 );
  }

  /**
   * Test "\AE".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth0119() throws Exception {

    testWidth( "{\\AE}", 903 );
  }

  /**
   * Test "abc".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth012() throws Exception {

    testWidth( "abc", 1500 );
  }

  /**
   * Test lowercase letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth013() throws Exception {

    testWidth( "abcdefghijklmnopqrstuvwxyz", 12706 );
  }

  /**
   * Test ".,-=)(/&%$\u00a7!".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth014() throws Exception {

    testWidth( ".,-=)(/&%$\u00a7!", 5334 );
  }

  /**
   * Test "{}".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth015() throws Exception {

    testWidth( "{}", 1000 );
  }

  /**
   * Test "AA".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth016() throws Exception {

    testWidth( "AA", 1500 );
  }

  /**
   * Test "\AA".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth017() throws Exception {

    testWidth( "\\AA", 2000 );
  }

  /**
   * Test "ss".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth018() throws Exception {

    testWidth( "ss", 788 );
  }

  /**
   * Test "\ss".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWidth019() throws Exception {

    testWidth( "\\ss", 1288 );
  }

}
