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

package org.extex.unit.base.register.toks;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.Namespace;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This abstract base class provides the methods to compute the keys for
 * numbered tokens registers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public abstract class AbstractToks extends AbstractAssignment {

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public AbstractToks(String name) {

        super(name);
    }

    /**
     * Return the key (the name of the primitive) for the numbered tokens
     * register.
     *
     * @param source the source for new tokens
     * @param context the interpreter context to use
     * @param typesetter the typesetter
     *
     * @return the key for the current register
     *
     * @throws HelpingException in case that a derived class needs to
     *  throw an Exception this one is declared.
     * @throws TypesetterException in case of an error in the typesetter
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String name =
                source.scanRegisterName(context, source, typesetter, getName());

        if (Namespace.SUPPORT_NAMESPACE_TOKS) {
            return context.getNamespace() + "toks#" + name;
        }
        return "toks#" + name;
    }

}
