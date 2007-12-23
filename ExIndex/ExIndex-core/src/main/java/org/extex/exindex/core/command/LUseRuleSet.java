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

import java.util.List;

import org.extex.exindex.core.command.type.RuleSetContainer;
import org.extex.exindex.core.command.type.SortRuleContainer;
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
 * <doc command="use-rule-set">
 * <h3>The Command <tt>use-rule-set</tt></h3>
 * 
 * <p>
 * The command <tt>use-rule-set</tt> can be used to add a sort rule.
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
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LUseRuleSet extends LFunction {

    /**
     * The field <tt>ruleSetContainer</tt> contains the container of rule
     * sets.
     */
    private RuleSetContainer ruleSetContainer;

    /**
     * The field <tt>sortRules</tt> contains the container of the sort rules.
     */
    private SortRuleContainer sortRules;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param ruleSetContainer the container of rule sets
     * @param sortRules the container of sort rules
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LUseRuleSet(String name, RuleSetContainer ruleSetContainer,
            SortRuleContainer sortRules)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_NUMBER(":phase"),//
                Arg.OPT_QSTRING_LIST(":rule-set")});
        this.ruleSetContainer = ruleSetContainer;
        this.sortRules = sortRules;
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param phase the phase
     * @param ruleSet the rule set
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, Long phase, LList ruleSet)
            throws LNonMatchingTypeException {

        int level = phase.intValue();

        for (LValue value : ruleSet) {
            String s = LString.stringValue(value);
            List<Rule> rs = ruleSetContainer.lookup(s);
            if (rs == null) {
                // TODO gene: evaluate unimplemented
                throw new RuntimeException("unimplemented");
            }
            sortRules.add(level, rs);
        }

        return LList.NIL;
    }

}
