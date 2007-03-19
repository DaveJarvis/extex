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

package org.extex.font.manager;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.font.BackendCharacter;
import org.extex.font.BackendFont;
import org.extex.font.BackendFontFactory;
import org.extex.font.BackendFontManager;
import org.extex.font.FontKey;

/**
 * Backend font manager for a tfm font.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class TfmBackendFontManager implements BackendFontManager {

    /**
     * The font list.
     */
    private SortedMap fontList;

    /**
     * The backend character.
     */
    private BackendCharacter recognizedCharcterId;

    /**
     * The recognized font.
     */
    private BackendFont recognizedFont;

    /**
     * Is it a recognized font?
     */
    private boolean newRecongnizedFont;

    /**
     * Creates a new object.
     */
    public TfmBackendFontManager() {

        super();
        fontList = new TreeMap();
    }

    /**
     * @see org.extex.font.BackendFontManager#getRecognizedCharId()
     */
    public BackendCharacter getRecognizedCharId() {

        return recognizedCharcterId;
    }

    /**
     * @see org.extex.font.BackendFontManager#getRecognizedFont()
     */
    public BackendFont getRecognizedFont() {

        return recognizedFont;
    }

    /**
     * @see org.extex.font.BackendFontManager#isNewRecongnizedFont()
     */
    public boolean isNewRecongnizedFont() {

        return newRecongnizedFont;
    }

    /**
     * @see org.extex.font.BackendFontManager#iterate()
     */
    public Iterator iterate() {

        // TODO mgn: iterate unimplemented
        return null;
    }

    /**
     * @see org.extex.font.BackendFontManager#recognize(org.extex.font.FontKey, org.extex.core.UnicodeChar)
     */
    public boolean recognize(final FontKey fontKey, final UnicodeChar uc) {

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (uc == null) {
            throw new IllegalArgumentException("unicodechar");
        }
        Info info = (Info) fontList.get(fontKey);

        if (info != null) {
            newRecongnizedFont = false;
            recognizedFont = info.getFont();
            recognizedCharcterId = new BackendCharacterImpl(uc);
            return true;
        }
        newRecongnizedFont = true;

        // TODO mgn: recognize unimplemented
        return false;
    }

    /**
     * Class for a backend character.
     */
    private class BackendCharacterImpl implements BackendCharacter {

        /**
         * The Unicode char.
         */
        private UnicodeChar uc;

        /**
         * Creates a new object.
         *
         * @param uc    the Unicode char.
         */
        public BackendCharacterImpl(final UnicodeChar uc) {

            this.uc = uc;
        }

        /**
         * @see org.extex.font.BackendCharacter#getId()
         */
        public int getId() {

            int cp = uc.getCodePoint();

            if (cp >= Unicode.OFFSET && cp <= Unicode.OFFSET + 0xff) {
                return cp - Unicode.OFFSET;
            }

            if (cp <= 255) {
                // TODO ...
                return cp;
            }
            return 0;
        }

        /**
         * @see org.extex.font.BackendCharacter#getName()
         */
        public String getName() {

            return Integer.toString(getId());
        }

    }

    /**
     * TODO missing JavaDoc.
     */
    private class Info {

        /**
         * Returns the backend font.
         *
         * @return the backend font.
         */
        public BackendFont getFont() {

            // TODO mgn: getFont unimplemented
            return null;
        }

    }

    /**
     * @see org.extex.font.BackendFontManager#reset()
     */
    public void reset() {

        // TODO mgn: reset unimplemented

    }

    /**
     * The backend font factory.
     */
    private BackendFontFactory factory;

    /**
     * @see org.extex.font.BackendFontManager#setBackendFontFactory(org.extex.font.BackendFontFactory)
     */
    public void setBackendFontFactory(final BackendFontFactory factory) {

        this.factory = factory;

    }

}
