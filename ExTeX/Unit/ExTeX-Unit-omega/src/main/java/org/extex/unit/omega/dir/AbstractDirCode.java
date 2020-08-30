/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.dir;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.Direction;
import org.extex.typesetter.tc.Direction.Dir;

/**
 * This is the abstract base class for primitives acquiring a direction.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AbstractDirCode extends AbstractCode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     *
     * @param token the initial token for the primitive
     */
    public AbstractDirCode(CodeToken token) {

        super(token);
    }

    /**
     * Scan a direction specification.
     *
     * @param source the source for new tokens
     * @param context the interpreter context
     *
     * @return the direction or null
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected Direction scanDir(TokenSource source, Context context)
            throws HelpingException, TypesetterException {

        Dir a = scan(source, context);
        if (a == null) {
            return null;
        }
        Dir b = scan(source, context);
        if (b == null) {
            return null;
        }
        Dir c = scan(source, context);
        if (c == null) {
            return null;
        }

        return new Direction(a, b, c);
    }

    /**
     * Scan a direction component specification.
     *
     * @param source the source for new tokens
     * @param context the interpreter context
     *
     * @return the direction found
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private Dir scan(TokenSource source, Context context)
            throws HelpingException, TypesetterException {

        Token t = source.scanToken(context);

        if (t == null) {
            throw new EofException();
        } else if (t.eq(Catcode.LETTER, 'L')) {
            return Direction.Dir.L;
        } else if (t.eq(Catcode.LETTER, 'R')) {
            return Direction.Dir.R;
        } else if (t.eq(Catcode.LETTER, 'T')) {
            return Direction.Dir.T;
        } else if (t.eq(Catcode.LETTER, 'B')) {
            return Direction.Dir.B;
        }

        source.push(t);
        throw new BadDirectionException();
    }

}
