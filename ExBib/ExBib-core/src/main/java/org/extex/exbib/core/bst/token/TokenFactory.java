/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.token;

import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;

/**
 * The token factory is a place to request tokens from. Currently only a few
 * constant tokens can be received from the factory.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public final class TokenFactory {

  /* Poor man's symbol table: created once, used often */

  /**
   * The {@link TInteger TInteger} with the value 1.
   */
  public static final TInteger T_ONE = new TInteger( 1, null );

  /**
   * The {@link TInteger TInteger} with the value 0.
   */
  public static final TInteger T_ZERO = new TInteger( 0, null );

  /**
   * The {@link TString TString} with the value "".
   */
  public static final TString T_EMPTY = new TString( "", null );

  /**
   * The TString containing the quote character only.
   */
  public static final TString T_QUOTE = new TString( "\"", null );


  private TokenFactory() {

    // not used
  }

}
