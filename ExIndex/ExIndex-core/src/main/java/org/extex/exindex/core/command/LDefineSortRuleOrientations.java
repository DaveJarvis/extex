/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command;

import org.extex.exindex.core.type.rules.SortRuleContainer;
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
*
 * <p>The Command {@code define-sort-rule-orientations}</p>
 * 
 * <p>
 * The command {@code define-sort-rule-orientations} can be used to define the
 * orientations and the number of passes for sorting.
 * </p>
 * 
 * <pre>
 *  (define-letter-group <i>orientation-list</i>)   </pre>
 * 
 * <p>
 * The command has a list as argument. This list determines with its length how
 * many passes are at most applied to the entry. The elements determine whether
 * the comparison is done left to right or right to left. For this purpose the
 * following values are allowed in the orientation list:
 * </p>
 * <dl>
 * <dt>left-to-right</dt>
 * <dd>The comparison is performed left to right.</dd>
 * <dt>forward</dt>
 * <dd>The comparison is performed left to right. This as an alias for backward
 * compatibility.</dd>
 * <dt>right-to-left</dt>
 * <dd>The comparison is performed right to left.</dd>
 * <dt>backward</dt>
 * <dd>The comparison is performed right to left. This as an alias for backward
 * compatibility.</dd>
 * </dl>
 * 
 * <p>
 * The following example shows a definition of at most three passes with
 * different orientations.
 * </p>
 * 
 * <pre>
 *  (define-sort-rule-orientations ("left-to-right" "right-to-left" "left-to-right")) </pre>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LDefineSortRuleOrientations extends LFunction {

    /**
     * The field {@code container} contains the container to store the
     * information in.
     */
    private final SortRuleContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container to store the information
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
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
     * @return {@code nil}
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
            } else if ("forward".startsWith(or)
                    || "left-to-right".startsWith(or)) {
                container.lookupOrCreateSortRule(Integer.valueOf(level))
                    .setAscending(true);
            } else if ("backward".startsWith(or)
                    || "right-to-left".startsWith(or)) {
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
