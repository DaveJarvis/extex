/*
 * Copyright (C) 2007-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command;

import org.extex.exindex.core.exception.InconsistentFlagsException;
import org.extex.exindex.core.exception.IndexAliasLoopException;
import org.extex.exindex.core.exception.UnknownIndexException;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.lisp.exception.LMissingArgumentsException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a test suite for (define-attributes).
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DefineIndexTest {

  /**
   * Test that a new index can be defined.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test01() throws Exception {

    IndexContainer container =
        SomeTestUtilities.runTest( "(define-index \"abc\")" );
    assertEquals( 2, container.getSize() );
  }

  /**
   * Test that :drop is accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test02() throws Exception {

    IndexContainer container =
        SomeTestUtilities.runTest( "(define-index \"a\" :drop)" );
    StructuredIndex a = container.get( "a" );
    assertNotNull( a );
    assertTrue( a.isDropped() );
  }

  /**
   * Test that :merge-to works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void test03() throws Exception {

    IndexContainer container =
        SomeTestUtilities.runTest( "(define-index \"a\")"
                                       + "(define-index \"b\" :merge-to " +
                                       "\"a\")" );
    StructuredIndex a = container.get( "a" );
    assertNotNull( a );
    StructuredIndex b = container.get( "b" );
    assertNotNull( b );
    assertEquals( a, b.getAlias() );
  }

  /**
   * Test that an argument is needed.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = LMissingArgumentsException.class)
  public final void testError01() throws Exception {

    SomeTestUtilities.runTest( "(define-index)" );
  }

  /**
   * Test that an undefined index in the merge-to leads to an
   * error.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = UnknownIndexException.class)
  public final void testError02() throws Exception {

    SomeTestUtilities.runTest( "(define-index \"a\" :merge-to \"b\")" );
  }

  /**
   * Test that a loop of length 1 is detected.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = IndexAliasLoopException.class)
  public final void testError03() throws Exception {

    SomeTestUtilities.runTest( "(define-index \"a\" :merge-to \"a\")" );
  }

  /**
   * Test that a loop of length 2 is detected.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = IndexAliasLoopException.class)
  public final void testError04() throws Exception {

    SomeTestUtilities.runTest( "(define-index \"a\")"
                                   + "(define-index \"b\" :merge-to \"a\")"
                                   + "(define-index \"a\" :merge-to \"b\")" );
  }

  /**
   * Test that :drop and :merge-to can not be used
   * together.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = InconsistentFlagsException.class)
  public final void testError05() throws Exception {

    SomeTestUtilities.runTest( "(define-index \"a\" :drop :merge-to \"b\")" );
  }

}
