/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.arithmetic;

import org.extex.core.exception.helping.CantUseAfterException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \multiply}.
 *
 * <p>The Primitive {@code \advance}</p>
 * <p>
 * This primitive implements an assignment. The variable given as next tokens is
 * multiplied by the quantity given after the optional {@code by}.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *   &lang;multiply&rang;
 *     &rarr; &lang;optional prefix&rang; {@code \multiply} &lang;multiplyable&rang;
 *
 *   &lang;multiplyable&rang;
 *     &rarr; &lang;integer variable&rang; &lang;optional {@code by}&rang; {@linkplain
 *      org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *      |  &lang;dimen variable&rang; &lang;optional {@code by}&rang; {@linkplain
 *      org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *      |  &lang;glue variable&rang; &lang;optional {@code by}&rang; {@linkplain
 *      org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *      |  &lang;muglue variable&rang; &lang;optional {@code by}&rang; {@linkplain
 *      org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *      &lang;8-bit&nbsp;number&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  {@code \global} &lang;optional prefix&rang;
 *
 *   &lang;optional {@code by}&rang;
 *     &rarr; [by]
 *      |  {@linkplain org.extex.interpreter.TokenSource#skipSpace()
 *            &lang;optional spaces&rang;}
 *   </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \multiply\count12 345  </pre>
 * <pre class="TeXSample">
 *    \multiply\count12 by -345  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @see org.extex.interpreter.type.code.Multiplyable
 */
public class Multiply extends AbstractAssignment {

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
  public Multiply( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Token cs = source.getToken( context );

    if( cs instanceof CodeToken ) {
      Code code = context.getCode( (CodeToken) cs );

      if( code instanceof Multiplyable ) {

        ((Multiplyable) code).multiply( prefix, context, source,
                                        typesetter );
        return;

      }
      else if( code == null ) {
        throw new UndefinedControlSequenceException( cs.toText() );
      }
    }
    else if( cs == null ) {
      throw new EofException( toText() );
    }
    throw new CantUseAfterException( cs.toString(), toText() );
  }

}
