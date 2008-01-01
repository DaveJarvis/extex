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
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define an index.
 * 
 * <doc command="define-index">
 * <h3>The Command <tt>define-index</tt></h3>
 * 
 * <p>
 * The command <tt>define-index</tt> can be used to define an index. Initially
 * only the default index with the name "" exists. Any entries directed to
 * another index are silently discarded.
 * </p>
 * 
 * <pre>
 *  (define-index
 *     <i>index-name</i>
 *     [:drop]
 *  )  </pre>
 * 
 * <p>
 * The command has as first argument the name of the index. The default index is
 * represented by the empty string ("").
 * </p>
 * 
 * <pre>
 *  (define-index "" :drop)  </pre>
 * 
 * <p>
 * The example above drops the default index. This might be useful when the
 * default index is not used for writing but just as a container for the
 * fallback parameters.
 * </p>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6728 $
 */
public class LDefineIndex extends AbstractLAdapter {

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
    public LDefineIndex(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, container, new Arg[]{Arg.LSTRING, Arg.OPT_BOOLEAN(":drop")});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the index
     * @param drop the indicator to drop the index
     * 
     * @return <tt>nil</tt>
     * 
     * @throws LNonMatchingTypeException in case an argument is not a String
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, LString name, Boolean drop)
            throws LNonMatchingTypeException,
                LSettingConstantException {

        StructuredIndex index = getContainer().defineIndex(name.getValue());
        index.setDropped(drop == Boolean.TRUE);

        return null;
    }

}
