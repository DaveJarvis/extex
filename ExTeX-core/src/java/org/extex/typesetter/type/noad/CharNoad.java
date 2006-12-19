/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.noad;

import java.util.logging.Logger;

import org.extex.font.type.other.NullFont;
import org.extex.interpreter.context.Color;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContextFactory;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.font.FontUtil;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathFontParameter;
import org.extex.typesetter.type.noad.util.MathSpacing;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.ImplicitKernNode;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides a container for a mathematical character.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class CharNoad extends AbstractNoad {

    /**
     * The field <tt>color</tt> contains the color.
     */
    private Color color;

    /**
     * The field <tt>glyph</tt> contains the character representation.
     */
    private MathGlyph glyph;

    /**
     * Creates a new object.
     *
     * @param character the character representation
     * @param tc the typesetting context for the color
     */
    protected CharNoad(final MathGlyph character, final TypesettingContext tc) {

        super();
        this.glyph = character;
        this.color = tc.getColor();
    }

    /**
     * Getter for the character.
     *
     * @return the character.
     */
    public MathGlyph getChar() {

        return this.glyph;
    }

    /**
     * @see org.extex.typesetter.type.noad.Noad#toString(
     *      java.lang.StringBuffer)
     */
    public void toString(final StringBuffer sb) {

        glyph.toString(sb);
    }

    /**
     * @see org.extex.typesetter.type.noad.Noad#typeset(
     *      org.extex.typesetter.type.noad.Noad,
     *      org.extex.typesetter.type.noad.NoadList,
     *      int,
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.type.noad.util.MathContext,
     *      java.util.logging.Logger)
     */
    public void typeset(final Noad previousNoad, final NoadList noads,
            final int index, final NodeList nodes,
            final MathContext mathContext, final Logger logger)
            throws TypesetterException,
                ConfigurationException {

        TypesetterOptions context = mathContext.getOptions();
        StyleNoad style = mathContext.getStyle();
        UnicodeChar c = glyph.getCharacter();
        Font font = context.getFont(MathFontParameter.key(context, //
                style.getFontName(), Integer.toString(glyph.getFamily())));
        if (font.getActualSize().eq(Dimen.ZERO)) {
            throw new TypesetterException(new HelpingException(getLocalizer(),
                    "TTP.UndefinedFamily", style.getStyleName(), Integer
                            .toString(glyph.getFamily()), c.toString()));
        }

        if (font.getWidth(c) == null) {
            FontUtil.charWarning(logger, context, font, c);
            return;
        }

        int size = nodes.size();
        if (size > 0) {
            Node n = nodes.get(size - 1);
            if (n instanceof CharNode) {
                CharNode cn = ((CharNode) n);
                if (cn.getTypesettingContext().getFont().equals(font)) {
                    FixedDimen kerning = font.getKerning(cn.getCharacter(), c);
                    if (kerning.ne(Dimen.ZERO_PT)) {
                        nodes.add(new ImplicitKernNode(kerning, true));
                    }
                }
            }
        }

        TypesettingContextFactory tcFactory = context
                .getTypesettingContextFactory();
        TypesettingContext tc = tcFactory.newInstance(context
                .getTypesettingContext(), font);
        tc = tcFactory.newInstance(tc, color);
        CharNode charNode = new CharNode(tc, c);
        //font.getGlyph(c).getItalicCorrection();
        FixedDimen delta = font.getItalicCorrection(c);
        Node scripts = makeScripts(charNode, mathContext, delta, logger);

        if (scripts != null) {
            nodes.add(scripts);
        } else {
            nodes.add(charNode);
        }

        //see "TTP [755]"
        //TODO gene: insert kern for italic correction if required
    }

}
