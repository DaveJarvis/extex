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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define an alphabet.
 * 
 * It also serves as a container for the alphabets collected.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineAlphabet extends LFunction {

    /**
     * The field <tt>map</tt> contains the alphabets.
     */
    private Map<String, List<String>> map = new HashMap<String, List<String>>();

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineAlphabet(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.QLIST});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the alphabet
     * @param list the list of symbols
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LNonMatchingTypeException in case an argument is not a String
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, String name, LList list)
            throws LNonMatchingTypeException,
                LSettingConstantException {

        List<String> words = new ArrayList<String>();

        for (LValue val : list) {
            words.add(LString.getString(val));
        }

        map.put(name, words);

        return LList.NIL;
    }

    /**
     * Getter for a named alphabet.
     * 
     * @param name the name of the alphabet
     * 
     * @return the alphabet or <code>null</code> if the name is not associated
     *         with an alphabet
     */
    public List<String> lookup(String name) {

        return map.get(name);
    }

}
