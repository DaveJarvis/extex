/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.register.transform;

import org.extex.core.count.CountParser;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;


/**
 * This class provides an implementation for the primitive <code>\transformdef</code>.
 *
 * <p>Example</p>
 * <pre>
 * \transformdef\hugo=7
 * </pre>
 *
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class TransformDef extends AbstractAssignment {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * @param name the name for debugging
     */
    public TransformDef(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        //  \transformdef\hugo=7
        CodeToken tok = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        String key = "transform#"
                + Long.toString(CountParser.scanInteger(context, source, typesetter));
        context.setCode(tok, new NamedTransform(key), prefix.isGlobal());
        prefix.clearGlobal();
    }
}