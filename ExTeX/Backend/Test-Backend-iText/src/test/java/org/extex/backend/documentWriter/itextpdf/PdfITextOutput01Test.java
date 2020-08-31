/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.itextpdf;

import java.io.File;
import java.util.Properties;

import org.extex.test.EqualityValidator;
import org.extex.test.ExTeXLauncher;
import org.extex.test.Validator;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the xml backend.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class PdfITextOutput01Test extends ExTeXLauncher {

  /**
   * The properties.
   */
  private static Properties prop = null;


  public PdfITextOutput01Test() {

    // delete temp files after the test
    new File( "texput.log" ).deleteOnExit();

  }

  /**
   * TODO
   *
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {

    prop = new Properties( System.getProperties() );
    prop.setProperty( "extex.output", "itext" );
  }

  /**
   * Test: use default output with afm font 'fxlr'.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void testDefaultOutput() throws Exception {

    // use default output
    prop.remove( "extex.output" );
    assertOutput( prop, // --- input code ---
                  "\\font\\hugo=fxlr " + "\\hugo " + "Hugo " + "\\end",
        /* logValidator */null, /* outputValidator */
                  new EqualityValidator( "test", "Hugo" + TERM ) );

  }

  /**
   * Test: use default output with afm font 'fxlr'.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void testOutput() throws Exception {

    assertOutput( prop, // --- input code ---
                  "\\font\\hugo=fxlr " + "\\hugo " + "Hugo " + "\\end",
        /* logValidator */
                  new Validator() {

                    @Override
                    public boolean validate( String s ) {

                      System.out.println( s );
                      return true;
                    }

                  }, /* outputValidator */
                  null );

  }

}
