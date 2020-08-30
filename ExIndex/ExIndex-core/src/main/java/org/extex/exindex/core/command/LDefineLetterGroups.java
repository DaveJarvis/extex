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

import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.LetterGroup;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define letter groups.
 * 
*
 * <p>The Command {@code define-letter-groups}</p>
 * 
 * <p>
 * The command {@code define-letter-groups} can be used to define a set of
 * letter groups with an order in one batch. This command provides an
 * abbreviation of several invocations of the command
 * {@code define-letter-group}. Thus the major concepts can be found there.
 * </p>
 * 
 * <pre>
 *  (define-letter-group <i>letter-groups-list</i>)   </pre>
 * 
 * <p>
 * The command has one arguments which is described now. The argument is a list
 * of strings. Those strings are the names of the letter groups. The order of
 * the list is translated into corresponding {@code :after} declarations.
 * </p>
 * 
 * <pre>
 *  (define-letter-groups ("A" "B" "C" "D"))   </pre>
 * 
 * <p>
 * This definition is equivalent to the following sequence of commands.
 * </p>
 * 
 * <pre>
 *  (define-letter-group "A")
 *  (define-letter-group "B" :after "A")
 *  (define-letter-group "C" :after "B")
 *  (define-letter-group "D" :after "C")   </pre>
 * 
 *
 * @see LDefineLetterGroup
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LDefineLetterGroups extends LFunction {

    /**
     * The field {@code container} contains the letter group container.
     */
    private final IndexContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineLetterGroups(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QLIST});
        this.container = container;
    }

    /**
     * Take a list of letter groups and store it in the container.
     * 
     * @param interpreter the interpreter
     * @param list the list of letter group names
     * 
     * @return {@code null}
     * 
     * @throws LNonMatchingTypeException in case of an error
     * @throws LException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, LList list)
            throws LNonMatchingTypeException,
                LException {

        LetterGroup last = null;

        for (LValue val : list) {
            String name = LString.stringValue(val);
            LetterGroup group = container.defineLetterGroup(name, name);
            if (last != null) {
                group.after(last);
            }
            last = group;
        }

        return null;
    }
}
