/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core;

import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.sorter.SorterFactory;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.auxio.AuxReader;
import org.extex.exbib.core.io.auxio.AuxReaderFactory;
import org.extex.exbib.core.io.bblio.BblWriterFactory;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.exbib.core.util.*;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains the assembler for εχBib.
 *
 * <h2>Parameters</h2>
 * <p>
 * The parameters to εχBib are passed in as
 * {@link Properties}. The keys and values in the properties are {@link String}
 * s. See {@link #setProperty(String, String)}. The names of some of the
 * supported parameters are provided as symbolic constants in this class:
 * </p>
 * <ul>
 * <li> {@link #PROP_BIB_ENCODING}</li>
 * <li> {@link #PROP_CONFIG}</li>
 * <li> {@link #PROP_CSF_ENCODING}</li>
 * <li> {@link #PROP_ENCODING}</li>
 * <li> {@link #PROP_FILE}</li>
 * <li> {@link #PROP_MIN_CROSSREF}</li>
 * <li> {@link #PROP_OUTFILE}</li>
 * <li> {@link #PROP_PROCESSOR}</li>
 * <li> {@link #PROP_SORT}</li>
 * </ul>
 *
 * <h2>Invocation</h2>
 * <p>
 * The tasks performed by this class can be applied with a few lines of code:
 * </p>
 *
 * <pre>
 *  ExBib exbib = new ExBib(new Properties());
 *  exbib.setFile("abc.aux");
 *  exbib.run();
 * </pre>
 *
 * <p>
 * This example uses the built-in defaults to read the data from the given aux
 * file and process it.
 * </p>
 *
 * <h2>Configuration</h2>
 * <p>
 * The method {@link #run()} reads part of the information needed for assembling
 * the application from a configuration file. The following example illustrates
 * how such a configuration file looks like.
 * </p>
 *
 * <pre>
 * &lt;<b>exbib</b> fallback="bbl"&gt;
 *   &lt;<b>Resource</b> src="path/exbibFinder"/&gt;
 *   &lt;<b>Processor</b> base="exbib/processor/" default="exbib"/&gt;
 *   &lt;<b>BblWriter</b> class="org.extex.exbib.core.io.bblio.BblWriter"&gt;
 *     &lt;<b>linelength</b>&gt;79&lt;<b>/linelength</b>&gt;
 *     &lt;<b>indent</b>&gt;  &lt;<b>/indent</b>&gt;
 *   &lt;<b>/BblWriter</b>&gt;
 *   &lt;<b>DB</b> class="org.extex.exbib.core.db.impl.DBImpl"&gt;
 *     &lt;<b>minCrossrefs</b>&gt;2&lt;<b>/minCrossrefs</b>&gt;
 *   &lt;<b>/DB</b>&gt;
 *   &lt;<b>Sorter</b> base="exbib/sorter/" default="locale"/&gt;
 *   &lt;<b>AuxReader</b> class="org.extex.exbib.core.io.auxio.AuxReaderImpl"/&gt;
 *   &lt;<b>BibReader</b> class="org.extex.exbib.core.io.bibio.BibReaderImpl"/&gt;
 *   &lt;<b>BibPrinter</b> base="exbib/printer/" default="bib"/&gt;
 * &lt;<b>/exbib</b>&gt;
 * </pre>
 *
 * <p>
 * The configuration is sought on the class path in the package
 * {@code config/exbib}.
 * </p>
 *
 * <h2>Dot Files</h2>
 * <p>
 * This class does <em>not</em> try to load settings from a dot file. This could
 * be done before creating an instance of this class.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ExBib {

  /**
   * This enumeration names the points for debugging.
   */
  public enum ExBibDebug {
    /**
     * The field {@code CSF} contains the indicator for debugging the csf
     * processing.
     */
    CSF,
    /**
     * The field {@code IO} contains the indicator for debugging the I/O
     * operations.
     */
    IO,
    /**
     * The field {@code MEM} contains the indicator for debugging the
     * memory allocation.
     */
    MEM,
    /**
     * The field {@code MISC} contains indicator for debugging
     * miscellaneous.
     */
    MISC,
    /**
     * The field {@code SEARCH} contains the indicator for debugging the
     * search.
     */
    SEARCH,
    /**
     * The field {@code TRACE} contains the indicator for tracing.
     */
    TRACE
  }

  /**
   * The field {@code VERSION} contains the official version number.
   */
  public static final String VERSION = "0.2";

  /**
   * The field {@code INCEPTION_YEAR} contains the year the development has
   * been started. This is fixed to be 2002 and should not be altered under
   * any circumstances.
   */
  public static final int INCEPTION_YEAR = 2002;

  /**
   * The field {@code CONFIGURATION_DEFAULT} contains the name of the
   * configuration to be used in default mode.
   */
  private static final String CONFIGURATION_DEFAULT = "exbib";

  /**
   * The field {@code AUX_FILE_EXTENSION} contains the extension of aux files
   * (in lower case).
   */
  private static final String AUX_FILE_EXTENSION = ".aux";

  /**
   * The field {@code BLG_FILE_EXTENSION} contains the extension of log files
   * (in lower case).
   */
  private static final String BLG_FILE_EXTENSION = ".blg";

  /**
   * The field {@code PROP_CONFIG} contains the name of the property to carry
   * the configuration. The default value is {@code "extex"}.
   */
  public static final String PROP_CONFIG = "exbib.config";

  /**
   * The field {@code PROP_BIB_ENCODING} contains the name of the property to
   * carry the encoding for bib files.
   */
  public static final String PROP_BIB_ENCODING = "exbib.bib.encoding";

  /**
   * The field {@code PROP_SORT} contains the specification for the sorter.
   */
  public static final String PROP_SORT = "exbib.sort";

  /**
   * The field {@code PROP_CSF_ENCODING} contains the name of the property to
   * carry the encoding for csf files.
   */
  public static final String PROP_CSF_ENCODING = "exbib.csf.encoding";

  /**
   * The field {@code PROP_ENCODING} contains the name of the property to
   * carry the encoding.
   */
  public static final String PROP_ENCODING = "exbib.encoding";

  /**
   * The field {@code PROP_FILE} contains the name of the property to carry
   * the aux file.
   */
  public static final String PROP_FILE = "exbib.file";

  /**
   * The field {@code PROP_MIN_CROSSREF} contains the name of the property
   * containing the value for {@code min.crossrefs}.
   */
  public static final String PROP_MIN_CROSSREF = "exbib.min.crossref";

  /**
   * The field {@code PROP_OUTFILE} contains the name of the property for the
   * output.
   */
  public static final String PROP_OUTFILE = "exbib.output";

  /**
   * The field {@code PROP_PROCESSOR} contains the name of the property for
   * the processor type.
   */
  public static final String PROP_PROCESSOR = "exbib.processor";

  /**
   * The field {@code PROP_BST} contains the name of the property for the bst
   * file.
   */
  public static final String PROP_BST = "exbib.bst";

  /**
   * The field {@code debug} contains the indicator for debugging output.
   */
  private final Set<ExBibDebug> debug = new HashSet<>();

  /**
   * The field {@code errors} contains the number of errors reported.
   */
  private int errors = 0;

  /**
   * The field {@code warnings} contains the number of warnings.
   */
  private long warnings = 0;

  /**
   * The field {@code properties} contains the settings for the program.
   */
  private Properties properties = null;

  /**
   * The field {@code logger} contains the logger.
   */
  private Logger logger;

  /**
   * The field {@code bundle} contains the resource bundle for i18n.
   */
  private ResourceBundle bundle;

  /**
   * The field {@code resourceFinder} contains the fallback resource finder.
   * If not set then a new one is created from the configuration.
   */
  private ResourceFinder resourceFinder = null;

  /**
   * Creates a new object. The properties containing the controlling
   * attributes are initialized from the System.properties.
   */
  public ExBib() {

    this( System.getProperties() );
  }

  /**
   * Creates a new object.
   *
   * @param properties the properties with the parameters
   */
  public ExBib( Properties properties ) {

    useLanguage( Locale.getDefault() );
    setProperties( properties );
    logger = Logger.getLogger( ExBib.class.getName() );
  }

  /**
   * Report an error and increment the error counter.
   *
   * @param tag  the tag of the resource bundle
   * @param args the arguments to be inserted for braced numbers
   * @return {@code false}
   */
  protected boolean error( String tag, Object... args ) {

    errors++;
    return log( tag, args );
  }

  /**
   * Report an exception and increment the error counter.
   *
   * @param e   the cause
   * @param tag the tag of the resource bundle
   * @return {@code false}
   */
  protected boolean error( Throwable e, String tag ) {

    error( tag, e.getLocalizedMessage() );

    if( debug.contains( ExBibDebug.MISC ) ) {
      logger.throwing( "", "", e );
    }

    return false;
  }

  /**
   * Getter for debug.
   *
   * @return the debug
   */
  public Set<ExBibDebug> getDebug() {

    return debug;
  }

  /**
   * Getter for properties.
   *
   * @return the properties
   */
  public Properties getProperties() {

    return properties;
  }

  /**
   * Getter for a property.
   *
   * @param key the key
   * @return the value or {@code null}
   * @see java.util.Properties#getProperty(java.lang.String)
   */
  public String getProperty( String key ) {

    return properties.getProperty( key );
  }

  /**
   * Log an info message.
   *
   * @param tag  the resource tag
   * @param args the arguments to be inserted
   * @return {@code false}
   */
  protected boolean info( String tag, Object... args ) {

    return log( Level.INFO, tag, args );
  }

  /**
   * Log a message.
   *
   * @param level the log level
   * @param tag   the resource tag
   * @param args  the arguments to be inserted
   * @return {@code false}
   */
  protected boolean log( Level level, String tag, Object... args ) {

    try {
      logger.log( level,
                  MessageFormat.format( bundle.getString( tag ), args ) );
    } catch( MissingResourceException e ) {
      logger.severe( MessageFormat.format( bundle.getString( "missing.tag" ),
                                           tag ) );
    }
    return false;
  }

  /**
   * Log a severe message.
   *
   * @param tag  the resource tag
   * @param args the arguments to be inserted
   * @return {@code false}
   */
  protected boolean log( String tag, Object... args ) {

    return log( Level.SEVERE, tag, args );
  }

  /**
   * Write a severe message to the logger. It is preceded by the banner if the
   * banner has not been shown before.
   *
   * @param tag  the resource tag of the message pattern
   * @param args the arguments
   * @return {@code false}
   */
  protected boolean logBanner( String tag, Object... args ) {

    return log( Level.SEVERE, tag, args );
  }

  /**
   * Make a sorter or throw an error.
   *
   * @param finder the resource finder
   * @param cfg    the configuration
   * @return the sorter; it can be {@code null} if none is required
   */
  protected SorterFactory makeSorterFactory( ResourceFinder finder,
                                             Configuration cfg ) {

    SorterFactory sorterFactory = new SorterFactory( cfg );
    sorterFactory.enableLogging( logger );
    sorterFactory.setResourceFinder( finder );
    sorterFactory.setProperties( properties );
    return sorterFactory;
  }

  /**
   * Set a property to a given value if not set yet.
   *
   * @param name  the name of the property
   * @param value the default value
   */
  protected void propertyDefault( String name, String value ) {

    if( !properties.containsKey( name ) && value != null ) {
      properties.setProperty( name, value );
    }
  }

  /**
   * Attach a handler to the logger to direct messages to a log file. This is
   * an extension point which could be implemented by derived classes. The
   * default implementation simply does nothing in this method.
   *
   * @param log       the base name of the file
   * @param extension the extension
   * @throws IOException in case of an I/O error
   */
  protected void recognizeFile( String log, String extension )
      throws IOException {


  }

  /**
   * This is the top level of the εχBib engine. When
   * all parameters are present then this method can be invoked.
   * <p>
   * The following steps are performed:
   * </p>
   * <ul>
   * <li>Read the configuration to get basic parameters.</li>
   * <li>Optionally make a new resource finder.</li>
   * <li>Make a {@link ProcessorContainer}.</li>
   * <li>Make an {@link AuxReader}
   * and use it to read the aux file storing the data in the container.</li>
   * <li>For each processor in the container run it to produce the output
   * file.</li>
   * </ul>
   *
   * @return {@code false} iff an error has occurred
   * @throws IOException            is case of an uncaught IOException
   * @throws ConfigurationException in case that the top-level configuration
   *                                could not be found
   */
  public boolean run() throws IOException, ConfigurationException {

    try {
      String file = properties.getProperty( PROP_FILE );
      if( file == null ) {
        return error( "missing.file" );
      }
      file = stripExtension( file, AUX_FILE_EXTENSION );

      Configuration config = ConfigurationFactory.newInstance(
          "exbib/" + properties.getProperty( PROP_CONFIG, "" ) );

      ResourceFinder finder =
          (resourceFinder != null
              ? resourceFinder
              : new ResourceFinderFactory().createResourceFinder(
              config.getConfiguration( "Resource" ),
              logger,
              properties,
              null ));

      if( debug.contains( ExBibDebug.SEARCH ) ) {
        finder.enableTracing( true );
      }

      recognizeFile( file, BLG_FILE_EXTENSION );

      FuncallObserver funcall = null;

      String encoding = properties.getProperty( PROP_ENCODING );
      BibReaderFactory bibReaderFactory =
          new BibReaderFactory( config.getConfiguration( "BibReader" ),
                                finder,
                                properties.getProperty( PROP_BIB_ENCODING ),
                                encoding );
      ProcessorContainer container =
          new ProcessorContainer( config, logger, properties ) {

            @Override
            protected void prepareProcessor( Processor processor,
                                             DB db )
                throws NotObservableException,
                ExBibIllegalValueException {

              db.registerObserver( "makeEntry", new EntryObserver(
                  logger, processor ) );
            }
          };

      container.setSorterFactory( makeSorterFactory( finder,
                                                     config.getConfiguration(
                                                         "Sorter" ) ) );
      container.setBibReaderFactory( bibReaderFactory );
      container.setResourceFinder( finder );
      container.registerObserver( "startRead", new DBObserver( logger,
                                                               bundle.getString(
                                                                   "observer" +
                                                                       ".db.pattern" ) ) );

      if( debug.contains( ExBibDebug.TRACE ) ) {
        funcall = runRegisterTracers( container );
      }

      AuxReader auxReader = new AuxReaderFactory(
          config.getConfiguration( "AuxReader" ) ).newInstance( finder );
      auxReader.register( new ResourceObserverImpl( logger ) );

      try {
        auxReader.load( container, file, encoding );
      } catch( FileNotFoundException e ) {
        return error( "aux.not.found", e.getMessage() );
      }
      String bst = (String) properties.get( PROP_BST );
      if( bst != null ) {
        container.findProcessor( "bbl" ).addBibliographyStyle(
            bst.split( "," ) );
      }

      if( !validate( container, file ) ) {
        return false;
      }

      final Configuration bblConfig = config.getConfiguration( "BblWriter" );
      BblWriterFactory bblWriterFactory =
          new BblWriterFactory( bblConfig, encoding ) {
            @Override
            protected void infoDiscarted() {
              info( "output.discarted" );
            }

            @Override
            protected void infoOutput( String file ) {
              info( "output.file", file );
            }

            @Override
            protected void infoStdout() {
              info( "output.to.stdout" );
            }
          };

      for( final String key : container ) {
        final Processor processor = container.getProcessor( key );

        for( final String style : processor.getBibliographyStyles() ) {
          info( "bst.file", style );
        }

        String outfile = properties.getProperty( PROP_OUTFILE );
        if( outfile == null || !"bbl".equals( key ) ) {
          outfile = file + "." + key;
        }
        try( Writer writer = bblWriterFactory.newInstance( outfile ) ) {
          warnings += processor.process( writer );
        } catch( FileNotFoundException e ) {
          return error( "output.could.not.be.opened", outfile );
        }
      }

      if( funcall != null ) {
        funcall.print();
      }
    } catch( ExBibImpossibleException | NotObservableException e ) {
      return error( e, "internal.error" );
    } catch( ExBibException e ) {
      return error( "verbatim", e.getLocalizedMessage() );
    } catch( ConfigurationWrapperException e ) {
      return error( e.getCause(), "installation.error" );
    } catch( ConfigurationException e ) {
      return error( "verbatim", e.getLocalizedMessage() );
    } catch( NoClassDefFoundError e ) {
      return error( e, "installation.error" );
    } catch( RuntimeException e ) {
      return error( e, "internal.error" );
    } finally {
      if( warnings > 0 ) {
        info( warnings == 1 ? "warning" : "warnings",
              Long.toString( warnings ) );
      }
      if( errors > 0 ) {
        log( errors == 1 ? "error" : "errors", Long.toString( errors ) );
      }
    }

    return errors <= 0;
  }

  /**
   * Register observers to get tracing output.
   *
   * @param container the processor container
   * @return the function call observer
   * @throws NotObservableException in case of an unknown observer name
   */
  private FuncallObserver runRegisterTracers( ProcessorContainer container )
      throws NotObservableException {

    FuncallObserver funcall =
        (debug.contains( ExBibDebug.TRACE )
            ? new FuncallObserver( logger )
            : null);

    container.registerObserver( "step",
                                new TracingObserver( logger,
                                                     bundle.getString(
                                                         "step_msg" ) ) );

    container.registerObserver( "run",
                                new TracingObserver( logger,
                                                     bundle.getString(
                                                         "do_msg" ) ) );
    container.registerObserver( "step", funcall );
    container.registerObserver( "push",
                                new TracingObserver( logger,
                                                     bundle.getString(
                                                         "push_msg" ) ) );
    container.registerObserver( "startParse", new TracingObserver( logger,
                                                                   bundle.getString(
                                                                       "start_parse_msg" ) ) );
    container.registerObserver( "endParse", new TracingObserver( logger,
                                                                 bundle.getString(
                                                                     "end_parse_msg" ) ) );
    return funcall;
  }

  /**
   * Setter for the debugging indicator.
   *
   * @param d indicator for debugging
   */
  public void setDebug( ExBibDebug d ) {

    debug.add( d );
  }

  /**
   * Setter for the debugging indicator.
   *
   * @param value indicator for debugging
   * @return {@code true} if everything went through
   */
  public boolean setDebug( String... value ) {

    for( final String s : value ) {
      try {
        if( "all".equals( s ) ) {
          Collections.addAll( debug, ExBibDebug.values() );
        }
        else if( "none".equals( s ) ) {
          debug.clear();
        }
        else {
          setDebug( ExBibDebug.valueOf( s.toUpperCase( Locale.ENGLISH ) ) );
        }
      } catch( IllegalArgumentException e ) {
        return logBanner( "debug.mode.unknown", s );
      }
    }
    return true;
  }

  /**
   * Setter for the file name. This is a convenience method to set the file
   * name in the properties. Before the assignment is performed it is checked
   * that the file name is not already set and the file name is not the empty
   * string. In those cases a message is written to the logger and nothing is
   * changed.
   *
   * @param arg the file name
   * @return {@code true} if everything went through
   */
  public boolean setFile( String arg ) {

    if( properties.getProperty( PROP_FILE ) != null ) {
      return logBanner( "one.file", arg );
    }
    else if( "".equals( arg ) ) {
      return logBanner( "empty.file", arg );
    }
    properties.setProperty( PROP_FILE, arg );
    return true;
  }

  /**
   * Setter for logger. The logger is set to a new value. The new value must
   * not be {@code null} or an {@link IllegalArgumentException} is
   * thrown.
   *
   * @param logger the logger to set which must not be {@code null}
   */
  public void setLogger( Logger logger ) {

    if( logger == null ) {
      throw new IllegalArgumentException();
    }
    this.logger = logger;
  }

  /**
   * Setter for properties.
   *
   * @param properties the properties to set
   */
  public void setProperties( Properties properties ) {

    this.properties = properties;
    propertyDefault( PROP_BIB_ENCODING, null );
    propertyDefault( PROP_CSF_ENCODING, null );
    propertyDefault( PROP_CONFIG, CONFIGURATION_DEFAULT );
    propertyDefault( PROP_SORT, null );
    propertyDefault( PROP_FILE, null );
    propertyDefault( PROP_ENCODING, null );
    propertyDefault( PROP_OUTFILE, null );
  }

  /**
   * Setter for a property. The properties carry the parameters. The used
   * parameters in this class are provided as constants.
   *
   * @param key   the key
   * @param value the value
   * @return the old value
   */
  @SuppressWarnings("unused")
  public Object setProperty( String key, String value ) {

    return properties.setProperty( key, value );
  }

  /**
   * Setter for resourceFinder.
   *
   * @param resourceFinder the resourceFinder to set
   */
  protected void setResourceFinder( ResourceFinder resourceFinder ) {

    this.resourceFinder = resourceFinder;
  }

  /**
   * Remove an extension if the given file name ends with it. The comparison
   * is performed case-insensitive.
   *
   * @param file      the name of the file
   * @param extension the postfix to remove
   * @return the normalized file name
   */
  @SuppressWarnings("SameParameterValue")
  private String stripExtension( String file, String extension ) {

    if( file.toLowerCase( Locale.ENGLISH ).endsWith( extension ) ) {
      return file.substring( 0, file.length() - extension.length() );
    }

    return file;
  }

  /**
   * Activate the language for the given locale. As a consequence the resource
   * bundle associated with the class {@link ExBib} and the locale is
   * activated.
   *
   * @param locale the locale
   */
  public void useLanguage( Locale locale ) {

    this.bundle = ResourceBundle.getBundle( ExBib.class.getName(), locale );
  }

  /**
   * Validate the container after a read.
   *
   * @param container the container
   * @param file      the file currently read for error messages
   * @return {@code true} iff everything is fine
   * @throws ExBibException               in case of an error
   * @throws ConfigurationException       in case of an configuration problem
   * @throws IOException                  in case of an I/O error
   * @throws CsfException                 in case of an error in the CSF
   * processing
   * @throws UnsupportedEncodingException in case of an unsupported encoding
   */
  private boolean validate( ProcessorContainer container, Object file )
      throws ConfigurationException,
      ExBibException,
      UnsupportedEncodingException,
      CsfException,
      IOException {

    if( container.isEmpty() ) {
      error( "bst.missing", file );
      error( "data.missing", file );
      return error( "citation.missing", file );
    }

    for( String key : container ) {
      Processor p = container.findProcessor( key );
      if( p.countBibliographyStyles() == 0 ) {
        error( "bst.missing.in", key, file );
      }
      if( p.countDatabases() == 0 ) {
        error( "data.missing.in", key, file );
      }
      if( p.countCitations() == 0 ) {
        error( "citation.missing.in", key, file );
      }
    }
    return errors <= 0;
  }

}
