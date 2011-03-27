/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains the entry point to the site builder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBuilderTest {

    /**
     * Prepare velocity to be quiet.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        Logger.getLogger("org.apache.velocity").setLevel(Level.OFF);
    }

    /**
     * Adapt the logger and its handlers to to be quiet.
     * 
     * @param logger the logger
     */
    private void silenceLogger(Logger logger) {

        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        for (Handler h : logger.getHandlers()) {
            h.setLevel(Level.OFF);
        }
    }

    /**
     * <testcase> A non-existent library leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = Exception.class)
    public void test001() throws Exception {

        SiteBuilder siteBuilder = new SiteBuilder();
        silenceLogger(siteBuilder.getLogger());
        siteBuilder.lib("undefined.vm");
        siteBuilder.createSiteBase("src/test/resources/empty-site");
        siteBuilder.run();
    }

    /**
     * <testcase> A non-existent target directory which can not be created
     * because it is a file already leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = FileNotFoundException.class)
    public void test002() throws Exception {

        SiteBuilder siteBuilder = new SiteBuilder();
        silenceLogger(siteBuilder.getLogger());
        siteBuilder.createSiteBase("src/test/resources/empty-site");
        File targetdir = new File("target/undefined");
        new FileWriter(targetdir).close(); // touch
        siteBuilder.setTarget(targetdir);
        siteBuilder.createSiteBase(new File("src/test/resources/empty-site"));
        try {
            siteBuilder.run();
        } finally {
            assertTrue("deletion failed: " + targetdir.toString(),
                targetdir.delete());
        }
    }

    /**
     * <testcase> A non-existent resource directory leads to an exception.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    // @Test(expected = FileNotFoundException.class)
    public void test003() throws Exception {

        SiteBuilder siteBuilder = new SiteBuilder();
        silenceLogger(siteBuilder.getLogger());
        siteBuilder.addResourceDir(new File("target/u_n_d_e_f"));
        siteBuilder.run();
    }

    /**
     * <testcase> A site map can be created. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test010() throws Exception {

        SiteBuilder siteBuilder = new SiteBuilder();
        silenceLogger(siteBuilder.getLogger());
        siteBuilder.setTarget(new File("target/test-site"));
        siteBuilder.createSiteBase("src/test/resources/empty-site");
        File siteMap = new File("target/sitemap.html");
        siteBuilder.createSiteMap().setOutput(siteMap);
        if (siteMap.exists()) {
            assertTrue("deletion failed: " + siteMap.toString(),
                siteMap.delete());
        }
        try {
            siteBuilder.run();
            assertTrue("site map has not been created", siteMap.exists());
        } finally {
            siteMap.deleteOnExit();
        }
    }
}
