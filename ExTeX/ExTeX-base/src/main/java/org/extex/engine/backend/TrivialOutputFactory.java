/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.engine.backend;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.outputStream.OutputStreamFactory;
import org.extex.backend.outputStream.OutputStreamObserver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The trivial output stream factory is not configurable. It just creates files
 * in the current directory.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TrivialOutputFactory implements OutputStreamFactory {

  /**
   * The field {@code extension} contains the default extension used when
   * type is {@code null}.
   */
  private String extension = null;

  /**
   * The field {@code observers} contains the list of registered observers.
   */
  private List<OutputStreamObserver> observers = null;


  public TrivialOutputFactory() {

  }

  /**
   * java.lang.String, java.lang.String)
   */
  public OutputStream getOutputStream( String name, String type )
      throws DocumentWriterException {

    String ext = (type != null ? type : extension);
    File file = new File( ".", name + "." + ext );
    NamedOutputStream stream;
    try {
      stream =
          new NamedOutputStream( file.toString(),
                                 new BufferedOutputStream( new FileOutputStream(
                                     file ) ) );
    } catch( FileNotFoundException e ) {
      throw new DocumentWriterException( e );
    }
    if( observers != null ) {
      for( OutputStreamObserver obs : observers ) {
        obs.update( name, type, stream );
      }
    }
    return stream;
  }

  /**
   * org.extex.backend.outputStream.OutputStreamObserver)
   */
  public void register( OutputStreamObserver observer ) {

    if( observers == null ) {
      observers = new ArrayList<OutputStreamObserver>();
    }
    observers.add( observer );
  }

  /**
   * java.lang.String)
   */
  public void setExtension( String extension ) {

    this.extension = extension;
  }

}
