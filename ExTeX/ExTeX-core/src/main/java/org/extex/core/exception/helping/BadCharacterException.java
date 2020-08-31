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

package org.extex.core.exception.helping;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception is raised when a bad character code is encountered.
 * <p>
 * The localization format is taken from the Localizer under the key
 * {@code TTP.BadChar}.
 * </p>
 * <p>
 * The format receives one argument which contains the character code.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BadCharacterException extends HelpingException {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2006L;

  /**
   * The field {@code cc} contains the invalid character.
   */
  private final long cc;

  /**
   * Creates a new object.
   *
   * @param code the bad character code
   */
  public BadCharacterException( long code ) {

    super( LocalizerFactory.getLocalizer( BadCharacterException.class ),
           "TTP.BadChar", Long.toString( code ) );
    cc = code;
  }

  /**
   * Getter for cc.
   *
   * @return the cc
   */
  public long getChar() {

    return this.cc;
  }

}
