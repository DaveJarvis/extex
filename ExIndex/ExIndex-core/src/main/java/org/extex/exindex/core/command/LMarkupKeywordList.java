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

import org.extex.exindex.core.command.type.LMarkup;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a keyword list.
 * 
 * <doc command="markup-keyword-list">
 * <h3>The Command <tt>markup-keyword-list</tt></h3>
 * 
 * <p>
 * The command <tt>markup-keyword-list</tt> can be used to specify the markup
 * for keyword lists.
 * </p>
 * 
 * <pre>
 *  (markup-keyword-list
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:sep <i>separator</i>]
 *     [:class <i>class</i>]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-keyword-list :open "\\begingroup " :close "\\endgroup ")  </pre>
 * 
 * TODO documentation incomplete
 * 
 * </doc>
 * 
 * <h3>Parameters</h3>
 * <p>
 * The parameters defined with this command are stored in the L system under the
 * key of the function name (i.e. <tt>markup-keyword-list</tt>).
 * </p>
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupKeywordList extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupKeywordList(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_STRING(":open", ""), //
                Arg.OPT_STRING(":close", ""), //
                Arg.OPT_STRING(":sep", ""), //
                Arg.OPT_NUMBER(":depth", Long.valueOf(0))});
    }

    /**
     * Take the markup for a keyword list and store it.
     * 
     * @param interpreter the interpreter
     * @param open the open string
     * @param close the close string
     * @param sep the separator
     * @param depth the depth
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String open, String close,
            String sep, Long depth)
            throws LSettingConstantException,
                LNonMatchingTypeException {

        LValue container = interpreter.get(getName());
        if (!(container instanceof LMarkup)) {
            throw new LNonMatchingTypeException(null);
        }

        String clazz = (depth == null ? null : depth.toString());
        ((LMarkup) container).set(clazz, open, close, sep);

        return null;
    }

}
