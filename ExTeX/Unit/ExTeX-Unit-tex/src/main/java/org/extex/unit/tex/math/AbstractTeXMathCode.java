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

package org.extex.unit.tex.math;

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
import org.extex.unit.tex.math.util.MathCodeConvertible;

/**
 * This is the base class for all math primitives using the TeX encoding. It
 * tries to ensure that the primitive is invoked in math mode only.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public abstract class AbstractTeXMathCode extends AbstractMathCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 24012007L;

    /**
     * The field <tt>CLASS_OFFSET</tt> contains the offset for adjoining the
     * math class.
     */
    private static final int CLASS_OFFSET = 12;

    /**
     * The field <tt>SPECIAL_MATH_CODE</tt> contains the special math code.
     */
    private static final int SPECIAL_MATH_CODE = 0x8000;

    /**
     * The constant <tt>CHARACTER_MASK</tt> contains the mask for the character
     * value in the <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> encoding.
     */
    private static final int CHARACTER_MASK = 0xff;

    /**
     * The constant <tt>FAMILY_MASK</tt> contains the mask for the family in the
     * <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> encoding.
     */
    private static final int FAMILY_MASK = 0xf;

    /**
     * The constant <tt>FAMILY_OFFSET</tt> contains the offset for the family in
     * the <logo>T<span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     * >e</span>X</logo> encoding.
     */
    private static final int FAMILY_OFFSET = 8;

    /**
     * The field <tt>VISITOR</tt> contains the visitor for mapping a math class
     * to the integer representation used by TeX..
     */
    private static final MathClassVisitor<Integer, Object, Object> VISITOR =
            new MathClassVisitor<Integer, Object, Object>() {

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitBinary(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitBinary(Object arg, Object arg2) {

                    return new Integer(2);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitClosing(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitClosing(Object arg, Object arg2) {

                    return new Integer(5);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitLarge(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitLarge(Object arg, Object arg2) {

                    return new Integer(1);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitOpening(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitOpening(Object arg, Object arg2) {

                    return new Integer(4);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitOrdinary(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitOrdinary(Object arg, Object arg2) {

                    return new Integer(0);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitPunctation(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitPunctation(Object arg, Object arg2) {

                    return new Integer(6);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitRelation(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitRelation(Object arg, Object arg2) {

                    return new Integer(3);
                }

                /**
                 * @see org.extex.typesetter.type.math.MathClassVisitor#visitVariable(java.lang.Object,
                 *      java.lang.Object)
                 */
                public Integer visitVariable(Object arg, Object arg2) {

                    return new Integer(7);
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
        int codePoint = mg.getCharacter().getCodePoint();
        if (codePoint > CHARACTER_MASK) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(AbstractTeXMathCode.class),
                "InvalidCharacterCode");
        }
        int mathFamily = mg.getFamily();
        if (mathFamily > FAMILY_MASK) {
            throw new HelpingException(
                LocalizerFactory.getLocalizer(AbstractTeXMathCode.class),
                "InvalidFamilyCode");
        }
        return (((Integer) mathClass.visit(VISITOR, null, null)).intValue() << CLASS_OFFSET)
                | (mathFamily << FAMILY_OFFSET) | codePoint;

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
                throw new HelpingException(
                    LocalizerFactory.getLocalizer(AbstractTeXMathCode.class),
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
            throw new HelpingException(
                LocalizerFactory.getLocalizer(AbstractTeXMathCode.class),
                "TTP.BadMathCharCode", Long.toString(code));
        } else if (code == SPECIAL_MATH_CODE) {
            return new MathCode(null, null);
        } else {
            return new MathCode(
                MathClass.getMathClass((int) (code >> CLASS_OFFSET)),
                new MathGlyph((int) (code >> FAMILY_OFFSET) & FAMILY_MASK,
                    UnicodeChar.get((int) (code & CHARACTER_MASK))));
        }

    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public AbstractTeXMathCode(CodeToken token) {

        super(token);
    }

}
