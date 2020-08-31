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

package org.extex.unit.omega.math.delimiter;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingNumberException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.math.MathClass;
import org.extex.typesetter.type.math.MathClassVisitor;
import org.extex.typesetter.type.math.MathDelimiter;
import org.extex.typesetter.type.noad.MathGlyph;
import org.extex.unit.tex.math.AbstractMathCode;
import org.extex.unit.tex.math.delimiter.Delimiter;

/**
 * This abstract class adds the ability to translate
 * {@link org.extex.typesetter.type.math.MathDelimiter MathDelimiter}s to and
 * from their  Omega encoding as numbers to abstract math code.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractOmegaDelimiter extends AbstractMathCode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The constant {@code CHAR_MASK} contains the character mask.
     */
    private static final int CHAR_MASK = 0xffff;

    /**
     * The field {@code CHAR_MAX} contains the maximal number for a character.
     */
    private static final int CHAR_MAX = 0xffff;

    /**
     * The field {@code CHAR_MIN} contains the minimal number for a character.
     */
    private static final int CHAR_MIN = 0;

    /**
     * The field {@code CLASS_MASK} contains the mask for the class. This
     * implies a maximal value.
     */
    private static final int CLASS_MASK = 0xf;

    /**
     * The constant {@code CLASS_MAX} contains the maximum number for a math
     * class.
     */
    private static final int CLASS_MAX = CLASS_MASK;

    /**
     * The field {@code CLASS_SHIFT} contains the number of bits to shift the
     * class rightwards in the  Omega encoding of delimiters.
     */
    private static final int CLASS_SHIFT = 24;

    /**
     * The field {@code FAM_MAX} contains the maximum of the family number.
     */
    private static final int FAM_MAX = 255;

    /**
     * The field {@code FAM_MIN} contains the minimum of the family number.
     */
    private static final int FAM_MIN = 0;

    /**
     * The field {@code LARGE_CLASS_OFFSET} contains the offset for large
     * character's class.
     */
    private static final int LARGE_CLASS_OFFSET = 8;

    /**
     * The field {@code MCV} contains the visitor to map a math class to
     * numbers.
     */
    private static final MathClassVisitor<Integer, Object, Object> MCV =
            new MathClassVisitor<Integer, Object, Object>() {

        public Integer visitBinary(Object ignore, Object ignore2) {

                    return new Integer(2);
                }

        public Integer visitClosing(Object ignore, Object ignore2) {

                    return new Integer(5);
                }

        public Integer visitLarge(Object ignore, Object ignore2) {

                    return new Integer(1);
                }

        public Integer visitOpening(Object ignore, Object ignore2) {

                    return new Integer(4);
                }

        public Integer visitOrdinary(Object ignore, Object ignore2) {

                    return new Integer(0);
                }

        public Integer visitPunctation(Object ignore, Object ignore2) {

                    return new Integer(6);
                }

        public Integer visitRelation(Object ignore, Object ignore2) {

                    return new Integer(3);
                }

        public Integer visitVariable(Object ignore, Object ignore2) {

                    return new Integer(7);
                }
            };

    /**
     * The field {@code SMALL_CHAR_OFFSET} contains the offset for the small
     * characters.
     */
    private static final int SMALL_CHAR_OFFSET = 12;

    /**
     * The field {@code SMALL_CLASS_OFFSET} contains the offset for the small
     * character's class.
     */
    private static final int SMALL_CLASS_OFFSET = 20;

    /**
     * Create a localizer for this class.
     * 
     * @return the localizer
     */
    protected static Localizer getMyLocalizer() {

        return LocalizerFactory.getLocalizer(AbstractOmegaDelimiter.class);
    }

    /**
     * Creates a new MathDelimiter object from the  Omega encoding.
     * <p>
     * The TeX encoding interprets the number as 27 bit hex number:
     * {@code "cssyyyyllxxxx}. Here the digits have the following meaning:
     * </p>
     * <dl>
     * <dt>c</dt>
     * <dd>the math class of this delimiter. It has a range from 0 to 7.</dd>
     * <dt>ll</dt>
     * <dd>the family for the large character. It has a range from 0 to 255.</dd>
     * <dt>xxxx</dt>
     * <dd>the character code of the large character.</dd>
     * <dt>ss</dt>
     * <dd>the family for the small character. It has a range from 0 to 255.</dd>
     * <dt>yyyy</dt>
     * <dd>the character code of the small character.</dd>
     * </dl>
     *
     * @param delcode the  Omega encoding for the delimiter
     * 
     * @return a new MathDelimiter
     * 
     * @throws HelpingException in case of a parameter out of range
     */
    public static MathDelimiter newMathDelimiter(long delcode)
            throws HelpingException {

        int classCode = (int) ((delcode >> CLASS_SHIFT));

        if (delcode < 0 || classCode > CLASS_MAX) {
            throw new HelpingException(getMyLocalizer(),
                "TTP.BadDelimiterCode", "\"" + Long.toHexString(delcode));
        }
        MathClass mathClass = MathClass.getMathClass(classCode);
        MathGlyph smallChar =
                new MathGlyph(
                    (int) ((delcode >> SMALL_CLASS_OFFSET) & CLASS_MASK),
                    UnicodeChar
                        .get((int) ((delcode >> SMALL_CHAR_OFFSET) & CHAR_MASK)));
        MathGlyph largeChar =
                new MathGlyph(
                    (int) ((delcode >> LARGE_CLASS_OFFSET) & CLASS_MASK),
                    UnicodeChar.get((int) (delcode & CHAR_MASK)));
        return new MathDelimiter(mathClass, smallChar, largeChar);
    }

    /**
     * Parse an extended εχTeX delimiter from a token source.
     * 
     * @param context the interpreter context
     * @param source the token source to read from
     * @param typesetter the typesetter
     * @param mClass the math class
     * @param primitive the name of the primitive for error handling
     * 
     * @return the MathDelimiter found
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private static MathDelimiter parse(Context context, TokenSource source,
            Typesetter typesetter, MathClass mClass, CodeToken primitive)
            throws HelpingException,
                TypesetterException {

        int smallFam = (int) source.parseNumber(context, source, typesetter);
        UnicodeChar smallChar =
                source.scanCharacterCode(context, typesetter, primitive);
        int largeFam = (int) source.parseNumber(context, source, typesetter);
        UnicodeChar largeChar =
                source.scanCharacterCode(context, typesetter, primitive);

        return new MathDelimiter(mClass, new MathGlyph(smallFam, smallChar),
            new MathGlyph(largeFam, largeChar));
    }

    /**
     * Parse a math delimiter.
     * 
     * <pre>
     *  \delimiter"1234567
     *  \delimiter open 22 `[ 1 `(
     * </pre>
     * 
     * @param context the interpreter context
     * @param source the token source to read from
     * @param typesetter the typesetter
     * @param primitive the name of the primitive for error handling
     * 
     * @return the MathDelimiter acquired
     * 
     * @throws ConfigurationException in case of an configuration error
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static MathDelimiter parseDelimiter(Context context,
            TokenSource source, Typesetter typesetter, CodeToken primitive)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {

            if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);
                if (code instanceof Delimiter) {
                    return newMathDelimiter(source.parseNumber(context, source,
                        typesetter));
                } else if (code instanceof ExpandableCode) {
                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);
                    // retry within the outer loop
                } else {
                    throw new HelpingException(getMyLocalizer(),
                        "TTP.MissingDelim");
                }
            } else {
                MathDelimiter del = context.getDelcode(t.getChar());
                if (del != null) {
                    return del;
                } else if (t instanceof OtherToken) {
                    source.push(t);
                    try {
                        return newMathDelimiter(source.parseNumber(context,
                            source, typesetter));
                    } catch (MissingNumberException e) {
                        throw new HelpingException(getMyLocalizer(),
                            "TTP.MissingDelim");
                    }
                } else {
                    source.push(t);
                    switch (t.getChar().getCodePoint()) {
                        case 'b':
                            if (source.getKeyword(context, "bin")) {
                                return parse(context, source, typesetter,
                                    MathClass.BINARY, primitive);
                            }
                            break;
                        case 'c':
                            if (source.getKeyword(context, "close")) {
                                return parse(context, source, typesetter,
                                    MathClass.CLOSING, primitive);
                            }
                            break;
                        case 'l':
                            if (source.getKeyword(context, "large")) {
                                return parse(context, source, typesetter,
                                    MathClass.LARGE, primitive);
                            }
                            break;
                        case 'o':
                            if (source.getKeyword(context, "open")) {
                                return parse(context, source, typesetter,
                                    MathClass.OPENING, primitive);
                            } else if (source.getKeyword(context, "ord")) {
                                return parse(context, source, typesetter,
                                    MathClass.ORDINARY, primitive);
                            }
                            break;
                        case 'p':
                            if (source.getKeyword(context, "punct")) {
                                return parse(context, source, typesetter,
                                    MathClass.PUNCTATION, primitive);
                            }
                            break;
                        case 'r':
                            if (source.getKeyword(context, "rel")) {
                                return parse(context, source, typesetter,
                                    MathClass.RELATION, primitive);
                            }
                            break;
                        case 'v':
                            if (source.getKeyword(context, "var")) {
                                return parse(context, source, typesetter,
                                    MathClass.VARIABLE, primitive);
                            }
                            break;
                        default:
                            throw new HelpingException(getMyLocalizer(),
                                "TTP.MissingDelim");
                    }
                }
            }
        }
        throw new EofException();
    }

    /**
     * Translate the delimiter into a  Omega encoded number or throw
     * an exception if this is not possible.
     * 
     * @param del the delimiter to encode
     * 
     * @return the  Omega encoded delimiter
     * 
     * @throws HelpingException in case of an error
     */
    public static long delimiterToLong(MathDelimiter del)
            throws HelpingException {

        if (del == null) {
            return -1;
        }

        long value =
                ((Integer) del.getMathClass().visit(MCV, null, null))
                    .longValue() << CLASS_SHIFT;

        int fam0 = del.getSmallChar().getFamily();
        if (fam0 < FAM_MIN || fam0 > FAM_MAX) {
            throw new HelpingException(getMyLocalizer(), "ExtendedDelimiter");
        }
        int c0 = del.getSmallChar().getCharacter().getCodePoint();
        if (c0 < CHAR_MIN || c0 > CHAR_MAX) {
            throw new HelpingException(getMyLocalizer(), "ExtendedDelimiter");
        }
        int fam1 = del.getLargeChar().getFamily();
        if (fam1 < FAM_MIN || fam1 > FAM_MAX) {
            throw new HelpingException(getMyLocalizer(), "ExtendedDelimiter");
        }
        int c1 = del.getLargeChar().getCharacter().getCodePoint();
        if (c1 < CHAR_MIN || c1 > CHAR_MAX) {
            throw new HelpingException(getMyLocalizer(), "ExtendedDelimiter");
        }
        value |= fam0 << SMALL_CLASS_OFFSET;
        value |= c0 << SMALL_CHAR_OFFSET;
        value |= fam1 << LARGE_CLASS_OFFSET;
        value |= c1;
        return value;
    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public AbstractOmegaDelimiter(CodeToken token) {

        super(token);
    }

}
