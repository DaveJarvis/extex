/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.io.bstio;

import java.io.FileNotFoundException;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceAware;

/**
 * This interface describes a reader for BST files.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface BstReader extends ResourceAware {

    /**
     * Parse the bibliography style represented by this instance.
     * 
     * @param processor the processor context
     * 
     * @throws ExBibException an exception of some kind is thrown upon errors
     * @throws ExBibBstNotFoundException if <i>file</i> can not be opened
     * @throws ConfigurationException in case that the reading apparatus detects
     *         a misconfiguration
     */
    void parse(BstProcessor processor)
            throws ExBibException,
                ExBibBstNotFoundException,
                ConfigurationException;

    /**
     * This parsing routine takes a single file argument and parses its
     * contents.
     * 
     * @param processor the processor context
     * @param file the name of the file to parse
     * 
     * @throws FileNotFoundException in case that the requested file could not
     *         be opened for reading
     * @throws ConfigurationException in case that the reading apparatus detects
     *         a misconfiguration
     * @throws ExBibException in case of an syntax error
     */
    void parse(BstProcessor processor, String file)
            throws ExBibException,
                FileNotFoundException,
                ConfigurationException;

    /**
     * The parser can be asked to save the comments found in a buffer. If the
     * buffer is <code>null</code> then the comments are discarded. This is the
     * default behavior.
     * 
     * @param save the buffer for the comment
     */
    void setSaveComment(StringBuilder save);

}
