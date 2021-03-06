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
 * This class represents the DVI instruction {@code bop}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DviBop extends AbstractDviCode {

  /**
   * The field {@code lastBop} contains the index of the last bop in the
   * stream or -1.
   */
  private final int lastBop;

  /**
   * The field {@code pageNo} contains the array of the page number
   * indicators.
   */
  private final int[] pageNo;

  /**
   * Creates a new object.
   *
   * @param pageNo  the array of page numbers
   * @param lastBop the index of the last bop or -1 at the beginning
   */
  public DviBop( int[] pageNo, int lastBop ) {

    super( "bop" );
    if( pageNo.length != 10 ) {
      throw new IllegalArgumentException( "count" );
    }
    this.pageNo = pageNo;
    this.lastBop = lastBop;
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

    stream.write( Dvi.BOP );
    int length = pageNo.length;
    for( int i = 0; i < length; i++ ) {
      write4( stream, pageNo[ i ] );
    }
    write4( stream, lastBop );
    return 1 + 4 * (length + 1);
  }

}
