/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * This is a test suite for the crossref feature.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CrossrefTest {

  /**
   * The field {@code DATA_DIR} contains the directory containing database,
   * styles and results.
   */
  private static final String DATA_DIR = "src/test/resources/bibtex/base";


  public CrossrefTest() {

  }

  /**
   * Run a test.
   *
   * @param style the style
   * @param xref  the parameter min-crossrefs
   * @param args  the additional arguments
   * @throws IOException in case of an I/O error
   */
  private void runTest( String style, String xref, String... args )
      throws IOException {

    TRunner.runTest(
        DATA_DIR + "/" + style,
        DATA_DIR + "/xampl",
        "book-crossref,inbook-crossref",
        new File( DATA_DIR, style + ".result." + xref + "xref" ),
        "--min-crossrefs", xref );
  }

  /**
   * Apply the abbrev style to xampl.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testAbbrv() throws Exception {

    runTest( "abbrv", "0", "--strict" );
  }

  /**
   * Apply the alpha style to xampl.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAlpha0() throws Exception {

    runTest( "alpha", "0", "--strict" );
  }

  /**
   * Apply the alpha style to xampl.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAlpha1() throws Exception {

    runTest( "alpha", "1", "--strict" );
  }

  /**
   * Apply the alpha style to xampl.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAlpha3() throws Exception {

    runTest( "alpha", "3", "--strict" );
  }

  /**
   * Apply the plain style to xampl.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testPlain() throws Exception {

    runTest( "plain", "--strict" );
  }

  /**
   * Apply the unsrt style to xampl.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testUnsrt() throws Exception {

    runTest( "unsrt", "--strict" );
  }

}
