/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.info;

import java.io.File;
import java.util.Properties;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\jobname</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class JobnameTest extends NoFlagsPrimitiveTester {

    /**
     * Constructor for JobnameTest.
     */
    public JobnameTest() {

        super("jobname", "");
    }

    /**
     * <testcase primitive="\jobname"> Test case checking that <tt>\jobname</tt>
     * delivers a decent default value. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testJobname1() throws Exception {

        assertSuccess(// --- input code ---
            "\\jobname" + "\\end ",
            // --- output channel ---
            "texput" + TERM);
    }

    /**
     * <testcase primitive="\jobname"> Test case checking that <tt>\jobname</tt>
     * can be set properly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testJobname2() throws Exception {

        Properties properties = (Properties) System.getProperties().clone();
        properties.setProperty("extex.jobname", "job");

        assertSuccess(properties,
        // --- input code ---
            "\\jobname" + "\\end ",
            // --- output channel ---
            "job" + TERM);
        new File("job.log").delete();
    }

}
