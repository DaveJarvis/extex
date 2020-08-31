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

import org.extex.base.type.file.InputFile;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.interaction.Interaction;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.file.AbstractFileCode;

/**
 * This class provides an implementation for the primitive {@code \openin}.
 *
 * <p>The Primitive {@code \openin}</p>
 * <p>
 * The primitive {@code \openin} tries to open a file or other named resource
 * for reading. The reference is stored in a read register to be used with
 * {@link org.extex.unit.tex.file.Read {@code \read}}. If the opening fails
 * then the read register is void. This can be checked with the primitive
 * {@link org.extex.unit.tex.conditional.Ifeof {@code \ifeof}}.
 * </p>
 * <p>
 * The assignment to a read register is always global. The opening is always
 * performed immediately. Thus no prefix is allowed.
 * </p>
 * <p>
 * The stream should be closed with
 * {@link org.extex.unit.tex.file.Closein {@code \closein}} when not needed any
 * more.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;openin&rang;
 *      &rarr; {@code \openin}  {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanInFileKey(Context, TokenSource, Typesetter)
 *        &lang;infile&nbsp;name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanFileName(Context, TokenSource)
 *        &lang;file name&rang;}   </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *  \openin3= abc.def
 *  \read3 to \line
 *  \closein3 </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Openin extends AbstractFileCode {

  /**
   * The field {@code FILE_TYPE} contains the file type to create an input
   * stream for.
   */
  private static final String FILE_TYPE = "tex";

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
  public Openin( CodeToken token ) {

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

    String key =
        AbstractFileCode.scanInFileKey( context, source, typesetter );
    source.getOptionalEquals( context );
    String name = scanFileName( context, source );

    InFile file;
    Interaction interaction = context.getInteraction();
    try {
      context.setInteraction( Interaction.BATCHMODE );
      TokenStream stream =
          source.getTokenStreamFactory().getStream( name, FILE_TYPE,
                                                    getEncoding( context ) );
      if( stream != null ) {
        file = new InputFile( stream, false );
      }
      else {
        file = null;
      }
    } finally {
      context.setInteraction( interaction );
    }
    context.setInFile( key, file, true );
    prefix.clearImmediate(); // strange; does this make any sense?
  }

}
