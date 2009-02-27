/*
 * Copyright (C) 2004-2007 Michael Niedermair
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

package org.extex.unit.tex;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import de.dante.tex.TestTeX;

/**
 * Test for the primitive <tt>jobname</tt>.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class JobnameTest {

    /**
     * main
     * 
     * @param args commandlineparams
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(JobnameTest.class);
    }

    /**
     * Test 01 for jobname
     * 
     * @exception Exception iff test failed
     */
    @Test
    public void testJobname01() throws Exception {

        TestTeX.test("jujobnametest", "ExTeX-Unit-tex");
    }

}
