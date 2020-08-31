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

package org.extex.interpreter.max;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the scanner routines.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ScanDimenTest extends ExTeXLauncher {

  /**
   * <testcase > This test case checks that the parser can decode a single
   * digit number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen0() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=4pt" + "\\the\\boxmaxdepth" + "\\end",
                  // --- output channel ---
                  "4.0pt" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a single
   * digit number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=4sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "4" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a negative
   * number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen11() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=-456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "-456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a number
   * with two minus signs in front.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen12() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=--456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a number
   * with three minus signs in front.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen13() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=---456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "-456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a two digit
   * number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=45sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "45" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a positive
   * number with a single {@code +} sign.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen21() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=+456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a number
   * with two plus signs in front.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen22() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=++456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a number
   * with three plus signs in front.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen23() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=+++456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a three
   * digit number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a negative
   * number with mixed signs.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen31() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=-+456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "-456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a negative
   * number with mixed signs.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen32() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=+-456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "-456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a number
   * with two minus signs and a plus sign in front.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen33() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=-+-456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a number
   * with two minus signs and two plus signs in front.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen34() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=-+-+456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser can decode a number
   * with three minus signs and two plus signs in front.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen35() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=-+-+-456sp" + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "-456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser expands macros on the
   * fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen40() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\mac{}" + "\\boxmaxdepth=\\mac-456sp"
                      + "\\escapechar=\\boxmaxdepth " + "\\the\\escapechar"
                      + "\\end",
                  // --- output channel ---
                  "-456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser expands macros on the
   * fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen41() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\mac{-}" + "\\boxmaxdepth=\\mac-456sp"
                      + "\\escapechar=\\boxmaxdepth" + "\\the\\escapechar"
                      + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser expands macros on the
   * fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen42() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\mac{}" + "\\boxmaxdepth=-\\mac456sp"
                      + "\\escapechar=\\boxmaxdepth" + "\\the\\escapechar"
                      + "\\end",
                  // --- output channel ---
                  "-456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser expands macros on the
   * fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen43() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\mac{-}" + "\\boxmaxdepth=-\\mac456sp"
                      + "\\escapechar=\\boxmaxdepth" + "\\the\\escapechar"
                      + "\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser expands macros on the
   * fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen44() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\mac{-1}" + "\\boxmaxdepth=-\\mac456sp"
                      + "\\escapechar=\\boxmaxdepth" + "\\the\\escapechar"
                      + "\\end",
                  // --- output channel ---
                  "1456" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser expands macros on the
   * fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen45() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\mac{1}" + "\\boxmaxdepth=-\\mac45" +
                      "\\mac6sp"
                      + "\\escapechar=\\boxmaxdepth" + "\\the\\escapechar"
                      + "\\end",
                  // --- output channel ---
                  "-14516" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser accepts hex numbers.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen5() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth=\"1asp " + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "26" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser accepts octal numbers.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimen6() throws Exception {

    assertSuccess(// --- input code ---
                  "\\boxmaxdepth='17sp " + "\\escapechar=\\boxmaxdepth"
                      + "\\the\\escapechar" + "\\end",
                  // --- output channel ---
                  "15" + TERM );
  }

  /**
   * <testcase > This test case checks that the parser accepts octal numbers.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testScanDimenErr1() throws Exception {

    assertFailure(// --- input code ---
                  "\\boxmaxdepth=' ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

}
