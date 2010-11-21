/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test suite for {@link MultiConfigurationIterator}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MultiConfigurationIteratorTest {

    /**
     * The field <tt>CFG1</tt> contains the first base configuration.
     */
    private static final Configuration CFG1 = new Configuration() {

        @Override
        public Configuration findConfiguration(String key)
                throws ConfigurationInvalidResourceException,
                    ConfigurationNotFoundException,
                    ConfigurationSyntaxException,
                    ConfigurationIOException {

            return "s".equals(key) ? this : null;
        }

        @Override
        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            return "s".equals(key) && "a".equals(attribute) ? this : null;
        }

        @Override
        public String getAttribute(String name) {

            return "a".equals(name) ? "A" : "X";
        }

        @Override
        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            Configuration c = findConfiguration(key);
            if (c == null) {
                throw new ConfigurationNotFoundException(key, null);
            }
            return c;
        }

        @Override
        public Configuration getConfiguration(String key, String attribute)
                throws ConfigurationException {

            Configuration c = findConfiguration(key, attribute);
            if (c == null) {
                throw new ConfigurationNotFoundException(key, attribute);
            }
            return c;
        }

        @Override
        public String getValue() throws ConfigurationException {

            return "val";
        }

        @Override
        public String getValue(String key) throws ConfigurationException {

            return "val";
        }

        @Override
        public int getValueAsInteger(String key, int defaultValue)
                throws ConfigurationException {

            return 42;
        }

        @Override
        public void getValues(List<String> list, String key) {

        }

        @Override
        public List<String> getValues(String key) {

            return new ArrayList<String>();
        }

        @Override
        public Iterator<Configuration> iterator() throws ConfigurationException {

            return new Iterator<Configuration>() {

                private int i = 0;

                @Override
                public boolean hasNext() {

                    return i < 2;
                }

                @Override
                public Configuration next() {

                    i++;
                    return CFG1;
                }

                @Override
                public void remove() {

                }
            };
        }

        @Override
        public Iterator<Configuration> iterator(String key)
                throws ConfigurationException {

            return !"s".equals(key) ? null : new Iterator<Configuration>() {

                private int i = 0;

                @Override
                public boolean hasNext() {

                    return i < 2;
                }

                @Override
                public Configuration next() {

                    i++;
                    return CFG1;
                }

                @Override
                public void remove() {

                }
            };
        }

        @Override
        public void setConfigurationLoader(ConfigurationLoader loader) {

        }
    };

    /**
     * The field <tt>mc1</tt> contains the multi-configuration with two
     * sub-configurations.
     */
    private MultiConfiguration mc2;

    /**
     * Create the test objects.
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        ArrayList<Configuration> a = new ArrayList<Configuration>();
        a.add(CFG1);
        a.add(CFG1);
        mc2 = new MultiConfiguration(a);
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.MultiConfigurationIterator#hasNext()}
     * .
     */
    @Test
    public void testHasNext() {

        assertTrue(mc2.iterator("s").hasNext());
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.MultiConfigurationIterator#next()}
     * .
     */
    @Test
    public void testNext() {

        int i = 0;
        Iterator<Configuration> it = mc2.iterator("s");
        while (it.hasNext()) {
            i++;
            it.next();
        }

        assertEquals(4, i);
    }

    /**
     * Test method for
     * {@link org.extex.framework.configuration.impl.MultiConfigurationIterator#remove()}
     * .
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {

        mc2.iterator().remove();
    }

}
