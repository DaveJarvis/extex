/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db.sorter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.exbib.core.exceptions.ExBibSorterNotFoundException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.junit.Test;

/**
 * This is a test suite for the sorter factory.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SorterFactoryTest {

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test00() throws Exception {

        assertNull(new SorterFactory(null).newInstance(null));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        assertNull(new SorterFactory(null).newInstance(""));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibSorterNotFoundException.class)
    public final void test02() throws Exception {

        Configuration config =
                ConfigurationFactory.newInstance("config/exbib/exbib.xml")
                    .getConfiguration("Sorter");
        assertNotNull(new SorterFactory(config).newInstance("xyzzy:"));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test03() throws Exception {

        Configuration config =
                ConfigurationFactory.newInstance("config/exbib/exbib.xml")
                    .getConfiguration("Sorter");
        assertNotNull(new SorterFactory(config).newInstance("csf"));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test04() throws Exception {

        Configuration config =
                ConfigurationFactory.newInstance("config/exbib/exbib.xml")
                    .getConfiguration("Sorter");
        assertNotNull(new SorterFactory(config).newInstance("csf:"));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReverse0() throws Exception {

        Configuration config =
                ConfigurationFactory.newInstance("config/exbib/exbib.xml")
                    .getConfiguration("Sorter");
        assertNull(new SorterFactory(config).newInstance("reverse"));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReverse1() throws Exception {

        Configuration config =
                ConfigurationFactory.newInstance("config/exbib/exbib.xml")
                    .getConfiguration("Sorter");
        assertNull(new SorterFactory(config).newInstance("reverse:"));
    }

    /**
     * Test method for
     * {@link org.extex.exbib.core.db.sorter.SorterFactory#newInstance(java.lang.String)}
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReverse2() throws Exception {

        Configuration config =
                ConfigurationFactory.newInstance("config/exbib/exbib.xml")
                    .getConfiguration("Sorter");
        Sorter sorter = new SorterFactory(config).newInstance("reverse:csf");
        assertNotNull(sorter);
        assertTrue(sorter instanceof Reverser);
    }

}
