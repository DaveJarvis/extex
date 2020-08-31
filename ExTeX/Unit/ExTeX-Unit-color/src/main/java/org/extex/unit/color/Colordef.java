/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.color;

import org.extex.color.Color;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \colordef}.
 *
 * <p>The Primitive {@code \colordef}</p>
 * <p>
 * The primitive {@code \colordef} defines a color variable and assigns it to a
 * control sequence. The color is initialized with a given color &ndash; either
 * a color constant or a color variable.
 * </p>
 * <p>
 * The control sequence can later be used wherever a color is expected.
 * </p>
 * <p>
 * The primitive {@code \colordef} constitutes an assignment. Thus the count
 * register {@code \globaldefs} and the token register
 * {@code \afterassignment} interact with it as for each assignment.
 * </p>
 * <p>
 * The primitive can be prefixed with the {@code \global} flag. In this case
 * the definition is performed globally. Otherwise the control sequence holds
 * the color value in the current group only. It is reset to the previous value
 * when the group is ended.
 * </p>
 * <p>
 * The color variable can be manipulated by assigning new colors to it. The
 * assignment is accomplished by specifying the new value after an optional
 * equals sign. Note that the assignment can not be prefixed by a
 * {@code \global} modifier since the scope has already been specified in the
 * declaration with {@code \colordef}.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;colordef&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \colordef} {@linkplain
 *       org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *       &lang;control sequence&rang;} &lang;color&rang;
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global}   </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \colordef\col alpha .1234 rgb {.2 .3 .4}  </pre>
 *
 * <br>
 *
 * <pre class="TeXSample">
 *    \global\colordef\col\color  </pre>
 *
 * <br>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Colordef extends AbstractAssignment {

  /**
   * This class carries a color value for storing it as code in the context.
   *
   * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
   */
  private static class ColorCode extends AbstractColor {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 20060528L;

    /**
     * The field {@code color} contains the color.
     */
    private Color color;

    /**
     * Creates a new object.
     *
     * @param color the color
     * @param token the initial token for the primitive
     */
    public ColorCode( Color color, CodeToken token ) {

      super( token );
      this.color = color;
    }

    /**
     * org.extex.interpreter.context.Context,
     * org.extex.interpreter.TokenSource,
     * org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign( Flags prefix, Context context, TokenSource source,
                        Typesetter typesetter )
        throws HelpingException,
        TypesetterException {

      color = ColorParser.parseColor( context, source, typesetter,
                                      getToken() );
    }

    /**
     * org.extex.interpreter.TokenSource,
     * org.extex.typesetter.Typesetter)
     */
    @Override
    public Color convertColor( Context context, TokenSource source,
                               Typesetter typesetter ) throws HelpingException {

      return color;
    }

  }

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Colordef( CodeToken token ) {

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
    Color color =
        ColorParser.parseColor( context, source, typesetter, getToken() );
    context.setCode( cs, new ColorCode( color, cs ), prefix.clearGlobal() );
  }

}
