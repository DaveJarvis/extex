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
import org.extex.exbib.core.bst.code.impl.TextPrefix;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for {@code text.prefix$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TextPrefixTest {

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
  }

  /**
   * Tear-down method.
   */
  @After
  public void tearDown() {

    p = null;
  }

  /**
   * text.prefix$ complains about an empty stack.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    new TextPrefix( "text.prefix$" ).execute( p, null, null );
  }

  /**
   * text.prefix$ complains about a stack containing only one argument
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testShortStack1() throws Exception {

    p.push( TokenFactory.T_ZERO );
    new TextPrefix( "text.prefix$" ).execute( p, null, null );
  }

  /**
   * "123" #2 text.prefix$ --> "12".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix1() throws Exception {

    testTextPrefixing( "123", 2, "12" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix11() throws Exception {

    testTextPrefixing( "1{23}4567", 1, "1" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix12() throws Exception {

    testTextPrefixing( "1{23}4567", 2, "1{2}" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix13() throws Exception {

    testTextPrefixing( "1{23}4567", 3, "1{23}" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix14() throws Exception {

    testTextPrefixing( "1{23}4567", 4, "1{23}4" );
  }

  /**
   * "123" #5 text.prefix$ --> "123".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix2() throws Exception {

    testTextPrefixing( "123", 5, "123" );
  }

  /**
   * "+*@" #2 text.prefix$ --> "+*".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix21() throws Exception {

    testTextPrefixing( "+*@", 2, "+*" );
  }

  /**
   * "+*@" #5 text.prefix$ --> "+*@".
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix22() throws Exception {

    testTextPrefixing( "+*@", 5, "+*@" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix31() throws Exception {

    testTextPrefixing( "+{*@}abc7", 1, "+" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix32() throws Exception {

    testTextPrefixing( "+{*@}abc7", 2, "+{*}" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix33() throws Exception {

    testTextPrefixing( "+{*@}abc7", 3, "+{*@}" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix34() throws Exception {

    testTextPrefixing( "+{*@}abc7", 4, "+{*@}a" );
  }

  /**
   * text.prefix$ treats braces as units.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix44() throws Exception {

    testTextPrefixing( "Ab{\\def{12}}xyz", 4, "Ab{\\def{12}}x" );
  }

  /**
   * text.prefix$ can cope with the empty string.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefixEmpty() throws Exception {

    testTextPrefixing( "", 0, "" );
  }

  /**
   * text.prefix$ can cope with the empty string.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefixEmpty2() throws Exception {

    testTextPrefixing( "", 1, "" );
  }

  /**
   * Run a test case.
   *
   * @param s   the string
   * @param len the length
   * @param res the result
   * @throws Exception in case of an error
   */
  private void testTextPrefixing( String s, int len, String res )
      throws Exception {

    p.push( new TString( s, null ) );
    p.push( new TInteger( len, null ) );
    new TextPrefix( "text.prefix$" ).execute( p, null, null );
    assertEquals( res, p.popString( null ).getValue() );
    assertNull( p.popUnchecked() );
  }

}
