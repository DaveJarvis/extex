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

package org.extex.unit.tex.file;

import org.extex.base.type.file.OutputFile;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.unit.tex.file.nodes.WhatsItOpenNode;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * This class provides an implementation for the primitive {@code \openout}.
 *
 * <p>The Primitive {@code \openout}</p>
 * <p>
 * The primitive {@code \openout} tries to open a file or other named
 * resource for writing. The reference is stored in a write register to be used
 * with {@link org.extex.unit.tex.file.Write {@code \write}}. If the opening
 * fails then the write register is void.
 * </p>
 * <p>
 * The opening of a write register is delayed until the nodes are shipped out.
 * If the invocation is prefixed with
 * {@link org.extex.unit.tex.prefix.Immediate {@code \immediate}} then the
 * resource is opened immediately.
 * </p>
 * <p>
 * The primitive {@code \openout} is not considered as assignment. Nor can it
 * be prefixed with {@code \global}. The definition of an output stream is
 * always global.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;openout&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \openout} {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanOutFileKey(Context, TokenSource, Typesetter)
 *        &lang;outfile&nbsp;name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanFileName(Context, TokenSource)
 *        &lang;file name&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \immediate} &lang;optional prefix&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *  \immediate\openout3= abc.def
 *  \write3{Hi there!}
 *  \closeout3 </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Openout extends AbstractFileCode {

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
  public Openout( CodeToken token ) {

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

    String key =
        AbstractFileCode.scanOutFileKey( context, source, typesetter );

    source.getOptionalEquals( context );
    String name = scanFileName( context, source );

    OutFile file = new OutputFile( new File( name ) );

    String encoding = getEncoding( context );
    if( prefix.clearImmediate() ) {
      try {
        file.open( key, encoding, source.getTokenStreamFactory() );
      } catch( UnsupportedEncodingException e ) {
        throw new HelpingException( getLocalizer(), "Encoding", encoding );
      }
      context.setOutFile( key, file, true );
    }
    else {
      typesetter.add( new WhatsItOpenNode( key, file, encoding, source
          .getTokenStreamFactory() ) );
    }
  }

}
