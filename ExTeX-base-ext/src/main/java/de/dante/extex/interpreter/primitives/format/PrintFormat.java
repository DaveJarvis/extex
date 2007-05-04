/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.format;

import java.text.DecimalFormat;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.NoHelpException;
import org.extex.interpreter.exception.helping.CantUseAfterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

import de.dante.extex.interpreter.type.real.Real;
import de.dante.extex.interpreter.type.real.RealConvertible;

/**
 * This class provides an implementation for the primitive
 * <code>\printformat</code>. It format the next primitive for the output
 * with the given pattern and the default <code>Locale</code>.
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * \the\printformat{pattern}\real7
 * </pre>
 * 
 * @see java.text.DecimalFormat
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class PrintFormat extends AbstractCode implements Theable {

    /**
     * The field <tt>serialVersionUID</tt> contains the ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public PrintFormat(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.push(the(context, source, typesetter));
    }

    /**
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws ConfigurationException,
                HelpingException, TypesetterException {

        // \the\printformat{pattern}\real7

        String pattern = source.scanTokensAsString(context, getName());

        if (pattern == null || pattern.trim().length() == 0) {
            pattern = "0.00";
        }

        Token cs = source.getToken(context);

        if (!(cs instanceof ControlSequenceToken)) {
            throw new CantUseAfterException(cs.toText(),
                printableControlSequence(context));
        }

        Code code = context.getCode((CodeToken) cs);

        if (code == null) {
            throw new UndefinedControlSequenceException(printable(context, cs));

        } else if (code instanceof RealConvertible) {
            Real val =
                    ((RealConvertible) code).convertReal(context, source,
                        typesetter);
            DecimalFormat form = new DecimalFormat(pattern);
            try {
                return context.getTokenFactory().toTokens( //
                    form.format(val.getValue()));
            } catch (CatcodeException e) {
                throw new NoHelpException(e);
            }
        } else {
            throw new CantUseAfterException(cs.toText(),
                printableControlSequence(context));
        }
    }
}
