/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.bst2groovy.linker.LinkingCode;

/**
 * This class implements the analyzer for option constants.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OptionCompiler extends GIntegerConstant implements Compiler {

    /**
     * The field <tt>init</tt> contains the initializer.
     */
    private String init;

    /**
     * The field <tt>INIT</tt> contains the linking code.
     */
    private final LinkingCode initCode = new LinkingCode() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GenericCode#print(org.extex.exbib.bst2groovy.io.CodeWriter,
         *      java.lang.String)
         */
        @Override
        public void print(CodeWriter writer) throws IOException {

            writer.write("\n\tprivate static final int ", init, "\n");
        }

    };

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name the name
     * @param value the value
     */
    public OptionCompiler(String name, int value) {

        super(value);
        this.name = name;
        this.init = name + " = " + Integer.toString(value);
    }

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

        state.push(this);
        linkData.add(initCode);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
     *      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        writer.write(name);
    }

}
