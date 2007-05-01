/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;

/**
 * This is the test suite for the ConfigurationXMLImpl class.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5417 $
 */
public class ConfigurationXMLImplTest extends TestCase {

    /**
     * The field <tt>CONFIGURATION</tt> contains the base name of an existing
     * configuration resource.
     */
    private static final String CONFIGURATION =
            "org/extex/framework/configuration/impl/Configuration";

    /**
     * Create a configuration instance to test.
     * 
     * @return the configuration instance to test
     * 
     * @throws ConfigurationInvalidResourceException if the resource is invalid
     * @throws ConfigurationNotFoundException if the resource could not be found
     * @throws ConfigurationSyntaxException if the configuration contains a
     *         syntax error
     * @throws ConfigurationIOException if an I/O error has occurred during the
     *         reading of the resource
     */
    private Configuration init()
            throws ConfigurationInvalidResourceException,
                ConfigurationNotFoundException,
                ConfigurationSyntaxException,
                ConfigurationIOException {

        Configuration cfg = new ConfigurationXMLImpl(CONFIGURATION);
        assertNotNull(cfg);
        return cfg;
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#ConfigurationXMLImpl(java.io.InputStream,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testConfigurationXMLImplInputStreamString1()
            throws Exception {

        try {
            new ConfigurationXMLImpl(null, null);
            assertFalse(true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(e.getClass().getName(), true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#ConfigurationXMLImpl(java.lang.String)}.
     */
    public final void testConfigurationXMLImplString1() {

        try {
            new ConfigurationXMLImpl(null);
            assertFalse("Exception expected", true);
        } catch (ConfigurationInvalidResourceException e) {
            assertTrue("Exception expected", true);
        } catch (ConfigurationException e) {
            assertFalse("Exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#ConfigurationXMLImpl(java.lang.String)}.
     */
    public final void testConfigurationXMLImplString2() {

        try {
            new ConfigurationXMLImpl("");
            assertFalse("Exception expected", true);
        } catch (ConfigurationInvalidResourceException e) {
            assertTrue("Exception expected", true);
        } catch (ConfigurationException e) {
            assertFalse("Other exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#ConfigurationXMLImpl(java.lang.String)}.
     */
    public final void testConfigurationXMLImplString3() {

        try {
            new ConfigurationXMLImpl("undef");
            assertFalse("Exception expected", true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue("Exception expected", true);
        } catch (ConfigurationException e) {
            assertFalse("Other Exception expected", true);
        }
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.ConfigurationXMLImpl#ConfigurationXMLImpl(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testConfigurationXMLImplString4() throws Exception {

        ConfigurationXMLImpl cfg = new ConfigurationXMLImpl(CONFIGURATION);
        assertNotNull(cfg);
        assertEquals("document(\"" + CONFIGURATION + "\")/abc", cfg.toString());
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.ConfigurationXMLImpl#findConfiguration(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testFindConfigurationString0() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration(null);
        assertNull(cfg);
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.ConfigurationXMLImpl#findConfiguration(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testFindConfigurationString1() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration("undef");
        assertNull(cfg);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#findConfiguration(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testFindConfigurationString2() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration("f");
        assertNotNull(cfg);
        assertEquals("document(\"" + CONFIGURATION + "\")/abc/f", cfg
            .toString());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#findConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testFindConfigurationStringString1() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration(null, null);
        assertNull(cfg);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#findConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testFindConfigurationStringString2() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration("f", null);
        assertNull(cfg);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#findConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testFindConfigurationStringString3() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration(null, "");
        assertNull(cfg);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#findConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testFindConfigurationStringString4() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration("d", "45");
        assertNotNull(cfg);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getAttribute(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetAttribute1() throws Exception {

        Configuration cfg = init();
        assertNull(cfg.getAttribute(null));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getAttribute(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetAttribute2() throws Exception {

        Configuration cfg = init();
        assertNull(cfg.getAttribute(""));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getAttribute(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetAttribute3() throws Exception {

        Configuration cfg = init();
        cfg = cfg.findConfiguration("d");
        String name = cfg.getAttribute("name");
        assertNotNull(name);
        assertEquals("23", name);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationString1() throws Exception {

        try {
            init().getConfiguration(null);
            assertFalse("Exception expected", true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse("Other Exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationString2() throws Exception {

        try {
            init().getConfiguration("");
            assertFalse("Exception expected", true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse("Other Exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationString3() throws Exception {

        try {
            init().getConfiguration("undef");
            assertFalse("Exception expected", true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse("Other Exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationString4() throws Exception {

        Configuration cfg = init();
        assertNotNull(cfg.getConfiguration("d"));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationStringString1() throws Exception {

        try {
            init().getConfiguration(null, null);
            assertFalse("Exception expected", true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        } catch (ConfigurationException e) {
            assertFalse("Other Exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationStringString2() throws Exception {

        try {
            init().getConfiguration("d", null);
            assertFalse("Exception expected", true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        } catch (ConfigurationException e) {
            assertFalse("Other Exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationStringString3() throws Exception {

        try {
            init().getConfiguration(null, "23");
            assertFalse("Exception expected", true);
        } catch (ConfigurationNotFoundException e) {
            assertTrue(true);
        } catch (ConfigurationException e) {
            assertFalse("Other Exception expected", true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getConfiguration(java.lang.String,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetConfigurationStringString4() throws Exception {

        Configuration cfg = init();
        assertNotNull(cfg.getConfiguration("d", "23"));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValue()}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValue1() throws Exception {

        Configuration cfg = init();
        assertEquals("", cfg.getConfiguration("e").getValue());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValue()}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValue2() throws Exception {

        Configuration cfg = init();
        assertEquals("AA", cfg.getConfiguration("d").getValue());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValue()}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValue3() throws Exception {

        Configuration cfg = init();
        assertEquals("\n  \n  CC\n ", cfg.getConfiguration("f").getValue());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValue(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueString0() throws Exception {

        Configuration cfg = init();
        assertEquals("", cfg.getValue(null));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValue(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueString1() throws Exception {

        Configuration cfg = init();
        assertEquals("", cfg.getValue("undef"));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValue(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueString2() throws Exception {

        Configuration cfg = init();
        assertEquals("", cfg.getValue("e"));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValue(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueString3() throws Exception {

        Configuration cfg = init();
        assertEquals("AA", cfg.getValue("d"));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValueAsInteger(java.lang.String,
     * int)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueAsInteger() throws Exception {

        Configuration cfg = init();
        assertEquals(333, cfg.getValueAsInteger(null, 333));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValueAsInteger(java.lang.String,
     * int)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueAsInteger0() throws Exception {

        Configuration cfg = init();
        assertEquals(333, cfg.getValueAsInteger("undef", 333));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValueAsInteger(java.lang.String,
     * int)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueAsInteger1() throws Exception {

        Configuration cfg = init();
        assertEquals(333, cfg.getValueAsInteger(null, 333));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValueAsInteger(java.lang.String,
     * int)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueAsInteger2() throws Exception {

        Configuration cfg = init();
        assertEquals(0, cfg.getValueAsInteger("n", 333));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValueAsInteger(java.lang.String,
     * int)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueAsInteger3() throws Exception {

        Configuration cfg = init();
        assertEquals(1, cfg.getValueAsInteger("m", 333));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValueAsInteger(java.lang.String,
     * int)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueAsInteger4() throws Exception {

        Configuration cfg = init();
        assertEquals(-1, cfg.getValueAsInteger("o", 333));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValueAsInteger(java.lang.String,
     * int)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValueAsInteger5() throws Exception {

        Configuration cfg = init();
        assertEquals(42, cfg.getValueAsInteger("p", 333));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesString1() throws Exception {

        Configuration cfg = init();
        List<String> values = cfg.getValues(null);
        assertNotNull(values);
        assertEquals(0, values.size());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesString2() throws Exception {

        Configuration cfg = init();
        List<String> values = cfg.getValues("undef");
        assertNotNull(values);
        assertEquals(0, values.size());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesString3() throws Exception {

        Configuration cfg = init();
        List<String> values = cfg.getValues("e");
        assertNotNull(values);
        assertEquals(1, values.size());
        assertEquals("", values.get(0));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesString4() throws Exception {

        Configuration cfg = init();
        List<String> values = cfg.getValues("d");
        assertNotNull(values);
        assertEquals(2, values.size());
        assertEquals("AA", values.get(0));
        assertEquals("BB", values.get(1));
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(List,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesStringListString0() throws Exception {

        Configuration cfg = init();
        try {
            cfg.getValues(null, null);
            assertFalse("Exception expected", true);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(List,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesStringListString1() throws Exception {

        Configuration cfg = init();
        List<String> sl = new ArrayList<String>();
        cfg.getValues(sl, null);
        assertEquals(0, sl.size());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(List,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesStringListString2() throws Exception {

        Configuration cfg = init();
        List<String> sl = new ArrayList<String>();
        cfg.getValues(sl, "e");
        assertEquals(1, sl.size());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#getValues(List,
     * java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testGetValuesStringListString3() throws Exception {

        Configuration cfg = init();
        List<String> sl = new ArrayList<String>();
        cfg.getValues(sl, "d");
        assertEquals(2, sl.size());
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#iterator()}.
     * 
     * @throws Exception in case of an error
     */
    public final void testIterator1() throws Exception {

        Configuration cfg = init();
        Iterator<Configuration> iterator = cfg.iterator();
        assertNotNull(iterator);
        int n = 0;
        while (iterator.hasNext()) {
            n++;
            iterator.next();
        }
        assertEquals(8, n);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#iterator(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testIteratorString1() throws Exception {

        Configuration cfg = init();
        Iterator<Configuration> iterator = cfg.iterator(null);
        assertNotNull(iterator);
        int n = 0;
        while (iterator.hasNext()) {
            n++;
            iterator.next();
        }
        assertEquals(0, n);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#iterator(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testIteratorString2() throws Exception {

        Configuration cfg = init();
        Iterator<Configuration> iterator = cfg.iterator("undef");
        assertNotNull(iterator);
        int n = 0;
        while (iterator.hasNext()) {
            n++;
            iterator.next();
        }
        assertEquals(0, n);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#iterator(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testIteratorString3() throws Exception {

        Configuration cfg = init();
        Iterator<Configuration> iterator = cfg.iterator("e");
        assertNotNull(iterator);
        int n = 0;
        while (iterator.hasNext()) {
            n++;
            iterator.next();
        }
        assertEquals(1, n);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#iterator(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    public final void testIteratorString4() throws Exception {

        Configuration cfg = init();
        Iterator<Configuration> iterator = cfg.iterator("d");
        assertNotNull(iterator);
        int n = 0;
        while (iterator.hasNext()) {
            n++;
            iterator.next();
        }
        assertEquals(2, n);
    }

    /**
     * Test method for {@link
     * org.extex.framework.configuration.impl.ConfigurationXMLImpl#toString()}.
     * 
     * @throws Exception in case of an error
     */
    public final void testToString() throws Exception {

        ConfigurationXMLImpl cfg =
                new ConfigurationXMLImpl(CONFIGURATION + ".xml");
        assertEquals("document(\"" + CONFIGURATION + ".xml\")/abc", cfg
            .toString());
    }

}
