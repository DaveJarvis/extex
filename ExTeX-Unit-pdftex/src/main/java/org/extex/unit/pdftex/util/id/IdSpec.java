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

package org.extex.unit.pdftex.util.id;

import java.io.Serializable;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.CountParser;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.pdftex.exception.InterpreterPdftexIdentifierTypeException;

/**
 * This is the abstract base class for ids.
 * An id can either be a number or a name.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4409 $
 */
public abstract class IdSpec implements Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * This method parses an id spec.
     *
     * @param source the source for new tokens
     * @param context the interpreter context
     * @param typesetter the typesetter
     * @param name the name of the current primitive
     *
     * @return the id instance
     *
     * @throws HelpingException in case of an parse error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static IdSpec parseIdSpec(Context context,
            TokenSource source, Typesetter typesetter, String name)
            throws HelpingException, TypesetterException {

        if (source.getKeyword(context, "num")) {
            long num = CountParser.scanNumber(context, source, typesetter);
            return new NumIdSpec(num);
        } else if (source.getKeyword(context, "name")) {
            String id = source.scanTokensAsString(context, name);
            return new NameIdSpec(id);
        } else {
            throw new InterpreterPdftexIdentifierTypeException(name);
        }
    }

    /**
     * Creates a new object.
     */
    protected IdSpec() {

        super();
    }

}
