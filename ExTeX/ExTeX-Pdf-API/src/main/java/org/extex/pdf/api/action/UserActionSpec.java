/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.pdf.api.action;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class represents a user action of PDF.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class UserActionSpec extends ActionSpec {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Parse a user action spec.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param name the name of the primitive
     *
     * @return the action spec found
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static ActionSpec parseActionSpec(Context context,
            TokenSource source, Typesetter typesetter,
            CodeToken name) throws HelpingException, TypesetterException {

        String user = source.scanTokensAsString(context, name);
        return new UserActionSpec(user);
    }

    /**
     * The field {@code user} contains the name of the user.
     */
    private final String user;

    /**
     * Creates a new object.
     *
     * @param user the user
     */
    public UserActionSpec(String user) {

        this.user = user;
    }

    /**
     * Getter for user.
     *
     * @return the user
     */
    protected String getUser() {

        return this.user;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "user " + user;
    }

    /**
     * This method is the entry point for the visitor pattern.
     *
     * @param visitor the visitor to call back
     *
     * @return an arbitrary return object
     *
     * @see org.extex.pdf.api.action.ActionSpec#visit(
     *      org.extex.pdf.api.action.ActionVisitor)
     */
    @Override
    public Object visit(ActionVisitor visitor) {

        return visitor.visitUser(this);
    }

}
