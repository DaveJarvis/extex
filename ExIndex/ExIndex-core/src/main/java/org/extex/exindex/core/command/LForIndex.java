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

import org.extex.exindex.core.exception.UnknownIndexException;
import org.extex.exindex.core.type.IndexContainer;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to restrict following commands to a
 * certain index.
 * 
 * <doc command="for-index">
 * <h3>The Command <tt>for-index</tt></h3>
 * 
 * <p>
 * The command <tt>for-index</tt> can be used to restrict the scope of the
 * following commands to a certain index.
 * </p>
 * 
 * <pre>
 *  (for-index
 *     <i>index-name</i>
 *  )  </pre>
 * 
 * <p>
 * The command has one argument which is the name of the index. The commands
 * sensitive to an index take this value to determine the index. These are
 * especially the markup commands.
 * </p>
 * <p>
 * The default index is represented by the empty string ("").
 * </p>
 * 
 * <pre>
 *  (for-index "")
 *  (markup-index :open "\begin{theindex}~n" :close "\end{theindex}~")
 *  (for-index "glossary")
 *  (markup-index :open "\begin{theglossary}~n" :close "\end{theglossary}~")  </pre>
 * 
 * <p>
 * The example above sets the markup for the index for two index names.
 * </p>
 * 
 * </doc>
 * 
 * 
 * <h3>Parameters</h3>
 * <p>
 * The name of the index currently in use is stored in the L system under the
 * key <tt>index;name</tt>.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6728 $
 */
public class LForIndex extends AbstractLAdapter {

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
    public LForIndex(String name, IndexContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, container, new Arg[]{Arg.LSTRING});
    }

    /**
     * Take a sort rule and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name of the alphabet
     * 
     * @return <tt>nil</tt>
     * 
     * @throws UnknownIndexException in case of an error
     */
    public LValue evaluate(LInterpreter interpreter, LString name)
            throws UnknownIndexException {

        String key = name == null ? null : name.getValue();
        if (!setCurrentIndex(key)) {
            throw new UnknownIndexException(null, key);
        }
        return null;
    }
}
