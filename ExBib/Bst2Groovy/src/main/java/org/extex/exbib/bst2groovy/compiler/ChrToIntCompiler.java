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

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.data.types.GStringConstant;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.exception.ChrToIntLengthException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.bst2groovy.linker.LinkingCode;

/**
 * This class implements the analyzer for the chr.to.int$ builtin.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ChrToIntCompiler implements Compiler {

    /**
     * This inner class is the expression for the chr.to.int$ builtin in the
     * target program.
     */
    private static class ChrToInt extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param a the argument
         */
        public ChrToInt(GCode a) {

            super(ReturnType.INT, "chrToInt", a);
        }
    }

    /**
     * The field <tt>CHR_TO_INT</tt> contains the linker code.
     */
    private static final LinkingCode CHR_TO_INT = new LinkingCode() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        @Override
        public void print(CodeWriter writer, String prefix) throws IOException {

            String in = prefix + Bst2Groovy.INDENT;
            writer
                .write(
                    prefix,
                    "int chrToInt(String s) {",
                    in,
                    "if (s.length() != 1) {",
                    in,
                    Bst2Groovy.INDENT,
                    "bstProcessor.warning(\"argument to chrToInt has wrong length\")",
                    in, "}", in, "return s.charAt(0)", prefix, "}\n");
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

        GCode a = state.pop();
        if (a instanceof GStringConstant) {
            String s = ((GStringConstant) a).getValue();
            if (s.length() != 1) {
                throw new ChrToIntLengthException(s);
            }
            state.push(new GIntegerConstant(s.charAt(0)));

        } else {
            state.push(new ChrToInt(a));
            linkData.add(CHR_TO_INT);
        }
    }

}
