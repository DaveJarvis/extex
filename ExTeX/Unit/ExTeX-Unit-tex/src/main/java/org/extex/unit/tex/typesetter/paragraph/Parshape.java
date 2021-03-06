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

package org.extex.unit.tex.typesetter.paragraph;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;

/**
 * This class provides an implementation for the primitive {@code \relax}.
 *
 * <p>The Primitive {@code \parshape}</p>
 * <p>
 * The primitive {@code \parshape} is a declaration of the shape of the
 * paragraph. With its help it is possible to control the left and right margin
 * of the current paragraph.
 * </p>
 * <p>
 * The shape of the paragraph is controlled on a line base. For each line the
 * left indentation and the width are given. The first argument of
 * {@code \parshape} determines the number of such pairs to follow.
 * </p>
 * <p>
 * When the paragraph is typeset the lines are indented and adjusted according
 * to the specification given. If there are more lines specified as actually
 * present in the current paragraph then the remaining specifications are
 * discarded at the end of the paragraph. If there are less lines then the last
 * specification is repeated.
 * </p>
 * <p>
 * If several {@code \parshape} declarations are given in one paragraph then
 * the one is used which is in effect at the end of the paragraph. This means
 * that later declarations overrule earlier ones.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;parshape&rang;
 *        &rarr; {@code \parshape} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;8-bit&nbsp;number&rang;} ... </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \parshape 3 20pt \linewidth
 *                20pt \linewidth
 *                0pt \linewidth </pre>
 *
 * <pre class="TeXSample">
 *    \parshape 0 </pre>
 *
 * <p>{@code \parshape} as special integer</p>
 * <p>
 * {@code \parshape} acts as special count register which can be queried. It
 * returns the size of the current parshape specification or 0 if none is
 * present.
 * </p>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \count1=\parshape  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Parshape extends AbstractCode
    implements CountConvertible, Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Parshape( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    ParagraphShape parshape = context.getParshape();
    return (parshape != null ? parshape.getSize() / 2 : 0);
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long n = source.parseInteger( context, source, typesetter );
    if( n <= 0 ) {
      context.setParshape( null );
    }
    else {
      ParagraphShape parshape = new ParagraphShape();
      while( n-- > 0 ) {
        Dimen left = source.parseDimen( context, source, typesetter );
        Dimen right = source.parseDimen( context, source, typesetter );
        parshape.add( left, right );
      }
      context.setParshape( parshape );
    }
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws CatcodeException,
      HelpingException,
      TypesetterException {

    ParagraphShape parshape = context.getParshape();
    return context.getTokenFactory().toTokens(
        parshape != null ? parshape.getSize() / 2 : 0 );
  }

}
