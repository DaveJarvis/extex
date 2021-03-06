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

package org.extex.unit.tex.string;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \number}.
 *
 * <p>The Primitive {@code \number}</p>
 * <p>
 * The primitive {@code \number} takes a following number specification of
 * any kind and produces a decimal representation of it. The associated tokens
 * &ndash; with category code 12 &ndash; are pushed back to the input stream.
 * Any leading zeroes and whitespace characters are omitted.
 * </p>
 * <p>
 * The primitive can be applied to a constant which is rather pointless except
 * that leading zeroes are discarded. It can as well be applied to a register in
 * which case one gets the tokens representing the content of the register.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;number&rang;
 *        &rarr; {@code \number} {@linkplain
 *           org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *           &lang;number&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \number 42  &rarr; 42</pre>
 *
 * <pre class="TeXSample">
 *    \count=-123
 *    \number -\count3  &rarr; 123 </pre>
 *
 * <pre class="TeXSample">
 *    \number -0042  &rarr; -42</pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Number extends AbstractCode implements ExpandableCode {

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
  public Number( CodeToken token ) {

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

    long number = source.parseInteger( context, source, typesetter );
    try {
      source.push( context.getTokenFactory().toTokens( number ) );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long number = source.parseInteger( context, source, typesetter );
    try {
      source.push( context.getTokenFactory().toTokens( number ) );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

}
