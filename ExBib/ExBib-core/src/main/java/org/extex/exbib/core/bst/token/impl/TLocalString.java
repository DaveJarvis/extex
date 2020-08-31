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

package org.extex.exbib.core.bst.token.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.VString;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a string valued field local to an entry. This class is
 * not related to externally stored values but used internally only.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TLocalString extends TLiteral {

  /**
   * Create a new object.
   *
   * @param value   the value
   * @param locator the locator
   * @throws ExBibException in case of an error
   */
  public TLocalString( String value, Locator locator ) throws ExBibException {

    super( value, locator );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  @Override
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    if( entry == null ) {
      throw new ExBibMissingEntryException( null, locator );
    }

    VString val = (VString) entry.getLocal( getValue() );
    processor.push( val == null
                        ? TokenFactory.T_EMPTY
                        : new TString( val.getContent(), locator ) );
  }

  /**
   * java.lang.Object[])
   */
  @Override
  public void visit( TokenVisitor visitor, Object... args )
      throws ExBibException {

    visitor.visitLocalString( this, args );
  }

}
