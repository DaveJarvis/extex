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

package org.extex.unit.tex.macro.exceptions;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception signals that an outer primitive has been encountered in the
 * definition of a macro. This means either in the pattern or the replacement
 * text.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExtraRightBraceInDefException extends HelpingException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param primitive the mane of the primitive
     */
    public ExtraRightBraceInDefException(String primitive) {

        super(LocalizerFactory.getLocalizer(ExtraRightBraceInDefException.class),
            "TTP.ExtraRightBrace", primitive);
    }

}
