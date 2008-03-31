/*
 * Copyright (C) 2003-2008 Gerd Neugebauer
 * This file is part of ExBib a BibTeX compatible database.
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

package org.extex.exbib.core.engine;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.extex.exbib.core.bst.Bibliography;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;

/**
 * This interface describes the interpreter engine for ExBib.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public interface Engine extends ResourceAware {

    /**
     * Getter for the file name.
     * 
     * @return the file name
     */
    public abstract String getFilename();

    /**
     * Parses an aux file and sends the result to a bibliography.
     * 
     * @param listener the bibliography to send the result to
     * 
     * @return an array of three numbers:
     *         <ul>
     *         <li>the number of data entries found</li>
     *         <li>the number of style entries found</li>
     *         <li>the number of citation entries found</li>
     *         </ul>
     * 
     * @throws IOException in case that the file could not be opened for reading
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public abstract int[] process(Bibliography listener)
            throws IOException,
                ConfigurationException;

    /**
     * Initialize the file to read from.
     * 
     * @param filename the name of the file to read from
     * 
     * @throws FileNotFoundException in case that the file could not be opened
     *         for reading
     * @throws ConfigurationException in case that the configuration is invalid
     */
    public abstract void setFilename(String filename)
            throws FileNotFoundException,
                ConfigurationException;

}
