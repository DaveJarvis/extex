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

package org.extex.main.tex;

import java.io.PrintStream;

import org.extex.core.Locator;
import org.extex.framework.i18n.Localizer;
import org.extex.interpreter.EditHandler;

/**
 * This is a dummy implementation for an EditHandler which just prints the
 * location to the error stream.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4445 $
 */
public class EditHandlerTeXImpl implements EditHandler {

    /**
     * Creates a new object.
     */
    public EditHandlerTeXImpl() {

        super();
    }

    /**
     * Edit a file at a given location.
     * 
     * @param localizer the localizer to acquire texts from
     * @param locator the locator for the place to edit
     * 
     * @return <code>true</code> iff the job can be continued
     * 
     * @see org.extex.interpreter.EditHandler#edit(
     *      org.extex.framework.i18n.Localizer, org.extex.core.Locator)
     */
    public boolean edit(Localizer localizer, Locator locator) {

        PrintStream stream = System.err;
        stream.println(localizer.format("EditHandler.edit", locator
            .getResourceName(), Integer.toString(locator.getLineNumber())));
        return false;
    }

}
