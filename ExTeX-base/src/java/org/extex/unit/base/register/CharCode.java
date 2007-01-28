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

package org.extex.unit.base.register;

import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Showable;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.count.CountConvertible;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.Token;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for a Code which represents a single
 * character.
 * The code is executable, expandable, and convertible into a
 * count register.
 * The token returned by expansion depends on the category code at the time of
 * expansion.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class CharCode extends AbstractCode
        implements
            ExpandableCode,
            CountConvertible,
            Showable,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 28022006L;

    /**
     * The field <tt>character</tt> contains the encapsulated Unicode character.
     */
    private UnicodeChar character;

    /**
     * Creates a new object.
     * @param uc the Unicode character to encapsulate
     */
    public CharCode(final UnicodeChar uc) {

        super(uc.toString());
        this.character = uc;
    }

    /**
     * @see org.extex.interpreter.type.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public long convertCount(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return character.getCodePoint();
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

        expand(prefix, context, source, typesetter);
    }

    /**
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException {

        try {
            Token t =
                    context.getTokenFactory().createToken(Catcode.OTHER,
                        character, context.getNamespace());
            source.push(t);
        } catch (CatcodeException e) {
            throw new InterpreterException(e);
        }
    }

    /**
     * Getter for character.
     *
     * @return the character
     */
    public UnicodeChar getCharacter() {

        return this.character;
    }

    /**
     * @see org.extex.interpreter.type.Showable#show(
     *      org.extex.interpreter.context.Context)
     */
    public Tokens show(final Context context) throws InterpreterException {

        return new Tokens(context, context.esc("char")
                + "\""
                + Integer.toHexString(getCharacter().getCodePoint())
                    .toUpperCase());
    }

    /**
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(final Context context, final TokenSource source,
            final Typesetter typesetter) throws InterpreterException {

        return new Tokens(context, Integer.toString(character.getCodePoint()));
    }

    /**
     * @see org.extex.interpreter.type.AbstractCode#toString()
     */
    public String toString() {

        return character.toString();
    }

}
