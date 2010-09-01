/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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
import org.extex.resource.ResourceAware;

/**
 * This interface describes the reader for aux files in &epsilon;&chi;Bib. The
 * responsibility of the reader is to parse the aux file and store the
 * information in a {@link ProcessorContainer}.
 * <p>
 * Some of the internal processing can be observed by registering an observer.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface AuxReader extends ResourceAware {

    /**
     * Getter for the file name.
     * 
     * @return the file name
     */
    String getFilename();

    /**
     * Parses an aux file and sends the result to a bibliography.
     * 
     * @param bibliographies the bibliography to send the result to
     * @param resource the resource to be processed
     * @param encoding the encoding for reading
     * 
     * @throws IOException in case that the file could not be opened for reading
     * @throws ConfigurationException in case that the configuration is invalid
     * @throws ExBibException in case of an error
     */
    void load(ProcessorContainer bibliographies, String resource,
            String encoding)
            throws IOException,
                ConfigurationException,
                ExBibException;

    /**
     * Register an observer for resource open events. The old observer is
     * overwritten.
     * 
     * @param observer the observer or <code>null</code> for none
     * 
     * @return the old observer or <code>null</code> for none
     */
    ResourceObserver register(ResourceObserver observer);

    /**
     * Register a handler for a macro in the aux file.
     * 
     * @param name the name
     * @param handler the handler
     * 
     * @return the old handler or <code>null</code> for none
     */
    AuxHandler register(String name, AuxHandler handler);

}
