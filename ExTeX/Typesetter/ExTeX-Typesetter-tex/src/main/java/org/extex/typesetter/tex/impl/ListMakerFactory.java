/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tex.impl;

import org.extex.core.Locator;
import org.extex.interpreter.ListMakers;
import org.extex.typesetter.ListMakerType;
import org.extex.typesetter.ListManager;
import org.extex.typesetter.listMaker.*;
import org.extex.typesetter.listMaker.math.MathListMaker;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class ListMakerFactory {

  /**
   * This interface describes a function object to create a list manager.
   */
  protected interface CC {

    /**
     * This is the creator function.
     *
     * @param manager the manager
     * @param locator the locator
     * @return the new instance
     */
    TokenDelegateListMaker create( ListManager manager, Locator locator );
  }

  /**
   * The field {@code CREATORS} contains the mapping from symbolic names to
   * the creator function.
   */
  private static final Map<ListMakerType, CC> CREATORS =
      new HashMap<ListMakerType, CC>();

  static {
    CREATORS.put( ListMakers.HORIZONTAL, new CC() {

      public TokenDelegateListMaker create( ListManager manager,
                                            Locator locator ) {

        return new HorizontalListMaker( manager, locator );
      }
    } );
    CREATORS.put( ListMakers.VERTICAL, new CC() {

      public TokenDelegateListMaker create( ListManager manager,
                                            Locator locator ) {

        return new VerticalListMaker( manager, locator );
      }
    } );
    CREATORS.put( ListMakers.RESTRICTED_HORIZONTAL, new CC() {

      public TokenDelegateListMaker create( ListManager manager,
                                            Locator locator ) {

        return new RestrictedHorizontalListMaker( manager, locator );
      }
    } );
    CREATORS.put( ListMakers.INNER_VERTICAL, new CC() {

      public TokenDelegateListMaker create( ListManager manager,
                                            Locator locator ) {

        return new InnerVerticalListMaker( manager, locator );
      }
    } );
    CREATORS.put( ListMakers.MATH, new CC() {

      public TokenDelegateListMaker create( ListManager manager,
                                            Locator locator ) {

        return new MathListMaker( manager, locator );
      }
    } );
  }


  public ListMakerFactory() {

  }

  /**
   * Create a new list maker according to the specified type. If the requested
   * type is not known then an UnsupportedOperationException is thrown.
   *
   * @param manager the typesetter
   * @param type    the type
   * @param locator the locator
   * @return the new instance
   */
  protected TokenDelegateListMaker createListMaker( ListManager manager,
                                                    ListMakerType type,
                                                    Locator locator ) {

    CC cc = CREATORS.get( type );
    if( cc == null ) {
      throw new UnsupportedOperationException( type.toString() );
    }
    return cc.create( manager, locator );
  }

}
