/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.typesetter.type.noad;

import de.dante.extex.interpreter.context.TypesettingContext;
import de.dante.extex.interpreter.primitives.register.font.NumberedFont;
import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.interpreter.type.font.Font;
import de.dante.extex.typesetter.TypesetterOptions;
import de.dante.extex.typesetter.type.Node;
import de.dante.extex.typesetter.type.NodeList;
import de.dante.extex.typesetter.type.noad.util.MathContext;
import de.dante.extex.typesetter.type.node.CharNode;
import de.dante.extex.typesetter.type.node.ImplicitKernNode;
import de.dante.util.UnicodeChar;
import de.dante.util.configuration.ConfigurationException;

/**
 * This class provides a container for a mathematical character.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CharNoad extends AbstractNoad implements Noad {

    /**
     * The field <tt>uc</tt> contains the character representation.
     */
    private MathGlyph glyph;

    /**
     * Creates a new object.
     *
     * @param character the character representation
     */
    protected CharNoad(final MathGlyph character) {

        super();
        this.glyph = character;
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
     * @see de.dante.extex.typesetter.type.noad.Noad#toString(
     *      java.lang.StringBuffer)
     */
    public void toString(final StringBuffer sb) {

        glyph.toString(sb);
    }

    /**
     * @see de.dante.extex.typesetter.type.noad.Noad#typeset(
     *      de.dante.extex.typesetter.type.NodeList,
     *      de.dante.extex.typesetter.type.noad.util.MathContext,
     *      de.dante.extex.typesetter.TypesetterOptions)
     */
    public void typeset(final NodeList nodes, final MathContext mathContext,
            final TypesetterOptions context) throws ConfigurationException {

        String type = mathContext.getStyle().getStyleName();
        Font font = context.getFont(NumberedFont.key(context, //
                type, Integer.toString(glyph.getFamily())));
        if (font == null) {
            //gene: impossible
            throw new NullPointerException("font");
        }

        UnicodeChar c = glyph.getCharacter();

        int size = nodes.size();
        if (size > 0) {
            Node n = nodes.get(size - 1);
            if (n instanceof CharNode) {
                CharNode cn = ((CharNode) n);
                if (cn.getTypesettingContext().getFont().equals(font)) {
                    Dimen kerning = font.getGlyph(cn.getCharacter())
                            .getKerning(c);
                    if (kerning.ne(Dimen.ZERO_PT)) {
                        nodes.add(new ImplicitKernNode(kerning));
                    }
                }
            }
        }

        TypesettingContext tc = context.getTypesettingContextFactory()
                .newInstance(context.getTypesettingContext(), font);
        nodes.addGlyph(new CharNode(tc, c));
    }
}