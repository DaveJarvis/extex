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

package org.extex.unit.tex.typesetter.spacing;

import org.extex.core.count.CountConstant;
import org.extex.core.count.FixedCount;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \ }.
 *
 * <p>The Primitive {@code \ }</p>
 * <p>
 * This primitive inserts an explicit space into the current list. This has an
 * effect in horizontal or restricted horizontal modes only. In other modes it
 * has no effect.
 * </p>
 * <p>
 * In contrast to a normal space it is not collapsed with adjacent spaces of
 * any kind. This means that several invocation in row add up extra space.
 * This is not recommended. See the macro {@code \hskip} instead.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;space primitive&rang;
 *        &rarr; {@code \ }  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    123\ 456  </pre>
 * <pre class="TeXSample">
 *    123\ \  456  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Space extends AbstractCode {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The constant {@code SPACEFACTOR} contains the spacefactor for the
   * space to add.
   */
  private static final FixedCount SPACEFACTOR = new CountConstant( 1000 );

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Space( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    typesetter.addSpace( context.getTypesettingContext(), SPACEFACTOR );
  }

}
