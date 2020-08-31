/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.table;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingLeftBraceException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.table.util.PreambleItem;

import java.util.List;

/**
 * This class provides an implementation for the primitive {@code \valign}.
 *
 * <p>The Primitive {@code \valign}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;valign&rang;
 *       &rarr; {@code \valign}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \valign  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Valign extends AbstractAlign {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Valign( CodeToken token ) {

    super( token );
  }

  /**
   * Use the preamble to assemble a new line.
   *
   * @param preamble   the list of preamble items to use
   * @param height     the target height or {@code null} for the natural
   *                   height
   * @param context    the interpreter context
   * @param source     the source for new tokens
   * @param typesetter the typesetter to use
   * @throws HelpingException in case of an error
   */
  private void applyPreamble( List<PreambleItem> preamble, Dimen height,
                              Context context, TokenSource source,
                              Typesetter typesetter )
      throws HelpingException {

    // TODO gene: execute() unimplemented
    throw new RuntimeException( "unimplemented" );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Dimen height = null;

    if( source.getKeyword( context, "to" ) ) {
      height = source.parseDimen( context, source, typesetter );
    }
    Token t = source.getToken( context );
    if( t == null ) {
      throw new EofException( toText( context ) );
    }
    else if( t.isa( Catcode.LEFTBRACE ) ) {
      List<PreambleItem> preamble = getPreamble( context, source );
      applyPreamble( preamble, height, context, source, typesetter );
    }
    else {
      throw new MissingLeftBraceException(
          toText( context ) );
    }
  }

}
