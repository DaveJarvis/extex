/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.pdf.api.PdftexSupport;
import org.extex.pdf.api.action.ActionSpec;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \pdfoutline}.
 *
 * <p>The Primitive {@code \pdfoutline}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;pdfoutline&rang;
 *       &rarr; {@code \pdfoutline} &lang;optional count&rang; {@linkplain
 *          org.extex.interpreter.TokenSource#scanTokens(Context, boolean, boolean, CodeToken)
 *          &lang;general text&rang;}
 *
 *    &lang;optional count&rang;
 *       &rarr;
 *        | {@code count} {@linkplain
 *          org.extex.interpreter.TokenSource#scanRegisterName(Context, TokenSource, Typesetter, CodeToken)
 *          &lang;number&rang;}   </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \pdfoutline {abc.png}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Pdfoutline extends AbstractPdftexCode {

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
  public Pdfoutline( CodeToken token ) {

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

    PdftexSupport writer = ensurePdftex( context, typesetter );

    ActionSpec action =
        ActionSpec.parseActionSpec( context, source, typesetter,
                                    getToken() );
    long count = 0;
    if( source.getKeyword( context, "count" ) ) {
      count = source.parseInteger( context, source, typesetter );
    }

    String text = source.scanTokensAsString( context, getToken() );

    writer.pdfoutline( action, count, text );
  }

}
