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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code &gt;}
 * <p>
 * This function pops two numeric arguments from the stack and compares them. It
 * succeeds if the first argument is less than the second argument. In case of
 * success the integer 1 is pushed otherwise the integer 0.
 * </p>
 * <p>
 * If the stack does not contain enough items or the arguments are not integers
 * then an error is raised.
 * </p>
 * <img src="doc-files/gt.png" alt="gt">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *     { namesleft #0 &gt; }
 *     {
 *       % some actions
 *
 *       namesleft #1 - 'namesleft :=
 *     }
 *   while$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>Pops the top two (integer) literals, compares them, and pushes the
 * integer 1 if the second is greater than the first, 0 otherwise.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Gt extends AbstractCode {

  /**
   * Create a new object.
   */
  public Gt() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public Gt( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    int a = processor.popInteger( locator ).getInt();
    int b = processor.popInteger( locator ).getInt();
    processor.push( a < b ? TokenFactory.T_ONE : TokenFactory.T_ZERO );
  }

}
