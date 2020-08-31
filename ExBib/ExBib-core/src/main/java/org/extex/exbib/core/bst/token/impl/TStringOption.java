/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.token.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.AbstractToken;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This is a derived class to differentiate options from normal strings.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TStringOption extends AbstractToken {

  /**
   * Creates a new object.
   *
   * @param value   the value
   * @param locator the locator
   */
  public TStringOption( String value, Locator locator ) {

    super( value, locator );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibIllegalValueException {

    Token value = processor.getOption( getValue(), TokenFactory.T_EMPTY );
    processor.push( value );
  }

  /**
   * java.lang.Object[])
   */
  public void visit( TokenVisitor visitor, Object... args )
      throws ExBibException {

    visitor.visitStringOption( this, args );
  }

}
