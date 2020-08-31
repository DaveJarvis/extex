/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a string valued field local to an entry. This class is
 * not related to externally stored values but used internally only.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TLocalLocator extends TLiteral {

  /**
   * This enumeration denotes the fields at hand.
   */
  public enum LocatorField {
    /**
     * The resource of the locator.
     */
    RESOURCE {
      @Override
      String get( Entry entry ) {

        return entry.getLocator().getResourceName();
      }
    },
    /**
     * The line of the locator.
     */
    LINE {
      @Override
      String get( Entry entry ) {

        return Integer.toString( entry.getLocator().getLineNumber() );
      }
    };

    /**
     * Get the value of the field from the entry's locator.
     *
     * @param entry the entry
     * @return the value of the field as String
     */
    abstract String get( Entry entry );
  }

  /**
   * The field {@code field} contains the specification of the field of the
   * locator.
   */
  private final LocatorField field;

  /**
   * Create a new object.
   *
   * @param value   the value
   * @param locator the locator
   * @param field   the field of the locator
   * @throws ExBibException in case of an error
   */
  public TLocalLocator( String value, Locator locator, LocatorField field )
      throws ExBibException {

    super( value, locator );
    if( field == null ) {
      throw new IllegalArgumentException( "field" );
    }
    this.field = field;
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

    processor.push( new TString( field.get( entry ), locator ) );
  }

  /**
   * java.lang.Object[])
   */
  @Override
  public void visit( TokenVisitor visitor, Object... args )
      throws ExBibException {

    visitor.visitLocalLocator( this, args );
  }

}
