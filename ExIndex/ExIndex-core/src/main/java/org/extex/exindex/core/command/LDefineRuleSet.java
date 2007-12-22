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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exindex.core.exception.InconsistentFlagsException;
import org.extex.exindex.core.type.rules.RegexRule;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.core.type.rules.StringRule;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingArgumentsException;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This is the adapter for the L system to define a rule set.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineRuleSet extends LFunction {

    /**
     * The field <tt>RULES</tt> contains the ...
     */
    private static final LSymbol RULES = LSymbol.get(":rules");

    /**
     * The field <tt>INHERIT_FROM</tt> contains the ...
     */
    private static final LSymbol INHERIT_FROM = LSymbol.get(":inherit-from");

    /**
     * The field <tt>AGAIN</tt> contains the ...
     */
    private static final LSymbol AGAIN = LSymbol.get(":again");

    /**
     * The field <tt>STRING</tt> contains the ...
     */
    private static final LSymbol STRING = LSymbol.get(":string");

    /**
     * The field <tt>BREGEX</tt> contains the ...
     */
    private static final LSymbol BREGEX = LSymbol.get(":bregex");

    /**
     * The field <tt>EREGEX</tt> contains the ...
     */
    private static final LSymbol EREGEX = LSymbol.get(":eregex");

    /**
     * The field <tt>map</tt> contains the ...
     */
    private Map<String, List<Rule>> map = new HashMap<String, List<Rule>>();

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineRuleSet(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.VARARG()});
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param rules
     * @param lst
     * 
     * @throws LException in case of an error
     */
    private void addInheritedRules(List<Rule> rules, LList lst)
            throws LException {

        for (LValue value : lst) {
            String s = LString.getString(value);
            List<Rule> rl = map.get(s);
            if (rl == null) {
                throw new LException(LocalizerFactory.getLocalizer(getClass())
                    .format("RuleUndefined", s));
            }
            for (Rule r : rl) {
                rules.add(r);
            }
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param rules
     * @param lst
     * 
     * @throws LNonMatchingTypeException in case of an error
     * @throws InconsistentFlagsException in case of an error
     */
    private void addRules(List<Rule> rules, LList lst)
            throws LNonMatchingTypeException,
                InconsistentFlagsException {

        int size = lst.size();
        if (size < 2) {
            // TODO gene: addRules unimplemented
            throw new RuntimeException("unimplemented");
        }
        boolean again = false;
        int t = 0;
        LSymbol type = null;
        String pattern = LString.getString(lst.get(0));
        String replacement = LString.getString(lst.get(0));
        for (int i = 0; i < size; i++) {
            LValue value = lst.get(i);
            if (value == AGAIN) {
                again = true;
            } else if (value == STRING || value == BREGEX || value == EREGEX) {
                type = (LSymbol) value;
                t++;
            } else {
                throw new LNonMatchingTypeException("");
            }
        }

        if (t > 1) {
            throw new InconsistentFlagsException(null, "", "");
        }

        if (type == null || type == STRING) {
            rules.add(new StringRule(pattern, replacement, again));
        } else if (type == BREGEX) {
            // TODO gene: addRules unimplemented
            throw new RuntimeException("BREGEX unimplemented");
        } else if (type == EREGEX) {
            rules.add(new RegexRule(pattern, replacement, again));
        }
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the rule set
     * @param args ...
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String name, LList args)
            throws LException {

        List<Rule> rules = new ArrayList<Rule>();
        int size = args.size();
        for (int i = 0; i < size; i++) {
            LValue a = args.get(i);
            if (a != RULES && a != INHERIT_FROM) {
                throw new LNonMatchingTypeException("");
            } else if (i + 1 >= size) {
                throw new LMissingArgumentsException(a.toString());
            }
            LValue v = args.get(++i);
            if (!(v instanceof LList)) {
                throw new LNonMatchingTypeException("");
            }
            LList lst = (LList) v;

            if (a == RULES) {
                addRules(rules, lst);
            } else if (a == INHERIT_FROM) {
                addInheritedRules(rules, lst);
            }
        }
        map.put(name, rules);

        return LList.NIL;
    }

}
