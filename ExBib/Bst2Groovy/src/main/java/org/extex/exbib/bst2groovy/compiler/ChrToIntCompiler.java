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

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.LinkContainer;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.VoidGCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;

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
    private class ChrToInt extends GenericCode {

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
    private static final GCode CHR_TO_INT = new VoidGCode() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            String in = prefix + Bst2Groovy.INDENT;
            writer.write(prefix);
            writer.write("int chrToInt(String s) {");
            writer.write(in);
            writer.write("if (s.length() != 1) {");
            writer.write(in + Bst2Groovy.INDENT);
            writer
                .write("bstProcessor.warning(\"argument to chrToInt has wrong length\")");
            writer.write(in);
            writer.write("}");
            writer.write(in);
            writer.write("return s.charAt(0)");
            writer.write(prefix);
            writer.write("}\n");
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

        GCode a = state.pop();
        state.push(new ChrToInt(a));
        linkData.add(CHR_TO_INT);
    }

}
