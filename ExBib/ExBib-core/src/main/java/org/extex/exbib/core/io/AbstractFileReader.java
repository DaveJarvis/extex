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

package org.extex.exbib.core.io;

import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observable;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationUnsupportedEncodingException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;

import java.io.*;

/**
 * This is the base class for all file reading classes in
 * ??Bib.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractFileReader implements Observable {

  /**
   * The field {@code filename} contains the name of the file to read.
   */
  private String filename = "???";

  /**
   * The field {@code reader} contains the internal reader to be used.
   */
  private LineNumberReader reader = null;

  /**
   * The field {@code locator} contains the locator from the user's
   * perspective.
   */
  private Locator locator = null;

  /**
   * The field {@code line} contains the temporary memory for the line.
   */
  private StringBuilder line = new StringBuilder();

  /**
   * The field {@code finder} contains the resource finder.
   */
  private ResourceFinder finder;

  /**
   * Create a new object.
   *
   * @throws ConfigurationException in case of a configuration error
   */
  public AbstractFileReader() throws ConfigurationException {

  }

  /**
   * Determine whether another read can be performed on the reader.
   *
   * @return {@code true} if read may be performed
   */
  public boolean canRead() {

    return reader != null;
  }

  /**
   * Close the current reader without destroying it. No further reading can be
   * performed afterwards. If the stream is already closed it has no effect.
   *
   * @throws IOException in case of an I/O error
   */
  public void close() throws IOException {

    try {
      if( reader != null ) {
        reader.close();
        line = null;
      }
    } finally {
      reader = null;
    }
  }

  /**
   * Getter for the line buffer.
   *
   * @return the line buffer
   */
  public StringBuilder getBuffer() {

    if( line == null && reader != null ) {
      read();
    }

    return line;
  }

  /**
   * Getter for the file name from which reading is performed.
   *
   * @return the file name
   */
  public String getFilename() {

    return filename;
  }

  /**
   * Getter for the locator.
   *
   * @return the locator
   */
  public Locator getLocator() {

    if( locator == null ) {
      locator = new Locator( filename, reader.getLineNumber() );
    }
    return locator;
  }

  /**
   * Open the reader for subsequent reading operations.
   *
   * @param name the name of the resource
   * @param r    the reader to use
   * @return a Reader for the requested file
   * @throws ConfigurationException in case that the configuration is invalid
   * @throws FileNotFoundException  in case that the reader is
   *                                {@code null}
   */
  public LineNumberReader open( String name, Reader r )
      throws FileNotFoundException,
      ConfigurationException {

    if( r == null ) {
      throw new FileNotFoundException( name );
    }
    filename = name;
    reader = new LineNumberReader( r );
    return reader;
  }

  /**
   * Open the reader for subsequent reading operations.
   *
   * @param name     the name of the file to open
   * @param type     the type of the file to open
   * @param encoding the encoding to use or {@code null} for the platform
   *                 default
   * @return a Reader for the requested file
   * @throws ConfigurationException in case that the configuration is invalid
   * @throws FileNotFoundException  in case that the named file could not be
   *                                opened for reading
   */
  public LineNumberReader open( String name, String type, String encoding )
      throws FileNotFoundException,
      ConfigurationException {

    if( finder == null ) {
      throw new IllegalArgumentException( "finder" );
    }

    NamedInputStream is = finder.findResource( name, type );
    if( is == null ) {
      throw new FileNotFoundException( name );
    }
    filename = is.getName();
    try {
      reader =
          new LineNumberReader( encoding == null
                                    ? new InputStreamReader( is )
                                    : new InputStreamReader( is, encoding ) );
    } catch( UnsupportedEncodingException e ) {
      throw new ConfigurationUnsupportedEncodingException( encoding,
                                                           null );
    }
    return reader;
  }

  /**
   * Attempt to fill the line buffer with an additional line.
   *
   * @return the internal line buffer
   */
  public StringBuilder read() {

    if( line == null || reader == null ) {
      return null;
    }

    try {
      String nextLine = reader.readLine();

      if( nextLine == null ) {
        line = null;
      }
      else {
        line.append( nextLine );
        locator = new Locator( filename, reader.getLineNumber() );
      }
    } catch( IOException e ) {
      line = null;
    }

    return line;
  }

  /**
   * org.extex.exbib.core.util.Observer)
   */
  @Override
  public void registerObserver( String name, Observer observer )
      throws NotObservableException {

    throw new NotObservableException( name );
  }

  /**
   * Setter for the line buffer.
   *
   * @param buffer the line buffer
   */
  public void setBuffer( StringBuilder buffer ) {

    line = buffer;
  }

  /**
   * Setter for the resource finder.
   *
   * @param f the resource finder
   */
  public void setResourceFinder( ResourceFinder f ) {

    this.finder = f;
  }

  /**
   * Returns a printable representation of this object.
   *
   * @return the printable representation
   */
  @Override
  public String toString() {

    return filename;
  }

}
