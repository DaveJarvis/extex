/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

import java.io.InputStream;
import java.util.logging.Logger;

import org.extex.scanner.stream.observer.file.OpenFileObserver;

/**
 * This observer reports that a certain file has been opened.
 * According to the behavior of <logo>TeX</logo> it logs an open brace and the
 * name of the file.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4708 $
 */
public class FileOpenObserver implements OpenFileObserver {

    /**
     * The field <tt>logger</tt> contains the current logger
     */
    private Logger logger;

    /**
     * Creates a new object.
     *
     * @param theLogger the logger to use
     */
    public FileOpenObserver(Logger theLogger) {

        super();
        this.logger = theLogger;
    }

    /**
     * This method is meant to be invoked just after a new file based stream has
     * been opened.
     *
     * @param filename the name of the file to be opened
     * @param filetype the type of the file to be opened. The type is resolved
     *  via the configuration to a file name pattern
     * @param stream the input stream to read from
     *
     * @see org.extex.scanner.stream.observer.file.OpenFileObserver#update(
     *      java.lang.String,
     *      java.lang.String,
     *      java.io.InputStream)
     */
    public void update(String filename, String filetype,
            InputStream stream) {

        logger.info("(" + filename + " ");
    }

}
