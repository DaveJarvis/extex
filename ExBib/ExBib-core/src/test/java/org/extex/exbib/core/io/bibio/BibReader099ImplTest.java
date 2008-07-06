/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.io.Locator;
import org.junit.Test;

/**
 * This is a test suite for {@link BibReader099Impl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibReader099ImplTest extends BibReaderTester {

    /**
     * Creates a new object.
     */
    public BibReader099ImplTest() {

        super(true);
    }

    /**
     * Create a new instance of the BibReader to be tested.
     * 
     * @return the test instance
     */
    @Override
    protected BibReader makeTestInstance() {

        return new BibReader099Impl();
    }

    /**
     * <testcase> The {@literal @comment} is simply ignored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCommentExt1() throws Exception {

        BibReader r = makeTestInstance();
        r.open("test", new StringReader("abc @comment{@abc{x}} def"));
        r.load(new TestDB() {

            /**
             * The field <tt>state</tt> contains the state.
             */
            private int state = 0;

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.bibio.BibReaderTester.TestDB#makeEntry(
             *      java.lang.String, java.lang.String,
             *      org.extex.exbib.core.io.Locator)
             */
            @Override
            public Entry makeEntry(String type, String key, Locator locator) {

                switch (state++) {
                    case 1:
                        assertTrue(true);
                        break;
                    default:
                        assertTrue(false);
                }
                return null;
            }

            /**
             * {@inheritDoc}
             * 
             * @see BibReaderImplTest.TestDB#storeComment( java.lang.String)
             */
            @Override
            public void storeComment(String comment) {

                switch (state++) {
                    case 0:
                        assertEquals("abc @comment{", comment);
                        break;
                    case 2:
                        assertEquals("} def", comment);
                        break;
                    default:
                        assertTrue("unexpected comment: " + comment, false);
                }
            }

        });
    }

}
