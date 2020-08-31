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

package org.extex.exbib.main;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test suite for built-ins.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BuiltinTest {

  /**
   * The field {@code DATA_DIR} contains the directory containing database,
   * styles and results.
   */
  private static final String DATA_DIR = "src/test/resources/bibtex/builtin";

  /**
   * Run a test case.
   *
   * @param style the file name
   * @throws IOException in case of an I/O error
   */
  private void runTest( String style ) throws IOException {

    TRunner.runTest( DATA_DIR + "/" + style,
                     DATA_DIR + "/a",
                     "*",
                     new File( DATA_DIR, style + ".result" ) );
  }

  /**
   * Run test for add.period$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAddPeriod1() throws Exception {

    runTest( "add_period" );
  }

  /**
   * Run tests for call.type$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCallType1() throws Exception {

    runTest( "call_type" );
  }

  /**
   * Run tests for change.case$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testChangeCase1() throws Exception {

    runTest( "change_case" );
  }

  /**
   * Run tests for chr.to.int$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testChrToInt1() throws Exception {

    runTest( "chr_to_int" );
  }

  /**
   * Run tests for cite$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCite1() throws Exception {

    runTest( "cite" );
  }

  /**
   * Run tests for concat$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConcat1() throws Exception {

    runTest( "concat" );
  }

  /**
   * Run tests for duplicate$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDuplicate1() throws Exception {

    runTest( "duplicate" );
  }

  /**
   * Run tests for empty$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty1() throws Exception {

    runTest( "empty" );
  }

  /**
   * Run tests for =$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEq1() throws Exception {

    runTest( "eq" );
  }

  /**
   * Run tests for format.names$.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testFormatNames1() throws Exception {

    runTest( "format_names" );
  }

  /**
   * Run tests for &gt;$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGt1() throws Exception {

    runTest( "gt" );
  }

  /**
   * Run tests for if$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIf1() throws Exception {

    runTest( "if" );
  }

  /**
   * Run tests for int.to.chr$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIntToChr1() throws Exception {

    runTest( "int_to_chr" );
  }

  /**
   * Run tests for int.to.str$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testIntToStr1() throws Exception {

    runTest( "int_to_str" );
  }

  /**
   * Run tests for &lt;.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLt1() throws Exception {

    runTest( "lt" );
  }

  /**
   * Run tests for -.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMinus1() throws Exception {

    runTest( "minus" );
  }

  /**
   * Run tests for missing$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMissing1() throws Exception {

    runTest( "missing" );
  }

  /**
   * Run tests for newline$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNewline1() throws Exception {

    runTest( "newline" );
  }

  /**
   * Run tests for num.names$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNumNames1() throws Exception {

    runTest( "num_names" );
  }

  /**
   * Run tests for plus$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPlus1() throws Exception {

    runTest( "plus" );
  }

  /**
   * Run tests for pop$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPop1() throws Exception {

    runTest( "pop" );
  }

  /**
   * Run tests for preamble$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPreamble1() throws Exception {

    runTest( "preamble" );
  }

  /**
   * Run tests for purify$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPurify1() throws Exception {

    runTest( "purify" );
  }

  /**
   * Run tests for quote$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testQuote1() throws Exception {

    runTest( "quote" );
  }

  /**
   * Run tests for set$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSet1() throws Exception {

    runTest( "set" );
  }

  /**
   * Run tests for skip$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkip1() throws Exception {

    runTest( "skip" );
  }

  /**
   * Run tests for stack$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testStack1() throws Exception {

    runTest( "stack" );
  }

  /**
   * Run tests for substring$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubstring1() throws Exception {

    runTest( "substring" );
  }

  /**
   * Run tests for swap$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSwap1() throws Exception {

    runTest( "swap" );
  }

  /**
   * Run tests for text.length$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextLength1() throws Exception {

    runTest( "text_length" );
  }

  /**
   * Run tests for text.prefix$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTextPrefix1() throws Exception {

    runTest( "text_prefix" );
  }

  /**
   * Run tests for top$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTop1() throws Exception {

    runTest( "top" );
  }

  /**
   * Run tests for type$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testType1() throws Exception {

    runTest( "type" );
  }

  /**
   * Run tests for warning$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWarning1() throws Exception {

    runTest( "warning" );
  }

  /**
   * Run tests for while$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWhile1() throws Exception {

    runTest( "while" );
  }

  /**
   * Run tests for width$.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testWidth1() throws Exception {

    runTest( "width" );
  }

  /**
   * Run tests for write$.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWrite1() throws Exception {

    runTest( "write" );
  }

}
