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
public class TextPrefixInfo implements Info {

    /**
     * TODO gene: missing JavaDoc.
     * 
     */
    private class TextPrefix implements GCode {

        /**
         * The field <tt>arg</tt> contains the argument.
         */
        private GCode arg;

        /**
         * The field <tt>arg2</tt> contains the second argument.
         */
        private GCode arg2;

        /**
         * Creates a new object.
         * 
         * @param arg the argument
         * @param arg2 the second argument
         */
        public TextPrefix(GCode arg, GCode arg2) {

            super();
            this.arg = arg;
            this.arg2 = arg2;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#getType()
         */
        public GType getType() {

            return GType.STRING;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write("text_prefix(");
            arg.print(writer, prefix);
            writer.write(", ");
            arg2.print(writer, prefix);
            writer.write(")");
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

        GCode a = stack.pop();
        GCode b = stack.pop();
        stack.push(new TextPrefix(a, b));
    }

}
