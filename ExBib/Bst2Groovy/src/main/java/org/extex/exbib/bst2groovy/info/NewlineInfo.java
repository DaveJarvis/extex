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
import org.extex.exbib.bst2groovy.data.types.GType;
import org.extex.exbib.bst2groovy.evaluator.OpenEndedStack;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NewlineInfo implements Info {

    /**
     * TODO gene: missing JavaDoc.
     */
    private static class Newline implements GCode {

        /**
         * Creates a new object.
         * 
         */
        private Newline() {

            super();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#getType()
         */
        public GType getType() {

            return GType.VOID;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write("\n");
            writer.write(prefix);
            writer.write("bibWriter.printnl()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "newline";
        }

    }

    /**
     * The field <tt>NEWLINE</tt> contains the singleton instance.
     */
    private static final Newline NEWLINE = new Newline();

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.info.Info#evaluate(org.extex.exbib.bst2groovy.data.EntryRefernce,
     *      org.extex.exbib.bst2groovy.evaluator.OpenEndedStack,
     *      org.extex.exbib.bst2groovy.data.GCodeContainer, Evaluator)
     */
    public void evaluate(EntryRefernce entryRefernce, OpenEndedStack stack,
            GCodeContainer code, Evaluator evaluator) {

        code.add(NEWLINE);
    }

}
