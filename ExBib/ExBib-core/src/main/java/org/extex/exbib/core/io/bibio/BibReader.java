/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.io.bibio;

import java.io.FileNotFoundException;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This interface describes a reader for bibliography files.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public interface BibReader extends Configurable {

    /**
     * Perform the loading into a database. The source given to the constructor
     * is used to fill a database.
     * 
     * @param db the database into which the loading should be performed
     * @param processor the processor context
     * 
     * @throws ExBibException in case of an error
     * @throws ConfigurationException in case of an configuration error
     */
    void load(DB db, Processor processor)
            throws ExBibException,
                ConfigurationException;

    /**
     * Open a file for reading
     * 
     * @param file the file to open for reading
     * 
     * @throws FileNotFoundException in case the file could not be opened for
     *         reading
     * @throws ConfigurationException in case that the configuration is invalid
     */
    void open(String file) throws ConfigurationException, FileNotFoundException;

}