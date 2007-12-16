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

package org.extex.exindex.core.parser;

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.function.Arg;
import org.extex.exindex.lisp.type.function.LFunction;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LIndexentry extends LFunction {

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * 
     * @throws NoSuchMethodException in case that no method corresponding to the
     *         argument specification could be found
     * @throws SecurityException in case a security problem occurred
     */
    public LIndexentry(String name)
            throws SecurityException,
                NoSuchMethodException {

        super(name, new Arg[]{//
            Arg.OPT_QSTRING_LIST(":key"),//
                    Arg.OPT_QSTRING_LIST(":print"),//
                    Arg.OPT_QSTRING_LIST(":tkey"),//
                    Arg.OPT_STRING(":attr"),//
                    Arg.OPT_STRING(":locref"),//
                    Arg.OPT_BOOLEAN(":open-range"),//
                    Arg.OPT_BOOLEAN(":close-range"),//
                    Arg.OPT_QSTRING_LIST(":xref")//
            });
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param interpreter
     * @param key
     * @param print
     * @param tkey
     * @param attr
     * @param locref
     * @param openRange
     * @param closeRange
     * @param xref
     * 
     * @return <code>null</code>
     */
    public LValue evaluate(LInterpreter interpreter, LList key, LList print,
            LList tkey, String attr, String locref, Boolean openRange,
            Boolean closeRange, LList xref) {

        return null;
    }

}
