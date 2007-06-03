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

package org.extex.font.format.tfm;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.extex.core.UnicodeChar;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * Test for the u2t factory.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class U2tFactoryTest extends TestCase {

    /**
     * The resource finder.
     */
    private ResourceFinder finder;

    /**
     * Creates a new object.
     * 
     * @throws ConfigurationException from the configuration system.
     */
    public U2tFactoryTest() throws ConfigurationException {

        if (finder == null) {
            makeResourceFinder();
        }
    }

    /**
     * Make a resource finder.
     * 
     * @throws ConfigurationException from the configurations system.
     */
    private void makeResourceFinder() throws ConfigurationException {

        Configuration config =
                new ConfigurationFactory().newInstance("U2tFactoryTest.xml");

        Logger logger = Logger.getLogger("Test");
        finder =
                new ResourceFinderFactory().createResourceFinder(config
                    .getConfiguration("Resource"), logger, new Properties(),
                    null /* provider */);

    }

    /**
     * Test the u2t factory.
     */
    public void test01() {

        U2tFactory u2t = U2tFactory.getInstance();

        assertNotNull(u2t);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test02()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);

        Map<UnicodeChar, Integer> map = u2t.loadU2t(null, null);
        assertNull(map);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test03()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("xxx", null);
        assertNull(map);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test04()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("xxx_not_defined", finder);
        assertNull(map);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test05()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);

        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("textext", finder);
        assertNotNull(map);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test06()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("textext", finder);
        assertTrue(map.size() > 0);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test07()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("textext", finder);

        Object val = map.get(null);
        assertNull(val);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test08()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("textext", finder);

        Integer val = map.get(UnicodeChar.get(0xFFFF));
        assertNull(val);

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test09()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("textext", finder);

        Integer val = map.get(UnicodeChar.get(0x0041));
        assertNotNull(val);
        assertEquals(0x41, val.intValue());

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test10()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("textext", finder);

        Integer val = map.get(UnicodeChar.get(0x00b8));
        assertNotNull(val);
        assertEquals(0x18, val.intValue());

    }

    /**
     * Test the u2t factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void test11()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<UnicodeChar, Integer> map = u2t.loadU2t("textext", finder);

        Integer val = map.get(UnicodeChar.get(0x0392));
        assertNotNull(val);
        assertEquals(0x42, val.intValue());

    }

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u01()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);

        Map<Integer, UnicodeChar> map = u2t.loadT2u(null, null);
        assertNull(map);

    }

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u02()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);

        Map<Integer, UnicodeChar> map = u2t.loadT2u("xxx", null);
        assertNull(map);

    }

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u03()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<Integer, UnicodeChar> map = u2t.loadT2u("xxx_not_defined", finder);
        assertNull(map);

    }

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u04()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<Integer, UnicodeChar> map = u2t.loadT2u("textext", finder);
        assertNotNull(map);

    }

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u05()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<Integer, UnicodeChar> map = u2t.loadT2u("textext", finder);
        assertNotNull(map);

        Object val = map.get(null);
        assertNull(val);

    }

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u06()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<Integer, UnicodeChar> map = u2t.loadT2u("textext", finder);
        assertNotNull(map);

        UnicodeChar uc = map.get(new Integer(0xFFFF));
        assertNull(uc);

    }

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u07()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<Integer, UnicodeChar> map = u2t.loadT2u("textext", finder);
        assertNotNull(map);

        UnicodeChar uc = map.get(new Integer(0x41));
        assertNotNull(uc);

        assertEquals(0x41, uc.getCodePoint());

    }

    // ----------------------------------------------------

    /**
     * Test the t2u factory.
     * 
     * @throws ConfigurationException from the configuration system.
     * @throws IOException if a io error occurred.
     * @throws NumberFormatException if a parse error occurred.
     */
    public void testT2u08()
            throws ConfigurationException,
                NumberFormatException,
                IOException {

        U2tFactory u2t = U2tFactory.getInstance();
        assertNotNull(u2t);
        assertNotNull(finder);

        Map<Integer, UnicodeChar> map = u2t.loadT2u("textext", finder);
        assertNotNull(map);

        UnicodeChar uc = map.get(new Integer(0x48));
        assertNotNull(uc);

        assertEquals(0x48, uc.getCodePoint());

    }

}
