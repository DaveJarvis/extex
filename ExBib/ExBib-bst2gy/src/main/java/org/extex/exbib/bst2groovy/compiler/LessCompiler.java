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

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.BinaryInfix;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.bool.GBoolean;
import org.extex.exbib.bst2groovy.data.processor.EntryReference;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.linker.LinkContainer;

/**
 * This class implements the analyzer for the {@code <} built-in.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LessCompiler implements Compiler {

    /**
     * This inner class is the expression for the {@code <} built-in in the target
     * program.
     */
    public static final class Less extends BinaryInfix {

        /**
         * Creates a new object.
         * 
         * @param a the first argument
         * @param b the second argument
         */
        public Less(GCode a, GCode b) {

            super(a, b, "<", BinaryInfix.RELATION_LEVEL);
        }

    @Override
        public GCode optimize() {

            super.optimize();
            GCode a = getArg(1);
            GCode b = getArg(0);
            if (a instanceof GIntegerConstant && b instanceof GIntegerConstant) {
                return new GIntegerConstant(
                    ((GIntegerConstant) a).getValue() < ((GIntegerConstant) b)
                        .getValue() ? 1 : 0);
            }
            return this;
        }
    }

    /**
     * This inner class is the expression for the {@code <=} built-in in the target
     * program.
     */
    public static final class LessEqual extends BinaryInfix {

        /**
         * Creates a new object.
         * 
         * @param a the first argument
         * @param b the second argument
         */
        public LessEqual(GCode a, GCode b) {

            super(a, b, "<=", BinaryInfix.RELATION_LEVEL);
        }

    @Override
        public GCode optimize() {

            super.optimize();
            GCode a = getArg(1);
            GCode b = getArg(0);
            if (a instanceof GIntegerConstant && b instanceof GIntegerConstant) {
                return new GIntegerConstant(
                    ((GIntegerConstant) a).getValue() <= ((GIntegerConstant) b)
                        .getValue() ? 1 : 0);
            }
            return this;
        }
    }

    /**
*      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryReference entryReference, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        GCode a = state.pop();
        GCode b = state.pop();
        state.push(new GBoolean(new Less(a, b)));
    }
}
