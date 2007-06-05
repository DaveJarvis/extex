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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
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
     * Class for a back-end character.
     */
    private class BackendCharacterImpl implements BackendCharacter {

        /**
         * The Unicode char.
         */
        private UnicodeChar uc;

        /**
         * Creates a new object.
         * 
         * @param uc the Unicode char.
         */
        public BackendCharacterImpl(UnicodeChar uc) {

            this.uc = uc;
        }

        /**
         * {@inheritDoc}
         * 
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
         * {@inheritDoc}
         * 
         * @see org.extex.font.BackendCharacter#getName()
         */
        public String getName() {

            return Integer.toString(getId());
        }

    }

    /**
     * Class for the info of used fonts and characters.
     */
    private class Info {

        /**
         * The List for the characters.
         */
        private List<BackendCharacter> backendCharacterList =
                new ArrayList<BackendCharacter>();

        /**
         * Add the char to the list.
         * 
         * @param ch The char to add.
         */
        public void add(BackendCharacter ch) {

            backendCharacterList.add(ch);
        }

        /**
         * The font key.
         */
        private FontKey fontKey;

        /**
         * Creates a new object.
         * 
         * @param fontKey The font key.
         */
        public Info(FontKey fontKey) {

            this.fontKey = fontKey;
        }

        /**
         * Getter for fontKey.
         * 
         * @return the fontKey
         */
        public FontKey getFontKey() {

            return fontKey;
        }

        /**
         * The backend font.
         */
        private BackendFont backendFont;

        /**
         * Getter for backendFont.
         * 
         * @return the backendFont
         */
        public BackendFont getBackendFont() {

            return backendFont;
        }

        /**
         * Setter for backendFont.
         * 
         * @param backendFont the backendFont to set
         */
        public void setBackendFont(BackendFont backendFont) {

            this.backendFont = backendFont;
        }

    }

    /**
     * The back-end font factory.
     */
    private BackendFontFactory factory;

    /**
     * The font list.
     */
    private SortedMap<FontKey, Info> fontList;

    /**
     * Is it a recognized font?
     */
    private boolean newRecongnizedFont;

    /**
     * The back-end character.
     */
    private BackendCharacter recognizedCharcterId;

    /**
     * The recognized font.
     */
    private BackendFont recognizedFont;

    /**
     * Creates a new object.
     */
    public TfmBackendFontManager() {

        super();
        fontList = new TreeMap<FontKey, Info>();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#getRecognizedCharId()
     */
    public BackendCharacter getRecognizedCharId() {

        return recognizedCharcterId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#getRecognizedFont()
     */
    public BackendFont getRecognizedFont() {

        return recognizedFont;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#isNewRecongnizedFont()
     */
    public boolean isNewRecongnizedFont() {

        return newRecongnizedFont;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#iterate()
     */
    public Iterator<BackendFont> iterate() {

        return new Iterator<BackendFont>() {

            /**
             * Iterator for the font key.
             */
            private Iterator<FontKey> it;

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Iterator#hasNext()
             */
            public boolean hasNext() {

                if (fontList != null && it == null) {
                    it = fontList.keySet().iterator();
                }
                if (it != null) {
                    return it.hasNext();
                }
                return false;
            }

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Iterator#next()
             */
            public BackendFont next() {

                if (fontList != null && it == null) {
                    it = fontList.keySet().iterator();
                }
                if (it == null) {
                    throw new NoSuchElementException();
                }
                FontKey key = it.next();
                return factory.getBackendFont(key);
            }

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Iterator#remove()
             */
            public void remove() {

                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#recognize(org.extex.font.FontKey,
     *      org.extex.core.UnicodeChar)
     */
    public boolean recognize(FontKey fontKey, UnicodeChar uc) {

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (uc == null) {
            throw new IllegalArgumentException("unicodechar");
        }

        Info info = fontList.get(fontKey);
        if (info != null) {
            newRecongnizedFont = false;
            recognizedFont = info.getBackendFont();
            recognizedCharcterId = new BackendCharacterImpl(uc);
            return true;
        }

        info = new Info(fontKey);
        newRecongnizedFont = true;
        recognizedFont = factory.getBackendFont(fontKey);
        if (recognizedFont == null) {
            return false;
        }
        info.setBackendFont(recognizedFont);
        recognizedCharcterId = new BackendCharacterImpl(uc);
        info.add(recognizedCharcterId);
        fontList.put(fontKey, info);

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#reset()
     */
    public void reset() {

        // TODO mgn: reset unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#setBackendFontFactory(org.extex.font.BackendFontFactory)
     */
    public void setBackendFontFactory(BackendFontFactory f) {

        this.factory = f;

    }

}
