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
 * This class represents the DVI instruction {@code pre}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DviPreamble extends AbstractDviCode {

  /**
   * The comment {@code MAX_COMMENT_LEN} contains the maximal length of the
   * comment string.
   */
  private static final int MAX_COMMENT_LEN = 255;

  /**
   * The field {@code comment} contains the comment string.
   */
  private final String comment;

  /**
   * The field {@code mag} contains the magnification.
   */
  private final int mag;

  /**
   * Creates a new object.
   *
   * @param mag     the magnification
   * @param comment the comment
   */
  public DviPreamble( int mag, String comment ) {

    super( "pre" );

    if( comment.length() > MAX_COMMENT_LEN ) {
      throw new IllegalArgumentException( "comment" );
    }
    this.mag = mag;
    this.comment = comment;
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

    stream.write( Dvi.PRE );
    stream.write( Dvi.DVI_ID );
    write4( stream, Dvi.DVI_UNIT_NUMERATOR );
    write4( stream, Dvi.DVI_UNIT_DENOMINATOR );
    write4( stream, mag );
    int length = comment.length();
    stream.write( length );
    for( int i = 0; i < length; i++ ) {
      stream.write( comment.charAt( i ) );
    }
    return 15 + length;
  }

}
