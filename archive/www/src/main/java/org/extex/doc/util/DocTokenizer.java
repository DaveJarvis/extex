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

package org.extex.doc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class DocTokenizer {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param buffer ...
     * @param a ...
     * @param result ...
     * 
     * @return ...
     */
    private static int scanEndTag(StringBuilder buffer, int a,
            List<DocToken> result) {

        int i = a;
        while (buffer.charAt(i) != '>') {
            i++;
        }
        result.add(new EndNode(buffer.substring(a, i)));
        return i + 1;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param buffer ...
     * @param a ...
     * @param result ...
     * 
     * @return ...
     */
    private static int scanEntity(StringBuilder buffer, int a,
            List<DocToken> result) {

        int i = a;
        while (buffer.charAt(i) != ';') {
            i++;
        }
        result.add(new EntityNode(buffer.substring(a, i)));
        return i;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param buffer ...
     * @param a ...
     * @param result ...
     * 
     * @return ...
     */
    private static int scanReference(StringBuilder buffer, int a,
            List<DocToken> result) {

        int i = a;
        while (buffer.charAt(i) != '}') {
            i++;
        }
        result.add(new LinkNode("", buffer.substring(a, i))); // TODO
        return i + 1;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param buffer ...
     * @param a ...
     * @param result ...
     * 
     * @return ...
     */
    private static int scanTag(StringBuilder buffer, int a,
            List<DocToken> result) {

        int i = a;
        i = skipSpace(buffer, a);
        char c = buffer.charAt(i++);
        if (c == '/') {
            return scanEndTag(buffer, i, result);
        }
        int i0 = i - 1;
        while (Character.isLetterOrDigit(buffer.charAt(i))) {
            i++;
        }
        String name = buffer.substring(i0, i);
        StartNode n = new StartNode(name);

        for (i = skipSpace(buffer, i); buffer.charAt(i) != '>'; i =
                skipSpace(buffer, i)) {
            i0 = i;
            c = buffer.charAt(i++);
            if (c == '/') {
                if ('>' == buffer.charAt(i)) {
                    result.add(new EndNode(name));
                    break;
                }
                // TODO gene: scanTag unimplemented
                throw new RuntimeException("syntax error");
            }

            while (Character.isLetterOrDigit(buffer.charAt(i))) {
                i++;
            }
            String att = buffer.substring(i0, i);
            if ('=' != buffer.charAt(i++)) {
                // TODO gene: scanTag unimplemented
                throw new RuntimeException("syntax error");
            }
            i = skipSpace(buffer, i);
            c = buffer.charAt(i++);
            if (c != '\'' && c != '"') {
                // TODO gene: scanTag unimplemented
                throw new RuntimeException("syntax error");
            }

            i0 = i;
            while (buffer.charAt(i) != c) {
                i++;
            }

            n.setAttribute(att, buffer.substring(i0, i));
            i++;
        }
        result.add(n);
        return i + 1;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param buffer ...
     * @param a ...
     * 
     * @return ...
     */
    private static int skipSpace(StringBuilder buffer, int a) {

        int i = a;
        while (i < buffer.length() && Character.isWhitespace(buffer.charAt(i))) {
            i++;
        }
        return i;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param buffer ...
     * 
     * @return ...
     */
    public static List<DocToken> tokenize(StringBuilder buffer) {

        List<DocToken> result = new ArrayList<DocToken>();
        int len = buffer.length();
        int a = 0;
        int i = skipSpace(buffer, a);
        while (i < len) {
            char c = buffer.charAt(i);
            if (c == '<') {
                if (a < i) {
                    result.add(new ConstantNode(buffer.substring(a, i)));
                }
                a = scanTag(buffer, i + 1, result);
                i = a;

            } else if (c == '&') {
                if (a < i) {
                    result.add(new ConstantNode(buffer.substring(a, i)));
                }
                a = scanEntity(buffer, i + 1, result);
                i = a;
            } else if (c == '{' && buffer.charAt(i + 1) == '@') {
                if (a < i) {
                    result.add(new ConstantNode(buffer.substring(a, i)));
                }
                a = scanReference(buffer, i + 1, result);
                i = a;
            }
            i++;
        }

        return result;
    }

}
