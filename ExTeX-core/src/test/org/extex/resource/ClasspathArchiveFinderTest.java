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

package org.extex.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5417 $
 */
public class ClasspathArchiveFinderTest extends TestCase {

    /**
     * The constant <tt>CFG</tt> contains the configuration.
     */
    private static final Configuration CFG = new Configuration() {

        public Configuration findConfiguration(String key) {

            return null;
        }

        public Configuration findConfiguration(String key,
                String attribute) throws ConfigurationException {

            return null;
        }

        public String getAttribute(String name) {

            if ("default".equals(name)) {
                return "xxx";
            }

            return null;
        }

        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return this;
        }

        public Configuration getConfiguration(String key,
                String attribute) throws ConfigurationException {

            return this;
        }

        public String getValue() throws ConfigurationException {

            return null;
        }

        public String getValue(String key) throws ConfigurationException {

            return null;
        }

        public int getValueAsInteger(String key, int defaultValue)
                throws ConfigurationException {

            return defaultValue;
        }

        public List<String> getValues(String key) {

            if ("extension".equals(key)) {
                List<String> list = new ArrayList<String>();
                list.add("");
                list.add(".tex");
                return list;
            }
            return null;
        }

        public void getValues(List<String> list, String key) {

            // not needed
        }

        public Iterator iterator() throws ConfigurationException {

            return null;
        }

        public Iterator iterator(String key)
                throws ConfigurationException {

            return null;
        }

    };

    /**
     * Test that a null configuration leads to an exception.
     *
     * @throws Exception in case of an error
     */
    public final void test1() throws Exception {

        try {
            new ClasspathArchiveFinder(null);
            assertFalse(true);
        } catch (ConfigurationMissingException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link org.extex.resource.ClasspathArchiveFinder#findResource(java.lang.String, java.lang.String)}.
     *
     * @throws Exception in case of an error
     */
    public final void testFindResource0() throws Exception {

        ClasspathArchiveFinder finder = new ClasspathArchiveFinder(CFG);

        assertNull(finder.findResource("undef", "tex"));
    }

    /**
     * Test method for {@link org.extex.resource.ClasspathArchiveFinder#findResource(java.lang.String, java.lang.String)}.
     *
     * @throws Exception in case of an error
     */
    public final void testFindResource1() throws Exception {

        ClasspathArchiveFinder finder = new ClasspathArchiveFinder(CFG);

        assertNull(finder.findResource(null, null));
    }

    /**
     * Test method for {@link org.extex.resource.ClasspathArchiveFinder#findResource(java.lang.String, java.lang.String)}.
     *
     * @throws Exception in case of an error
     */
    public final void testFindResource2() throws Exception {

        ClasspathArchiveFinder finder = new ClasspathArchiveFinder(CFG);

        assertNotNull("a not found", finder.findResource("a", "tex"));
    }

    /**
     * Test method for {@link org.extex.resource.ClasspathArchiveFinder#findResource(java.lang.String, java.lang.String)}.
     *
     * @throws Exception in case of an error
     */
    public final void testFindResource3() throws Exception {

        ClasspathArchiveFinder finder = new ClasspathArchiveFinder(CFG);

        assertNotNull("a not found", finder.findResource("a.tex", ""));
    }

    /**
     * Test method for {@link org.extex.resource.ClasspathArchiveFinder#findResource(java.lang.String, java.lang.String)}.
     *
     * @throws Exception in case of an error
     */
    public final void testFindResource4() throws Exception {

        ClasspathArchiveFinder finder = new ClasspathArchiveFinder(CFG);

        assertNotNull("aa not found", finder.findResource("aa", "tex"));
    }

    /**
     * Test method for {@link org.extex.resource.ClasspathArchiveFinder#findResource(java.lang.String, java.lang.String)}.
     *
     * @throws Exception in case of an error
     */
    public final void testFindResource5() throws Exception {

        ClasspathArchiveFinder finder = new ClasspathArchiveFinder(CFG);

        assertNull("bb unexpectedly found", finder.findResource("bb", "tex"));
    }

}
