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

import org.extex.core.UnicodeChar;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.NoHelpException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Showable;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.CountConvertible;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for a Code which represents a single
 * character. The code is executable, expandable, and convertible into a count
 * register. The token returned by expansion depends on the category code at the
 * time of expansion.
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
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 28022006L;

    /**
     * The field <tt>character</tt> contains the encapsulated Unicode
     * character.
     */
    private UnicodeChar character;

    /**
     * Creates a new object.
     * 
     * @param uc the Unicode character to encapsulate
     */
    public CharCode(UnicodeChar uc) {

        super(uc.toString());
        this.character = uc;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return character.getCodePoint();
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

        expand(prefix, context, source, typesetter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        try {
            Token t =
                    context.getTokenFactory().createToken(Catcode.OTHER,
                        character, context.getNamespace());
            source.push(t);
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
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
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * 
     * @return the description of the primitive as list of Tokens
     * @see org.extex.interpreter.type.Showable#show(
     *      org.extex.interpreter.context.Context)
     */
    public Tokens show(Context context) throws HelpingException {

        try {
            return context.getTokenFactory().toTokens(
                context.esc("char")
                        + "\""
                        + Integer.toHexString(getCharacter().getCodePoint())
                            .toUpperCase());
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
     * This method is the getter for the description of the primitive.
     * 
     * @param context the interpreter context
     * @param source the source for further tokens to qualify the request
     * @param typesetter the typesetter to use
     * 
     * @return the description of the primitive as list of Tokens
     * @throws CatcodeException in case of an error in token creation
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException, TypesetterException {

        return context.getTokenFactory().toTokens( //
            Integer.toString(character.getCodePoint()));
    }

    /**
     * @see org.extex.interpreter.type.AbstractCode#toString()
     */
    public String toString() {

        return character.toString();
    }

}
