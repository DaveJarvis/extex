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

import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StructuredIndex extends LetterGroupContainer implements LValue {

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
        throw new RuntimeException("unimplemented");
    }

    /**
     * Store an index entry.
     * 
     * @param entry the entry to store
     * 
     * @throws IndexerException in case of an error
     */
    public void store(RawIndexentry entry) throws IndexerException {

        String[] mk = entry.getMergeKey();
        if (mk == null || mk.length == 0) {
            // TODO gene: computeLetterGroup unimplemented
            throw new RuntimeException("unimplemented");
        }
        LetterGroup group = findLetterGroup(mk[0]);
        if (group == null) {
            throw new IndexerException("", "", LocalizerFactory.getLocalizer(
                getClass()).format("NoPropoperLetterGroup", entry.toString()));
        }
        group.store(entry);
    }

    /**
     * Write the structured index according to the definition of the parameters.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error
     */
    public void write(Writer writer, LInterpreter interpreter)
            throws IOException,
                LException {

        boolean first = true;
        interpreter.writeString(writer, "markup:index-open");

        interpreter.writeString(writer, "markup:letter-group-list-open");
        for (LetterGroup group : sorted()) {
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
