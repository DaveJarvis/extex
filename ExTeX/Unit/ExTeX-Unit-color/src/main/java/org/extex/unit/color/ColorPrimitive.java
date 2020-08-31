/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \color}.
 *
 * <p>The Primitive {@code \color}</p>
 * <p>
 * The primitive {@code \color} sets the current color value to the value
 * given. The value can be any color specification for one of the supported
 * color models.
 * </p>
 * <p>
 * The color models of εχTeX use components of two bytes. This means that 
 * values from 0
 * to 65535 can be stored in each component. The external representation is a
 * floating point number in the range from 0.0 to 1.0.
 * </p>
 * <p>
 * The color models of εχTeX support an alpha channel.
 * </p>
 *
 * <p>The RGB Color Model</p>
 *
 * <p>
 * The RGB color model provides three values for the red, green, and blue
 * channel. Each is given as floating point number from 0.0 to 1.0.
 * </p>
 *
 * <p>The CMYK Color Model</p>
 *
 * <p>
 * The CMYK color model provides four values for cyan, magenta, yellow, and
 * black channel. Each is given as floating point number from 0.0 to 1.0.
 * </p>
 *
 * <p>The Grayscale Model</p>
 *
 * <p>
 * The gray-scale color model provides one value for the gray channel. It is
 * given as floating point number from 0.0 to 1.0.
 * </p>
 *
 * <p>The HSV Color Model</p>
 *
 * <p>
 * The HSV color model provides three values for the hue, saturation, and value
 * channel. Each is given as floating point number from 0.0 to 1.0.
 * </p>
 *
 * <p>The Alpha Channel</p>
 *
 * <p>
 * The alpha channel determines the opactivity of the color. A value of 0 means
 * that the given color completely overwrites the underlying texture. A value of
 * 1.0 is the maximal admissible alpha value. In this case the color is in fact
 * invisible. In between the background shines through to the degree of the
 * alpha value.
 * </p>
 * <p>
 * Note that the alpha channel may not be supported by any output device. In
 * such a case it is up to the back-end driver to make best use of the alpha
 * value or ignore it at all.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;color&rang;
 *      &rarr; &lang;prefix&rang; {@code \color} &lang;alpha&rang; &lang;color&rang;
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       |  {@code \global}
 *
 *    &lang;alpha&rang;
 *      &rarr;
 *       |  {@code alpha} &lang;number&rang;
 *
 *    &lang;color&rang;
 *      &rarr; {@code {} &lang;color value&rang; &lang;color value&rang; &lang;color value&rang; {@code }}
 *       |  {@code rgb} {@code {} &lang;color value&rang; &lang;color value&rang; &lang;color value&rang; {@code }}
 *       |  {@code gray} {@code {} &lang;color value&rang; {@code }}
 *       |  {@code cmyk} {@code {} &lang;color value&rang; &lang;color value&rang; &lang;color value&rang; &lang;color value&rang; {@code }}
 *       |  {@code hsv} {@code {} &lang;color value&rang; &lang;color value&rang; &lang;color value&rang; {@code }}
 *       |  &lang;color convertible&rang;
 *
 *    &lang;color value&rang;
 *      &rarr; &lang;number&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \color{\r \b \g}  </pre>
 *
 * <br>
 *
 * <pre class="TeXSample">
 *    \color gray {\gray}  </pre>
 *
 * <br>
 *
 * <pre class="TeXSample">
 *    \color rgb {\r \b \g}  </pre>
 *
 * <br>
 *
 * <pre class="TeXSample">
 *    \color rgb {1 .2 .3333}  </pre>
 *
 * <br>
 *
 * <pre class="TeXSample">
 *    \color hsv {\h \s \v}  </pre>
 *
 * <br>
 *
 * <pre class="TeXSample">
 *    \color alpha 500 rgb {\r \b \g} </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ColorPrimitive extends AbstractColor {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public ColorPrimitive( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws ConfigurationException,
      HelpingException,
      TypesetterException {

    Color color =
        ColorParser.parseColor( context, source, typesetter, getToken() );
    context.set( color, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Color convertColor( Context context, TokenSource source,
                             Typesetter typesetter ) throws HelpingException {

    return context.getTypesettingContext().getColor();
  }

}
