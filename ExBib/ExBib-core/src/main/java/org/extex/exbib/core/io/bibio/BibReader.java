/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * This interface describes a reader for bibliography files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface BibReader extends ResourceAware {

  /**
   * Close the current reader without destroying it. No further reading can be
   * performed.
   *
   * @throws IOException in case of an I/O error
   */
  void close() throws IOException;

  /**
   * Perform the loading into a database. The source given to the constructor
   * is used to fill a database.
   *
   * @param db the database into which the loading should be performed
   * @throws ExBibException         in case of an error
   * @throws ConfigurationException in case of an configuration error
   */
  void load( DB db ) throws ExBibException, ConfigurationException;

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
  LineNumberReader open( String name, Reader r )
      throws FileNotFoundException,
      ConfigurationException;

  /**
   * Open a file for reading.
   *
   * @param file     the file to open for reading
   * @param encoding the encoding
   * @throws FileNotFoundException  in case the file could not be opened for
   *                                reading
   * @throws ConfigurationException in case that the configuration is invalid
   */
  void open( String file, String encoding )
      throws ConfigurationException,
      FileNotFoundException;

}
