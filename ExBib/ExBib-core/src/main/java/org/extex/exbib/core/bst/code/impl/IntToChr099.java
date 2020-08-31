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
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code int.to.chr$}
 * <p>
 * This function takes an integer code point from the stack and translates it
 * into a single character sting containing the character associated to the code
 * point. This string is left on the stack.
 * </p>
 * <p>
 * This function limits its argument to the ASCII range 0-255.
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
 * characters. Thus in compatibility mode of ??Bib the
 * use of a number larger than 255 leads to an error. In
 * ??Bib native mode those numbers are treated
 * correctly as larger Unicode code points.
 * </p>
 * <img src="doc-files/int.to.chr.png" alt="int.to.chr">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *   #0 int.to.chr$
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation</dt>
 * <dd>Pops the top (integer) literal, interpreted as the ASCII integer value of
 * a single character, converts it to the corresponding single-character string,
 * and pushes this string.</dd>
 * </dl>
 *
 * <dl>
 * <dt>BibTeX web documentation:</dt>
 * <dd>The {@code built_in} function {@code int.to.chr$} pops the top
 * (integer) literal, interpreted as the {@code ASCII_code} of a single
 * character, converts it to the corresponding single-character string, and
 * pushes this string. If the literal isn't an appropriate integer, it complains
 * and pushes the null string.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IntToChr099 extends AbstractCode {

  /**
   * The field {@code CHAR_MAX} contains the maximum allowed character value.
   */
  private static final int CHAR_MAX = 255;

  /**
   * Create a new object.
   */
  public IntToChr099() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public IntToChr099( String name ) {

    super( name );
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  @Override
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    int arg = processor.popInteger( locator ).getInt();

    if( arg < 0 || arg > CHAR_MAX ) {
      Localizer localizer = LocalizerFactory.getLocalizer( getClass() );
      throw new ExBibIllegalValueException( localizer.format(
          "illegal.argument", Integer.toString( arg ) ), locator );
    }

    processor.push( new TString( String.valueOf( (char) arg ), locator ) );
  }

}
