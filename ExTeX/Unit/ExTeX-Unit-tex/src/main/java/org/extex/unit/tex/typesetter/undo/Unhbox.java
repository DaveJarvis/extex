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
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.unit.tex.register.box.Setbox;

/**
 * This class provides an implementation for the primitive
 * {@code &#x5c;unhbox}.
 *
 * <p>The Primitive {@code &#x5c;unhbox}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;unhbox&rang;
 *       &rarr; {@code &#x5c;unhbox} {@linkplain
 *          org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *          &lang;8-bit&nbsp;number&rang;} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    &#x5c;unhbox42  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Unhbox extends AbstractCode {

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
  public Unhbox( CodeToken token ) {

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

    String key = Setbox.getKey( context, source, typesetter, getToken() );
    Box box = context.getBox( key );
    if( box == null || box.isVoid() ) {
      return;
    }
    else if( !box.isHbox() ) {
      throw new HelpingException( getLocalizer(), "TTP.IncompatibleUnbox" );
    }

    NodeList nl = box.getNodes();
    box.clear();
    for( Node node : nl ) {
      typesetter.add( node );
    }
  }

}
