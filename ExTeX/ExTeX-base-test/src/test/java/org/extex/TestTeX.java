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

package org.extex;

import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.interpreter.*;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.Token;
import org.junit.Assert;

import java.io.*;
import java.util.Properties;

/**
 * Test for ExTeX.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
*/
public final class TestTeX {

    /**
     * The field {@code DATA_DIR} contains the location of the data directory.
     */
    private static final String DATA_DIR = "/src/test/resources/data/";

    /**
     * The field {@code ERROR_HANDLER} contains the error handler which always
     * fails.
     */
    private static final ErrorHandler ERROR_HANDLER = new ErrorHandler() {

        @Override
        public boolean handleError(GeneralException e, Token token,
                TokenSource source, Context context) {

            e.printStackTrace();
            Assert.fail("error in tex document");
            return false; // not reached
        }

        @Override
        public void setEditHandler(EditHandler editHandler) {

            // noop
        }
    };

    /**
     * Make an {@code Interpreter}.
     * 
     * @return an {@code Interpreter}
     */
    public static Interpreter makeInterpreter() {

        return makeInterpreter("tex.xml");
    }

    /**
     * Make an {@code Interpreter}.
     * 
     * @param configurationFile configuration file for ExTeX
     * @return an {@code Interpreter}
     */
    public static Interpreter makeInterpreter(String configurationFile) {

        Configuration config =
                ConfigurationFactory.newInstance("config/" + configurationFile);
        InterpreterFactory intf =
                new InterpreterFactory(config.getConfiguration("Interpreter"),
                    null);

        return intf.newInstance(null, null);
    }

    /**
     * Perform the test.
     * 
     * @param basename the name of the test
     * @param project the name of the project
     * 
     * @throws Exception in case of an error
     */
    public static void test(String basename, String project) throws Exception {

        test(basename, project, null);
    }

    /**
     * Perform the test.
     * 
     * @param basename the name of the test
     * @param project the name of the project
     * @param units the units to load
     * 
     * @throws Exception in case of an error
     */
    public static void test(String basename, String project, String units)
            throws Exception {

        testRun(basename, "../" + project + DATA_DIR + basename + ".testtxt",
            project, units);
    }

    /**
     * Run ExTeX with a special file and compare the output with a output test
     * file.
     * 
     * @param texfile the tex file
     * @param outfile the output test file
     * @param project the name of the project
     * @param units the additional units (colon separated)
     * 
     * @exception Exception iff an error occurs; iff the two files are not
     *            equals AssertionFailedError
     */
    public static void testRun(String texfile, String outfile, String project,
            String units) throws Exception {

        // run ExTeX
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Properties pro = (Properties) System.getProperties().clone();
        pro.setProperty("extex.output", "test-plain"); // gene
        // pro.setProperty("extex.output", "dump");
        pro.setProperty("extex.config", "tex.xml"); // gene
        pro.setProperty("extex.nobanner", "true"); // gene
        pro.setProperty("extex.texinputs",
            "../" + project + "/src/test/resources/data"); // gene
        pro.setProperty("extex.file", texfile);
        pro.setProperty("extex.jobname", texfile);
        if (units != null) {
            pro.setProperty("extex.units", units); // gene
        }
        // BATCHMODE
        // TODO: handle errors??? (TE)
        pro.setProperty("extex.interaction", "0");

        ExTeX extex = new ExTeX(pro, ".extex-test");
        extex.setErrorHandler(ERROR_HANDLER);
        extex.setOutStream(output);

        extex.run();
        extex.close();

        // compare

        BufferedReader intesttxt = new BufferedReader(new FileReader(outfile));
        Reader stringReader = new StringReader(output.toString());
        BufferedReader intxt = new BufferedReader(stringReader);

        try {
            String linetxt;
            String linetesttxt;
            while ((linetxt = intxt.readLine()) != null) {
                linetesttxt = intesttxt.readLine();
                Assert.assertNotNull(linetesttxt); // gene
                Assert.assertEquals(linetesttxt, linetxt);
            }

            linetesttxt = intesttxt.readLine();

            Assert.assertNotNull("Left-over: " + linetesttxt, linetesttxt);

        } finally { // gene: to assure that the resources are freed
            intxt.close();
            intesttxt.close();
            new File(texfile + ".log").deleteOnExit();
        }
    }

    /**
     * private: no instance
     */
    private TestTeX() {

        // noop
    }

}
