/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.prefix;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * {@code \protected}.
 * It does simply nothing, but as a side effect the prefix <i>PROTECTED</i> is
 * added to the prefixes.
 *
 * <p>The Prefix Primitive {@code \protected}</p>
 * <p>
 * The primitive {@code \protected} is a prefix modifying the operation of a
 * following primitive. If the immediately following token denotes another
 * prefix primitives then the functionality is accumulated. This means that
 * the next non-prefix primitive is modified by any directly preceding prefix
 * primitives.
 * </p>
 * <p>
 * Multiple {@code \protected} prefixes act identical to a single one.
 * </p>
 *
 * <p>Syntax</p>
 *
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 * <pre class="syntax">
 *    &lang;protected&rang;
 *      &rarr; {@code \protected}  </pre>
 *
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \protected\def\abc{123}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Protected extends AbstractCode implements PrefixCode {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Protected( CodeToken token ) {

    super( token );
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.interpreter.type.Code#execute(
   *org.extex.interpreter.Flags,
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource,
   * org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context,
                       TokenSource source, Typesetter typesetter ) {

    prefix.setProtected();
  }

}
