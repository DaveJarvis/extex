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

import org.extex.exindex.core.type.rules.RegexRule;
import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a merge rule.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LSortRule extends LFunction {

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
     * @param in
     * @return
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
     * @param run
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, String pattern,
            String replacement, Boolean again, Long run)
            throws LSettingConstantException {

        LSymbol symbol = LSymbol.get("sort-rules-" + run.toString());
        LValue sr = interpreter.get(symbol);
        LList sortRules;
        if (sr == null) {
            sortRules = new LList();
            interpreter.setq(symbol, sortRules);
        } else {
            sortRules = (LList) sr;
        }
        Rule rule = new RegexRule(pattern, replacement, again.booleanValue());

        // sortRules.add(rule);
        return null;
    }

}
