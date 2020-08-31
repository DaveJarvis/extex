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

package org.extex.font.format.ofm;

import org.extex.font.format.tfm.TfmHeaderArray;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test for the {@link OfmReader}.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class OfmReaderTest {

  /**
   * The reader.
   */
  private OfmReader reader;

  /**
   * Creates a new object.
   *
   * @throws Exception if an error occurred.
   */
  public OfmReaderTest() throws Exception {

    if( reader == null ) {
      reader =
          new OfmReader( new FileInputStream(
              "../ExTeX-Font-tfm/src/font/omlgc.ofm" ), "omlgc" );
    }
  }

  /**
   * Test, if the font exists.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void testExist01() throws Exception {

    assertNotNull( reader );
  }

  /**
   * Test the header.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void testHeader01() throws Exception {

    assertNotNull( reader );
    assertEquals( 0, reader.getOfmLevel() );

    OfmHeaderLengths l = reader.getLengths();
    assertNotNull( l );

    assertEquals( 0x21, l.getBc() );
  }

  /**
   * Test the header.
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void testHeader02() throws Exception {

    assertNotNull( reader );

    TfmHeaderArray h = reader.getHeader();
    assertNotNull( h );
    assertEquals( "10.0", h.getDesignsize().toString() );
    assertEquals( 0x48B7D0D4, h.getChecksum() );
    assertEquals( "OMEGA-LGC", h.getCodingscheme() );
  }

}
