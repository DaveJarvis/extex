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

package org.extex.color;

import org.extex.color.model.*;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.ImpossibleException;

/**
 * This implementation of a color converter is based on the formulas in the
 * color space FAQ.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BasicColorConverter implements ColorConverter {


  public BasicColorConverter() {

  }

  /**
   * The field {@code CMYK_CONVERTER} contains the converter for colors to
   * the CMYK model.
   */
  private static final ColorVisitor CMYK_CONVERTER = new ColorVisitor() {

    /**
     * @see org.extex.color.ColorVisitor#visitCmyk(
     *org.extex.color.model.CmykColor,
     *      java.lang.Object)
     */
    public Object visitCmyk( CmykColor color, Object value )
        throws GeneralException {

      return color;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitGray(
     *org.extex.color.model.GrayscaleColor,
     *      java.lang.Object)
     */
    public Object visitGray( GrayscaleColor color, Object value )
        throws GeneralException {

      return null;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitHsv(
     *org.extex.color.model.HsvColor,
     *      java.lang.Object)
     */
    public Object visitHsv( HsvColor color, Object value )
        throws GeneralException {

      return null;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitRgb(
     *org.extex.color.model.RgbColor,
     *      java.lang.Object)
     */
    public Object visitRgb( RgbColor color, Object value )
        throws GeneralException {

      int r = Color.MAX_VALUE - color.getRed();
      int g = Color.MAX_VALUE - color.getGreen();
      int b = Color.MAX_VALUE - color.getBlue();
      int black = (r > b ? (b > g ? g : b) : (r > g ? g : r));
      return ColorFactory.getCmyk(
          black == Color.MAX_VALUE ? 0 : (r - black)
              / (Color.MAX_VALUE - black),
          black == Color.MAX_VALUE ? 0 : (g - black)
              / (Color.MAX_VALUE - black),
          black == Color.MAX_VALUE ? 0 : (r - black)
              / (Color.MAX_VALUE - black),
          black, color.getAlpha() );
    }
  };

  /**
   * The field {@code GRAY_CONVERTER} contains the converter for colors to
   * the gray-scale model.
   */
  private static final ColorVisitor GRAY_CONVERTER = new ColorVisitor() {

    /**
     * @see org.extex.color.ColorVisitor#visitCmyk(
     *org.extex.color.model.CmykColor,
     *      java.lang.Object)
     */
    public Object visitCmyk( CmykColor color, Object value )
        throws GeneralException {

      return null;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitGray(
     *org.extex.color.model.GrayscaleColor,
     *      java.lang.Object)
     */
    public Object visitGray( GrayscaleColor color, Object value )
        throws GeneralException {

      return color;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitHsv(
     *org.extex.color.model.HsvColor,
     *      java.lang.Object)
     */
    public Object visitHsv( HsvColor color, Object value )
        throws GeneralException {

      return null;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitRgb(
     *org.extex.color.model.RgbColor,
     *      java.lang.Object)
     */
    public Object visitRgb( RgbColor color, Object value )
        throws GeneralException {

      return ColorFactory.getGray( (222 * color.getRed() + 707
          * color.getGreen() + 713 * color.getBlue()) / 1000, color
                                       .getAlpha() );
    }
  };

  /**
   * The field {@code RGB_CONVERTER} contains the converter for colors to
   * the RGB model.
   */
  private static final ColorVisitor RGB_CONVERTER = new ColorVisitor() {

    /**
     * @see org.extex.color.ColorVisitor#visitCmyk(
     *org.extex.color.model.CmykColor,
     *      java.lang.Object)
     */
    public Object visitCmyk( CmykColor color, Object value )
        throws GeneralException {

      int r =
          Color.MAX_VALUE - color.getCyan()
              * (Color.MAX_VALUE - color.getBlack())
              / Color.MAX_VALUE + color.getBlack();
      int g =
          Color.MAX_VALUE - color.getMagenta()
              * (Color.MAX_VALUE - color.getBlack())
              / Color.MAX_VALUE + color.getBlack();
      int b =
          Color.MAX_VALUE - color.getYellow()
              * (Color.MAX_VALUE - color.getBlack())
              / Color.MAX_VALUE + color.getBlack();
      return ColorFactory.getRgb( (r < 0 ? 0 : r),
                                  (g < 0 ? 0 : g),
                                  (b < 0 ? 0 : b),
                                  color.getAlpha() );
    }

    /**
     * @see org.extex.color.ColorVisitor#visitGray(
     *org.extex.color.model.GrayscaleColor,
     *      java.lang.Object)
     */
    public Object visitGray( GrayscaleColor color, Object value )
        throws GeneralException {

      int g = color.getGray();
      return ColorFactory.getRgb( g, g, g, color.getAlpha() );
    }

    /**
     * @see org.extex.color.ColorVisitor#visitHsv(
     *org.extex.color.model.HsvColor,
     *      java.lang.Object)
     */
    public Object visitHsv( HsvColor color, Object value )
        throws GeneralException {

      return null;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitRgb(
     *org.extex.color.model.RgbColor,
     *      java.lang.Object)
     */
    public Object visitRgb( RgbColor color, Object value )
        throws GeneralException {

      return color;
    }
  };

  /**
   * The field {@code HSV_CONVERTER} contains the converter for colors to
   * the HSV model.
   */
  private static final ColorVisitor HSV_CONVERTER = new ColorVisitor() {

    /**
     * @see org.extex.color.ColorVisitor#visitCmyk(
     *org.extex.color.model.CmykColor,
     *      java.lang.Object)
     */
    public Object visitCmyk( CmykColor color, Object value )
        throws GeneralException {

      return null;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitGray(
     *org.extex.color.model.GrayscaleColor,
     *      java.lang.Object)
     */
    public Object visitGray( GrayscaleColor color, Object value )
        throws GeneralException {

      return null;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitHsv(
     *org.extex.color.model.HsvColor,
     *      java.lang.Object)
     */
    public Object visitHsv( HsvColor color, Object value )
        throws GeneralException {

      return color;
    }

    /**
     * @see org.extex.color.ColorVisitor#visitRgb(
     *org.extex.color.model.RgbColor,
     *      java.lang.Object)
     */
    public Object visitRgb( RgbColor color, Object value )
        throws GeneralException {

      return null;
    }
  };

  /**
   * Convert an arbitrary color to the CMYK model.
   * If an conversion is not supported then {@code null} is returned.
   *
   * @param color the color to convert
   * @return the corresponding color in the CMYK model or {@code null}
   * if a conversion is not supported.
   * @see org.extex.color.ColorConverter#toCmyk(
   *org.extex.color.Color)
   */
  public CmykColor toCmyk( Color color ) {

    try {
      return (CmykColor) color.visit( CMYK_CONVERTER, null );
    } catch( GeneralException e ) {
      throw new ImpossibleException( this.getClass().getName() );
    }
  }

  /**
   * Convert an arbitrary color to the gray-scale model.
   * If an conversion is not supported then {@code null} is returned.
   *
   * @param color the color to convert
   * @return the corresponding color in the RGB model or {@code null} if
   * a conversion is not supported.
   * @see org.extex.color.ColorConverter#toGrayscale(
   *org.extex.color.Color)
   */
  public GrayscaleColor toGrayscale( Color color ) {

    try {
      return (GrayscaleColor) color.visit( GRAY_CONVERTER, null );
    } catch( GeneralException e ) {
      throw new ImpossibleException( this.getClass().getName() );
    }
  }

  /**
   * Convert an arbitrary color to the HSV model.
   * If an conversion is not supported then {@code null} is returned.
   *
   * @param color the color to convert
   * @return the corresponding color in the HSV model or {@code null} if
   * a conversion is not supported.
   * @see org.extex.color.ColorConverter#toHsv(
   *org.extex.color.Color)
   */
  public HsvColor toHsv( Color color ) {

    try {
      return (HsvColor) color.visit( HSV_CONVERTER, null );
    } catch( GeneralException e ) {
      throw new ImpossibleException( this.getClass().getName() );
    }
  }

  /**
   * Convert an arbitrary color to the RGB model.
   * If an conversion is not supported then {@code null} is returned.
   *
   * @param color the color to convert
   * @return the corresponding color in the RGB model or {@code null} if
   * a conversion is not supported.
   * @see org.extex.color.ColorConverter#toRgb(
   *org.extex.color.Color)
   */
  public RgbColor toRgb( Color color ) {

    try {
      return (RgbColor) color.visit( RGB_CONVERTER, null );
    } catch( GeneralException e ) {
      throw new ImpossibleException( this.getClass().getName() );
    }
  }

}
