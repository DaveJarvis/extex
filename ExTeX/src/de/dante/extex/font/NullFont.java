/*
 * Copyright (C) 2003-2004  Gerd Neugebauer, Michael Niedermair
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
package de.dante.extex.font;

import de.dante.extex.interpreter.type.Dimen;
import de.dante.extex.interpreter.type.Font;
import de.dante.extex.interpreter.type.Glue;
import de.dante.util.UnicodeChar;

/**
 * This class implements a dummy font which does not contain any characters.
 *
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NullFont implements Font {

    /**
     * Creates a new object.
     */
    public NullFont() {
        super();
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getSpace()
     */
    public Glue getSpace() {
        return new Glue(12 * Dimen.ONE);
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getEm()
     */
    public Dimen getEm() {
        return new Dimen(12 * Dimen.ONE);
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getEx()
     */
    public Dimen getEx() {
        return new Dimen(6 * Dimen.ONE);
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getFontDimen(long)
     */
    public Dimen getFontDimen(final long index) {
        return null;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getFontName()
     */
    public String getFontName() {
        return "dummy";
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getDepth(de.dante.util.UnicodeChar)
     */
    public Dimen getDepth(final UnicodeChar c) {
        return null;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getHeight(de.dante.util.UnicodeChar)
     */
    public Dimen getHeight(final UnicodeChar c) {
        return null;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#getWidth(de.dante.util.UnicodeChar)
     */
    public Dimen getWidth(final UnicodeChar c) {
        return null;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#isDefined(de.dante.util.UnicodeChar)
     */
    public boolean isDefined(final UnicodeChar c) {
        return false;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#kern(de.dante.util.UnicodeChar,
     *      de.dante.util.UnicodeChar)
     */
    public Dimen kern(final UnicodeChar c1, final UnicodeChar c2) {
        return null;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#ligature(de.dante.util.UnicodeChar,
     *      de.dante.util.UnicodeChar)
     */
    public String ligature(final UnicodeChar c1, final UnicodeChar c2) {
        return null;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#externalFileName()
     */
    public String externalFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see de.dante.extex.interpreter.type.Font#isExternalFont()
     */
    public boolean isExternalFont() {
        // TODO Auto-generated method stub
        return false;
    }

}
