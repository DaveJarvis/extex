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
import java.io.Writer;

import org.extex.exindex.core.command.type.LMarkup;
import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.parser.raw.RawIndexentry;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a structured index as a whole.
 * 
 * <div style="float:right;"> <img src="doc-files/classes.png"/> <br />
 * Figure: The structure </div>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StructuredIndex extends LetterGroupContainer {

    /**
     * The field <tt>depth</tt> contains the hierarchy depth; i.e. 0 for flat
     * and Integer.MAX_VALUE for tree.
     */
    private int depth = Integer.MAX_VALUE;

    /**
     * Creates a new object.
     */
    public StructuredIndex() {

        super();
    }

    /**
     * Setter for depth.
     * 
     * @param depth the depth to set
     */
    public void setDepth(int depth) {

        this.depth = depth;
    }

    /**
     * Store an index entry.
     * 
     * @param entry the entry to store
     * 
     * @throws IndexerException in case of an error
     */
    public void store(RawIndexentry entry) throws IndexerException {

        String[] sortKey = entry.getSortKey();
        LetterGroup group =
                (sortKey != null && sortKey.length != 0
                        ? findLetterGroup(sortKey[0])
                        : null);

        if (group == null) {
            group = getLetterGroup("default");
            if (group == null) {
                throw new IndexerException(null, LocalizerFactory.getLocalizer(
                    getClass()).format("NoPropoperLetterGroup",
                    entry.toString()));
            }
        }
        group.store(entry, depth);
    }

    /**
     * Write the structured index according to the definition of the parameters.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * @param trace the trace indicator
     * 
     * @throws IOException in case of an I/O error
     * @throws LException in case of an error
     */
    public void write(Writer writer, LInterpreter interpreter, boolean trace)
            throws IOException,
                LException {

        LMarkup markup = (LMarkup) interpreter.get("markup-index");
        LMarkup markupList =
                (LMarkup) interpreter.get("markup-letter-group-list");

        boolean first = true;
        markup.write(writer, interpreter, null, LMarkup.OPEN, trace);
        markupList.write(writer, interpreter, null, LMarkup.OPEN, trace);

        for (LetterGroup group : sorted()) {
            if (group.isEmpty()) {
                // skip
            } else if (first) {
                first = false;
            } else {
                markupList.write(writer, interpreter, null, LMarkup.SEP, trace);
            }
            group.write(writer, interpreter, trace);
        }

        markupList.write(writer, interpreter, null, LMarkup.CLOSE, trace);
        markup.write(writer, interpreter, null, LMarkup.CLOSE, trace);
    }

}
