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
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define a letter group.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineLetterGroup extends LFunction {

    /**
     * The field <tt>container</tt> contains the ...
     */
    private LetterGroupContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineLetterGroup(String name, LetterGroupContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.OPT_STRING(":before", null),
                Arg.OPT_STRING(":after", null), Arg.OPT_LIST(":prefixes")});
        this.container = container;
    }

    /**
     * Take a letter group and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the letter group and its main prefix
     * @param before optionally the name of a letter group going before this one
     * @param after optionally the name of a letter group going after this one
     * @param prefixes
     * 
     * @return <tt>null</tt>
     * 
     * @throws LException in case of an error
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, String name,
            String before, String after, LList prefixes)
            throws LException,
                LSettingConstantException {

        LetterGroup g = null;

        if (prefixes == null) {
            g = container.defineLetterGroup(name);
        } else {
            g = container.linkPrefixes(name, prefixes);
        }

        if (after != null) {
            LetterGroup ag = container.defineLetterGroup(after);
            g.after(ag);
        }
        if (before != null) {
            LetterGroup bg = container.defineLetterGroup(before);
            bg.after(g);
        }

        return null;
    }

}
