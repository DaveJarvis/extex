/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import org.extex.core.UnicodeChar;
import org.extex.interpreter.type.font.Font;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;

/**
 * This class provides an implementation for a ligature builder.
 * Nothing is inserted. This is just a noop.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
 */
public class EmptyLigatureBuilder implements LigatureBuilder {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     */
    public EmptyLigatureBuilder() {

        super();
    }

    /**
     * @see org.extex.language.ligature.LigatureBuilder#insertLigatures(
     *      org.extex.typesetter.type.NodeList, int)
     */
    public int insertLigatures(final NodeList list, final int start)
            throws HyphenationException {

        int i = start;
        while (i < list.size() && !(list.get(i) instanceof CharNode)) {
            i++;
        }
        Font font = ((CharNode) list.get(i)).getTypesettingContext().getFont();

        while (i < list.size()
                && (list.get(i) instanceof CharNode)
                && ((CharNode) list.get(i)).getTypesettingContext().getFont() == font) {
            i++;
        }
        return i;
    }

    /**
     * @see org.extex.language.ligature.LigatureBuilder#getLigature(
     *      org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar,
     *      org.extex.interpreter.type.font.Font)
     */
    public UnicodeChar getLigature(final UnicodeChar c1, final UnicodeChar c2,
            final Font f) {

        return null;
    }

}
