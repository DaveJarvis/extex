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
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LString;
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
 *  )
 * </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-keyword-list :open "\\begingroup " :close "\\endgroup ")
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
 * <dt>markup:keyword-list-<i>class</i>-open</dt>
 * <dd>...</dd>
 * <dt>markup:keyword-list-<i>class</i>-close</dt>
 * <dd>...</dd>
 * <dt>markup:keyword-list-<i>class</i>-sep</dt>
 * <dd>...</dd>
 * </dl>
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

        super(name, new Arg[]{Arg.OPT_LSTRING(":open"),//
                Arg.OPT_LSTRING(":close"),//
                Arg.OPT_LSTRING(":sep"),//
                Arg.OPT_NUMBER(":depth")});
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
     */
    public LValue evaluate(LInterpreter interpreter, LString open,
            LString close, LString sep, Long depth)
            throws LSettingConstantException {

        String d = depth.toString();
        interpreter.setq("markup:keyword-list-" + d + "-open", open);
        interpreter.setq("markup:keyword-list-" + d + "-close", close);
        interpreter.setq("markup:keyword-list-" + d + "-sep", sep);

        return null;
    }

}
