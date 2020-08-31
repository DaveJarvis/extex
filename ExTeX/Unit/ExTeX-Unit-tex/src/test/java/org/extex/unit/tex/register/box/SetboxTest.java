/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.box;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \setbox}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class SetboxTest extends NoFlagsButGlobalPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( SetboxTest.class );
  }


  public SetboxTest() {

    setPrimitive( "setbox" );
    setArguments( "1=\\hbox{}" );
    setPrepare( "0" );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * needs a key.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure( "\\setbox ",
                   "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * needs a box.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure( "\\setbox 1",
                   "A <box> was supposed to be here" );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * respects groups.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGroup1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "{\\setbox1=\\hbox{abc}}\\box1\\end ",
        "" );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * respects \global.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGroup2() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "{\\global\\setbox1=\\hbox{abc}}\\box1\\end ",
        "abc" + TERM );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * respects \globaldefs.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGroup3() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\globaldefs=1{\\setbox1=\\hbox{abc}}\\box1\\end ",
        "abc" + TERM );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * respects \everyhbox.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEveryhbox1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\everyhbox{x}-\\setbox1=\\hbox{abc}\\box1\\end ",
        "-xabc" + TERM );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * respects \afterassignment.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAfterassignment1() throws Exception {

    assertSuccess(
        DEFINE_BRACES
            + "\\afterassignment x-\\setbox1=\\hbox{abc}\\box1\\end ",
        "-xabc" + TERM );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * takes some kind of a box.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\setbox1=\\box0\\end ",
        "" );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * takes some kind of a box.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign2() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\setbox1=\\copy0\\end ",
        "" );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * takes some kind of a box.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign3() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\setbox1=\\lastbox\\end ",
        "" );
  }

  /**
   * <testcase primitive="\setbox"> Test case checking that {@code \setbox}
   * assigns without the =.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign4() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\setbox1\\hbox{abc}--\\box1\\end ",
        "--abc" + TERM );
  }

  // TODO implement more primitive specific test cases
}
