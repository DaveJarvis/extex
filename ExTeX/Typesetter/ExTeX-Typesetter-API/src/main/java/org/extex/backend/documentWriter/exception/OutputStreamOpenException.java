/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.exception;

/**
 * This exception is used to signal that an output stream could not be opened.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class OutputStreamOpenException extends DocumentWriterException {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new object.
   *
   * @param message the error message
   * @param cause   the root of all evil
   */
  public OutputStreamOpenException( String message, Throwable cause ) {

    super( message, cause );
  }

}
