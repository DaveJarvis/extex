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

package org.extex.font.exception;

import org.extex.font.FontKey;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * Font exception if problems occurred during reading the font file.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class CorruptFontException extends FontException {

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The font key.
     */
    private final FontKey key;

    /**
     * Creates a new object.
     * 
     * @param key the font key.
     * @param msg the message.
     */
    public CorruptFontException(FontKey key, String msg) {

        super(msg);
        this.key = key;
    }

@Override
    public String getLocalizedMessage() {

        Localizer localizer = LocalizerFactory.getLocalizer(this.getClass());
        return localizer.format("Message", key.toString(), getMessage());
    }

}
