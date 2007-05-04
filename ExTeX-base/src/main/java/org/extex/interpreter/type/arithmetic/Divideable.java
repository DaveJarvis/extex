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

package org.extex.interpreter.type.arithmetic;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This is a interface to mark those classes which are able to divide something.
 *
 * <doc type="howto" name="divide">
 * <h3>Extending <tt>\divide</tt></h3>
 * <p>
 *  The primitive <tt>\divide</tt> is designed to be expanded. It is fairly
 *  simple to write a dividable primitive. The associated code simply has to
 *  implement the interface <tt>Divideable</tt>. Whenever <tt>\divide</tt>
 *  is encountered immediately followed by a token which has the proper code
 *  associated, the method <tt>divide</tt> is invoked. It is up to this
 *  method to gather further arguments and perform the division.
 * </p>
 * <p>
 *  With this interface the division is in fact tied to the implementing
 *  code and not to the primitive <tt>\divide</tt>. Each primitive can be
 *  made aware for division without touching the code for <tt>\divide</tt>.
 * </p>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public interface Divideable {

    /**
     * This method is called when the macro <tt>\divide</tt> has been seen.
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
    void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException, TypesetterException;

}
