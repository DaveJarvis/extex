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

package org.extex.unit.tex.register.count;

import org.extex.core.count.CountParser;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.InitializableCode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;

/**
 * This abstract base class provides the methods to compute the keys for
 * numbered count registers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public abstract class AbstractCount extends AbstractAssignment
        implements
            InitializableCode {

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public AbstractCount(String name) {

        super(name);
    }

    /**
     * Return the key (the name of the primitive) for the numbered count
     * register.
     *
     * @param context the interpreter context to use
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the key for the current register
     *
     * @throws InterpreterException in case that a derived class need to throw
     *  an Exception this one is declared.
     */
    protected String getKey(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        String name;
        name = source.scanRegisterName(context, source, typesetter, getName());

        if (Namespace.SUPPORT_NAMESPACE_COUNT) {
            return context.getNamespace() + "\b" + name;
        }
        return name;
    }

    /**
     * Initialize the Code with some value coming from a String.
     *
     * @param context the interpreter context
     * @param source the source of information for the initialization
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.InitializableCode#init(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void init(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        if (source == null) {
            return;
        }
        Token t = source.getNonSpace(context);
        if (t == null) {
            return;
        }
        source.push(t);
        long value = CountParser.scanInteger(context, source, typesetter);
        context.setCount(getKey(context, source, typesetter), value, true);
    }

}
