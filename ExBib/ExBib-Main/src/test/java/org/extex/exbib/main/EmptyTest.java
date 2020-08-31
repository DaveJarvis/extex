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

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test suite for the empty tests.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class EmptyTest {

  /**
   * The field {@code DATA_DIR} contains the directory containing database,
   * styles and results.
   */
  private static final String DATA_DIR = "src/test/resources/bibtex/empty";

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
   * Run test empty_1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty1() throws Exception {

    runTest( "empty_1" );
  }

  /**
   * Run test empty_2.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty2() throws Exception {

    runTest( "empty_2" );
  }

}
