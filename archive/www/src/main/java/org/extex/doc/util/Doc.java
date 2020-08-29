/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.doc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Doc {

    /**
     * The field <tt>ARGUMENT_PATTERN</tt> contains the ...
     */
    private static final Pattern ARGUMENT_PATTERN =
            Pattern.compile("\\W*([a-zA-Z_][a-zA-Z0-9_]*)=\"([^\"]*)\"(.*)");

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args ...
     * @param sb ...
     * 
     * @return the new doc
     */
    public static Doc get(CharSequence args, StringBuilder sb) {

        CharSequence a = args;
        Map<String, String> map = new HashMap<String, String>();
        for (Matcher m = ARGUMENT_PATTERN.matcher(a); m.matches(); m =
                ARGUMENT_PATTERN.matcher(a)) {
            map.put(m.group(1), m.group(2));
            a = m.group(3);
        }

        return new Doc(map, DocTokenizer.tokenize(sb));
    }

    /**
     * The field <tt>m</tt> contains the ...
     */
    private Map<String, String> attributes;

    /**
     * The field <tt>pack</tt> contains the ...
     */
    private String pack;

    /**
     * The field <tt>location</tt> contains the ...
     */
    private String location;

    /**
     * The field <tt>list</tt> contains the ...
     */
    private List<DocToken> list;

    /**
     * Creates a new object.
     * 
     * @param list ...
     * @param m ...
     */
    private Doc(Map<String, String> m, List<DocToken> list) {

        this.attributes = m;
        this.list = list;
    }

    /**
     * Getter for the value of an attribute.
     * 
     * @param key the key
     * 
     * @return the value
     */
    public String get(String key) {

        return attributes.get(key);
    }

    /**
     * Getter for location.
     * 
     * @return the location
     */
    public String getLocation() {

        return location;
    }

    /**
     * Getter for pack.
     * 
     * @return the pack
     */
    public String getPackage() {

        return pack;
    }

    /**
     * Setter for location.
     * 
     * @param location the location to set
     */
    public void setLocation(String location) {

        this.location = location;
    }

    /**
     * Setter for pack.
     * 
     * @param pack the pack to set
     */
    public void setPackage(String pack) {

        this.pack = pack;
    }

}
