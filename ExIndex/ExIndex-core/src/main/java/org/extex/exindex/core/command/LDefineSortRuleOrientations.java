/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.exindex.core.command;

import org.extex.exindex.core.type.SortRuleContainer;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define rule set orientations and the
 * number of phases.
 * 
 * <doc command="define-sort-rule-orientations">
 * <h3>The Command <tt>define-sort-rule-orientations</tt></h3>
 * 
 * <p>
 * The command <tt>define-sort-rule-orientations</tt> can be used to define
 * the orientations and the number of passes for sorting.
 * </p>
 * 
 * <pre>
 *  (define-letter-group <i>orientation-list</i>)
 * </pre>
 * 
 * <p>
 * The command has an argument which is described below.
 * </p>
 * 
 * <pre>
 *  (define-sort-rule-orientations ("ascending" "descending" "ascending"))
 * </pre>
 * 
 * TODO documentation incomplete
 * 
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineSortRuleOrientations extends LFunction {

    /**
     * The field <tt>container</tt> contains the container to store the
     * information in.
     */
    private SortRuleContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container to store the information
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineSortRuleOrientations(String name, SortRuleContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QLIST});
        this.container = container;
    }

    /**
     * Take a definition of sort rule orientations and store it.
     * 
     * @param interpreter the interpreter
     * @param list the list of orientations
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, LList list)
            throws LNonMatchingTypeException {

        int level = 0;

        for (LValue val : list) {
            String or = LString.stringValue(val);
            if ("".equals(or)) {
                throw new LNonMatchingTypeException(null);
            } else if ("forward".startsWith(or) || "ascending".startsWith(or)) {
                container.lookupOrCreateSortRule(Integer.valueOf(level))
                    .setAscending(true);
            } else if ("backward".startsWith(or) || "descending".startsWith(or)) {
                container.lookupOrCreateSortRule(Integer.valueOf(level))
                    .setAscending(false);
            } else {
                throw new LNonMatchingTypeException(null);
            }
            level++;
        }

        return LList.NIL;
    }

}
