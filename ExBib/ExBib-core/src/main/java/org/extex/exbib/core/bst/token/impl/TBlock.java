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

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.AbstractToken;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

import java.util.Iterator;

/**
 * This class represents a list of values.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TBlock extends AbstractToken implements Iterable<Token> {

  /**
   * The field {@code value} contains the list of Tokens in the block.
   */
  private TokenList value = null;

  /**
   * Creates a new object without any elements.
   *
   * @param locator the locator
   */
  public TBlock( Locator locator ) {

    super( locator );
    value = new TokenList( locator );
  }

  /**
   * Adds a Token to the end of the list of Tokens in this block.
   *
   * @param token the Token to add
   */
  public void add( Token token ) {

    value.add( token );
  }

  /**
   * {@inheritDoc}
   * <p>
   * To execute an object of this class the token list of the value is simply
   * pushed to the stack of the processor for further treatment.
   *
   * @see org.extex.exbib.core.bst.code.Code#execute(org.extex.exbib.core.bst.BstProcessor,
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    processor.push( value );
  }

  @Override
  public String expand( Processor processor ) throws ExBibException {

    return value.expand( processor );
  }

  @Override
  protected String getString() {

    return "{" + value.toString() + "}";
  }

  /**
   * Getter for the TokenList contained in this block.
   *
   * @return the contents
   */
  public TokenList getTokenList() {

    return value;
  }

  public Iterator<Token> iterator() {

    return value.iterator();
  }

  /**
   * java.lang.Object[])
   */
  public void visit( TokenVisitor visitor, Object... args )
      throws ExBibException {

    visitor.visitBlock( this, args );
  }

}
