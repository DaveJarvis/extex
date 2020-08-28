/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import org.extex.font.BackendCharacter;
import org.extex.font.BackendFont;
import org.extex.font.BackendFontFactory;
import org.extex.font.BackendFontManager;
import org.extex.font.FontKey;

/**
 * Abstract Backend font manager.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractBackendFontManager implements BackendFontManager {

    /**
     * The back-end font factory.
     */
    protected BackendFontFactory factory;

    /**
     * The font list.
     */
    protected Map<FontKey, ManagerInfo> fontList;

    /**
     * Is it a recognized font?
     */
    protected boolean newRecongnizedFont;

    /**
     * The back-end character.
     */
    protected BackendCharacter recognizedCharcterId;

    /**
     * The recognized font.
     */
    protected BackendFont recognizedFont;


    public AbstractBackendFontManager() {

        fontList = new HashMap<FontKey, ManagerInfo>();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#getRecognizedCharId()
     */
    @Override
    public BackendCharacter getRecognizedCharId() {

        return recognizedCharcterId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#getRecognizedFont()
     */
    @Override
    public BackendFont getRecognizedFont() {

        return recognizedFont;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#isNewRecongnizedFont()
     */
    @Override
    public boolean isNewRecongnizedFont() {

        return newRecongnizedFont;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#iterator()
     */
    @Override
    public Iterator<ManagerInfo> iterator() {

        return new Iterator<ManagerInfo>() {

            /**
             * Iterator for the font key.
             */
            private Iterator<FontKey> it;

            /**
             * Create the <code>Iterator</code> and sort it.
             */
            private void createIterator() {

                Set<FontKey> keySet = fontList.keySet();
                TreeSet<FontKey> sort =
                        new TreeSet<FontKey>(new Comparator<FontKey>() {

                            @Override
                            public int compare(FontKey o1, FontKey o2) {

                                return o1.getName().compareTo(o2.getName());
                            }

                        });
                for (FontKey key : keySet) {
                    sort.add(key);
                }

                it = sort.iterator();
            }

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Iterator#hasNext()
             */
            @Override
            public boolean hasNext() {

                if (fontList != null && it == null) {
                    createIterator();
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
            @Override
            public ManagerInfo next() {

                if (fontList != null && it == null) {
                    createIterator();
                }
                if (it == null) {
                    throw new NoSuchElementException();
                }
                FontKey key = it.next();
                return fontList.get(key);
            }

            /**
             * {@inheritDoc}
             * 
             * @see java.util.Iterator#remove()
             */
            @Override
            public void remove() {

                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#reset()
     */
    @Override
    public void reset() {

        newRecongnizedFont = false;
        recognizedCharcterId = null;
        recognizedFont = null;
        fontList.clear();

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFontManager#setBackendFontFactory(org.extex.font.BackendFontFactory)
     */
    @Override
    public void setBackendFontFactory(BackendFontFactory f) {

        this.factory = f;

    }
}
