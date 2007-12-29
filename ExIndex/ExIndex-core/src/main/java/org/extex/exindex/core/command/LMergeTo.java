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

package org.extex.exindex.core.command;

import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.command.type.AttributeMergeInfo;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LBoolean;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a rule set.
 * 
 * <doc command="merge-to">
 * <h3>The Command <tt>merge-to</tt></h3>
 * 
 * <p>
 * The command <tt>merge-to</tt> can be used to add a merge rule.
 * </p>
 * 
 * <pre>
 *  (merge-to <i>from-attribute</i> <i>to-attribute</i>
 *     [:drop]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (merge-to "abc" "ABC")   </pre>
 * 
 * TODO documentation incomplete
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMergeTo extends LFunction {

    /**
     * The field <tt>map</tt> contains the mapping from name to mapping info.
     */
    private Map<String, AttributeMergeInfo> map =
            new HashMap<String, AttributeMergeInfo>();

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMergeTo(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.LSTRING, Arg.LSTRING, //
                Arg.OPT_LBOOLEAN(":drop")});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param from the attribute from which to merge
     * @param to the attribute to which to merge
     * @param drop the drop indicator
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, LString from, LString to,
            LBoolean drop)
            throws LSettingConstantException,
                LNonMatchingTypeException {

        map.put(LString.stringValue(from), new AttributeMergeInfo(LString
            .stringValue(to), drop == LBoolean.TRUE));
        return null;
    }

    /**
     * Look-up for the merge info of an attribute.
     * 
     * @param name the name of the attribute from which to merge
     * 
     * @return the info for the attribute or <code>null</code> for none
     */
    public AttributeMergeInfo lookup(String name) {

        return map.get(name);
    }
}
