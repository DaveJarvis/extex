/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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
import org.extex.pdf.api.exception.InterpreterPdftexActionTypeException;
import org.extex.pdf.api.exception.InterpreterPdftexIdentifierTypeException;
import org.extex.pdf.api.id.NameIdSpec;
import org.extex.pdf.api.id.NumIdSpec;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an abstract base for goto actions in PDF.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4409 $
 */
public abstract class GotoActionSpec extends ActionSpec {

    /**
     * Parse a goto action spec.
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
            String name) throws HelpingException, TypesetterException {

        if (source.getKeyword(context, "num")) {
            long num = source.parseNumber(context, source, typesetter);
            return new GotoIdActionSpec(null, new NumIdSpec(num), null);

        } else if (source.getKeyword(context, "name")) {
            String id = source.scanTokensAsString(context, name);
            return new GotoIdActionSpec(null, new NameIdSpec(id), null);

        } else if (source.getKeyword(context, "page")) {
            long page = source.parseNumber(context, source, typesetter);
            String text = source.scanTokensAsString(context, name);
            return new GotoPageActionSpec(null, page, text, null);

        } else if (!source.getKeyword(context, "file")) {
            throw new InterpreterPdftexIdentifierTypeException(name);
        }
        String file = source.scanTokensAsString(context, name);

        if (source.getKeyword(context, "name")) {
            String id = source.scanTokensAsString(context, name);
            Boolean newWindow = null;
            if (source.getKeyword(context, "newwindow")) {
                newWindow = Boolean.TRUE;
            } else if (source.getKeyword(context, "newwindow")) {
                newWindow = Boolean.FALSE;
            }
            return new GotoIdActionSpec(file, new NameIdSpec(id), newWindow);

        } else if (source.getKeyword(context, "page")) {
            long page = source.parseNumber(context, source, typesetter);
            String text = source.scanTokensAsString(context, name);
            Boolean newWindow = null;
            if (source.getKeyword(context, "newwindow")) {
                newWindow = Boolean.TRUE;
            } else if (source.getKeyword(context, "newwindow")) {
                newWindow = Boolean.FALSE;
            }
            return new GotoPageActionSpec(file, page, text, newWindow);
        }

        throw new InterpreterPdftexActionTypeException(name);
    }

}
