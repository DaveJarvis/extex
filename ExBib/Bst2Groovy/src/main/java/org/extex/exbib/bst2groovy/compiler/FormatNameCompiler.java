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
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.StringGCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;

/**
 * This class implements the analyzer for the format.name$ builtin.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FormatNameCompiler implements Compiler {

    /**
     * This inner class is the expression for the format.name$ builtin in the
     * target program.
     */
    private class FormatName extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param name the argument
         * @param index the index
         * @param format the format specification
         */
        public FormatName(GCode name, GCode index, GCode format) {

            super(ReturnType.STRING, "format_name", name, index, format);
        }
    }

    /**
     * The field <tt>FORMAT_NAME</tt> contains the link code.
     */
    private static final GCode FORMAT_NAME = new StringGCode() {

        public void print(Writer writer, String prefix) throws IOException {

            writer
                .write("String format_name(String name, int index, String format) {\n"
                        + "}\n");
        }
    };

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

        GCode format = state.pop();
        GCode index = state.pop();
        GCode name = state.pop();

        state.push(new FormatName(name, index, format));
        linkData.add(FORMAT_NAME);
    }

}
