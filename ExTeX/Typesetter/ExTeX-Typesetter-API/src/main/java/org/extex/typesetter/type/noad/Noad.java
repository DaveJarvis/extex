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
import org.extex.typesetter.type.noad.util.MathSpacing;

import java.util.logging.Logger;

/**
 * The interface Noad is a type of data structure which represents mathematical
 * constructions.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface Noad {

  /**
   * Getter for spacing class.
   *
   * @return the spacing class
   */
  MathSpacing getSpacingClass();

  /**
   * Getter for the subscript.
   *
   * @return the subscript.
   */
  Noad getSubscript();

  /**
   * Getter for the superscript.
   *
   * @return the superscript.
   */
  Noad getSuperscript();

  /**
   * Setter for spacing class.
   *
   * @param spacingClass the spacing class to set
   */
  void setSpacingClass( MathSpacing spacingClass );

  /**
   * Setter for the subscript.
   *
   * @param subscript the subscript to set.
   */
  void setSubscript( Noad subscript );

  /**
   * Setter for the superscript.
   *
   * @param superscript the superscript to set.
   */
  void setSuperscript( Noad superscript );

  /**
   * Produce a printable representation of the noad in a StringBuilder.
   *
   * @param sb the string buffer
   */
  void toString( StringBuilder sb );

  /**
   * Produce a printable representation to a certain depth of the noad.
   *
   * @param sb    the string buffer
   * @param depth the depth to which the full information should be given
   */
  void toString( StringBuilder sb, int depth );

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
   */
  void typeset( Noad previousNoad, NoadList noads, int index, NodeList list,
                MathContext mathContext, Logger logger )
      throws TypesetterException,
      ConfigurationException;

}
