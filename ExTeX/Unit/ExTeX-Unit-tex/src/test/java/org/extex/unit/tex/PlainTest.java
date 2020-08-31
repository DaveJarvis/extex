/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * This is the test suite for plain.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PlainTest extends ExTeXLauncher {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( PlainTest.class );
  }


  public PlainTest() {

  }

  /**
   * Test case checking that plain leads to a format file.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPlain() throws Exception {

    File fmt = new File( ".", "texput.fmt" );
    File log = new File( ".", "texput.log" );
    assertFailure(
        "\\input ../ExTeX-Unit-tex/src/test/resources/tex/plain-dump \\end",
        "Preloading the plain format: codes, registers, parameters, fonts, " +
            "more fonts,\n"
            + "macros, math definitions, output routines, hyphenation\n"
            + "Beginning to dump on file " + fmt.toString() + "\n" );

    assertTrue( "Missing format file", fmt.exists() );
    assertTrue( "Format file is of wrong type", fmt.isFile() );
    fmt.delete();

    assertTrue( "Missing log file", log.exists() );
    assertTrue( "Log file is of wrong type", log.isFile() );
    log.delete();
  }

}
