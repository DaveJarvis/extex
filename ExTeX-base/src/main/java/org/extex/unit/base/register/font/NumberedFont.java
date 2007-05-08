/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.base.register.font;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.CountParser;
import org.extex.scanner.type.Namespace;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for a font stored under a name and a
 * number in the context.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class NumberedFont extends NamedFont {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public NumberedFont(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.base.register.font.NamedFont#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String theName = getName();
        String theNumber =
                Long.toString(CountParser.scanNumber(context, source,
                    typesetter));
        return key(context, theName, theNumber);
    }

    /**
     * Construct the reference key for a numbered font.
     * 
     * @param context the interpreter context
     * @param theName the base name of the font
     * @param theNumber the number of the font
     * 
     * @return the key
     */
    public static String key(Context context, String theName, String theNumber) {

        if (Namespace.SUPPORT_NAMESPACE_FONT) {
            return context.getNamespace() + "\b" + theName + "#" + theNumber;
        }
        return theName + "#" + theNumber;
    }

}
