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

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.pdf.api.PdftexSupport;
import org.extex.pdf.api.node.PdfRefXImage;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.RuleNode;

/**
 * This class provides an implementation for the primitive
 * {@code \pdfximage}.
 *
 * <p>The Primitive {@code \pdfximage}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;pdfximage&rang;
 *       &rarr; {@code \pdfximage} ... </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \pdfximage {abc.png}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Pdfximage extends AbstractPdftexCode {

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
  public Pdfximage( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws TypesetterException, HelpingException {

    PdftexSupport writer = ensurePdftex( context, typesetter );

    FixedDimen width = Dimen.ONE_PT; // TODO gene:provide correct default
    FixedDimen height = Dimen.ONE_PT; // TODO gene:provide correct default
    FixedDimen depth = Dimen.ONE_PT; // TODO gene:provide correct default
    String attr = null;
    long page = 0;

    for( ; ; ) {
      if( source.getKeyword( context, "width" ) ) {
        width = source.parseDimen( context, source, typesetter );
      }
      else if( source.getKeyword( context, "height" ) ) {
        height = source.parseDimen( context, source, typesetter );
      }
      else if( source.getKeyword( context, "depth" ) ) {
        depth = source.parseDimen( context, source, typesetter );
      }
      else if( source.getKeyword( context, "attr" ) ) {
        attr = source.scanTokensAsString( context, getToken() );
      }
      else if( source.getKeyword( context, "page" ) ) {
        page = source.parseInteger( context, source, typesetter );
      }
      else {
        break;
      }
    }

    String resource = source.scanTokensAsString( context, getToken() );

    PdfRefXImage image =
        writer.getXImage( resource, new RuleNode( width,
                                                  height,
                                                  depth,
                                                  context.getTypesettingContext(),
                                                  true ), attr, page,
                          prefix.isImmediate() );

    typesetter.add( image );

    prefix.clearImmediate();
  }

}
