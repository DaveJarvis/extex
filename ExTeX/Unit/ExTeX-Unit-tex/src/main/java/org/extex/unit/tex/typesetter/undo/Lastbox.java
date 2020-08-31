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
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;

/**
 * This class provides an implementation for the primitive {@code \lastbox}.
 *
 * <p>The Primitive {@code \lastbox}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;lastbox&rang;
 *    &rarr; {@code \lastbox}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \lastbox  </pre>
 * <pre class="TeXSample">
 *    \box1=\lastbox  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Lastbox extends AbstractCode implements Boxable {

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
  public Lastbox( CodeToken token ) {

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

    Mode mode = typesetter.getMode();
    if( mode.isMath() || mode == Mode.VERTICAL ) {
      throw new HelpingException( getLocalizer(), "TTP.LastBoxIn",
                                  toText(), mode.toString() );
    }

    // TODO gene: what's to do?
    // throw new RuntimeException("unimplemented");
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
   * org.extex.scanner.type.token.Token)
   */
  public Box getBox( Context context, TokenSource source,
                     Typesetter typesetter, Token insert )
      throws HelpingException,
      TypesetterException {

    Mode mode = typesetter.getMode();
    if( mode.isMath() /* || mode == Mode.VERTICAL */ ) {
      throw new HelpingException( getLocalizer(), "TTP.LastBoxIn",
                                  toText(), mode.toString() );
    }

    Node nodes = typesetter.getLastNode();
    Box box = null;

    if( nodes instanceof NodeList ) {
      typesetter.removeLastNode();
      box = new Box( (NodeList) nodes );
    }

    if( insert != null ) {
      source.push( insert );
    }

    return box;
  }
}
