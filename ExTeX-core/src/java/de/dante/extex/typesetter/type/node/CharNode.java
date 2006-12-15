/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.typesetter.type.node;

import org.extex.font.Glyph;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.type.UnicodeChar;
import org.extex.util.exception.GeneralException;

import de.dante.extex.typesetter.type.NodeVisitor;


/**
 * This is the Node which carries a single character.
 *
 * @see "<logo>TeX</logo> &ndash; The Program [134]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class CharNode extends AbstractNode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The field <tt>character</tt> contains the single character represented
     * by this node.
     */
    private UnicodeChar character;

    /**
     * The field <tt>typesettingContext</tt> contains the typesetting context
     */
    private TypesettingContext typesettingContext;

    /**
     * Creates a new object.
     *
     * @param context the typesetting context
     * @param uc the Unicode character
     */
    public CharNode(final TypesettingContext context, final UnicodeChar uc) {

        super();
        typesettingContext = context;
        character = uc;
        Font font = context.getFont();

        FixedGlue x = font.getWidth(uc);
        setWidth(x != null ? x.getLength() : Dimen.ZERO_PT);
        x = font.getHeight(uc);
        setHeight(x != null ? x.getLength() : Dimen.ZERO_PT);
        x = font.getDepth(uc);
        setDepth(x != null ? x.getLength() : Dimen.ZERO_PT);
    }

    /**
     * @see org.extex.typesetter.type.Node#countChars()
     */
    public int countChars() {

        return 1;
    }

    /**
     * Getter for character.
     *
     * @return the character.
     */
    public UnicodeChar getCharacter() {

        return this.character;
    }

    /**
     * @see org.extex.typesetter.type.Node#getChars()
     */
    public CharNode[] getChars() {

        return new CharNode[]{this};
    }

    /**
     * Getter for glyph.
     *
     * @return the glyph
     *
     * @deprecated Try to avoid this method
     */
    public Glyph getGlyph() {

        return this.typesettingContext.getFont().getGlyph(character);
    }

    /**
     * Getter for the space factor.
     *
     * @return the space factor
     */
    public int getSpaceFactor() {

        return 1000; //TODO gene: getSpaceFactor() incomplete
    }

    /**
     * Getter for typesetting  context.
     *
     * @return the typesetting context.
     */
    public TypesettingContext getTypesettingContext() {

        return this.typesettingContext;
    }

    /**
     * @see "<logo>TeX</logo> &ndash; The Program [174]"
     * @see org.extex.typesetter.type.Node#toString(
     *      java.lang.StringBuffer,
     *      java.lang.String,
     *      int,
     *      int)
     */
    public void toString(final StringBuffer sb, final String prefix,
            final int breadth, final int depth) {

        Font font = typesettingContext.getFont();
        sb.append(getLocalizer()
                .format("String.Format",
                        (font == null ? "*" : font.getFontName()),
                        character.toString()));
        if (false) {
            sb.append(" (");
            sb.append(getHeight().toString());
            sb.append("+");
            sb.append(getDepth().toString());
            sb.append(")x");
            sb.append(getWidth().toString());
        }
    }

    /**
     * @see "<logo>TeX</logo> &ndash; The Program [174]"
     * @see org.extex.typesetter.type.Node#toText(
     *      java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toText(final StringBuffer sb, final String prefix) {

        Font font = typesettingContext.getFont();
        sb.append(getLocalizer()
                .format("Text.Format",
                        (font == null ? "*" : font.getFontName()),
                        character.toString()));
    }

    /**
     * @see org.extex.typesetter.type.Node#visit(
     *      org.extex.typesetter.type.NodeVisitor,
     *      java.lang.Object)
     */
    public Object visit(final NodeVisitor visitor, final Object value)
            throws GeneralException {

        return visitor.visitChar(this, value);
    }

}