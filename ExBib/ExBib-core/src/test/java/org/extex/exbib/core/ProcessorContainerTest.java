/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for {@link ProcessorContainer}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ProcessorContainerTest {

    /**
     * <testcase> A non-number for min.crossref leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void test10() throws Exception {

        Configuration cfg = ConfigurationFactory.newInstance("exbib/exbib");
        Properties properties = new Properties();
        properties.setProperty(ExBib.PROP_MIN_CROSSREF, "xxx");
        new ProcessorContainer(cfg, null, properties);
    }

    /**
     * <testcase> <code>null</code> parameters lead to a
     * {@link NullPointerException}. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testError1() throws Exception {

        new ProcessorContainer(null, null, null);
    }

    /**
     * <testcase> A non-number for min.crossref leads to an error. </testcase>
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
     * <testcase> A non-number for min.crossref leads to an error. </testcase>
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
     * <testcase> An unknown local sorter leads to an error. </testcase>
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
     * <testcase> An unknown global sorter leads to an error. </testcase>
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
     * <testcase> An unknown processor is returned as <code>null</code>.
     * </testcase>
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
     * <testcase> An unknown processor is returned as <code>null</code>.
     * </testcase>
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
     * <testcase> A new instance is empty. </testcase>
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
