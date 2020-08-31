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

package org.extex.exbib.core.bst;

import org.extex.exbib.core.bst.code.impl.*;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.Writer;

import java.util.logging.Logger;

/**
 * This implementation of a processor provides the functionality of
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X&nbsp;0.99c.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BstInterpreter099c extends BstInterpreterCore {

  /**
   * Creates a new object.
   *
   * @throws ExBibException in case that the constructor of the superclass
   *                        throws one.
   */
  public BstInterpreter099c() throws ExBibException {

    this( null, null, null );
  }

  /**
   * Creates a new object.
   *
   * @param db        the database to use
   * @param outWriter the output writer
   * @param logger    the logger
   * @throws ExBibException in case that the superclass has something to
   *                        complain or one of the functions is already
   *                        defined by the
   *                        superclass
   */
  public BstInterpreter099c( DB db, Writer outWriter, Logger logger )
      throws ExBibException {

    super( db, outWriter, logger );
    init();
  }

  /**
   * Initializing all functions.
   *
   * @throws ExBibException just in case some of the function is already
   *                        defined. This should not happen.
   */
  private void init() throws ExBibException {

    Locator locator = new Locator( getClass().getName() + "#init()", 0 );
    addFunction( ">", new Gt( ">" ), locator );
    addFunction( "<", new Lt( "<" ), locator );
    addFunction( "=", new Eq( "=" ), locator );
    addFunction( "+", new Plus( "+" ), locator );
    addFunction( "-", new Minus( "-" ), locator );
    addFunction( "*", new Concat( "*" ), locator );
    addFunction( ":=", new Set( ":=" ), locator );
    addFunction( "add.period$", new AddPeriod( "add.period$" ), locator );
    addFunction( "call.type$", new CallType( "call.type$" ), locator );
    addFunction( "change.case$", new ChangeCase( "change.case$" ), locator );
    addFunction( "chr.to.int$", new ChrToInt( "chr.to.int$" ), locator );
    addFunction( "cite$", new Cite( "cite$" ), locator );
    addFunction( "duplicate$", new Duplicate( "duplicate$" ), locator );
    addFunction( "empty$", new Empty( "empty$" ), locator );
    addFunction( "format.name$", new FormatName099( "format.name$" ), locator );
    addFunction( "if$", new If( "if$" ), locator );
    addFunction( "int.to.chr$", new IntToChr099( "int.to.chr$" ), locator );
    addFunction( "int.to.str$", new IntToStr( "int.to.str$" ), locator );
    addFunction( "missing$", new Missing( "missing$" ), locator );
    addFunction( "newline$", new Newline( "newline$" ), locator );
    addFunction( "num.names$", new NumNames( "num.names$" ), locator );
    addFunction( "pop$", new Pop( "pop$" ), locator );
    addFunction( "preamble$", new Preamble( "preamble$" ), locator );
    addFunction( "purify$", new Purify( "purify$" ), locator );
    addFunction( "quote$", new Quote( "quote$" ), locator );
    addFunction( "skip$", new Skip( "skip$" ), locator );
    addFunction( "stack$", new Stack( "stack$" ), locator );
    addFunction( "substring$", new Substring( "substring$" ), locator );
    addFunction( "swap$", new Swap( "swap$" ), locator );
    addFunction( "text.length$", new TextLength( "text.length$" ), locator );
    addFunction( "text.prefix$", new TextPrefix( "text.prefix$" ), locator );
    addFunction( "top$", new Top( "top$" ), locator );
    addFunction( "type$", new Type( "type$" ), locator );
    addFunction( "warning$", new Warning( "warning$" ), locator );
    addFunction( "while$", new While( "while$" ), locator );
    addFunction( "width$", new Width( "width$" ), locator );
    addFunction( "write$", new Write( "write$" ), locator );
  }

}
