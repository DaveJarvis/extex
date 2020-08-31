/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.register.CharCode;

/**
 * This class provides an implementation for the primitive {@code \chardef}
 * .
 *
 * <p>The Primitive {@code \chardef}</p>
 * <p>
 * The primitive {@code \chardef} allows you to define a control sequence or
 * active character to be equivalent to a character. This mean that the new
 * entity can be used wherever a character is allowed.
 * </p>
 * <p>
 * A character is represented by a code point; i.e. a positive number denoting
 * the character position. In TeX only 8-bit number where allowed. In
 * εχTeX arbitrary positive numbers are valid as values.
 * </p>
 * <p>
 * The definition is performed with respect to to group to keep it locally. The
 * prefix {@code /global} or the value of {@code \globaldefs} can influence
 * the scope.
 * </p>
 * <p>
 * This primitive is an assignment. All actions around assignments are
 * performed.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;chardef&rang;
 *      &rarr; {@code \chardef} {@linkplain
 *        org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *        &lang;control sequence&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;number&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \chardef\abc=45  </pre>
 *
 * <pre class="TeXSample">
 *    \chardef\abc 33  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Chardef extends AbstractAssignment {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Chardef( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    CodeToken cs = source.getControlSequence( context, typesetter );
    source.getOptionalEquals( context );
    UnicodeChar uc =
        source.scanCharacterCode( context, typesetter, getToken() );

    context.setCode( cs, new CharCode( cs, uc ), prefix.clearGlobal() );
  }

}
