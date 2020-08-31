/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.exindex.core.parser.makeindex;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * This is a test suite for the xindy parser.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MakeindexParserTest {

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test01() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "" ), "rsc", interpreter );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void test02() throws Exception {

    Indexer interpreter = new Indexer();
    interpreter.setq( "makeindex:level", new LString( "" ) );
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "" ), "rsc", interpreter );
    xp.parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test03() throws Exception {

    Indexer interpreter = new Indexer();
    interpreter.setq( "makeindex:level", new LChar( '!' ) );
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "" ), "rsc", interpreter );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test04() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "\\relax" ), "rsc",
                             interpreter );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test05() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "\\indexentry{abc}{123}" ),
                             "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :locref " +
            "\"123\")\n",
        entry.toString() );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test06() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc@def}{123}" ), "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"def\") :locref " +
            "\"123\")\n",
        entry.toString() );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test07() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc|def}{123}" ), "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :attr " +
            "\"def\" :locref \"123\")\n",
        entry.toString() );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test08() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc|def(}{123}" ), "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :attr " +
            "\"def\" :locref \"123\" :open-range)\n",
        entry.toString() );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test09() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc|def)}{123}" ), "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :attr " +
            "\"def\" :locref \"123\" :close-range)\n",
        entry.toString() );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test10() throws Exception {

    Indexer interpreter = new Indexer();
    interpreter.setq( "makeindex:keyword", new LString( "\\gloentry" ) );
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\gloentry{abc|def)}{123}" ), "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :attr " +
            "\"def\" :locref \"123\" :close-range)\n",
        entry.toString() );
    assertNull( xp.parse() );
    xp.close();
  }

  /**
   * Test that multiple entries can be parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test11() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc}{123}\\indexentry{def}{123}" ), "rsc",
                             interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :locref " +
            "\"123\")\n",
        entry.toString() );
    entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"def\") :print (\"def\") :locref " +
            "\"123\")\n",
        entry.toString() );
    xp.close();
  }

  /**
   * Test that multiple entries can be parsed and whitespace in
   * between is skipped.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test12() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc}{123} \n" + "\\indexentry{def}{123}" ),
                             "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :locref " +
            "\"123\")\n",
        entry.toString() );
    entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"def\") :print (\"def\") :locref " +
            "\"123\")\n",
        entry.toString() );
    xp.close();
  }

  /**
   * Test that multiple entries can be parsed and anything in
   * between is skipped.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test13() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc}{123} \nxyz-.,"
                + "\\indexentry{def}{123}" ), "rsc", interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"abc\") :print (\"abc\") :locref " +
            "\"123\")\n",
        entry.toString() );
    entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"def\") :print (\"def\") :locref " +
            "\"123\")\n",
        entry.toString() );
    xp.close();
  }

  /**
   * Test that multiple entries can be parsed and nothing after the
   * end and after the parser has been closed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test14() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader(
            "\\indexentry{abc}{123} \nxyz-.,"
                + "\\indexentry{def}{123}" ), "rsc", interpreter );
    assertNotNull( xp.parse() );
    assertNotNull( xp.parse() );
    assertNull( xp.parse() );
    xp.close();
    assertNull( xp.parse() );
  }

  /**
   * Test that an entry can be parsed and embedded braces are
   * treated right.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test15() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser(
            new StringReader( "\\indexentry{a{b}c}{123}" ), "rsc",
            interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"a{b}c\") :print (\"a{b}c\") :locref " +
            "\"123\")\n",
        entry.toString() );
    xp.close();
  }

  /**
   * Test that an entry with a structured item can be parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test16() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser(
            new StringReader( "\\indexentry{a!b!c}{123}" ), "rsc",
            interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"a\" \"b\" \"c\") :print (\"a\" \"b\"" +
            " \"c\") :locref \"123\")\n",
        entry.toString() );
    xp.close();
  }

  /**
   * Test that an entry with an embedded \{ can be parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test17() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser(
            new StringReader( "\\indexentry{a\\{}{123}" ), "rsc",
            interpreter );
    RawIndexentry entry = xp.parse();
    assertNotNull( entry );
    assertEquals(
        "(indexentry :index \"\" :key (\"a\\{\") :print (\"a\\{\") :locref " +
            "\"123\")\n",
        entry.toString() );
    xp.close();
  }

  /**
   * Test that the keyword has to be followed by an open brace.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexMissingCharException.class)
  public final void testError01() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "\\indexentry abc" ),
                             "rsc", interpreter );
    xp.parse();
  }

  /**
   * Test that an unbalance brace in an entry leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexEofException.class)
  public final void testError02() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "\\indexentry{abc" ),
                             "rsc", interpreter );
    xp.parse();
  }

  /**
   * Test that an unbalance brace in the page reference leads to an
   * error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexEofException.class)
  public final void testError03() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "\\indexentry{abc}{13" ),
                             "rsc", interpreter );
    xp.parse();
  }

  /**
   * Test that an entry with an \ at the end leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexEofException.class)
  public final void testError04() throws Exception {

    Indexer interpreter = new Indexer();
    MakeindexParser xp =
        new MakeindexParser( new StringReader( "\\indexentry{a\\" ),
                             "rsc", interpreter );
    xp.parse();
  }

  /**
   * Test that makeindex:keyword does not accept nil as value.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testKeywordError1() throws Exception {

    Indexer interpreter = new Indexer();
    interpreter.setq( "makeindex:keyword", LList.NIL );
    new MakeindexParser( new StringReader( "" ), "rsc", interpreter );
  }

}
