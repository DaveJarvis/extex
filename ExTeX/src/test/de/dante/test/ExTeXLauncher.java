/*
 * Copyright (C) 2004 Gerd Neugebauer
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

package de.dante.test;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import junit.framework.TestCase;
import de.dante.extex.ExTeX;
import de.dante.extex.logging.LogFormatter;
import de.dante.extex.main.exception.MainException;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExTeXLauncher extends TestCase {

    /**
     * ...
     *
     * @return ...
     */
    private static void prepareProperties(final Properties properties) {

        properties.setProperty("extex.output", "text");
        properties.setProperty("extex.interaction", "batchmode");
        properties.setProperty("extex.fonts", "src/font");
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public ExTeXLauncher(final String arg) {

        super(arg);
    }

    /**
     * Run  some code through ExTeX.
     *
     * @param properties ...
     * @param code the code to expand
     * @param log the expected output on the log stream
     * @param expect the expected output on the output stream
     *
     * @throws MainException in case of an error
     */
    public void runCode(final Properties properties, final String code,
            final String log, final String expect) throws MainException {

        prepareProperties(properties);
        properties.setProperty("extex.code", code);
        properties.setProperty("extex.file", "");

        ExTeX main = new ExTeX(properties);

        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(bytes, new LogFormatter());
        logger.addHandler(handler);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        main.setOutStream(stream);

        main.run();

        handler.close();
        logger.removeHandler(handler);
        if (log != null) {
            assertEquals(log, bytes.toString());
        }
        if (expect != null) {
            assertEquals(expect, stream.toString());
        }
    }

    /**
     * Run some code through ExTeX.
     *
     * @param code the code to expand
     * @param log the expected output on the log stream
     * @param expect the expected output on the output stream
     *
     * @throws Exception in case of an error
     */
    public void runCode(final String code, final String log, final String expect)
            throws Exception {

        runCode(System.getProperties(), code, log, expect);
    }

    /**
     * Run ExTeX on a file.
     *
     * @param file the name of the file to read from
     *
     * @return the contents of the log file
     *
     * @throws Exception in case of an error
     */
    public String runFile(final String file) throws Exception {

        Properties properties = System.getProperties();
        prepareProperties(properties);
        properties.setProperty("extex.code", "");
        properties.setProperty("extex.file", file);
        properties.setProperty("extex.jobname", file);

        ExTeX main = new ExTeX(properties);

        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(bytes, new LogFormatter());
        logger.addHandler(handler);
        main.setLogger(logger);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        main.setOutStream(stream);

        main.run();

        handler.close();
        logger.removeHandler(handler);
        return bytes.toString();
    }

}