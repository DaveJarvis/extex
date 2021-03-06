/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex;

import org.extex.test.ExTeXLauncher;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This is a test suite for expansion.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MemoryTest extends ExTeXLauncher {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( MemoryTest.class );
  }


  public MemoryTest() {

  }

  /**
   * Test case checking that an infinite recursion throws an
   * {@link OutOfMemoryError}
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  // Try to avoid a timeout
  public void test1() throws Exception {

    Properties properties = getProps();
    properties.setProperty( "extex.jobname", "job" );
    properties.setProperty( "extex.output", "dump" );

    try {
      assertFailure( properties,
                     // --- input code ---
                     DEFINE_BRACES + "\\def\\x{\\x}\\x",
                     // --- output channel ---
                     "Java heap space" );
      assertFalse( true );
    } catch( OutOfMemoryError e ) {
      assertTrue( true );
    } finally {
      new File( "job.log" ).delete();
    }
  }

}
