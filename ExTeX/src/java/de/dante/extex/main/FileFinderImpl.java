/*
 * Copyright (C) 2003  Gerd Neugebauer
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
package de.dante.extex.main;

import de.dante.util.configuration.ConfigurationException;
import de.dante.util.file.FileFinder;
import de.dante.extex.i18n.Messages;
import de.dante.extex.logging.Logger;

import java.io.File;
import java.io.IOException;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FileFinderImpl implements FileFinder {

    /** ... */
    private Logger logger;

    /**
     * Creates a new object.
     */
    public FileFinderImpl(Logger logger) {
        super();
        this.logger = logger;
    }

    /**
     * @see de.dante.util.file.FileFinder#findFile(java.lang.String, java.lang.String)
     */
    public File findFile(String name, String type)
                  throws ConfigurationException {
        File file = null;

        try {
            while (file == null) {
                if (!name.equals("")) {
                    logger.severe("\n! "+Messages.format("CLI.FileNotFound",
                                                  name));
                }

                logger.severe(Messages.format("CLI.PromptFile"));
                name = readLine();

                if (name.equals("")) {
                } else if (name.charAt(0) == '\\') {
                    //TODO make use of the line read
                    return null;
                }
            }
        } catch (IOException e) {
            //TODO incomplete
            file = null;
        }

        return file;
    }

    /**
     * ...
     *
     * @return the line read
     *
     * @throws IOException ...
     */
    private String readLine() throws IOException {
        StringBuffer sb = new StringBuffer();
        int c;

        while ((c = System.in.read()) >= 0 && c == ' ') {
        }

        if (c >= 0 && c != '\n') {
            do {
                sb.append((char) c);
            } while ((c = System.in.read()) >= 0 && c != '\n');
        }

        return sb.toString();
    }
}
