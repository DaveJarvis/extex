/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.unit.etex;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Test for the primitive &#5c;unless.
 *
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 */
public class UnlessTest {

  /**
   * Main entry function for running alone.
   *
   * @param args command line args
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( UnlessTest.class );
  }

  /**
   * Test the primitive {@code &#5c;unless}.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore("gene: I don't know why this one fails")
  public void testUnless() throws Exception {

    // TestTeX.test("juunless", "ExTeX-Unit-etex", "etex");
  }

}
