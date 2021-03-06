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

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.compiler.NewlineCompiler.Newline;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.processor.EntryReference;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.linker.LinkContainer;

/**
 * This class implements the analyzer for the {@code write$} built-in.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class WriteCompiler implements Compiler {

    /**
     * This inner class is the expression for the write$ built-in in the target
     * program.
     */
    private static final class Write extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param arg the arguments
         */
        private Write(GCode... arg) {

            super(ReturnType.VOID, "write", arg);
        }

    @Override
        public boolean hasSideEffect() {

            return true;
        }

        /**
    *      int)
         */
        @Override
        public int optimize(List<GCode> list, int index) {

            if (index + 1 >= list.size()) {
                return index + 1;
            }
            GCode next = list.get(index + 1);
            if (next instanceof Newline) {
                list.remove(index + 1);
                list.set(index, new Writeln(getArg(0)));
                return index + 1;
            }
            if (!(next instanceof Write)) {
                return index + 1;
            }
            List<GCode> a = new ArrayList<GCode>();
            a.add(getArg(0));
            a.add(((Write) next).getArg(0));
            list.remove(index);
            list.remove(index);
            while (index < list.size()) {
                next = list.get(index);
                if (next instanceof Newline) {
                    list.set(index, new Writeln(a.toArray(new GCode[]{})));
                    return index + 1;
                } else if (next instanceof Write) {
                    a.add(((Write) next).getArg(0));
                    list.remove(index);
                } else {
                    break;
                }
            }
            list.add(index, new Write(a.toArray(new GCode[]{})));
            return index + 1;
        }

    }

    /**
     * This inner class is the expression for the write$ built-in in the target
     * program.
     */
    private static final class Writeln extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param arg the arguments
         */
        private Writeln(GCode... arg) {

            super(ReturnType.VOID, "writeln", arg);
        }

    }

    /**
*      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryReference entryReference, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        if (state.stackHasSideEffects()) {
            throw new RuntimeException("unimplemented");
        }
        state.add(new Write(state.pop()));
    }

}
