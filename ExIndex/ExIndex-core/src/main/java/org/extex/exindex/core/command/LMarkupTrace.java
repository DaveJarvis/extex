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
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LBoolean;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to trace the markup.
 * 
 * <doc command="markup-trace">
 * <h3>The Command <tt>markup-trace</tt></h3>
 * 
 * <p>
 * The command <tt>markup-trace</tt> can be used to control the tracing of the
 * markup.
 * </p>
 * 
 * <pre>
 *  (markup-trace
 *     [:on]
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-trace :on)   </pre>
 * 
 * <p>
 * The tracing is initially off. With the argument <tt>:on</tt> it can be
 * turned on. When the tracing is on the description of the markup is printed on
 * the output stream in addition to the contents of the structured index.
 * </p>
 * 
 * <pre>
 *  (markup-trace :on :open ">" :close "<")   </pre>
 * 
 * <p>
 * The arguments <tt>:open</tt> and <tt>:close</tt> can be used to specify
 * the strings which should precede and follow the symbolic name. The default
 * for <tt>:open</tt> is <tt>&lt;</tt> and the default for <tt>:close</tt>
 * is <tt>&gt;</tt>.
 * </p>
 * 
 * <pre>
 *  (markup-trace :on :open "%  <" :close ">~n")   </pre>
 * 
 * <p>
 * To neutralize the effect on the processed output <tt>:open</tt> and
 * <tt>:close</tt> can be defined to include the tracing information as
 * comment into the generated sources of the structured index.
 * </p>
 * 
 * <p>
 * The result of tracing may look like following example:
 * </p>
 * 
 * <pre>
 * <INDEX:OPEN>
 *   <LETTER-GROUP-LIST:OPEN>
 *   &hellip;
 *   <LETTER-GROUP-LIST:CLOSE>
 * <INDEX:CLOSE>   </pre>
 * 
 * </doc>
 * 
 * <h3>Parameters</h3>
 * <p>
 * The parameters defined with this command are stored in the L system.
 * </p>
 * <p>
 * The following parameters are set:
 * </p>
 * <dl>
 * <dt>markup-trace</dt>
 * <dd>The markup strings.</dd>
 * <dt>markup:trace</dt>
 * <dd>The trace indicator.</dd>
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupTrace extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupTrace(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.OPT_BOOLEAN(":on"), //
                Arg.OPT_STRING(":open", "<"), //
                Arg.OPT_STRING(":close", ">\n")});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param on the indicator for tracing
     * @param open the opening markup
     * @param close the closing markup
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, Boolean on, String open,
            String close) throws LSettingConstantException {

        interpreter.setq("markup:trace", LBoolean.valueOf(on.booleanValue()));
        LMarkup m = (LMarkup) interpreter.get("markup-trace");

        m.setDefault(open, close);

        return null;
    }

}
