/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.exception;

import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.parser.ResourceLocator;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception signals that an unknown argument has been encountered.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class RawIndexException extends LException {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2008L;

  /**
   * The field {@code locator} contains the name and line number of the
   * resource.
   */
  private final ResourceLocator locator;

  /**
   * Creates a new object.
   *
   * @param locator the locator
   * @param message the message
   */
  public RawIndexException( ResourceLocator locator, String message ) {

    super( message );
    this.locator = locator;
  }

  @Override
  public String getLocalizedMessage() {

    String message = getMessage();

    return LocalizerFactory.getLocalizer( RawIndexException.class ).format(
        (message == null ? "NoMessage" : "Message"), locator.getResource(),
        Integer.toString( locator.getLineNumber() ), message );
  }

}
