/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.impl;

import java.io.IOException;
import java.util.logging.Logger;

import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.exception.ScannerException;

/**
 * This LaTeX parser pre-loads some common packages to get things started.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LaTeXParserImpl extends EmptyLaTeXParser {

    /**
     * Creates a new object.
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error while scanning
     */
    public LaTeXParserImpl() throws IOException, ScannerException {

        load("TeX");
        load("plain");
        load("latex/LaTeX2e");
    }

    /**
     * Creates a new object.
     * 
     * @param finder the resource finder
     * @param logger the logger
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error while scanning
     */
    public LaTeXParserImpl(ResourceFinder finder, Logger logger)
            throws IOException,
                ScannerException {

        this();
        setResourceFinder(finder);
        setLogger(logger);
    }

}
