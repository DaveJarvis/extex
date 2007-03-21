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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.font.BackendCharacter;
import org.extex.font.BackendFont;
import org.extex.font.BackendFontFactory;
import org.extex.font.BackendFontManager;
import org.extex.font.FontKey;

/**
 * A list of backend managers.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class BackendFontManagerList implements BackendFontManager {

    /**
     * Map the fonts to the managers.
     */
    private Map font2managers;

    /**
     * The list for the managers.
     */
    private List managers;

    /**
     * Recognized font manager.
     */
    private BackendFontManager recognizedManager = null;

    /**
     * Creates a new object.
     */
    public BackendFontManagerList() {

        super();
        reset();
    }

    /**
     * Add a new backend font manager.
     *
     * @param manager the new manager.
     */
    public void add(final BackendFontManager manager) {

        if (manager == null) {
            throw new IllegalArgumentException("manager");
        }
        managers.add(manager);
        manager.setBackendFontFactory(factory);
    }

    /**
     * @see java.util.List#get(int)
     */
    public BackendFontManager get(final int index) {

        return (BackendFontManager) managers.get(index);
    }

    /**
     * @see org.extex.font.BackendFontManager#getRecognizedCharId()
     */
    public BackendCharacter getRecognizedCharId() {

        if (recognizedManager == null) {
            throw new IllegalStateException("recognizedManager");
        }
        return recognizedManager.getRecognizedCharId();
    }

    /**
     * @see org.extex.font.BackendFontManager#getRecognizedFont()
     */
    public BackendFont getRecognizedFont() {

        if (recognizedManager == null) {
            throw new IllegalStateException("recognizedManager");
        }
        return recognizedManager.getRecognizedFont();
    }

    /**
     * @see org.extex.font.BackendFontManager#isNewRecongnizedFont()
     */
    public boolean isNewRecongnizedFont() {

        if (recognizedManager == null) {
            throw new IllegalStateException("recognizedManager");
        }
        return recognizedManager.isNewRecongnizedFont();
    }

    /**
     * @see org.extex.font.BackendFontManager#iterate()
     */
    public Iterator iterate() {

        return new Iterator() {

            /**
             * The index.
             * -1  : not started yet
             * >=0 : working
             * -2  : ended
             */
            private int index = -1;

            /**
             * The iterator.
             */
            private Iterator iterator = null;

            /**
             * @see java.util.Iterator#hasNext()
             */
            public boolean hasNext() {

                switch (index) {
                    case -1 :
                        // ignore
                        break;
                    case -2 :
                        return false;

                    default :
                        if (iterator.hasNext()) {
                            return true;
                        }

                        break;
                }

                while (++index < managers.size()) {
                    iterator = ((BackendFontManager) managers.get(index))
                            .iterate();
                    if (iterator.hasNext()) {
                        return true;
                    }
                }

                iterator = null;
                index = -2;
                return false;
            }

            /**
             * @see java.util.Iterator#next()
             */
            public Object next() {

                return iterator.next();
            }

            /**
             * @see java.util.Iterator#remove()
             */
            public void remove() {

                throw new UnsupportedOperationException();

            }

        };
    }

    /**
     * @see org.extex.font.BackendFontManager#recognize(org.extex.font.FontKey,
     *      org.extex.core.UnicodeChar)
     */
    public boolean recognize(final FontKey fontKey, final UnicodeChar uc) {

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (uc == null) {
            throw new IllegalArgumentException("unicodechar");
        }
        recognizedManager = (BackendFontManager) font2managers.get(fontKey);
        if (recognizedManager == null) {
            for (int i = 0, n = managers.size(); i < n; i++) {
                BackendFontManager fm = (BackendFontManager) managers.get(i);
                if (fm.recognize(fontKey, uc)) {
                    recognizedManager = fm;
                    return true;
                }
            }
            return false;
        }
        return recognizedManager.recognize(fontKey, uc);
    }

    /**
     * @see org.extex.font.BackendFontManager#reset()
     */
    public void reset() {

        font2managers = new HashMap();
        managers = new ArrayList();
        recognizedManager = null;
    }

    /**
     * {@inheritDoc}
     * @see java.util.List#size()
     */
    public int size() {

        return managers.size();
    }

    /**
     * The backend font factory.
     */
    private BackendFontFactory factory;

    /**
     * {@inheritDoc}
     * @see org.extex.font.BackendFontManager#setBackendFontFactory(org.extex.font.BackendFontFactory)
     */
    public void setBackendFontFactory(final BackendFontFactory factory) {

        this.factory = factory;

        for (int i = 0, n = managers.size(); i < n; i++) {
            BackendFontManager fm = (BackendFontManager) managers.get(i);
            fm.setBackendFontFactory(factory);
        }
    }

}
