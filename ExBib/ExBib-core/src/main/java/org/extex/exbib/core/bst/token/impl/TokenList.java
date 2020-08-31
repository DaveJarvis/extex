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
import org.extex.exbib.core.exceptions.ExBibMissingLiteralException;
import org.extex.exbib.core.io.Locator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a container for a sorted sequence of {@link Token Token}s. In
 * addition to the operation as container it also implements the interface
 * {@link Token Token} itself to act as one.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TokenList extends AbstractToken implements Iterable<Token> {

  /**
   * The field {@code value} contains the internal representation upon which
   * the list is based.
   */
  private final List<Token> value = new ArrayList<>();

  /**
   * Creates a new object.
   *
   * @param locator the locator
   */
  public TokenList( Locator locator ) {

    super( null, locator );
  }

  /**
   * Add a token to the end of the token list.
   *
   * @param t the token to add
   */
  public void add( Token t ) {

    value.add( t );
  }

  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    for( Token t : value ) {
      t.execute( processor, entry, locator );
    }
  }

  @Override
  public String expand( Processor processor ) throws ExBibException {

    StringBuilder sb = new StringBuilder();

    for( Token t : value ) {
      sb.append( t.expand( processor ) );
    }

    return sb.toString();
  }

  /**
   * Getter for an element.
   *
   * @param index the index of the element
   * @return the element
   * @see java.util.List#get(int)
   */
  public Token get( int index ) {

    return value.get( index );
  }

  /**
   * Compute a printable representation of this object.
   *
   * @return the printable representation
   */
  @Override
  protected String getString() {

    String sep = " ";
    StringBuilder sb = new StringBuilder();
    boolean first = true;

    for( Token t : value ) {
      if( first ) {
        first = false;
      }
      else {
        sb.append( sep );
      }
      sb.append( t.toString() );
    }

    return sb.toString();
  }

  @Override
  public String getValue() {

    StringBuilder sb = new StringBuilder();

    for( Token t : value ) {
      sb.append( t.toString() );
    }

    return sb.toString();
  }

  /**
   * Getter for the iterator.
   *
   * @return the iterator
   */
  public Iterator<Token> iterator() {

    return value.iterator();
  }

  /**
   * Getter for the size.
   *
   * @return the size
   * @see java.util.List#size()
   */
  public int size() {

    return value.size();
  }

  /**
   * Transform the TokenList into a {@link List} if the elements
   * are of type {@link TLiteral TLiteral} only. If some other elements are
   * found then an exception is thrown.
   *
   * @return the StringList of the literal names
   * @throws ExBibMissingLiteralException if some other {@link Token} than a
   *                                      {@link TLiteral} is found
   */
  public List<String> toStringList() throws ExBibMissingLiteralException {

    List<String> list = new ArrayList<>();

    for( Token t : value ) {
      if( !(t instanceof TLiteral) ) {
        throw new ExBibMissingLiteralException( t.toString(), null );
      }

      list.add( t.getValue() );
    }

    return list;
  }

  public void visit( TokenVisitor visitor, Object... args )
      throws ExBibException {

    visitor.visitTokenList( this, args );
  }

}
