/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.processor.EntryReference;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.bst2groovy.linker.LinkingCode;

/**
 * This class implements the analyzer for the {@code add.period$} built-in.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AddPeriodCompiler implements Compiler {

    /**
     * This inner class is the expression for the add.period$ built-in in the
     * target program.
     */
    private static class AddPeriod extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param arg the argument
         */
        public AddPeriod(GCode arg) {

            super(ReturnType.STRING, "addPeriod", arg);
        }
    }

    /**
     * The field {@code ADD_PERIOD} contains the code for the add_period
     * method.
     */
    private static final LinkingCode ADD_PERIOD =
            new LinkingCode("addPeriod()") {

                /**
            *      java.lang.String)
                 */
                @Override
                public void print(CodeWriter writer) throws IOException {

                    writer.write("\n\tString addPeriod(String s) {",
                        "\n\t\treturn s == null || s == '' ? '' "
                                + ": s.matches(\".*[.!?]\") ? s : s + \".\"",
                        "\n\t}\n");
                }

            };

    /**
*      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryReference entryReference, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        state.push(new AddPeriod(state.pop()));
        linkData.add(ADD_PERIOD);
    }

}
