/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.resource.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * This input stream carries a name to transport the information where the input
 * is coming from.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class NamedInputStream extends InputStream {

  /**
   * The field {@code name} contains the name.
   */
  private final String name;

  /**
   * The field {@code stream} contains the stream.
   */
  private final InputStream stream;

  /**
   * Creates a new object.
   *
   * @param stream the stream
   * @param name   the name
   */
  public NamedInputStream( InputStream stream, String name ) {

    this.stream = stream;
    this.name = name;
  }

  @Override
  public int available() throws IOException {

    return stream.available();
  }

  @Override
  public void close() throws IOException {

    stream.close();
  }

  @Override
  public boolean equals( Object obj ) {

    return stream.equals( obj );
  }

  /**
   * Getter for name.
   *
   * @return the name
   */
  public String getName() {

    return name;
  }

  @Override
  public int hashCode() {

    return stream.hashCode();
  }

  @Override
  public void mark( int readlimit ) {

    stream.mark( readlimit );
  }

  @Override
  public boolean markSupported() {

    return stream.markSupported();
  }

  @Override
  public int read() throws IOException {

    return stream.read();
  }

  @Override
  public int read( byte[] b ) throws IOException {

    return stream.read( b );
  }

  @Override
  public int read( byte[] b, int off, int len ) throws IOException {

    return stream.read( b, off, len );
  }

  @Override
  public void reset() throws IOException {

    stream.reset();
  }

  @Override
  public long skip( long n ) throws IOException {

    return stream.skip( n );
  }

  @Override
  public String toString() {

    return stream.toString();
  }

}
