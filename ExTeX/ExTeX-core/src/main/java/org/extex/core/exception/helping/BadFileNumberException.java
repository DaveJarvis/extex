/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.core.exception.helping;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception is raised when a illegal file reference has been encountered.
 * <p>
 *  The localization format is taken from the Localizer under the key
 *  {@code TTP.BadFileNumber}.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BadFileNumberException extends HelpingException {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Creates a new object.
     *
     * @param value the illegal file reference
     * @param min the minimum for numerical values
     * @param max the maximum for numerical values
     */
    public BadFileNumberException(String value, String min,
            String max) {

        super(LocalizerFactory.getLocalizer(BadFileNumberException.class),
                "TTP.BadFileNumber", value, min, max);
    }

}
