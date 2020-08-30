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

import java.util.List;

import org.extex.exindex.core.exception.UnknownAttributeException;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to use a rule set.
 * 
*
 * <p>The Command {@code use-rule-set}</p>
 * 
 * <p>
 * The command {@code use-rule-set} can be used to add a sort rule. This
 * command honors the current index. This means that the sort rule is attached
 * to the current index.
 * </p>
 * 
 * <pre>
 *  (use-rule-set
 *     [:run <i>level</i>]
 *     [:rule-set <i>list-of-rule-set-names</i>]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (use-rule-set :run 1 :rules ("abc" "def"))   </pre>
 * 
 * TODO documentation incomplete
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LUseRuleSet extends LFunction {

    /**
     * The field {@code container} contains the container of rule sets.
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
    public LUseRuleSet(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_NUMBER(":phase", Integer.valueOf(0)),
                Arg.OPT_QSTRING_LIST(":rule-set")});
        this.container = container;
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param phase the phase
     * @param ruleList the list of rules
     * 
     * @return {@code nil}
     * 
     * @throws LNonMatchingTypeException in case of an error
     * @throws UnknownAttributeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, Integer phase,
            LList ruleList)
            throws LNonMatchingTypeException,
                UnknownAttributeException {

        for (LValue value : ruleList) {
            String s = LString.stringValue(value);
            List<Rule> ruleSet = container.lookupRule(s);
            if (ruleSet == null) {
                throw new UnknownAttributeException(null, s);
            }
            container.addSortRules(phase, ruleSet);
        }

        return null;
    }

}
