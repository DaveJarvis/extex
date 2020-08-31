/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.backend.pageFilter.selector;

import org.extex.backend.exception.BackendException;
import org.extex.backend.exception.BackendMissingTargetException;
import org.extex.backend.pageFilter.PagePipe;
import org.extex.typesetter.type.page.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * This page filter selects some pages to be shipped out.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PageSelector implements PagePipe {

  /**
   * The field {@code out} contains the output target.
   */
  private PagePipe out = null;

  /**
   * The field {@code pageNo} contains the current page number.
   */
  private int pageNo = 0;

  /**
   * The field {@code ranges} contains the list of ranges to check.
   */
  private List<Rule> ranges = null;


  public PageSelector() {

    pageNo = 0;
  }

  /**
   * Creates a new object.
   *
   * @param spec the specification of the pages to select
   */
  public PageSelector( String spec ) {

    addSelector( spec );
  }

  /**
   * Add some specifications for pages to be selected.
   *
   * @param spec the specification of the pages to select
   */
  public void addSelector( String spec ) {

    ranges = new ArrayList<Rule>();
    String[] r = spec.split( "," );

    for( int i = 0; i < r.length; i++ ) {
      String s = r[ i ].trim();
      int j = s.indexOf( '-' );
      if( j < 0 ) {
        int from = Integer.parseInt( s );
        addRule( new IntervalRule( from, from ) );
      }
      else if( j == 0 ) {
        addRule( new IntervalRule( Integer.MAX_VALUE, Integer.parseInt( s
                                                                            .substring(
                                                                                1 ) ) ) );
      }
      else if( j == s.length() - 1 ) {
        addRule( new IntervalRule( Integer.parseInt( s.substring( 0, s
            .length() - 2 ) ), Integer.MAX_VALUE ) );
      }
      else {
        addRule( new IntervalRule( Integer
                                       .parseInt( s.substring( 0, j - 1 ) ),
                                   Integer.parseInt( s
                                                         .substring( j ) ) ) );
      }
      //TODO gene: extend the syntax for mod
    }
  }

  /**
   * Add a rule to the set of rules.
   *
   * @param rule the rule to add
   */
  protected void addRule( IntervalRule rule ) {

    ranges.add( rule );
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.backend.pageFilter.PagePipe#close()
   */
  public void close() throws BackendException {

    if( out == null ) {
      throw new BackendMissingTargetException();
    }

    out.close();
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.backend.pageFilter.PagePipe#setOutput(
   *org.extex.backend.pageFilter.PagePipe)
   */
  public void setOutput( PagePipe pipe ) {

    this.out = pipe;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.backend.pageFilter.PagePipe#setParameter(
   *java.lang.String,
   * java.lang.String)
   */
  public void setParameter( String name, String value ) {

    //not needed
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.backend.pageFilter.PagePipe#shipout(
   *org.extex.typesetter.type.page.Page)
   */
  public void shipout( Page page ) throws BackendException {

    if( out == null ) {
      throw new BackendMissingTargetException();
    }

    pageNo++;

    if( ranges == null ) {
      out.shipout( page );
      return;
    }

    int size = ranges.size();
    for( int i = 0; i < size; i++ ) {
      if( ranges.get( i ).check( pageNo ) ) {
        out.shipout( page );
        return;
      }
    }
  }

}
