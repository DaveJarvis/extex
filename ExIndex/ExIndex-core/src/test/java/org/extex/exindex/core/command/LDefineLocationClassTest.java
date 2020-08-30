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

import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingArgumentsException;
import org.junit.Test;

/**
 * This is a test suite for (define-attributes).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LDefineLocationClassTest {

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        SomeTestUtilities
            .runTest("(define-location-class \"abc\" (\"arabic-numbers\"))");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LMissingArgumentsException.class)
    public final void testError01() throws Exception {

        SomeTestUtilities.runTest("(define-location-class )");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError02() throws Exception {

        SomeTestUtilities.runTest("(define-location-class \"abc\")");
    }

}
