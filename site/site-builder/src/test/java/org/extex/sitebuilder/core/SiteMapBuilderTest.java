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

package org.extex.sitebuilder.core;

import java.io.File;

import org.junit.Test;

/**
 * This is a test suite for {@link SiteMapBuilder}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteMapBuilderTest {

    /**
     * <testcase>A <code>null</code> target leads to an error in
     * generate().</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTarget01() throws Exception {

        SiteMapBuilder siteMapBuilder = new SiteMapBuilder();
        siteMapBuilder.generate();
    }

    /**
     * <testcase>A <code>null</code> template leads to an error in
     * generate().</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTemplate01() throws Exception {

        SiteMapBuilder siteMapBuilder = new SiteMapBuilder();
        siteMapBuilder.setTarget(new File("target/sitemap.html"));
        siteMapBuilder.setTemplate(null);
        siteMapBuilder.generate();
    }
}
