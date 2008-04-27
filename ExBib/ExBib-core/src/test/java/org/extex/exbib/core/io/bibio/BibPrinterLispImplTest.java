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

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.io.StringBufferWriter;
import org.junit.Test;

/**
 * This is a test suite for {@link BibPrinterImpl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibPrinterLispImplTest {

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bibio.BibPrinterImpl#print(org.extex.exbib.core.db.DB)}.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testPrint1() throws Exception {

        DB db = BiblioTester.loadBib("src/test/resources/bibtex/base/xampl");

        StringBuffer buffer = new StringBuffer();
        new BibPrinterLispImpl(new StringBufferWriter(buffer)).print(db);

        // assertEquals("", buffer.toString());
    }

}
