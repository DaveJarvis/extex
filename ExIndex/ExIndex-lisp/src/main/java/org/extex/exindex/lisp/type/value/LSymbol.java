/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.lisp.type.value;

import java.io.PrintStream;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * This class is a node containing a symbol. A symbol is contained in a symbol
 * table and the contract guarantees that for any name only one symbol exists in
 * the system.
 * <p>
 * This means that symbols can be compared with {@code ==}. The contract
 * of the LSymbol ensures that two symbols which compare as the same are the
 * same and two symbols which fail to compare equal are different. This means
 * the comparison of the mere text is not necessary.
 * </p>
 * <p>
 * The symbol tables talks care to free the memory as soon as no reference
 * exists in the system. Thus the symbol table relies on the cooperation of the
 * calling code to avoid memory leaks.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public final class LSymbol implements LValue {

  /**
   * The field {@code symbolTable} contains the symbol table.
   */
  private static final Map<String, LSymbol> SYMBOL_TABLE =
      new WeakHashMap<>();

  /**
   * This is the factory method for symbols.
   *
   * @param value the name
   * @return the symbol corresponding to the name
   */
  public static synchronized LSymbol get( String value ) {

    LSymbol sym = SYMBOL_TABLE.get( value );
    if( sym == null ) {
      sym = new LSymbol( value );
      if( value != null && !"".equals( value ) && value.charAt( 0 ) == ':' ) {
        sym.setMutable( false );
      }
      SYMBOL_TABLE.put( value, sym );
    }

    return sym;
  }

  /**
   * The field {@code value} contains the value.
   */
  private final String value;

  /**
   * The field {@code mutable} contains the indicator for non-constants.
   */
  private boolean mutable = true;

  /**
   * Creates a new object. In fact these instances are managed via the factory
   * method.
   *
   * @param value the value
   */
  private LSymbol( String value ) {

    this.value = value;
  }

  /**
   * Getter for value.
   *
   * @return the value
   */
  public String getValue() {

    return value;
  }

  /**
   * Getter for the {@code mutable} property.
   * This is a boolean property indicating the modifiable symbols in an
   * interpreter.
   *
   * @return the mutable
   */
  public boolean isMutable() {

    return mutable;
  }

  public void print( PrintStream stream ) {

    stream.print( value );
  }

  /**
   * Setter for mutable.
   *
   * @param mutable the mutable to set
   */
  public void setMutable( boolean mutable ) {

    this.mutable = mutable;
  }

  @Override
  public String toString() {

    return value;
  }

}
