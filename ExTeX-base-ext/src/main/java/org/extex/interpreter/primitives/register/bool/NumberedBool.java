/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\bool</code>.
 * It sets the named bool register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <p>
 * All features are inherited from
 * {@link org.extex.interpreter.primitives.register.bool.NamedBool NamedBool}.
 * Just the key has to be provided under which this Bool has to be stored. This
 * key is constructed from the name, a hash mark and the running number.
 * </p>
 * 
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \bool12=true
 * </pre>
 * 
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class NumberedBool extends NamedBool {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public NumberedBool(CodeToken token) {

        super(token, null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.primitives.register.bool.NamedBool#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return getName()
                + "#"
                + Long.toString(source
                    .parseInteger(context, source, typesetter));
    }
}
