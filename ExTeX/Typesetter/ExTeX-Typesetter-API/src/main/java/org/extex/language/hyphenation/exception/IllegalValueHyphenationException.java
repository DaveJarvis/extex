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

package org.extex.language.hyphenation.exception;

/**
 * This exception signals that an illegal value token has been encountered in a
 * hyphenation pattern. A value token must have the catcode OTHER and a digit
 * as its character.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IllegalValueHyphenationException extends HyphenationException {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * Creates a new object.
   *
   * @param message the message
   */
  public IllegalValueHyphenationException( String message ) {

    super( message );
  }

}
