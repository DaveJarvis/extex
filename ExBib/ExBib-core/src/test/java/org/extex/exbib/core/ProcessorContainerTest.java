/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.extex.exbib.core.db.sorter.SorterFactory;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.exceptions.ExBibSorterNotFoundException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.junit.Test;

/**
 * This is a test suite for {@link ProcessorContainer}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ProcessorContainerTest {

    /**
     *  A non-number for min.crossref leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingNumberException.class)
    public final void test10() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_MIN_CROSSREF, "xxx");
        new ProcessorContainer(cfg, null, properties);
    }

    /**
     * {@code null} parameters lead to a {@link NullPointerException}
* 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testError1() throws Exception {

        new ProcessorContainer(null, null, null);
    }

    /**
     *  A non-number for min.crossref leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingNumberException.class)
    public final void testError2() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_MIN_CROSSREF, "xxx");
        new ProcessorContainer(cfg, null, properties);
    }

    /**
     *  A non-number for min.crossref leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingNumberException.class)
    public final void testError3() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_MIN_CROSSREF + ".bbl", "xxx");
        new ProcessorContainer(cfg, null, properties).findProcessor(null);
    }

    /**
     *  An unknown local sorter leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibSorterNotFoundException.class)
    public final void testError4() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_SORT + ".bbl", "xxx");
        ProcessorContainer pc = new ProcessorContainer(cfg, null, properties);
        pc.setSorterFactory(new SorterFactory(cfg.findConfiguration("Sorter")));
        pc.findProcessor(null);
    }

    /**
     *  An unknown global sorter leads to an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibSorterNotFoundException.class)
    public final void testError5() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_SORT, "xxx");
        ProcessorContainer pc = new ProcessorContainer(cfg, null, properties);
        pc.setSorterFactory(new SorterFactory(cfg.findConfiguration("Sorter")));
        pc.findProcessor(null);
    }

    /**
     *  An unknown processor is returned as {@code null}.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetProcessor1() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        assertNull(new ProcessorContainer(cfg, null, new Properties())
            .getProcessor(null));
    }

    /**
     *  An unknown processor is returned as {@code null}.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetProcessor2() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        assertNull(new ProcessorContainer(cfg, null, new Properties())
            .getProcessor("abc"));
    }

    /**
     *  A new instance is empty. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testIsEmpty1() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        assertTrue(new ProcessorContainer(cfg, null, new Properties())
            .isEmpty());
    }

}
