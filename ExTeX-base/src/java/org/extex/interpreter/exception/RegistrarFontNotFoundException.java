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

package org.extex.interpreter.exception;

import org.extex.font.FontKey;
import org.extex.util.framework.RegistrarException;
import org.extex.util.framework.i18n.LocalizerFactory;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class RegistrarFontNotFoundException extends RegistrarException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 24012007L;

    /**
     * The field <tt>key</tt> contains the font key which caused the problem.
     */
    private FontKey key;

    /**
     * Creates a new object.
     *
     */
    public RegistrarFontNotFoundException(final FontKey key) {

        this.key = key;
    }

    /**
     * Getter for key.
     *
     * @return the key
     */
    public FontKey getKey() {

        return key;
    }

    /**
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage() {

        return LocalizerFactory.getLocalizer(
            RegistrarFontNotFoundException.class).format("FontNotFound",
            key.getName());
    }

}
