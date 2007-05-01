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

import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.HorizontalListMaker;
import org.extex.typesetter.listMaker.ListManager;

/**
 * This an abstract base class for primitives in horizontal mode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public abstract class AbstractHorizontalCode extends AbstractCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     * @param codeName the name of the code
     */
    public AbstractHorizontalCode(String codeName) {

        super(codeName);
    }

    /**
     * Check that the current mode is a horizontal mode and throw an exception
     * if another mode is detected.
     *
     * @param typesetter the typesetter to ask for the mode
     *
     * @throws HelpingException in case of an error
     */
    protected void ensureHorizontalMode(Typesetter typesetter)
            throws HelpingException {

        Mode mode = typesetter.getMode();
        if (mode == Mode.VERTICAL || mode == Mode.INNER_VERTICAL) {
            throw new HelpingException(LocalizerFactory
                    .getLocalizer(AbstractHorizontalCode.class),
                    "TTP.MissingInserted", "}");
        }

    }

    /**
     * Check that the current mode is a horizontal mode and open a new
     * list maker if another mode is detected.
     *
     * @param typesetter the typesetter to ask for the mode
     *
     * @throws InterpreterException in case of an error
     */
    protected void switchToHorizontalMode(Typesetter typesetter)
            throws InterpreterException {

        Mode mode = typesetter.getMode();
        if (mode == Mode.VERTICAL || mode == Mode.INNER_VERTICAL) {
            ListManager man = typesetter.getManager();
            ListMaker hlist = new HorizontalListMaker(man, typesetter
                    .getLocator());
            try {
                man.push(hlist);
            } catch (TypesetterException e) {
                throw new InterpreterException(e);
            }
        }
    }

}
