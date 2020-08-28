/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.csf;

import org.extex.exbib.core.exceptions.ExBibCsfNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for a CsfSorter.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CsfSorterTest {

    /**
     * <testcase> start() terminates immediately if the resource is null.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStart1() throws Exception {

        new CsfSorter(null).start();
    }

    /**
     * <testcase> start() terminates immediately if the resource is the empty
     * string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStart2() throws Exception {

        new CsfSorter("").start();
    }

    /**
     * <testcase> start() throws an exception if the resource can not be found.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibCsfNotFoundException.class)
    public void testStart3() throws Exception {

        CsfSorter csfSorter = new CsfSorter("abc");
        csfSorter.setResourceFinder(new ResourceFinder() {

            @Override
            public NamedInputStream findResource(String name, String type)
                    throws ConfigurationException {

                return null;
            }

            @Override
            public void enableTracing(boolean flag) {

            }
        });
        csfSorter.start();
    }

}
