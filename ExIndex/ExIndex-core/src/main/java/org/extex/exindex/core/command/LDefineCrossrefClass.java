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

import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.alphabet.CrossreferenceLocationClass;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is the adapter for the L system to define a cross-reference class.
 * 
 * <doc command="define-crossref-class">
 * <h3>The Command <tt>define-crossref-class</tt></h3>
 * 
 * <p>
 * The command <tt>define-crossref-class</tt> can be used to define a
 * cross-reference class.
 * </p>
 * 
 * <pre>
 *  (define-crossref-class
 *     <i>crossref-class-name</i>
 *     [:unverified]
 *  )   </pre>
 * 
 * <p>
 * The command has some arguments which are described in turn.
 * </p>
 * 
 * <pre>
 *  (define-crossref-class "see")   </pre>
 * 
 * <p>
 * The mandatory argument is a string which is taken as the name of the
 * cross-reference group.
 * </p>
 * 
 * <pre>
 *  (define-crossref-class "see" :unverified)   </pre>
 * 
 * <p>
 * Usually the cross-references are checked to avoid dangling references. If an
 * undefined cross-reference is found a waring is issued. The cross-reference
 * class can be marked as unverified. In this case dangling references are
 * silently consumed.
 * </p>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineCrossrefClass extends LFunction {

    /**
     * The field <tt>container</tt> contains the container for classes.
     */
    private LocationClassContainer container;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param container the container for cross-reference classes
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LDefineCrossrefClass(String name, LocationClassContainer container)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{Arg.STRING, //
                Arg.OPT_BOOLEAN(":unverified", Boolean.FALSE)});
        this.container = container;
    }

    /**
     * Take a cross-reference group and store it.
     * 
     * @param interpreter the interpreter
     * @param name the name
     * @param unverified the indicator for unverified classes
     * 
     * @return <tt>null</tt>
     * 
     * @throws LSettingConstantException should not happen
     */
    public LValue evaluate(LInterpreter interpreter, String name,
            Boolean unverified) throws LSettingConstantException {

        container.addLocationClass(name, new CrossreferenceLocationClass(
            unverified == Boolean.TRUE));
        return null;
    }

}
