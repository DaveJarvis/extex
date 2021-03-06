/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

import java.io.IOException;

/**
 * This is the abstract base class for all value items.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractValueItem implements ValueItem {

  /**
   * The field {@code content} contains the content of the value.
   */
  private String content;

  /**
   * Creates a new object.
   *
   * @param content the initial value
   */
  public AbstractValueItem( String content ) {

    this.content = content;
  }

  /**
   * This is the default implementation of an expansion. It simply expands the
   * content of the object.
   *
   * @param sb the target StringBuilder
   * @param db the database context
   */
  @Override
  public void expand( StringBuilder sb, DB db ) {

    sb.append( content );
  }

  /**
   * Getter for the value of the object.
   *
   * @return the value
   */
  @Override
  public String getContent() {

    return content;
  }

  /**
   * Setter for the value.
   *
   * @param content the new value
   */
  @Override
  public void setContent( String content ) {

    this.content = content;
  }

  /**
   * Return a String representation of the object suitable for a
   * BibTeX file.
   *
   * @return the string representation
   */
  @Override
  public String toString() {

    return getContent();
  }

  /**
   * The visitor pattern is used to distinguish the different subtypes. Thus
   * the derived classes are forced to overwrite this method.
   *
   * @param visitor the visitor
   * @param db      the database context
   * @throws IOException just in case
   */
  @Override
  public abstract void visit( ValueVisitor visitor, DB db ) throws IOException;
}
