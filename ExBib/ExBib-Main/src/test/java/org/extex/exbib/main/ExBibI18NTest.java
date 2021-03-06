/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.fail;

/**
 * This is a test suite for different encodings.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ExBibI18NTest {

  private final static String DIR_TARGET = "build";

  /**
   * The field {@code DATA_DIR} contains the directory containing database,
   * styles and results.
   */
  private static final String DATA_DIR = "src/test/resources/bibtex/i18n";

  /**
   * The field {@code STYLE_DIR} contains the style directory.
   */
  private static final String STYLE_DIR = "src/test/resources/bibtex/base";

  /**
   * Check that some greek characters encoded as UTF-8 are processed correctly
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreek() throws Exception {

    File data =
        BibTester
            .makeFile( DIR_TARGET + "/data.bib",
                       "UTF-8",
                       "@Book{ aristotle,\n"
                           + "  author =       {A\u03c1\u03b9\u03c3\u03c4o" +
                           "\u03c4\u03ad\u03bb\u03b7\u03c2},\n"
                           + "  title =        {title},\n"
                           + "  publisher =    {publisher},\n"
                           + "  year =         {year}\n" + "}\n" + "" );
    File aux = BibTester.makeFile( "test.aux", "ISO-8859-1",
                                   "\\relax\n"
                                       + "\\citation{*}\n"
                                       + "\\bibstyle{" + STYLE_DIR + "/alpha" +
                                       ".bst" + "}\n"
                                       + "\\bibdata{" + DIR_TARGET + "/data" +
                                       ".bib}\n" );
    try {
      TRunner.runTest( aux, new File( DATA_DIR, "greek.result" ),
                       "--enc=UTF-8" );
    } finally {
      if( aux.exists() && !aux.delete() ) {
        fail( aux.toString() + ": deletion failed" );
      }
      if( data.exists() && !data.delete() ) {
        fail( data.toString() + ": deletion failed" );
      }
    }
  }

}
