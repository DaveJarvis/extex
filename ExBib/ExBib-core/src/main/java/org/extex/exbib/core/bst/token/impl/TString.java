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
import org.extex.exbib.core.bst.token.AbstractToken;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a {@code String} token, i.e. a value enclosed in
 * double quotes.
 *
 * <p>
 * You can try to store the {@code null} value in a TString. This is used
 * to indicate a missing field. The reported value is the empty string.
 * Nevertheless the method {@link #isNull() isNull} can be used to distinguish
 * between those two cases.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TString extends AbstractToken {

  /**
   * Creates a new object.
   *
   * @param value   the value
   * @param locator the locator
   */
  public TString( String value, Locator locator ) {

    super( value, locator );
  }

  /**
   * Comparison is reduced to the comparison of the values.
   *
   * @param that other string to compare to
   * @return {@code true} iff the values are equal
   */
  @Override
  public boolean equals( Object that ) {

    return that instanceof TString
        && getValue().equals( ((TString) that).getValue() );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    processor.step( toString() );
    processor.push( this );
  }

  /**
   * Compute the string representation for this object. This value is used by
   * the method {@link #toString() toString()}.
   *
   * @return the string representation
   */
  @Override
  protected String getString() {

    return "\"" + getValue() + "\"";
  }

  @Override
  public int hashCode() {

    return getValue().hashCode();
  }

  /**
   * java.lang.Object[])
   */
  public void visit( TokenVisitor visitor, Object... args )
      throws ExBibException {

    visitor.visitString( this, args );
  }

}
