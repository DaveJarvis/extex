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

import org.extex.framework.i18n.Localizer;

/**
 * This exception signals the error handler that a continuation is not
 * desirable.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class InterpreterPanicException extends HelpingException {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 1L;

  /**
   * Creates a new object.
   *
   * @param localizer the localizer
   * @param tag       the name of the format for the localizer
   */
  public InterpreterPanicException( Localizer localizer, String tag ) {

    super( localizer, tag, "?" );
  }

  /**
   * Creates a new object.
   *
   * @param localizer the localizer
   * @param tag       the name of the format for the localizer
   * @param arg       the argument
   */
  public InterpreterPanicException( Localizer localizer, String tag,
                                    String arg ) {

    super( localizer, tag, arg );
  }

}
