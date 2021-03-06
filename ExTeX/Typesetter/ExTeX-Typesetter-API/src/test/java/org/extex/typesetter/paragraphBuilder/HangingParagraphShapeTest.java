/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.paragraphBuilder;

import org.extex.core.dimen.Dimen;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the class HangingParagraphShape.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class HangingParagraphShapeTest {

  /**
   * The constant {@code INDENT} contains the amount of indentation to use.
   */
  private static final int INDENT = 1024;

  /**
   * The constant {@code RIGHT} contains the right margin.
   */
  private static final int RIGHT = 4096;

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( HangingParagraphShapeTest.class );
  }

  /**
   * Test case checking that parshape of length 0 repeats 0 as left and the
   * right margin.
   */
  @Test
  public void test0() {

    HangingParagraphShape shape =
        new HangingParagraphShape( 0, new Dimen( INDENT ),
                                   new Dimen( RIGHT ) );

    assertEquals( 0, shape.getIndent( 0 ).getValue() );
    assertEquals( 0, shape.getIndent( 1 ).getValue() );
    assertEquals( 0, shape.getIndent( 2 ).getValue() );
    assertEquals( 0, shape.getIndent( 3 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 0 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 1 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 2 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 3 ).getValue() );
  }

  /**
   * Test case checking that parshape of length 1 repeats 0 as left and the
   * right margin.
   */
  @Test
  public void test1() {

    HangingParagraphShape shape =
        new HangingParagraphShape( 1, new Dimen( INDENT ),
                                   new Dimen( RIGHT ) );

    assertEquals( 0, shape.getIndent( 0 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 1 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 2 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 3 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 0 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 1 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 2 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 3 ).getValue() );
  }

  /**
   * Test case checking that parshape of length 2 repeats 0 as left and the
   * right margin.
   */
  @Test
  public void test2() {

    HangingParagraphShape shape =
        new HangingParagraphShape( 2, new Dimen( INDENT ),
                                   new Dimen( RIGHT ) );

    assertEquals( 0, shape.getIndent( 0 ).getValue() );
    assertEquals( 0, shape.getIndent( 1 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 2 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 3 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 0 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 1 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 2 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 3 ).getValue() );
  }

  /**
   * ...
   */
  @Test
  public void test3() {

    HangingParagraphShape shape =
        new HangingParagraphShape( 3, new Dimen( INDENT ),
                                   new Dimen( RIGHT ) );

    assertEquals( 0, shape.getIndent( 0 ).getValue() );
    assertEquals( 0, shape.getIndent( 1 ).getValue() );
    assertEquals( 0, shape.getIndent( 2 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 3 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 0 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 1 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 2 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 3 ).getValue() );
  }

  /**
   * ...
   */
  @Test
  public void testM1() {

    HangingParagraphShape shape =
        new HangingParagraphShape( -1, new Dimen( INDENT ), new Dimen(
            RIGHT ) );

    assertEquals( INDENT, shape.getIndent( 0 ).getValue() );
    assertEquals( 0, shape.getIndent( 1 ).getValue() );
    assertEquals( 0, shape.getIndent( 2 ).getValue() );
    assertEquals( 0, shape.getIndent( 3 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 0 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 1 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 2 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 3 ).getValue() );
  }

  /**
   * ...
   */
  @Test
  public void testM2() {

    HangingParagraphShape shape =
        new HangingParagraphShape( -2, new Dimen( INDENT ), new Dimen(
            RIGHT ) );

    assertEquals( INDENT, shape.getIndent( 0 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 1 ).getValue() );
    assertEquals( 0, shape.getIndent( 2 ).getValue() );
    assertEquals( 0, shape.getIndent( 3 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 0 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 1 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 2 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 3 ).getValue() );
  }

  /**
   * ...
   */
  @Test
  public void testM3() {

    HangingParagraphShape shape =
        new HangingParagraphShape( -3, new Dimen( INDENT ), new Dimen(
            RIGHT ) );

    assertEquals( INDENT, shape.getIndent( 0 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 1 ).getValue() );
    assertEquals( INDENT, shape.getIndent( 2 ).getValue() );
    assertEquals( 0, shape.getIndent( 3 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 0 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 1 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 2 ).getValue() );
    assertEquals( RIGHT, shape.getLength( 3 ).getValue() );
  }

}
