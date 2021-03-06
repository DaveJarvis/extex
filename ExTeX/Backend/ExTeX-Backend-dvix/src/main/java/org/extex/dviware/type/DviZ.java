/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.dviware.type;

import org.extex.dviware.Dvi;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class represents the DVI instruction {@code z}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DviZ extends AbstractDviCode {

  /**
   * The field {@code value} contains the value.
   */
  private final int value;

  /**
   * Creates a new object.
   *
   * @param value the value
   */
  public DviZ( int value ) {

    super( "z" + variant( value ) );
    this.value = value;
  }

  /**
   * Write the code to the output stream.
   *
   * @param stream the target stream
   * @return the number of bytes actually written
   * @throws IOException in case of an error
   * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
   */
  public int write( OutputStream stream ) throws IOException {

    return opcodeSigned( Dvi.Z1, value, stream );
  }

}
