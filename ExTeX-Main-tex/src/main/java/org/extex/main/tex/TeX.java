/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.main.tex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.ExTeX;
import org.extex.backend.BackendDriver;
import org.extex.backend.documentWriter.DocumentWriterOptions;
import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.NamedOutputStream;
import org.extex.backend.outputStream.OutputFactory;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.outputStream.OutputStreamObserver;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.NotObservableException;
import org.extex.font.CoreFontFactory;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationUnsupportedEncodingException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.interaction.InteractionUnknownException;
import org.extex.interpreter.observer.pop.PopObservable;
import org.extex.interpreter.observer.pop.PopObserver;
import org.extex.interpreter.observer.push.PushObservable;
import org.extex.interpreter.observer.push.PushObserver;
import org.extex.interpreter.observer.streamClose.StreamCloseObservable;
import org.extex.interpreter.observer.streamClose.StreamCloseObserver;
import org.extex.logging.LogFormatter;
import org.extex.main.tex.exception.MainCodingException;
import org.extex.main.tex.exception.MainConfigurationException;
import org.extex.main.tex.exception.MainException;
import org.extex.main.tex.exception.MainIOException;
import org.extex.main.tex.exception.MainMissingArgumentException;
import org.extex.main.tex.exception.MainUnknownDebugOptionException;
import org.extex.main.tex.exception.MainUnknownOptionException;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.stream.observer.file.OpenFileObservable;
import org.extex.scanner.stream.observer.file.OpenFileObserver;

/**
 * This is the command line interface to <logo>ExTeX</logo>. It does all the
 * horrible details necessary to interact with the user of the command line in
 * nearly the same way as <logo>TeX</logo> does.
 * <p>
 * The command line interface provides the following features:
 * </p>
 * <ul>
 * <li>Specifying format, input file, and <logo>TeX</logo> code on the command
 * line.</li>
 * <li>Interacting with the user to get an input file.</li>
 * <li>Interacting with the user in case on an error</li>
 * </ul>
 * 
 * <h3>ExTeX: Command Line Usage</h3>
 * 
 * <p>
 * This program is normally used through a wrapper which performs all necessary
 * initializations and hides the implementation language from the casual user.
 * Since this is the default case it is described here first. Details about the
 * direct usage without the wrapper can be found in section <a
 * href="#invocation">Direct Java Invocation</a>.
 * </p>
 * <p>
 * This program &ndash; called <tt>extex</tt> here &ndash; has in its simplest
 * form of invocation one parameter. This parameter is the name of the file to
 * process:
 * </p>
 * 
 * <pre class="CLISample">
 *   extex file.tex </pre>
 * 
 * <p>
 * The input file is sought in the current directory and other locations.
 * Details about searching can be found in <a href="#fileSearch">Searching TeX
 * Files</a>.
 * </p>
 * <p>
 * In general the syntax of invocation is as follows:
 * </p>
 * 
 * <pre class="CLIsyntax">
 *   extex &lang;options&rang; &lang;file&rang; &lang;code&rang; </pre>
 * 
 * <p>
 * Command line parameters are the way of setting options with highest priority.
 * The command line parameters overrule all settings in other parameter files.
 * The command line options are contained in the table below.
 * </p>
 * 
 * <dl>
 * <dt><tt>&lang;code&rang;</tt></dt>
 * <dd> This parameter contains <logo>ExTeX</logo> code to be executed
 * directly. The execution is performed after any code specified in an input
 * file. on the command line the code has to start with a backslash. This
 * restriction does not hold for the property settings. </dd>
 * <dd>Property: <tt><a href="#extex.code">extex.code</a></tt></dd>
 * 
 * <dt><tt>&lang;file&rang;</tt></dt>
 * <dd> This parameter contains the file to read from. A file name may not start
 * with a backslash or an ampercent. It has no default.<br />
 * Any arguments after the file name are treated as code to be executed when the
 * file has been processed. </dd>
 * <dd>Property: <a href="#extex.file"><tt>extex.file</tt></a></dd>
 * 
 * <dt><tt>-- &lang;file&rang;</tt></dt>
 * <dd> This parameter contains the file to read from. A file name may start
 * with any character since it is protected by the prefix <tt>--</tt>. The
 * file name has no default.<br />
 * Any arguments after the file name are treated as code to be executed when the
 * file has been processed. </dd>
 * <dd>Property: <a href="#extex.file"><tt>extex.file</tt></a></dd>
 * 
 * <dt><tt>--&lang;property&rang; &lang;value&rang;</tt></dt>
 * <dd>
 * <p>
 * The properties mentioned throughout this description can be set directly. It
 * is even possible to set a property with any name to a value. The name is not
 * checked checked against a list. Thus it is possible to overwrite system and
 * user settings.
 * </p>
 * <p>
 * For instance the properties <tt>user.name</tt> and <tt>java.version</tt>
 * are used at some places, but can not by set on the command line by other
 * means.
 * </p>
 * <p>
 * Example: The following invocations are identical:
 * 
 * <pre>
 *      extex --extex.file=abc
 *      extex --extex.file abc
 *      extex -- abc
 *      extex abc </pre>
 * 
 * </p>
 * </dd>
 * 
 * <dt><a name="-configuration"><tt>-configuration &lang;resource&rang;</tt></a>
 * <br />
 * <tt>-configuration=&lang;resource&rang;</tt></a> </dt>
 * <dd> This parameter contains the name of the configuration resource to use.
 * This configuration resource is sought on the class path. </dd>
 * <dd>Property: <tt><a href="#extex.config">extex.config</a></tt></dd>
 * 
 * <dt><a name="-copyright"><tt>-copyright</tt></a></dt>
 * <dd> This command line option produces a copyright notice on the standard
 * output stream and terminates the program afterwards. </dd>
 * 
 * <dt><tt>&amp;&lang;format&rang;</tt></dt>
 * <dt><a name="-fmt"><tt>-fmt &lang;format&rang;</tt></a></dt>
 * <dd> This parameter contains the name of the format to read. An empty string
 * denotes that no format should be read. This is the default. </dd>
 * <dd>Property: <tt><a href="#extex.format">extex.format</a></tt></dd>
 * 
 * <dt><a name="-debug"><tt>-debug &lang;spec&rang;</tt><br />
 * <tt>-debug=&lang;spec&rang;</tt> </a></dt>
 * <dd> This command line parameter can be used to instruct the program to
 * produce debugging output of several kinds. The specification &lang;spec&rang;
 * is interpreted left to right. Each character is interpreted according to the
 * following table: <table>
 * <th>
 * <td>Spec</td>
 * Description
 * <td></td>
 * <td>See</td>
 * </th>
 * <tr>
 * <td><tt>F</tt></td>
 * <td> This specifier contains the indicator whether or not to trace the
 * searching for input files. </td>
 * <td><tt><a href="#extex.trace.input.files">extex.trace.input.files</a></tt></td>
 * </tr>
 * <tr>
 * <td><tt>f</tt></td>
 * <td> This specifier contains the indicator whether or not to trace the
 * searching for font files. </td>
 * <td><tt><a href="#extex.trace.font.files">extex.trace.font.files</a></tt></td>
 * </tr>
 * <tr>
 * <td><tt>M</tt></td>
 * <td> This specifier contains the indicator whether or not to trace the
 * execution of macros. </td>
 * <td><tt><a href="#extex.trace.macros">extex.trace.macros</a></tt></td>
 * </tr>
 * <tr>
 * <td><tt>T</tt></td>
 * <td> This specifier contains the indicator whether or not to trace the work
 * of the tokenizer. </td>
 * <td><tt><a href="#extex.trace.tokenizer">extex.trace.tokenizer</a></tt></td>
 * </tr>
 * </table> </dd>
 * 
 * <dt><a name="-halt"><tt>-halt-on-error</tt></a></dt>
 * <dd> This parameter contains the indicator whether the processing should halt
 * after the first error has been encountered. </dd>
 * <dd>Property:
 * <tt><a href="#extex.halt.on.error">extex.halt.on.error</a></tt></dd>
 * 
 * <dt><a name="-help"><tt>-help</tt></a></dt>
 * <dd> This command line option produces a short usage description on the
 * standard output stream and terminates the program afterwards. </dd>
 * 
 * <dt><a name="-ini"><tt>-ini</tt></a></dt>
 * <dd> If set to <code>true</code> then the attempt to load a format with the
 * name derived from the program name is omitted. </dd>
 * <dd>Property: <tt><a href="#extex.ini">extex.ini</a></tt> </dd>
 * 
 * <dt><a name="-interaction"><tt>-interaction &lang;mode&rang;</tt> <br />
 * <tt>-interaction=&lang;mode&rang;</tt></a> </a></dt>
 * <dd> This parameter contains the interaction mode. Possible values are the
 * numbers 0..3 and the symbolic names batchmode (0), nonstopmode (1),
 * scrollmode (2), and errorstopmode (3). The symbolic names can be abbreviated
 * up to at least one character. </dd>
 * <dd>Property: <tt><a href="#extex.interaction">extex.interaction</a></tt></dd>
 * 
 * <dt><a name="-job"><tt>-job-name &lang;name&rang;</tt><br />
 * <tt>-job-name=&lang;name&rang;</tt></a><br />
 * <tt>-jobname &lang;name&rang;</tt></a><br />
 * <tt>-jobname=&lang;name&rang;</tt></a> </a></dt>
 * <dd> This parameter contains the name of the job. It is overwritten if a file
 * is given to read from. In this case the base name of the input file is used
 * instead. </dd>
 * <dd>Property: <tt><a href="#extex.jobname">extex.jobname</a></tt></dd>
 * 
 * <dt><a name="-language"><tt>-language &lang;language&rang;</tt><br />
 * <tt>-language=&lang;language&rang;</tt> </a></dt>
 * <dd> This parameter contains the name of the locale to be used for the
 * messages. </dd>
 * <dd>Property: <tt><a href="#extex.lang">extex.lang</a></tt> </dd>
 * 
 * <dt><a name="-output"><tt>-output &lang;format&rang;</tt><br />
 * <tt>-output=&lang;format&rang;</tt> </a></dt>
 * <dd> This parameter contains the output format. This logical name is resolved
 * via the configuration. Reasonable values are <tt>dvi</tt>, <tt>ps</tt>,
 * and <tt>pdf</tt>. </dd>
 * <dd>Property: <tt><a href="#extex.output">extex.output</a></tt></dd>
 * 
 * <dt><a name="-outputdir"><tt>-output-directory &lang;directory&rang;</tt><br />
 * <tt>-output-directory=&lang;directory&rang;</tt><br />
 * <tt>-texoutputs &lang;directory&rang;</tt><br />
 * <tt>-texoutputs=&lang;directory&rang;</tt> </a></dt>
 * <dd> This parameter contains the output directory. The normal output files
 * are tried to place there. If this fails a fallback is tried additionally.
 * </dd>
 * <dd>Property: <tt><a href="#tex.output.dir">tex.output.dir</a></tt></dd>
 * 
 * <dt><a name="-parse-first-line"/><tt>-parse-first-line</tt></a></dt>
 * <dd> This parameter can be used to force the parsing of the first line of the
 * input file. </dd>
 * <dd>Property:
 * <tt><a href="#extex.parse.first.line">extex.parse.first.line</a></tt></dd>
 * 
 * <dt><a name="-progname"/><tt>-progname &lang;name&rang;</tt><br />
 * <tt>-progname=&lang;name&rang;</tt> </a></dt>
 * <dd> This parameter can be used to overrule the name of the program shown in
 * the banner and the version information. </dd>
 * <dd>Property: <tt><a href="#extex.progname">extex.progname</a></tt></dd>
 * 
 * <dt><a name="-recorder"/><tt>-recorder</tt><br />
 * <tt>-recorder=&lang;file name&rang;</tt> </a></dt>
 * <dd> This parameter can be used to activate the file name recorder. If the
 * file name recorder is active then all files opened during the run are
 * collected and written to a file. The file name used is the jobname with the
 * extension <tt>.fls</tt> appended. If the second form of invocation is used
 * then the file name can be given.</dd>
 * <dd>Property: <tt><a href="#tex.recorder">tex.recorder</a></tt></dd>
 * 
 * <dt><a name="-texinputs"/><tt>-texinputs &lang;path&rang;</tt><br />
 * <tt>-texinputs=&lang;path&rang;</tt> </a></dt>
 * <dd> This parameter contains the additional directories for searching
 * <logo>ExTeX</logo> input files. </dd>
 * <dd>Property: <tt><a href="#extex.texinputs">extex.texinputs</a></tt>
 * </dd>
 * 
 * <dt><a name="-texmfoutputs"><tt>-texmfoutputs &lang;dir&rang;</tt><br />
 * <tt>-texmfoutputs=&lang;dir&rang;</tt> </a></dt>
 * <dd> This parameter contains the name of the property for the fallback if the
 * output directory fails to be writable. </dd>
 * <dd>Property:
 * <tt><a href="#tex.output.dir.fallback">tex.output.dir.fallback</a></tt>
 * </dd>
 * 
 * <dt><a name="-version"/><tt>-version</tt></dt>
 * <dd> This command line parameter forces that the version information is
 * written to standard output and the program is terminated. </dd>
 * </dl>
 * 
 * <p>
 * Command line parameters can be abbreviated up to a unique prefix &ndash; and
 * sometimes even more. Thus the following invocations are equivalent:
 * 
 * <pre class="CLIsyntax">
 *   extex -v
 *   extex -ve
 *   extex -ver
 *   extex -vers
 *   extex -versi
 *   extex -versio
 *   extex -version  </pre>
 * 
 * </p>
 * 
 * 
 * <a name="first-line"/>
 * <h3>First Line Parsing</h3>
 * 
 * <p>
 * The feature of first line parsing can be enabled with the command line option
 * <tt>-parse-first-line</tt> and the property <tt>extex.parse.first.line</tt>.
 * </p>
 * <p>
 * If the feature is enabled then the first line of the first input file is
 * parsed. If this line starts with <tt>%&</tt> then the next characters up to
 * a white-space are taken as format name to be loaded. The remaining characters
 * up to the newline characters are ignored.
 * </p>
 * <p>
 * The first line is simply passed to the interpreter if it does not start with
 * <tt>%&</tt>.
 * </p>
 * <p>
 * The following sample shows a first line which pre-loads the format
 * <tt>latex</tt>.
 * </p>
 * 
 * <pre class="TeX">
 *  %&latex some comment
 * </pre>
 * 
 * 
 * <a name="settings"/>
 * <h3>Settings and Command Line Parameters</h3>
 * 
 * <p>
 * Settings can be stored in properties files. Those settings are the fallback
 * values if no corresponding command line arguments are found.
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
 * <dd> This parameter contains <logo>ExTeX</logo> code to be executed
 * directly. The execution is performed after any code specified in an input
 * file. on the command line the code has to start with a backslash. This
 * restriction does not hold for the property settings. </dd>
 * <dd>Command line: <tt>&lang;code&rang;</tt></dd>
 * 
 * <dt><a name="extex.color.converter"/><tt>extex.color.converter</tt></dt>
 * <dd> This parameter contains the name of the configuration resource to use
 * for the color converter. </dd>
 * <dd>Default: <tt></tt></dd>
 * 
 * <dt><a name="extex.config"/><tt>extex.config</tt></dt>
 * <dd> This parameter contains the name of the configuration resource to use.
 * This configuration resource is sought on the class path. </dd>
 * <dd>Command line: <a href="#-configuration"><tt>-configuration &lang;resource&rang;</tt></a></dd>
 * <dd>Default: <tt>extex.xml</tt></dd>
 * 
 * <dt><a name="extex.encoding"/><tt>extex.encoding</tt></dt>
 * <dd> This parameter contains the name of the property for the standard
 * encoding to use. </dd>
 * <dd>Default: <tt>ISO-8859-1</tt></dd>
 * 
 * <dt><a name="extex.error.handler"/><tt>extex.error.handler</tt></dt>
 * <dd> This parameter contains the logical name of the error handler. </dd>
 * 
 * <dt><a name="extex.error.handler"/><tt>extex.error.handler</tt></dt>
 * <dd> This parameter contains the logical name of the error handler. </dd>
 * 
 * <dt><a name="extex.fonts"/><tt>extex.fonts</tt></dt>
 * <dd> This parameter contains the name of the property indicating where to
 * find font files. The value is a path similar to extex.texinputs. </dd>
 * 
 * <dt><a name="extex.halt.on.error"/><tt>extex.halt.on.error</tt></dt>
 * <dd> This parameter contains the name of the property indicating whether the
 * processing should stop after the first error. </dd>
 * <dd>Command line: <a href="#-halt"><tt>-halt-on-error</tt></a> </dd>
 * 
 * <dt><a name="extex.file"><tt>extex.file</tt></a></dt>
 * <dd> This parameter contains the file to read from. It has no default </dd>
 * <dd>Command line: <a href="&lang"><tt>&lang;file&rang;</tt></a> </dd>
 * 
 * <dt><a name="extex.format"/><tt>extex.format</tt></dt>
 * <dd> This parameter contains the name of the format to read. An empty string
 * denotes that no format should be read. This is the default. </dd>
 * <dd>Command line: <a href="#-fmt"><tt>-fmt &lang;format&rang;</tt></a></dd>
 * 
 * <dt><a name="extex.ini"/><tt>extex.ini</tt></dt>
 * <dd> If set to <code>true</code> then act as initex. This command line
 * option is defined for compatibility to <logo>TeX</logo> only. In <logo>ExTeX</logo>
 * it has no effect at all. </dd>
 * <dd>Command line: <a href="#-ini"><tt>-ini</tt></a> </dd>
 * 
 * <dt><a name="extex.interaction"/><tt>extex.interaction</tt></dt>
 * <dd> This parameter contains the interaction mode. possible values are the
 * numbers 0..3 and the symbolic names batchmode (0), nonstopmode (1),
 * scrollmode (2), and errorstopmode (3). </dd>
 * <dd>Command line: <a href="#-interaction"><tt>-interaction &lang;mode&rang;</tt></a></dd>
 * <dd>Default: <tt>3</tt></dd>
 * 
 * <dt><a name="extex.jobname"/><tt>extex.jobname</tt></dt>
 * <dd> This parameter contains the name of the job. It is overwritten if a file
 * is given to read from. In this case the base name of the input file is used
 * instead. </dd>
 * <dd>Command line:<a href="#-job"> <tt>-job-name &lang;name&rang;</tt></a><br>
 * <tt>-jobname &lang;name&rang;</tt></a><br>
 * <tt>-job-name=&lang;name&rang;</tt></a><br>
 * <tt>-jobname=&lang;name&rang;</tt></a></dd>
 * <dd>Default: <tt>texput</tt></dd>
 * 
 * <dt><a name="extex.jobnameMaster"/><tt>extex.jobnameMaster</tt></dt>
 * <dd> This parameter contains the name of the job to be used with high
 * priority. </dd>
 * <dd>Default: <tt>texput</tt></dd>
 * 
 * <dt><a name="extex.lang"/><tt>extex.lang</tt></dt>
 * <dd> This parameter contains the name of the locale to be used for the
 * messages. </dd>
 * <dd>Command line: <a href="#-language"><tt>-language &lang;language&rang;</tt></a>
 * </dd>
 * 
 * <dt><a name="extex.nobanner"/><tt>extex.nobanner</tt></dt>
 * <dd> This parameter contains a boolean indicating that the banner should be
 * suppressed. </dd>
 * 
 * <dt><a name="extex.output"/><tt>extex.output</tt></dt>
 * <dd> This parameter contains the output format. This logical name is resolved
 * via the configuration. </dd>
 * <dd>Command line: <a href="#-output"><tt>-output &lang;format&rang;</tt></a></dd>
 * <dd>Default: <tt>pdf</tt></dd>
 * 
 * <dt><a name="tex.output.dir"/><tt>tex.output.dir</tt></dt>
 * <dd> This parameter contains the directory where output files should be
 * created. If the directory fails to be writable then a fallback is tried
 * instead. </dd>
 * <dd>Command line: <a href="#-texoutputs"><tt>-texoutputs &lang;dir&rang;</tt></a></dd>
 * <dd>Default: <i>none</i></dd>
 * 
 * <dt><a name="tex.output.dir.fallback"/><tt>tex.output.dir.fallback</tt></dt>
 * <dd> This parameter contains the name of the property for the fallback if the
 * output directory fails to be writable. </dd>
 * <dd>Command line: <a href="#-texmfoutputs"><tt>-texmfoutputs &lang;dir&rang;</tt></a>
 * </dd>
 * 
 * <dt><a name="extex.paper"/><tt>extex.paper</tt></dt>
 * <dd> This parameter contains the default size of the paper. It can be one of
 * the symbolic names defined in <tt>paper/paper.xml</tt>. Otherwise the
 * value is interpreted as a pair of width and height separated by a space.
 * </dd>
 * 
 * <dt><a name="extex.parse.first.line"/><tt>extex.parse.first.line</tt></dt>
 * <dd> This boolean parameter controls whether the first line of the input file
 * should be parsed. If it is <code>true</code> and the first line starts with
 * <tt>%&amp;</tt> then the following characters up to a white-space character
 * are taken as the name of a format to be loaded. </dd>
 * 
 * <dt><a name="extex.progname"/><tt>extex.progname</tt></dt>
 * <dd> This parameter can be used to overrule the name of the program shown in
 * the banner and the version information. </dd>
 * <dd>Command line: <a href="#-progname"><tt>-progname</tt></a></dd>
 * <dd>Default: <tt>ExTeX</tt></dd>
 * 
 * <dt><a name="tex.recorder"/><tt>tex.recorder</tt></dt>
 * <dd> This parameter can be used to activate the file name recorder. If the
 * file name recorder is active then all files opened during the run are
 * collected and written to a file. The file name is stored in this parameter.
 * If the file name is <code>null</code> the no file is written. If the file
 * name is empty then the output is written to the standard output stream.
 * Otherwise the file name is opened fro writing. </dd>
 * <dd>Command line: <a href="#-recorder"><tt>-recorder</tt></a></dd>
 * <dd>Default: none</dd>
 * 
 * <dt><a name="extex.texinputs"/><tt>extex.texinputs</tt></dt>
 * <dd> This parameter contains the additional directories for searching TeX
 * input files. </dd>
 * <dd>Command line: <a href="#-texinputs"><tt>-texinputs &lang;path&rang;</tt></a>
 * </dd>
 * 
 * <dt><a name="extex.token.stream"/><tt>extex.token.stream</tt></dt>
 * <dd> This string parameter contains the logical name of the configuration to
 * use for the token stream. </dd>
 * 
 * <dt><a name="extex.trace.input.files"/><tt>extex.trace.input.files</tt></dt>
 * <dd> This boolean parameter contains the indicator whether or not to trace
 * the search for input files. </dd>
 * 
 * <dt><a name="extex.trace.font.files"/><tt>extex.trace.font.files</tt></dt>
 * <dd> This boolean parameter contains the indicator whether or not to trace
 * the search for font files. </dd>
 * 
 * <dt><a name="extex.trace.macros"/><tt>extex.trace.macros</tt></dt>
 * <dd> This boolean parameter contains the indicator whether or not to trace
 * the execution of macros. </dd>
 * 
 * <dt><a name="extex.trace.tokenizer"/><tt>extex.trace.tokenizer</tt></dt>
 * <dd> This boolean parameter contains the indicator whether or not to trace
 * the work of the tokenizer. </dd>
 * 
 * <dt><a name="extex.typesetter"/><tt>extex.typesetter</tt></dt>
 * <dd> This parameter contains the name of the typesetter to use. If it is not
 * set then the default from the configuration file is used. </dd>
 * </dl>
 * 
 * <p>
 * There is another level of properties which is considered between the compiled
 * in defaults and the user's Those are the system properties of the Java
 * system. There system wide settings can be stored. Nevertheless, you should
 * use this feature sparsely.
 * </p>
 * 
 * 
 * 
 * <a name="invocation"/>
 * <h3>Direct Java Invocation</h3>
 * 
 * <p>
 * The direct invocation of the Java needs some settings to be preset. These
 * settings are needed for <logo>ExTeX</logo> to run properly. The following
 * premises are needed:
 * </p>
 * <ul>
 * <li>Java needs to be installed (see section <a
 * href="#installation">Installation</a>. The program <tt>java</tt> is
 * assumed to be on the path of executables. </li>
 * <li>Java must be configured to find the jar files from the ExTeX
 * distribution. This can be accomplished by setting the environment variable
 * <tt>CLASSPATH</tt> or <tt>JAVA_HOME</tt>. See the documentation of your
 * Java system for details. </li>
 * </ul>
 * <p>
 * Now <logo>ExTeX</logo> can be invoked with the same parameters described
 * above:
 * </p>
 * 
 * <pre class="CLIsyntax">
 *   java org.extex.ExTeX &lang;options&rang; &lang;file&rang; </pre>
 * 
 * <p>
 * The result should be the same as the invocation of the wrapper.
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * 
 * @version $Revision$
 */
public class TeX extends ExTeX {

    /**
     * This inner class contains the open file observer.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private final class Observer implements OpenFileObserver {

        /**
         * The field <tt>finder</tt> contains the resource finder.
         */
        private ResourceFinder finder;

        /**
         * The field <tt>first</tt> contains the indicator of the first visit.
         */
        private boolean first = true;

        /**
         * Creates a new object.
         * 
         * @param finder the resource finder
         */
        public Observer(ResourceFinder finder) {

            this.finder = finder;
        }

        /**
         * This method is meant to be invoked just after a new file based stream
         * has been opened.
         * 
         * @param filename the name of the file to be opened
         * @param filetype the type of the file to be opened. The type is
         *        resolved via the configuration to a file name pattern
         * @param stream the input stream to read from
         * 
         * @see org.extex.scanner.stream.observer.file.OpenFileObserver#update(
         *      java.lang.String, java.lang.String, java.io.InputStream)
         */
        public void update(String filename, String filetype, InputStream stream) {

            if ("tex".equals(filetype) && first) {
                first = false;
                if (stream.markSupported()) {
                    stream.mark(8);
                    try {
                        if (stream.read() == '%' && stream.read() == '&') {
                            StringBuffer fmt = new StringBuffer();
                            int c;
                            for (c = stream.read(); c > 0
                                    && !Character.isWhitespace((char) c); c =
                                    stream.read()) {
                                fmt.append((char) c);
                            }
                            while (c > 0 && c != '\n') {
                                c = stream.read();
                            }
                            String format = fmt.toString();
                            if (!format.equals("")) {
                                loadFormat(format, interpreter, finder,
                                    getProperty(PROP_JOBNAME), null, null, null);
                            }
                        } else {
                            stream.reset();
                        }
                    } catch (IOException e) {
                        try {
                            stream.reset();
                        } catch (IOException e1) {
                            getLogger().throwing(TeX.class.getName(),
                                "update()", e);
                        }
                    } catch (GeneralException e) {
                        getLogger()
                            .throwing(TeX.class.getName(), "update()", e);
                    } catch (ConfigurationException e) {
                        getLogger()
                            .throwing(TeX.class.getName(), "update()", e);
                    }
                }
            }
        }
    }

    /**
     * The constant <tt>COPYRIGHT_YEAR</tt> contains the starting year of
     * development for the copyright message. This number is fixed to be the
     * year 2003 and should not be modified.
     */
    private static final int COPYRIGHT_YEAR = 2003;

    /**
     * The field <tt>DOT_EXTEX</tt> contains the name of the user properties
     * file. This file contains property settings which are read when
     * <logo>ExTeX</logo> is started.
     */
    private static final String DOT_EXTEX = ".extex";

    /**
     * The constant <tt>EXIT_INTERNAL_ERROR</tt> contains the exit code for
     * internal errors.
     */
    protected static final int EXIT_INTERNAL_ERROR = -1;

    /**
     * The constant <tt>EXIT_OK</tt> contains the exit code of the program for
     * the success case.
     */
    protected static final int EXIT_OK = 0;

    /**
     * The constant <tt>PAGE_KEYS</tt> contains the resoruce keys to report
     * pages and file.
     */
    private static final String[] PAGE_KEYS =
            new String[]{"ExTeX.NoPages", "ExTeX.Page", "ExTeX.Pages",
                    "ExTeX.NoPages", "ExTeX.File.Page", "ExTeX.File.Pages"};

    /**
     * The field <tt>PROP_OUTPUTDIR</tt> contains the name of the property
     * where the directory where output files should be created.
     */
    private static final String PROP_OUTPUT_DIR = "tex.output.dir";

    /**
     * The field <tt>PROP_OUTPUTDIR_FALLBACK</tt> contains the name of the for
     * the fallback if the output directory fails to be writable.
     */
    private static final String PROP_OUTPUT_DIR_FALLBACK =
            "tex.output.dir.fallback";

    /**
     * The field <tt>PROP_PARSE_FIRST_LINE</tt> contains the name of the
     * property to enable the parsing of the first line of the file.
     */
    private static final String PROP_PARSE_FIRST_LINE =
            "extex.parse.first.line";

    /**
     * The field <tt>PROP_RECORDER</tt> contains the name of the property to
     * control the recorder.
     */
    private static final String PROP_RECORDER = "tex.recorder";

    /**
     * The constant <tt>TRACE_MAP</tt> contains the mapping from single
     * characters to tracing property names.
     */
    private static final Map<String, String> TRACE_MAP =
            new HashMap<String, String>();

    static {
        TRACE_MAP.put("+", PROP_TRACING_ONLINE);
        TRACE_MAP.put("F", PROP_TRACE_INPUT_FILES);
        TRACE_MAP.put("f", PROP_TRACE_FONT_FILES);
        TRACE_MAP.put("M", PROP_TRACE_MACROS);
        TRACE_MAP.put("T", PROP_TRACE_TOKENIZER);
    }

    /**
     * This is the main method which is invoked to run the whole engine from the
     * command line. It creates a new <logo>ExTeX</logo> object and invokes
     * <tt>{@link #run(java.lang.String[]) run()}</tt> on it.
     * <p>
     * The return value is used as the exit status.
     * </p>
     * <p>
     * The properties to be used are taken from the
     * <tt>{@link java.lang.System#getProperties() System.properties}</tt> and
     * the user's properties in the file <tt>.extex</tt>. The user properties
     * are loaded both from the users home directory and the current directory.
     * Finally the properties can be overwritten on the command line.
     * </p>
     * 
     * @param args the list of command line arguments
     */
    public static void main(String[] args) {

        System.exit(mainProgram(args));
    }

    /**
     * This is the main method which is invoked to run the whole engine from the
     * command line. It creates a new <logo>ExTeX</logo> object and invokes
     * <tt>{@link #run(java.lang.String[]) run()}</tt> on it.
     * <p>
     * The return value is the exit status. The value 0 indicates an successful
     * run.
     * </p>
     * <p>
     * The properties to be used are taken from the
     * <tt>{@link java.lang.System#getProperties() System.properties}</tt> and
     * the user's properties in the file <tt>.extex</tt>. The user properties
     * are loaded both from the users home directory and the current directory.
     * Finally the properties can be overwritten on the command line.
     * </p>
     * 
     * @param args the list of command line arguments
     * 
     * @return the program exit status
     */
    public static int mainProgram(String[] args) {

        try {

            return new TeX(System.getProperties(), DOT_EXTEX).run(args);

        } catch (Exception e) {
            Logger logger = Logger.getLogger(TeX.class.getName());
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);

            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new LogFormatter());
            consoleHandler.setLevel(Level.WARNING);
            logger.addHandler(consoleHandler);

            logException(logger, LocalizerFactory.getLocalizer(TeX.class)
                .format("ExTeX.SevereError", e.toString()), e);
            return EXIT_INTERNAL_ERROR;
        }
    }

    /**
     * The field <tt>fileRecorder</tt> contains the observer used to record
     * all opened files.
     */
    private FileRecorder fileRecorder = null;;

    /**
     * The field <tt>interpreter</tt> contains the interpreter. This is an
     * intermediate variable used to transport the interpreter to places where
     * it is needed.
     */
    private Interpreter interpreter;

    /**
     * The field <tt>localizer</tt> contains the localizer.
     */
    private Localizer localizer = LocalizerFactory.getLocalizer(TeX.class);

    /**
     * The field <tt>observers</tt> contains the observers.
     * <p>
     * NOTE: if weak references are used then the instances have to be kept in
     * some variables to avoid that the garbage collector does its work.
     * </p>
     */
    private List<Object> observers = new ArrayList<Object>();

    /**
     * The field <tt>primaryFile</tt> contains the ...
     */
    private String primaryFile = null;

    /**
     * The field <tt>queryFileHandler</tt> contains the instance of the
     * handler to ask for a file name if none is given.
     */
    private QueryFileHandler queryFileHandler = new QueryFileHandlerTeXImpl();

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
     * @throws InterpreterException in case of an invalid interaction mode
     * @throws IOException in case of an IO Error during the reading of the
     *         properties file
     * 
     * @see org.extex.ExTeX#ExTeX(java.util.Properties, java.lang.String)
     */
    public TeX(Properties theProperties, String dotFile)
            throws InterpreterException,
                IOException {

        super(theProperties, dotFile);
        propertyDefault(PROP_PARSE_FIRST_LINE, "");
    }

    /**
     * Print the copying file.
     * 
     * @param printStream the stream to print to
     * 
     * @return the exit code
     * 
     * @throws IOException in case of an IO error
     */
    private int copying(PrintStream printStream) throws IOException {

        String file =
                this.getClass().getName().replace('.', '/').replaceAll(
                    "[a-z0-9_A-Z]+$", "LICENSE.txt");
        InputStream stream =
                getClass().getClassLoader().getResourceAsStream(file);
        if (stream == null) {
            printStream.println(file + ": resource not found");
            return EXIT_INTERNAL_ERROR;
        }
        try {
            int c;
            while ((c = stream.read()) >= 0) {
                printStream.print((char) c);
            }
        } finally {
            stream.close();
        }
        return EXIT_OK;
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
     * Getter for queryFileHandler.
     * 
     * @return the queryFileHandler
     */
    public QueryFileHandler getQueryFileHandler() {

        return this.queryFileHandler;
    }

    /**
     * Log some message with the info level priority.
     * 
     * @return the exit code
     * 
     * @param message the message to log
     */
    private int info(String message) {

        getLogger().info(message);
        return EXIT_OK;
    }

    /**
     * Initialize the input streams. If the property <i>extex.file</i> is set
     * and not the empty string, (e.g. from the command line) then this value is
     * used as file name to read from. If the property <i>extex.code</i> is set
     * and not the empty string (e.g. from the command line) then this value is
     * used as initial input after the input file has been processed. Finally,
     * if everything before failed then read input from the stdin stream.
     * 
     * @param interpreter the interpreter context
     * @param properties the controlling properties
     * 
     * @return <code>true</code> if the stream have not been initialized
     * 
     * @throws ConfigurationException in case of a configuration error
     * 
     * @see org.extex.ExTeX#initializeStreams(
     *      org.extex.interpreter.Interpreter, java.util.Properties)
     */
    protected boolean initializeStreams(Interpreter interpreter,
            Properties properties) {

        TokenStreamFactory factory = interpreter.getTokenStreamFactory();

        this.interpreter = interpreter;

        try {
            interpreter.addStream(factory
                .newInstance(new TeXInputReader(getLogger(), properties
                    .getProperty(PROP_ENCODING), interpreter)));
        } catch (UnsupportedEncodingException e) {
            throw new ConfigurationUnsupportedEncodingException(properties
                .getProperty(PROP_ENCODING), "<stdin>");
        }

        super.initializeStreams(interpreter, properties);

        return false;
    }

    /**
     * Write the indicator of the pages produced to the logger.
     * 
     * @param backend the back-end driver
     * 
     * @see org.extex.ExTeX#logPages(org.extex.backend.BackendDriver)
     */
    protected void logPages(BackendDriver backend) {

        int pages = backend.getPages();

        if (pages == 0 && primaryFile != null) {
            new File(primaryFile).delete();
            primaryFile = null;
        }

        String pattern =
                PAGE_KEYS[(pages < 2 ? pages : 2)
                        + (primaryFile == null ? 0 : 3)];
        getLogger().log(
            (getBooleanProperty(PROP_NO_BANNER) ? Level.FINE : Level.INFO),
            localizer.format(pattern, primaryFile, //
                Integer.toString(pages)));
    }

    /**
     * Create a new document writer.
     * 
     * @param config the configuration object for the document writer
     * @param outFactory the output factory
     * @param options the options to be passed to the document writer
     * @param colorConfig the configuration for the color converter
     * @param finder the resource finder if one is requested
     * @param fontFactory the font factory
     * 
     * @return the new document writer
     * 
     * @throws DocumentWriterException in case of an error
     * @throws ConfigurationException in case of a configuration problem
     * 
     * @see org.extex.ExTeX#makeBackend(
     *      org.extex.framework.configuration.Configuration,
     *      org.extex.backend.outputStream.OutputStreamFactory,
     *      org.extex.backend.documentWriter.DocumentWriterOptions,
     *      org.extex.framework.configuration.Configuration,
     *      org.extex.resource.ResourceFinder, CoreFontFactory)
     */
    protected BackendDriver makeBackend(Configuration config,
            OutputStreamFactory outFactory, DocumentWriterOptions options,
            Configuration colorConfig, ResourceFinder finder,
            CoreFontFactory fontFactory)
            throws DocumentWriterException,
                ConfigurationException {

        outFactory.register(new OutputStreamObserver() {

            /**
             * Recognize that a new output stream has been delivered.
             * 
             * @param name the name of the resource requested
             * @param type the type of the resource
             * @param stream the stream to be delivered
             * 
             * @see org.extex.backend.outputStream.OutputStreamObserver#update(
             *      java.lang.String, java.lang.String, java.io.OutputStream)
             */
            public void update(String name, String type, OutputStream stream) {

                if (primaryFile != null) {
                    // ignore
                } else if (stream instanceof NamedOutputStream) {
                    primaryFile = ((NamedOutputStream) stream).getName();
                } else if (stream != null) {
                    primaryFile = name;
                }
            }
        });

        return super.makeBackend(config, outFactory, options, colorConfig,
            finder, fontFactory);
    }

    /**
     * Create a new interpreter.
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
     * 
     * @see org.extex.ExTeX#makeInterpreter(
     *      org.extex.framework.configuration.Configuration,
     *      org.extex.backend.outputStream.OutputStreamFactory,
     *      org.extex.resource.ResourceFinder, java.lang.String)
     */
    protected Interpreter makeInterpreter(Configuration config,
            OutputStreamFactory outFactory, ResourceFinder finder,
            String jobname)
            throws ConfigurationException,
                GeneralException,
                FontException,
                IOException {

        interpreter =
                super.makeInterpreter(config, outFactory, finder, jobname);
        Logger logger = getLogger();

        interpreter.getContext().setStandardTokenStream(
            interpreter.getTokenStreamFactory().newInstance(
                new InputStreamReader(System.in)));

        if (fileRecorder != null && interpreter instanceof OpenFileObservable) {
            ((OpenFileObservable) interpreter).registerObserver(fileRecorder);
        }

        if (interpreter instanceof StreamCloseObservable) {
            StreamCloseObserver observer = new FileCloseObserver(logger);
            ((StreamCloseObservable) interpreter).registerObserver(observer);
            observers.add(observer);
        }
        if (getBooleanProperty(PROP_TRACE_TOKENIZER)) {

            if (interpreter instanceof PopObservable) {
                PopObserver observer = new TokenObserver(logger);
                ((PopObservable) interpreter).registerObserver(observer);
                observers.add(observer);
            }
            if (interpreter instanceof PushObservable) {
                PushObserver observer = new TokenPushObserver(logger);
                ((PushObservable) interpreter).registerObserver(observer);
                observers.add(observer);
            }
        }
        if (getBooleanProperty(PROP_TRACE_MACROS)) {
            interpreter.getContext().setCount("tracingcommands", 1, true);
        }

        return interpreter;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ExTeX#makeOutputFactory(java.lang.String,
     *      org.extex.framework.configuration.Configuration)
     */
    protected OutputFactory makeOutputFactory(String jobname,
            Configuration config) {

        OutputFactory outputFactory = super.makeOutputFactory(jobname, config);

        if (getProperty(PROP_RECORDER) != null) {
            fileRecorder = new FileRecorder();
            outputFactory.register(fileRecorder);
            fileRecorder.setOutputFactory(outputFactory);
        }
        return outputFactory;
    }

    /**
     * Create a TokenStreamFactory. Implicitly the logger is used.
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
            ResourceFinder finder)
            throws ConfigurationException,
                NotObservableException {

        TokenStreamFactory factory =
                super.makeTokenStreamFactory(config, finder);
        factory.registerObserver(new FileOpenObserver(getLogger()));

        if (!"".equals(getProperty(PROP_FILE))
                && getBooleanProperty(PROP_PARSE_FIRST_LINE)) {
            factory.registerObserver(new Observer(finder));
        }

        return factory;
    }

    /**
     * Loads a properties file into the already existing properties. The values
     * from the file overwrite existing values.
     * 
     * @param arg the name of the resource to load
     * 
     * @return <code>true</code> iff the resource has been loaded successfully
     * 
     * @throws IOException just in case
     */
    protected boolean mergeProperties(String arg) throws IOException {

        InputStream in = getClass().getResourceAsStream("/config/extex/" + arg);
        if (in == null) {
            File file = new File(".extex-" + arg);
            if (!file.canRead()) {
                return false;
            }
            try {
                in = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                return false; // this should not happen since checked before
            }
        }
        getProperties().load(in);
        in.close();
        return true;
    }

    /**
     * Call the recorder at the end of the run to write out its collected list.
     */
    private void recorderStop() {

        String recorder = getProperty(PROP_RECORDER);
        if (fileRecorder == null) {
            return;
        }
        try {
            if ("-".equals(recorder)) {
                recorder = null;
            } else if ("".equals(recorder)) {
                recorder = getProperty(PROP_JOBNAME);
            }
            fileRecorder.print(recorder);
        } catch (IOException e) {
            // ignored on purpose
        } catch (DocumentWriterException e) {
            // ignored on purpose
        }
    }

    /**
     * This method provides access to the whole functionality of <logo>ExTeX</logo>
     * on the command line. The exception is that this method does not call
     * <code>{@link System#exit(int) System.exit()}</code> but returns the
     * exit status as result.
     * 
     * @param args the list of command line arguments
     * 
     * @return the exit status
     */
    public int run(String[] args) {

        try {
            int exitStatus = runCommandLine(args);
            recorderStop();
            return exitStatus;

        } catch (MainException e) {
            showBanner(null, Level.INFO);
            Throwable x = e.getCause();
            if (!(x instanceof InterpreterException)
                    || !((InterpreterException) x).isProcessed()) {
                logException(getLogger(), e.getLocalizedMessage(), e);
            }
        } catch (InteractionUnknownException e) {
            showBanner(null, Level.INFO);
            Throwable x = e.getCause();
            if (!(x instanceof InterpreterException)
                    || !((InterpreterException) x).isProcessed()) {
                logException(getLogger(), e.getLocalizedMessage(), e);
            }
        } catch (Throwable e) {
            showBanner(null, Level.INFO);
            logInternalError(e);
            info(getLocalizer().format("ExTeX.Logfile",
                getProperty(PROP_JOBNAME)));

        }
        return EXIT_INTERNAL_ERROR;
    }

    /**
     * Run super.run() and remap the Exceptions
     * 
     * @return the interpreter instance used
     * 
     * @throws MainException in case of an error
     */
    private Interpreter runAndRemapExceptions() throws MainException {

        String out1 = getProperty(PROP_OUTPUT_DIR);
        String out2 = getProperty(PROP_OUTPUT_DIR_FALLBACK);

        if (out1 != null) {
            if (out2 != null) {
                setProperty(PROP_OUTPUT_DIRS, out1 + ":" + out2);
            } else {
                setProperty(PROP_OUTPUT_DIRS, out1 + ":.");
            }
        } else if (out2 != null) {
            setProperty(PROP_OUTPUT_DIRS, ".:" + out2);
        }

        if (!getBooleanProperty(PROP_INI) && getProperty(PROP_FMT).equals("")) {
            setProperty(PROP_FMT, getProperty(PROP_PROGNAME));
        }

        try {

            return run();

        } catch (CharacterCodingException e) {
            throw new MainCodingException(e);
        } catch (ConfigurationException e) {
            throw new MainConfigurationException(e);
        } catch (IOException e) {
            throw new MainIOException(e);
        } catch (GeneralException e) {
            throw new MainException(e);
        }
    }

    /**
     * Process the command line arguments.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code
     * 
     * @throws MainException in case of an error
     * @throws IOException in case of an IO error
     * @throws InteractionUnknownException in case of an unknown interaction
     *         mode
     */
    protected int runCommandLine(String[] args)
            throws MainException,
                IOException,
                InteractionUnknownException {

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals("")) {
                // silently ignored as TeXk does
                continue;
            }
            int c = arg.charAt(0);

            if (c == '-') {
                if (arg.equals("-")) {
                    // extex - <file>
                    return runWithFile(args, i + 1);
                }
                arg = arg.substring(1);
                switch (arg.charAt(0)) {
                    case '-':
                        String a;
                        if (!"-".equals(arg)) {
                            // extex --<property>...
                            a = arg.substring(1);
                        } else if (++i < args.length) {
                            // extex -- <property>...
                            a = args[i];
                        } else {
                            // extex --
                            throw new MainMissingArgumentException("--");
                        }
                        int eq = a.indexOf('=');
                        if (eq >= 0) {
                            // extex -- <property>=<value>
                            setProperty(a.substring(0, eq), a.substring(eq + 1));
                        } else if (++i < args.length) {
                            // extex -- <property> <value>
                            setProperty(a, args[i]);
                        } else {
                            // extex -- <property>
                            throw new MainMissingArgumentException("-" + arg);
                        }
                        break;
                    case 'c':
                        if (set("configuration", PROP_CONFIG, args, i)) {
                            // extex -configuration <configuration>
                            i++;
                        } else if (set("configuration", PROP_CONFIG, arg)) {
                            // extex -configuration=<configuration>
                        } else if ("copyright".startsWith(arg)) {
                            // extex -copyright
                            int year =
                                    Calendar.getInstance().get(Calendar.YEAR);
                            String copyrightYear =
                                    (year <= COPYRIGHT_YEAR ? Integer
                                        .toString(COPYRIGHT_YEAR) : Integer
                                        .toString(COPYRIGHT_YEAR)
                                            + "-" + Integer.toString(year));
                            return info(getLocalizer().format(
                                "ExTeX.Copyright", copyrightYear));
                        } else if ("copying".startsWith(arg)) {
                            // extex -copying
                            return copying(System.err);
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'd':
                        if ("debug".startsWith(arg)) {
                            if (++i >= args.length) {
                                // extex -debug
                                throw new MainMissingArgumentException(arg);
                            }
                            // extex -debug <debug flags>
                            useTrace(args[i]);
                        } else if (arg.startsWith("debug=")
                                || arg.startsWith("debu=")
                                || arg.startsWith("deb=")
                                || arg.startsWith("de=")
                                || arg.startsWith("d=")) {
                            // extex -debug=<debug flags>
                            useTrace(arg.substring(arg.indexOf('=') + 1));
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'h':
                        if ("help".startsWith(arg)) {
                            // extex -help
                            return info(getLocalizer().format("ExTeX.Usage",
                                getProperty(PROP_PROGNAME)));
                        } else if ("halt-on-error".startsWith(arg)) {
                            // extex -halt-on-error
                            setProperty(PROP_HALT_ON_ERROR, "true");
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'f':
                        if (set("fmt", PROP_FMT, args, i)) {
                            // extex -fmt <format>
                            i++;
                        } else if (set("fmt", PROP_FMT, arg)) {
                            // extex -fmt=<format>
                            // ok
                        } else if (set("format", PROP_FMT, args, i)) {
                            // extex -format <format>
                            i++;
                        } else if (set("format", PROP_FMT, arg)) {
                            // extex -format=<format>
                            // ok
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'i':
                        if (set("interaction", PROP_INTERACTION, args, i)) {
                            // extex -interaction <mode>
                            i++;
                            applyInteraction();
                        } else if (set("interaction", PROP_INTERACTION, arg)) {
                            // extex -interaction=<mode>
                            applyInteraction();
                        } else if ("initial".startsWith(arg)) {
                            // extex -ini
                            setProperty(PROP_INI, "true");
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'j':
                        if (set("job-name", PROP_JOBNAME_MASTER, arg)
                                || set("jobname", PROP_JOBNAME_MASTER, arg)) {
                            // extex -job-name=<name>
                            // extex -jobname=<name>
                            // ok
                        } else if (set("job-name", PROP_JOBNAME_MASTER, args, i)
                                || set("jobname", PROP_JOBNAME_MASTER, args, i)) {
                            // extex -job-name <name>
                            // extex -jobname <name>
                            i++;
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'l':
                        if (set("language", PROP_LANG, args, i)) {
                            // extex -language <language>
                            i++;
                            applyLanguage();
                        } else if (set("language", PROP_LANG, arg)) {
                            // extex -language=<language>
                            applyLanguage();
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'o':
                        if (set("output", PROP_OUTPUT_TYPE, args, i)) {
                            // extex -output <type>
                            i++;
                        } else if (set("output", PROP_OUTPUT_TYPE, arg)) {
                            // extex -output=<type>
                            // ok
                        } else if (set("output-path", PROP_OUTPUT_DIRS, args, i)) {
                            // extex -output-path <type>
                            i++;
                        } else if (set("output-path", PROP_OUTPUT_DIRS, arg)) {
                            // extex -output-path=<type>
                            // ok
                        } else if (set("output-directory", PROP_OUTPUT_DIR,
                            args, i)) {
                            // extex -output-directory <path>
                            i++;
                        } else if (set("output-directory", PROP_OUTPUT_DIR, arg)) {
                            // extex -output-directory=<path>
                            // ok
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'p':
                        if (set("progname", PROP_PROGNAME, args, i)) {
                            // extex -progname <name>
                            i++;
                        } else if (set("progname", PROP_PROGNAME, arg)) {
                            // extex -progname=<name>
                            // ok
                        } else if ("parse-first-line".startsWith(arg)) {
                            // extex -parse-first-line
                            setProperty(PROP_PARSE_FIRST_LINE, "true");
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'r':
                        if (set("recorder", PROP_RECORDER, arg)) {
                            // extex -recorder=<name>
                        } else if ("recorder".startsWith(arg)) {
                            // extex -recorder
                            setProperty(PROP_RECORDER, "");
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 't':
                        if (set("texinputs", PROP_TEXINPUTS, args, i)) {
                            // extex -texinputs <path>
                            i++;
                        } else if (set("texinputs", PROP_TEXINPUTS, arg)) {
                            // extex -texinputs=<path>
                            // ok
                        } else if (set("texoutputs", PROP_OUTPUT_DIR, args, i)) {
                            // extex -texoutputs <path>
                            i++;
                        } else if (set("texoutputs", PROP_OUTPUT_DIR, arg)) {
                            // extex -texoutputs=<path>
                            // ok
                        } else if (set("texmfoutputs",
                            PROP_OUTPUT_DIR_FALLBACK, args, i)) {
                            // extex -texmfoutputs <path>
                            i++;
                        } else if (set("texmfoutputs",
                            PROP_OUTPUT_DIR_FALLBACK, arg)) {
                            // extex -texmfoutputs=<path>
                            // ok
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    case 'v':
                        if ("version".startsWith(arg)) {
                            // extex -version
                            showBanner(null, Level.INFO);
                            return EXIT_OK;
                        } else if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                        break;
                    default:
                        if (!mergeProperties(arg)) {
                            throw new MainUnknownOptionException(arg);
                        }
                }
            } else if (c == '&') {
                // extex &<format>
                setProperty(PROP_FMT, arg.substring(1));
            } else if (c == '\\') {
                // extex \<code>
                applyLanguage();
                return runWithCode(args, i);
            } else {
                // extex <file>
                applyLanguage();
                return runWithFile(args, i);
            }
        }

        // extex
        applyLanguage();
        return runWithoutFile();
    }

    /**
     * The command line is processed starting at an argument which starts with a
     * backslash or follows a file name argument. This argument and any
     * following arguments are taken as input to the tokenizer.
     * 
     * @param arguments the list of arguments to process
     * @param position starting index
     * 
     * @return the exit code
     * 
     * @throws MainException in case of an error in {@link #run() run()}
     */
    private int runWithCode(String[] arguments, int position)
            throws MainException {

        if (position < arguments.length) {
            StringBuffer in = new StringBuffer();

            for (int i = position; i < arguments.length; i++) {
                in.append(" ");
                in.append(arguments[i]);
            }

            setProperty(PROP_CODE, in.toString());
        }

        runAndRemapExceptions();
        return EXIT_OK;
    }

    /**
     * Process the command line arguments when the i<sup>th</sup> argument is
     * a file name. The file is prepared to be read from. The remaining
     * arguments are used as input to the processor.
     * 
     * @param arguments the list of arguments to process
     * @param position starting index
     * 
     * @return the exit code
     * 
     * @throws MainException in case of an error
     * @throws InteractionUnknownException if the interaction from the
     *         properties is unknown
     */
    private int runWithFile(String[] arguments, int position)
            throws MainException,
                InteractionUnknownException {

        if (position >= arguments.length) {
            return runWithoutFile();
        }

        setInputFileName(arguments[position]);
        return runWithCode(arguments, position + 1);
    }

    /**
     * Ask the query file handler to provide a file name and use it.
     * 
     * @return the exit code
     * 
     * @throws MainException in case of an error
     * @throws InteractionUnknownException if the interaction from the
     *         properties is unknown
     */
    private int runWithoutFile()
            throws MainException,
                InteractionUnknownException {

        if (!Interaction.get(getProperty(PROP_INTERACTION)).equals(
            Interaction.ERRORSTOPMODE)) {
            return EXIT_INTERNAL_ERROR;
        }

        try {
            showBanner(new ConfigurationFactory()
                .newInstance(getProperty(PROP_CONFIG)), Level.INFO);
        } catch (ConfigurationException e) {
            showBanner(null, Level.INFO);
        }

        QueryFileHandler queryHandler = getQueryFileHandler();
        setInputFileName((queryHandler != null //
                ? queryHandler.query(getLogger(), getProperties())
                : null));

        runAndRemapExceptions();
        return EXIT_OK;
    }

    /**
     * Parse a command line parameter of the form <i>key</i><tt>=</tt><i>value</i>
     * and assign the value to a property with a given name.
     * 
     * @param name the command line parameter in its longest form
     * @param tag the name of the property
     * @param value the command line parameter including the value
     * 
     * @return <code>true</code> iff the syntax is correct and the value of
     *         the property has been set
     */
    private boolean set(String name, String tag, String value) {

        int i = value.indexOf('=');
        if (i < 0 || !name.startsWith(value.substring(0, i))) {
            return false;
        }
        setProperty(tag, value.substring(i + 1));
        return true;
    }

    /**
     * Acquire the next argument from the command line and set a property
     * accordingly. If none is found then an exception is thrown.
     * 
     * @param name the name of the argument
     * @param tag the name of the property to set
     * @param arguments the list of arguments
     * @param position the starting index
     * 
     * @return <code>true</code> iff the syntax is correct and the value of
     *         the property has been set
     * 
     * @throws MainMissingArgumentException in case of an error
     */
    protected boolean set(String name, String tag, String[] arguments,
            int position) throws MainMissingArgumentException {

        if (!name.startsWith(arguments[position].substring(1))) {
            return false;
        } else if (position >= arguments.length - 1) {
            throw new MainMissingArgumentException(tag);
        }

        setProperty(tag, arguments[position + 1]);
        return true;
    }

    /**
     * Setter for the input file name.
     * 
     * @param name the name of the input file. If it is <code>null</code> then
     *        the values are reset to the initial state
     */
    private void setInputFileName(String name) {

        if (name != null) {
            setProperty(PROP_JOBNAME, //
                (name.matches(".*\\.[a-zA-Z0-9_]*") //
                        ? name.substring(0, name.lastIndexOf("."))
                        : name));
            setProperty(PROP_FILE, name);
        } else {
            setProperty(PROP_JOBNAME, "texput");
            setProperty(PROP_FILE, "");
        }
    }

    /**
     * Setter for queryFileHandler.
     * 
     * @param queryFileHandler the queryFileHandler to set
     */
    public void setQueryFileHandler(QueryFileHandler queryFileHandler) {

        this.queryFileHandler = queryFileHandler;
    }

    /**
     * Acquire the next argument from the command line and use it as a
     * specification to control the tracing features. The appropriate properties
     * are set accordingly.
     * 
     * @param arg the argument
     * 
     * @throws MainUnknownDebugOptionException in case that the specified option
     *         letter has no assigned property to set
     */
    protected void useTrace(String arg) throws MainUnknownDebugOptionException {

        getLogger().setLevel(Level.FINE);

        for (int i = 0; i < arg.length(); i++) {
            String prop = TRACE_MAP.get(arg.substring(i, i + 1));
            if (prop != null) {
                setProperty(prop, "true");
            } else {
                throw new MainUnknownDebugOptionException(arg.substring(i,
                    i + 1));
            }
        }
    }

}