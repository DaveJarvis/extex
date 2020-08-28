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

package org.extex.test;

import org.extex.ExTeX;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.core.Locator;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.EditHandler;
import org.extex.interpreter.ErrorHandler;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.observer.expandMacro.ExpandMacroObservable;
import org.extex.interpreter.observer.expandMacro.ExpandMacroObserver;
import org.extex.interpreter.type.Code;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.token.Token;
import org.extex.test.font.LauncherFont;

import java.io.*;
import java.nio.charset.CharacterCodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import static org.junit.Assert.*;

/**
 * This base class for test cases handles all the nifty gritty details of
 * running an instance of {@link ExTeX ExTeX}.
 * <p>
 * This class provides some properties in addition to those provided by
 * {@link ExTeX ExTeX} or supporting classes. The following list defines them:
 * </p>
 * 
 * <dl>
 * <dt>extex.launcher.loglevel</dt>
 * <dd>This property sets the log level of the logger in effect. It takes
 * symbolic (String) names of the log levels: <tt>config</tt>, <tt>info</tt>,
 * <tt>warning</tt>, <tt>severe</tt>, <tt>fine</tt>, <tt>finer</tt>,
 * <tt>finest</tt></dd>
 * 
 * <dt>extex.launcher.verbose</dt>
 * <dd>This property is a boolean value which indicates that the code to be run
 * should be logged.</dd>
 * 
 * <dt>extex.launcher.trace</dt>
 * <dd>This property is a boolean value which indicates that the code executed
 * should be logged.</dd>
 * 
 * <dt>extex.launcher.time</dt>
 * <dd>This property is a boolean value which indicates that the time elapsed in
 * the execution of the test code should be logged.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class ExTeXLauncher {

    /**
     * Inner class for the error handler.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    @SuppressWarnings("RedundantThrows")
    private static class EHandler implements ErrorHandler {

        /**
         * The field <tt>logger</tt> contains the target logger.
         */
        private final Logger logger;

        /**
         * Creates a new object.
         * 
         * @param theLogger the target logger
         */
        public EHandler(Logger theLogger) {

            this.logger = theLogger;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.ErrorHandler#handleError(org.extex.core.exception.GeneralException,
         *      org.extex.scanner.type.token.Token,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.interpreter.context.Context)
         */
        @Override
        public boolean handleError(GeneralException e, Token token,
                TokenSource source, Context context) throws HelpingException {

            logger.log(Level.SEVERE, e.getLocalizedMessage());
            return false;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.ErrorHandler#setEditHandler(org.extex.interpreter.EditHandler)
         */
        @Override
        public void setEditHandler(EditHandler editHandler) {

            // not supported
        }
    }

    /**
     * The constant <tt>DEFINE_BRACES</tt> contains the definition of the usual
     * category codes for braces { and }.
     */
    public static final String DEFINE_BRACES = "\\catcode`\\{=1 "
            + "\\catcode`\\}=2\\relax ";

    /**
     * The constant <tt>DEFINE_CATCODES</tt> contains the definition of the
     * usual category codes for {, }, $, &, #, ^, _, and ^^10.
     */
    public static final String DEFINE_CATCODES = "\\catcode`\\{=1 "
            + "\\catcode`\\}=2 " + "\\catcode`\\$=3 " + "\\catcode`\\&=4 "
            + "\\catcode`\\#=6 " + "\\catcode`\\^=7 " + "\\catcode`\\_=8 "
            + "\\catcode`\\^^I=10 ";

    /**
     * The constant <tt>DEFINE_HASH</tt> contains the definition of the category
     * code for #.
     */
    public static final String DEFINE_HASH = "\\catcode`\\#=6 ";

    /**
     * The constant <tt>DEFINE_MATH</tt> contains the definition of the catcode
     * for math shift $.
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
    private static final Map<String, Level> LEVEL_MAP = new HashMap<>();

    /**
     * The field <tt>SEP</tt> contains the separator for properties.
     */
    private static final String SEP = System.getProperty("path.separator", ":");

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
        provide(properties, "extex.texinputs", //
            "." + SEP + "../texmf/src/texmf/test/texmf" + SEP + //
                    "../texmf/src/main/texmf" + SEP + //
                    "../texmf/src/texmf");
        provide(properties, "extex.fonts", //
            "." + SEP + "../texmf/src/texmf/test/texmf" + SEP + //
                    "../texmf/src/main/texmf" + SEP + //
                    "../texmf/src/texmf");
        provide(properties, "extex.nobanner", "true");
        provide(properties, "extex.typesetter", "devel");
    }

    /**
     * Set a property if it has not been set yet.
     * 
     * @param properties the properties to modify
     * @param name the name of the property
     * @param value the new value
     */
    private static void provide(Properties properties, String name, String value) {

        if (properties.getProperty(name) == null) {
            properties.setProperty(name, value);
        }
    }

    /**
     * The field <tt>config</tt> contains the name of the configuration to use.
     */
    private String config = "base-test.xml";

    /**
     * The field <tt>defaultLog</tt> contains the default log output unless
     * specified explicitly.
     */
    private String defaultLog = "";

    /**
     * The field <tt>err</tt> contains the error stream for reporting.
     */
    private final PrintStream err = System.err;

    /**
     * The field <tt>props</tt> contains the merged properties from the system
     * properties and the properties loaded from <tt>.extex-test</tt>.
     */
    private Properties props = null;

    /**
     * The field <tt>setHsize</tt> contains the indicator to use a wider hsize.
     */
    private boolean setHsize = true;

    /**
     * The field <tt>trace</tt> contains the indicator for tracing.
     */
    private boolean trace = false;


    public ExTeXLauncher() {

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
    public Interpreter assertFailure(Properties properties, String code,
            String log) throws Exception {

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
    public Interpreter assertFailure(String code, String log) throws Exception {

        return assertOutput(getProps(), code, log, "");
    }

    /**
     * Run some code through <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
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
    public Interpreter assertOutput(Properties properties, String code,
            String log, String expect) throws HelpingException {

        return assertOutput(properties,
            code, //
            (log == null ? null : new EqualityValidator("log stream", log)),
            (expect == null
                    ? null
                    : new EqualityValidator("out stream", expect)));
    }

    /**
     * Run some code through <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param properties the properties to start with
     * @param code the code to expand
     * @param logValidator the validator for the log stream or <code>null</code>
     * @param outputValidator the validator for the output stream or
     *        <code>null</code>
     * 
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     * 
     * @throws HelpingException in case of an error
     */
    public Interpreter assertOutput(Properties properties, String code,
            Validator logValidator, Validator outputValidator)
            throws HelpingException {

        boolean errorP = false;

        prepareProperties(properties);
        properties.setProperty("extex.code", code);
        properties.setProperty("extex.file", "");
        properties.setProperty("extex.nobanner", "true");
        properties.setProperty("extex.config", getConfig());

        if( Boolean.parseBoolean(
            properties.getProperty(
                "extex.launcher.trace", "false" ) ) ) {
            trace = true;
        }

        ExTeX extex = new ExTeX(properties) {

            /**
             * @see org.extex.ExTeX#makeInterpreter(org.extex.framework.configuration.Configuration,
             *      org.extex.backend.outputStream.OutputStreamFactory,
             *      org.extex.resource.ResourceFinder, java.lang.String)
             */
            @Override
            protected Interpreter makeInterpreter(Configuration config,
                    OutputStreamFactory outFatory, ResourceFinder finder,
                    String jobname)
                    throws GeneralException,
                        FontException,
                        IOException {

                Interpreter interpreter =
                        super.makeInterpreter(config, outFatory, finder,
                            jobname);
                Context context = interpreter.getContext();
                context.set(new LauncherFont(), true);
                context.setStandardTokenStream(interpreter
                    .getTokenStreamFactory().getStream(
                        new InputStreamReader(System.in)));
                context.set(context.getLanguage("0"), true);
                if (setHsize) {
                    context.setDimen("hsize", new Dimen(Dimen.ONE * 3000), //
                        true);
                }
                if (trace && interpreter instanceof ExpandMacroObservable) {
                    ((ExpandMacroObservable) interpreter)
                        .registerObserver(new ExpandMacroObserver() {

                            @Override
                            public void update(Token token, Code code,
                                    Locator locator) {

                                err.println(locator.toString(1) + "\t"
                                        + token.toText());
                            }
                        });
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

        if( Boolean.parseBoolean(
            properties.getProperty( "extex.launcher.verbose",
                                    "false" ) ) ) {
            Logger.getLogger(ExTeXLauncher.class.getName()).info(
                "Running:\n" + code + "\n");
        }

        init(extex);

        Interpreter interpreter = null;
        long t = System.currentTimeMillis();
        try {
            interpreter = extex.run();
        } catch (CharacterCodingException e) {
            fail("Character coding error: " + e.getLocalizedMessage());
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Configuration error: " + e.getLocalizedMessage());
        } catch (IOException e) {
            fail("I/O error: " + e.getLocalizedMessage());
        } catch (GeneralException e) {
            if (e.getCause() != null
                    && e.getCause() instanceof RuntimeException) {
                String message = e.getCause().toString();
                fail("Error: " + ("".equals(message) ? e.toString() : message));
            }
            errorP = true;
        } catch (Throwable e) {
            fail("Error: " + e.getLocalizedMessage());
        } finally {
            if( Boolean.parseBoolean(
                properties.getProperty( "extex.launcher.time",
                                        "false" ) ) ) {
                t = System.currentTimeMillis() - t;
                StringBuilder sb = new StringBuilder();
                sb.append(t % 1000);
                sb.append("ms");
                t = t / 1000;
                if (t > 0) {
                    sb.insert(0, " ");
                    sb.insert(0, t % 60);
                    t = t / 60;
                    if (t > 0) {
                        sb.insert(0, ":");
                        sb.insert(0, t);
                    }
                }
                sb.insert(0, "time = ");
                err.println(sb.toString());
            }
        }

        handler.close();
        logger.removeHandler(handler);
        String err = byteStream.toString();
        if (logValidator != null) {
            assertTrue("log", logValidator.validate(err));
        } else {
            assertFalse("No error expected", errorP);
        }
        if (outputValidator != null) {
            assertTrue("output stream", //
                outputValidator.validate(stream.toString()));
        }
        return interpreter;
    }

    /**
     * Run some code through <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
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
    public Interpreter assertOutput(String code, String log, String expect)
            throws Exception {

        return assertOutput(getProps(), code, log, expect);
    }

    /**
     * Run some code through <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     * 
     * @param properties the properties to modify
     * @param code the code to expand
     * @param expect the expected output on the output stream
     * 
     * @return a new instance of the <tt>Interpreter</tt> class which has been
     *         used for the test run. This object can be inspected in additional
     *         asserts.
     * 
     * @throws HelpingException in case of an error
     */
    public Interpreter assertSuccess(Properties properties, String code,
            String expect) throws HelpingException {

        return assertOutput(properties, code, defaultLog, expect);
    }

    /**
     * Run some code through <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
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
        final Level level = LEVEL_MAP.get( properties.getProperty(
            "extex.launcher.loglevel", "info" ) );

        return level == null ? Level.INFO : level;
    }

    /**
     * Getter for properties.
     * 
     * @return the properties
     */
    public Properties getProps() {

        if (props == null) {
            props = (Properties) System.getProperties().clone();

            File file = new File(".extex-test");
            if (file.canRead()) {
                try(final InputStream inputStream = new FileInputStream( file)) {
                    props.load(inputStream);
                } catch (IOException ignored) {
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
     * Format a log messages properly. This emulates the way <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> does it; i.e. prefix and postfix are appended and line
     * breaking is applied.
     * 
     * @param message the message to format
     * 
     * @return the formatted message according to the log channel rules
     */
    public String out(String message) {

        return "> " + message + ".\n";
    }

    /**
     * Run <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> on a file.
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
     * Run <logo>&epsilon;&chi;T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> on a file.
     * 
     * @param file the name of the file to read from
     * @param properties properties to start with
     * 
     * @return the contents of the log file
     * 
     * @throws Exception in case of an error
     */
    public String runFile(String file, Properties properties) throws Exception {

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
