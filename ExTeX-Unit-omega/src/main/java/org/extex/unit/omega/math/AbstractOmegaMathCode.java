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

package org.extex.unit.omega.math;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.math.MathClass;
import org.extex.typesetter.type.math.MathClassVisitor;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.noad.MathGlyph;
import org.extex.unit.tex.math.AbstractMathCode;
import org.extex.unit.tex.math.util.MathCodeConvertible;

/**
 * This is the base class for all math primitives using the Omega encoding. It
 * tries to ensure that the primitive is invoked in math mode only.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractOmegaMathCode extends AbstractMathCode {

    /**
     * The field <tt>CLASS_OFFSET</tt> contains the offset for adjoining the
     * math class.
     */
    private static final int CLASS_OFFSET = 24;

    /**
     * The field <tt>SPECIAL_MATH_CODE</tt> contains the special math code.
     */
    private static final int SPECIAL_MATH_CODE = 0x8000000;

    /**
     * The constant <tt>CHARACTER_MASK</tt> contains the mask for the
     * character value in the <logo>TeX</logo> encoding.
     */
    private static final int CHARACTER_MASK = 0xffff;

    /**
     * The constant <tt>FAMILY_MASK</tt> contains the mask for the family in
     * the <logo>TeX</logo> encoding.
     */
    private static final int FAMILY_MASK = 0xff;

    /**
     * The constant <tt>FAMILY_OFFSET</tt> contains the offset for the family
     * in the <logo>TeX</logo> encoding.
     */
    private static final int FAMILY_OFFSET = 16;

    /**
     * The field <tt>VISITOR</tt> contains the visitor for mapping a math
     * class to the integer representation used by Omega.
     */
    private static final MathClassVisitor<Long, Object, Object> VISITOR =
            new MathClassVisitor<Long, Object, Object>() {

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitBinary(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitBinary(Object arg, Object arg2) {

                    return new Long(2);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitClosing(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitClosing(Object arg, Object arg2) {

                    return new Long(5);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitLarge(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitLarge(Object arg, Object arg2) {

                    return new Long(1);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitOpening(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitOpening(Object arg, Object arg2) {

                    return new Long(4);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitOrdinary(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitOrdinary(Object arg, Object arg2) {

                    return new Long(0);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitPunctation(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitPunctation(Object arg, Object arg2) {

                    return new Long(6);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitRelation(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitRelation(Object arg, Object arg2) {

                    return new Long(3);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitVariable(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Long visitVariable(Object arg, Object arg2) {

                    return new Long(7);
                }
            };

    /**
     * Convert a {@link MathCode MathCode} to a number using the TeX encoding.
     * 
     * @param mc the math code
     * 
     * @return a TeX-encoded math code
     * 
     * @throws HelpingException in case of an error
     */
    public static long mathCodeToLong(MathCode mc) throws HelpingException {

        MathClass mathClass = mc.getMathClass();
        if (mathClass == null) {
            return SPECIAL_MATH_CODE;
        }
        MathGlyph mg = mc.getMathGlyph();
        long codePoint = mg.getCharacter().getCodePoint();
        if (codePoint > CHARACTER_MASK) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(AbstractOmegaMathCode.class),
                "InvalidCharacterCode");
        }
        long mathFamily = mg.getFamily();
        if (mathFamily > FAMILY_MASK) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(AbstractOmegaMathCode.class), "InvalidFamilyCode");
        }
        long cat = ((Long) mathClass.visit(VISITOR, null, null)).longValue();
        return (cat << CLASS_OFFSET) | (mathFamily << FAMILY_OFFSET)
                | codePoint;

    }

    /**
     * Parse Math code according to TeX rules and extensions.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param primitive the name of the invoking primitive
     * 
     * @return the MathCode
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static MathCode parseMathCode(Context context, TokenSource source,
            Typesetter typesetter, CodeToken primitive)
            throws HelpingException,
                TypesetterException {

        Token t = source.getToken(context);
        if (t instanceof LeftBraceToken) {
            MathClass mc =
                    (MathClass) source.parse(MathClass.class, context, source,
                        typesetter);
            long family = source.parseNumber(context, source, typesetter);
            UnicodeChar c =
                    source.scanCharacterCode(context, typesetter, primitive);

            t = source.getToken(context);
            if (!(t instanceof RightBraceToken)) {
                if (t == null) {
                    throw new EofException();
                }
                throw new HelpingException(LocalizerFactory
                    .getLocalizer(AbstractOmegaMathCode.class),
                    "MissingRightBrace");
            }
            return new MathCode(mc, new MathGlyph((int) family, c));
        } else if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code instanceof MathCodeConvertible) {
                return ((MathCodeConvertible) code).convertMathCode(context,
                    source, typesetter);
            }
        }

        source.push(t);
        long code = source.parseInteger(context, source, typesetter);

        if (code < 0 || code > SPECIAL_MATH_CODE) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(AbstractOmegaMathCode.class),
                "TTP.BadMathCharCode", Long.toString(code));
        } else if (code == SPECIAL_MATH_CODE) {
            return new MathCode(null, null);
        } else {
            return new MathCode(MathClass
                .getMathClass((int) (code >> CLASS_OFFSET)), //
                new MathGlyph((int) (code >> FAMILY_OFFSET) & FAMILY_MASK, //
                    UnicodeChar.get((int) (code & CHARACTER_MASK))));
        }

    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public AbstractOmegaMathCode(CodeToken token) {

        super(token);
    }

}
