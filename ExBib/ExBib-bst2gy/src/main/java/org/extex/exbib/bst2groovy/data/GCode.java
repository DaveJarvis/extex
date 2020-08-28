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

package org.extex.exbib.bst2groovy.data;

import java.io.IOException;
import java.util.List;

import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This interface describes some code in the target language. It is a node in
 * the tree making up the program.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface GCode {

    /**
     * Determine the type of the expression. The unknown type is represented by
     * the constant {@link ReturnType#UNKNOWN}.
     * 
     * @return the type which is never <code>null</code>
     */
    ReturnType getType();

    /**
     * Check whether the code has a side effect on the output writer.
     * 
     * @return <code>true</code> iff something is written to the output writer
     */
    boolean hasSideEffect();

    /**
     * Optimize this code. This optimization does involve the current node only.
     * See {@link #optimize(List, int)} for an optimization in the context.
     * 
     * @return the optimized code which is never <code>null</code>
     */
    GCode optimize();

    /**
     * Optimize this code in the context of a code list. The optimization may
     * inspect the surrounding elements and modify the list as needed.
     * 
     * @param list the list
     * @param index the current index
     * 
     * @return the next index of the element which should be optimized next.
     *         This is usually the value <code>index + 1</code>
     */
    int optimize(List<GCode> list, int index);

    /**
     * Print the expression to a writer.
     * 
     * @param writer the target writer
     * @param prefix the prefix to be added at the begin of each new line
     * 
     * @throws IOException in case of an I/O error
     */
    void print(CodeWriter writer, String prefix) throws IOException;

    /**
     * Unify the current instance with another object.
     * 
     * @param other the other code to unify with
     * 
     * @return <code>true</code> upon success
     */
    boolean unify(GCode other);
}
