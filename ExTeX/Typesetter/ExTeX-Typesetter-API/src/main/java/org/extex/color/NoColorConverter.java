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


import org.extex.color.model.CmykColor;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.HsvColor;
import org.extex.color.model.RgbColor;

/**
 * This implementation of a color converter does no conversions at all. It is a
 * dummy which forces that colors are always given in the target color space.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5563 $
 */
public class NoColorConverter implements ColorConverter {

    /**
     * Creates a new object.
     */
    public NoColorConverter() {

    }

    /**
     * Convert an arbitrary color to the CMYK model.
     * If an conversion is not supported then <code>null</code> is returned.
     *
     * @param color the color to convert
     *
     * @return the corresponding color in the CMYK model or <code>null</code>
     *  if a conversion is not supported.
     *
     * @see org.extex.color.ColorConverter#toCmyk(
     *      org.extex.color.Color)
     */
    public CmykColor toCmyk(Color color) {

        if (color instanceof CmykColor) {
            return (CmykColor) color;

        }

        return null;
    }

    /**
     * Convert an arbitrary color to the RGB model.
     * If an conversion is not supported then <code>null</code> is returned.
     *
     * @param color the color to convert
     *
     * @return the corresponding color in the RGB model or <code>null</code> if
     *  a conversion is not supported.
     *
     * @see org.extex.color.ColorConverter#toGrayscale(
     *      org.extex.color.Color)
     */
    public GrayscaleColor toGrayscale(Color color) {

        if (color instanceof GrayscaleColor) {
            return (GrayscaleColor) color;

        }

        return null;
    }

    /**
     * Convert an arbitrary color to the HSV model.
     * If an conversion is not supported then <code>null</code> is returned.
     *
     * @param color the color to convert
     *
     * @return the corresponding color in the HSV model or <code>null</code> if
     *  a conversion is not supported.
     *
     * @see org.extex.color.ColorConverter#toHsv(
     *      org.extex.color.Color)
     */
    public HsvColor toHsv(Color color) {

        if (color instanceof HsvColor) {
            return (HsvColor) color;

        }

        return null;
    }

    /**
     * Convert an arbitrary color to the RGB model.
     * If an conversion is not supported then <code>null</code> is returned.
     *
     * @param color the color to convert
     *
     * @return the corresponding color in the RGB model or <code>null</code> if
     *  a conversion is not supported.
     *
     * @see org.extex.color.ColorConverter#toRgb(
     *      org.extex.color.Color)
     */
    public RgbColor toRgb(Color color) {

        if (color instanceof RgbColor) {
            return (RgbColor) color;

        }

        return null;
    }

}
