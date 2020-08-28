/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.backend.BackendDriver;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.base.parser.ConstantDimenParser;
import org.extex.color.ColorConverter;
import org.extex.color.ColorConverterFacory;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.NotObservableException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.engine.*;
import org.extex.engine.backend.BackendFactory;
import org.extex.engine.backend.OutputFactory;
import org.extex.engine.exception.RegistrarFontNotFoundException;
import org.extex.engine.typesetter.TypesetterFactory;
import org.extex.font.CoreFontFactory;
import org.extex.font.exception.FontException;
import org.extex.framework.Registrar;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.*;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.ErrorHandler;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.InterpreterFactory;
import org.extex.interpreter.LoadUnit;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.ContextFactory;
import org.extex.interpreter.context.observer.interaction.InteractionObservable;
import org.extex.interpreter.context.observer.interaction.InteractionObserver;
import org.extex.interpreter.exception.InteractionUnknownException;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.loader.LoaderException;
import org.extex.interpreter.max.StringSource;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.language.LanguageManager;
import org.extex.language.LanguageManagerFactory;
import org.extex.logging.LogFormatter;
import org.extex.resource.PropertyAware;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.stream.TokenStreamOptions;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.ModifiableFont;
import org.extex.typesetter.tc.font.impl.FontImpl;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.*;

/**
 * This is the programmatic interface to the <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> functionality. A program may use this functionality to
 * perform all necessary actions without the burden of the traditional
 * <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> command line interface.
 * <p>
 * The programmatic interface provides the following features:
 * </p>
 * <ul>
 * <li>Specifying format, input file and <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> code in properties.</li>
 * </ul>
 * 
 * <a name="settings"/> <h3>Settings</h3>
 * 
 * <p>
 * Settings can be stored in properties files. Those settings are the fallback
 * if none are provided otherwise.
 * </p>
 * <p>
 * The properties are stored in a file named <tt>.extex</tt>. It is sought in
 * the users home directory. This determined by the system property
 * <tt>user.home</tt>. Afterwards it is sought in the current directory. The
 * local settings of a directory overwrite the user's setting. The user's
 * setting overwrite the compiled in defaults
 * </p>
 * <p>
 * The following properties are recognized:
 * </p>
 * <dl>
 * <dt><a name="extex.code"/><tt>extex.code</tt></a></dt>
 * <dd>This parameter contains <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> code to be executed directly. The execution is performed
 * after any code specified in an input file. on the command line the code has
 * to start with a backslash. This restriction does not hold for the property
 * settings.</dd>
 * <dd>Command line: <tt>&lang;code&rang;</tt></dd>
 * 
 * <dt><a name="extex.color.converter"/><tt>extex.color.converter</tt></dt>
 * <dd>This parameter contains the name of the configuration resource to use for
 * the color converter.</dd>
 * <dd>Default: <tt></tt></dd>
 * 
 * <dt><a name="extex.config"/><tt>extex.config</tt></dt>
 * <dd>This parameter contains the name of the configuration resource to use.
 * This configuration resource is sought on the class path.</dd>
 * <dd>Default: <tt>extex.xml</tt></dd>
 * 
 * <dt><a name="extex.encoding"/><tt>extex.encoding</tt></dt>
 * <dd>This parameter contains the name of the property for the standard
 * encoding to use.</dd>
 * <dd>Default: <tt>ISO-8859-1</tt></dd>
 * 
 * <dt><a name="extex.error.handler"/><tt>extex.error.handler</tt></dt>
 * <dd>This parameter contains the logical name of the error handler.</dd>
 * 
 * <dt><a name="extex.error.handler"/><tt>extex.error.handler</tt></dt>
 * <dd>This parameter contains the logical name of the error handler.</dd>
 * 
 * <dt><a name="extex.fonts"/><tt>extex.fonts</tt></dt>
 * <dd>This parameter contains the name of the property indicating where to find
 * font files. The value is a path similar to extex.texinputs.</dd>
 * 
 * <dt><a name="extex.halt.on.error"/><tt>extex.halt.on.error</tt></dt>
 * <dd>This parameter contains the name of the property indicating whether the
 * processing should stop after the first error.</dd>
 * 
 * <dt><a name="extex.file"><tt>extex.file</tt></a></dt>
 * <dd>This parameter contains the file to read from. It has no default</dd>
 * 
 * <dt><a name="extex.format"/><tt>extex.format</tt></dt>
 * <dd>This parameter contains the name of the format to read. An empty string
 * denotes that no format should be read. This is the default.</dd>
 * 
 * <dt><a name="extex.ini"/><tt>extex.ini</tt></dt>
 * <dd>If set to <code>true</code> then act as initex. This command line option
 * is defined for compatibility to <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> only. In <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> it has no effect at all.</dd>
 * 
 * <dt><a name="extex.interaction"/><tt>extex.interaction</tt></dt>
 * <dd>This parameter contains the interaction mode. possible values are the
 * numbers 0..3 and the symbolic names <tt>batchmode</tt> (0),
 * <tt>nonstopmode</tt> (1), <tt>scrollmode</tt> (2), and <tt>errorstopmode</tt>
 * (3).</dd>
 * <dd>Default: <tt>3</tt></dd>
 * 
 * <dt><a name="extex.jobname"/><tt>extex.jobname</tt></dt>
 * <dd>This parameter contains the name of the job. It is overwritten if a file
 * is given to read from. In this case the base name of the input file is used
 * instead.</dd>
 * <dd>Default: <tt>texput</tt></dd>
 * 
 * <dt><a name="extex.jobname.master"/><tt>extex.jobname.master</tt></dt>
 * <dd>This parameter contains the name of the job to be used with high
 * priority.</dd>
 * <dd>Default: <tt>texput</tt></dd>
 * 
 * <dt><a name="extex.lang"/><tt>extex.lang</tt></dt>
 * <dd>This parameter contains the name of the locale to be used for the
 * messages.</dd>
 * 
 * <dt><a name="extex.name"/><tt>extex.name</tt></dt>
 * <dd>This parameter contains the name of the program for messages.</dd>
 * 
 * <dt><a name="extex.nobanner"/><tt>extex.nobanner</tt></dt>
 * <dd>This parameter contains a Boolean indicating that the banner should be
 * suppressed.</dd>
 * 
 * <dt><a name="extex.output"/><tt>extex.output</tt></dt>
 * <dd>This parameter contains the output format. This logical name is resolved
 * via the configuration.</dd>
 * <dd>Default: <tt>pdf</tt></dd>
 * 
 * <dt><a name="extex.output.directories"/><tt>extex.output.directories</tt></dt>
 * <dd>This parameter contains a colon separated list of directories where
 * output files should be created. The directories are tried in turn until one
 * is found where the creation of the output file succeeds.</dd>
 * <dd>Default: <tt>.</tt></dd>
 * 
 * <dt><a name="extex.paper"/><tt>extex.paper</tt></dt>
 * <dd>This parameter contains the default size of the paper. It can be one of
 * the symbolic names defined in <tt>paper/paper.xml</tt>. Otherwise the value
 * is interpreted as a pair of width and height separated by a space.</dd>
 * 
 * <dt><a name="extex.progname"/><tt>extex.progname</tt></dt>
 * <dd>This parameter can be used to overrule the name of the program shown in
 * the banner and the version information.</dd>
 * <dd>Default: <tt>ExTeX</tt></dd>
 * 
 * <dt><a name="extex.stacktrace.on.internal.error"/>
 * <tt>extex.stacktrace.on.internal.error</tt></dt>
 * <dd>This boolean parameter contains indicator that a stack trace should be
 * written to the log stream on internal errors.</dd>
 * 
 * <dt><a name="extex.texinputs"/><tt>extex.texinputs</tt></dt>
 * <dd>This parameter contains the additional directories for searching
 * <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> input files.</dd>
 * 
 * <dt><a name="extex.token.stream"/><tt>extex.token.stream</tt></dt>
 * <dd>This string parameter contains the logical name of the configuration to
 * use for the token stream.</dd>
 * 
 * <dt><a name="extex.trace.input.files"/><tt>extex.trace.input.files</tt></dt>
 * <dd>This Boolean parameter contains the indicator whether or not to trace the
 * search for input files.</dd>
 * 
 * <dt><a name="extex.trace.font.files"/><tt>extex.trace.font.files</tt></dt>
 * <dd>This Boolean parameter contains the indicator whether or not to trace the
 * search for font files.</dd>
 * 
 * <dt><a name="extex.trace.macros"/><tt>extex.trace.macros</tt></dt>
 * <dd>This Boolean parameter contains the indicator whether or not to trace the
 * execution of macros.</dd>
 * 
 * <dt><a name="extex.trace.tokenizer"/><tt>extex.trace.tokenizer</tt></dt>
 * <dd>This Boolean parameter contains the indicator whether or not to trace the
 * work of the tokenizer.</dd>
 * 
 * <dt><a name="extex.typesetter"/><tt>extex.typesetter</tt></dt>
 * <dd>This parameter contains the name of the typesetter to use. If it is not
 * set then the default from the configuration file is used.</dd>
 * 
 * <dt><a name="extex.units"/><tt>extex.units</tt></dt>
 * <dd>This parameter contains a colon separated list of units to be loaded into
 * <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo>.</dd>
 * 
 * </dl>
 * 
 * <p>
 * There is another level of properties which is considered between the compiled
 * in defaults and the user's properties. Those are the system properties of the
 * Java system. In those properties system wide settings can be stored.
 * Nevertheless, you should use this feature sparsely.
 * </p>
 * 
 * 
 * <a name="configuration"/> <h3>Configuration Resources</h3>
 * 
 * <p>
 * The configuration of <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> is controlled by several configuration resources. The
 * fallback for those configuration resources are contained in the
 * <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> jar file. In this section we will describe how to overwrite
 * the settings in the default configuration resource.
 * </p>
 * 
 * TODO gene: doc incomplete
 * 
 * 
 * <a name="running"/> <h3>Programmatically Using <logo>&epsilon;&chi;T<span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo></h3>
 * 
 * <p>
 * This class is the central point for using an instance of
 * <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> from within a program. For this purpose the class has been
 * specially designed. The class can be used as is. This is not the normal way
 * to apply <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo>. The configuration via configuration files and properties
 * can be used to influence the behavior of the instance. Nevertheless the fine
 * points in the live cycle can not be accessed this way. This can be achieved
 * via deriving another class from it and overwriting one of the protected
 * methods.
 * </p>
 * <p>
 * The class provides several protected methods which are solemnly made
 * accessible to extend the functionality of the class. To do so you need
 * insight into the live cycle of the class.
 * </p>
 * <div class="figure"> <img src="doc-files/ExTeX-start.png" title="Starting
 * ExTeX"/> <div class="caption"> Starting <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> </div> </div>
 * <p>
 * Mainly there are two steps in using this class. The first step is the
 * creation of a new instance. This phase is depicted in the figure above. Note
 * that no interpreter is created during this phase. Thus the possibilities to
 * influence the run-time behavior is rather limited.
 * </p>
 * <dl>
 * <dt>{@link #propertyDefault(String,String) propertyDefault()}</dt>
 * <dd>This method is invoked several times to provide the compiled-in defaults
 * for certain properties.</dd>
 * <dt>{@link #applyLanguage() applyLanguage()}</dt>
 * <dd>This method is invoked to adjust the setting for the language.</dd>
 * <dt>{@link #applyInteraction() applyInteraction()}</dt>
 * <dd>This method is invoked to adjust the setting for the interaction mode.</dd>
 * </dl>
 * <p>
 * The major activities are carried out when the method {@link #run() run()} is
 * invoked. The methods used in this phase are shown in the figure below.
 * </p>
 * <div class="figure"> <img src="doc-files/ExTeX-run.png" title="Running
 * ExTeX"/> <div class="caption"> Running <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> </div> </div>
 * <dl>
 * <dt>{@link #makeLogFile(String) makeLogFile()}</dt>
 * <dd>Creates the log file.</dd>
 * <dt>{@link #makeLogHandler(File) makeLogHandler()}</dt>
 * <dd>Creates the log handler.</dd>
 * <dt>{@link #showBanner(Configuration,Level) showBanner()}</dt>
 * <dd>Presents the banner if necessary.</dd>
 * <dt>{@link #makeOutputFactory(String,Configuration) makeOutputFactory()}</dt>
 * <dd>Creates the output factory.</dd>
 * <dt>{@link #makeResourceFinder(Configuration) makeResourceFinder()}</dt>
 * <dd>Creates the resource finder.</dd>
 * <dt>
 * {@link #makeInterpreter(Configuration,OutputStreamFactory,ResourceFinder,String)
 * makeInterpreter()}</dt>
 * <dd>Start to create the interpreter. In the course of this operation some
 * more methods are invoked:
 * <dl>
 * <dt>{@link #makeFontFactory(Configuration,ResourceFinder) makeFontFactory()}</dt>
 * <dd>Creates the font factory.</dd>
 * <dt>{@link #makeTokenFactory(Configuration) makeTokenFactory()}</dt>
 * <dd>Creates the token factory.</dd>
 * <dt>
 * {@link #makeContext(Configuration,TokenFactory,CoreFontFactory,Interpreter,ResourceFinder,String,OutputStreamFactory)
 * makeContext()}</dt>
 * <dd>Creates the initial context and optionally loads a format:
 * <dl>
 * <dt>{@link #makeDefaultFont(Configuration,CoreFontFactory) makeDefaultFont()}
 * </dt>
 * <dd>Creates the default font from the specification in the configuration.</dd>
 * <dt>
 * {@link #makeLanguageManager(Configuration,OutputStreamFactory,ResourceFinder)
 * makeLanguageManager()}</dt>
 * <dd>Creates the language manager.</dd>
 * <dt>
 * {@link #loadFormat(String,Interpreter,ResourceFinder,String,Configuration,OutputStreamFactory,TokenFactory)
 * loadFormat()}</dt>
 * <dd>Loads a format if one is given.</dd>
 * </dl>
 * </dd>
 * <dt>{@link #initializeStreams(Interpreter,Properties) initializeStreams()}</dt>
 * <dd>Initialize the input and output streams.</dd>
 * <dt>
 * {@link #makeTypesetter(Interpreter,Configuration,OutputStreamFactory,ResourceFinder,CoreFontFactory)
 * makeTypesetter()}</dt>
 * <dd>Creates the typesetter.</dd>
 * </dl>
 * </dd>
 * <dt>{@link org.extex.interpreter.Interpreter#run() run()}</dt>
 * <dd>Runs the processing loop of the interpreter until it reaches an end.</dd>
 * <dt>{@link #logPages(BackendDriver) logPages()}</dt>
 * <dd>Do everything necessary to log the pages.</dd>
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * 
 * @version $Revision: 4770 $
 */
public class ExTeX {

    /**
     * The field <tt>CONTEXT_TAG</tt> contains the name of the tag for the
     * configuration of the context.
     */
    private static final String CONTEXT_TAG = "Context";

    /**
     * The field <tt>DEFAULT_JOBNAME</tt> contains the default for the job name
     * if none can be determined otherwise.
     */
    private static final String DEFAULT_JOBNAME = "texput";

    /**
     * The constant <tt>DEFAULT_LANGUAGE_KEY</tt> contains the key for the
     * default language.
     */
    private static final String DEFAULT_LANGUAGE_KEY = "0";

    /**
     * The constant <tt>VERSION</tt> contains the manually incremented version
     * string.
     */
    private static final String EXTEX_VERSION = "0.0";

    /**
     * The field <tt>FORMAT_FALLBACK</tt> contains the fallback to be tried if
     * the specified format can not be loaded. If it is <code>null</code> then
     * none is tried.
     */
    private static final String FORMAT_FALLBACK = "tex";

    /**
     * The constant <tt>formatType</tt> contains the type for the format.
     */
    private static final String FORMAT_TYPE = "fmt";

    /**
     * The field <tt>LANGUAGE_TAG</tt> contains the name of the tag for the
     * configuration of the language manager.
     */
    private static final String LANGUAGE_TAG = "Language";

    /**
     * The constant <tt>PROP_BANNER</tt> contains the name of the property for
     * the banner.
     */
    protected static final String PROP_BANNER = "extex.banner";

    /**
     * The constant <tt>PROP_CODE</tt> contains the name of the property for the
     * <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> code to be inserted at the beginning of the job.
     */
    protected static final String PROP_CODE = "extex.code";

    /**
     * The constant <tt>PROP_COLOR_CONVERTER</tt> contains the name of the
     * property for the color converter to use.
     */
    protected static final String PROP_COLOR_CONVERTER =
            "extex.color.converter";

    /**
     * The constant <tt>PROP_CONFIG</tt> contains the name of the property for
     * the configuration resource to use.
     */
    protected static final String PROP_CONFIG = "extex.config";

    /**
     * The constant <tt>PROP_ENCODING</tt> contains the name of the property for
     * the standard encoding to use.
     */
    protected static final String PROP_ENCODING = "extex.encoding";

    /**
     * The constant <tt>PROP_ERROR_HANDLER</tt> contains the name of the
     * property for the error handler type to use. Possible values are resolved
     * via the configuration.
     */
    protected static final String PROP_ERROR_HANDLER = "extex.error.handler";

    /**
     * The constant <tt>PROP_FILE</tt> contains the name of the property for the
     * input file to read.
     */
    protected static final String PROP_FILE = "extex.file";

    /**
     * The constant <tt>PROP_FMT</tt> contains the name of the property for the
     * name of the format file to use.
     */
    protected static final String PROP_FMT = "extex.format";

    /**
     * The constant <tt>PROP_HALT_ON_ERROR</tt> contains the name of the
     * property indicating whether the processing should stop at the first
     * error.
     */
    protected static final String PROP_HALT_ON_ERROR = "extex.halt.on.error";

    /**
     * The constant <tt>PROP_INI</tt> contains the name of the property for the
     * Boolean value indicating that some kind of emulations for iniTeX should
     * be provided. Currently this has no effect in <logo>&epsilon;&chi;T<span
     * style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo>.
     */
    protected static final String PROP_INI = "extex.ini";

    /**
     * The constant <tt>PROP_INTERACTION</tt> contains the name of the property
     * for the interaction mode.
     */
    protected static final String PROP_INTERACTION = "extex.interaction";

    /**
     * The constant <tt>PROP_INTERNAL_STACKTRACE</tt> contains the name of the
     * property indicating that a stack trace should be written for internal
     * errors.
     */
    protected static final String PROP_INTERNAL_STACKTRACE //
    = "extex.stacktrace.on.internal.error";

    /**
     * The constant <tt>PROP_JOBNAME</tt> contains the name of the property for
     * the job name. The value can be overruled by the property named in
     * <tt>PROP_JOBNAME_MASTER</tt>.
     */
    protected static final String PROP_JOBNAME = "extex.jobname";

    /**
     * The constant <tt>PROP_JOBNAME_MASTER</tt> contains the name of the
     * property for the job name to be used with high priority.
     */
    protected static final String PROP_JOBNAME_MASTER = "extex.jobname.master";

    /**
     * The constant <tt>PROP_LANG</tt> contains the name of the property for the
     * language to use for messages.
     */
    protected static final String PROP_LANG = "extex.lang";

    /**
     * The constant <tt>PROP_NAME</tt> contains the name of the property for the
     * program name used in messages.
     */
    protected static final String PROP_NAME = "extex.name";

    /**
     * The constant <tt>PROP_NO_BANNER</tt> contains the name of the property
     * for the Boolean value indicating whether or not to show a program banner.
     */
    protected static final String PROP_NO_BANNER = "extex.nobanner";

    /**
     * The constant <tt>PROP_OUTPUT_DIRS</tt> contains the name of the property
     * for the output directory path.
     */
    protected static final String PROP_OUTPUT_DIRS = "extex.output.directories";

    /**
     * The constant <tt>PROP_OUTPUT_TYPE</tt> contains the name of the property
     * for the output driver. This value is resolved by the
     * {@link org.extex.backend.documentWriter.DocumentWriterFactory
     * DocumentWriterFactory} to find the appropriate class.
     */
    protected static final String PROP_OUTPUT_TYPE = "extex.output";

    /**
     * The constant <tt>PROP_PAGE</tt> contains the name of the property for the
     * default page dimensions.
     */
    protected static final String PROP_PAGE = "extex.page";

    /**
     * The constant <tt>PROP_PROGNAME</tt> contains the name of the property for
     * the program name used in format loading.
     */
    protected static final String PROP_PROGNAME = "extex.progname";

    /**
     * The constant <tt>PROP_TEXINPUTS</tt> contains the name of the property
     * for the additional texinputs specification of directories.
     */
    protected static final String PROP_TEXINPUTS = "extex.texinputs";

    /**
     * The constant <tt>PROP_TOKEN_STREAM</tt> contains the name of the property
     * for the token stream class to use.
     */
    protected static final String PROP_TOKEN_STREAM = "extex.token.stream";

    /**
     * The constant <tt>PROP_TRACE_FONT_FILES</tt> contains the name of the
     * property for the Boolean determining whether or not the searching for
     * font files should produce tracing output.
     */
    protected static final String PROP_TRACE_FONT_FILES =
            "extex.trace.font.files";

    /**
     * The constant <tt>PROP_TRACE_INPUT_FILES</tt> contains the name of the
     * property for the Boolean determining whether or not the searching for
     * input files should produce tracing output.
     */
    protected static final String PROP_TRACE_INPUT_FILES =
            "extex.trace.input.files";

    /**
     * The constant <tt>PROP_TRACE_MACROS</tt> contains the name of the property
     * for the Boolean determining whether or not the execution of macros should
     * produce tracing output.
     */
    protected static final String PROP_TRACE_MACROS = "extex.trace.macros";

    /**
     * The constant <tt>PROP_TRACE_TOKENIZER</tt> contains the name of the
     * property for the Boolean determining whether or not the tokenizer should
     * produce tracing output.
     */
    protected static final String PROP_TRACE_TOKENIZER =
            "extex.trace.tokenizer";

    /**
     * The constant <tt>PROP_TRACING_ONLINE</tt> contains the name of the
     * property for the Boolean determining whether or not the tracing should
     * produce log output in the log file only.
     */
    protected static final String PROP_TRACING_ONLINE = "extex.tracing.online";

    /**
     * The constant <tt>PROP_TYPESETTER_TYPE</tt> contains the name of the
     * property for the typesetter to use. This value is resolved by the
     * {@link org.extex.engine.typesetter.TypesetterFactory TypesetterFactory}
     * to find the appropriate class.
     */
    protected static final String PROP_TYPESETTER_TYPE = "extex.typesetter";

    /**
     * The constant <tt>TAG_ERRORHANDLER</tt> contains the name of the tag in
     * the configuration file which contains the specification for the error
     * handler factory.
     */
    private static final String TAG_ERRORHANDLER = "ErrorHandler";

    /**
     * The constant <tt>TAG_FONT</tt> contains the name of the tag in the
     * configuration file which contains the specification for the font.
     */
    private static final String TAG_FONT = "BaseFont";

    /**
     * Getter for the version.
     * 
     * @return the version number for this class
     */
    public static String getVersion() {

        return EXTEX_VERSION;
    }

    /**
     * Log a {@link java.lang.Throwable Throwable} including its stack trace to
     * the logger.
     * 
     * @param logger the target logger
     * @param text the prefix text to log
     * @param e the Throwable to log
     */
    protected static void logException(Logger logger, String text, Throwable e) {

        logger.log(Level.SEVERE, text == null ? "" : text);
        logger.log(Level.FINE, "", e);
    }

    /**
     * The field <tt>errorHandler</tt> contains the error handler to use.
     */
    private ErrorHandler errorHandler = null;

    /**
     * The field <tt>ini</tt> contains the indicator for iniTeX.
     */
    private final boolean ini;

    /**
     * The field <tt>interactionObserver</tt> contains the observer called
     * whenever the interaction mode is changed.
     */
    private InteractionObserver interactionObserver = null;

    /**
     * The field <tt>iProvider</tt> contains the bridge from the resource finder
     * to the context.
     */
    private final ContextawareInteractionIndicator iProvider =
            new ContextawareInteractionIndicator();

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated with
     * a localizer for the name of this class.
     */
    private Localizer localizer = LocalizerFactory.getLocalizer(ExTeX.class);

    /**
     * The field <tt>logger</tt> contains the logger currently in use.
     */
    private Logger logger = Logger.getLogger(ExTeX.class.getName());

    /**
     * The field <tt>noBanner</tt> contains the indicator that a banner has
     * already been printed and a repetition should be avoided.
     */
    private final boolean noBanner;

    /**
     * The field <tt>outStream</tt> contains the output stream for the document
     * writer.
     */
    private OutputStream outStream = null;

    /**
     * The field <tt>properties</tt> contains the properties containing the
     * settings for the invocation.
     */
    private final Properties properties;

    /**
     * The field <tt>showBanner</tt> is a boolean indicating that it is
     * necessary to display the banner. This information is needed for the cases
     * where errors show up before the normal banner has been printed.
     */
    private boolean showBanner = true;

    /**
     * The field <tt>consoleHandler</tt> contains the ...
     */
    private Handler consoleHandler;

    /**
     * Creates a new object and supplies some properties for those keys which
     * are not contained in the properties already. A detailed list of the
     * properties supported can be found in section <a
     * href="#settings">Settings</a>.
     * 
     * @param theProperties the properties to start with. This object is used
     *        and modified. The caller should provide a new instance if this is
     *        not desirable.
     * 
     * @throws HelpingException in case of an error
     */
    public ExTeX(Properties theProperties) throws HelpingException {

        this.properties = theProperties;
        propertyDefault(PROP_BANNER, System.getProperty("java.version"));
        propertyDefault(PROP_CODE, "");
        propertyDefault(PROP_CONFIG, "extex.xml");
        propertyDefault(PROP_ENCODING, "ISO-8859-1");
        propertyDefault(PROP_ERROR_HANDLER, "");
        propertyDefault(PROP_FILE, "");
        propertyDefault(PROP_FMT, "");
        propertyDefault(PROP_INI, "");
        propertyDefault(PROP_INTERACTION, "3");
        propertyDefault(PROP_JOBNAME, DEFAULT_JOBNAME);
        propertyDefault(PROP_JOBNAME_MASTER, "");
        propertyDefault(PROP_NAME, "ExTeX");
        propertyDefault(PROP_NO_BANNER, "");
        propertyDefault(PROP_LANG, "");
        propertyDefault(PROP_OUTPUT_TYPE, "");
        propertyDefault(PROP_OUTPUT_DIRS, ".");
        propertyDefault(PROP_PAGE, "");
        propertyDefault(PROP_PROGNAME, "extex");
        propertyDefault(PROP_TEXINPUTS, null);
        propertyDefault(PROP_TOKEN_STREAM, "base");
        propertyDefault(PROP_TRACE_INPUT_FILES, "");
        propertyDefault(PROP_TRACE_FONT_FILES, "");
        propertyDefault(PROP_TRACE_MACROS, "");
        propertyDefault(PROP_TRACE_TOKENIZER, "");
        propertyDefault(PROP_TRACING_ONLINE, "");
        propertyDefault(PROP_TYPESETTER_TYPE, "");
        propertyDefault(PROP_INTERNAL_STACKTRACE, "");

        noBanner = getBooleanProperty(PROP_NO_BANNER);
        showBanner = !noBanner;
        ini = !getBooleanProperty(PROP_INI);

        applyLanguage();

        logger = Logger.getLogger(getClass().getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        logger.addHandler(consoleHandler);
        interactionObserver = new InteractionModeObserver(consoleHandler);
        applyInteraction();
    }

    /**
     * Creates a new object and initializes the properties from given properties
     * and possibly from a user's properties in the file <tt>.extex</tt>. The
     * user properties are loaded from the users home directory and the current
     * directory.
     * 
     * @param theProperties the properties to consider
     * @param dotFile the name of the local configuration file. In the case that
     *        this value is <code>null</code> no user properties will be
     *        considered.
     * 
     * @throws HelpingException in case of an invalid interaction mode
     * @throws IOException in case of an IO Error during the reading of the
     *         properties file
     * 
     * @see #ExTeX(java.util.Properties)
     */
    public ExTeX(Properties theProperties, String dotFile)
            throws IOException,
                HelpingException {

        this(theProperties);

        if (dotFile != null) {
            loadUserProperties(new File(System.getProperty("user.home"),
                dotFile));
            loadUserProperties(new File(dotFile));

            applyLanguage();
        }
    }

    /**
     * Propagate the settings for the interaction mode to the
     * <code>interactionObserver</code>.
     * 
     * @throws InteractionUnknownException in case that the interaction is not
     *         set properly
     */
    protected void applyInteraction() throws InteractionUnknownException {

        String interaction = properties.getProperty(PROP_INTERACTION);
        try {
            interactionObserver.receiveInteractionChange(null,
                Interaction.get(interaction));
        } catch ( InteractionUnknownException | RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new InteractionUnknownException(interaction);
        }
    }

    /**
     * Try to determine which language to use and configure the localizer
     * accordingly. If no locale has been given then the default locale is used.
     * If the given locale is not supported then the default locale is used and
     * a warning is logged.
     */
    protected void applyLanguage() {

        String lang = properties.getProperty(PROP_LANG);

        if (lang == null || "".equals(lang)) {
            // do nothing
        } else if (lang.matches("[a-z][a-z]")) {
            Locale.setDefault(new Locale(lang));
        } else if (lang.matches("[a-z][a-z][-_][a-z][a-z]")) {
            Locale.setDefault(new Locale(lang.substring(0, 1), //
                lang.substring(3, 4)));
        } else if (lang.matches("[a-z][a-z][-_][a-z][a-z][-_][a-z][a-z]")) {
            Locale.setDefault(new Locale(lang.substring(0, 1), //
                lang.substring(3, 4), lang.substring(6, 7)));
        } else {
            getLogger().warning(localizer.format("ExTeX.locale.error", lang));
            return;
        }

        localizer = LocalizerFactory.getLocalizer(ExTeX.class);
    }

    /**
     * Close the instance by detaching the console handler from the logger.
     */
    public void close() {

        if (consoleHandler != null) {
            consoleHandler.close();
            logger.removeHandler(consoleHandler);
            consoleHandler = null;
        }
    }

    /**
     * Find the name of the job.
     * 
     * @return the correct job name
     */
    private String determineJobname() {

        String jobname = properties.getProperty(PROP_JOBNAME_MASTER);

        if (jobname == null || jobname.equals("")) {
            jobname = properties.getProperty(PROP_JOBNAME);
            if (jobname == null || jobname.equals("")) {
                jobname = DEFAULT_JOBNAME;
            }
        }
        return new File(jobname).getName();
    }

    /**
     * Getter for a named property as boolean.
     * 
     * @param key the property name
     * 
     * @return the value of the named property
     */
    public boolean getBooleanProperty(String key) {

        return Boolean.valueOf(this.properties.getProperty(key)).booleanValue();
    }

    /**
     * Getter for localizer.
     * 
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return this.localizer;
    }

    /**
     * Getter for logger.
     * 
     * @return the logger.
     */
    public Logger getLogger() {

        return this.logger;
    }

    /**
     * Getter for outStream.
     * 
     * @return the outStream.
     */
    public OutputStream getOutStream() {

        return this.outStream;
    }

    /**
     * Getter for properties.
     * 
     * @return the properties
     */
    public Properties getProperties() {

        return this.properties;
    }

    /**
     * Getter for a named property.
     * 
     * @param key the property name
     * 
     * @return the value of the named property or <code>null</code>
     */
    public String getProperty(String key) {

        return this.properties.getProperty(key);
    }

    /**
     * Initialize the input streams. If the property <i>extex.file</i> is set
     * and not the empty string, (e.g. from the command line) then this value is
     * used as file name to read from. If the property <i>extex.code</i> is set
     * and not the empty string (e.g. from the command line) then this value is
     * used as initial input after the input file has been processed.
     * 
     * @param interpreter the interpreter context
     * @param prop the properties
     * 
     * @return <code>true</code> if the stream have not been initialized
     * 
     * @throws ConfigurationException in case of a configuration error
     */

    protected boolean initializeStreams(Interpreter interpreter, Properties prop) {

        TokenStreamFactory factory = interpreter.getTokenStreamFactory();
        boolean notInitialized = true;

        String filename = prop.getProperty(PROP_FILE);

        if (filename != null && !filename.equals("")) {

            TokenStream stream =
                    factory.getStream(filename, "tex",
                        prop.getProperty(PROP_ENCODING));
            if (stream == null) {
                logger.severe(localizer.format("TTP.FileNotFound", filename));
            } else {
                interpreter.addStream(stream);
                notInitialized = false;
            }
        }

        String post = prop.getProperty(PROP_CODE);

        if (post != null && !post.equals("")) {
            interpreter.addStream(factory.getStream(post));
            notInitialized = false;
        }

        return notInitialized;
    }

    /**
     * Load a format if a non-empty name of a format is given.
     * 
     * @param fmt the name of the format to use or <code>null</code> or the
     *        empty string to suppress loading
     * @param interpreter the interpreter to delegate the loading to
     * @param finder the resource finder to use for locating the format file
     * @param jobname the name of the job
     * @param config the interpreter configuration
     * @param outFactory the output stream factory
     * @param tokenFactory the token factory to assign to the context or
     *        <code>null</code> if no assignment should be performed
     * 
     * @return the context
     * 
     * @throws GeneralException in case of some error
     * @throws IOException in case, well, you guess it
     * @throws ConfigurationException in case of a configuration error
     */
    protected Context loadFormat(String fmt, Interpreter interpreter,
            ResourceFinder finder, String jobname, Configuration config,
            OutputStreamFactory outFactory, TokenFactory tokenFactory)
            throws IOException,
                GeneralException {

        String format = (fmt == null ? "" : fmt);
        String time = DateFormat.getDateTimeInstance(DateFormat.SHORT, //
            DateFormat.SHORT).format(new Date());

        Context context = interpreter.getContext();

        if (tokenFactory != null) {
            context.setTokenFactory(tokenFactory);
        }

        if (!format.equals("")) {
            InputStream stream = finder.findResource(fmt, FORMAT_TYPE);

            if (stream == null && !format.equals(FORMAT_FALLBACK)) {
                logger.warning(localizer.format("FormatSubstituted", format,
                    FORMAT_FALLBACK));
                format = FORMAT_FALLBACK;
                stream = finder.findResource(FORMAT_FALLBACK, FORMAT_TYPE);
            }
            if (stream == null) {
                throw new HelpingException(localizer, "FormatNotFound", format);
            }
            Object ref1 =
                    Registrar.register(new ResourceFinderInjector(finder),
                        ResourceAware.class);
            Object ref2 =
                    Registrar.register(
                        new FontInjector(context.getFontFactory()),
                        ModifiableFont.class);
            try {
                // TODO gene: provide adequate configuration names
                interpreter.loadFormat(stream, format, "ExTeX", "ExTeX");

            } catch (LoaderException e) {
                logger.throwing(this.getClass().getName(), "loadFormat()", e);
                throw new HelpingException(localizer, "TTP.FormatFileError",
                    format);
            } catch (RegistrarFontNotFoundException e) {
                throw new HelpingException(localizer, "FontNotFound", e
                    .getKey().getName());
            } finally {
                Registrar.unregister(ref1);
                Registrar.unregister(ref2);
            }
            context = interpreter.getContext();
            logger.fine(localizer.format("ExTeX.FormatDate", context.getId(),
                time));
        } else if (ini) {
            if (!noBanner) {
                logger.fine(localizer.format("ExTeX.NoFormatDate", time));
            }

            Interaction mode = context.getInteraction();
            try {
                context.setInteraction(Interaction.ERRORSTOPMODE);

                for (Iterator<Configuration> iterator = config.iterator("unit"); iterator
                    .hasNext();) {
                    LoadUnit.loadUnit(iterator.next(), context, interpreter,
                        null, logger, outFactory, finder);
                }

            } finally {
                if (mode != null) {
                    context.setInteraction(mode);
                }
            }

        } else {
            throw new GeneralException();
        }

        interpreter.setJobname(jobname);

        if (context instanceof InteractionObservable) {
            ((InteractionObservable) context)
                .registerInteractionObserver(interactionObserver);
        } else {
            logger.info(localizer.format("InteractionNotSupported"));
        }

        if (getBooleanProperty(PROP_TRACING_ONLINE)) {
            context.setCount("tracingonline", 1, true);
        }

        iProvider.setContext(context);

        return context;
    }

    /**
     * Load properties from a given file if it exists.
     * 
     * @param file the file to consider
     * 
     * @throws IOException in case of an IO Error during the reading of the
     *         properties file
     */
    protected void loadUserProperties(File file) throws IOException {

        if (file != null && file.canRead()) {
            FileInputStream stream = new FileInputStream(file);
            properties.load(stream);
            stream.close();
        }
    }

    /**
     * Remap an exception into an InterpreterException.
     * 
     * @param t the throwable to be remapped
     * 
     * @return an appropriate exception
     */
    protected InterpreterException logError(Throwable t) {

        Throwable e = t;
        Throwable cause = e.getCause();

        while (cause instanceof GeneralException) {

            e = cause;
            cause = e.getCause();
        }

        if (e instanceof HelpingException) {
            HelpingException he = (HelpingException) e;
            if (!he.isProcessed()) {
                he.setProcessed(true);
                logger.severe("\n" + he.getLocalizedMessage() + "\n");
            }
            InterpreterException ie = new InterpreterException(he);
            ie.setProcessed(true);
            return ie;
        } else if (e instanceof InterpreterException) {
            InterpreterException ie = (InterpreterException) e;
            if (!ie.isProcessed()) {
                ie.setProcessed(true);
                logger.severe("\n" + e.getLocalizedMessage() + "\n");
            }

            return ie;
        }
        return new InterpreterException(e);
    }

    /**
     * Log a Throwable including its stack trace to the logger.
     * 
     * @param e the Throwable to log
     */
    protected void logInternalError(Throwable e) {

        if (getBooleanProperty(PROP_INTERNAL_STACKTRACE)) {
            e.printStackTrace();
        }

        logException(logger, e.toString(), e);

        Throwable t = e;
        String msg;
        do {
            msg = t.getLocalizedMessage();
            if (msg != null && !"".equals(msg)) {
                logException(logger,
                    localizer.format("ExTeX.InternalError", msg), e);
                return;
            }
            t = t.getCause();
        } while (t != null);

        msg = e.getClass().getName();
        msg = msg.substring(msg.lastIndexOf('.') + 1);

        logException(logger, localizer.format("ExTeX.InternalError", msg), e);
    }

    /**
     * This method can be overwritten to provide logging functionality for the
     * pages produced.
     * 
     * @param backend the back-end driver
     */
    protected void logPages(BackendDriver backend) {

        // to be overwritten
    }

    /**
     * Create a new document writer.
     * 
     * <pre>
     *   {@link #makeColorConverter(Configuration) makeColorConverter()}
     * </pre>
     * 
     * 
     * @param config the configuration object for the document writer
     * @param outFactory the output factory
     * @param options the options to be passed to the document writer
     * @param finder the resource finder if one is requested by the document
     *        writer
     * @param fontFactory the font factory for the document writer
     * 
     * @return the new document writer
     * 
     * @throws DocumentWriterException in case of an error
     * @throws ConfigurationException in case of a configuration problem
     */
    protected BackendDriver makeBackend(Configuration config,
            OutputStreamFactory outFactory, DocumentWriterOptions options,
            ResourceFinder finder, CoreFontFactory fontFactory)
            throws DocumentWriterException {

        String outputType = properties.getProperty(PROP_OUTPUT_TYPE);
        BackendFactory backendFactory = new BackendFactory();
        backendFactory.configure(config);
        backendFactory.enableLogging(logger);
        backendFactory.setOptions(options);
        return backendFactory.newInstance(//
            outputType, //
            outFactory, //
            finder, //
            properties, //
            properties.getProperty(PROP_NAME) + " " + EXTEX_VERSION, //
            fontFactory, //
            makeColorConverter(config.getConfiguration("ColorConverter")));
    }

    /**
     * Make a new instance of a color converter.
     * 
     * @param config the configuration to use
     * 
     * @return the new color converter
     * 
     * @throws ConfigurationException in case of a configuration problem
     */
    protected ColorConverter makeColorConverter(Configuration config) {

        return new ColorConverterFacory(config, logger).newInstance(//
            properties.getProperty(PROP_COLOR_CONVERTER));
    }

    /**
     * Prepare the context according to its configuration.
     * 
     * <pre>
     *   {@link #makeDefaultFont(Configuration, CoreFontFactory) makeDefaultFont()}
     *   {@link #makeLanguageManager(Configuration, OutputStreamFactory, ResourceFinder) makeLanguageManager()}
     *   {@link #loadFormat(String, Interpreter, ResourceFinder, String, Configuration, OutputStreamFactory, TokenFactory) loadFormat()}
     *   {@link #makePageSize(Context) makePageSize()}
     * </pre>
     * 
     * 
     * @param config the configuration of the interpreter
     * @param tokenFactory the token factory to inject
     * @param fontFactory the font factory
     * @param interpreter the interpreter
     * @param finder the resource finder
     * @param jobname the job name
     * @param outFactory the output stream factory
     * 
     * @return the newly created context
     * 
     * @throws ConfigurationException in case of a configuration error
     * @throws FontException in case of a font error
     * @throws GeneralException in case of an error
     * @throws IOException in case of an IO error
     */
    protected Context makeContext(Configuration config,
            TokenFactory tokenFactory, CoreFontFactory fontFactory,
            Interpreter interpreter, ResourceFinder finder, String jobname,
            OutputStreamFactory outFactory)
            throws GeneralException,
                FontException,
                IOException {

        Context context =
                new ContextFactory(config.getConfiguration(CONTEXT_TAG), logger)
                    .newInstance(null);

        interpreter.setContext(context);

        context.setFontFactory(fontFactory);
        context.set(
            makeDefaultFont(config.findConfiguration(TAG_FONT), fontFactory),
            true);

        context.setLanguageManager(makeLanguageManager(config, outFactory,
            finder));
        context.set(context.getLanguage(DEFAULT_LANGUAGE_KEY), true);

        context =
                loadFormat(properties.getProperty(PROP_FMT), interpreter,
                    finder, jobname, config, outFactory, tokenFactory);

        String units = properties.getProperty("extex.units");
        if (units != null) {
            for (String s : units.split(":")) {
                if (!s.equals("")) {
                    interpreter.loadUnit(s, finder);
                }
            }
        }
        makePageSize(context);

        if (getBooleanProperty(PROP_TRACE_MACROS)) {
            context.setCount("tracingcommands", 1, true);
        }

        return context;
    }

    /**
     * Create a default font for the interpreter context.
     * 
     * @param config the configuration object for the font. This can be
     *        <code>null</code>
     * @param fontFactory the font factory to request the font from
     * 
     * @return the default font
     * 
     * @throws GeneralException in case of an error of some other kind
     * @throws ConfigurationException in case that some kind of problems have
     *         been detected in the configuration
     * @throws FontException in case of problems with the font itself
     */
    protected Font makeDefaultFont(Configuration config,
            CoreFontFactory fontFactory) throws GeneralException, FontException {

        if (config == null) {
            return new FontImpl(fontFactory.getInstance(null));
        }
        String attributeName = "name";
        String attributeSize = "size";
        String defaultFont = config.getAttribute(attributeName);

        if (defaultFont == null || defaultFont.equals("")) {
            return new FontImpl(fontFactory.getInstance(null));
        }

        String size = config.getAttribute(attributeSize);
        if (size == null) {
            return new FontImpl(fontFactory.getInstance(fontFactory
                .getFontKey(defaultFont)));
        }

        Font font = null;
        try {
            float f = Float.parseFloat(size);
            font =
                    new FontImpl(fontFactory.getInstance(fontFactory
                        .getFontKey(defaultFont, new Dimen(
                            ((long) (Dimen.ONE * f))))));
        } catch (NumberFormatException e) {
            throw new ConfigurationSyntaxException(attributeSize,
                config.toString());
        }
        return font;
    }

    /**
     * Create a new font factory.
     * 
     * @param config the configuration object for the font factory
     * @param finder the resource finder to use
     * 
     * @return the new font factory
     * 
     * @throws ConfigurationException in case that some kind of problems have
     *         been detected in the configuration
     */
    protected CoreFontFactory makeFontFactory(Configuration config,
            ResourceFinder finder) {

        CoreFontFactory fontFactory;
        String fontClass = config.getAttribute("class");

        if (fontClass == null || fontClass.equals("")) {
            throw new ConfigurationMissingAttributeException("class", config);
        }

        try {
            fontFactory =
                    (CoreFontFactory) (Class.forName(fontClass).getConstructor(
                        new Class[]{}).newInstance(new Object[]{}));

        } catch (NoSuchMethodException e) {
            throw new ConfigurationNoSuchMethodException(e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationClassNotFoundException(fontClass);
        } catch ( Exception e) {
            throw new ConfigurationInstantiationException(e);
        }
        if (fontFactory instanceof Configurable) {
            ((Configurable) fontFactory).configure(config);
        }
        if (fontFactory instanceof LogEnabled) {
            ((LogEnabled) fontFactory).enableLogging(logger);
        }
        if (fontFactory instanceof ResourceAware) {
            ((ResourceAware) fontFactory).setResourceFinder(finder);
        }
        if (fontFactory instanceof PropertyAware) {
            ((PropertyAware) fontFactory).setProperties(properties);
        }

        return fontFactory;
    }

    /**
     * Create a new interpreter.
     * 
     * <pre>
     *   {@link #makeFontFactory(Configuration, ResourceFinder) makeFontFactory()}
     *   {@link #makeTokenStreamFactory(Configuration, ResourceFinder) makeTokenStreamFactory()}
     *   {@link #makeTokenFactory(Configuration) makeTokenFactory()}
     * </pre>
     * 
     * @param config the configuration object for the interpreter
     * @param outFactory the factory for new output streams
     * @param finder the resource finder
     * @param jobname the job name
     * 
     * @return the new interpreter
     * 
     * @throws ConfigurationException in case that some kind of problems have
     *         been detected in the configuration
     * @throws GeneralException in case of an error of some other kind
     * @throws FontException in case of problems with the font itself
     * @throws IOException in case of an IO error
     */
    protected Interpreter makeInterpreter(Configuration config,
            OutputStreamFactory outFactory, ResourceFinder finder,
            String jobname) throws GeneralException, FontException, IOException {

        CoreFontFactory fontFactory =
                makeFontFactory(config.getConfiguration("Fonts"), finder);

        TokenStreamFactory tokenStreamFactory =
                makeTokenStreamFactory(config.getConfiguration("Scanner"),
                    finder);

        Configuration interpreterConfig =
                config.getConfiguration("Interpreter");

        Interpreter interpreter =
                new InterpreterFactory(interpreterConfig, logger).newInstance(
                    properties, outFactory);

        TokenFactory tokenFactory =
                makeTokenFactory(interpreterConfig
                    .getConfiguration("TokenFactory"));

        interpreter.setContext(makeContext(interpreterConfig, tokenFactory,
            fontFactory, interpreter, finder, jobname, outFactory));

        interpreter.setInteraction(Interaction.get(
            properties.getProperty(PROP_INTERACTION)));

        interpreter.setFontFactory(fontFactory);

        ErrorHandler errHandler = errorHandler;
        if (errHandler == null) {
            ErrorHandlerFactory errorHandlerFactory =
                    new ErrorHandlerFactory(
                        interpreterConfig.getConfiguration(TAG_ERRORHANDLER));
            errorHandlerFactory.enableLogging(logger);
            errHandler = errorHandlerFactory.newInstance(//
                properties.getProperty(PROP_ERROR_HANDLER));
        }
        interpreter.setErrorHandler(errHandler);

        interpreter.setTokenStreamFactory(tokenStreamFactory);
        Context context = interpreter.getContext();
        tokenStreamFactory.setOptions((TokenStreamOptions) context);

        initializeStreams(interpreter, properties);

        Typesetter typesetter = makeTypesetter(interpreter, config, //
            outFactory, finder, fontFactory);
        interpreter.setTypesetter(typesetter);

        Iterator<UnitInfo> unitIterator = context.unitIterator();
        while (unitIterator.hasNext()) {
            unitIterator.next().setTypesetter(typesetter);
        }

        return interpreter;
    }

    /**
     * Prepare the hyphenation manager according to its configuration.
     * <p>
     * This method can be overwritten in derived classes to perform additional
     * tasks. In this case it might be a good idea to invoke this method from
     * the super-class to do its job.
     * </p>
     * 
     * @param config the configuration
     * @param outFactory the output stream factory
     * @param finder the resource finder
     * 
     * @return the language manager created
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    protected LanguageManager makeLanguageManager(Configuration config,
            OutputStreamFactory outFactory, ResourceFinder finder) {

        LanguageManagerFactory factory = new LanguageManagerFactory();
        factory.enableLogging(logger);
        factory.configure(config.getConfiguration(LANGUAGE_TAG));
        return factory.newInstance(DEFAULT_LANGUAGE_KEY, outFactory, finder);
    }

    /**
     * Find the name for the log file.
     * <p>
     * This method can be overwritten in derived classes to perform additional
     * tasks. In this case it might be a good idea to invoke this method from
     * the super-class to do its job.
     * </p>
     * 
     * @param jobname the name of the job
     * 
     * @return the new file
     */
    protected File makeLogFile(String jobname) {

        String[] dirs = properties.getProperty(PROP_OUTPUT_DIRS).split(":");

        for (int i = 0; i < dirs.length; i++) {

            File logFile = new File(dirs[i], jobname + ".log");

            if (logFile.exists()) {
                if (logFile.canWrite()) {
                    return logFile;
                }
            } else {
                File dir = logFile.getParentFile();
                if (dir != null && dir.canWrite()) {
                    return logFile;
                }
            }
        }

        return null;
    }

    /**
     * Create a new Handler for the log file.
     * <p>
     * This method can be overwritten in derived classes to perform additional
     * tasks. In this case it might be a good idea to invoke this method from
     * the super-class to do its job.
     * </p>
     * 
     * @param logFile the name of the log file
     * 
     * @return the new handler
     */
    protected Handler makeLogHandler(File logFile) {

        if (logFile == null) {
            return null;
        }

        Handler handler;
        try {
            OutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(logFile));
            handler = new StreamHandler(stream, new LogFormatter());
            handler.setLevel(Level.ALL);
            logger.addHandler(handler);

        } catch (SecurityException e) {
            logger.severe(localizer.format("ExTeX.LogFileError",
                e.getLocalizedMessage()));
            handler = null;
        } catch (IOException e) {
            logger.severe(localizer.format("ExTeX.LogFileError",
                e.getLocalizedMessage()));
            handler = null;
        }
        return handler;
    }

    /**
     * Create the output factory.
     * 
     * @param jobname the job name
     * @param config the configuration
     * 
     * @return the output factory
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    protected OutputFactory makeOutputFactory(String jobname,
            Configuration config) {

        OutputFactory outFactory = new OutputFactory(//
            properties.getProperty(PROP_OUTPUT_DIRS).split(":"), //
            jobname);
        outFactory.setDefaultStream(outStream);
        outFactory.configure(config);
        outFactory.enableLogging(logger);
        return outFactory;
    }

    /**
     * Determine the default paper size.
     * 
     * @param context the context
     * 
     * @throws ConfigurationException in case of an configuration error
     * @throws GeneralException in case of another error
     */
    private void makePageSize(Context context) throws GeneralException {

        String page = (String) properties.get(PROP_PAGE);
        Configuration cfg =
                ConfigurationFactory.newInstance("config/paper/paper")
                    .findConfiguration(page);
        String w = "210mm";
        String h = "297mm";
        int i = page.indexOf(' ');
        if (cfg != null) {
            w = cfg.getAttribute("width");
            h = cfg.getAttribute("height");
        } else if (i > 0) {
            w = page.substring(0, i);
            h = page.substring(i + 1);
        } else if (!"".equals(page)) {
            throw new GeneralException(localizer.format(
                "ExTeX.InvalidPageSize", page));
        }
        try {
            Dimen width =
                    ConstantDimenParser
                        .scan(context, new StringSource(w), null);
            Dimen height =
                    ConstantDimenParser
                        .scan(context, new StringSource(h), null);

            context.setDimen("mediawidth", width, true);
            context.setDimen("mediaheight", height, true);
        } catch (Exception e) {
            throw new GeneralException(localizer.format(
                "ExTeX.InvalidPageSize", page), e);
        }
    }

    /**
     * Create a ResourceFinder. Implicitly the logger and the properties are
     * used.
     * <p>
     * This method can be overwritten in derived classes to perform additional
     * tasks. In this case it might be a good idea to invoke this method from
     * the super-class to do its job.
     * </p>
     * 
     * @param config the configuration
     * 
     * @return the new resource finder
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    protected ResourceFinder makeResourceFinder(Configuration config) {

        ResourceFinder finder =
                new ResourceFinderFactory().createResourceFinder(config,
                    logger, properties, iProvider);

        if (getBooleanProperty(PROP_TRACE_INPUT_FILES)) {
            finder.enableTracing(true);
        }

        return finder;
    }

    /**
     * Prepare the token factory according to its configuration.
     * <p>
     * This method can be overwritten in derived classes to perform additional
     * tasks. In this case it might be a good idea to invoke this method from
     * the super-class to do its job.
     * </p>
     * 
     * @param config the configuration
     * 
     * @return the token factory
     * 
     * @throws ConfigurationException in case of a configuration error
     */
    protected TokenFactory makeTokenFactory(Configuration config) {

        TokenFactoryFactory tokenFactoryFactory = new TokenFactoryFactory();
        tokenFactoryFactory.configure(config);
        tokenFactoryFactory.enableLogging(logger);
        return tokenFactoryFactory.createInstance();
    }

    /**
     * Create a TokenStreamFactory. The logger is used implicitly.
     * <p>
     * This method can be overwritten in derived classes to perform additional
     * tasks. In this case it might be a good idea to invoke this method from
     * the super-class to do its job.
     * </p>
     * 
     * @param config the configuration object for the token stream factory
     * @param finder the file finder for the token stream factory
     * 
     * @return the token stream factory
     * 
     * @throws ConfigurationException in case that some kind of problems have
     *         been detected in the configuration
     * @throws NotObservableException in case that the observer for file events
     *         could not be registered
     */
    protected TokenStreamFactory makeTokenStreamFactory(Configuration config,
            ResourceFinder finder) throws NotObservableException {

        TokenStreamFactory factory = new TokenStreamFactory(//
            properties.getProperty(PROP_TOKEN_STREAM));
        factory.configure(config);
        factory.enableLogging(logger);
        factory.setResourceFinder(finder);

        return factory;
    }

    /**
     * Create a new typesetter.
     * <p>
     * This method can be overwritten in derived classes to perform additional
     * tasks. In this case it might be a good idea to invoke this method from
     * the super-class to do its job.
     * </p>
     * 
     * <pre>
     *   {@link #makeBackend(Configuration, OutputStreamFactory, DocumentWriterOptions, ResourceFinder, CoreFontFactory) makeBackend()}
     *     {@link #makeColorConverter(Configuration) makeColorConverter()}
     * </pre>
     * 
     * 
     * @param interpreter the interpreter
     * @param config the global configuration object
     * @param outFactory the output stream factory
     * @param finder the resource finder
     * @param fontFactory the font factory
     * 
     * @return the new typesetter
     * 
     * @throws ConfigurationException in case that some kind of problems have
     *         been detected in the configuration
     * @throws TypesetterException in case of an error
     * @throws CatcodeException in case of a problem with catcodes
     * @throws DocumentWriterException just in case
     */
    protected Typesetter makeTypesetter(Interpreter interpreter,
            Configuration config, OutputStreamFactory outFactory,
            ResourceFinder finder, CoreFontFactory fontFactory)
            throws TypesetterException,
                CatcodeException,
                DocumentWriterException {

        Context context = interpreter.getContext();

        BackendDriver backend = makeBackend(//
            config.getConfiguration("Backend"), //
            outFactory, //
            (DocumentWriterOptions) context, //
            finder, //
            fontFactory);

        TypesetterFactory factory = new TypesetterFactory();
        factory.configure(config.getConfiguration("Typesetter"));
        factory.enableLogging(logger);
        Typesetter typesetter =
                factory.newInstance(
                    properties.getProperty(PROP_TYPESETTER_TYPE), context,
                    backend, interpreter);

        return typesetter;
    }

    /**
     * Set a property to a given value if not set yet.
     * 
     * @param name the name of the property
     * @param value the default value
     */
    protected void propertyDefault(String name, String value) {

        if (!properties.containsKey(name) && value != null) {
            properties.setProperty(name, value);
        }
    }

    /**
     * Run the program with the parameters already stored in the properties.
     * <p>
     * The whole work is performed in several steps. Those steps are represented
     * as protected methods. Thus a derived class can intercept the process by
     * overwriting some of these methods. The follow steps are performed in the
     * sequence given:
     * </p>
     * 
     * <pre>
     * {@link #makeLogFile(String) makeLogFile()}
     * {@link #makeLogHandler(File) makeLogHandler()}
     * {@link #showBanner(Configuration, Level) showBanner()}
     * {@link #makeOutputFactory(String, Configuration) makeOutputFactory()}
     * {@link #makeResourceFinder(Configuration) makeResourceFinder()}
     * {@link #makeInterpreter(Configuration, OutputStreamFactory, ResourceFinder, String) makeInterpreter()}
     *   {@link #makeFontFactory(Configuration, ResourceFinder) makeFontFactory()}
     *   {@link #makeTokenStreamFactory(Configuration, ResourceFinder) makeTokenStreamFactory()}
     *   {@link #makeTokenFactory(Configuration) makeTokenFactory()}
     *   {@link #makeContext(Configuration, TokenFactory, CoreFontFactory, Interpreter, ResourceFinder, String, OutputStreamFactory) makeContext()}
     *     {@link #makeDefaultFont(Configuration, CoreFontFactory) makeDefaultFont()}
     *     {@link #makeLanguageManager(Configuration, OutputStreamFactory, ResourceFinder) makeLanguageManager()}
     *     {@link #loadFormat(String, Interpreter, ResourceFinder, String, Configuration, OutputStreamFactory, TokenFactory) loadFormat()}
     *     {@link #makePageSize(Context) makePageSize()}
     *   {@link #initializeStreams(Interpreter, Properties) initializeStreams()}
     *   {@link #makeTypesetter(Interpreter, Configuration, OutputStreamFactory, ResourceFinder, CoreFontFactory) makeTypesetter()}
     *     {@link #makeBackend(Configuration, OutputStreamFactory, DocumentWriterOptions, ResourceFinder, CoreFontFactory) makeBackend()}
     *       {@link #makeColorConverter(Configuration) makeColorConverter()}
     * </pre>
     * 
     * @return the interpreter used for running
     * 
     * @throws IOException in case of a reading error<br>
     *         Especially <br>
     *         CharacterCodingException in case of problems with the character
     *         encoding
     * @throws ConfigurationException in case of a configuration error
     * @throws InterpreterException in case of another error<br>
     *         Especially <br>
     *         ErrorLimitException in case that the error limit has been reached
     */
    public Interpreter run() throws IOException, InterpreterException {
        final String jobname = determineJobname();
        final File logFile = makeLogFile(jobname);
        Handler logHandler = null;

        try {
            logHandler = makeLogHandler(logFile);

            final Configuration config = ConfigurationFactory.newInstance(
                properties.getProperty( PROP_CONFIG ) );
            showBanner(config, (showBanner ? Level.INFO : Level.FINE));

            final Interpreter interpreter =
                    makeInterpreter(
                        config,
                        makeOutputFactory(jobname,
                            config.getConfiguration("Output")),
                        makeResourceFinder(config.getConfiguration("Resource")),
                        jobname);

            interpreter.run();

            logPages(interpreter.getTypesetter().getBackendDriver());

            return interpreter;

        } catch ( ConfigurationException | IOException e) {
            logger.throwing(this.getClass().getName(), "run", e);
            throw e;
        } catch (Exception e) {
            throw logError(e);
        } catch (OutOfMemoryError e) {
            System.gc();
            throw new InterpreterException("OOM");
        } catch (Throwable e) {
            logInternalError(e);
            if (getBooleanProperty(PROP_INTERNAL_STACKTRACE)) {
                e.printStackTrace(System.err);
            }
        } finally {
            if (logHandler != null) {
                logHandler.close();
                logger.removeHandler(logHandler);
                // see "TeX -- The Program [1333]"
                logger.log((noBanner ? Level.FINE : Level.INFO), //
                    localizer.format("ExTeX.Logfile", logFile));
            }
        }
        return null;
    }

    /**
     * Setter for errorHandler.
     * 
     * @param handler the errorHandler to set.
     */
    public void setErrorHandler(ErrorHandler handler) {

        errorHandler = handler;
    }

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set.
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Setter for outStream.
     * 
     * @param outputStream the outStream to set.
     */
    public void setOutStream(OutputStream outputStream) {

        this.outStream = outputStream;
    }

    /**
     * Setter for a named property.
     * 
     * @param key the property name
     * @param value the new value of the named property
     */
    protected void setProperty(String key, String value) {

        properties.setProperty(key, value);
    }

    /**
     * Print the program banner to the logger stream and remember that this has
     * been done already to avoid repetition. The property
     * <tt>extex.nobanner</tt> can be used to suppress the banner.
     * 
     * @param configuration the configuration to use. The configuration may
     *        contain a tag <tt>banner</tt>. If present then the value is used.
     *        The configuration can be <code>null</code>. Then it is ignored.
     * @param priority the log level
     */
    protected void showBanner(Configuration configuration, Level priority) {

        if (showBanner) {
            showBanner = false;
            if (getBooleanProperty(PROP_NO_BANNER)) {
                return;
            }

            String banner;
            if (configuration != null) {
                try {
                    banner =
                            configuration.getConfiguration("banner").getValue();
                } catch (ConfigurationException e) {
                    banner = properties.getProperty(PROP_BANNER);
                }
            } else {
                banner = properties.getProperty(PROP_BANNER);
            }

            String name = properties.getProperty(PROP_NAME);
            logger.log(priority, localizer.format("ExTeX.Version", //
                name, //
                EXTEX_VERSION, //
                banner));
        }
    }

}
