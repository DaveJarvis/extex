/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.dimen;

import org.extex.base.parser.dimen.Accumulator;
import org.extex.base.parser.dimen.Function0;
import org.extex.base.parser.dimen.Function2;
import org.extex.base.parser.dimen.LengthParser;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.scaled.ScaledNumber;
import org.extex.test.ExTeXLauncher;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * This is a test suite for the for the parser of data type Dimen.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LengthParserTest extends ExTeXLauncher {

  /**
   * Test case showing that abs on a positive value results in this value
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAbs1() throws Exception {

    assertSuccess( "\\dimen1=1.2pt"
                       + "\\dimen0=abs(\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "1.2pt" + TERM );
  }

  /**
   * Test case showing that abs on a negative value results in the negated
   * value
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAbs2() throws Exception {

    assertSuccess( "\\dimen1=-1.2pt"
                       + "\\dimen0=abs(\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "1.2pt" + TERM );
  }

  /**
   * Test case showing that abs() needs a closing brace.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAbs3() throws Exception {

    assertFailure( "\\dimen0=abs(4pt ",
                   "Unexpected end of file" );
  }

  /**
   * Test case showing that a dimen variable incremented by a fraction is
   * parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAdd1() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(\\dimen1+1.23pt)"
                       + "\\the\\dimen0\\end",
                   "3.23pt" + TERM );
  }

  /**
   * Test case showing that a fraction incremented by a dimen variable is
   * parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAdd2() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(1.23pt+\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "3.23pt" + TERM );
  }

  /**
   * Test case showing that a successive addition is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAdd3() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(\\dimen1+\\dimen1+\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "6.0pt" + TERM );
  }

  /**
   * Test case showing that values of differnt order can not be added
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAdd4() throws Exception {

    assertFailure( "\\dimen0=(1+2pt)",
                   "Incompatible unit for + found: sp^0 <> sp^1" );
  }

  /**
   * Test case showing that cos needs a scalar argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCos1() throws Exception {

    assertFailure( "\\dimen0=cos(1pt) ",
                   "Function cos requires a scalar argument instead of 1.0sp" );
  }

  /**
   * Test case showing that cos(0) = 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCos2() throws Exception {

    assertSuccess( "\\dimen0=(cos(0)pt)"
                       + "\\the\\dimen0\\end",
                   "1.0pt" + TERM );
  }

  /**
   * Test case showing that cos(pi/2) = 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCos3() throws Exception {

    assertSuccess( "\\dimen0=(cos(pi/2)pt)"
                       + "\\the\\dimen0\\end",
                   "0.0pt" + TERM );
  }

  /**
   * Test case showing that a count variable is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount1() throws Exception {

    assertSuccess( "\\count1=12345 "
                       + "\\dimen0=\\count1 pt"
                       + "\\the\\dimen0\\end",
                   "12345.0pt" + TERM );
  }

  /**
   * Test case showing that a dimen constant divided by zero leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDiv1() throws Exception {

    assertFailure( "\\dimen0=(1pt/0)",
                   "Arithmetic overflow" );
  }

  /**
   * Test case showing that 2pt*2pt/1pt=4pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDiv10() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(\\dimen1*\\dimen1/1pt)"
                       + "\\the\\dimen0\\end",
                   "4.0pt" + TERM );
  }

  /**
   * Test case showing that 4pt/2 = 2pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDiv2() throws Exception {

    assertSuccess( "\\dimen0=(4pt/2) "
                       + "\\the\\dimen0\\end",
                   "2.0pt" + TERM );
  }

  /**
   * Test case showing that a dimen variable divided by zero leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDivError1() throws Exception {

    assertFailure( "\\dimen1=2pt"
                       + "\\dimen0=(\\dimen1/0)",
                   "Arithmetic overflow" );
  }

  /**
   * Test case showing that unmatched parentheses are recognized.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure( "\\dimen0=(/",
                   "Missing number, treated as zero" );
  }

  /**
   * Test case showing that unmatched parentheses are recognized.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError2() throws Exception {

    assertFailure( "\\dimen0=(1pt;",
                   "Missing ) inserted for expression" );
  }

  /**
   * Test case showing that unmatched parentheses at EOF is recognized
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError3() throws Exception {

    assertFailure( "\\dimen0=(1pt",
                   "Unexpected end of file" );
  }

  /**
   * Test case showing that min() needs a closing brace.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testErrorMin3() throws Exception {

    assertFailure( "\\dimen0=min(4pt, 2pt, 3pt :",
                   "Missing ) inserted for expression" );
  }

  /**
   * Test case showing that min() needs a closing brace.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testErrorMin4() throws Exception {

    assertFailure( "\\dimen0=min(4pt, 2pt, 3pt ", "Unexpected end of file" );
  }

  /**
   * Test case showing that expansion during the parsing works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand1() throws Exception {

    assertSuccess( DEFINE_BRACES
                       + "\\def\\xxx{1.2pt}"
                       + "\\dimen0=\\xxx "
                       + "\\the\\dimen0\\end",
                   "1.2pt" + TERM );
  }

  /**
   * Test case showing that an expression needs a proper expression
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExprError1() throws Exception {

    assertFailure( "\\dimen0= ",
                   "Missing number, treated as zero" );
  }

  /**
   * Test case showing that an expression needs a closing brace.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExprError2() throws Exception {

    assertFailure( "\\dimen0=(4pt ",
                   "Unexpected end of file" );
  }

  /**
   * Test case showing that an expression needs a closing brace.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExprError3() throws Exception {

    assertFailure( "\\dimen0=(4pt",
                   "Unexpected end of file" );
  }

  /**
   * Test case showing that undefined function is recognized.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testFunctionError1() throws Exception {

    assertFailure( "\\dimen0=(xyzzy())",
                   "Syntax error" );
  }

  /**
   * Test case showing that max on one dimen results in this value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMax1() throws Exception {

    assertSuccess( "\\dimen1=1.2pt"
                       + "\\dimen0=max(\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "1.2pt" + TERM );
  }

  /**
   * Test case showing that max() on three dimens works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMax2() throws Exception {

    assertSuccess( "\\dimen0=max(1pt, 2pt, 3pt)"
                       + "\\the\\dimen0\\end",
                   "3.0pt" + TERM );
  }

  /**
   * Test case showing that max on dimen works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMax3() throws Exception {

    assertSuccess( "\\dimen0=max(4pt, 2pt, 3pt)"
                       + "\\the\\dimen0\\end",
                   "4.0pt" + TERM );
  }

  /**
   * Test case showing that the function max needs arguments.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMaxError1() throws Exception {

    assertFailure( "\\dimen0=max()",
                   "Missing number, treated as zero" );
  }

  /**
   * Test case showing that incompatible units lead to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMaxError2() throws Exception {

    assertFailure( "\\dimen0=max(1.2, 12pt)",
                   "Incompatible unit for max found: sp^0 <> sp^1" );
  }

  /**
   * Test case showing that max needs an argument parenthesis.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMaxError3() throws Exception {

    assertFailure( "\\dimen0=max",
                   "Unexpected end of file" );
  }

  /**
   * Test case showing that max needs an argument parenthesis.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMaxError4() throws Exception {

    assertFailure( "\\dimen0=max x",
                   "Missing ( inserted for function max instead of the letter" +
                       " x" );
  }

  /**
   * Test case showing that min() on a single value returns this value
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMin1() throws Exception {

    assertSuccess( "\\dimen1=1.2pt"
                       + "\\dimen0=min(\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "1.2pt" + TERM );
  }

  /**
   * Test case showing that min on three dimens works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMin2() throws Exception {

    assertSuccess( "\\dimen0=min(1pt, 2pt, 3pt)"
                       + "\\the\\dimen0\\end",
                   "1.0pt" + TERM );
  }

  /**
   * Test case showing that min() in three dimens works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMin3() throws Exception {

    assertSuccess( "\\dimen0=min(4pt, 2pt, 3pt)"
                       + "\\the\\dimen0\\end",
                   "2.0pt" + TERM );
  }

  /**
   * Test case showing that the function min() needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMinError1() throws Exception {

    assertFailure( "\\dimen0=min()",
                   "Missing number, treated as zero" );
  }

  /**
   * Test case showing that min on number and dimen leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMinError2() throws Exception {

    assertFailure( "\\dimen0=min(1.2, 12pt)",
                   "Incompatible unit for min found: sp^0 <> sp^1" );
  }

  /**
   * Test case showing that negation of a variable is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMinus1() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=-\\dimen1 "
                       + "\\the\\dimen0\\end",
                   "-1.23pt" + TERM );
  }

  /**
   * Test case showing that double negation of a variable is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMinus2() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=--\\dimen1 "
                       + "\\the\\dimen0\\end",
                   "1.23pt" + TERM );
  }

  /**
   * Test case showing that a integer multiplied dimen variable is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMult1() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=(2*\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "2.45999pt" + TERM );
  }

  /**
   * Test case showing that a dimen variable multiplied by an integer is
   * parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMult2() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=(\\dimen1 *2)"
                       + "\\the\\dimen0\\end",
                   "2.45999pt" + TERM );
  }

  /**
   * Test case showing that a fraction multiplied dimen variable is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMult3() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(1.23*\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "2.45999pt" + TERM );
  }

  /**
   * Test case showing that a dimen variable multiplied by a fraction is
   * parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMult4() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(\\dimen1*1.23)"
                       + "\\the\\dimen0\\end",
                   "2.45999pt" + TERM );
  }

  /**
   * Test case showing that the function pi() is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPi1() throws Exception {

    assertSuccess( "\\dimen1=(pi pt)"
                       + "\\the\\dimen1\\end",
                   "3.14159pt" + TERM );
  }

  /**
   * Test case showing that a positive sign of a variable is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPlus1() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=+\\dimen1 "
                       + "\\the\\dimen0\\end",
                   "1.23pt" + TERM );
  }

  /**
   * Test case showing that double positive sign of a variable is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPlus2() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=++\\dimen1 "
                       + "\\the\\dimen0\\end",
                   "1.23pt" + TERM );
  }

  /**
   * Test case showing that 0pt is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPt0() throws Exception {

    assertSuccess( "\\dimen0=0pt\\the\\dimen0\\end",
                   "0.0pt" + TERM );
  }

  /**
   * Test case showing that a positive number with fraction is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPt1() throws Exception {

    assertSuccess( "\\dimen0=123.4pt\\the\\dimen0\\end",
                   "123.4pt" + TERM );
  }

  /**
   * Test case showing that a negative number with fraction is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPt2() throws Exception {

    assertSuccess( "\\dimen0=-123.4pt\\the\\dimen0\\end",
                   "-123.4pt" + TERM );
  }

  /**
   * Test case showing that a positive fraction number is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPt3() throws Exception {

    assertSuccess( "\\dimen0=.45pt\\the\\dimen0\\end",
                   "0.45pt" + TERM );
  }

  /**
   * Test case showing that a negative fraction number is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPt4() throws Exception {

    assertSuccess( "\\dimen0=-.45pt\\the\\dimen0\\end",
                   "-0.45pt" + TERM );
  }

  /**
   * Test case showing that a registered function is called.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRegister0() throws Exception {

    LengthParser.register( "f", new Function0() {

      @Override
      public Accumulator apply() throws HelpingException {

        return new Accumulator( 1234 * ScaledNumber.ONE / 100 );
      }
    } );
    assertSuccess( "\\dimen0=(f pt) "
                       + "\\the\\dimen0\\end",
                   "12.34pt" + TERM );
    assertNotNull( LengthParser.unregister( "f" ) );
  }

  /**
   * Test case showing that a registered function is called.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRegister2() throws Exception {

    LengthParser.register( "h", new Function2() {

      @Override
      public Accumulator apply( Accumulator arg1, Accumulator arg2 )
          throws HelpingException {

        return new Accumulator( 3456 * ScaledNumber.ONE / 100, 1 );
      }
    } );
    assertSuccess( "\\dimen0=(h(1pt,2pt)) "
                       + "\\the\\dimen0\\end",
                   "34.56pt" + TERM );
    assertNotNull( LengthParser.unregister( "h" ) );
  }

  /**
   * Test case showing that a binary function needs a comma as argument
   * separator
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRegister3() throws Exception {

    LengthParser.register( "h", new Function2() {

      @Override
      public Accumulator apply( Accumulator arg1, Accumulator arg2 )
          throws HelpingException {

        return null;
      }
    } );
    assertFailure( "\\dimen0=(h(1pt:2pt)) "
                       + "\\the\\dimen0\\end",
                   "Missing , instead of the character :" );
  }

  /**
   * Test case showing that a binary function needs a comma as argument
   * separator
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRegister4() throws Exception {

    LengthParser.register( "h", new Function2() {

      @Override
      public Accumulator apply( Accumulator arg1, Accumulator arg2 )
          throws HelpingException {

        return null;
      }
    } );
    assertFailure( "\\dimen0=(h(1pt ",
                   "Unexpected end of file" );
  }

  /**
   * Test case showing that sgn(0) = 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSgn1() throws Exception {

    assertSuccess( "\\dimen0=sgn(0)pt"
                       + "\\the\\dimen0\\end",
                   "0.0pt" + TERM );
  }

  /**
   * Test case showing that sgn(10) = 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSgn2() throws Exception {

    assertSuccess( "\\dimen0=sgn(10)pt"
                       + "\\the\\dimen0\\end",
                   "1.0pt" + TERM );
  }

  /**
   * Test case showing that sgn(-10) = -1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSgn3() throws Exception {

    assertSuccess( "\\dimen0=sgn(-10)pt"
                       + "\\the\\dimen0\\end",
                   "-1.0pt" + TERM );
  }

  /**
   * Test case showing that sin needs a scalar argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSin1() throws Exception {

    assertFailure( "\\dimen0=sin(1pt) ",
                   "Function sin requires a scalar argument instead of 1.0sp" );
  }

  /**
   * Test case showing that sin(0) = 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSin2() throws Exception {

    assertSuccess( "\\dimen0=sin(0)pt"
                       + "\\the\\dimen0\\end",
                   "0.0pt" + TERM );
  }

  /**
   * Test case showing that sin(pi/2) = 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSin3() throws Exception {

    assertSuccess( "\\dimen0=(sin(pi/2)pt)"
                       + "\\the\\dimen0\\end",
                   "0.99998pt" + TERM );
  }

  /**
   * Test case showing that a dimen variable subtracted by a fraction is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubtract1() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(\\dimen1-1.23pt)"
                       + "\\the\\dimen0\\end",
                   "0.77pt" + TERM );
  }

  /**
   * Test case showing that a dimen variable subtracted by a fraction is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubtract2() throws Exception {

    assertSuccess( "\\dimen1=2pt"
                       + "\\dimen0=(\\dimen1-\\dimen1-\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "-2.0pt" + TERM );
  }

  /**
   * Test case showing that numbers with different orders can not be subtracted
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubtract3() throws Exception {

    assertFailure( "\\dimen0=(12pt-1)",
                   "Incompatible unit for - found: sp^1 <> sp^0" );
  }

  /**
   * Test case showing that numbers with different orders can not be
   * subtracted
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSubtract4() throws Exception {

    assertFailure( "\\dimen0=(1-12pt)",
                   "Incompatible unit for - found: sp^0 <> sp^1" );
  }

  /**
   * Test case showing that tan needs a scalar argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTan1() throws Exception {

    assertFailure( "\\dimen0=tan(1pt) ",
                   "Function tan requires a scalar argument instead of 1.0sp" );
  }

  /**
   * Test case showing that tan(0) = 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTan2() throws Exception {

    assertSuccess( "\\dimen0=tan(0)pt"
                       + "\\the\\dimen0\\end",
                   "0.0pt" + TERM );
  }

  /**
   * Test case showing that a complex formula works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTerm1() throws Exception {

    assertSuccess( "\\dimen1=1.2pt"
                       + "\\dimen0=((1+2)*\\dimen1)"
                       + "\\the\\dimen0\\end",
                   "3.59999pt" + TERM );
  }

  /**
   * Test case showing that dimens need a unit.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnitError1() throws Exception {

    assertFailure( "\\dimen1=2pt"
                       + "\\dimen0=(1)",
                   "Illegal unit of measure (pt inserted)" );
  }

  /**
   * Test case showing that a unit of pt^{-1} can not be assigned to a dimen
   * register
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnitError2() throws Exception {

    assertFailure( "\\dimen1=2pt"
                       + "\\dimen0=(1/\\dimen1)",
                   "Illegal unit for a length value found: sp^-1" );
  }

  /**
   * Test case showing that a value with unit pt^2 can not be assigned to a
   * dimen register
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnitError3() throws Exception {

    assertFailure( "\\dimen0=(1pt*1pt)",
                   "Illegal unit for a length value found: sp^2" );
  }

  /**
   * Test case showing that a value with dimension pt^3 can not be assigned
   * to a dimen
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnitError4() throws Exception {

    assertFailure( "\\dimen0=(1pt*1pt*1pt)",
                   "Illegal unit for a length value found: sp^3" );
  }

  /**
   * Test case showing that a dimen variable is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVar1() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=\\dimen1 "
                       + "\\the\\dimen0\\end",
                   "1.23pt" + TERM );
  }

  /**
   * Test case showing that a negated dimen variable is parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVar2() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=-\\dimen1 "
                       + "\\the\\dimen0\\end",
                   "-1.23pt" + TERM );
  }

  /**
   * Test case showing that a double negated dimen variable is parsed
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVar3() throws Exception {

    assertSuccess( "\\dimen1=1.23pt"
                       + "\\dimen0=--\\dimen1 "
                       + "\\the\\dimen0\\end",
                   "1.23pt" + TERM );
  }

}
