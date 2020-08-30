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
     * Absorb all characters to a defined one.
     * 
     * @param term the last character
     * 
     * @return {@code true} iff the end has been found
     * 
     * @throws IOException in case of an error
     */
    public boolean scanTo(char term) throws IOException {

        for (int c = read(); c >= 0; c = read()) {
            if (c == term) {
                return true;
            }
        }
        return false;
    }

    /**
     * Scan the input up to a terminating character and collect the contents
     * encountered while scanning in a buffer.
     * 
     * @param term the terminating character
     * @param buffer the buffer to store the characters in
     * 
     * @return {@code true} iff the terminating character has been found
     * 
     * @throws IOException in case of an error
     */
    public boolean scanTo(char term, Appendable buffer) throws IOException {

        for (int c = read(); c >= 0; c = read()) {
            if (c == term) {
                return true;
            }
            buffer.append((char) c);
        }
        return false;
    }

    /**
     * Read to a certain XML tag.
     * 
     * @param tag the name of the tag
     * 
     * @return {@code true} iff the tag has been found
     * 
     * @throws IOException in case of an error
     */
    public boolean scanToTag(String tag) throws IOException {

        while (scanTo('<')) {
            StringBuilder b = new StringBuilder();
            if (!scanTo('>', b)) {
                break;
            }
            if (b.toString().equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Read to a certain XML tag and collect the characters found.
     * 
     * @param endtag the name of the end tag
     * @param buffer the buffer to collect the contents in
     * 
     * @return {@code true} iff the tag has been found
     * 
     * @throws IOException in case of an error
     */
    public boolean scanToTag(String endtag, Appendable buffer)
            throws IOException {

        while (scanTo('<', buffer)) {
            StringBuilder b = new StringBuilder();
            if (!scanTo('>', b)) {
                break;
            }
            if (b.toString().equalsIgnoreCase(endtag)) {
                return true;
            }
            buffer.append('<');
            buffer.append(b);
            buffer.append('>');
        }
        return false;
    }

}
