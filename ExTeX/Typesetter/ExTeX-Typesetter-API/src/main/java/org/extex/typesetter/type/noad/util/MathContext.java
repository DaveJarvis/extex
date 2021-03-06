/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.noad.util;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.Glue;
import org.extex.core.glue.GlueComponent;
import org.extex.core.muskip.Mudimen;
import org.extex.core.muskip.Muskip;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.noad.StyleNoad;

/**
 * This class provides a container for the information on the current
 * mathematical appearance.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MathContext {

  /**
   * The constant {@code MU_UNIT} contains the unit length for the math unit.
   */
  private static final long MU_UNIT = 18 * Dimen.ONE;

  /**
   * The field {@code context} contains the data object for options.
   */
  private final TypesetterOptions options;

  /**
   * The field {@code style} contains the current style.
   */
  private StyleNoad style;

  /**
   * Creates a new object.
   *
   * @param style   the new style
   * @param context the typesetter context
   */
  public MathContext( StyleNoad style, TypesetterOptions context ) {

    this.style = style;
    this.options = context;
  }

  /**
   * Extract a font dimen from an appropriate font.
   *
   * @param p the parameter to extract
   * @return the value of the font dimen
   * @throws TypesetterException in case of an error. The exception will have
   *                             a cause exception in it containing a
   *                             HelpingException
   * @see "TTP [708]"
   */
  public FixedDimen mathParameter( MathFontParameter p )
      throws TypesetterException {

    return mathParameter( p, style );
  }

  /**
   * Retrieve a font parameter from either a symbol font or an extension font.
   * If the parameter does not exist then an error is raised.
   *
   * @param p           the parameter to extract
   * @param actualStyle the actual style to be used instead of the current
   *                    style
   * @return the value of the font dimen
   * @throws TypesetterException in case of an error. The exception will have
   *                             a cause exception in it containing a
   *                             HelpingException
   */
  public FixedDimen mathParameter( MathFontParameter p,
                                   StyleNoad actualStyle )
      throws TypesetterException {

    Font font = options.getFont( MathFontParameter.key( options,
                                                        actualStyle.getFontName(),
                                                        p.inSymbol() ? "2" :
                                                            "3" ) );
    FixedDimen value;
    if( font == null || (value = font.getFontDimen( p.getNo() )) == null ) {
      throw new TypesetterException(

          new HelpingException( LocalizerFactory.getLocalizer( getClass() ),
                                p.inSymbol()
                                    ? "TTP.InsufficientSymbolFonts"
                                    : "TTP.InsufficientExtensionFonts" ) );
    }
    return value;
  }

  /**
   * Convert a mudimen into a dimen.
   *
   * <p>
   * From The TeXbook:
   * </p>
   * <p><i>
   * There are 18 mu to an em, where the em is taken from family 2
   * (the math symbols family). In other words, {@code \textfont 2}
   * defines the em value for {@code mu} in display and text styles;
   * {@code \scriptfont 2} defines the em for script size material;
   * and {@code \scriptscriptfont 2} defines it for scriptscript size.
   * </i></p>
   *
   * @param mudimen the math dimen to convert
   * @return a new instance of a Dimen corresponding to the parameter
   * @throws TypesetterException in case of an error
   * @see "TTP [717]"
   */
  public Dimen convert( Mudimen mudimen ) throws TypesetterException {

    Dimen length = new Dimen( mudimen.getLength().getValue() );
    length.multiply( mathParameter( MathFontParameter.MATH_QUAD, style )
                         .getValue(), MU_UNIT );

    return length;
  }

  /**
   * Convert a muglue into a glue.
   * <p>
   * From The TeXbook:
   *
   * <p><i>
   * There are 18 mu to an em, where the em is taken from family~2
   * (the math symbols family). In other words, ^|\textfont|~|2| defines
   * the em
   * value for |mu| in display and text styles; ^|\scriptfont|~|2| defines the
   * em for script size material; and ^|\scriptscriptfont|~|2| defines it for
   * scriptscript size.
   * </i></p>
   *
   * @param muglue the math glue to convert
   * @return a new instance of a glue corresponding to the parameter
   * @throws TypesetterException in case of an error
   * @see "TTP [716]"
   */
  public Glue convert( Muskip muglue ) throws TypesetterException {

    long factor =
        mathParameter( MathFontParameter.MATH_QUAD, style ).getValue();
    Dimen length = new Dimen( muglue.getLength() );
    GlueComponent stretch = new GlueComponent( muglue.getStretch() );
    GlueComponent shrink = new GlueComponent( muglue.getShrink() );

    length.multiply( factor, MU_UNIT );
    if( stretch.getOrder() != 0 ) {
      stretch.multiply( factor, MU_UNIT );
    }
    if( shrink.getOrder() != 0 ) {
      shrink.multiply( factor, MU_UNIT );
    }

    return new Glue( length, stretch, shrink );
  }

  /**
   * Getter for the options.
   *
   * @return the options
   */
  public TypesetterOptions getOptions() {

    return this.options;
  }

  /**
   * Getter for style.
   *
   * @return the style.
   */
  public StyleNoad getStyle() {

    return this.style;
  }

  /**
   * Setter for style.
   *
   * @param style the style to set.
   */
  public void setStyle( StyleNoad style ) {

    this.style = style;
  }

}
