/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.command.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.command.AbstractCommand;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.io.Locator;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class represents a {@code READ} command.
 * <p>
 * The {@code read} command read entries into the database. The database is
 * empty before the read command is encountered. The list of entries is ordered
 * according to the order they are encountered.
 * </p>
 *
 * <pre>
 *   READ
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BstRead extends AbstractCommand {

  /**
   * Creates a new object.
   *
   * @param locator the locator
   */
  public BstRead( Locator locator ) {

    super( null, locator );
  }

  /**
   * org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Locator locator )
      throws ExBibException {

    try {
      processor.loadDatabases();
    } catch( FileNotFoundException e ) {
      throw new ExBibFileNotFoundException( e.getMessage(), null );
    }
  }

  /**
   * Compute a printable string representation for this object.
   *
   * @return the string representation
   */
  @Override
  public String toString() {

    return "READ";
  }

  /**
   * java.lang.Object[])
   */
  public void visit( CommandVisitor visitor, Object... args )
      throws IOException,
      ExBibException {

    visitor.visitRead( this, args );
  }

}
