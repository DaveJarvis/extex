/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code.impl;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code char.to.int$}
 * <p>
 * This function translates a character to the corresponding integer code point.
 * It takes a string argument from the stack. This argument must contain exactly
 * one character.
 * </p>
 * <p>
 * Note that B<small>IB</small><span style="margin-left: -0.15em;"
 * >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X&nbsp;0.99c and B<small>IB</small><span
 * style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X&nbsp;8 restrict the characters to 8~bit characters.
 * ??Bib has expanded the definition to 16~bit Unicode
 * characters.
 * </p>
 * <img src="doc-files/chr.to.int.png" alt="chr.to.int">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *   "a" chr.to.int$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation:</dt>
 * <dd>Pops the top (string) literal, makes sure it's a single character,
 * converts it to the corresponding ASCII integer, and pushes this integer.</dd>
 * </dl>
 *
 * <dl>
 * <dt>BibTeX web documentation:</dt>
 * <dd>The {@code built_in} function {@code chr.to.int$} pops the top
 * (string) literal, makes sure it's a single character, converts it to the
 * corresponding {@code ASCII_code} integer, and pushes this integer. If
 * the literal isn't an appropriate string, it complains and pushes the integer
 * 0.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ChrToInt extends AbstractCode {

  /**
   * Create a new object.
   */
  public ChrToInt() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public ChrToInt( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  @Override
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    String c = processor.popString( locator ).getValue();

    if( c.length() != 1 ) {
      Localizer localizer = LocalizerFactory.getLocalizer( getClass() );
      throw new ExBibIllegalValueException( localizer.format(
          "invalid.length", c ), locator );
    }

    processor.push( new TInteger( c.charAt( 0 ), locator ) );
  }

}
