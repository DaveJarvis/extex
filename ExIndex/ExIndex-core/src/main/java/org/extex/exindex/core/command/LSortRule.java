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
import org.extex.exindex.core.type.rules.StringRule;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a sort rule.
 * 
*
 * <p>The Command {@code sort-rule}</p>
 * 
 * <p>
 * The command {@code sort-rule} can be used to add a sort rule. This command
 * honors the current index. This means that the sort rule is attached to the
 * current index.
 * </p>
 * 
 * <pre>
 *  (sort-rule <i>pattern</i> <i>replacement-text</i>
 *     [:again]
 *     [:run <i>level</i>]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (sort-rule "abc" "ABC")   </pre>
 * 
 * TODO documentation incomplete
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LSortRule extends LFunction {

    /**
     * The field {@code container} contains the container.
     */
    private final IndexContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container of indices
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LSortRule(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.STRING,
                Arg.OPT_BOOLEAN(":again", Boolean.FALSE),
                Arg.OPT_NUMBER(":run", Integer.valueOf(0))});
        this.container = container;
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param pattern the pattern
     * @param replacement the replacement text
     * @param again the optional indicator to restart the replacement cycle from
     * start
     * @param phase the phase number to add this rule to; it defaults to 0 if
     * not given
     * 
     * @return {@code null}
     * 
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, String pattern,
            String replacement, Boolean again, Integer phase)
            throws LSettingConstantException {

        container.addSortRule(phase,
            new StringRule(pattern, replacement, again.booleanValue()));
        return null;
    }

}
