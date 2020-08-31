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
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.pdf.api.PdftexSupport;
import org.extex.pdf.api.node.PdfObject;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \pdfobj}.
 *
 * <p>The Primitive {@code \pdfobj}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;pdfobj&rang;
 *       &rarr; {@code \pdfobj} &lang;optional attr&rang; &lang;optional stream or file&rang; {@linkplain
 *          org.extex.interpreter.TokenSource#scanTokens(Context, boolean, boolean, CodeToken)
 *          &lang;general text&rang;}
 *
 *    &lang;optional attr&rang;
 *       &rarr; {@code attr} {@linkplain
 *          org.extex.interpreter.TokenSource#scanTokens(Context, boolean, boolean, CodeToken)
 *          &lang;general text&rang;}
 *       |
 *
 *    &lang;optional stream or file&rang;
 *       &rarr; {@code file}
 *       |  {@code stream}
 *       |
 *    </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \pdfobj {abc.png}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Pdfobj extends AbstractPdftexCode {

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
  public Pdfobj( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException,
      TypesetterException,
      ConfigurationException {

    PdftexSupport writer = ensurePdftex( context, typesetter );
    String attr = null;
    boolean isStream = false;

    if( source.getKeyword( context, "attr" ) ) {
      attr = source.scanTokensAsString( context, getToken() );
    }
    if( source.getKeyword( context, "stream" ) ) {
      isStream = true;
    }
    else if( source.getKeyword( context, "file" ) ) {
      isStream = false;
    }

    String text = source.scanTokensAsString( context, getToken() );

    PdfObject a = writer.getObject( attr, isStream, text );
    typesetter.add( a );

    prefix.clearImmediate();
  }

}
