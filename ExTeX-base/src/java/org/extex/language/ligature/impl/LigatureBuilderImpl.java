/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.language.ligature.impl;

import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.font.Font;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.ImplicitKernNode;
import org.extex.typesetter.type.node.LigatureNode;

/**
 * This class provides an implementation for a ligature builder.
 * Kerning and ligatures are inserted according to the specification from the
 * font.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
 */
public class LigatureBuilderImpl implements LigatureBuilder {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     */
    public LigatureBuilderImpl() {

        super();
    }

    /**
     * Get a single ligature of to characters.
     *
     * @param c1 the first character
     * @param c2 the second character
     * @param f the current font
     *
     * @return the ligature of c1 and c2 or <code>null</code> if none exists
     *
     * @see org.extex.language.ligature.LigatureBuilder#getLigature(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar,
     *      org.extex.interpreter.type.font.Font)
     */
    public UnicodeChar getLigature(final UnicodeChar c1, final UnicodeChar c2,
            final Font f) {

        return f.getLigature(c1, c2);
    }

    /**
     * Take a node list and transform character sequences into ligatures where
     * appropriate. The processing should extend over all characters with the
     * same font and non-character nodes. It should return the control to the
     * caller as soon as a character node with another font is found.
     *
     * @param list the node list to create ligatures for
     * @param start the index in the list to start processing
     *
     * @return the index after last node processed
     *
     * @throws HyphenationException in case of an error
     *
     * @see org.extex.language.ligature.LigatureBuilder#insertLigatures(
     *      org.extex.typesetter.type.NodeList, int)
     */
    public int insertLigatures(final NodeList list, final int start)
            throws HyphenationException {

        int i = start;
        while (i < list.size() && !(list.get(i) instanceof CharNode)) {
            i++;
        }
        if (i >= list.size()) {
            return i;
        }
        CharNode lastNode = (CharNode) list.get(i);
        TypesettingContext lastTc = lastNode.getTypesettingContext();
        Font font = lastTc.getFont();
        UnicodeChar lastC = lastNode.getCharacter();

        while (++i < list.size()) {

            Node node = list.get(i);
            if (!(node instanceof CharNode)) {
                return i;
            }
            CharNode thisNode = (CharNode) node;
            if (thisNode.getTypesettingContext() != lastTc) {
                return i;
            }
            UnicodeChar thisC = thisNode.getCharacter();

            UnicodeChar lig = font.getLigature(lastC, thisC);
            if (lig != null) {
                list.remove(i--);
                list.remove(i);
                lastNode = new LigatureNode(lastTc, lig, lastNode, thisNode);
                list.add(i--, lastNode);
                lastC = lig;
            } else {
                FixedDimen kern = font.getKerning(lastC, thisC);
                if (kern != null && kern.ne(Dimen.ZERO)) {
                    list.add(i, new ImplicitKernNode(kern, true));
                    i++;
                }
                lastNode = thisNode;
                lastC = thisC;
            }
        }

        return i;
    }

}
