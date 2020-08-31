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

package org.extex.unit.tex.register.skip;

import org.extex.test.ExTeXLauncher;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a abstract base class for testing skip registers. It provides some
 * test cases common to all skip registers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractSkipRegisterTester extends ExTeXLauncher {

  /**
   * The field {@code primitive} contains the name of the primitive to
   * test.
   */
  private final String primitive;

  /**
   * The field {@code invocation} contains the concatenation of primitive
   * name and arguments.
   */
  private final String invocation;

  /**
   * The field {@code init} contains the default value.
   */
  private final String init;

  /**
   * The field {@code prepare} contains the the preparation code inserted
   * before each test.
   */
  private String prepare = "";

  /**
   * Creates a new object.
   *
   * @param primitive the name of the skip register to test
   * @param args      the parameters for the invocation
   * @param init      the default value
   */
  public AbstractSkipRegisterTester( String primitive, String args,
                                     String init ) {

    this.primitive = primitive;
    this.invocation = primitive + args;
    this.init = init;
  }

  /**
   * Creates a new object.
   *
   * @param primitive the name of the skip register to test
   * @param args      the arguments for the invocation
   * @param init      the default value
   * @param prepare   the preparation code inserted before each test
   */
  public AbstractSkipRegisterTester( String primitive, String args,
                                     String init, String prepare ) {

    this( primitive, args, init );
    this.prepare = this.prepare + prepare;
  }

  /**
   * Test case showing that the prefix {@code \immediate} is not applicable
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterImmediatePrefix1() throws Exception {

    assertFailure(// --- input code ---
                  prepare + "\\immediate\\" + invocation + "= 2pt ",
                  // --- error channel ---
                  "You can't use the prefix `\\immediate' with the control " +
                      "sequence"
                      + (primitive.length() > 14 ? "\n" : " ") + "\\" + primitive );
  }

  /**
   * Test case showing that the prefix {@code \immediate} is not applicable
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testSkipRegisterProtectedPrefix1() throws Exception {

    assertFailure(// --- input code ---
                  prepare + "\\protected\\" + invocation + "= 2pt ",
                  // --- error channel ---
                  "You can't use the prefix `\\protected' with the control " +
                      "sequence"
                      + (primitive.length() > 14 ? "\n" : " ") + "\\" + primitive );
  }

  /**
   * Test case showing that the prefix {@code \long} is not applicable
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterLongPrefix1() throws Exception {

    assertFailure(// --- input code ---
                  prepare + "\\long\\" + invocation + "= 2pt ",
                  // --- error channel ---
                  "You can't use the prefix `\\long' with the control sequence"
                      + (primitive.length() > 19 ? "\n" : " ") + "\\" + primitive );
  }

  /**
   * Test case showing that the prefix {@code \outer} is not applicable
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterOuterPrefix1() throws Exception {

    assertFailure(// --- input code ---
                  prepare + "\\outer\\" + invocation + "= 2pt ",
                  // --- error channel ---
                  "You can't use the prefix `\\outer' with the control sequence"
                      + (primitive.length() > 18 ? "\n" : " ") + "\\" + primitive );
  }

  /**
   * Test case showing that the primitive is defined and has its default value
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDefault1() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\the\\" + invocation + "\\end",
                  // --- output channel ---
                  init + TERM );
  }

  /**
   * Test case showing that the primitive is applicable to \showthe
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterShowthe1() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\showthe\\" + invocation + "\\end",
                 // --- output channel ---
                 out( init ), "" );
  }

  /**
   * Test case showing that an assignment of a constant 12.3pt works when 
   * using an equal sign after the primitive name
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign1() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation
                     + "=1.2pt plus 1pt minus 2pt\\showthe\\" + invocation
                     + "\\end",
                 // --- output channel ---
                 "> 1.2pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 12.3pt works when 
   * using no equal sign after the primitive name
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign2() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation
                     + " 12.3pt plus 1pt minus 2pt\\showthe\\" + invocation
                     + "\\end",
                 // --- output channel ---
                 "> 12.3pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant -12.3pt works when 
   * using an equal sign after the primitive name
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign3() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation
                     + "=-12.3pt plus 1pt minus 2pt \\showthe\\" + invocation
                     + "\\end",
                 // --- output channel ---
                 "> -12.3pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant -12.3pt works when 
   * using no equal sign after the primitive name
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign4() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation
                     + "-12.3pt plus 1pt minus 2pt \\showthe\\" + invocation
                     + "\\end",
                 // --- output channel ---
                 "> -12.3pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant -12.3pt works when 
   * using {@code \globaldefs}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign5() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\globaldefs=1 " + "\\begingroup\\" + invocation
                     + "-12.3pt plus 1.0pt minus 2.0pt\\endgroup"
                     + "\\showthe\\" + invocation + "\\end",
                 // --- output channel ---
                 "> -12.3pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment of a flexible unit is not allowed 
   * in the first component
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign10() throws Exception {

    assertFailure(// --- input code ---
                  prepare + "\\" + invocation + "=1.2fil\\showthe\\" + invocation
                      + "\\end",
                  // --- output channel ---
                  "Illegal unit of measure (pt inserted)" );
  }

  /**
   * Test case showing that an assignment of a constant 1.2fil works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign11() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=0pt plus 1.2fil\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.0pt plus 1.2fil.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 1.2fill works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign12() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=0pt plus 1.2fill\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.0pt plus 1.2fill.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 1.2filll works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign13() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=0pt plus 1.2filll\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.0pt plus 1.2filll.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 1.2fil works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign14() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=0pt minus 1.2fil\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.0pt minus 1.2fil.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 1.2fill works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign15() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=0pt minus 1.2fill\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.0pt minus 1.2fill.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 1.2filll works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign16() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=0pt minus 1.2filll\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.0pt minus 1.2filll.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 1.2mm works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAssign20() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation
                     + "=1.2in plus 1pt minus 2pt\\showthe\\" + invocation
                     + "\\end",
                 // --- output channel ---
                 "> 86.72377pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment respects {@code \\afterassignment}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAfterassignment1() throws Exception {

    String pre = ("leftskip".equals( primitive ) ? " " : "");
    String post =
        ("rightskip".equals( primitive )
            || "parfillskip".equals( invocation ) ? " " : "");
    assertOutput(// --- input code ---
                 prepare + "\\afterassignment b a" + "\\" + invocation
                     + "-12.3ptc\\showthe\\" + invocation + "\\end",
                 // --- output channel ---
                 "> -12.3pt.\n", pre + "abc" + post + TERM );
  }

  /**
   * Test case showing that the value is dimen convertible.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testSkipRegisterConvertible1() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\" + invocation + "-12.3pt \\dimen0=\\" + invocation
                      + " \\the\\dimen0 \\end",
                  // --- output channel ---
                  "-12.3pt" + TERM );
  }

  /**
   * Test case showing that the value is count convertible.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testSkipRegisterConvertible2() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\" + invocation + "-12.3pt \\count0=\\" + invocation
                      + " \\the\\count0 \\end",
                  // --- output channel ---
                  "-806093" + TERM );
  }

  /**
   * Test case showing that an assignment respects grouping.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterGroup1() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\begingroup\\" + invocation
                      + "=12.3pt plus 1pt minus 2pt\\endgroup" + " \\the\\"
                      + invocation + "\\end",
                  // --- output channel ---
                  init + TERM );
  }

  /**
   * Test case showing that an assignment of a constant 12.3pt works when 
   * using an equal sign after the primitive name
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterGlobalAssign1() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\begingroup\\global\\" + invocation
                     + "=12.3pt plus 1pt minus 2pt \\endgroup" + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 12.3pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment of a constant 12.3pt works when 
   * using no equal sign after the primitive name
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterGlobalAssign2() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\begingroup\\global\\" + invocation
                     + " 12.3pt plus 1pt minus 2pt \\endgroup" + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 12.3pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an advancement by the constant 12pt works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAdvance1() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=23pt plus 1.0pt minus 2.0pt "
                     + "\\advance\\" + invocation + " 12pt " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 35.0pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an advancement by the constant 12pt works when 
   * using the keyword {@code by}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAdvance2() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=23pt plus 1pt minus 2pt "
                     + "\\advance\\" + invocation + " by 12pt " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 35.0pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an advancement by the constant -12pt works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAdvance3() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=23pt  plus 1.0pt minus 2.0pt"
                     + "\\advance\\" + invocation + "-12pt " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 11.0pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an advancement by the constant -12pt works when 
   * using the keyword {@code by}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAdvance4() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=23pt plus 1pt minus 2pt"
                     + "\\advance\\" + invocation + " by -12pt " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 11.0pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an advancement by the constant -12.3pt works when
   * using {@code \globaldefs}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAdvance5() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\globaldefs=1 " + "\\begingroup\\" + invocation
                     + "-12.3pt plus 1pt minus 2pt \\endgroup" + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> -12.3pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment respects {@code \\afterassignment}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAfterassignment2() throws Exception {

    String pre = ("leftskip".equals( primitive ) ? " " : "");
    String post =
        ("rightskip".equals( primitive )
            || "parfillskip".equals( invocation ) ? " " : "");
    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=0pt" + "\\afterassignment b a"
                     + "\\advance\\" + invocation
                     + "-12.3pt plus 1pt minus 2ptc\\showthe\\" + invocation
                     + "\\end",
                 // --- output channel ---
                 "> -12.3pt plus 1.0pt minus 2.0pt.\n",
                 pre + "abc" + post + TERM );
  }

  /**
   * Test case showing that an advancing respects grouping.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterGroup2() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\begingroup\\advance\\" + invocation
                      + " 12.3pt plus 1pt minus 2pt \\endgroup" + " \\the\\"
                      + invocation + "\\end",
                  // --- output channel ---
                  init + TERM );
  }

  /**
   * Test case showing that an multiplication with the constant 0 works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterMultiply0() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\" + invocation + "=3pt plus 1.0pt minus 2.0pt "
                      + "\\multiply\\" + invocation + " 0 " + "\\the\\"
                      + invocation + "\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * Test case showing that an multiplication with the constant 12 works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterMultiply1() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3pt plus 1pt minus 2pt "
                     + "\\multiply\\" + invocation + " 12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 36.0pt plus 12.0pt minus 24.0pt.\n", "" );
  }

  /**
   * Test case showing that an multiplication with the constant 12 works when
   * using the keyword {@code by}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterMultiply2() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3pt plus 1pt minus 2pt "
                     + "\\multiply\\" + invocation + " by 12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 36.0pt plus 12.0pt minus 24.0pt.\n", "" );
  }

  /**
   * Test case showing that an multiplication by the constant -12 works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterMultiply3() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3pt plus 1pt minus 2pt "
                     + "\\multiply\\" + invocation + "-12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> -36.0pt plus -12.0pt minus -24.0pt.\n", "" );
  }

  /**
   * Test case showing that an multiplication by the constant -12 works when 
   * using the keyword {@code by}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterMultiply4() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3pt plus 1pt minus 2pt "
                     + "\\multiply\\" + invocation + " by -12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> -36.0pt plus -12.0pt minus -24.0pt.\n", "" );
  }

  /**
   * Test case showing that a multiplication by a constant -12.3pt works when
   * using {@code \globaldefs}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterMultiply5() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\globaldefs=1 " + "\\" + invocation
                     + "=12pt plus 1pt minus 2pt " + "\\begingroup\\multiply\\"
                     + invocation + " 3 \\endgroup" + "\\showthe\\" + invocation
                     + "\\end",
                 // --- output channel ---
                 "> 36.0pt plus 3.0pt minus 6.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment respects {@code \\afterassignment}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAfterassignment3() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\" + invocation + "=0pt " + "\\afterassignment " +
                      "b a"
                      + "\\multiply\\" + invocation + "-12 c\\the\\" + invocation
                      + "\\end",
                  // --- output channel ---
                  "abc0.0pt" + TERM );
  }

  /**
   * Test case showing that multiplication respects grouping.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterGroup3() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3pt plus 1pt minus 2pt "
                     + "\\begingroup\\multiply\\" + invocation
                     + " 12 \\endgroup" + " \\showthe\\" + invocation + "\\end",
                 // --- output channel ---
                 "> 3.0pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an division by the constant 12 works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDivide0() throws Exception {

    assertFailure(
        // --- input code ---
        prepare + "\\" + invocation + "=3.6pt " + "\\divide\\" + invocation
            + " 0 " + "\\the\\" + invocation + "\\end",
        // --- error channel ---
        "Arithmetic overflow" );
  }

  /**
   * Test case showing that an division by the constant 12 works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDivide1() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3.6pt plus 12pt minus 24pt "
                     + "\\divide\\" + invocation + " 12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.29999pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an division by the constant 12 works when using 
   * the keyword {@code by}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDivide2() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3.6pt plus 12pt minus 24pt "
                     + "\\divide\\" + invocation + " by 12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.29999pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an multiplication by the constant -12 works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDivide3() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3.6pt plus 12pt minus 24pt "
                     + "\\divide\\" + invocation + "-12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> -0.29999pt plus -1.0pt minus -2.0pt.\n", "" );
  }

  /**
   * Test case showing that an multiplication by the constant -12 works when 
   * using the keyword {@code by}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDivide4() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=3.6pt plus 12pt minus 24pt "
                     + "\\divide\\" + invocation + " by -12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> -0.29999pt plus -1.0pt minus -2.0pt.\n", "" );
  }

  /**
   * Test case showing that a division by a constant -12.3pt works when using
   * {@code \globaldefs}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDivide5() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\globaldefs=1 " + "\\" + invocation + "=-246pt "
                     + "\\begingroup\\divide\\" + invocation + "-123 \\endgroup"
                     + "\\showthe\\" + invocation + "\\end",
                 // --- output channel ---
                 "> 2.0pt.\n", "" );
  }

  /**
   * Test case showing that an assignment respects {@code \\afterassignment}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterAfterassignment4() throws Exception {

    assertSuccess(// --- input code ---
                  prepare + "\\afterassignment b a" + "\\divide\\" + invocation
                      + "-12 c\\end",
                  // --- output channel ---
                  "abc" + TERM );
  }

  /**
   * Test case showing that division by the constant -12 works when using the
   * keyword {@code by}
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterDivide6() throws Exception {

    assertOutput(// --- input code ---
                 prepare + "\\" + invocation + "=-3.6pt plus -12pt minus -24pt "
                     + "\\divide\\" + invocation + " by -12 " + "\\showthe\\"
                     + invocation + "\\end",
                 // --- output channel ---
                 "> 0.29999pt plus 1.0pt minus 2.0pt.\n", "" );
  }

  /**
   * Test case showing that division respects grouping.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSkipRegisterGroup4() throws Exception {

    assertOutput(
        // --- input code ---
        prepare + "\\" + invocation + "=3pt plus 1pt minus 2pt "
            + "\\begingroup\\divide\\" + invocation + " 123 \\endgroup"
            + " \\showthe\\" + invocation + "\\end",
        // --- output channel ---
        "> 3.0pt plus 1.0pt minus 2.0pt.\n", "" );
  }

}
