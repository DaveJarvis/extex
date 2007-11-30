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

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StructuredIndex extends ArrayList<LetterGroup> implements LValue {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     */
    public StructuredIndex() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        // TODO gene: print unimplemented

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(Writer writer, LInterpreter interpreter)
            throws IOException {

        boolean first = true;
        interpreter.writeString(writer, "markup:index-open");

        interpreter.writeString(writer, "markup:letter-group-list-open");
        for (LetterGroup group : this) {
            if (first) {
                first = false;
            } else {
                interpreter.writeString(writer, "markup:letter-group-list-sep");
            }
            group.write(writer, interpreter);
        }
        interpreter.writeString(writer, "markup:letter-group-list-close");

        interpreter.writeString(writer, "markup:index-close");
    }

}
