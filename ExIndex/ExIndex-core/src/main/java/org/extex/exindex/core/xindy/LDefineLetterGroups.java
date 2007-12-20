/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.xindy;

import org.extex.exindex.core.type.LetterGroup;
import org.extex.exindex.core.type.LetterGroupContainer;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define letter groups.
 * 
 * <doc command="define-letter-groups">
 * <h3>The Command <tt>define-letter-groups</tt></h3>
 * 
 * <p>
 * The command <tt>define-letter-groups</tt> can be used to define a set of
 * letter groups with an order in one batch. This command provides an
 * abbreviation of several invocations of the command
 * <tt>define-letter-group</tt>. Thus the major concepts can be found there.
 * </p>
 * 
 * <pre>
 *  (define-letter-group <i>letter-groups-list</i>)
 * </pre>
 * 
 * <p>
 * The command has one arguments which is described now. The argument is a list
 * of strings. Those strings are the names of the letter groups. The order of
 * the list is translated into corresponding <tt>:after</tt> declarations.
 * </p>
 * 
 * <pre>
 *  (define-letter-groups ("A" "B" "C" "D"))
 * </pre>
 * 
 * <p>
 * This definition is equivalent to the following sequence of commands.
 * </p>
 * 
 * <pre>
 *  (define-letter-group "A")
 *  (define-letter-group "B" :after "A")
 *  (define-letter-group "C" :after "B")
 *  (define-letter-group "D" :after "C")
 * </pre>
 * 
 * </doc>
 * 
 * @see LDefineLetterGroup
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineLetterGroups extends LFunction {

    /**
     * The field <tt>container</tt> contains the ...
     */
    private LetterGroupContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineLetterGroups(String name, LetterGroupContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QLIST});
        this.container = container;
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param list the list of letter group names
     * 
     * @return <tt>null</tt>
     * 
     * @throws LNonMatchingTypeException in case of an error
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, LList list)
            throws LNonMatchingTypeException,
                LSettingConstantException {

        LetterGroup last = null;

        for (LValue val : list) {
            String name = LString.getString(val);
            LetterGroup g = container.defineLetterGroup(name);
            if (last != null) {
                g.after(last);
            }
            last = g;
        }

        return null;
    }
}
