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
 * This is the adapter for the L system to define the markup for an attribute
 * group.
 * 
 * <doc command="markup-attribute-group">
 * <h3>The Command <tt>markup-attribute-group</tt></h3>
 * 
 * <p>
 * The command <tt>markup-attribute-group</tt> can be used to specify the
 * markup for attribute groups.
 * </p>
 * 
 * <pre>
 *  (markup-attribute-group
 *     [:open <i>open-markup</i>]
 *     [:close <i>close-markup</i>]
 *     [:group <i>level</i>]
 *  )   </pre>
 * 
 * <p>
 * The command has some optional arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (markup-attribute-group :open "\\begingroup " :close "\\endgroup ")   </pre>
 * 
 * TODO documentation incomplete
 * 
 * </doc>
 * 
 * <h3>Parameters</h3>
 * <p>
 * The parameters defined with this command are stored in the L system under the
 * key of the function name (i.e. <tt>markup-attribute-group</tt>).
 * </p>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMarkupAttributeGroup extends AbstractLAdapter {

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
    public LMarkupAttributeGroup(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, container, new Arg[]{Arg.OPT_STRING(":open", ""), //
                Arg.OPT_STRING(":close", ""), //
                Arg.OPT_STRING(":sep", ""), //
                Arg.OPT_NUMBER(":group", null)});
    }

    /**
     * Take the markup for an attribute group and store it.
     * 
     * @param interpreter the interpreter
     * @param open the open string
     * @param close the close string
     * @param sep the separator
     * @param group the group
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     * @throws LNonMatchingTypeException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, String open, String close,
            String sep, Integer group)
            throws LSettingConstantException,
                LNonMatchingTypeException {

        getMarkup(interpreter).set(group == null ? null : group.toString(),
            open, close, sep);

        return null;
    }

}
