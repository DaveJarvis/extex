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
import org.extex.font.exception.FontException;

/**
 * A list of back-end managers.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class BackendFontManagerList implements BackendFontManager {

    /**
     * The back-end font factory.
     */
    private BackendFontFactory factory;

    /**
     * Map the fonts to the managers.
     */
    private Map<FontKey, BackendFontManager> font2managers;

    /**
     * The list for the managers.
     */
    private List<BackendFontManager> managers;

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
     * Add a new back-end font manager.
     * 
     * @param manager the new manager.
     */
    public void add(BackendFontManager manager) {

        if (manager == null) {
            throw new IllegalArgumentException("manager");
        }
        managers.add(manager);
        manager.setBackendFontFactory(factory);
    }

    /**
     * Returns the {@link BackendFontManager}.
     * 
     * @param index The index.
     * @return Returns the {@link BackendFontManager}.
     * @see java.util.List#get(int)
     */
    public BackendFontManager get(int index) {

        return managers.get(index);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#getRecognizedCharId()
     */
    public BackendCharacter getRecognizedCharId() {

        if (recognizedManager == null) {
            throw new IllegalStateException("recognizedManager");
        }
        return recognizedManager.getRecognizedCharId();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#getRecognizedFont()
     */
    public BackendFont getRecognizedFont() {

        if (recognizedManager == null) {
            throw new IllegalStateException("recognizedManager");
        }
        return recognizedManager.getRecognizedFont();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#isNewRecongnizedFont()
     */
    public boolean isNewRecongnizedFont() {

        if (recognizedManager == null) {
            throw new IllegalStateException("recognizedManager");
        }
        return recognizedManager.isNewRecongnizedFont();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#iterate()
     */
    public Iterator<ManagerInfo> iterate() {

        return new Iterator<ManagerInfo>() {

            /**
             * The index. -1 : not started yet >=0 : working -2 : ended
             */
            private int index = -1;

            /**
             * The iterator.
             */
            private Iterator<ManagerInfo> iterator = null;

            /**
             * @see java.util.Iterator#hasNext()
             */
            public boolean hasNext() {

                switch (index) {
                    case -1:
                        // ignore
                        break;
                    case -2:
                        return false;

                    default:
                        if (iterator.hasNext()) {
                            return true;
                        }

                        break;
                }

                while (++index < managers.size()) {
                    iterator = managers.get(index).iterate();
                    if (iterator.hasNext()) {
                        return true;
                    }
                }

                iterator = null;
                index = -2;
                return false;
            }

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Iterator#next()
             */
            public ManagerInfo next() {

                return iterator.next();
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
     * TODO missing JavaDoc
     * 
     * @return TODO
     */
    public Iterator<ManagerInfo> iterateManagerInfo() {

        return new Iterator<ManagerInfo>() {

            /**
             * The index. -1 : not started yet >=0 : working -2 : ended
             */
            private int index = -1;

            /**
             * The iterator.
             */
            private Iterator<ManagerInfo> iterator = null;

            /**
             * @see java.util.Iterator#hasNext()
             */
            public boolean hasNext() {

                switch (index) {
                    case -1:
                        // ignore
                        break;
                    case -2:
                        return false;

                    default:
                        if (iterator.hasNext()) {
                            return true;
                        }

                        break;
                }

                while (++index < managers.size()) {
                    iterator = managers.get(index).iterate();
                    if (iterator.hasNext()) {
                        return true;
                    }
                }

                iterator = null;
                index = -2;
                return false;
            }

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Iterator#next()
             */
            public ManagerInfo next() {

                return iterator.next();
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
    public boolean recognize(FontKey fontKey, UnicodeChar uc)
            throws FontException {

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (uc == null) {
            throw new IllegalArgumentException("unicodechar");
        }
        recognizedManager = font2managers.get(fontKey);
        if (recognizedManager == null) {
            for (BackendFontManager fm : managers) {
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

        font2managers = new HashMap<FontKey, BackendFontManager>();
        managers = new ArrayList<BackendFontManager>();
        recognizedManager = null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#setBackendFontFactory(org.extex.font.BackendFontFactory)
     */
    public void setBackendFontFactory(BackendFontFactory f) {

        this.factory = f;

        for (BackendFontManager fm : managers) {
            fm.setBackendFontFactory(f);
        }
    }

    /**
     * Returns the size of the manager list.
     * 
     * @return Returns the size of the manager list.
     * @see java.util.List#size()
     */
    public int size() {

        return managers.size();
    }
}
