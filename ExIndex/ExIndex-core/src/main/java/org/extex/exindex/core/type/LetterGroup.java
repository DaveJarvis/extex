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

package org.extex.exindex.core.type;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.lisp.type.value.LValue;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LetterGroup extends ArrayList<IndexEntry> implements LValue {

    /**
     * The field <tt>serialVersionUID</tt> contains the ...
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>before</tt> contains the ...
     */
    private String before;

    /**
     * The field <tt>after</tt> contains the ...
     */
    private String after;

    /**
     * The field <tt>list</tt> contains the ...
     */
    private List<String> prefixes;

    /**
     * Creates a new object.
     * 
     * @param before
     * @param after
     * @param prefixes
     */
    public LetterGroup(String before, String after, List<String> prefixes) {

        super();
        this.before = before;
        this.after = after;
        this.prefixes = prefixes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print("#LetterGroup(");
        if (before != null && !"".equals(before)) {
            stream.print(" :before \"");
            stream.print(before);
            stream.print("\"");
        }
        if (after != null && !"".equals(after)) {
            stream.print(" :after \"");
            stream.print(after);
            stream.print("\"");
        }
        if (prefixes != null && !prefixes.isEmpty()) {
            stream.print(" :prefixes (");
            for (String s : prefixes) {
                stream.print("\"");
                stream.print(s);
                stream.print("\" ");
            }
            stream.print(")");
        }
        stream.print(")");
    }

}
