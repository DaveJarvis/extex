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

import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.exception.InconsistentFlagsException;
import org.extex.exindex.core.type.rules.RegexRule;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.core.type.rules.RuleSetContainer;
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
 * <doc type="exindex-command" command="define-rule-set">
 * 
 * <h3>The Command <tt>define-rule-set</tt></h3>
 * 
 * <p>
 * The command <tt>define-rule-set</tt> can be used to define a rule set.
 * </p>
 * 
 * <pre>
 *  (define-rule-set <i>rule-set-name</i>
 *     <i>rule-set-specifications</i>
 *  )   </pre>
 * 
 * <p>
 * The command takes as first argument the name of the rule set. The following
 * arguments specify how the contents of the rule set is constructed.
 * </p>
 * 
 * <pre>
 *  (define-rule-set "empty")   </pre>
 * 
 * <p>
 * The example above shows the simplest case of an empty rule set. This might
 * not be very helpful.
 * </p>
 * 
 * <pre>
 *  :inherit-from <i>rule-name-list</i>   </pre>
 * 
 * <p>
 * The specification of a rule set can be contain an inherit instruction. This
 * instruction starts with the keyword <tt>:inherit-from</tt>. This keyword is
 * followed by a list of strings denoting rule set names. Those names need to be
 * defined. The definitions of those names are included at this place.
 * </p>
 * 
 * <pre>
 *  (define-rule-set "my-rule-set"
 *     :inherit-from ("abc"))   </pre>
 * 
 * <p>
 * The example shows the definition of a rule set <tt>my-rule-set</tt>. It
 * contains all rules from the rule set <tt>abc</tt>.
 * </p>
 * 
 * <pre>
 *  :rules <i>list-of-rules</i>   </pre>
 * 
 * <p>
 * The other possibility for a specification is an explicit rule set. It is
 * stared with the keyword <tt>:rules</tt>. This keyword is followed by a list
 * containing the rules.
 * </p>
 * 
 * <pre>
 *  (<i>pattern</i> <i>replacement-text</i>
 *    [:string | :bregex | :eregex]
 *    [:again]
 *  )   </pre>
 * 
 * <p>
 * Any rule is a list consisting of a pattern, a replacement text, and some more
 * optional arguments.
 * </p>
 * 
 * <pre>
 *  (define-rule-set "my-rule-set"
 *     :rules (("abc" "def" )))   </pre>
 * 
 * <p>
 * </p>
 * 
 * <pre>
 *  (define-rule-set "my-rule-set"
 *     :rules (("abcd" "defd" ))
 *     :inherit-from ("din5007")
 *     :rules (("abc" "def" )))   </pre>
 * 
 * <p>
 * </p>
 * 
 * TODO documentation incomplete
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineRuleSet extends LFunction {

    /**
     * The field <tt>RULES</tt> contains the symbol <tt>:rules</tt>.
     */
    private static final LSymbol RULES = LSymbol.get(":rules");

    /**
     * The field <tt>INHERIT_FROM</tt> contains the symbol
     * <tt>:inherit-from</tt>.
     */
    private static final LSymbol INHERIT_FROM = LSymbol.get(":inherit-from");

    /**
     * The field <tt>AGAIN</tt> contains the symbol <tt>:again</tt>.
     */
    private static final LSymbol AGAIN = LSymbol.get(":again");

    /**
     * The field <tt>STRING</tt> contains the symbol <tt>:string</tt>.
     */
    private static final LSymbol STRING = LSymbol.get(":string");

    /**
     * The field <tt>BREGEX</tt> contains the symbol <tt>:bregexp</tt>.
     */
    private static final LSymbol BREGEX = LSymbol.get(":bregexp");

    /**
     * The field <tt>EREGEX</tt> contains the symbol <tt>:eregexp</tt>.
     */
    private static final LSymbol EREGEX = LSymbol.get(":eregexp");

    /**
     * The field <tt>container</tt> contains the reference to the rule set
     * container to store information in.
     */
    private final RuleSetContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container for indices
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineRuleSet(String name, RuleSetContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.VARARG()});
        this.container = container;
    }

    /**
     * Add rules from a inheritance list to a rule set.
     * 
     * @param rules the target rule set
     * @param lst the source list
     * 
     * @throws LException in case of an error
     */
    private void addInheritedRules(List<Rule> rules, LList lst)
            throws LException {

        for (LValue value : lst) {
            String s = LString.stringValue(value);
            List<Rule> rl = container.lookupRule(s);
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
     * Parse rules and add them to a rule set.
     * 
     * @param rules the target rule set
     * @param args the source
     * 
     * @throws LNonMatchingTypeException in case of an error
     * @throws InconsistentFlagsException in case of an error
     */
    private void addRules(List<Rule> rules, LList args)
            throws LNonMatchingTypeException,
                InconsistentFlagsException {

        int size = args.size();
        if (size < 2) {
            throw new LNonMatchingTypeException("");
        }
        boolean again = false;
        int type = 0;
        String pattern = LString.stringValue(args.get(0));
        String replacement = LString.stringValue(args.get(0));
        for (int i = 0; i < size; i++) {
            LValue value = args.get(i);
            if (value == AGAIN) {
                again = true;
            } else if (value == STRING) {
                type |= 1;
            } else if (value == BREGEX) {
                type |= 2;
            } else if (value == EREGEX) {
                type |= 4;
            } else {
                throw new LNonMatchingTypeException(value.toString());
            }
        }

        switch (type) {
            case 0:
                // default is :string
            case 1:
                rules.add(new StringRule(pattern, replacement, again));
                break;
            case 2:
                rules.add(new RegexRule(pattern, replacement, again));
                break;
            case 3:
                throw new InconsistentFlagsException(null, ":string",
                    ":bregexp");
            case 4:
                rules.add(new RegexRule(pattern, replacement, again));
                break;
            case 5:
                throw new InconsistentFlagsException(null, ":string",
                    ":eregexp");
            case 6:
            case 7:
                throw new InconsistentFlagsException(null, ":bregexp",
                    ":eregexp");
        }
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the rule set
     * @param args the arguments
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
        container.addRule(name, rules);

        return LList.NIL;
    }

}
