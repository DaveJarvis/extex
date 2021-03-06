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
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code cite$}
 * <p>
 * This function takes the citation key of the current entry and pushes the
 * string value to the stack. If there is no current entry then an error is
 * raised.
 * </p>
 * <img src="doc-files/cite.png" alt="cite">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *   "there's a month but no year in " cite$ * warning$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>Pushes the string that was the {@code \cite}-command argument for
 * this entry.</dd>
 * </dl>
 *
 * <dl>
 * <dt>BibTeX web documentation:</dt>
 * <dd>The {@code built_in} function {@code cite$} pushes the
 * appropriate string from {@code cite_list} onto the stack.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Cite extends AbstractCode {

  /**
   * Create a new object.
   */
  public Cite() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public Cite( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    if( entry == null ) {
      throw new ExBibMissingEntryException( null, locator );
    }

    String key = entry.getKey();
    String cite = processor.getCite( key );
    processor.push( new TString( (cite != null ? cite : key), locator ) );
  }

}
