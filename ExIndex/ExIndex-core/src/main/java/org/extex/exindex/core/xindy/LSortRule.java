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

import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.type.rules.RegexRule;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a merge rule.
 * 
 * <doc command="sort-rule">
 * <h3>The Command <tt>sort-rule</tt></h3>
 * 
 * <p>
 * The command <tt>sort-rule</tt> can be used to add a sort rule.
 * </p>
 * 
 * <pre>
 *  (sort-rule pattern replacement
 *     [:again]
 *     [:run <i>level</i>]
 *  )
 * </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (sort-rule "abc" "ABC")
 * </pre>
 * 
 * TODO documentation incomplete
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LSortRule extends LFunction {

    /**
     * The field <tt>rules</tt> contains the list of rules to apply.
     */
    private List<List<Rule>> rules = new ArrayList<List<Rule>>();

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LSortRule(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.STRING,
                Arg.OPT_BOOLEAN(":again"), //
                Arg.OPT_NUMBER(":run")});
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param in the input string
     * 
     * @return the result
     */
    public String apply(String in) {

        // TODO gene: enclosing_method unimplemented

        return in;
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param pattern the pattern
     * @param replacement the replacement text
     * @param again the optional indicator to restart the replacement cycle from
     *        start
     * @param run the run number to add this rule to; it defaults to 0 if not
     *        given
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, String pattern,
            String replacement, Boolean again, Long run)
            throws LSettingConstantException {

        int level = (run == null ? 0 : run.intValue());
        Rule rule = new RegexRule(pattern, replacement, again.booleanValue());
        for (int i = rules.size(); i < level; i++) {
            rules.add(null);
        }
        List<Rule> list = rules.get(level);
        if (list == null) {
            list = new ArrayList<Rule>();
            rules.add(level, list);
        }
        list.add(rule);
        return null;
    }
}
