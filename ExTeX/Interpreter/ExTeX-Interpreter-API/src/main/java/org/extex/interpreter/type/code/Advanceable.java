/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.code;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This is a interface to mark those Classes which are able to advance
 * something.
 *
 *
 *
 * <p>Extending {@code \advance}</p>
 * <p>
 *  The primitive {@code \advance} is designed to be expanded. It is fairly
 *  simple to write an advancable primitive. The associated code simply has to
 *  implement the interface {@code Advanceable}. Whenever {@code \advance}
 *  is encountered immediately followed by a token which has the proper code
 *  associated, the method {@code advance} is invoked. It is up to this
 *  method to gather further arguments and perform the functionality.
 * </p>
 * <p>
 *  With this interface the functionality is in fact tied to the implementing
 *  code and not to the primitive {@code \advance}. Each primitive can be
 *  made aware for advancing without touching the code for {@code \advance}.
 * </p>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface Advanceable {

    /**
     * This method is called when the macro {@code \advance} has been seen.
     * It performs the remaining tasks for the expansion.
     *
     * @param prefix the prefix for the command
     * @param context the processor context
     * @param source the token source to parse
     * @param typesetter the typesetter
     *
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * @throws org.extex.framework.configuration.exception.ConfigurationException
     *   in case of an configuration error
     */
    void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException, TypesetterException;

}
