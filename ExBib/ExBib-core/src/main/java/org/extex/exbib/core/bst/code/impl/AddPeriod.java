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
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

import java.util.regex.Pattern;

/**
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X built-in function {@code add.period$}
 * <p>
 * This function pops a string argument from the stack and inspects it. It the
 * argument ends in one of the characters period '.', exclamation mark '!', or
 * question mark '?' then the argument is pushed back to the stack. Otherwise a
 * period '.' is added to the argument and the result pushed to the stack.
 * <p>
 * When determining the last character closing braces are ignored.
 * </p>
 * <img src="doc-files/add.period.png" alt="add.period">
 * <p>
 * The following example is taken from {@code alpha.bst}:
 * </p>
 *
 * <pre>
 *   FUNCTION {fin.entry}
 *   { add.period$
 *     write$
 *     newline$
 *   }
 * </pre>
 *
 * <hr>
 *
 * <dl>
 * <dt>BibTeX documentation:</dt>
 * <dd>Pops the top (string) literal, adds a `{@code .}' to it if the last
 * non`{@code }}' character isn't a `{@code .}', `{@code ?}', or
 * `{@code !}, and pushes this resulting string.</dd>
 * </dl>
 *
 * <dl>
 * <dt>BibTeX web documentation:</dt>
 * <dd>The {@code built_in} function {@code add.period$} pops the top
 * (string) literal, adds a {@code period} to a nonnull string if its last
 * non{@code right_brace} character isn't a {@code period},
 * {@code question_mark}, or {@code exclamation_mark}, and pushes this
 * resulting string back onto the stack. If the literal isn't a string, it
 * complains and pushes the null string.</dd>
 * </dl>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class AddPeriod extends AbstractCode implements Configurable {

  /**
   * The field {@code omitPattern} contains the pattern to determine when not
   * to add a period.
   */
  private Pattern omitPattern = Pattern.compile( ".*[.!?][}]*$" );

  /**
   * Create a new object.
   */
  public AddPeriod() {

  }

  /**
   * Creates a new object.
   *
   * @param name the function name in the processor context
   */
  public AddPeriod( String name ) {

    super( name );
  }

  /**
   * Attach a period if feasible.
   *
   * @param value the value to attach a period to
   * @return the result
   */
  public String addPeriod( String value ) {

    if( !"".equals( value ) && !omitPattern.matcher( value ).matches() ) {
      return value + ".";
    }
    return value;
  }

  public void configure( Configuration config ) throws ConfigurationException {

    String op = config.getValue( "omitPattern" );
    if( op != null ) {
      omitPattern = Pattern.compile( op );
    }
  }

  /**
   * org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
   */
  public void execute( BstProcessor processor, Entry entry, Locator locator )
      throws ExBibException {

    Token a = processor.pop( locator );
    processor.push( new TString( addPeriod( a.getValue() ), locator ) );
  }

}
