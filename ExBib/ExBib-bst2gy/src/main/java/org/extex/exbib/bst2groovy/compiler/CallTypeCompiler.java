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
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.bst2groovy.linker.LinkingCode;

/**
 * This class implements the analyzer for the call.type$ built-in.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CallTypeCompiler implements Compiler {

    /**
     * This inner class is the expression for the call.type$ built-in in the
     * target program.
     */
    private static class CallType extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param entry the name of the entry variable
         */
        public CallType(String entry) {

            super(ReturnType.VOID, "callType", entry);
        }
    }

    /**
     * The field <tt>CALL_TYPE</tt> contains the linker code.
     */
    private static final LinkingCode CALL_TYPE = new LinkingCode("callType()") {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.linker.LinkingCode#print(org.extex.exbib.bst2groovy.io.CodeWriter)
         */
        @Override
        public void print(CodeWriter writer) throws IOException {

            writer.write("\n\tvoid callType(Entry entry) {", //
                "\n\t\tdef typeFunction = types[entry.getType()]", //
                "\n\t\tif (typeFunction == null) {", //
                "\n\t\t\ttypeFunction = types['default.type']", //
                "\n\t\t\tif (typeFunction == null) {", //
                "\n\t\t\t\twarning('missing default.type')", //
                "\n\t\t\t\treturn", //
                "\n\t\t\t}", //
                "\n\t\t}", //
                "\n\t\ttypeFunction(entry)", //
                "\n\t}\n");
        }
    };

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryRefernce entryRefernce, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        state.add(new CallType(entryRefernce.getName()));
        linkData.add(CALL_TYPE);
    }

}
