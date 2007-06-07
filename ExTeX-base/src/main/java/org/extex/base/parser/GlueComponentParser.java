/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.base.parser;

import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.GlueComponent;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides a means to store floating numbers with an order.
 *
 * <p>Examples</p>
 * <pre>
 * 123 pt
 * -123 pt
 * 123.456 pt
 * 123.pt
 * .465 pt
 * -.456pt
 * +456pt
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4399 $
 */
public class GlueComponentParser {

    /**
     * The constant <tt>BP100_PER_IN</tt> contains the number of 100 big points
     * per inch.
     */
    private static final int BP100_PER_IN = 7200;

    /**
     * The constant <tt>CM100_PER_IN</tt> contains the number of 100 centimeters
     * per inch.
     */
    private static final int CM100_PER_IN = 254;

    /**
     * The constant <tt>MAX_ORDER</tt> contains the maximal allowed order.
     */
    private static final int MAX_ORDER = 4;

    /**
     * The field <tt>POINT_PER_100_IN</tt> contains the conversion factor from
     * inch to point. The value contained is the number of points in 100 inch.
     */
    private static final int POINT_PER_100_IN = 7227;

    /**
     * The constant <tt>PT_PER_PC</tt> contains the number of points per pica.
     */
    private static final int PT_PER_PC = 12;

    /**
     * Getter for the localizer.
     * The localizer is associated with the name of the class GlueComponent.
     *
     * @return the localizer
     */
    protected static Localizer getMyLocalizer() {

        return LocalizerFactory.getLocalizer(GlueComponentParser.class);
    }

    /**
     * Creates a new object.
     *
     * @param context the interpreter context
     * @param source the source for the tokens to be read
     * @param typesetter the typesetter
     * @param fixed if <code>true</code> then no glue order is allowed
     *
     * @return a new instance with the value from the input stream
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static GlueComponent parse(Context context,
            TokenSource source, Typesetter typesetter,
            boolean fixed) throws HelpingException, TypesetterException {

        long value = 0;

        Token t;
        for (;;) {
            t = source.getNonSpace(context);
            if (t == null) {
                throw new HelpingException(getMyLocalizer(), "TTP.IllegalUnit");
            } else if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof DimenConvertible) {
                    value =
                            ((DimenConvertible) code).convertDimen(context,
                                source, typesetter);
                    return new GlueComponent(value);
                } else if (code instanceof ExpandableCode) {
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        value = ScaledNumberParser.scanFloat(context, source, typesetter, t);

        GlueComponent gc =
                attachUnit(value, context, source, typesetter, fixed);
        if (gc == null) {
            // cf. TTP [459]
            throw new HelpingException(getMyLocalizer(), "TTP.IllegalUnit");
        }
        return gc;
    }

    /**
     * Convert a value by scanning a unit and storing its converted value in a
     * GlueComponent.
     *
     * @param context the interpreter context
     * @param source the source for the tokens to be read
     * @param typesetter the typesetter
     * @param fixed if <code>true</code> then no glue order is allowed
     * @param value the value to encapsulate
     *
     * @return the value converted into a GlueComponent or <code>null</code>
     *  if no suitable unit could be found
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static GlueComponent attachUnit(long value,
            Context context, TokenSource source,
            Typesetter typesetter, boolean fixed)
            throws HelpingException, TypesetterException {

        Token t;
        long v = value;
        long mag = 1000;
        if (source.getKeyword(context, "true")) { // cf. TTP[453], TTP[457]
            mag = context.getMagnification();
        }

        t = source.getNonSpace(context);

        // cf. TTP[458]
        if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code instanceof DimenConvertible) {
                v *= ((DimenConvertible) code).convertDimen(context, //
                    source, typesetter) / GlueComponent.ONE;
                return new GlueComponent(v);
            }
        } else if (t instanceof LetterToken) {
            int c = t.getChar().getCodePoint();
            t = source.getToken(context);
            if (t == null) {
                throw new HelpingException(getMyLocalizer(), "TTP.IllegalUnit");
            }
            switch (c) {
                case 'b':
                    if (t.equals(Catcode.LETTER, 'p')) {
                        v = v * POINT_PER_100_IN / BP100_PER_IN;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'c':
                    if (t.equals(Catcode.LETTER, 'm')) {
                        v = v * POINT_PER_100_IN / CM100_PER_IN;
                    } else if (t.equals(Catcode.LETTER, 'c')) {
                        v = v * 14856 / 1157;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'd':
                    if (t.equals(Catcode.LETTER, 'd')) {
                        v = v * 1238 / 1157;
                    } else if (t.equals(Catcode.LETTER, 'm')) {
                        v = v * POINT_PER_100_IN / CM100_PER_IN / 10;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'e':
                    if (t.equals(Catcode.LETTER, 'x')) {
                        FixedDimen ex =
                                context.getTypesettingContext().getFont()
                                    .getEm();
                        v = v * ex.getValue() / GlueComponent.ONE;
                    } else if (t.equals(Catcode.LETTER, 'm')) {
                        FixedDimen em =
                                context.getTypesettingContext().getFont()
                                    .getEm();
                        v = v * em.getValue() / GlueComponent.ONE;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'f':
                    if (fixed && t.equals(Catcode.LETTER, 'i')) {
                        int order = 1;
                        for (t = source.getToken(context); //
                        (t != null && (t.equals('l') || t.equals('L'))); //
                        t = source.getToken(context)) {
                            order++;
                        }
                        source.push(t);
                        if (order > MAX_ORDER) {
                            break;
                        }
                        source.skipSpace();
                        return new GlueComponent(v, (byte) order);
                    }
                    break;
                case 'i':
                    if (t.equals(Catcode.LETTER, 'n')) {
                        v = v * POINT_PER_100_IN / 100;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'k':
                    if (t.equals(Catcode.LETTER, 'm')) {
                        v = v * POINT_PER_100_IN / CM100_PER_IN / 100000;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'm':
                    if (t.equals(Catcode.LETTER, 'm')) {
                        v = v * POINT_PER_100_IN / (CM100_PER_IN * 10);
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'n':
                    if (t.equals(Catcode.LETTER, 'd')) {
                        v = v * 4818 / 635;
                    } else if (t.equals(Catcode.LETTER, 'c')) {
                        v = v * 803 / 1270;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 'p':
                    if (t.equals(Catcode.LETTER, 't')) {
                        // nothing to do
                    } else if (t.equals(Catcode.LETTER, 'c')) {
                        v = v * PT_PER_PC;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                case 's':
                    if (t.equals(Catcode.LETTER, 'p')) {
                        v = v / GlueComponent.ONE;
                    } else {
                        break;
                    }
                    if (mag != 1000) {
                        v = v * mag / 1000;
                    }
                    source.skipSpace();
                    return new GlueComponent(v);
                default:
                    break;
            }
        }

        source.push(t);
        return null;
    }

    /**
     * Creates a new object from a TokenStream.
     *
     * @param source the source for new tokens
     * @param context the interpreter context
     * @param typesetter the typesetter
     *
     * @return a new instance with the value from the input stream
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static GlueComponent parse(TokenSource source,
            Context context, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        return parse(context, source, typesetter, false);
    }

    /**
     * Creates a new object.
     */
    private GlueComponentParser() {
        // never used
    }

}
