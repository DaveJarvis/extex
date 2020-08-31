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

package org.extex.exbib.core.bst.token.impl;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.exception.ExBibEmptyFunctionNameException;
import org.extex.exbib.core.bst.token.AbstractToken;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFunctionUndefinedException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a literal token which corresponds to a macro.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TLiteral extends AbstractToken {

  /**
   * Creates a new object.
   *
   * @param locator the locator
   * @param value   the literal value
   * @throws ExBibEmptyFunctionNameException in case that the given value is
   *                                         {@code null} or the empty string
   */
  public TLiteral( String value, Locator locator )
      throws ExBibEmptyFunctionNameException {

    super( value, locator );

    if( value == null || "".equals( value ) ) {
      throw new ExBibEmptyFunctionNameException( locator );
    }
  }

  /**
   * The definition of the literal as function is sought and the respective
   * code is executed. If no function definition is found then an Exception is
   * thrown.
   *
   * @param processor the processor context
   * @param entry     the current entry or {@code null}
   * @param locator   the locator
   * @throws ExBibException in case that something else goes wrong,
   * especially<br>
   *                        ExBibFunctionUndefinedException in case that
   *                        the function named
   *                        in this object is not defined
   * @see org.extex.exbib.core.bst.code.Code#execute(org.extex.exbib.core.bst.BstProcessor,
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  @Override
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    String name = getValue();
    processor.step( name );

    Code code = processor.getFunction( name );

    if( code == null ) {
      throw new ExBibFunctionUndefinedException( name, locator );
    }

    code.execute( processor, entry, locator );
  }

  /**
   * The expansion of a Literal is the value of the macro stored under this
   * name. If the macro is not defined then it expands to the empty string.
   */
  @Override
  public String expand( Processor processor ) {

    String macro = processor.getMacro( getValue() );
    return macro != null ? macro : "";
  }

  /**
   * java.lang.Object[])
   */
  @Override
  public void visit( TokenVisitor visitor, Object... args )
      throws ExBibException {

    visitor.visitLiteral( this, args );
  }

}
