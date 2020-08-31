/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.normalizer;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CollatorPipe implements Collator {

  /**
   * The field {@code c1} contains the first collator.
   */
  private final Collator c1;

  /**
   * The field {@code c2} contains the second collator.
   */
  private final Collator c2;

  /**
   * Creates a new object.
   *
   * @param c1 the first collator
   * @param c2 the second collator
   */
  public CollatorPipe( Collator c1, Collator c2 ) {

    this.c1 = c1;
    this.c2 = c2;
  }

  @Override
  public String collate( String s ) {

    return c2.collate( c1.collate( s ) );
  }

}
