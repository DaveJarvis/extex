/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.core.exception;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is a base class for exceptions which carry an additional helping text
 * and a processed indicator.
 *
 * <h2>Exception-related rules</h2>
 *
 * <ul>
 * <li>Each component should define an exception hierarchy of its own.</li>
 * <li>No method should declare {@code throws Exception}. The exceptions
 * declared should be as specific as possible to allow a fine grained error
 * handling.</li>
 * <li>The outside interface of a component should only throw exceptions of the
 * component. Exceptions of underlying components should not be passed
 * through.</li>
 * <li>Exceptions should not carry text. Design exceptions for
 * internationalization. Sooner or later it will pay off.</li>
 * <li>Exceptions should be used for exceptional cases only. In the normal
 * course of a program execution no exception should be thrown. Exceptions
 * should not be abused for control flow in a program.</li>
 * <li>Exceptions need not to be optimized for speed. Since exceptions are
 * rarely used they can be slow.</li>
 * </ul>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class GeneralException extends Exception {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  private static final long serialVersionUID = 2007L;

  /**
   * The field {@code processed} contains the indicator that the exception
   * has been processed by an error handler already.
   */
  private boolean processed = false;

  /**
   * Creates a new object with the default exit code of -1.
   */
  public GeneralException() {

  }

  /**
   * Creates a new object with the default exit code of -1.
   *
   * @param message the message
   */
  public GeneralException( String message ) {

    super( message );
  }

  /**
   * Creates a new object with the default exit code of -1.
   *
   * @param message the message
   * @param cause   the cause for a chained exception
   */
  public GeneralException( String message, Throwable cause ) {

    super( message, cause );
  }

  /**
   * Creates a new object with the default exit code of -1.
   *
   * @param cause the cause for a chained exception
   */
  public GeneralException( Throwable cause ) {

    super( cause );
  }

  /**
   * Getter for further help information.
   *
   * @return the help information
   */
  public String getHelp() {

    return null;
  }

  /**
   * Getter for localizer.
   *
   * @return the localizer.
   */
  protected Localizer getLocalizer() {

    return LocalizerFactory.getLocalizer( this.getClass() );
  }


  /**
   * Getter for processed.
   *
   * @return the processed
   */
  public boolean isProcessed() {

    return this.processed;
  }

  /**
   * Setter for processed.
   *
   * @param processed the processed to set
   */
  public void setProcessed( boolean processed ) {

    this.processed = processed;
  }

}
