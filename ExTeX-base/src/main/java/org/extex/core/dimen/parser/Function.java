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

package org.extex.core.dimen.parser;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This interface describes a function object which is able to parse its
 * arguments from a toke stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4404 $
 */
public interface Function {

    /**
     * Acquire arguments and compute a function.
     *
     * @param accumulator the accumulator to receive the result
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @throws HelpingException TODO
     * @throws TypesetterException TODO
     */
    void apply(Accumulator accumulator, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException;

}
