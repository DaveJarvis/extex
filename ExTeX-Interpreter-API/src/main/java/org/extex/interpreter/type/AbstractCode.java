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

import org.extex.core.exception.helping.CantUseInException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.Registrar;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This is the abstract base class which can be used for all classes
 * implementing the interface Code. It provides some useful definitions for most
 * of the methods.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractCode implements Code, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>localizer</tt> contains the localizer or
     * <code>null</code> if none has been set yet.
     */
    private transient Localizer localizer = null;

    /**
     * The field <tt>token</tt> contains the name of this code for debugging.
     */
    private CodeToken token;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public AbstractCode(CodeToken token) {

        super();
        this.token = token;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        throw new CantUseInException(toText(context), typesetter.getMode()
            .toString());
    }

    /**
     * Getter for localizer.
     * 
     * @return the localizer.
     */
    protected Localizer getLocalizer() {

        if (localizer == null) {
            localizer = LocalizerFactory.getLocalizer(getClass().getName());
        }
        return localizer;
    }

    /**
     * @see org.extex.interpreter.type.Code#getName()
     */
    public String getName() {

        return token == null ? "" : token.toText();
    }

    /**
     * Getter for token.
     *
     * @return the token
     */
    public CodeToken getToken() {
    
        return token;
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return token == null ? "" : token.toString();
    }

    /**
     * Determine the printable representation of the control sequence or active
     * character initially bound to the primitive. For the control sequence
     * attach the escape character in front of the name and return the result.
     * Otherwise just use the character.
     * <p>
     * If the token is not known then the empty string is returned.
     * </p>
     * 
     * @return the control sequence including the escape character, the active
     *         character or the empty string
     */
    public String toText() {

        return token == null ? "" : token.toText();
    }

    
    /**
     * Determine the printable representation of the control sequence or active
     * character initially bound to the primitive. For the control sequence
     * attach the current escape character from the context in front of the name
     * and return the result. Otherwise just use the character.
     * <p>
     * If the token is not known then the empty string is returned.
     * </p>
     * 
     * @param context the processing context
     * 
     * @return the control sequence including the escape character, the active
     *         character or the empty string
     */
    public String toText(Context context) {

        return token == null ? "" : token.toText(context.escapechar());
    }

}
