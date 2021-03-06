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
import org.extex.exbib.core.bst.code.impl.Purify;
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

/**
 * Test suite for {@code purify$}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PurifyTest {

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
   * Run a test case.
   *
   * @param in  the input
   * @param res the expected result
   * @throws Exception in case of an error
   */
  private void test( String in, String res ) throws Exception {

    p.push( new TString( in, null ) );
    new Purify( "purify$" ).execute( p, null, null );
    assertEquals( res, p.popString( null ).getValue() );
  }

  /**
   * The empty stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibStackEmptyException.class)
  public void testEmptyStack() throws Exception {

    new Purify( "purify$" ).execute( p, null, null );
  }

  /**
   * Lowercase letters are left unchanged.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify1() throws Exception {

    test( "abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz" );
  }

  /**
   * \i is translated to i.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify10() throws Exception {

    test( "{\\'\\i}\\'\\i", "ii" );
  }

  /**
   * \j is translated to j.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify11() throws Exception {

    test( "{\\'\\j}\\'\\j", "jj" );
  }

  /**
   * \LaTeX is translated to LaTeX.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify12() throws Exception {

    test( "{{\\LaTeX}}123{x\\LaTeX}123\\LaTeX", "LaTeX123xLaTeX123LaTeX" );
  }

  /**
   * \TeX is translated to TeX.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify13() throws Exception {

    test( "{{\\TeX}}123{xxx\\TeX}123\\TeX", "TeX123xxxTeX123TeX" );
  }

  /**
   * Uppercase letters are left unchanged.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify2() throws Exception {

    test( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ" );
  }

  /**
   * Numbers are left unchanged.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify3() throws Exception {

    test( "0123456789", "0123456789" );
  }

  /**
   * Whitespace (including ~) is normalized to spaces.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify4() throws Exception {

    test( "a  b~c-d", "a  b c d" );
  }

  /**
   * Parentheses and brackets are discarded.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify5() throws Exception {

    test( "[]()", "" );
  }

  /**
   * Braces are discarded.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify6() throws Exception {

    test( "{}", "" );
  }

  /**
   * \ae and \AE are translated to ae and AE resp.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify7() throws Exception {

    test( "{\\ae}\\ae{\\AE}\\AE", "aeaeAEAE" );
  }

  /**
   * \oe and \OE are translated to oe and OE resp.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify8() throws Exception {

    test( "{\\oe}\\oe{\\OE}\\OE", "oeoeOEOE" );
  }

  /**
   * \ss is translated to ss.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify9() throws Exception {

    test( "{\\ss}\\ss", "ssss" );
  }

  /**
   * The empty stack leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibMissingStringException.class)
  public void testTypeError1() throws Exception {

    p.push( new TInteger( 1, null ) );
    new Purify( "purify$" ).execute( p, null, null );
  }

}
