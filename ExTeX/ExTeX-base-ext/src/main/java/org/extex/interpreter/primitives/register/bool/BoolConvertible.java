/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.interpreter.primitives.register.bool;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This is an interface which describes the feature to be convertibe into a
 * bool.
 * 
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
*/
public interface BoolConvertible {

    /**
     * Convert to a bool.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter The typesetter
     * @return the converted value
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Bool convertBool(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException;
}
