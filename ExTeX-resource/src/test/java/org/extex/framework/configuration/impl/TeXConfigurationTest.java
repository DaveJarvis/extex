/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.impl;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * This is a test suite for the {@link TeXConfiguration}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TeXConfigurationTest {

  /**
   * Make an input stream reading from a string.
   *
   * @param s the string
   * @return the input stream
   */
  private InputStream makeStream( String s ) {

    return new ByteArrayInputStream( s.getBytes() );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testGetConfiguration1() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( "  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} " ),
            "" );
    assertNotNull( cfg.getConfiguration( "x" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationNotFoundException.class)
  public final void testGetConfiguration2() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( "  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} " ),
            "" );
    try {
      cfg.getConfiguration( "y" );
    } catch( ConfigurationNotFoundException e ) {
      assertEquals( "y", e.getConfigName() );
      throw e;
    }
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testGetValue1() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( "  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} " ),
            "" );
    assertEquals( " ", cfg.getValue() );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead02() throws Exception {

    TeXConfiguration cfg = new TeXConfiguration( makeStream( "\\x{}" ), "" );
    assertNotNull( cfg );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead03() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration( makeStream( "  \\xyz { } " ), "" );
    assertNotNull( cfg );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead04() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration( makeStream( "  \\xyz [abc=def] { } " ), "" );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead05() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( "  \\xyz [abc=def,xxx={123}] { } " ), "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
    assertEquals( "xyz", cfg.getName() );
    Iterator<Configuration> iterator = cfg.iterator();
    assertFalse( iterator.hasNext() );
    iterator = cfg.iterator( "abc" );
    assertFalse( iterator.hasNext() );
    assertNull( cfg.findConfiguration( "x" ) );
    assertNull( cfg.findConfiguration( "x", "y" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead06() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( "  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} " ),
            "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
    assertEquals( "xyz", cfg.getName() );
    Iterator<Configuration> iterator = cfg.iterator();
    assertTrue( iterator.hasNext() );
    iterator = cfg.iterator( "abc" );
    assertFalse( iterator.hasNext() );
    assertNotNull( cfg.findConfiguration( "x" ) );
    assertNull( cfg.findConfiguration( "x", "y" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead07() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( "  \\xyz [abc=def,xxx={123}] { \\x[name=42]{}} " ),
            "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
    assertEquals( "xyz", cfg.getName() );
    Iterator<Configuration> iterator = cfg.iterator();
    assertTrue( iterator.hasNext() );
    iterator = cfg.iterator( "abc" );
    assertFalse( iterator.hasNext() );
    assertNotNull( cfg.findConfiguration( "x", "42" ) );
    assertNull( cfg.findConfiguration( "x", "y" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead10() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration( makeStream( "  \\xyz { abc } " ), "" );
    assertNotNull( cfg );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead11() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration( makeStream( "  \\xyz { {abc}} " ), "" );
    assertNotNull( cfg );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testRead12() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration( makeStream( "  \\xyz [a= {{abc}}]{} " ), "" );
    assertEquals( "{abc}", cfg.getAttribute( "a" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testReadComment01() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( " % fff\n \\xyz [abc=def,xxx={123}] { } " ), "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testReadComment02() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( " \\xyz % fff\n [abc=def,xxx={123}] { } " ), "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testReadComment03() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( " \\xyz [abc % fff\n=def,xxx={123}] { } " ), "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testReadComment04() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( " \\xyz [abc = % fff\n def,xxx={123}] { } " ), "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testReadComment05() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( " \\xyz [abc = % fff\n def,xxx={123}] { abc } " ),
            "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
  }

  /**
   * @throws Exception in case of an error
   */
  @Test
  public final void testReadComment06() throws Exception {

    TeXConfiguration cfg =
        new TeXConfiguration(
            makeStream( " \\xyz [abc = % fff\n def,xxx={123}] {\\qwertz {}} " ),
            "" );
    assertEquals( "123", cfg.getAttribute( "xxx" ) );
    assertEquals( "def", cfg.getAttribute( "abc" ) );
  }

  /**
   * An empty stream signals a failure in locating the configuration
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationNotFoundException.class)
  public final void testReadError0() throws Exception {

    new TeXConfiguration( null, "" );
  }

  /**
   * The empty stream is not valid.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError01() throws Exception {

    new TeXConfiguration( makeStream( "" ), "" );
  }

  /**
   * The empty stream with whitespace only is not valid.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError02() throws Exception {

    new TeXConfiguration( makeStream( "  \t " ), "" );
  }

  /**
   * The initial cs has to be followed by something.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError04() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx" ), "" );
  }

  /**
   * The initial cs has to be followed by [ or {.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError05() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx(" ), "" );
  }

  /**
   * The initial cs has to be followed by [ and an attribute name.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError06() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[" ), "" );
  }

  /**
   * The initial cs has to be followed by [, an attribute name and an =
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError07() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[a" ), "" );
  }

  /**
   * The initial cs has to be followed by [, an attribute name and an =
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError08() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[a%=\n" ), "" );
  }

  /**
   * The initial cs has to be followed by [, an attribute name and an =
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError09() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[a= %=\n" ), "" );
  }

  /**
   * The initial cs has to be followed by [, an attribute name and an =
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError10() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[a %=\na" ), "" );
  }

  /**
   * Attributes need to be closed by ].
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError11() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[a=x\n" ), "" );
  }

  /**
   * Attributes need to be closed by ].
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError12() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[a=x b" ), "" );
  }

  /**
   * Attributes need to be unique.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError13() throws Exception {

    new TeXConfiguration( makeStream( "\\xxx[a=x,a=y]" ), "" );
  }

  /**
   * A lonely backslash raises an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError14() throws Exception {

    new TeXConfiguration( makeStream( "\\" ), "" );
  }

  /**
   * A lonely backslash in the configuration raises an
   * error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError15() throws Exception {

    new TeXConfiguration( makeStream( "\\a{\\" ), "" );
  }

  /**
   * A lonely backslash in the configuration raises an
   * error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError16() throws Exception {

    new TeXConfiguration( makeStream( "\\={\\" ), "" );
  }

  /**
   * A missing opening brace for a sub-configuration raises an error
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError17() throws Exception {

    new TeXConfiguration( makeStream( "\\a{\\=" ), "" );
  }

  /**
   * An unclosed brace raises an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError18() throws Exception {

    new TeXConfiguration( makeStream( "\\a{" ), "" );
  }

  /**
   * A missing backslash raises an error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError19() throws Exception {

    new TeXConfiguration( makeStream( "a" ), "" );
  }

  /**
   * A missing closing bracket for the attribute section raises an error
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError20() throws Exception {

    new TeXConfiguration( makeStream( "\\a[x=y" ), "" );
  }

  /**
   * A missing closing brace in the attribute section raises an error
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public final void testReadError21() throws Exception {

    new TeXConfiguration( makeStream( "\\a[x={y\\" ), "" );
  }

}
