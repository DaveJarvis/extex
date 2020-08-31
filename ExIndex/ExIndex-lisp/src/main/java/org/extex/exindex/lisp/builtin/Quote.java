/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.lisp.builtin;

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This function takes one argument and returns it. This means the argument is
 * not evaluated but left unchanged.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Quote extends LFunction {

  /**
   * Creates a new object.
   *
   * @param name the name of the function
   * @throws NoSuchMethodException in case that no method corresponding to the
   *                               argument specification could be found
   * @throws SecurityException     in case a security problem occurred
   */
  public Quote( String name ) throws SecurityException, NoSuchMethodException {

    super( name, new Arg[]{Arg.QVALUE} );
  }

  /**
   * Evaluate the function.
   *
   * @param interpreter the interpreter
   * @param arg         the term to quote
   * @return the quoted term
   */
  public LValue evaluate( LInterpreter interpreter, LValue arg ) {

    return arg;
  }
}
