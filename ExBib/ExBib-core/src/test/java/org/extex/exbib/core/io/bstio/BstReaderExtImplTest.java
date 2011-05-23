/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bstio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.exception.ExBibNoNumberException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TIntegerOption;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TStringOption;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibUnexpectedEofException;
import org.extex.exbib.core.exceptions.ExBibUnexpectedException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for {@link BstReaderExtImpl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BstReaderExtImplTest {

    /**
     * A resource finder for the tests.
     */
    private static final class RF implements ResourceFinder {

        /**
         * The field <tt>content</tt> contains the content.
         */
        private String content;

        /**
         * Creates a new object.
         * 
         * @param content the content
         */
        public RF(String content) {

            this.content = content;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
         */
        public void enableTracing(boolean flag) {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
         *      java.lang.String)
         */
        public NamedInputStream findResource(String name, String type)
                throws ConfigurationException {

            return new NamedInputStream(new ByteArrayInputStream(content
                .getBytes()), "test");
        }
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedEofException.class)
    public final void testOption01() throws Exception {

        BstProcessor processor = new BstInterpreter099c();
        processor.setDb(new DBImpl());
        processor.addBibliographyStyle("test");
        BstReader r = new BstReaderExtImpl();
        r.setResourceFinder(new RF("option "));
        r.parse(processor);
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedException.class)
    public final void testOption02() throws Exception {

        BstProcessor processor = new BstInterpreter099c();
        processor.setDb(new DBImpl());
        processor.addBibliographyStyle("test");
        BstReader r = new BstReaderExtImpl();
        r.setResourceFinder(new RF("option abc"));
        r.parse(processor);
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibUnexpectedException.class)
    public final void testOption03() throws Exception {

        BstProcessor processor = new BstInterpreter099c();
        processor.setDb(new DBImpl());
        processor.addBibliographyStyle("test");
        BstReader r = new BstReaderExtImpl();
        r.setResourceFinder(new RF("option 123"));
        r.parse(processor);
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibNoNumberException.class)
    public final void testOptionInteger1() throws Exception {

        BstProcessor processor = new BstInterpreter099c();
        processor.setDb(new DBImpl());
        processor.addBibliographyStyle("test");
        BstReader r = new BstReaderExtImpl();
        r.setResourceFinder(new RF("option integer {abc}{def}"));
        r.parse(processor);
        // runTest(processor, "");
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptionInteger2() throws Exception {

        BstProcessor processor = new BstInterpreter099c();
        processor.setDb(new DBImpl());
        processor.addBibliographyStyle("test");
        BstReader r = new BstReaderExtImpl();
        r.setResourceFinder(new RF("option integer {abc}{#123}"));
        r.parse(processor);
        Code f = processor.getFunction("abc");
        assertNotNull(f);
        assertTrue(f instanceof TIntegerOption);
        assertEquals("abc", ((TIntegerOption) f).getValue());
        Token op = processor.getOption("abc");
        assertTrue(op.getClass().getName(), op instanceof TInteger);
        assertEquals("123", ((TInteger) op).getValue());
        // runTest(processor, "");
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.io.bstio.BstPrinterImpl#print(org.extex.exbib.core.bst.BstProcessor)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOptionString1() throws Exception {

        BstProcessor processor = new BstInterpreter099c();
        processor.setDb(new DBImpl());
        processor.addBibliographyStyle("test");
        BstReader r = new BstReaderExtImpl();
        r.setResourceFinder(new RF("option string {abc}{def}"));
        r.parse(processor);
        Code f = processor.getFunction("abc");
        assertNotNull(f);
        assertTrue(f.getClass().getName(), f instanceof TStringOption);
        assertEquals("abc", ((TStringOption) f).getValue());
        Token op = processor.getOption("abc");
        assertTrue(op.getClass().getName(), op instanceof TString);
        assertEquals("def", ((TString) op).getValue());
    }

}
