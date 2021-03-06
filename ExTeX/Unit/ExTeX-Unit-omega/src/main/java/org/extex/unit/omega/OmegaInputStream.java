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

package org.extex.unit.omega;

import org.extex.interpreter.context.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class OmegaInputStream extends InputStream {

  /**
   * The field {@code context} contains the interpreter context.
   */
  private final Context context;

  /**
   * The field {@code stream} contains the input stream itself.
   */
  private final InputStream stream;

  /**
   * Creates a new object.
   *
   * @param context the interpreter context
   * @param stream  the input stream
   */
  public OmegaInputStream( InputStream stream, Context context ) {

    this.stream = stream;
    this.context = context;
  }

  @Override
  public int available() throws IOException {

    return stream.available();
  }

  @Override
  public int read() throws IOException {

    return stream.read();
  }

}
