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

package org.extex.exindex.core.parser.xindy;

import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexSyntaxException;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.core.type.raw.Reference;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * This is a test suite for the xindy parser.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class XindyParserTest {

  /**
   * Check two string array for equal contents.
   *
   * @param a the first array
   * @param b the second array
   * @return {@code true} iff they have equal length and contents
   */
  private boolean eq( String[] a, String[] b ) {

    int aLength = a.length;
    if( aLength != b.length ) {
      return false;
    }
    for( int i = 0; i < aLength; i++ ) {
      if( !a[ i ].equals( b[ i ] ) ) {
        return false;
      }
    }
    return true;
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test01() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :key (\"abc\") :locref \"1\")" ), "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertNull( xp.parse() );
    assertTrue( ie.getMainKey() == ie.getPrintKey() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test02() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :key (\"abc\") :attr \"see\" :locref \"1\")" ),
                         "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertNull( xp.parse() );
    assertTrue( ie.getMainKey() == ie.getPrintKey() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test03() throws Exception {

    XindyParser xp =
        new XindyParser(
            new StringReader(
                "(indexentry :key (\"abc\") :attr \"see\" :locref \"1\" " +
                    ":open-range)" ),
            "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertNull( xp.parse() );
    assertTrue( eq( ie.getMainKey(), ie.getPrintKey() ) );
    assertNotNull( ie.getRef().getLayer() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test04() throws Exception {

    XindyParser xp =
        new XindyParser(
            new StringReader(
                "(indexentry :key (\"abc\") :attr \"see\" :locref \"1\" " +
                    ":close-range)" ),
            "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertNull( xp.parse() );
    assertTrue( eq( ie.getMainKey(), ie.getPrintKey() ) );
    assertNotNull( ie.getRef().getLayer() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test05() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :tkey (\"abc\") :locref \"1\")" ), "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertNull( xp.parse() );
    assertTrue( eq( ie.getMainKey(), ie.getPrintKey() ) );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test06() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :tkey ((\"abc\")) :locref \"1\")" ), "rsc",
                         null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertNull( xp.parse() );
    assertTrue( eq( ie.getMainKey(), ie.getPrintKey() ) );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test07() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :tkey ((\"abc\" \"def\")) :locref \"1\")" ),
                         "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertTrue( eq( ie.getMainKey(), new String[]{"abc"} ) );
    assertTrue( eq( ie.getPrintKey(), new String[]{"def"} ) );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test08() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :key \"abc\" :locref \"1\")" ), "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertNull( xp.parse() );
    assertTrue( eq( ie.getMainKey(), ie.getPrintKey() ) );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test09() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :key \"abc\" :print \"def\" :locref \"1\")" ),
                         "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertTrue( eq( ie.getMainKey(), new String[]{"abc"} ) );
    assertTrue( eq( ie.getPrintKey(), new String[]{"def"} ) );
    Reference ref = ie.getRef();
    assertNotNull( ref );
    assertTrue( eq( ref.getLocation(), new String[]{"1"} ) );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test10() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader(
            "(indexentry :key \"abc\" :print \"def\" :xref \"def\")" ),
                         "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    assertTrue( eq( ie.getMainKey(), new String[]{"abc"} ) );
    assertTrue( eq( ie.getPrintKey(), new String[]{"def"} ) );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test11() throws Exception {

    XindyParser xp =
        new XindyParser(
            new StringReader(
                "(indexentry :key \"abc\" :print \"def\" :locref (\"123\"))" ),
            "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    Reference ref = ie.getRef();
    assertNotNull( ref );
    // assertEquals(ref.getLayer(), "");
    assertTrue( eq( ref.getLocation(), new String[]{"123"} ) );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test30() throws Exception {

    XindyParser xp =
        new XindyParser(
            new StringReader(
                "(indexentry :index \"\" :key \"abc\" :print \"def\" :locref " +
                    "(\"123\"))" ),
            "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    Reference ref = ie.getRef();
    assertNotNull( ref );
    assertTrue( eq( ref.getLocation(), new String[]{"123"} ) );
    String index = ie.getIndex();
    assertEquals( "", index );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test31() throws Exception {

    XindyParser xp =
        new XindyParser(
            new StringReader(
                "(indexentry :index \"a\" :key \"abc\" :print \"def\" :locref" +
                    " (\"123\"))" ),
            "rsc", null );
    RawIndexentry ie = xp.parse();
    assertNotNull( ie );
    Reference ref = ie.getRef();
    assertNotNull( ref );
    assertTrue( eq( ref.getLocation(), new String[]{"123"} ) );
    String index = ie.getIndex();
    assertEquals( "a", index );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testBound1() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :key (\"1\") :key (\"1\") )" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testBound2() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :print (\"1\") :print (\"1\") )" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testBound3() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :attr \"1\" :attr \"1\" )" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testBound5() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :xref (\"1\") :xref (\"1\") )" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testBound6() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :key (\"1\") :tkey (\"1\") )" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testEmpty1() throws Exception {

    XindyParser xp = new XindyParser( new StringReader( "" ), "rsc", null );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testEmpty2() throws Exception {

    XindyParser xp = new XindyParser( new StringReader( "   " ), "rsc", null );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testEmpty3() throws Exception {

    XindyParser xp =
        new XindyParser( new StringReader( "; xxx\n   " ), "rsc", null );
    assertNull( xp.parse() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   */
  @Test(expected = NullPointerException.class)
  public final void testErr1() {

    new XindyParser( null, null, null );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   */
  @Test(expected = NullPointerException.class)
  public final void testErr2() {

    new XindyParser( null, "", null );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError01() throws Exception {

    new XindyParser( new StringReader( "abc" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError02() throws Exception {

    new XindyParser( new StringReader( "123" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError03() throws Exception {

    new XindyParser( new StringReader( "nil" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError04() throws Exception {

    new XindyParser( new StringReader( "()" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError05() throws Exception {

    new XindyParser( new StringReader( "(123)" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError06() throws Exception {

    new XindyParser( new StringReader( "(abc)" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError07() throws Exception {

    new XindyParser( new StringReader( "(indexentry 123)" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError08() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :tkey ((\"\" \"\" \"\")))" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError09() throws Exception {

    new XindyParser( new StringReader( "(indexentry :tkey (()))" ), "rsc",
                     null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError10() throws Exception {

    new XindyParser( new StringReader( "(indexentry :tkey (abc def))" ),
                     "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError11() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :key (\"1\") :xref (\"1\") :open-range)" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError12() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :key (\"1\") :xref (\"1\") :close-range)" ), "rsc",
                     null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError13() throws Exception {

    new XindyParser(
        new StringReader(
            "(indexentry :key (\"1\") :locref \"1\" :close-range :open-range)"
        ),
        "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError14() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :key \"x\" :xref (\"1\") :xref (\"2\"))" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError15() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :key \"x\" :locref (\"1\") :xref (\"2\"))" ), "rsc",
                     null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testError16() throws Exception {

    new XindyParser( new StringReader(
        "(indexentry :key \"x\" :xref (\"1\") :locref (\"2\"))" ), "rsc",
                     null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testError17() throws Exception {

    new XindyParser( new StringReader( "(indexentry :key x)" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testMissing01() throws Exception {

    new XindyParser( new StringReader( "(indexentry)" ), "rsc", null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testMissing02() throws Exception {

    new XindyParser( new StringReader( "(indexentry :key (\"abc\"))" ), "rsc",
                     null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testMissing03() throws Exception {

    new XindyParser( new StringReader( "(indexentry :key )" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testMissing04() throws Exception {

    new XindyParser( new StringReader( "(indexentry :key (abc))" ), "rsc",
                     null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexException.class)
  public final void testMissing05() throws Exception {

    new XindyParser( new StringReader( "(indexentry :xxx (abc))" ), "rsc",
                     null ).parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testMissing06() throws Exception {

    new XindyParser( new StringReader( "(indexentry :print )" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testMissing07() throws Exception {

    new XindyParser( new StringReader( "(indexentry :tkey )" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testMissing08() throws Exception {

    new XindyParser( new StringReader( "(indexentry :attr )" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testMissing09() throws Exception {

    new XindyParser( new StringReader( "(indexentry :locref )" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testMissing10() throws Exception {

    new XindyParser( new StringReader( "(indexentry :xref )" ), "rsc", null )
        .parse();
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.parser.xindy.XindyParser#parse()}.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = RawIndexSyntaxException.class)
  public final void testMissing11() throws Exception {

    new XindyParser( new StringReader( "(indexentry :index)" ), "rsc", null )
        .parse();
  }

}
