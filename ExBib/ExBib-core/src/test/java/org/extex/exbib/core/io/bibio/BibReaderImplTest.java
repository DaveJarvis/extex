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

import java.io.FileNotFoundException;
import java.io.StringReader;

import org.junit.Test;

/**
 * This is a test suite for {@link BibReaderImpl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibReaderImplTest extends BibReaderTester {

    /**
     * Creates a new object.
     */
    public BibReaderImplTest() {

        super(false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.bibio.BibReaderTester#makeTestInstance()
     */
    @Override
    protected BibReader makeTestInstance() throws FileNotFoundException {

        return new BibReaderImpl();
    }

    /**
     * <testcase> The empty input is accepted and does not require anything to
     * be stored in the database. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCommentExt1() throws Exception {

        BibReader r = makeTestInstance();
        r.open("test", new StringReader("abc @comment{@abc} def"));
        r.load(new TestDB() {

            /**
             * {@inheritDoc}
             * 
             * @see BibReaderImplTest.TestDB#storeComment( java.lang.String)
             */
            @Override
            public void storeComment(String comment) {

                assertEquals("abc @comment{@abc} def", comment);
            }

        });
    }

}
