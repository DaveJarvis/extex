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

package org.extex.unit.tex.typesetter;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This an abstract base class for primitives in vertical mode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public abstract class AbstractVerticalCode extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Creates a new object.
     *
     * @param codeName the name of the code
     */
    public AbstractVerticalCode(String codeName) {

        super(codeName);
    }

    /**
     * Check that the current mode is a vertical mode and throw an exception
     * if another mode is detected.
     *
     * @param typesetter the typesetter to ask for the mode
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * @throws ConfigurationException in case of an configuration error
     */
    protected void ensureVerticalMode(Typesetter typesetter)
            throws HelpingException, TypesetterException, ConfigurationException {

        Mode mode = typesetter.getMode();
        if (typesetter.getMode() == Mode.HORIZONTAL) { // see TTP[1094]
            // see TTP[1095]
            typesetter.par();
            mode = typesetter.getMode();
        }

        if (!(mode == Mode.VERTICAL || mode == Mode.INNER_VERTICAL)) {
            throw new HelpingException(LocalizerFactory
                .getLocalizer(AbstractVerticalCode.class),
                "TTP.MissingInserted", "}");
        }
    }

}
