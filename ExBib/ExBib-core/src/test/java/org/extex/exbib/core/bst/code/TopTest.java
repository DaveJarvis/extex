/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.code;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.Top;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.StringBufferWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>top$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TopTest {

    /**
     * The field <tt>out</tt> contains the output buffer.
     */
    private final StringBuffer out = new StringBuffer();

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private BstProcessor p = null;

    /**
     * The field <tt>err</tt> contains the error buffer.
     */
    private Handler err;

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        err = new StoringHandler();
        logger.addHandler(err);
        p = new BstInterpreter099c(new DBImpl(),
            new StringBufferWriter(out), logger);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
    }

    /**
     * <testcase> top$ complains about an empty stack. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Top("top$").execute(p, null, null);
    }

    /**
     * <testcase> top$ can pop a number. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTopInteger() throws Exception {

        p.push(new TInteger(1234, null));
        new Top("top$").execute(p, null, null);
        assertEquals("#1234\n", err.toString());
        assertEquals("", out.toString());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> top$ can pop a string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTopString() throws Exception {

        p.push(new TString("abc", null));
        new Top("top$").execute(p, null, null);
        assertEquals("\"abc\"\n", err.toString());
        assertEquals("", out.toString());
        assertNull(p.popUnchecked());
    }

}
