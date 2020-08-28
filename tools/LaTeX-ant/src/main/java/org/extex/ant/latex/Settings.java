/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.ant.latex;

import java.io.File;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public interface Settings {

    /**
     * The field <tt>BIBTEX_COMMAND</tt> contains the name of the property for
     * the bibtex command.
     */
    public static final String BIBTEX_COMMAND = "bibtex.command";

    /**
     * The field <tt>LATEX_COMMAND</tt> contains the name of the property for
     * the latex command.
     */
    public static final String LATEX_COMMAND = "latex.command";

    /**
     * The field <tt>MAKEINDEX_COMMAND</tt> contains the name of the property
     * for the makeindex command.
     */
    public static final String MAKEINDEX_COMMAND = "makeindex.command";

    /**
     * The field <tt>MAKEINDEX_COMMAND</tt> contains the name of the property
     * for the workng directory.
     */
    public static final String CWD = "latex.working.directory";

    /**
     * The field <tt>OUTPUT_FORMAT</tt> contains the name of the property for
     * the output format.
     */
    public static final String OUTPUT_FORMAT = "latex.output.format";

    /**
     * The field <tt>OUTPUT_DIRACTROY</tt> contains the name of the property for
     * the output directory.
     */
    public static final String OUTPUT_DIRECTORY = "latex.output.directory";

    /**
     * Getter for a property with a fall-back value.
     * 
     * @param key the key
     * @param fallback the fall-back value
     * 
     * @return the value or the fall-back value if none is found
     */
    String get(String key, String fallback);

    /**
     * Getter for the working directory.
     * 
     * @return the working directory
     */
    File getWorkingDirectory();

    /**
     * Log a message.
     * 
     * @param string the message
     */
    void log(String string);

}
