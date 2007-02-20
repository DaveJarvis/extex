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

import org.extex.scanner.TokenStream;
import org.extex.util.framework.configuration.Configuration;

/**
 * Test cases for the string implementation of a token stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TokenStreamStreamImplTest extends TokenStreamStringImplTest {

    /**
     * Command line interface.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(TokenStreamStreamImplTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param name the name
     */
    public TokenStreamStreamImplTest(final String name) {

        super(name);
    }

    /**
     * The field <tt>CONF</tt> contains the configuration.
     */
    private static final Configuration CONF = new MyConfiguration(null);

    /**
     * Create a stream of tokens fed from a string.
     *
     * @param line the input string
     * @return the new token stream
     * @throws IOException in case of an error
     */
    protected TokenStream makeStream(final String line) throws IOException {

        return new TokenStreamImpl(CONF, null, new ByteArrayInputStream(line
            .getBytes()), "test", "ISO-8859-1");
    }
}
