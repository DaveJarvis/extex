/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.typesetter.impl;

import de.dante.extex.i18n.HelpingException;
import de.dante.extex.i18n.Messages;
import de.dante.extex.i18n.PanicException;
import de.dante.extex.interpreter.type.count.Count;
import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.typesetter.ListMaker;
import de.dante.extex.typesetter.Mode;
import de.dante.extex.typesetter.type.noad.Noad;
import de.dante.util.GeneralException;
import de.dante.util.framework.i18n.Localizer;
import de.dante.util.framework.i18n.LocalizerFactory;

/**
 * This abstract class provides some methods common to all ListMakers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractListMaker implements ListMaker {

    /**
     * The field <tt>manager</tt> contains the manager to ask for global
     * changes.
     */
    private Manager manager;

    /**
     * Creates a new object.
     *
     * @param theManager the manager to ask for global changes
     */
    public AbstractListMaker(final Manager theManager) {

        super();
        this.manager = theManager;
    }

    /**
     * Getter for manager.
     *
     * @return the manager.
     */
    public Manager getManager() {

        return manager;
    }

    /**
     * @see de.dante.extex.typesetter.ListMaker#getMode()
     */
    public abstract Mode getMode();

    /**
     * @see de.dante.extex.typesetter.ListMaker#setSpacefactor(
     *      de.dante.extex.interpreter.type.count.Count)
     */
    public void setSpacefactor(final Count f) throws GeneralException {

        throw new HelpingException("TTP.ImproperSForPD", "spacefactor");
    }

    /**
     * @see de.dante.extex.typesetter.ListMaker#add(
     *      de.dante.extex.typesetter.type.noad.Noad)
     */
    public void add(final Noad noad) throws GeneralException {

        throw new PanicException("TTP.Confusion", //
                Messages.format("ListMaker.Noad.unexpected"));
    }

    /**
     * @see de.dante.extex.typesetter.ListMaker#toggleDisplaymath()
     */
    public void toggleDisplaymath() throws GeneralException {

        ListMaker mathList = new DisplaymathListMaker(manager);
        manager.push(mathList);
    }

    /**
     * @see de.dante.extex.typesetter.ListMaker#toggleMath()
     */
    public void toggleMath() throws GeneralException {

        ListMaker mathList = new MathListMaker(manager);
        manager.push(mathList);
    }

    /**
     * @see de.dante.extex.typesetter.ListMaker#setPrevDepth(
     *      de.dante.extex.interpreter.type.dimen.Dimen)
     */
    public void setPrevDepth(final Dimen pd) throws GeneralException {

        throw new HelpingException("TTP.ImproperSForPD", "prevdepth");
    }

    /**
     * ...
     *
     * @return ...
     */
    protected Localizer getLocalizer() {
        
        return LocalizerFactory.getLocalizer(this.getClass().getName());
    }
}