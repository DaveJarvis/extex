/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.name;

import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;

import java.util.List;

/**
 * This factory manages names. It implements a pipe of parsed values. This is a
 * tradeoff between the time to parse and the memory to keep the parsed values.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public final class NameFactory {

  /**
   * This class is a container for a pair consisting of a string and a list of
   * names.
   */
  private static class PipeItem {

    /**
     * The field {@code key} contains the key.
     */
    private String key;

    /**
     * The field {@code value} contains the value.
     */
    private List<Name> value;

    /**
     * Creates a new object.
     *
     * @param key   the key
     * @param value the value
     */
    protected PipeItem( String key, List<Name> value ) {

      this.key = key;
      this.value = value;
    }

    @Override
    public String toString() {

      return key + " -> " + value.toString() + "\n";
    }

  }

  /**
   * The field {@code PIPE_SIZE} contains the size of the cache.
   */
  private static final int PIPE_SIZE = 8;

  /**
   * The field {@code highlander} contains the only one.
   */
  private static final NameFactory highlander = new NameFactory();

  /**
   * Getter for the only instance.
   *
   * @return the only instance
   */
  public static NameFactory getFactory() {

    return highlander;
  }

  /**
   * The field {@code pipe} contains the cache.
   */
  private PipeItem[] pipe = new PipeItem[ PIPE_SIZE ];

  /**
   * The field {@code pp} contains the pointer to the next insertion point in
   * the pipe.
   */
  private int pp = 0;


  private NameFactory() {


  }

  /**
   * Getter for the name list associated with this object. The name list can
   * be used to store the result of parsing the value to names. Thus it works
   * as cache.
   *
   * @param s       the string to be decomposed into names
   * @param locator the locator for error messages
   * @return the names which is never {@code null}
   * @throws ExBibNoNameException     in case of an error
   * @throws ExBibSyntaxException     in case of an error
   * @throws ExBibImpossibleException in case of an error which should not
   *                                  happen
   */
  public List<Name> getNames( String s, Locator locator )
      throws ExBibSyntaxException,
      ExBibNoNameException,
      ExBibImpossibleException {

    String key = s.trim();

    for( int i = 0; i < PIPE_SIZE; i++ ) {
      PipeItem pi = pipe[ i ];
      if( pi != null && pi.key.equals( s ) ) {
        pp = (i + 1) % PIPE_SIZE;
        return pi.value;
      }
    }

    List<Name> names = Name.parse( key, locator );
    if( pipe[ pp ] == null ) {
      pipe[ pp ] = new PipeItem( key, names );
    }
    else {
      pipe[ pp ].key = key;
      pipe[ pp ].value = names;
    }

    return names;
  }

  /**
   * Reset the factory to get rid of cached names.
   */
  protected void reset() {

    pipe = new PipeItem[ PIPE_SIZE ];
    pp = 0;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    for( PipeItem pi : pipe ) {
      if( pi != null ) {
        sb.append( pi.toString() );
      }
      else {
        sb.append( "-\n" );
      }
    }
    return sb.toString();
  }

}
