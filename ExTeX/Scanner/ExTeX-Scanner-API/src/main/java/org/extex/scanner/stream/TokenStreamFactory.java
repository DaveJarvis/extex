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

package org.extex.scanner.stream;

import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.*;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.stream.exception.MissingResourceFinderException;
import org.extex.scanner.stream.observer.file.OpenFileObservable;
import org.extex.scanner.stream.observer.file.OpenFileObserver;
import org.extex.scanner.stream.observer.file.OpenFileObserverList;
import org.extex.scanner.stream.observer.reader.OpenReaderObservable;
import org.extex.scanner.stream.observer.reader.OpenReaderObserver;
import org.extex.scanner.stream.observer.reader.OpenReaderObserverList;
import org.extex.scanner.stream.observer.string.OpenStringObservable;
import org.extex.scanner.stream.observer.string.OpenStringObserver;
import org.extex.scanner.stream.observer.string.OpenStringObserverList;
import org.extex.scanner.stream.observer.writer.OpenWriterObservable;
import org.extex.scanner.stream.observer.writer.OpenWriterObserver;
import org.extex.scanner.stream.observer.writer.OpenWriterObserverList;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the factory to provide an instance of a
 * {@link org.extex.scanner.api.TokenStream TokenStream}. Like any good factory
 * it is controlled by its configuration.  Configuration
 * <p>
 * Mainly the configuration needs to specify which class to use for the
 * TokenStream. The name of the class is given as the argument
 * {@code class} as shown below.
 * </p>
 *
 * <pre>
 *   &lt;Scanner class="the.pack.age.TheClass"/&gt;
 * </pre>
 *
 * <p>
 * The class given must implement the interface
 * {@link org.extex.scanner.api.TokenStream TokenStream}. In addition an
 * appropriate constructor is required:
 * </p>
 *
 * <pre>
 *     public TheClass(Configuration config, Reader reader, Boolean isFile,
 *          String theSource) throws IOException
 * </pre>
 *
 * <p>
 * If the Token stream is fed from a file then the additional parameter
 * {@code buffersize} is taken into account. This parameter is optional. Its
 * usage can look as follows:
 * </p>
 *
 * <pre>
 *   &lt;Scanner class="the.pack.age.TheClass"
 *         buffersize="0"/&gt;
 * </pre>
 *
 * <p>
 * The value given is a number. If this number is positive then it is
 * interpreted as the size of the buffer for the file reading operation. If it
 * is 0 or empty then no buffer will be used. If it is negative, then the
 * default buffer size will be used.
 * </p>
 * <p>
 * In addition to the class for the Token stream the reader class can be
 * specified for the case that reading from a file is requested. In this case
 * the mapping from bytes to characters according to an encoding. The name is
 * given as the parameter {@code reader} as shown below:
 * </p>
 *
 * <pre>
 *   &lt;Scanner class="the.pack.age.TheClass"
 *         reader="another.pack.age.TheReaderClass"/&gt;
 * </pre>
 *
 * <p>
 * Note that the attribute {@code reader} is optional. If none is given or the
 * value is the empty string then {@code java.io.InputStreamReader} is used
 * instead.
 * </p>
 * <p>Observable Events</p>
 * <p>
 * Observers can be registered for several events:
 * </p>
 * <dl>
 * <dt>{@code file}</dt>
 * <dd>This event is triggered by the request for a TokenStream fed from a file.
 * It is deferred until the file has been found and opened. The name of the file
 * is passed as argument to the observer.</dd>
 * <dt>{@code reader}</dt>
 * <dd>This event is triggered by the request for a TokenStream fed from an
 * arbitrary Reader. The reader is passed as argument to the observer.</dd>
 * <dt>{@code string}</dt>
 * <dd>This event is triggered by the request for a TokenStream fed from a
 * String. The string is passed as argument to the observer.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TokenStreamFactory extends AbstractFactory<Object>
    implements
    OpenFileObservable,
    OpenStringObservable,
    OpenReaderObservable,
    OpenWriterObservable {

  /**
   * The constant {@code BUFFERSIZE_ATTRIBUTE} contains the name of the
   * attribute used to get the buffer size.
   */
  private static final String BUFFERSIZE_ATTRIBUTE = "buffersize";

  /**
   * The constant {@code CLASS_ATTRIBUTE} contains the name of the attribute
   * used to get the class name.
   */
  private static final String CLASS_ATTRIBUTE = "class";

  /**
   * The field {@code bufferSize} contains the buffer size. A value less than
   * 1 indicates that the default should be used.
   */
  private int bufferSize;

  /**
   * The field {@code configuration} contains the configuration for this
   * instance.
   */
  private Configuration configuration;

  /**
   * The field {@code decorators} contains the list of decorators for input
   * streams acquired from a resource.
   */
  private List<InputStreamInterceptor> inStreamInterceptors;

  /**
   * The field {@code interceptors} contains the list of decorators for
   * readers.
   */
  private List<ReaderInterceptor> inReaderInterceptors;

  /**
   * The field {@code openFileObservers} contains the observers registered
   * for the "file" event.
   */
  private OpenFileObserver openFileObservers;

  /**
   * The field {@code openReaderObservers} contains the observers registered
   * for the "reader" event.
   */
  private OpenReaderObserver openReaderObservers;

  /**
   * The field {@code openStringObservers} contains the observers registered
   * for the "string" event.
   */
  private OpenStringObserver openStringObservers;

  /**
   * The field {@code openWriterObservers} contains the observers registered
   * for the "writer" event.
   */
  private OpenWriterObserver openWriterObservers;

  /**
   * The field {@code options} contains the options for the token stream.
   */
  private TokenStreamOptions options;

  /**
   * The field {@code readerConstructor} contains the constructor for the
   * reader variant.
   */
  private Constructor<?> readerConstructor;

  /**
   * The field {@code tag} contains the tag name of the sub-configuration to
   * use.
   */
  private final String tag;

  /**
   * The field {@code outWriterInterceptors} contains the output writer
   * interceptor list.
   */
  private List<WriterInterceptor> outWriterInterceptors;

  /**
   * The field {@code outStreamInterceptors} contains the output stream
   * interceptor list.
   */
  private List<OutputStreamInterceptor> outStreamInterceptors;

  /**
   * Creates a new object.
   *
   * @param tag the tag name of the sub-configuration to use
   * @throws ConfigurationException in case of an error in the configuration
   */
  public TokenStreamFactory( String tag ) throws ConfigurationException {

    this.tag = tag;
  }

  @Override
  public void configure( Configuration config ) throws ConfigurationException {

    super.configure( config );
    this.configuration = selectConfiguration( tag );
    String classname = configuration.getAttribute( CLASS_ATTRIBUTE );
    if( classname == null ) {
      throw new ConfigurationMissingAttributeException( CLASS_ATTRIBUTE,
                                                        configuration );
    }
    try {
      readerConstructor =
          Class.forName( classname ).getConstructor(
              Configuration.class,
              TokenStreamOptions.class, Reader.class,
              Boolean.class, String.class );
    } catch( SecurityException e ) {
      throw new ConfigurationInstantiationException( e );
    } catch( NoSuchMethodException e ) {
      throw new ConfigurationNoSuchMethodException( e );
    } catch( ClassNotFoundException e ) {
      throw new ConfigurationClassNotFoundException( classname,
                                                     configuration );
    }
    String bs = config.getAttribute( BUFFERSIZE_ATTRIBUTE );
    if( bs != null && bs.matches( "^[0-9]+$" ) ) {
      bufferSize = Integer.parseInt( bs );
    }
    else {
      bufferSize = 0;
    }
  }

  /**
   * Provide a new instance of a token stream reading from a string.
   *
   * @param line the line of input to read from
   * @return the new instance
   * @throws ConfigurationException in case of an error in the configuration
   */
  public TokenStream getStream( CharSequence line )
      throws ConfigurationException {

    TokenStream stream = getStream( new StringReader( line.toString() ),
                                    Boolean.FALSE, "*" );

    if( openStringObservers != null ) {
      openStringObservers.update( line );
    }

    return stream;
  }

  /**
   * Provide a new instance of a token stream reading from a Reader.
   *
   * @param reader the reader to get new characters from
   * @return the new instance
   * @throws ConfigurationException in case of an error in the configuration
   */
  public TokenStream getStream( Reader reader ) throws ConfigurationException {

    TokenStream stream = getStream( reader, Boolean.FALSE, "*" );

    if( openReaderObservers != null ) {
      openReaderObservers.update( reader );
    }

    return stream;
  }

  /**
   * Provide a new instance of a token stream reading from a Reader.
   *
   * @param reader the reader to get new characters from
   * @param isFile the indicator for file readers
   * @param source the description of the source
   * @return the new instance
   * @throws ConfigurationException in case of an error in the configuration
   */
  protected TokenStream getStream( Reader reader, Boolean isFile,
                                   String source )
      throws ConfigurationException {

    if( reader == null ) {
      throw new IllegalArgumentException( "reader" );
    }
    Reader r = reader;

    if( inReaderInterceptors != null ) {
      for( ReaderInterceptor iri : inReaderInterceptors ) {
        r = iri.pipe( r );
      }
    }

    TokenStream stream;
    try {

      stream = (TokenStream) readerConstructor.newInstance(
          new Object[]{configuration, options, r, isFile, source} );

    } catch( final Exception e ) {
      throw new ConfigurationInstantiationException( e );
    }

    enableLogging( stream, getLogger() );

    return stream;
  }

  /**
   * Provide a new instance of a token stream reading from a file or other
   * resource.
   *
   * @param name     the name of the file to be read
   * @param type     the type of the file to be read
   * @param encoding the name of the encoding to use
   * @return the new instance or {@code null} if the resource could not
   * be located
   * @throws ConfigurationException in case of an error in the configuration
   */
  public TokenStream getStream( String name, String type, String encoding )
      throws ConfigurationException {

    ResourceFinder resourceFinder = getResourceFinder();
    if( resourceFinder == null ) {
      throw new MissingResourceFinderException( "" );
    }
    InputStream istream = resourceFinder.findResource( name, type );

    if( istream == null ) {
      return null;
    }
    if( bufferSize > 0 ) {
      istream = new BufferedInputStream( istream, bufferSize );
    }
    else {
      istream = new BufferedInputStream( istream );
    }

    if( inStreamInterceptors != null ) {
      for( InputStreamInterceptor isi : inStreamInterceptors ) {
        istream = isi.pipe( istream );
      }
    }

    TokenStream stream;
    try {
      stream =
          getStream( encoding == null
                         ? new InputStreamReader( istream )
                         : new InputStreamReader( istream, encoding ),
                     Boolean.TRUE, name );
    } catch( UnsupportedEncodingException e ) {
      throw new ConfigurationUnsupportedEncodingException( encoding,
                                                           null );
    }

    if( openFileObservers != null ) {
      openFileObservers.update( name, type, istream );
    }

    return stream;
  }

  /**
   * Register an input stream interceptor to be applied for each token stream
   * originated at a resource.
   *
   * @param interceptor the additional interceptor
   */
  public void register( InputStreamInterceptor interceptor ) {

    if( inStreamInterceptors == null ) {
      inStreamInterceptors = new ArrayList<>();
    }
    inStreamInterceptors.add( interceptor );
  }

  /**
   * Register an output stream interceptor to be applied for each output
   * stream.
   *
   * @param interceptor the additional interceptor
   */
  public void register( OutputStreamInterceptor interceptor ) {

    if( outStreamInterceptors == null ) {
      outStreamInterceptors = new ArrayList<>();
    }
    outStreamInterceptors.add( interceptor );
  }

  /**
   * Register a reader interceptor to be applied for each token stream
   * originated at a resource.
   *
   * @param interceptor the additional interceptor
   */
  public void register( ReaderInterceptor interceptor ) {

    if( inReaderInterceptors == null ) {
      inReaderInterceptors = new ArrayList<>();
    }
    inReaderInterceptors.add( interceptor );
  }

  /**
   * Register a writer interceptor to be applied for each writer.
   *
   * @param interceptor the additional interceptor
   */
  public void register( WriterInterceptor interceptor ) {

    if( outWriterInterceptors == null ) {
      outWriterInterceptors = new ArrayList<>();
    }
    outWriterInterceptors.add( interceptor );
  }

  @Override
  public void registerObserver( OpenFileObserver observer ) {

    openFileObservers =
        OpenFileObserverList.register( openFileObservers, observer );
  }

  @Override
  public void registerObserver( OpenReaderObserver observer ) {

    openReaderObservers =
        OpenReaderObserverList.register( openReaderObservers, observer );
  }

  @Override
  public void registerObserver( OpenStringObserver observer ) {

    openStringObservers =
        OpenStringObserverList.register( openStringObservers, observer );
  }

  @Override
  public void registerObserver( OpenWriterObserver observer ) {

    openWriterObservers =
        OpenWriterObserverList.register( openWriterObservers, observer );
  }

  /**
   * Setter for options.
   *
   * @param options the options to set.
   */
  public void setOptions( TokenStreamOptions options ) {

    this.options = options;
  }

  /**
   * Construct a Writer for an output stream. This may build up a pipe of
   * output stream and writers to perform all desirable steps.
   * <p>
   * The encoding can be given as a hint.
   * </p>
   *
   * @param stream   the stream to put bytes to
   * @param key      the name in terms of the interpreter
   * @param encoding the optional encoding. If the encoding is
   *                 {@code null} then it is ignored
   * @return the writer for the task
   * @throws UnsupportedEncodingException in case of an error with the
   *                                      encoding
   */
  public Writer writerStream( OutputStream stream, String key, String encoding )
      throws UnsupportedEncodingException {

    OutputStream s = stream;

    if( outStreamInterceptors != null ) {
      for( OutputStreamInterceptor i : outStreamInterceptors ) {
        s = i.pipe( s );
      }
    }

    OutputStreamWriter w;
    if( encoding != null ) {
      w = new OutputStreamWriter( s, encoding );
    }
    else {
      w = new OutputStreamWriter( stream );
    }
    Writer writer = new BufferedWriter( w );

    if( outWriterInterceptors != null ) {
      for( WriterInterceptor i : outWriterInterceptors ) {
        writer = i.pipe( writer );
      }
    }

    if( openWriterObservers != null ) {
      openWriterObservers.update( writer );
    }
    return writer;
  }

}
