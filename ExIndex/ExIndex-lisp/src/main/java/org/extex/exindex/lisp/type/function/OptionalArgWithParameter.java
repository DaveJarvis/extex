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

package org.extex.exindex.lisp.type.function;

import java.util.List;

import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingParameterException;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OptionalArgWithParameter extends Arg {

    /**
     * Creates a new object.
     * 
     * @param flag the name of the flag
     * @param resultClass the result class
     * @param def the default value
     * @param quoted the indicator for quoted expressions
     */
    public OptionalArgWithParameter(String flag, Class<?> resultClass,
            Object def, boolean quoted) {

        super(flag, resultClass, def, quoted);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.function.Arg#parse( java.util.List, int,
     *      java.lang.Object[], int)
     */
    @Override
    public final int parse(List<LValue> args, int ai, Object[] arguments,
            int index) throws LException {

        if (ai >= args.size()) {
            throw new LMissingParameterException(getFlag());
        }
        arguments[index] = validate(args.get(ai));
        return ai + 1;
    }

}
