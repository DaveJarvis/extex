/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharacterCodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import junit.framework.TestCase;

import org.extex.ExTeX;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.ErrorHandler;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.logging.LogFormatter;
import org.extex.main.errorHandler.editHandler.EditHandler;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.token.Token;
import org.extex.test.font.LauncherFont;

/**
 * This base class for test cases handles all the nifty gritty details of
 * running an instance of <logo>ExTeX</logo>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExTeXLauncher extends TestCase {

    /**
     * Inner class for the error handler.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private static class EHandler implements ErrorHandler {

        /**
         * The field <tt>logger</tt> contains the target logger.
         */
        private Logger logger;

        /**
         * Creates a new object.
         *
         * @param theLogger the target logger
         */
        public EHandler(Logger theLogger) {

            super();
            this.logger = theLogger;
        }

        /**
         * This is the error handling callback.
         *
         * @param e the exception which has led to the invocation
         * @param token the token leading to the error
         * @param source the token source
         * @param context the processor context
         *
         * @return <code>true</code> iff the processing can continue
         *
         * @see org.extex.interpreter.ErrorHandler#handleError(
         *      org.extex.core.exception.GeneralException,
         *      org.extex.scanner.type.token.Token,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.interpreter.context.Context)
         */
        public boolean handleError(GeneralException e, Token token,
                TokenSource source, Context context) throws HelpingException {

            logger.log(Level.SEVERE, e.getLocalizedMessage());
            return false;
        }

        /**
         * Setter for the edit handler.
         *
         * @param editHandler the new edit handler
         *
         * @see org.extex.interpreter.ErrorHandler#setEditHandler(
         *      org.extex.main.errorHandler.editHandler.EditHandler)
         */
        public void setEditHandler(EditHandler editHandler) {

            // not supported
        }
    }

    /**
     * The constant <tt>DEFINE_BRACES</tt> contains the definition of the
     * usual category codes for braces { and }.
     */
    public static final String DEFINE_BRACES =
            "\\catcode`\\{=1 " + "\\catcode`\\}=2 ";

    /**
     * The constant <tt>DEFINE_CATCODES</tt> contains the definition of the
     * usual category codes for {, }, $, &, #, ^, _, and ^^10.
     */
    public static final String DEFINE_CATCODES =
            "\\catcode`\\{=1 " + "\\catcode`\\}=2 " + "\\catcode`\\$=3 "
                    + "\\catcode`\\&=4 " + "\\catcode`\\#=6 "
                    + "\\catcode`\\^=7 " + "\\catcode`\\_=8 "
                    + "\\catcode`\\^^I=10 ";

    /**
     * The constant <tt>DEFINE_HASH</tt> contains the definition of the
     * category code for #.
     */
    public static final String DEFINE_HASH = "\\catcode`\\#=6 ";

    /**
     * The constant <tt>DEFINE_MATH</tt> contains the definition of the
     * catcode for math shift $.
     */
    public static final String DEFINE_MATH = "\\catcode`\\$=3 ";

    /**
     * The constant <tt>DEFINE_TILDE</tt> contains the definition of the
     * category code for ~.
     */
    public static final String DEFINE_TILDE = "\\catcode`\\~=13 ";

    /**
     * The field <tt>levelMap</tt> contains the mapping for debug levels from
     * String representation to Level values.
     */
    private static final Map<String, Level> LEVEL_MAP =
            new HashMap<String, Level>();

    /**
     * The constant <tt>TERM</tt> contains the terminating string for output.
     */
    public static final String TERM = "\n\n";

    static {
        LEVEL_MAP.put("config", Level.CONFIG);
        LEVEL_MAP.put("info", Level.INFO);
        LEVEL_MAP.put("warning", Level.WARNING);
        LEVEL_MAP.put("severe", Level.SEVERE);
        LEVEL_MAP.put("fine", Level.FINE);
        LEVEL_MAP.put("finer", Level.FINER);
        LEVEL_MAP.put("finest", Level.FINEST);
    }

    /**
     * Set some properties to default values. The properties set are:
     * <dl>
     * <dt><tt>extex.output</tt></dt>
     * <dd>Preset to <tt>test-plain</tt></dd>
     * <dt><tt>extex.interaction</tt></dt>
     * <dd>Preset to <tt>batchmode</tt></dd>
     * <dt><tt>extex.fonts</tt></dt>
     * <dd>Preset to <tt>src/font</tt></dd>
     * </dl>
     *
     * @param properties the properties to adapt
     */
    private static void prepareProperties(Properties properties) {

        provide(properties, "extex.output", "test-plain");
        provide(properties, "extex.interaction", "batchmode");
        provide(properties, "extex.fonts", "../ExTeX-BaseFont/src/font");
        provide(properties, "extex.nobanner", "true");
    }

    /**
     * Set a property if it has not been set yet.
     *
     * @param properties the properties to modify
     * @param name the name of the property
     * @param value the new value
     */
    private static void provide(Properties properties, String name,
            String value) {

        if (properties.getProperty(name) == null) {
            properties.setProperty(name, value);
        }
    }

    /**
     * The field <tt>config</tt> contains the name of the configuration to
     * use.
     */
    private String config = "base-test.xml";

    /**
     * The field <tt>defaultLog</tt> contains the default log output unless
     * specified explicitly.
     */
    private String defaultLog = "";

    /**
     * The field <tt>props</tt> contains the merged properties from the system
     * properties and the properties loaded from <tt>.extex-test</tt>.
     */
    private Properties props = null;

    /**
     * The field <tt>setHsize</tt> contains the indicator to use a wider
     * hsize.
     */
    private boolean setHsize = true;

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public ExTeXLauncher(String arg) {

        super(arg);
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * Run some code through <tt>Interpreter</tt> which is expected to fail.
     *
     * @param properties the properties to modify
     * @param code the code to expand
     * @param log the expected output on the log stream
     *
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     *
     * @throws Exception in case of an error
     */
    public Interpreter assertFailure(Properties properties,
            String code, String log) throws Exception {

        return assertOutput(properties, code, log, "");
    }

    /**
     * Run some code through <tt>Interpreter</tt> which is expected to fail.
     *
     * @param code the code to expand
     * @param log the expected output on the log stream
     *
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     *
     * @throws Exception in case of an error
     */
    public Interpreter assertFailure(String code, String log)
            throws Exception {

        return assertOutput(getProps(), code, log, "");
    }

    /**
     * Run some code through <logo>ExTeX</logo>.
     *
     * @param properties the properties to start with
     * @param code the code to expand
     * @param log the expected output on the log stream
     * @param expect the expected output on the output stream
     *
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     *
     * @throws HelpingException in case of an error
     */
    public Interpreter assertOutput(Properties properties,
            String code, String log, String expect)
            throws HelpingException {

        boolean errorP = false;

        prepareProperties(properties);
        properties.setProperty("extex.code", code);
        properties.setProperty("extex.file", "");
        properties.setProperty("extex.nobanner", "true");
        properties.setProperty("extex.config", getConfig());

        ExTeX extex = new ExTeX(properties) {

            /**
             * @see org.extex.ExTeX#makeInterpreter(
             *      org.extex.framework.configuration.Configuration,
             *      org.extex.backend.outputStream.OutputStreamFactory,
             *      org.extex.resource.ResourceFinder, java.lang.String)
             */
            protected Interpreter makeInterpreter(Configuration config,
                    OutputStreamFactory outFatory,
                    ResourceFinder finder, String jobname)
                    throws GeneralException,
                        FontException,
                        IOException {

                Interpreter interpreter =
                        super.makeInterpreter(config, outFatory, finder,
                            jobname);
                Context context = interpreter.getContext();
                context.set(new LauncherFont(), true);
                context.setStandardTokenStream(interpreter
                    .getTokenStreamFactory().newInstance(
                        new InputStreamReader(System.in)));
                context.set(context.getLanguage("0"), true);
                if (setHsize) {
                    context.setDimen("hsize", new Dimen(Dimen.ONE * 3000), //
                        true);
                }
                return interpreter;
            }
        };

        Level level = getLogLevel(properties);
        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);
        logger.setLevel(level);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(byteStream, new LogFormatter());
        handler.setLevel(level);
        logger.addHandler(handler);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        extex.setOutStream(stream);
        extex.setErrorHandler(new EHandler(logger));
        extex.setLogger(logger);

        if (Boolean.valueOf(properties.getProperty("extex.launcher.verbose", //
            "false")).booleanValue()) {
            Logger.getLogger(ExTeXLauncher.class.getName()).info(
                "Running:\n" + code + "\n");
        }

        init(extex);

        Interpreter interpreter = null;
        try {
            interpreter = extex.run();
        } catch (CharacterCodingException e) {
            errorP = true;
        } catch (ConfigurationException e) {
            errorP = true;
            e.printStackTrace();
        } catch (IOException e) {
            errorP = true;
        } catch (GeneralException e) {
            errorP = true;
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }

        handler.close();
        logger.removeHandler(handler);
        if (log != null) {
            assertEquals(log, byteStream.toString());
        } else {
            assertFalse("No error expected", errorP);
        }
        if (expect != null) {
            assertEquals(expect, stream.toString());
        }
        return interpreter;
    }

    /**
     * Run some code through <logo>ExTeX</logo>.
     *
     * @param code the code to expand
     * @param log the expected output on the log stream
     * @param expect the expected output on the output stream
     *
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     *
     * @throws Exception in case of an error
     */
    public Interpreter assertOutput(String code, String log,
            String expect) throws Exception {

        return assertOutput(getProps(), code, log, expect);
    }

    /**
     * Run some code through <logo>ExTeX</logo>.
     *
     * @param properties the properties to modify
     * @param code the code to expand
     * @param expect the expected output on the output stream
     *
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     *
     * @throws Exception in case of an error
     */
    public Interpreter assertSuccess(Properties properties,
            String code, String expect) throws Exception {

        return assertOutput(properties, code, defaultLog, expect);
    }

    /**
     * Run some code through <logo>ExTeX</logo>.
     *
     * @param code the code to expand
     * @param expect the expected output on the output stream
     *
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     *
     * @throws Exception in case of an error
     */
    public Interpreter assertSuccess(String code, String expect)
            throws Exception {

        return assertOutput(getProps(), code, defaultLog, expect);
    }

    /**
     * Getter for config.
     *
     * @return the config
     */
    protected String getConfig() {

        return this.config;
    }

    /**
     * Getter for defaultLog.
     *
     * @return the defaultLog
     */
    protected String getDefaultLog() {

        return this.defaultLog;
    }

    /**
     * Determine the log level.
     *
     * @param properties the properties
     *
     * @return the log level
     */
    private Level getLogLevel(Properties properties) {

        Level level =
                LEVEL_MAP.get(properties.getProperty("extex.launcher.loglevel",
                    "info"));

        return level == null ? Level.INFO : level;
    }

    /**
     * Getter for properties.
     *
     * @return the properties
     */
    public Properties getProps() {

        if (props == null) {
            props = System.getProperties();

            File file = new File(".extex-test");
            if (file.canRead()) {
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    props.load(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    // ignored on purpose
                }
            }
        }
        return (Properties) this.props.clone();
    }

    /**
     * Initialize the ExTeX object just before the code is run.
     *
     * @param extex the ExTeX object
     */
    protected void init(ExTeX extex) {

        //
    }

    /**
     * Getter for setHsize.
     *
     * @return the setHsize
     */
    protected boolean isSetHsize() {

        return this.setHsize;
    }

    /**
     * Format a log messages properly. This emulates the way <logo>TeX</logo>
     * does it; i.e. prefix and postfix are appended and line breaking is
     * applied.
     *
     * @param message the message to format
     *
     * @return the formatted message according to the log channel rules
     */
    public String out(String message) {

        return "> " + message + ".\n";
    }

    /**
     * Run <logo>ExTeX</logo> on a file.
     *
     * @param file the name of the file to read from
     *
     * @return the contents of the log file
     *
     * @throws Exception in case of an error
     */
    public String runFile(String file) throws Exception {

        return runFile(file, System.getProperties());
    }

    /**
     * Run <logo>ExTeX</logo> on a file.
     *
     * @param file the name of the file to read from
     * @param properties properties to start with
     *
     * @return the contents of the log file
     *
     * @throws Exception in case of an error
     */
    public String runFile(String file, Properties properties)
            throws Exception {

        prepareProperties(properties);
        properties.setProperty("extex.code", "\\errorstopmode ");
        properties.setProperty("extex.file", file);
        properties.setProperty("extex.jobname", file);

        ExTeX extex = new ExTeX(properties);

        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(bytes, new LogFormatter());
        handler.setLevel(Level.WARNING);
        logger.addHandler(handler);
        extex.setLogger(logger);
        extex.setErrorHandler(new EHandler(logger));

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        extex.setOutStream(stream);

        extex.run();

        handler.close();
        logger.removeHandler(handler);
        return bytes.toString();
    }

    /**
     * Setter for configuration.
     *
     * @param config the configuration to set
     */
    public void setConfig(String config) {

        this.config = config;
    }

    /**
     * Setter for defaultLog.
     *
     * @param defaultLog the defaultLog to set
     */
    protected void setDefaultLog(String defaultLog) {

        this.defaultLog = defaultLog;
    }

    /**
     * Setter for setHsize.
     *
     * @param setHsize the setHsize to set
     */
    protected void setHsize(boolean setHsize) {

        this.setHsize = setHsize;
    }

    /**
     * Getter for properties.
     *
     * @return the properties
     */
    public Properties showNodesProperties() {

        Properties p = getProps();
        p.put("extex.output", "test-tree");
        return p;
    }

}
