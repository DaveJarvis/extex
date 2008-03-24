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

package org.extex.exbib.core.io;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * A <code>Locator</code> is a pointer to a certain line and position in a
 * file. This class can be used to store the position to be used in an error
 * message. The position should point to something meaningful for the user and
 * not merely for internal debugging.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class Locator {

    /**
     * The field <tt>file</tt> contains the name of the file where the Locator
     * points to.
     */
    private String file;

    /**
     * The field <tt>line</tt> contains the number of the line in the file.
     */
    private int line;

    /**
     * The field <tt>position</tt> contains the position, i.e. Column, in the
     * line.
     */
    private int position;

    /**
     * Creates a new Locator object. The position is set to 0.
     * 
     * @param file the name of the file
     * @param line the number of the line
     */
    public Locator(String file, int line) {

        super();
        this.file = file;
        this.line = line;
        this.position = 0;
    }

    /**
     * Creates a new Locator object.
     * 
     * @param file the name of the file
     * @param line the number of the line
     * @param column the position, i.e. column
     */
    public Locator(String file, int line, int column) {

        super();
        this.file = file;
        this.line = line;
        this.position = column;
    }

    /**
     * Getter for the file name
     * 
     * @return the file name
     */
    public String getFile() {

        return file;
    }

    /**
     * Getter for the line number
     * 
     * @return the line number
     */
    public int getLine() {

        return line;
    }

    /**
     * Getter for the position.
     * 
     * @return the position
     */
    public int getPosition() {

        return position;
    }

    /**
     * Conversion to a readable form. The exact text is formatted using the
     * format <tt>Message</tt> with the line number as argument 0 and the file
     * as argument 1.
     * 
     * @return the readable form of this object.
     */
    @Override
    public String toString() {

        return LocalizerFactory.getLocalizer(getClass()).format("Message",
            Integer.toString(getLine()), getFile());
    }
}
