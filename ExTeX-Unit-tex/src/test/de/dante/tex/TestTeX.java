/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package de.dante.tex;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

import junit.framework.Assert;

import org.extex.ExTeX;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.interpreter.ErrorHandler;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.InterpreterFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.main.errorHandler.editHandler.EditHandler;
import org.extex.scanner.type.token.Token;

/**
 * Test for ExTeX.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 * @version $Revision$
 */
public final class TestTeX {

    /**
     * TODO missing JavaDoc.
     *
     */
    private static class AssertFailErrorHandler implements ErrorHandler {

        /**
         * TODO missing JavaDoc
         *
         * @param e ...
         * @param token ...
         * @param source ...
         * @param context ...
         * @return ...
         *
         * @see org.extex.interpreter.ErrorHandler#handleError(
         *      org.extex.core.exception.GeneralException,
         *      org.extex.scanner.type.token.Token,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.interpreter.context.Context)
         */
        public boolean handleError(final GeneralException e, final Token token,
                final TokenSource source, final Context context) {

            Assert.fail("error in tex document");
            return false; // not reached
        }

        /**
         * TODO missing JavaDoc
         *
         * @param editHandler ...
         *
         * @see org.extex.interpreter.ErrorHandler#setEditHandler(
         *      org.extex.main.errorHandler.editHandler.EditHandler)
         */
        public void setEditHandler(final EditHandler editHandler) {

        }
    }

    /**
     * TODO missing JavaDoc.
     */
    private static ErrorHandler errorHandler = new AssertFailErrorHandler();

    /**
     * private: no instance
     */
    private TestTeX() {

    }

    /**
     * Run ExTeX with a special file and compare the output with a output test
     * file.
     *
     * @param texfile the tex file
     * @param outfile the output test file
     * @param project ...
     *
     * @exception Exception iff an error occurs; iff the two files are not
     *                equals AssertionFailedError
     */
    public static void testRun(final String texfile, final String outfile,
            final String project) throws Exception {

        // run ExTeX
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Properties pro = (Properties) System.getProperties().clone();
        ExTeX extex = new ExTeX(pro, ".extex-test");
        // pro.setProperty("extex.output", "dump");
        pro.setProperty("extex.output", "test-plain"); // gene
        pro.setProperty("extex.config", "tex.xml"); // gene
        pro.setProperty("extex.texinputs", //
                        "../" + project + "src/test/data"); // gene
        pro.setProperty("extex.file", texfile);
        pro.setProperty("extex.jobname", texfile);
        // BATCHMODE
        // TODO: handle errors??? (TE)
        pro.setProperty("extex.interaction", "0");

        extex.setErrorHandler(errorHandler);
        extex.setOutStream(output);

        extex.run();

        // compare

        BufferedReader intesttxt = new BufferedReader(new FileReader(outfile));
        Reader stringReader = new StringReader(output.toString());
        BufferedReader intxt = new BufferedReader(stringReader);

        try {
            String linetxt;
            String linetesttxt;
            while ((linetxt = intxt.readLine()) != null) {
                linetesttxt = intesttxt.readLine();

                Assert.assertEquals(linetesttxt, linetxt);
            }

            linetesttxt = intesttxt.readLine();

            Assert.assertNotNull("Left-over: " + linetesttxt, linetesttxt);

            new File(texfile + ".log").delete();

        } finally { // gene: to assure that the resources are freed
            intxt.close();
            intesttxt.close();
        }
    }

    /**
     * TODO missing JavaDoc
     *
     * @param basename ...
     *
     * @throws Exception ...
     */
    public static void test(final String basename) throws Exception {

        throw new RuntimeException("died");
        // testRun(basename, "develop/test/data/" + basename + ".testtxt");
    }

    /**
     * TODO missing JavaDoc.
     *
     * @param basename ...
     * @param project ...
     *
     * @throws Exception ...
     */
    public static void test(final String basename, final String project)
            throws Exception {

        testRun(
                basename, //
                "../" + project + "/src/test/data/" + basename + ".testtxt",
                project);
    }

    /**
     * Make an <code>Interpreter</code>.
     *
     * @param configurationFile configuration file for ExTeX
     * @return an <code>Interpreter</code>
     * @exception Exception if an error occurs
     */
    public static Interpreter makeInterpreter(final String configurationFile)
            throws Exception {

        Configuration config = new ConfigurationFactory()
                .newInstance("config/" + configurationFile);
        InterpreterFactory intf = new InterpreterFactory(config
                .getConfiguration("Interpreter"), null);

        return intf.newInstance(null, null);
    }

    /**
     * Make an <code>Interpreter</code>.
     *
     * @return an <code>Interpreter</code>
     * @exception Exception if an error occurs
     */
    public static Interpreter makeInterpreter() throws Exception {

        return makeInterpreter("tex.xml");
    }

}
