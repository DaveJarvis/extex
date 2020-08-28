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

package org.extex.exindex.core.parser.xindy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for {@link XindyParserFactory}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class XindyParserFactoryTest {

    /**
     * The field <tt>EMPTY_RESOURCE_FINDER</tt> contains the resource finder
     * which returns an empty input stream.
     */
    private static final ResourceFinder EMPTY_RESOURCE_FINDER =
            new ResourceFinder() {

                public void enableTracing(boolean flag) {

                    
                }

                public NamedInputStream findResource(String name, String type)
                        throws ConfigurationException {

                    return new NamedInputStream(new ByteArrayInputStream(
                        new byte[]{}), "");
                }
            };

    /**
     * <testcase> If the resource can not be found then <code>null</code> is
     * returned by {@link XindyParserFactory#create(String, String, Indexer)}.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        XindyParserFactory factory = new XindyParserFactory();
        factory.setResourceFinder(new ResourceFinder() {

            public void enableTracing(boolean flag) {

                
            }

            public NamedInputStream findResource(String name, String type)
                    throws ConfigurationException {

                return null;
            }
        });
        assertNull(factory.create(null, null, null));
    }

    /**
     * <testcase> If the resource can be found then a {@link XindyParser} is
     * returned by {@link XindyParserFactory#create(String, String, Indexer)}
     * when the default encoding is requested with <code>null</code> as
     * encoding. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test2() throws Exception {

        XindyParserFactory factory = new XindyParserFactory();
        factory.setResourceFinder(EMPTY_RESOURCE_FINDER);
        RawIndexParser parser = factory.create(null, null, null);
        assertNotNull(parser);
        assertTrue(parser instanceof XindyParser);
    }

    /**
     * <testcase> If the resource can be found then a {@link XindyParser} is
     * returned by {@link XindyParserFactory#create(String, String, Indexer)}
     * when the default encoding is requested with <code>""</code> as
     * encoding. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test3() throws Exception {

        XindyParserFactory factory = new XindyParserFactory();
        factory.setResourceFinder(EMPTY_RESOURCE_FINDER);
        RawIndexParser parser = factory.create(null, "", null);
        assertNotNull(parser);
        assertTrue(parser instanceof XindyParser);
    }

    /**
     * <testcase> If the resource can be found then a {@link XindyParser} is
     * returned by {@link XindyParserFactory#create(String, String, Indexer)}
     * when the default encoding is requested with an existing encoding (utf-8).
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test4() throws Exception {

        XindyParserFactory factory = new XindyParserFactory();
        factory.setResourceFinder(EMPTY_RESOURCE_FINDER);
        RawIndexParser parser = factory.create(null, "utf-8", null);
        assertNotNull(parser);
        assertTrue(parser instanceof XindyParser);
    }

    /**
     * <testcase> If the resource finder is not set then
     * {@link XindyParserFactory#create(String, String, Indexer)} throws a
     * {@link NullPointerException}. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testError1() throws Exception {

        new XindyParserFactory().create(null, null, null);
    }

    /**
     * <testcase> If the resource can be found and the encoding is unknown (xxx)
     * then an {@link UnsupportedEncodingException} is thrown by
     * {@link XindyParserFactory#create(String, String, Indexer)}. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnsupportedEncodingException.class)
    public final void testError2() throws Exception {

        XindyParserFactory factory = new XindyParserFactory();
        factory.setResourceFinder(EMPTY_RESOURCE_FINDER);
        factory.create(null, "xxx", null);
    }

}
