/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.base.register.font;

import org.extex.font.type.other.NullFont;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ComparableCode;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.font.FontConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an implementation for the primitive
 * <code>\nullfont</code>.
 *
 * <doc name="nullfont">
 * <h3>The Primitive <tt>\nullfont</tt></h3>
 * <p>
 *  TODO missing documentation
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    <tt>\nullfont</tt>  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \font123=\nullfont  </pre>
 *
 * </doc>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class NullfontPrimitive extends AbstractCode
        implements
            FontConvertible,
            ComparableCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>nullFont</tt> contains the font encapsulated in this
     * primitive.
     */
    private NullFont nullFont = new NullFont();

    /**
     * Creates a new object.
     *
     * @param name the name of the primitive
     */
    public NullfontPrimitive(final String name) {

        super(name);
    }

    /**
     * @see org.extex.interpreter.type.ComparableCode#compare(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.context.Context)
     */
    public boolean compare(final Token token, final Context context)
            throws InterpreterException {

        return (token instanceof CodeToken)
                && context.getCode((CodeToken) token) == this;
    }

    /**
     * Convert some primitive value into a font.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @return the converted value
     *
     * @see org.extex.interpreter.type.font.FontConvertible#convertFont(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Font convertFont(final Context context, final TokenSource source,
            final Typesetter typesetter) {

        return nullFont;
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter) {

        context.set(nullFont, prefix.clearGlobal());
    }

}
