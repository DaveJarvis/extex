/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter;

import org.extex.TestTeX;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Test for the register {@code count}.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class CountTest {

    /**
     * main
     * 
     * @param args command line args
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(CountTest.class);
    }

    /**
     * count01
     * 
     * @exception Exception iff test failed
     */
    @Test
    @Ignore("gene: I don't know why this one fails")
    public void testCount01() throws Exception {

        TestTeX.test("jucount01", "ExTeX-Unit-tex");
    }

}
