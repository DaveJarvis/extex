/*
 * Copyright (C) 2005-2009 The ExTeX Group and individual authors listed below
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

import java.io.Serializable;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.pdf.api.exception.PdftexActionTypeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This is the abstract base class for actions in PDF.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class ActionSpec implements Serializable {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * Parse an action spec.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param primitive the name of the primitive
     *
     * @return the action spec found
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static ActionSpec parseActionSpec(Context context,
            TokenSource source, Typesetter typesetter,
            CodeToken primitive) throws HelpingException, TypesetterException {

        if (source.getKeyword(context, "user")) {
            return UserActionSpec.parseActionSpec(context, source, typesetter,
                primitive);
        } else if (source.getKeyword(context, "goto")) {
            return GotoActionSpec.parseActionSpec(context, source, typesetter,
                primitive);
        } else if (source.getKeyword(context, "thread")) {
            return ThreadActionSpec.parseActionSpec(context, source,
                typesetter, primitive);
        }

        throw new PdftexActionTypeException(primitive.toText());
    }

    /**
     * This method is the entry point for the visitor pattern.
     *
     * @param visitor the visitor to call back
     *
     * @return an arbitrary return object
     */
    public abstract Object visit(ActionVisitor visitor);

}
