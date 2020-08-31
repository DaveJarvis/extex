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

package org.extex.exbib.core.io.bblio;

import org.extex.exbib.core.io.StringBufferWriter;
import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This is a test suite for the {@link BblWriter}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BblWriterTest {

  /**
   * This is a dummy configuration.
   */
  private static class DummyConfig implements Configuration {

    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      // findConfiguration unimplemented
      return null;
    }

    /**
     * java.lang.String)
     */
    public Configuration findConfiguration( String key, String attribute )
        throws ConfigurationException {

      // findConfiguration unimplemented
      return null;
    }

    public String getAttribute( String name ) throws ConfigurationException {

      return null;
    }

    public Configuration getConfiguration( String key )
        throws ConfigurationException {

      return null;
    }

    /**
     * java.lang.String)
     */
    public Configuration getConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    public String getValue() throws ConfigurationException {

      // getValue unimplemented
      return null;
    }

    public String getValue( String key ) throws ConfigurationException {

      return null;
    }

    /**
     * int)
     */
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 20;
    }

    /**
     * java.lang.String)
     */
    public void getValues( List<String> list, String key ) {

      // getValues unimplemented
    }

    public List<String> getValues( String key ) throws ConfigurationException {

      return null;
    }

    public Iterator<Configuration> iterator() throws ConfigurationException {

      // iterator unimplemented
      return null;
    }

    public Iterator<Configuration> iterator( String key )
        throws ConfigurationException {

      return null;
    }

    public void setConfigurationLoader( ConfigurationLoader loader ) {

      // setConfigurationLoader unimplemented
    }
  }

  /**
   * Run a test and compare the result.
   *
   * @param in  the input string
   * @param res the expected result string
   * @throws Exception in case of an error
   */
  private void runTest( String in, String res ) throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.configure( new DummyConfig() );
    w.print( in );
    w.close();

    assertEquals( res, sb.toString() );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA00() throws Exception {

    runTest( " abc xxxxxxxxx1xxxxxxx", " abc\n  xxxxxxxxx1xxxxxxx" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA01() throws Exception {

    runTest( "z abc xxxxxxxxx1xxxxxxx", "z abc\n  xxxxxxxxx1xxxxxxx" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA02() throws Exception {

    runTest( "zz abc xxxxxxxxx1xxxxxxx", "zz abc\n  xxxxxxxxx1xxxxxxx" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA03() throws Exception {

    runTest( "zzz abc xxxxxxxxx1xxxxxxx", "zzz abc\n  xxxxxxxxx1xxxxxxx" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA04() throws Exception {

    runTest( "zzzz abc xxxxxxxxx1xxxxxxx", "zzzz abc\n  xxxxxxxxx1xxxxxxx" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA05() throws Exception {

    runTest( "zzzzn abc xxxxxxxxx1xxxxxxx", "zzzzn abc\n  " +
        "xxxxxxxxx1xxxxxxx" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA15() throws Exception {

    runTest( "zzzznzzzznzzzzn abc xxxxxxxxx1",
             "zzzznzzzznzzzzn abc\n  xxxxxxxxx1" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA16() throws Exception {

    runTest( "zzzznzzzznzzzznz abc xxxxxxxxx1",
             "zzzznzzzznzzzznz abc\n  xxxxxxxxx1" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA17() throws Exception {

    runTest( "zzzznzzzznzzzznzz abc xxxxxxxxx1",
             "zzzznzzzznzzzznzz\n  abc xxxxxxxxx1" );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA20() throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.setIndent( "___" );
    w.configure( new DummyConfig() );
    w.print( " abc xxxxxxxxx1xxxxxxx" );
    w.close();

    assertEquals( " abc\n___xxxxxxxxx1xxxxxxx", sb.toString() );
  }

  /**
   * Test the line breaking: Whitespace in unusable positions doies no harm
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA30() throws Exception {

    runTest( "zzzz zzzz zzzznzz abc xxxxxxxxx1",
             "zzzz zzzz zzzznzz\n  abc xxxxxxxxx1" );
  }

  /**
   * Test the line breaking: Whitespace are translated to space.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA31() throws Exception {

    runTest( "zzzz\tzzzz zzzznzz abc xxxxxxxxx1",
             "zzzz zzzz zzzznzz\n  abc xxxxxxxxx1" );
  }

  /**
   * Test the line breaking: multiple spaces are reduced to one.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA32() throws Exception {

    runTest( "zzzz \tzzzz zzzznzz abc xxxxxxxxx1",
             "zzzz  zzzz zzzznzz\n  abc xxxxxxxxx1" );
  }

  /**
   * A {@code null} argument to the constructor leads to an exception
   *
   * @throws Exception in case of an error
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNull() throws Exception {

    new BblWriter( null );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPrintln01() throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.configure( new DummyConfig() );
    w.println();
    w.close();

    assertEquals( "\n", sb.toString() );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPrintln02() throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.configure( new DummyConfig() );
    w.print( "abc  " );
    w.println();
    w.close();

    assertEquals( "abc\n", sb.toString() );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPrintln11() throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.configure( new DummyConfig() );
    w.println( "abc  " );
    w.close();

    assertEquals( "abc\n", sb.toString() );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPrintln12() throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.configure( new DummyConfig() );
    w.println( "ab\nc  " );
    w.close();

    assertEquals( "ab\nc\n", sb.toString() );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPrintln13() throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.configure( new DummyConfig() );
    w.println( "ab  \nc  " );
    w.close();

    assertEquals( "ab\nc\n", sb.toString() );
  }

  /**
   * Test toString() methos.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testToString1() throws Exception {

    StringBuffer sb = new StringBuffer();
    Writer w = new BblWriter( new StringBufferWriter( sb ) );
    assertEquals( "79 ><", w.toString() );
  }

  /**
   * Test the line breaking.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testWrite1() throws Exception {

    StringBuffer sb = new StringBuffer();
    BblWriter w = new BblWriter( new StringBufferWriter( sb ) );
    w.configure( new DummyConfig() );
    w.write( 'a' );
    w.close();

    assertEquals( "a", sb.toString() );
  }

}
