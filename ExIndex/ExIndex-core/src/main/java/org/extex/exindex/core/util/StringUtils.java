/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides some utilities for Strings and such.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class StringUtils {

    /**
     * Create a list of strings from the arguments.
     * 
     * @param args the arguments
     * 
     * @return the list
     */
    public static List<String> makeList(String... args) {

        ArrayList<String> list = new ArrayList<String>();
        for (String a : args) {
            list.add(a);
        }
        return list;
    }

    /**
     * Translate a {@link String} into a printable representation and place it
     * in a {@link StringBuilder}. The String is surrounded by double quotes
     * and special characters are quoted.
     * 
     * @param sb the target string builder
     * @param s the string to translate
     */
    public static void putPrintable(StringBuilder sb, String s) {

        sb.append("\"");
        sb.append(s.replaceAll("\\\\", "\\\\")//
            .replaceAll("\"", "\\\"")//
            .replaceAll("\n", "\\n")//
            .replaceAll("\t", "\\t"));
        sb.append("\"");
    }


    private StringUtils() {

        // forbidden
    }
}
