/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.typesetter.AbstractVerticalCode;

/**
 * This class provides an implementation for the primitive {@code \vskip}.
 *
 * <p>The Primitive {@code \vskip}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *     &lang;vskip&rang;
 *         &rarr; {@code \vskip} &lang;Glue&rang;
 * </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *     \vskip 1em plus 1pt minus 1pt
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Vskip extends AbstractVerticalCode implements VerticalSkip {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Vskip( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    ensureVerticalMode( typesetter );
    Glue glue = source.parseGlue( context, source, typesetter );

    typesetter.add( glue );
  }

  /**
   * This method acquires a vertical glue.
   * <p>
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public FixedGlue getGlue( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    return source.parseGlue( context, source, typesetter );
  }

}
