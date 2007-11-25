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

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a rule set.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupIndex extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupIndex(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_LSTRING(":open"),//
                Arg.OPT_LSTRING(":close"),//
                Arg.OPT_BOOLEAN(":flat"), //
                Arg.OPT_BOOLEAN(":tree"), //
                Arg.OPT_LNUMBER(":hierdepth")});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param open
     * @param close
     * @param flat
     * @param tree
     * @param hierdepth
     * 
     * @return <tt>nil</tt>
     */
    public LValue evaluate(LInterpreter interpreter, LString open,
            LString close, Boolean flat, Boolean tree, LNumber hierdepth) {

        if (flat.booleanValue()) {
            if (tree.booleanValue() || hierdepth.getValue() != 0) {
                // TODO gene: evaluate unimplemented
                throw new RuntimeException("unimplemented");
            }
            interpreter.setq("markup:index-hierdepth", new LNumber(0));
        } else if (tree.booleanValue()) {
            if (flat.booleanValue() || hierdepth.getValue() != 0) {
                // TODO gene: evaluate unimplemented
                throw new RuntimeException("unimplemented");
            }
            interpreter.setq("markup:index-hierdepth", new LNumber(
                Long.MAX_VALUE));
        } else {
            interpreter.setq("markup:index-hierdepth", hierdepth);
        }
        interpreter.setq("markup:index-open", open);
        interpreter.setq("markup:index-close", close);

        return LList.NIL;
    }

}
