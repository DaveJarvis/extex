/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * This class is a buffered reader with some additional methods to support
 * paring.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ParsingReader extends BufferedReader {

    /**
     * Creates a new object.
     * 
     * @param in the source for characters
     */
    public ParsingReader(Reader in) {

        super(in);
    }

    /**
     * Scan the input up to a terminating character and collect the contents
     * encountered while scanning in a buffer.
     * 
     * @param term the terminating character
     * @param buffer the buffer to store the characters in
     * 
     * @return <code>true</code> iff the terminating character has been found
     * 
     * @throws IOException in case of an error
     */
    public boolean scanTo(char term, StringBuilder buffer) throws IOException {

        for (int c = read(); c >= 0; c = read()) {
            if (c == term) {
                return true;
            }
            buffer.append((char) c);
        }
        return false;
    }

    /**
     * Absorb all characters to a defined one.
     * 
     * @param term the last character
     * 
     * @return <code>true</code> iff the end has been found
     * 
     * @throws IOException in case of an error
     */
    public boolean skipTo(char term) throws IOException {

        for (int c = read(); c >= 0; c = read()) {
            if (c == term) {
                return true;
            }
        }
        return false;
    }
}
