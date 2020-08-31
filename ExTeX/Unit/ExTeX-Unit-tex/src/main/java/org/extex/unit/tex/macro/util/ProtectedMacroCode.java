/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro.util;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.type.ProtectedCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This class is identical to the super class MacroCode but in addition it
 * implements the marker interface ProtectedCode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ProtectedMacroCode extends MacroCode implements ProtectedCode {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 20060405L;

  /**
   * Creates a new object.
   *
   * @param token      the initial token for the primitive
   * @param flags      the flags controlling the behavior of the macro
   * @param thePattern the pattern for the acquiring of the arguments
   * @param theBody    the expansion text
   * @param notLong    the indicator that the macro does not allow embedded
   *                  pars
   * @throws HelpingException in case of an error
   */
  public ProtectedMacroCode( CodeToken token, Flags flags, boolean notLong,
                             MacroPattern thePattern, Tokens theBody )
      throws HelpingException {

    super( token, flags, notLong, thePattern, theBody );
  }

}
