/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.exceptions;

import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is the base exception class for the exception in the context of the BST
 * processor. All exceptions in this context are derived from this class.
 *
 * <p>
 * The main feature of this class is the fact that it contains a
 * {@link org.extex.exbib.core.io.Locator Locator}. This attribute can be used
 * to get a more precise information about the source of a problem.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ExBibException extends Exception {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2008L;

  /**
   * The locator where the exception has been encountered in terms of the
   * user.
   */
  private transient Locator locator = null;

  /**
   * Create a new object.
   *
   * @param message the message
   */
  public ExBibException( String message ) {

    super( message );
  }

  /**
   * Creates a new object.
   *
   * @param message the message
   * @param locator the locator
   */
  public ExBibException( String message, Locator locator ) {

    super( message );
    this.locator = locator;
  }

  /**
   * Create a new object.
   *
   * @param message the message
   * @param cause   the cause
   */
  public ExBibException( String message, Throwable cause ) {

    super( message, cause );
  }

  /**
   * Create a new object.
   *
   * @param cause the cause
   */
  public ExBibException( Throwable cause ) {

    super( cause );
  }

  /**
   * Format a locator for addition to a message.
   *
   * @param loc the locator
   * @return the formatted locator
   */
  public String formatLocator( Locator loc ) {

    Localizer l =
        LocalizerFactory.getLocalizer( ExBibException.class.getName() );
    if( loc == null ) {
      return l.format( "NoLocator" );
    }
    return l.format( "Locator", loc.toString() );
  }

  @Override
  public String getLocalizedMessage() {

    Throwable cause = getCause();

    return getLocalizer().format( "Message", super.getMessage(),
                                  cause != null ? cause.toString() : "" );
  }

  /**
   * Acquire the localizer for the current class.
   *
   * @return the localizer
   */
  protected Localizer getLocalizer() {

    return LocalizerFactory.getLocalizer( getClass() );
  }

  /**
   * Getter for locator.
   *
   * @return the locator
   */
  public Locator getLocator() {

    return locator;
  }

  /**
   * Return a string representation of this exception. The string
   * representation contains information from the locator if present.
   *
   * @return the string representation
   */
  @Override
  public String toString() {

    String message = getLocalizedMessage();

    if( locator != null ) {
      Localizer l =
          LocalizerFactory.getLocalizer( ExBibException.class
                                             .getName() );
      return l.format( "LocatedMessage", locator.toString(), message );
    }

    Throwable cause = getCause();

    if( cause != null ) {
      return (message == null || "".equals( message ) ? "" : message + ": ")
          + cause.toString();
    }

    return message;
  }

}
