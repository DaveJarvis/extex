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
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code warning$}
 * <p>
 * This function pops a string from the stack and prints it as a warning to the
 * log stream. The message is terminated by a newline character. The line in the
 * log file may be rewrapped to fit into a given line length.
 * </p>
 * <p>
 * An empty stack leads to an error as well as a wrong type argument on the
 * stack.
 * </p>
 * <img src="doc-files/warning.png" alt="warning">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *   "there's a number but no series in " cite$ * warning$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>Pops the top (string) literal and prints it following a warning message.
 * This also increments a count of the number of warning messages issued.</dd>
 * </dl>
 *
 * <dl>
 * <dt>BibTeX web documentation:</dt>
 * <dd>The {@code built_in} function {@code warning$} pops the top
 * (string) literal and prints it following a warning message. This is
 * implemented as a special {@code built_in} function rather than using the
 * {@code top$} function so that it can {@code mark_warning}.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Warning extends AbstractCode {

  /**
   * The field {@code localizer} contains the cached localizer.
   */
  private transient Localizer localizer = null;

  /**
   * Create a new object.
   */
  public Warning() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public Warning( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    Token arg = processor.popString( locator );

    if( localizer == null ) {
      localizer = LocalizerFactory.getLocalizer( getClass() );
    }

    processor.warning( localizer.format( "Message", arg.getValue() ) );
  }

}
