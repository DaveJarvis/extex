/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.extex.framework.configuration.Configuration;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.exception.ScannerException;

/**
 * Test cases for the string implementation of a token stream.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TokenStreamStreamImplBufferedTest
        extends
            TokenStreamStringImplTest {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(TokenStreamStreamImplBufferedTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public TokenStreamStreamImplBufferedTest(String name) {

        super(name);
    }

    /**
     * The field <tt>conf</tt> contains the configuration.
     */
    private static final Configuration CONF = new MyConfiguration("16");

    /**
     * Create a stream of tokens fed from a string.
     * 
     * @param line the input string
     * @return the new token stream
     * @throws IOException in case of an error
     */
    @Override
    protected TokenStream makeStream(String line) throws IOException {

        return new TokenStreamImpl(CONF, null, new InputStreamReader(
            new ByteArrayInputStream(line.getBytes())), Boolean.FALSE, "test");
    }

    /**
     * <testcase> This test case validates that an IOException is remapped into
     * a Scanner Exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testException1() throws Exception {

        TokenStreamImpl stream =
                new TokenStreamImpl(new MyConfiguration("16"), null,
                    new InputStreamReader(new InputStream() {

                        @Override
                        public int read() throws IOException {

                            throw new IOException();
                        }
                    }), Boolean.FALSE, "test");
        try {
            stream.get(FACTORY, TOKENIZER);
            assertFalse(true);
        } catch (ScannerException e) {
            assertTrue(true);
        }
    }

}
