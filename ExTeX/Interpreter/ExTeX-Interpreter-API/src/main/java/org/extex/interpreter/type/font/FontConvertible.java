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

package org.extex.interpreter.type.font;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This is an interface which describes the feature to be convertible into a
 * font.
 * 
 * <p>
 * A font specification can either be a font stored in a font register or one of
 * the fonts used for math typesetting.
 * </p>
 * <p>
 * The formal description is the following:
 * </p>
 *
 * <pre class="syntax">
 *    &lang;font&rang;
 *        &rarr;  &lang;loaded font&rang;
 *         |   {@code \textfont} {@linkplain
 *             org.extex.interpreter.parser.CountParser#parseNumber(Context,TokenSource,Typesetter)
 *             &lang;8-bit&nbsp;number&rang;}
 *         |   {@code \scriptfont} {@linkplain
 *             org.extex.interpreter.parser.CountParser#parseNumber(Context,TokenSource,Typesetter)
 *             &lang;8-bit&nbsp;number&rang;}
 *         |   {@code \scriptscriptfont} {@linkplain
 *             org.extex.interpreter.parser.CountParser#parseNumber(Context,TokenSource,Typesetter)
 *             &lang;8-bit&nbsp;number&rang;}
 *         |   {@code \font}
 *  </pre>
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface FontConvertible {

    /**
     * Convert some primitive value into a font.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the converted value
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    Font convertFont(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException;

}
