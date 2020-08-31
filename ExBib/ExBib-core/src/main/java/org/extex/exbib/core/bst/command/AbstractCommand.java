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

package org.extex.exbib.core.bst.command;

import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.io.Locator;

/**
 * This is the abstract base class for all commands. A command is characterized
 * by a value. For reporting appropriate locations of an error to the user a
 * locator is contained also. Both value and locator are immutable, i.e. they
 * are mandatory in the constructor and no setters are provided.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractCommand implements Command {

  /**
   * The field {@code locator} contains the locator for the command.
   */
  private Locator locator = null;

  /**
   * The field {@code value} contains the value for the command.
   */
  private Token value = null;

  /**
   * Creates a new object.
   *
   * @param value   the value
   * @param locator the locator
   */
  public AbstractCommand( Token value, Locator locator ) {

    this.value = value;
    this.locator = locator;
  }

  /**
   * Getter for the Locator.
   *
   * @return the locator
   */
  public Locator getLocator() {

    return locator;
  }

  public Token getValue() {

    return value;
  }

}
