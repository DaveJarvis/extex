/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.extex.backend.documentWriter.util.PageManager;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This class contains a test suite for the {@link PageManager}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PageManagerTest {

    /**
     * Command line interface for the tests.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(PageManagerTest.class);
    }

    /**
     * Run a test case.
     * 
     * @param reference the string containing the indicator of the expected
     *        result
     * @param spec the specification to test
     */
    private void run(String reference, String spec) {

        PageManager pm = new PageManager();
        if (spec != null) {
            pm.addPages(spec);
        }

        for (int i = 0; i < reference.length(); i++) {
            if (reference.charAt(i) == '_') {
                assertTrue( "true " + i, pm.isSelected( i));
            } else {
                assertFalse( "false " + i, pm.isSelected( i));
            }
        }
    }

    /**
     *  Check that initially all pages are accepted.
     */
    @Test
    public void testInit() {

        run("____________________", null);
    }

    /**
     *  Check that no pages are accepted for the empty specification.
     *
     */
    @Test
    public void testEmpty() {

        run("01234567890123456789", "");
    }

    /**
     *  Check that no pages are accepted for the empty specification.
     *
     */
    @Test
    public void testSpace() {

        run("01234567890123456789", " ");
    }

    /**
     * Check that only the page 1 is accepted if only this page is requested (single page number)
*/
    @Test
    public void test1() {

        run("0_234567890123456789", "1");
    }

    /**
     * Check that only the page 2 is accepted if only this page is requested (single page number)
*/
    @Test
    public void test2() {

        run("01_34567890123456789", "2");
    }

    /**
     * Check that only the page 1 is accepted if only this page is requested (single page number; two digits)
*/
    @Test
    public void test3() {

        run("01234567890_23456789", "11");
    }

    /**
     * Check that only the pages 1 and 3 is accepted if only this page is requested (comma operator)
*/
    @Test
    public void test4() {

        run("0_2_4567890123456789", "1,3");
    }

    /**
     * Check that all the pages up to 2 are accepted if only this page is requested (initial to operator)
*/
    @Test
    public void test5() {

        run("___34567890123456789", "-2");
    }

    /**
     * Check that all the pages greater than 12 are accepted if only this page is requested (terminal to operator)
*/
    @Test
    public void test6() {

        run("012345678901________", "12-");
    }

    /**
     * Check that all requested pages are accepted (complex combination)
*/
    @Test
    public void test10() {

        run("0_2_4567_____3456789", "1,3,8-12");
    }

    /**
     * Check that all requested pages are accepted (complex combination)
*/
    @Test
    public void test11() {

        run("0____________3456789", "1-9,8-12");
    }

}
