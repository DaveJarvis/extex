/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.stream.exception;

import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This Exception is thrown when no file finder has been provided before it is
 * needed.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MissingResourceFinderException extends ConfigurationException {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * The field {@code message} contains the message of this exception.
   */
  private final String message;

  /**
   * Create a new object.
   *
   * @param aMessage the message string
   */
  public MissingResourceFinderException( String aMessage ) {

    super( aMessage, (String) null );
    this.message = aMessage;
  }

  /**
   * Getter for the text prefix of this ConfigException.
   *
   * @return the text
   */
  @Override
  public String getLocalizedMessage() {

    return getLocalizer().format(
        "ConfigurationMissingFileFinderException.Text", message );
  }

}
