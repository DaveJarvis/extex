/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.math;

import org.extex.core.exception.helping.MissingMathException;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.listMaker.math.NoadConsumer;

/**
 * This is the base class for all math primitives using the TeX encoding.
 * It tries to ensure that the primitive is invoked in math mode only.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractMathCode extends AbstractCode {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public AbstractMathCode( CodeToken token ) {

    super( token );
  }

  /**
   * Get the current list maker as Noad consumer. If the current list maker is
   * not of the proper type then an exception is thrown.
   *
   * @param context    the interpreter context
   * @param typesetter the master typesetter
   * @return the current list maker
   * @throws MissingMathException in case that the current mode is not a
   *                              math mode
   */
  protected NoadConsumer getListMaker( Context context,
                                       Typesetter typesetter )
      throws MissingMathException {

    ListMaker lm = typesetter.getListMaker();
    if( !(lm instanceof NoadConsumer) ) {
      throw new MissingMathException( toText( context ) );
    }
    return (NoadConsumer) lm;
  }

}
