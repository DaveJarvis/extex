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

package org.extex.unit.tex.math.limits;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.noad.Noad;
import org.extex.typesetter.type.noad.OperatorNoad;
import org.extex.unit.tex.math.AbstractMathCode;

/**
 * This class provides an implementation for the primitive
 * {@code \displaylimits}.
 *
 * <p>The Math Primitive {@code \displaylimits}</p>
 * <p>
 * The math primitive {@code \displaylimits} influences the treatment of
 * limits when occurring after a math operator. The consequence is that the
 * limits are typeset in the way as in display style.
 * </p>
 * <p>
 * In any other circumstances the primitive raises an error.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;displaylimits&rang;
 *       &rarr; {@code \displaylimits}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \displaylimits  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Displaylimits extends AbstractMathCode {

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
  public Displaylimits( CodeToken token ) {

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
    Noad noad = nc.getLastNoad();

    if( !(noad instanceof OperatorNoad) ) {
      throw new HelpingException( getLocalizer(), "TTP.MisplacedLimits" );
    }

    ((OperatorNoad) noad).setLimits( null );
  }

}
