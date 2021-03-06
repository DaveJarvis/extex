/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.noad;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;

import java.util.logging.Logger;

/**
 * This class provides a container for Noads and Nodes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @see "TTP [???]"
 */
public class MathList extends AbstractNoad {

  /**
   * The field {@code list} is the container for the elements of this node
   * list.
   */
  private final NoadList nucleus = new NoadList();

  /**
   * Creates a new object without any items.
   */
  public MathList() {

  }

  /**
   * Add an arbitrary knot to the list.
   *
   * @param noad the noad to add
   */
  public void add( Noad noad ) {

    nucleus.add( noad );
  }

  /**
   * Test whether the node list is empty.
   *
   * @return {@code true}, if the list is empty, otherwise
   * {@code false}.
   */
  public boolean empty() {

    return nucleus.size() == 0;
  }

  /**
   * Getter for a node at a given position.
   *
   * @param index the position
   * @return the node at position <i>index</i> of {@code null} if index
   * is out of bounds
   */
  public Noad get( int index ) {

    return nucleus.get( index );
  }

  /**
   * Getter for the last noad previously stored.
   *
   * @return the last noad or {@code null} if none is available
   */
  public Noad getLastNoad() {

    return nucleus.getLastNoad();
  }

  /**
   * Remove an element at a given position.
   *
   * @param index the position
   * @return the element previously located at position <i>index</i>
   */
  public Noad remove( int index ) {

    return this.nucleus.remove( index );
  }

  /**
   * Return the size of the {@code MathList}.
   *
   * @return the size of the {@code MathList}
   */
  public int size() {

    return nucleus.size();
  }

  /**
   * Get the string representation of this object for debugging purposes.
   *
   * @return the string representation
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    toString( sb );
    return sb.toString();
  }

  /**
   * Produce a printable representation of the noad in a StringBuilder.
   *
   * @param sb the string buffer
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder)
   */
  @Override
  public void toString( StringBuilder sb ) {

    for( int i = 0; i < nucleus.size(); i++ ) {
      nucleus.get( i ).toString( sb );
    }
  }

  /**
   * Produce a printable representation to a certain depth of the noad.
   *
   * @param sb    the string buffer
   * @param depth the depth to which the full information should be given
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder, int)
   */
  @Override
  public void toString( StringBuilder sb, int depth ) {

    if( depth < 0 ) {
      sb.append( "{}" );
    }
    else {
      for( int i = 0; i < nucleus.size(); i++ ) {
        nucleus.get( i ).toString( sb, depth - 1 );
      }
    }
  }

  /**
   * Translate a Noad into a NodeList.
   *
   * @param previousNoad the previous noad
   * @param noads        the list of noads currently processed
   * @param index        the index of the current node in the list
   * @param list         the list to add the nodes to. This list contains
   *                     the Nodes
   *                     previously typeset. Thus it can be used to look back
   * @param mathContext  the context to consider
   * @param logger       the logger for debugging and tracing information
   * @throws TypesetterException    in case of a problem
   * @throws ConfigurationException in case of a configuration problem
   * @see org.extex.typesetter.type.noad.Noad#typeset(org.extex.typesetter.type.noad.Noad,
   * org.extex.typesetter.type.noad.NoadList, int,
   * org.extex.typesetter.type.NodeList,
   * org.extex.typesetter.type.noad.util.MathContext,
   * java.util.logging.Logger)
   */
  @Override
  public void typeset( Noad previousNoad, NoadList noads, int index,
                       NodeList list, MathContext mathContext, Logger logger )
      throws TypesetterException,
      ConfigurationException {

    Noad previous = null;
    Noad n;

    for( int i = 0; i < nucleus.size(); i++ ) {
      n = nucleus.get( i );
      n.typeset( previous, nucleus, i, list, mathContext, logger );
      previous = n;
    }
  }

}
