/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.register.dimen;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.InitializableCode;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.util.framework.configuration.Configurable;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationException;

import de.dante.extex.scanner.type.token.Token;
import de.dante.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive <code>\dimen</code>.
 * It sets the named length register to the value given,
 * and as a side effect all prefixes are zeroed.
 *
 * <p>Example</p>
 * <pre>
 * \day=345
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DimenParameter extends DimenPrimitive
        implements
            InitializableCode,
            Configurable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>key</tt> contains the key.
     */
    private String key;

    /**
     * Creates a new object.
     *
     * @param name the name for debugging
     */
    public DimenParameter(final String name) {

        super(name);
        key = name;
    }

    /**
     * @see org.extex.util.framework.configuration.Configurable#configure(
     *      org.extex.util.framework.configuration.Configuration)
     */
    public void configure(final Configuration config)
            throws ConfigurationException {

        String k = config.getAttribute("key");
        if (k != null) {
            key = k;
        }
    }

    /**
     * Return the key (the name of the primitive) for the register.
     *
     * @param context the interpreter context to use
     * @param source the source for new tokens
     *
     * @return the key for the current register
     *
     * @see de.dante.extex.interpreter.primitives.register.dimen.AbstractDimen#getKey(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    protected String getKey(final Context context, final TokenSource source,
            final Typesetter typesetter) {

        return key;
    }

    /**
     * Initialize the Code with some value coming from a Token source.
     *
     * @param context the interpreter context
     * @param source the source of information for the initialization
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.type.InitializableCode#init(
     *      org.extex.interpreter.context.Context, TokenSource, Typesetter)
     */
    public void init(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        if (source == null) {
            return;
        }
        Token t = source.getNonSpace(context);
        if (t == null) {
            return;
        }
        source.push(t);
        Dimen d = Dimen.parse(context, source, typesetter);
        context.setDimen(getKey(context, null, typesetter), d, true);
    }

}
