/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.ocp.util.Ocp;
import org.extex.unit.omega.ocp.util.OcpUtil;

/**
 * This class provides an implementation for the primitive {@code \ocp}.
 *
 * <p>The Primitive {@code \ocp}</p>
 * <p>
 * The primitive {@code \ocp} can be used to define a control sequence which
 * holds an &Omega;CP program.
 * </p>
 * <p>
 * This primitive is an assignment. This means that the value of
 * {@code \globaldefs} and {@code \afterassignment} are taken into
 * account.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;ocp&rang;
 *      &rarr; &lang;prefix&rang; {@code \ocp} {@linkplain
 *       org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *       &lang;control sequence&rang;}  {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} &lang;resource name&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 * \ocp\abc=def </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class OcpPrimitive extends AbstractAssignment implements ResourceAware {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2007L;

  /**
   * The field {@code finder} contains the resource finder.
   */
  private transient ResourceFinder finder;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public OcpPrimitive( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws TypesetterException, HelpingException {

    CodeToken cs = source.getControlSequence( context, typesetter );
    source.getOptionalEquals( context );
    String resource = OcpUtil.scanOcpFileName( context, source, getToken() );
    Ocp ocp = Ocp.load( resource, finder );
    if( ocp == null ) {
      throw new HelpingException( getLocalizer(), "message", resource );
    }
    context.setCode( cs, ocp, prefix.clearGlobal() );
  }

  /**
   * Setter for the resource finder.
   *
   * @param resourceFinder the resource finder
   * @see org.extex.resource.ResourceAware#setResourceFinder(
   *org.extex.resource.ResourceFinder)
   */
  public void setResourceFinder( ResourceFinder resourceFinder ) {

    this.finder = resourceFinder;
  }

}
