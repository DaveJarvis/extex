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

package org.extex.framework.configuration.impl;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6459 $
 */
public class PropertiesConfigurationTest extends TestCase {

    /**
     * The field <tt>B0</tt> contains the empty properties file contents.
     */
    private static final byte[] B0 = "".getBytes();

    /**
     * The field <tt>B1</tt> contains a properties file contents.
     */
    private static final byte[] B1 = ("b=123\n" + "x.a=456").getBytes();

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#findConfiguration(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFindConfigurationString1() throws Exception {

        PropertiesConfiguration cfg =
                new PropertiesConfiguration(new ByteArrayInputStream(B1), "xxx");
        assertNull(cfg.findConfiguration("xxx"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#findConfiguration(java.lang.String, java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFindConfigurationStringString1() throws Exception {

        PropertiesConfiguration cfg =
                new PropertiesConfiguration(new ByteArrayInputStream(B1), "xxx");
        assertNull(cfg.findConfiguration("xxx", "a"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getAttribute(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetAttribute1() throws Exception {

        PropertiesConfiguration cfg =
                new PropertiesConfiguration(new ByteArrayInputStream(B1), "xxx");
        assertNull(cfg.getAttribute("a"));
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getAttribute(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetAttribute2() throws Exception {

        PropertiesConfiguration cfg =
                new PropertiesConfiguration(new ByteArrayInputStream(B1), "xxx");
        String attribute = cfg.getAttribute("b");
        assertNotNull(attribute);
        assertEquals("123", attribute);
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getConfiguration(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetConfigurationString1() throws Exception {

        PropertiesConfiguration cfg =
                new PropertiesConfiguration(new ByteArrayInputStream(B1), "xxx");
        try {
            cfg.getConfiguration("xxx");
            fail("exception expected");
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getConfiguration(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testGetConfigurationStringString() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getValue()}
     * .
     */
    @Test
    @Ignore
    public final void testGetValue() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getValueAsInteger(java.lang.String, int)}
     * .
     */
    @Test
    @Ignore
    public final void testGetValueAsInteger() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getValues(java.util.List, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testGetValuesListOfStringString() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getValues(java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testGetValuesString() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#getValue(java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testGetValueString() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#iterator()}
     * .
     */
    @Test
    @Ignore
    public final void testIterator() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#iterator(java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testIteratorString() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#PropertiesConfiguration()}
     * .
     */
    @Test
    public final void testPropertiesConfiguration() {

        PropertiesConfiguration cfg = new PropertiesConfiguration();
        assertEquals("", cfg.toString());
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#PropertiesConfiguration(java.io.InputStream, java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testPropertiesConfigurationInputStreamString()
            throws Exception {

        PropertiesConfiguration cfg =
                new PropertiesConfiguration(new ByteArrayInputStream(B0), "xxx");
        assertEquals("document(\"xxx\")", cfg.toString());
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.PropertiesConfiguration#setConfigurationLoader(org.extex.framework.configuration.ConfigurationLoader)}
     * .
     */
    @Test
    @Ignore
    public final void testSetConfigurationLoader() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link java.lang.Object#toString()}.
     */
    @Test
    @Ignore
    public final void testToString() {

        fail("Not yet implemented"); // TODO
    }

}
