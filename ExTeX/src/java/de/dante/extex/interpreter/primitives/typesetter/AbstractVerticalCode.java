/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.typesetter;

import de.dante.extex.interpreter.exception.InterpreterException;
import de.dante.extex.interpreter.exception.helping.HelpingException;
import de.dante.extex.interpreter.type.AbstractCode;
import de.dante.extex.typesetter.Mode;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.framework.configuration.exception.ConfigurationException;
import de.dante.util.framework.i18n.LocalizerFactory;

/**
 * This an abstract base class for primitives in vertical mode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
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
    public AbstractVerticalCode(final String codeName) {

        super(codeName);
    }

    /**
     * Check that the current mode is a vertical mode and throw an exception
     * if another mode is detected.
     *
     * @param typesetter the typesetter to ask for the mode
     *
     * @throws InterpreterException in case of an error
     */
    protected void ensureVerticalMode(final Typesetter typesetter)
            throws InterpreterException {

        Mode mode = typesetter.getMode();
        if (typesetter.getMode() == Mode.HORIZONTAL) { // see TTP[1094]
            try {
                // see TTP[1095]
                typesetter.par();
            } catch (ConfigurationException e) {
                throw new InterpreterException(e);
            }
            mode = typesetter.getMode();
        }

        if (!(mode == Mode.VERTICAL || mode == Mode.INNER_VERTICAL)) {
            throw new HelpingException(LocalizerFactory
                    .getLocalizer(AbstractVerticalCode.class),
                    "TTP.MissingInserted", "}");
        }
    }

}
