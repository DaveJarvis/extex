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

import org.extex.exindex.core.finder.SearchPath;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.resource.ResourceFinder;

/**
 * This is the adapter for the L system to parse a merge rule.
 * 
 * <p>The Command {@code searchpath}</p>
 * 
 * <p>
 * The command {@code searchpath} can be used to manipulate the search path.
 * </p>
 * 
 * <pre>
 *  (searchpath 
 *     [<i>path</i> | <i>path-list</i>]
 *  )
 * </pre>
 * 
 * <p>
 * The command comes in two variant which are described in turn.
 * </p>
 * 
 * <pre>
 *  (searchpath ".:/usr/local/lib/texmf/xindy:")
 * </pre>
 *
 * <br>
 * 
 * <pre>
 *  (searchpath ("." "/usr/local/lib/texmf/xindy" :default))
 * </pre>
 *
 * <br>
 *
 * <pre>
 *  (searchpath ("." :last "/usr/local/lib/texmf/xindy" :default))
 * </pre>
 * 
 * <p>
 * If a path element starts with a tilde (~) then this character is replaced by
 * the home directory of the current user. This is consistent with tilde
 * expansion in some Unix shells.
 * </p>
 * 
 * TODO documentation incomplete
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LSearchpath extends LFunction {

    /**
     * The field {@code FLAG_DEFAULT} contains the flag :default.
     */
    private static final LSymbol FLAG_DEFAULT = LSymbol.get(":default");

    /**
     * The field {@code FLAG_LAST} contains the flag :last.
     */
    private static final LSymbol FLAG_LAST = LSymbol.get(":last");

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     * argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LSearchpath(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.QVALUE});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param value the value
     * 
     * @return {@code nil}
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, LValue value)
            throws LNonMatchingTypeException {

        if (value instanceof LString) {
            store(interpreter, ((LString) value).getValue());
        } else if (value instanceof LList) {
            store(interpreter, ((LList) value));
        } else {
            throw new LNonMatchingTypeException("");
        }

        return LList.NIL;
    }

    /**
     * Get the last items on the search path.
     * 
     * @param interpreter the interpreter
     * 
     * @return the list of registered paths
     */
    private String[] getLast(LInterpreter interpreter) {

        ResourceFinder finder = interpreter.getResourceFinder();
        if (finder instanceof SearchPath) {
            return ((SearchPath) finder).get();
        }
        return new String[0];
    }

    /**
     * Store the new search path.
     * 
     * @param interpreter the interpreter
     * @param list the list of items
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    private void store(LInterpreter interpreter, LList list)
            throws LNonMatchingTypeException {

        String[] items = new String[list.size()];
        int i = 0;

        for (LValue val : list) {
            if (val instanceof LString) {
                items[i++] = ((LString) val).getValue();

            } else if (val instanceof LSymbol) {
                if (val == FLAG_DEFAULT) {
                    items[i++] = null;
                } else if (val == FLAG_LAST) {
                    String[] last = getLast(interpreter);
                    String[] a = new String[items.length + last.length];
                    for (int j = 0; j < i; j++) {
                        a[j] = items[j];
                    }
                    for (int j = 0; j < last.length; j++) {
                        a[i++] = last[j];
                    }
                    items = a;
                } else {
                    throw new LNonMatchingTypeException("???");
                }
            } else {
                throw new LNonMatchingTypeException("???");
            }
        }

        ResourceFinder finder = interpreter.getResourceFinder();
        if (finder instanceof SearchPath) {
            ((SearchPath) finder).set(items);
        } else {
            SearchPath f = new SearchPath(finder);
            f.set(items);
            interpreter.setResourceFinder(f);
        }
    }

    /**
     * Store the new search path.
     * 
     * @param interpreter the interpreter
     * @param path the list of items for the search path
     */
    private void store(LInterpreter interpreter, String path) {

        ResourceFinder finder = interpreter.getResourceFinder();
        if (finder instanceof SearchPath) {
            ((SearchPath) finder).set(path);
        } else {
            SearchPath f = new SearchPath(finder);
            f.set(path);
            interpreter.setResourceFinder(f);
        }
    }

}
