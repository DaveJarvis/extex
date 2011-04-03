/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

/**
 * This is a test suite for {@link ParsingReader}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ParsingReaderTest {

    /**
     * <testcase> TODO </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test001() throws IOException {

        ParsingReader reader = new ParsingReader(new StringReader(""));
        assertFalse(reader.scanTo('$'));
    }

    /**
     * <testcase> TODO </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test002() throws IOException {

        ParsingReader reader = new ParsingReader(new StringReader(""));
        assertFalse(reader.scanTo('$', new StringBuilder()));
    }

    /**
     * <testcase> TODO </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test003() throws IOException {

        ParsingReader reader = new ParsingReader(new StringReader("<tag 123"));
        assertFalse(reader.scanToTag("tag"));
    }

    /**
     * <testcase> TODO </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test004() throws IOException {

        ParsingReader reader = new ParsingReader(new StringReader("<tag 123"));
        assertFalse(reader.scanToTag("tag", new StringBuilder()));
    }

    /**
     * <testcase> TODO </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test005() throws IOException {

        ParsingReader reader =
                new ParsingReader(new StringReader("abc<tag> 123"));
        StringBuilder buffer = new StringBuilder();
        assertTrue(reader.scanToTag("tag", buffer));
        assertEquals("abc", buffer.toString());
    }

    /**
     * <testcase> TODO </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test006() throws IOException {

        ParsingReader reader =
                new ParsingReader(new StringReader("a<b/>c<tag> 123"));
        StringBuilder buffer = new StringBuilder();
        assertTrue(reader.scanToTag("tag", buffer));
        assertEquals("a<b/>c", buffer.toString());
    }

}
