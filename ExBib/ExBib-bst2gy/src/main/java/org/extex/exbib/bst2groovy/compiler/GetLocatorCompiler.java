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
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.core.bst.token.impl.TLocalLocator.LocatorField;

/**
 * This class implements the analyzer for a locator reader.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 7609 $
 */
public class GetLocatorCompiler implements Compiler {

    /**
     * This inner class is the expression for a getter of a local String in the
     * target program.
     */
    private class GetLocator extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param entry the name of the entry
         */
        public GetLocator(String entry) {

            super(ReturnType.STRING, entry + ".getLocator()."
                    + getGetter(field.toString()));
        }
    }

    /**
     * Construct the name of the getter for a given attribute.
     * 
     * @param name the name of the attribute
     * 
     * @return the name of the getter
     */
    private static String getGetter(String name) {

        StringBuilder buffer = new StringBuilder();
        buffer.append("get");
        buffer.append(Character.toUpperCase(name.charAt(0)));
        buffer.append(name.substring(1).toLowerCase());
        return buffer.toString();
    }

    /**
     * The field <tt>field</tt> contains the field.
     */
    private LocatorField field;

    /**
     * Creates a new object.
     * 
     * @param field the name of the local string
     */
    public GetLocatorCompiler(LocatorField field) {

        this.field = field;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryRefernce entry, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        state.push(new GetLocator(entry.getName()));
    }

}
