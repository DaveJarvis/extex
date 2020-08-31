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

package org.extex.unit.tex.math.symbol;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.noad.BinaryNoad;
import org.extex.typesetter.type.noad.Noad;
import org.extex.typesetter.type.noad.util.MathSpacing;
import org.extex.unit.tex.math.AbstractMathCode;

/**
 * This class provides an implementation for the primitive {@code \mathbin}.
 *
 * <p>The Math Primitive {@code \mathbin}</p>
 * <p>
 * The primitive {@code \mathbin} takes an argument and treats it as a binary
 * symbol. It works in math mode only. The argument can either be a single
 * letter of a math expression enclosed in braces.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;mathbin&rang;
 *       &rarr; {@code \mathbin} &lang;formula&rang;
 *
 *    &lang;formula&rang;
 *       &rarr;  &lang;letter&rang;
 *         |  {@code {} &lang;math material&rang; {@code }}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \mathbin x  </pre>
 *
 * <pre class="TeXSample">
 *    \mathbin\mathchar"1234  </pre>
 *
 * <pre class="TeXSample">
 *    \mathbin{::}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Mathbin extends AbstractMathCode {

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
  public Mathbin( CodeToken token ) {

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
      ConfigurationException,
      TypesetterException {

    NoadConsumer nc = getListMaker( context, typesetter );
    Noad noad = nc.scanNoad( prefix, context, source, typesetter,
                             getToken(), GroupType.MATH_GROUP );

    if( noad != null ) {
      noad.setSpacingClass( MathSpacing.BIN );
      nc.add( noad );
    }
    else {
      nc.add( new BinaryNoad( noad, context.getTypesettingContext() ) );
    }
  }

}
