/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.exception;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link ConfigurationIOException}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ConfigurationIOExceptionTest {

  /**
   * ...
   */
  @Test
  public void testGetLocalizedMessage() {

    ConfigurationIOException e =
        new ConfigurationIOException( new Exception( "abc" ) );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "I/O error abc\n\tcaused by abc", e.getLocalizedMessage() );
  }

  /**
   * ...
   */
  @Test
  public void testGetLocalizer() {

    ConfigurationIOException e = new ConfigurationIOException( null );
    Locale.setDefault( Locale.ENGLISH );
    assertNotNull( e.getLocalizer() );
  }

  /**
   * ...
   */
  @Test
  public void testGetMessage1() {

    ConfigurationIOException e = new ConfigurationIOException( null );
    Locale.setDefault( Locale.ENGLISH );
    assertNull( e.getMessage() );
  }

  /**
   * ...
   */
  @Test
  public void testGetMessage2() {

    ConfigurationIOException e =
        new ConfigurationIOException( new Exception( "abc" ) );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( null, e.getMessage() );
  }

  /**
   * ...
   */
  @Test
  public void testGetSource1() {

    ConfigurationIOException e = new ConfigurationIOException( null );
    Locale.setDefault( Locale.ENGLISH );
    assertNull( e.getSource() );
  }

  /**
   * ...
   */
  @Test
  public void testGetSource2() {

    ConfigurationIOException e =
        new ConfigurationIOException( new Exception( "abc" ) );
    Locale.setDefault( Locale.ENGLISH );
    assertNull( e.getSource() );
  }

  /**
   * ...
   */
  @Test
  public void testGetText1() {

    ConfigurationIOException e = new ConfigurationIOException( null );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "I/O error ", e.getText() );
  }

  /**
   * ...
   */
  @Test
  public void testGetText2() {

    ConfigurationIOException e =
        new ConfigurationIOException( new Exception( "abc" ) );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "I/O error abc", e.getText() );
  }

}
