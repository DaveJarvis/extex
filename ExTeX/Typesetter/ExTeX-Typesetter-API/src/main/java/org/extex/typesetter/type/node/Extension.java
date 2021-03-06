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

package org.extex.typesetter.type.node;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;

import java.io.Serializable;

/**
 * This interface describes the capabilities for an extension object to be
 * inserted into an extension node.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface Extension extends Serializable {

  /**
   * Getter for the depth of the node.
   *
   * @return the depth
   */
  Dimen getDepth();

  /**
   * Getter for the height of the node.
   *
   * @return the height
   */
  Dimen getHeight();

  /**
   * Getter for the width of the node.
   *
   * @return the width
   */
  Dimen getWidth();

  /**
   * Setter for the depth of the node.
   *
   * @param depth the nde depth
   */
  void setDepth( FixedDimen depth );

  /**
   * Setter for the height of the node.
   *
   * @param height the new height
   */
  void setHeight( FixedDimen height );

  /**
   * Setter for the width of the node.
   *
   * @param width the new width
   */
  void setWidth( FixedDimen width );

  /**
   * This method puts the printable representation into the StringBuilder.
   * This is meant to produce a exhaustive form as it is used in tracing
   * output to the log file.
   *
   * @param sb     the output string buffer
   * @param prefix the prefix string inserted at the beginning of each line
   */
  void toString( StringBuilder sb, String prefix );

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a short form only as it is used in error
   * messages to the user.
   *
   * @param sb     the output string buffer
   * @param prefix the prefix string inserted at the beginning of each line
   */
  void toText( StringBuilder sb, String prefix );

}
