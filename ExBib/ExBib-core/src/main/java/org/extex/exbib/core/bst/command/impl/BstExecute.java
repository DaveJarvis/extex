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
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

import java.io.IOException;

/**
 * This class represents an {@code EXECUTE} command.
 * <p>
 * The {@code execute} command executes the function in the argument. There is
 * no current entry then this code is executed.
 * </p>
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *   EXECUTE {begin.bib}
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BstExecute extends AbstractCommand {

  /**
   * Creates a new object.
   *
   * @param value   the value of this Command
   * @param locator the locator from the users perspective
   * @throws ExBibIllegalValueException in case of a {@code null} value
   */
  public BstExecute( Token value, Locator locator )
      throws ExBibIllegalValueException {

    super( value, locator );

    if( value == null ) {
      Localizer localizer = LocalizerFactory.getLocalizer( getClass() );
      throw new ExBibIllegalValueException(
          localizer.format( "empty.token" ), locator );
    }
  }

  /**
   * org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Locator locator )
      throws ExBibException {

    getValue().execute( processor, null, locator );
  }

  /**
   * Compute a printable string representation for this object.
   *
   * @return the string representation
   */
  @Override
  public String toString() {

    return "EXECUTE { " + getValue().getValue() + " }";
  }

  /**
   * java.lang.Object[])
   */
  public void visit( CommandVisitor visitor, Object... args )
      throws IOException,
      ExBibException {

    visitor.visitExecute( this, args );
  }
}
