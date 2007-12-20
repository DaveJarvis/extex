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

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LBoolean;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to parse a range.
 * 
 * <doc command="markup-range">
 * <h3>The Command <tt>markup-range</tt></h3>
 * 
 * <p>
 * The command <tt>markup-range</tt> can be used to specify the markup for
 * ranges.
 * </p>
 * 
 * <pre>
 *  (markup-range
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:sep <i>separator</i>]
 *     [:length <i>length</i>]
 *     [:ignore-end]
 *     [:class <i>class</i>]
 *  )
 * </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-range :open "\\begingroup " :close "\\endgroup ")
 * </pre>
 * 
 * TODO documentation incomplete
 * 
 * </doc>
 * 
 * <h3>Parameters</h3>
 * <p>
 * The parameters defined with this command are stored in the L system. If a
 * parameter is not given then a <code>nil</code> value is stored.
 * </p>
 * <p>
 * The following parameters are set:
 * </p>
 * <dl>
 * <dt>markup:range-<i>class</i>-open</dt>
 * <dd>...</dd>
 * <dt>markup:range-<i>class</i>-close</dt>
 * <dd>...</dd>
 * <dt>markup:range-<i>class</i>-sep</dt>
 * <dd>...</dd>
 * <dt>markup:range-<i>class</i>-length</dt>
 * <dd>...</dd>
 * <dt>markup:range-<i>class</i>-ignore-end</dt>
 * <dd>...</dd>
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupRange extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupRange(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_LSTRING(":open"),//
                Arg.OPT_LSTRING(":close"),//
                Arg.OPT_LSTRING(":sep"),//
                Arg.OPT_STRING(":class", ""),//
                Arg.OPT_LNUMBER(":length"),//
                Arg.OPT_LBOOLEAN(":ignore-end")});
    }

    /**
     * Take the markup for a range and store it.
     * 
     * @param interpreter the interpreter
     * @param open the open string
     * @param close the close string
     * @param sep the separator
     * @param clazz the class
     * @param length the length
     * @param ignoreEnd the indicator to ignore the end
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, LString open,
            LString close, LString sep, String clazz, LNumber length,
            LBoolean ignoreEnd) throws LSettingConstantException {

        interpreter.setq("markup:range-" + clazz + "-open", open);
        interpreter.setq("markup:range-" + clazz + "-close", close);
        interpreter.setq("markup:range-" + clazz + "-sep", sep);
        interpreter.setq("markup:range-" + clazz + "-length", length);
        interpreter.setq("markup:range-" + clazz + "-ignore-end", ignoreEnd);

        return null;
    }
}
