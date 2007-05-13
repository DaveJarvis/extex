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

package org.extex.unit.color.util;

import org.extex.base.parser.ScaledNumberParser;
import org.extex.color.Color;
import org.extex.color.model.ColorFactory;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.GlueComponent;
import org.extex.core.scaled.ScaledNumber;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.color.ColorConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides a parser for color specifications.
 * Several color models are supported.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4781 $
 */
public final class ColorParser {

    /**
     * This internal interface is used to describe the parsers for the different
     * color models.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4781 $
     */
    private interface ColorMode {

        /**
         * Parse a color value.
         *
         * @param context the interpreter context
         * @param source the token source
         * @param typesetter the typesetter
         * @param alpha the alpha channel
         * @param name the name of the primitive
         *
         * @return the color found
         * 
         * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
         */
        Color parse(Context context, TokenSource source, Typesetter typesetter,
                int alpha, String name) throws HelpingException, TypesetterException;
    }

    /**
     * The field <tt>CMYK_MODE</tt> contains the parser for a cmyk color.
     */
    private static final ColorMode CMYK_MODE = new ColorMode() {

        /**
         * @see org.extex.unit.color.util.ColorParser.ColorMode#parse(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter,
         *      int,
         *      java.lang.String)
         */
        public Color parse(Context context, TokenSource source,
                Typesetter typesetter, int alpha, String name)
                throws HelpingException, TypesetterException {

            int c = scanColorComponent(context, source, typesetter, name);
            int m = scanColorComponent(context, source, typesetter, name);
            int y = scanColorComponent(context, source, typesetter, name);
            int k = scanColorComponent(context, source, typesetter, name);
            return ColorFactory.getCmyk(c, m, y, k, alpha);
        }

    };

    /**
     * The field <tt>GRAY_MODE</tt> contains the parser for a gray-scale color.
     */
    private static final ColorMode GRAY_MODE = new ColorMode() {

        /**
         * @see org.extex.unit.color.util.ColorParser.ColorMode#parse(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter,
         *      int,
         *      java.lang.String)
         */
        public Color parse(Context context, TokenSource source,
                Typesetter typesetter, int alpha, String name)
                throws HelpingException, TypesetterException {

            int gray = scanColorComponent(context, source, typesetter, name);
            return ColorFactory.getGray(gray, alpha);
        }

    };

    /**
     * The field <tt>HSV_MODE</tt> contains the parser for a hsv color.
     */
    private static final ColorMode HSV_MODE = new ColorMode() {

        /**
         * @see org.extex.unit.color.util.ColorParser.ColorMode#parse(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter,
         *      int,
         *      java.lang.String)
         */
        public Color parse(Context context, TokenSource source,
                Typesetter typesetter, int alpha, String name)
                throws HelpingException, TypesetterException {

            int h = scanColorComponent(context, source, typesetter, name);
            int s = scanColorComponent(context, source, typesetter, name);
            int v = scanColorComponent(context, source, typesetter, name);
            return ColorFactory.getHsv(h, s, v, alpha);
        }

    };

    /**
     * @see org.extex.unit.color.util.ColorParser.ColorMode#parse(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      int,
     *      java.lang.String)
     */
    private static final ColorMode RGB_MODE = new ColorMode() {

        /**
         * {@inheritDoc}
         *
         * @see org.extex.unit.color.util.ColorParser.ColorMode#parse(
         *      org.extex.interpreter.context.Context,
         *      org.extex.interpreter.TokenSource,
         *      org.extex.typesetter.Typesetter,
         *      int,
         *      java.lang.String)
         */
        public Color parse(Context context, TokenSource source,
                Typesetter typesetter, int alpha, String name)
                throws HelpingException, TypesetterException {

            int r = scanColorComponent(context, source, typesetter, name);
            int g = scanColorComponent(context, source, typesetter, name);
            int b = scanColorComponent(context, source, typesetter, name);
            return ColorFactory.getRgb(r, g, b, alpha);
        }

    };

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060528L;

    /**
     * Parse a color specification made up of a color constant for one of the
     * supported color models or a control sequence which is bound to
     * color convertible code.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param name the name of the invoking primitive
     *
     * @return the color found
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static Color parseColor(Context context,
            TokenSource source, Typesetter typesetter,
            String name) throws HelpingException, TypesetterException {

        Token t = source.getNonSpace(context);
        Color color = null;
        if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code instanceof ColorConvertible) {
                color =
                    ((ColorConvertible) code).convertColor(context, source,
                            typesetter);
            }
        } else {
            source.push(t);
            color =
                    ColorParser.parseColorConstant(context, source, typesetter,
                        name);
        }
        if (color == null) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(ColorParser.class), "MissingColor");
        }

        return color;
    }

    /**
     * Parse a color specification made up of a color constant for one of the
     * supported color models.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param name the name of the invoking primitive
     *
     * @return the color found
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static Color parseColorConstant(Context context,
            TokenSource source, Typesetter typesetter,
            String name) throws HelpingException, TypesetterException {

        int alpha = 0;
        ColorMode mode = RGB_MODE;

        for (;;) {
            if (source.getKeyword(context, "alpha")) {
                alpha = scanColorComponent(context, source, typesetter, name);
            } else if (source.getKeyword(context, "rgb")) {
                mode = RGB_MODE;
            } else if (source.getKeyword(context, "gray")) {
                mode = GRAY_MODE;
            } else if (source.getKeyword(context, "hsv")) {
                mode = HSV_MODE;
            } else if (source.getKeyword(context, "cmyk")) {
                mode = CMYK_MODE;
            } else {
                break;
            }
        }
        Token t = source.getNonSpace(context);
        if (t == null) {
            throw new EofException(name);
        } else if (!(t instanceof LeftBraceToken)) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(ColorParser.class), "MissingLeftBrace");
        }

        Color color = mode.parse(context, source, typesetter, alpha, name);
        t = source.getNonSpace(context);
        if (!(t instanceof RightBraceToken)) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(ColorParser.class), "MissingRightBrace");
        }
        return color;
    }

    /**
     * Scan a color component and translate it into a color value.
     *
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * @param name the name of the primitive for error messages
     *
     * @return the color component in units of Color.MAX_VALUE
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private static int scanColorComponent(Context context,
            TokenSource source, Typesetter typesetter,
            String name) throws HelpingException, TypesetterException {

        long cc;
        try {
            cc = ScaledNumberParser.parse(context, source, typesetter);
        } catch (EofException e) {
            throw new EofException(name);
        }
        if (cc < 0 || cc > ScaledNumber.ONE) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(ColorParser.class), "IllegalValue", //
                Long.toString(cc));
        }
        return (int) (cc * Color.MAX_VALUE / GlueComponent.ONE);
    }

    /**
     * Creates a new object.
     */
    private ColorParser() {

        // never used
    }

}
