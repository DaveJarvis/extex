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
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define attributes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineAttributes extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineAttributes(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QLIST});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param list
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LNonMatchingTypeException
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, LList list)
            throws LNonMatchingTypeException,
                LSettingConstantException {

        LList attributeGroups = new LList();
        for (LValue val : list) {
            if (val instanceof LString) {
                LList ag = new LList();
                ag.add(val);
                attributeGroups.add(ag);
            } else if (val instanceof LList) {
                for (LValue v : (LList) val) {
                    LString.getString(v); // check the type
                }
                attributeGroups.add(val);
            } else {
                throw new LNonMatchingTypeException("");
            }
        }

        interpreter.setq("attributes", attributeGroups);

        return null;
    }

}
