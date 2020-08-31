/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.info.util;

import org.extex.core.exception.helping.HelpingException;

/**
 * This class provides an Exception with the possibility to provide additional
 * help on the error encountered. Thus it has two levels of information: the
 * first level is the message and the second level is the additional help.
 * <p>
 * In contrast to
 * {@link org.extex.core.exception.helping.HelpingException HelpingException}
 * the messages are not mapped. Thus they are not subject to
 * internationalization.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FixedHelpingException extends HelpingException {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * The field {@code help} contains the string which is shown if further
   * help is requested.
   */
  private final String help;

  /**
   * The field {@code message} contains the message. We need to keep it here
   * since the parent class does not provide writing access to the message of
   * the underlying Exception.
   */
  private final String message;

  /**
   * Creates a new object.
   *
   * @param message the message of this Exception
   * @param help    the help string
   */
  public FixedHelpingException( String message, String help ) {

    this.message = message;
    this.help = help;
  }

  /**
   * Getter for further help information.
   *
   * @return the help information
   * @see org.extex.core.exception.GeneralException#getHelp()
   */
  @Override
  public String getHelp() {

    return help;
  }

  /**
   * Creates a localized description of this throwable.
   *
   * @return the localized description of this throwable.
   * @see java.lang.Throwable#getLocalizedMessage()
   */
  @Override
  public String getLocalizedMessage() {

    return message;
  }

  /**
   * Creates a description of this throwable.
   *
   * @return the description of this throwable.
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage() {

    return message;
  }

}
