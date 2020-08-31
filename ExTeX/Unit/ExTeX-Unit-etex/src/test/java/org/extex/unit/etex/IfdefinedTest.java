/*
 * Copyright (C) 2004-2007  The ExTeX Group and individual authors listed below
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
 * Test for the primitive \ifdefined.
 *
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 */
public class IfdefinedTest {

  /**
   * main
   *
   * @param args command line args
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IfdefinedTest.class );
  }

  /**
   * Test the primitive \ifdefined.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore("gene: I don't know why this one fails")
  public void testIfdefined() throws Exception {

    // TestTeX.test("juifdefined", "ExTeX-Unit-etex", "etex");
  }

}
