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

package org.extex.unit.tex.conditional;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;
import org.extex.unit.base.file.AbstractFileCode;

/**
 * This class provides an implementation for the primitive {@code \ifeof}.
 *
 * <p>The Primitive {@code \ifeof}</p>
 * <p>
 * This primitive tests for end of file on the given read register. The read
 * register is specified as a (expanded) number.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;ifeof&rang;
 *      &rarr; {@code \ifeof} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;number&rang;} &lang;true text&rang; {@code \fi}
 *      | {@code \ifeof} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;number&rang;} &lang;true text&rang; {@code \else} &lang;false text&rang; {@code \fi} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \ifeof 3 -E-O-F- \else ready \fi  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Ifeof extends AbstractIf {

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
  public Ifeof( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public boolean conditional( Context context, TokenSource source,
                              Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key =
        AbstractFileCode.scanInFileKey( context, source, typesetter );

    InFile inFile = context.getInFile( key );
    if( inFile == null ) {
      return true;
    }
    return inFile.isEof();
  }

}
