/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define the markup for an index entry.
 * 
 * <doc command="markup-index-entry">
 * <h3>The Command <tt>markup-index-entry</tt></h3>
 * 
 * <p>
 * The command <tt>markup-index-entry</tt> can be used to specify the markup
 * for index entries.
 * </p>
 * 
 * <pre>
 *  (markup-index-entry
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:depth <i>level</i>]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-index-entry :open "\\begingroup " :close "\\endgroup ")   </pre>
 * 
 * <p>
 * The arguments <tt>:open</tt> and <tt>:close</tt> take a parameter each.
 * They denote the opening and closing string for an index entry.
 * </p>
 * 
 * <pre>
 *  (markup-index-entry :close "\\par " :depth 2)   </pre>
 * 
 * <p>
 * The argument <tt>:depth</tt> specified for which depth the rule should be
 * applied. If no depth is given the markup is applied for all levels which do
 * not have a markup set. This means that such a command sets the default
 * values.
 * </p>
 * 
 * </doc>
 * 
 * <h3>Parameters</h3>
 * <p>
 * The parameters defined with this command are stored in the L system under the
 * key of the function name (i.e. <tt>markup-indexentry</tt>).
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupIndexEntry extends AbstractLAdapter {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the index container
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LMarkupIndexEntry(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, container, new Arg[]{Arg.OPT_STRING(":open", ""), //
                Arg.OPT_STRING(":close", ""), //
                Arg.OPT_NUMBER(":depth", null)});
    }

    /**
     * Take the markup for an index entry and store it.
     * 
     * @param interpreter the interpreter
     * @param open the open string
     * @param close the close string
     * @param depth the depth or null if none has been given
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String open, String close,
            Integer depth)
            throws LSettingConstantException,
                LNonMatchingTypeException {

        getMarkup(interpreter).set((depth == null ? null : depth.toString()),
            open, close);

        return null;
    }

}
