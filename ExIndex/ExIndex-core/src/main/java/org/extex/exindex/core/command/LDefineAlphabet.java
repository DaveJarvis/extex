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

import org.extex.exindex.core.type.alphabet.AlphabetContainer;
import org.extex.exindex.core.type.alphabet.ListAlphabet;
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
 * <doc type="exindex-command" command="define-alphabet"> <h3>The Command
 * <tt>define-alphabet</tt></h3>
 * 
 * <p>
 * The command <tt>define-alphabet</tt> can be used to define an alphabet.
 * </p>
 * 
 * <pre>
 *  (define-alphabet
 *     <i>alphabet-name</i>
 *     <i>string-list</i>
 *  )  </pre>
 * 
 * <p>
 * The command has as first argument the name of the alphabet. This argument is
 * a string. The second argument is a list of strings. The elements of the
 * <i>string-list</i> are the letters of the alphabet.
 * </p>
 * 
 * <pre>
 *  (define-alphabet "Pentateuch" ("gen" "ex" "lev" "num" "dtn"))   </pre>
 * 
 * <p>
 * The example above defines a new alphabet with the five letters.
 * </p>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineAlphabet extends LFunction {

    /**
     * The field <tt>container</tt> contains the container for alphabets.
     */
    private final AlphabetContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container for alphabets
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineAlphabet(String name, AlphabetContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, Arg.QLIST});
        this.container = container;
    }

    /**
     * Take an alphabet and store it.
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

        String[] words = new String[list.size()];
        int i = 0;

        for (LValue val : list) {
            words[i++] = LString.stringValue(val);
        }

        container.addAlphabet(name, new ListAlphabet(words));
        return null;
    }

}
