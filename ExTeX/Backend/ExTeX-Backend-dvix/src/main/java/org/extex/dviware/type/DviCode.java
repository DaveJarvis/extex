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
 * This interface describes DVI code. It must be written to the output stream
 * at the end.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface DviCode {

  /**
   * The constant {@code POP} contains the DviCode for the {@code pop}
   * instruction. This instruction doe not carry any parameters. Thus a
   * singleton can be used.
   */
  DviCode POP = new DviCode() {

    /**
     * @see org.extex.dviware.type.DviCode#getName()
     */
    public String getName() {

      return "pop";
    }

    /**
     * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
     */
    public int write( OutputStream stream ) throws IOException {

      stream.write( Dvi.POP );
      return 1;
    }

  };

  /**
   * The constant {@code PUSH} contains the DviCode for the {@code push}
   * instruction. This instruction doe not carry any parameters. Thus a
   * singleton can be used.
   */
  DviCode PUSH = new DviCode() {

    /**
     * @see org.extex.dviware.type.DviCode#getName()
     */
    public String getName() {

      return "push";
    }

    /**
     * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
     */
    public int write( OutputStream stream ) throws IOException {

      stream.write( Dvi.PUSH );
      return 1;
    }

  };

  /**
   * The constant {@code W0} contains the DviCode for the {@code w0}
   * instruction. This instruction doe not carry any parameters. Thus a
   * singleton can be used.
   */
  DviCode W0 = new DviCode() {

    /**
     * @see org.extex.dviware.type.DviCode#getName()
     */
    public String getName() {

      return "w0";
    }

    /**
     * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
     */
    public int write( OutputStream stream ) throws IOException {

      stream.write( Dvi.W0 );
      return 1;
    }

  };

  /**
   * The constant {@code X0} contains the DviCode for the {@code x0}
   * instruction. This instruction doe not carry any parameters. Thus a
   * singleton can be used.
   */
  DviCode X0 = new DviCode() {

    /**
     * @see org.extex.dviware.type.DviCode#getName()
     */
    public String getName() {

      return "x0";
    }

    /**
     * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
     */
    public int write( OutputStream stream ) throws IOException {

      stream.write( Dvi.X0 );
      return 1;
    }

  };

  /**
   * The constant {@code X0} contains the DviCode for the {@code y0}
   * instruction. This instruction doe not carry any parameters. Thus a
   * singleton can be used.
   */
  DviCode Y0 = new DviCode() {

    /**
     * @see org.extex.dviware.type.DviCode#getName()
     */
    public String getName() {

      return "y0";
    }

    /**
     * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
     */
    public int write( OutputStream stream ) throws IOException {

      stream.write( Dvi.Y0 );
      return 1;
    }

  };

  /**
   * The constant {@code Z0} contains the DviCode for the {@code z0}
   * instruction. This instruction doe not carry any parameters. Thus a
   * singleton can be used.
   */
  DviCode Z0 = new DviCode() {

    /**
     * @see org.extex.dviware.type.DviCode#getName()
     */
    public String getName() {

      return "z0";
    }

    /**
     * @see org.extex.dviware.type.DviCode#write(java.io.OutputStream)
     */
    public int write( OutputStream stream ) throws IOException {

      stream.write( Dvi.Z0 );
      return 1;
    }

  };

  /**
   * Getter for the name of the DVI instruction.
   *
   * @return the name of the DVI instruction
   */
  String getName();

  /**
   * Write the code to the output stream.
   *
   * @param stream the target stream
   * @return the number of bytes actually written
   * @throws IOException in case of an error
   */
  int write( OutputStream stream ) throws IOException;

}
