/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import java.util.logging.Logger;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.macro.util.MacroCode;
import org.extex.unit.tex.macro.util.MacroPattern;
import org.extex.unit.tex.macro.util.ProtectedMacroCode;

/**
 * This class provides an implementation for the primitive <code>\edef</code>.
 * 
 * <doc name="edef">
 * <h3>The Primitive <tt>\edef</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;edef&rang;
 *       &rarr; &lang;prefix&rang; <tt>\edef</tt> {@linkplain
 *    org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *    &lang;control sequence&rang;} &lang;parameter text&rang; <tt>{</tt> &lang;replacement text&rang; <tt>}</tt>
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       | <tt>\global</tt> &lang;prefix&rang;
 *       | <tt>\long</tt> &lang;prefix&rang;
 *       | <tt>\outer</tt> &lang;prefix&rang;
 *       | <tt>\proteced</tt> &lang;prefix&rang;</pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \edef#1{--#1--}  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class Edef extends AbstractAssignment implements LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param name the name for debugging
     */
    public Edef(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Code#execute(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        boolean notLong = !prefix.clearLong();
        boolean global = prefix.clearGlobal();
        boolean protect = prefix.clearProtected();
        MacroPattern pattern =
                MacroPattern.getPattern(context, source, notLong, cs);
        Tokens body;
        try {
            body = source.scanUnprotectedTokens(context, //
                false, false, getName());

            Token t = pattern.get(pattern.length() - 1);
            if (t instanceof LeftBraceToken) {
                body.add(t);
            }
        } catch (EofException e) {
            throw new EofInToksException(cs.toText(context.escapechar()));
        }

        String csName = printable(context, cs);
        MacroCode macroCode =
                (protect //
                        ? new ProtectedMacroCode(csName, prefix, notLong,
                            pattern, body) //
                        : new MacroCode(csName, prefix, notLong, pattern, body));
        macroCode.enableLogging(logger);

        context.setCode(cs, macroCode, global);
    }

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger logger) {

        this.logger = logger;
    }

}
