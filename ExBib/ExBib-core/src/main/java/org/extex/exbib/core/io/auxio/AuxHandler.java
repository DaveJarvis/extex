/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.auxio;

import java.io.IOException;

import org.extex.exbib.core.ProcessorContainer;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This interface describes a handler for macros found in an aux file.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface AuxHandler {

    /**
     * Invoke the handler to do its job.
     * 
     * @param arg the macro parameter argument
     * @param bibliographies the bibliography
     * @param type the type of the bibliography
     * @param auxReader the engine
     * @throws IOException in case of an I/O error
     * @throws ConfigurationException in case of an configuration error
     * @throws ExBibException in case of an error
     */
    void invoke(String arg, ProcessorContainer bibliographies, String type,
            AuxReader auxReader)
            throws ConfigurationException,
                IOException,
                ExBibException;

}
