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

package org.extex.core.exception.helping;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception is raised when a {@code \csname} without matching
 * {@code \endcsname} is encountered.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */

public class MissingEndcsnameException extends HelpingException {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2006L;

  /**
   * Creates a new object.
   *
   * @param token the token encountered
   */
  public MissingEndcsnameException( String token ) {

    super( LocalizerFactory.getLocalizer( MissingEndcsnameException.class ),
           "TTP.MissingEndcsname" );
  }

  /**
   * Creates a localized description of this throwable. Subclasses may
   * override this method in order to produce a locale-specific message. For
   * subclasses that do not override this method, the default implementation
   * returns the same result as {@code getMessage()}.
   *
   * @return The localized description of this throwable.
   * @see java.lang.Throwable#getLocalizedMessage()
   */
  @Override
  public String getLocalizedMessage() {

    return super.getMessage();
  }

}
