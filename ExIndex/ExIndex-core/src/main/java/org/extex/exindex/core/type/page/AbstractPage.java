/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.page;

/**
 * This is the abstract base class for pages.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractPage implements PageReference {

  /**
   * This is a factory method for a page reference. This factory makes a
   * distinction of the following types of page numbers:
   * <ul>
   * <li>numeric page numbers</li>
   * <li>uppercase roman numeral page numbers</li>
   * <li>lowercase roman numeral page numbers</li>
   * <li>uppercase letter page numbers</li>
   * <li>lowercase letter page numbers</li>
   * <li>other page numbers</li>
   * </ul>
   *
   * @param p     the page specification
   * @param encap the encapsulator, which might be {@code null}
   * @return an appropriate instance of a page reference
   */
  public static PageReference get( String p, String encap ) {

    PageReference page;
    if( p.matches( "^-?[0-9]+$" ) ) {
      page = new NumericPage( encap, p );
    }
    else if( p.matches( "^[IVXLCM]+$" ) ) {
      page = new UpperRomanPage( encap, p );
    }
    else if( p.matches( "^[ivxlcm]+$" ) ) {
      page = new LowerRomanPage( encap, p );
    }
    else if( p.matches( "^[A-Z]+$" ) ) {
      page = new UpperPage( encap, p );
    }
    else if( p.matches( "^[a-z]+$" ) ) {
      page = new LowerPage( encap, p );
    }
    else {
      page = new SomePage( encap, p );
    }
    return page;
  }

  /**
   * The field {@code enc} contains the encapsulator.
   */
  private final String enc;

  /**
   * The field {@code page} contains the page number.
   */
  private final String page;

  /**
   * The field {@code ord} contains the ordinal number or -1 for none.
   */
  private final int ord;

  /**
   * Creates a new object.
   *
   * @param enc  the encapsulator
   * @param page the page number
   * @param ord  the ordinal number
   */
  public AbstractPage( String enc, String page, int ord ) {

    this.enc = enc;
    this.page = page;
    this.ord = ord;
  }

  public String getEncap() {

    return enc;
  }

  public int getOrd() {

    return ord;
  }

  public String getPage() {

    return page;
  }

  @Override
  public String toString() {

    return page;
  }

}
