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

package org.extex.unit.omega.mode;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * This class contains the definitions of input modes in Omega. The definitions
 * are provides as constants defined in this class.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class OmegaMode implements Serializable {

  /**
   * The field {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2006L;

  /**
   * The constant {@code ONEBYTE} contains the mode for a single byte stream.
   * The encoding is more or less a variant of ASCII.
   */
  public static final OmegaMode ONEBYTE = new OmegaMode() {

    /**
     * The field {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Return the singleton constant object after the serialized instance
     * has been read back in.
     *
     * @return the one and only instance of this object
     *
     * @throws ObjectStreamException never
     */
    protected Object readResolve() throws ObjectStreamException {

      return ONEBYTE;
    }

    /**
     * Return the printable representation for this instance.
     *
     * @return the printable representation for this instance
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

      return "onebyte";
    }
  };

  /**
   * The constant {@code EBCDIC} contains the mode for a single byte stream.
   * The encoding is more or less a variant of EBCDIC.
   */
  public static final OmegaMode EBCDIC = new OmegaMode() {

    /**
     * The field {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Return the singleton constant object after the serialized instance
     * has been read back in.
     *
     * @return the one and only instance of this object
     *
     * @throws ObjectStreamException never
     */
    protected Object readResolve() throws ObjectStreamException {

      return EBCDIC;
    }

    /**
     * Return the printable representation for this instance.
     *
     * @return the printable representation for this instance
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

      return "ebcdic";
    }
  };

  /**
   * The constant {@code TWOBYTE} contains the mode for a double byte stream.
   * The encoding is more or less a variant of Unicode with the higher
   * endian byte first.
   */
  public static final OmegaMode TWOBYTE = new OmegaMode() {

    /**
     * The field {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Return the singleton constant object after the serialized instance
     * has been read back in.
     *
     * @return the one and only instance of this object
     *
     * @throws ObjectStreamException never
     */
    protected Object readResolve() throws ObjectStreamException {

      return TWOBYTE;
    }

    /**
     * Return the printable representation for this instance.
     *
     * @return the printable representation for this instance
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

      return "twobyte";
    }
  };

  /**
   * The constant {@code TWOBYTE_LE} contains the mode for a double byte
   * stream. The encoding is more or less a variant of Unicode with the lower
   * endian byte first.
   */
  public static final OmegaMode TWOBYTE_LE = new OmegaMode() {

    /**
     * The field {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Return the singleton constant object after the serialized instance
     * has been read back in.
     *
     * @return the one and only instance of this object
     *
     * @throws ObjectStreamException never
     */
    protected Object readResolve() throws ObjectStreamException {

      return TWOBYTE_LE;
    }

    /**
     * Return the printable representation for this instance.
     *
     * @return the printable representation for this instance
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

      return "twobyteLE";
    }
  };

  /**
   * Creates a new object.
   * This constructor is private to avoid abuse.
   */
  protected OmegaMode() {


  }

}
