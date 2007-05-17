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

package org.extex.unit.tex.info;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.CodeExpander;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\the</code>.
 * 
 * <doc name="the">
 * <h3>The Primitive <tt>\the</tt></h3>
 * <p>
 * The primitive <tt>\the</tt> inserts the definition of certain primitives
 * into the input stream. If the token following <tt>\the</tt> is not theable
 * then an error is raised.
 * </p>
 * <p>
 * During the expansion of arguments of macros like <tt>\edef</tt>,
 * <tt>\xdef</tt>, <tt>\message</tt>, and others the further expansion of
 * the tokens is inhibited.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;the&rang;
 *      &rarr; <tt>\the</tt> &lang;internal quantity&rang; </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \the\count123  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4770 $
 */
public class The extends AbstractCode implements ExpandableCode, CodeExpander {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060408L;

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public The(String name) {

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

        Token cs = source.getToken(context);

        if (cs == null) {
            throw new EofException(printableControlSequence(context));
        }
        if (cs instanceof CodeToken) {

            Code code = context.getCode((CodeToken) cs);

            if (code instanceof Theable) {
                Tokens toks;
                try {
                    toks = ((Theable) code).the(context, source, typesetter);
                } catch (CatcodeException e) {
                    throw new NoHelpException(e);
                }
                source.push(toks);
                return;
            } else if (code == null) {
                throw new UndefinedControlSequenceException(//
                    cs.toText(context.escapechar()));
            }
        }

        throw new HelpingException(getLocalizer(), "TTP.CantUseAfterThe", //
            cs.toString(), printableControlSequence(context));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        execute(prefix, context, source, typesetter);
    }

    /**
     * Expand the first token and place the result in a token list. During the
     * expansion additional tokens might be used.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param tokens the target token list
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.CodeExpander#expandCode(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.tokens.Tokens)
     */
    public void expandCode(Context context, TokenSource source,
            Typesetter typesetter, Tokens tokens) throws HelpingException, TypesetterException {

        Token cs = source.getToken(context);

        if (cs == null) {
            throw new EofException(printableControlSequence(context));
        }
        if (cs instanceof CodeToken) {

            Code code = context.getCode((CodeToken) cs);

            if (code instanceof Theable) {
                try {
                    tokens.add(((Theable) code)
                        .the(context, source, typesetter));
                } catch (CatcodeException e) {
                    throw new NoHelpException(e);
                }
                return;
            }
        }

        throw new HelpingException(getLocalizer(), "TTP.CantUseAfterThe", //
            cs.toString(), printableControlSequence(context));
    }
}
