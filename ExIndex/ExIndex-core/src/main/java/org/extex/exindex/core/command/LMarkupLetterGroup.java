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

import org.extex.exindex.core.type.transform.Capitalize;
import org.extex.exindex.core.type.transform.Downcase;
import org.extex.exindex.core.type.transform.Transform;
import org.extex.exindex.core.type.transform.Upcase;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a rule set.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupLetterGroup extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupLetterGroup(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_STRING(":group", ""),//
                Arg.OPT_LSTRING(":open"),//
                Arg.OPT_LSTRING(":close"),//
                Arg.OPT_LSTRING(":open-head"),//
                Arg.OPT_LSTRING(":close-head"),//
                Arg.OPT_BOOLEAN(":upcase"),//
                Arg.OPT_BOOLEAN(":downcase"),//
                Arg.OPT_BOOLEAN(":capitalize")});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param group the name of the group
     * @param open the open string
     * @param close the close string
     * @param openHead
     * @param closeHead
     * @param upcase
     * @param downcase
     * @param capitalize
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, String group,
            LString open, LString close, LString openHead, LString closeHead,
            Boolean upcase, Boolean downcase, Boolean capitalize)
            throws LSettingConstantException {

        interpreter.setq("markup:letter-group-" + group + "-open", open);
        interpreter.setq("markup:letter-group-" + group + "-close", close);
        interpreter.setq("markup:letter-group-" + group + "-open-head",
            openHead);
        interpreter.setq("markup:letter-group-" + group + "-close-head",
            closeHead);
        if (1 < (upcase.booleanValue() ? 0 : 1)
                + (downcase.booleanValue() ? 0 : 1)
                + (capitalize.booleanValue() ? 0 : 1)) {
            // TODO gene: evaluate unimplemented
            throw new RuntimeException("unimplemented");
        }
        Transform transform;
        if (upcase.booleanValue()) {
            transform = new Upcase();
            interpreter.setq("markup:letter-group-" + group + "-transform",
                transform);
        } else if (downcase.booleanValue()) {
            transform = new Downcase();
            interpreter.setq("markup:letter-group-" + group + "-transform",
                transform);
        } else if (capitalize.booleanValue()) {
            transform = new Capitalize();
            interpreter.setq("markup:letter-group-" + group + "-transform",
                transform);
        }

        return null;
    }

}
