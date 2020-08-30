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

package org.extex.latexParser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.extex.latexParser.api.NodeList;
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.exception.ScannerException;
import org.junit.Test;

/**
 * This is a test suite for the LaTeX parser implementation.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LaTeXParserImplTest {

    /**
     * This class is a log handler which stores the log record in a buffer.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static final class MyHandler extends Handler {

        /**
         * The field {@code sb} contains the buffer.
         */
        private StringBuilder sb;

        /**
         * Creates a new object.
         * 
         * @param sb
         */
        public MyHandler(StringBuilder sb) {

            this.sb = sb;
        }

    @Override
        public void close() {


        }

    @Override
        public void flush() {


        }

    @Override
        public void publish(LogRecord record) {

            sb.append(record.getMessage());
            sb.append("\n");
        }
    }

    /**
     * Perform a test.
     * 
     * @param s the input
     * @param log the expected log output
     * 
     * @return the nodes constructed
     * 
     * @throws IOException
     * @throws ScannerException
     * @throws SyntaxError
     */
    private static NodeList makeTest(String s, String log)
            throws IOException,
                ScannerException,
                SyntaxError {

        StringBuilder sb = new StringBuilder();
        ResourceFinder finder = null;
        Logger logger = Logger.getLogger("xxx");
        logger.setUseParentHandlers(false);
        logger.addHandler(new MyHandler(sb));
        LaTeXParserImpl lp = new LaTeXParserImpl(finder, logger);
        NodeList nodes =
                lp.parse(new ByteArrayInputStream(s.getBytes()), "xyz");
        assertEquals(log, sb.toString());
        return nodes;
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        NodeList nodes = makeTest("", "");
        assertNotNull(nodes);
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test02() throws Exception {

        NodeList nodes =
                makeTest("\\abc ", "xyz:1: undefined control sequence \\abc\n");
        assertNotNull(nodes);
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test03() throws Exception {

        NodeList nodes =
                makeTest("\n\\abc ",
                    "xyz:2: undefined control sequence \\abc\n");
        assertNotNull(nodes);
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test04() throws Exception {

        NodeList nodes = makeTest("\n\\endinput ", "");
        assertNotNull(nodes);
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test05() throws Exception {

        NodeList nodes =
                makeTest("\n\\begin{abc} ",
                    "xyz:2: undefined environment abc\n");
        assertNotNull(nodes);
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEnd1() throws Exception {

        NodeList nodes =
                makeTest("\n\\end ", "xyz:2: unexpected end of file\n");
        assertNotNull(nodes);
        assertEquals("\n\n", nodes.toString());
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEnd2() throws Exception {

        NodeList nodes =
                makeTest("\n\\end{} ",
                    "xyz:2: environment name expected instead of {}\n");
        assertNotNull(nodes);
        assertEquals("\n\n", nodes.toString());
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEnd3() throws Exception {

        NodeList nodes =
                makeTest("\n\\end{\\relax} ",
                    "xyz:2: environment name expected instead of \\relax \n");
        assertNotNull(nodes);
        assertEquals("\n\n", nodes.toString());
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEnd4() throws Exception {

        NodeList nodes =
                makeTest("\n\\end{document} ",
                    "xyz:2: end of environemnt document has been found without begin\n");
        assertNotNull(nodes);
        assertEquals("\n\n", nodes.toString());
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEnv1() throws Exception {

        NodeList nodes =
                makeTest("\n\\begin{document} abc ",
                    "xyz:2: unexpected end of file in environment document\n");
        assertNotNull(nodes);
        assertEquals("\n\n\\begin{document} abc \\end{document}", nodes
            .toString());
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGroup1() throws Exception {

        NodeList nodes =
                makeTest("\n{ abc ",
                    "xyz:2: missing right brace for group started at xyz:2\n");
        assertNotNull(nodes);
        assertEquals("\n\n{ abc }", nodes.toString());
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = FileNotFoundException.class)
    public final void testLoad1() throws Exception {

        LaTeXParserImpl lp = new LaTeXParserImpl(null, null);
        lp.load("xyzzy");
    }

    /**
     * Test method for
     * {@link org.extex.latexParser.impl.EmptyLaTeXParser#parse(java.io.InputStream, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOk1() throws Exception {

        NodeList nodes =
                makeTest(
                    "\\documentclass{article}\\begin{document}\\end{document}",
                    "");
        assertNotNull(nodes);
        assertEquals(
            "\\documentclass{article}\\begin{document}\\end{document} ", nodes
                .toString());
    }

}
