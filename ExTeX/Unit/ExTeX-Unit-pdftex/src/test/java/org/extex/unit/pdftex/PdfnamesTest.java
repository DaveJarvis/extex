/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex;

import org.extex.test.NoFlagsButProtectedPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \pdfnames}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PdfnamesTest extends NoFlagsButProtectedPrimitiveTester {

  /**
   * Creates a new namesect.
   */
  public PdfnamesTest() {

    super( "pdfnames", "{}", "\\pdfoutput=1 " );
    setConfig( "pdftex-test" );
  }

  /**
   * <testcase primitive="\pdfnames">
   * Test case checking that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\pdfoutput=1 " + "a \\pdfnames b",
                  //--- output message ---
                  "Missing `{' inserted" );
  }

  //TODO implement more primitive specific test cases
}
