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

package org.extex.exindex.core.exception;

import org.extex.exindex.lisp.parser.ResourceLocator;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * This is a test case for {@link InconsistentFlagsException}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class InconsistentFlagsExceptionTest {

  /**
   * The constant {@code RL} contains the locator.
   */
  private static final ResourceLocator RL = new ResourceLocator() {

    public int getLineNumber() {

      return 1;
    }

    public String getResource() {

      return "rsc";
    }
  };

  /**
   * The locator can be {@code null} and the message does not show a location
   */
  @Test
  public final void testGetLocalizedMessage1() {

    Locale locale = Locale.getDefault();
    try {
      Locale.setDefault( Locale.ENGLISH );
      InconsistentFlagsException e =
          new InconsistentFlagsException( null, ":f1", ":f2" );
      assertEquals( "The flags :f1 and :f2 may not be used together.\n",
                    e.getLocalizedMessage() );
    } finally {
      Locale.setDefault( locale );
    }
  }

  /**
   * The message shows the location if the locator is not {@code null}
   */
  @Test
  public final void testGetLocalizedMessage2() {

    Locale locale = Locale.getDefault();
    try {
      Locale.setDefault( Locale.ENGLISH );
      InconsistentFlagsException e =
          new InconsistentFlagsException( RL, ":f1", ":f2" );
      assertEquals(
          "rsc:1: The flags :f1 and :f2 may not be used together.\n",
          e.getLocalizedMessage() );
    } finally {
      Locale.setDefault( locale );
    }
  }

  /**
   * The locator can be {@code null} and the message does not show a
   * location in German
   */
  @Test
  public final void testGetLocalizedMessageDE1() {

    Locale locale = Locale.getDefault();
    try {
      Locale.setDefault( Locale.GERMAN );
      InconsistentFlagsException e =
          new InconsistentFlagsException( null, ":f1", ":f2" );
      assertEquals(
          "Die Optionen :f1 und :f2 k\u00f6nnen nicht zusammen benutzt werden" +
              ".\n",
          e.getLocalizedMessage() );
    } finally {
      Locale.setDefault( locale );
    }
  }

  /**
   * The message shows the location if the locator is not {@code null} in German
   */
  @Test
  public final void testGetLocalizedMessageDE2() {

    Locale locale = Locale.getDefault();
    try {
      Locale.setDefault( Locale.GERMAN );
      InconsistentFlagsException e =
          new InconsistentFlagsException( RL, ":f1", ":f2" );
      assertEquals(
          "rsc:1: Die Optionen :f1 und :f2 k\u00f6nnen nicht zusammen benutzt" +
              " werden.\n",
          e.getLocalizedMessage() );
    } finally {
      Locale.setDefault( locale );
    }
  }

}
