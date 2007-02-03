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

package org.extex.interpreter.type;

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.CantUseInException;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.util.framework.Registrar;
import org.extex.util.framework.i18n.Localizable;
import org.extex.util.framework.i18n.Localizer;

/**
 * This is the abstract base class which can be used for all classes
 * implementing the interface Code. It provides some useful definitions for
 * most of the methods.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractCode implements Code, Localizable, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Return the printable version of a token for error messages.
     *
     * @param context the processing context
     * @param token the token to get a printable representation for
     *
     * @return the control sequence including the escape character
     */
    public static String printable(final Context context, final Token token) {

        if (token instanceof ControlSequenceToken) {
            return context.esc("") + ((ControlSequenceToken) token).getName();
        }
        return token.getChar().toString();
    }

    /**
     * The field <tt>localizer</tt> contains the localizer or <code>null</code>
     * if none has been set yet.
     */
    private transient Localizer localizer = null;

    /**
     * The field <tt>name</tt> contains the name of this code for debugging.
     */
    private String name;

    /**
     * Creates a new object.
     *
     * @param codeName the name of the primitive
     */
    public AbstractCode(final String codeName) {

        super();
        this.name = codeName;
    }

    /**
     * Setter for the localizer.
     *
     * @param theLocalizer the new value for the localizer
     *
     * @see org.extex.util.framework.i18n.Localizable#enableLocalization(
     *      org.extex.util.framework.i18n.Localizer)
     */
    public void enableLocalization(final Localizer theLocalizer) {

        this.localizer = theLocalizer;
    }

    /**
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        throw new CantUseInException(printableControlSequence(context),
            typesetter.getMode().toString());
    }

    /**
     * Getter for localizer.
     *
     * @return the localizer.
     */
    protected Localizer getLocalizer() {

        return this.localizer;
    }

    /**
     * @see org.extex.interpreter.type.Code#getName()
     */
    public String getName() {

        return name;
    }

    /**
     * @see org.extex.interpreter.type.Code#isIf()
     */
    public boolean isIf() {

        return false;
    }

    /**
     * @see org.extex.interpreter.type.Code#isOuter()
     */
    public boolean isOuter() {

        return false;
    }

    /**
     * Attach the current escape character in front of the name and return the
     * result.
     * <p>
     * This method is meant to produce a printable version of the control
     * sequence for error messages.
     * </p>
     *
     * @param context the processing context
     *
     * @return the control sequence including the escape character
     */
    protected String printableControlSequence(final Context context) {

        return context.esc(name);
    }

    /**
     * Restore the internal state when the instance is loaded from file.
     *
     * @return the object which should be used instead of the one read
     *
     * @throws ObjectStreamException in case of an error
     */
    protected Object readResolve() throws ObjectStreamException {

        return Registrar.reconnect(this);
    }

    /**
     * @see org.extex.interpreter.type.Code#setName(java.lang.String)
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return name;
    }

}
