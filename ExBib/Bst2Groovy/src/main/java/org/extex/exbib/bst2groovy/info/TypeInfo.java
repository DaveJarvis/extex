/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.info;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.Evaluator;
import org.extex.exbib.bst2groovy.data.EntryRefernce;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.evaluator.OpenEndedStack;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TypeInfo implements Info {

    /**
     * TODO gene: missing JavaDoc.
     * 
     */
    private class Type implements GCode {

        /**
         * The field <tt>entry</tt> contains the name of the entry.
         */
        private String entry;

        /**
         * Creates a new object.
         * 
         * @param entry the name of the entry
         */
        public Type(String entry) {

            super();
            this.entry = entry;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#getType()
         */
        public org.extex.exbib.bst2groovy.data.types.GType getType() {

            return org.extex.exbib.bst2groovy.data.types.GType.STRING;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(entry);
            writer.write(".getType()");
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.info.Info#evaluate(org.extex.exbib.bst2groovy.data.EntryRefernce,
     *      org.extex.exbib.bst2groovy.evaluator.OpenEndedStack,
     *      org.extex.exbib.bst2groovy.data.GCodeContainer, Evaluator)
     */
    public void evaluate(EntryRefernce entryRefernce, OpenEndedStack stack,
            GCodeContainer code, Evaluator evaluator) {

        stack.push(new Type(entryRefernce.getName()));
    }

}
