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
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

import java.util.logging.Logger;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code top$}
 * <p>
 * This function pops the topmost literal from the stack and prints it to the
 * log stream. This function is meant for debugging purposes. If the stack is
 * empty an error is raised.
 * </p>
 * <img src="doc-files/top.png" alt="top">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *  top$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>Pops and prints the top of the stack on the terminal and log file. It's
 * useful for debugging.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Top extends AbstractCode {

  /**
   * Create a new object.
   */
  public Top() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public Top( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    String a = processor.pop( locator ).toString();
    Logger logger = processor.getLogger();
    if( logger != null ) {
      logger.info( a + "\n" );
    }
  }

}
