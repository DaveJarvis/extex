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

package org.extex.unit.extex.backend;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for \writeType.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class WriterTypeTest extends NoFlagsPrimitiveTester {

    /**
     * Creates a new object.
     * 
     * @param arg the argument
     */
    public WriterTypeTest(String arg) {

        super(arg, "writerType", "{test-plain}");
        setConfig("extex-test");
    }

    /**
     * <testcase><tt>\writeType</tt> checks for an existing configuration.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUndef1() throws Exception {

        assertFailure(DEFINE_BRACES + " \\writerType={undef}\\end",
        // expect
            "The document writer `undef' is unknown.");
    }

    /**
     * <testcase><tt>\writeType</tt> needs a brace</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError0() throws Exception {

        assertFailure(DEFINE_BRACES + "\\writerType",
        // expect
            "Unexpected end of file while processing \\writerType");
    }

    /**
     * <testcase><tt>\writeType</tt> is not applicable after the document
     * writer has already shipped out a page.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertOutput(DEFINE_BRACES + "\\shipout\\vbox{a} \\writerType={dvi} ",
        // expect
            "The document writer is already in action.", null);
    }

    /**
     * <testcase><tt>\writeType</tt> is not applicable after the document
     * writer has already shipped out a page.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertOutput(DEFINE_BRACES + "\\def\\x{}\\writerType={d\\x vix} ",
        // expect
            "", null);
    }

    /**
     * <testcase><tt>\writeType</tt> is applicable to <tt>\showthe</tt>.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShowthe1() throws Exception {

        assertOutput("\\showthe\\writerType\\end",
        // expect
            out("test-plain"), "");
    }

    /**
     * <testcase><tt>\writeType</tt> is applicable to <tt>\showthe</tt>.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTokensConvertible1() throws Exception {

        assertOutput("\\toks0=\\writerType\\showthe\\toks0\\end",
        // expect
            out("test-plain"), "");
    }

}
