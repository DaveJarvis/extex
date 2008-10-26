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

package org.extex.exbib.bst2groovy.compiler;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.LinkContainer;
import org.extex.exbib.bst2groovy.data.IntGCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GString;

/**
 * This class implements the analyzer for a field integer reader.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GetLocalIntegerCompiler implements Compiler {

    /**
     * This inner class is the expression for a getter of a local String in the
     * target program.
     */
    private class GetLocalInteger extends IntGCode {

        /**
         * The field <tt>entry</tt> contains the name of the entry.
         */
        private String entry;

        /**
         * Creates a new object.
         * 
         * @param entry the name of the entry
         */
        public GetLocalInteger(String entry) {

            this.entry = entry;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(entry);
            writer.write(".getLocal(");
            writer.write(GString.translate(name));
            writer.write(").getValue()");
        }
    }

    /**
     * The field <tt>name</tt> contains the name of the variable.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name
     */
    public GetLocalIntegerCompiler(String name) {

        this.name = name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.LinkContainer)
     */
    public void evaluate(EntryRefernce entryRefernce, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        state.push(new GetLocalInteger(entryRefernce.getName()));
    }

}
