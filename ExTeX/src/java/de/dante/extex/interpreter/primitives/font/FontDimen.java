/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package de.dante.extex.interpreter.primitives.font;

import de.dante.extex.font.EFMType1AFMFont;
import de.dante.extex.i18n.GeneralHelpingException;
import de.dante.extex.interpreter.AbstractCode;
import de.dante.extex.interpreter.Code;
import de.dante.extex.interpreter.Flags;
import de.dante.extex.interpreter.Theable;
import de.dante.extex.interpreter.TokenSource;
import de.dante.extex.interpreter.context.Context;
import de.dante.extex.interpreter.type.Dimen;
import de.dante.extex.interpreter.type.Font;
import de.dante.extex.interpreter.type.Tokens;
import de.dante.extex.scanner.ControlSequenceToken;
import de.dante.extex.scanner.Token;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;

/**
 * This class provides an implementation for the primitive
 * <code>\fontdimen</code>.
 * <p>
 * Example:
 * <pre>
 * \fontdimen13\ff=5pt
 * \the\fontdimen13\ff
 * </pre>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontDimen extends AbstractCode implements Theable {

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public FontDimen(final String name) {

        super(name);
    }

    /**
     * @see de.dante.extex.interpreter.Code#execute(de.dante.extex.interpreter.Flags,
     *      de.dante.extex.interpreter.context.Context,
     *      de.dante.extex.interpreter.TokenSource,
     *      de.dante.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws GeneralException {

        // \fontdimen13\ff=5pt
        int idx = (int) source.scanInteger();
        Token tok = source.scanNonSpace();
        if (tok == null || !(tok instanceof ControlSequenceToken)) {
            throw new GeneralHelpingException("FONT.nofontprimitive");
        }
        Code code = context.getMacro(tok.getValue());
        if (code == null || !(code instanceof FontCode)) {
            throw new GeneralHelpingException("FONT.nofontprimitive");
        }
        source.scanOptionalEquals();
        Dimen size = new Dimen(context, source);
        Font font = ((FontCode) code).getFont();

        // TODO incomplete
        if (font instanceof EFMType1AFMFont) {
            font.setFontDimen("#" + String.valueOf(idx), size);
        } else {
            throw new GeneralHelpingException("FONT.wrongtype");
        }
        /*if (font.getFontType().equals("tfm-normal")) {
         setDimenValue(font, idx, size, paramLabelnormal);
         } else if (font.getFontType().equals("tfm-mathext")) {
         setDimenValue(font, idx, size, paramLabelmathext);
         } else if (font.getFontType().equals("tfm-mathsyml")) {
         setDimenValue(font, idx, size, paramLabelmathsyml);
         } else {
         throw new GeneralHelpingException("FONT.wrongtype");
         }
         */
        prefix.clear();
    }

    //    /**
    //     * Set the dimen-value.
    //     * @param font            the font
    //     * @param idx            the idx from the parameter (index start with 0 -> +1!)
    //     * @param size            the new size
    //     * @param paramLabel    the name of the parametr
    //     * @throws GeneralException if a error occoured
    //     */
    //    private void setDimenValue(Font font, int idx, Dimen size,
    //            final String[] paramLabel) throws GeneralException {
    //
    //        if (idx <= 0 && idx > paramLabel.length) {
    //            throw new GeneralHelpingException("FONT.wrongdimennumber", String
    //                    .valueOf(paramLabel.length - 1));
    //        }
    //        font.setFontDimen(paramLabel[idx - 1], size);
    //    }

    //    /**
    //     * Return the dimen-value
    //     * @param font            the font
    //     * @param idx            the idx from the parameter (index start with 0 -> +1!)
    //     * @param paramLabel    the name of the parametr
    //     * @throws GeneralException if a error occoured
    //     */
    //    private Dimen getDimenValue(final Font font, final int idx,
    //            final String[] paramLabel) throws GeneralException {
    //
    //        if (idx <= 0 && idx > paramLabel.length) {
    //            throw new GeneralHelpingException("FONT.wrongdimennumber", String
    //                    .valueOf(paramLabel.length - 1));
    //        }
    //        return font.getFontDimen(paramLabel[idx - 1]);
    //    }

    /**
     * @see de.dante.extex.interpreter.Theable#the(
     *       de.dante.extex.interpreter.context.Context, de.dante.extex.interpreter.TokenSource)
     */
    public Tokens the(final Context context, final TokenSource source)
            throws GeneralException {

        // \the\fontdimen7\ff
        int idx = (int) source.scanInteger();
        Token tok = source.scanNonSpace();
        if (tok == null || !(tok instanceof ControlSequenceToken)) {
            throw new GeneralHelpingException("FONT.nofontprimitive");
        }
        Code code = context.getMacro(tok.getValue());
        if (code == null || !(code instanceof FontCode)) {
            throw new GeneralHelpingException("FONT.nofontprimitive");
        }
        Font font = ((FontCode) code).getFont();

        Dimen size = new Dimen(0);

        // TODO incomplete
        if (font instanceof EFMType1AFMFont) {
            size = font.getFontDimen("#" + String.valueOf(idx));
        } else {
            throw new GeneralHelpingException("FONT.wrongtype");
        }
        return new Tokens(context, size.toString());
    }

    /**
     * normal paramter names
     */
    private static final String[] NORMAL = {"SLANT", "SPACE", "STRETCH",
            "SHRINK", "XHEIGHT", "QUAD", "EXTRASPACE"};

    /**
     * mathext parameter names
     */
    private static final String[] MATHEXT = {"SLANT", "SPACE", "STRETCH",
            "SHRINK", "XHEIGHT", "QUAD", "EXTRASPACE", "DEFAULTRULETHICKNESS",
            "BIGOPSPACING1", "BIGOPSPACING2", "BIGOPSPACING3", "BIGOPSPACING4",
            "BIGOPSPACING5"};

    /**
     * mathsyml-parameternames
     */
    private static final String[] MATHSYML = {"SLANT", "SPACE", "STRETCH",
            "SHRINK", "XHEIGHT", "QUAD", "EXTRASPACE", "NUM1", "NUM2", "NUM3",
            "DENOM1", "DENOM2", "SUP1", "SUP2", "SUP3", "SUB1", "SUB2",
            "SUPDROP", "SUBDROP", "DELIM1", "DELIM2", "AXISHEIGHT"};

}
