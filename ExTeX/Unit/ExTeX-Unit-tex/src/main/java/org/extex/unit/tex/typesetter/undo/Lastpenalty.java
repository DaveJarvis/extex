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

package org.extex.unit.tex.typesetter.undo;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.PenaltyNode;

/**
 * This class provides an implementation for the primitive
 * {@code \lastpenalty}.
 *
 * <p>The Primitive {@code \lastpenalty}</p>
 * <p>
 * TODO missing documentation
 * </p>
 * <p>
 * Examples:
 * </p>
 *
 * <pre class="TeXSample">
 *    \count1=\lastpenalty  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Lastpenalty extends AbstractCode
    implements
    CountConvertible,
    DimenConvertible,
    Theable {

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
  public Lastpenalty( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Node node = typesetter.getLastNode();
    return (node instanceof PenaltyNode
        ? ((PenaltyNode) node).getPenalty()
        : 0);
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertDimen( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Node node = typesetter.getLastNode();
    return (node instanceof PenaltyNode
        ? ((PenaltyNode) node).getPenalty()
        : 0);
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws CatcodeException,
      HelpingException,
      TypesetterException {

    Node node = typesetter.getLastNode();
    long penalty =
        (node instanceof PenaltyNode ? ((PenaltyNode) node)
            .getPenalty() : 0);
    return context.getTokenFactory().toTokens( penalty );
  }

}
