/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font;

import java.util.Iterator;

import org.extex.core.UnicodeChar;

/**
 * The back-end font manager describes the abilities needed by a back-end to
 * deal with fonts. The procedure assumes that in a first pass all characters of
 * all fonts on a page or in a range of pages are passed to the manager. The
 * manager returns a representation appropriate for the back-end format.
 *
 * <p>
 *  In a second pass the manager can be asked to return all fonts. Those fonts
 *  can be embedded into the output file &ndash; either completely or simply as
 *  a list of font names.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface BackendFontManager {

    /**
     * Take a character and a font and see if they can be managed by this
     * manager. The internal state is adjusted to report the font and the
     * character appropriately after the invocation has returned.
     *
     * @param fontKey the font key for the character at hand
     * @param uc the Unicode character at hand
     *
     * @return <code>true</code> iff the character is defined in the font given
     *  and a font of a requested format can be delivered.
     */
    boolean recognize(FontKey fontKey, UnicodeChar uc);

    /**
     * Get the font info for the most recently recognized pair of font and
     * character. If invoked before any character has been recognized
     * successfully then <code>null</code> is returned.
     *
     * @return the font info for the most recently recognized font/character
     *  pair
     */
    BackendFont getRecognizedFont();

    /**
     * Get the char info for the most recently recognized pair of font and
     * character. If invoked before any character has been recognized
     * successfully then <code>null</code> is returned.
     *
     * @return the char info for the most recently recognized font/character
     *  pair
     */
    BackendCharacter getRecognizedCharId();

    /**
     * Get the new font indicator for the most recently recognized pair of font
     * and character. If the font has never been recognized before then
     * <code>true</code> is returned. The font compared for this test is the
     * back-end font &ndash; not the font passed in. This means that each time
     * getRecognizedFont() returns a new font info this method returns
     * <code>true</code>.
     * <p>
     *  If invoked before any character has been recognized
     *  successfully then <code>null</code> is returned.
     * </p>
     *
     * @return the font info for the most recently recognized font/character
     *  pair
     */
    boolean isNewRecongnizedFont();

    /**
     * Return the iterator for all recognized back-end fonts.
     *
     * @return the iterator for all recognized back-end font
     */
    Iterator iterate();

    /**
     * Rest the internal state to the initial state. Afterwards the instance
     * does not know anything about recognized fonts and can be used like a
     * newly created instance.
     */
    void reset();

    /**
     * Setter for the back-end font factory.
     *
     * @param factory the back-end font factory to set
     */
    void setBackendFontFactory(BackendFontFactory factory);
}
