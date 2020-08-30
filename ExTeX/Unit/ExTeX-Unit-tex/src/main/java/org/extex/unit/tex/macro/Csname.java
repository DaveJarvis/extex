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

package org.extex.unit.tex.macro;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.Relax;

/**
 * This class provides an implementation for the primitive {@code \csname}.
 * 
 * <p>The Primitive {@code \csname}</p>
 * <p>
 * The primitive {@code \csname} absorbs further tokens until a matching
 * {@link org.extex.unit.tex.macro.Endcsname {@code \endcsname}} is found. The
 * tokens found are expanded. Spaces are ignored. The expansion should lead to
 * character tokens only. No primitives producing something different are
 * allowed. A new token is constructed from the characters. The escape character
 * is the current escape character.
 * </p>
 * <p>
 * If the meaning of the new token is currently not defined then it is defined
 * to be equivalent to the original meaning of
 * {@link org.extex.unit.base.Relax \relax}.
 * </p>
 * <p>
 * If a non-expandable token is encountered then an error is raised.
 * </p>
 * 
 * <p>Syntax</p>

 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;csname&rang;
 *      &rarr; {@code \csname} &lang;expandable tokens&rang; {@code \endcsname}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \csname abc\endcsname  </pre>
 * 
 * <p>
 * This results in the control sequence {@code \abc}.
 * </p>
 * 
 * <pre class="TeXSample">
 *    \csname ab#de\endcsname  </pre>
 * 
 * <p>
 * The example is valid. It shows that even non-character tokens might be
 * contained.
 * </p>
 * 
 * <pre class="TeXSample">
 *    \csname \TeX\endcsname  </pre>
 * 
 * <p>
 * This is usually illegal since {@code \TeX} is defined in plain to contain
 * some non-expandable primitives.
 * </p>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Csname extends AbstractCode implements ExpandableCode, PrefixCode {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Expand tokens and collect the result until {@code \endcsname} is
     * found. In fact the termination condition is that a Token is found which
     * is assigned to {@link Endcsname Endcsname}.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param loc the localizer
     * 
     * @return the Tokens found while scanning the input tokens
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * @throws ConfigurationException in case of an configuration error
     */
    public static String scanToEndCsname(Context context, TokenSource source,
            Typesetter typesetter, Localizer loc)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        Tokens toks = new Tokens();
        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {

            if (t instanceof CodeToken) {
                Code code = context.getCode((CodeToken) t);

                if (code instanceof Endcsname) {

                    return toks.toText();

                } else if (code instanceof ExpandableCode) {

                    ((ExpandableCode) code).expand(Flags.NONE, context, source,
                        typesetter);

                } else if (code == null) {

                    throw new UndefinedControlSequenceException(t.toText());

                } else {

                    throw new HelpingException(loc, "TTP.MissingEndcsname",
                        context.esc("endcsname"), context.esc(t));
                }

            } else if (!(t instanceof SpaceToken)) {

                toks.add(t);
            }
        }
        throw new EofException();
    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Csname(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        String s = scanToEndCsname(context, source, typesetter, getLocalizer());

        try {
            CodeToken t =
                    (CodeToken) context.getTokenFactory().createToken(
                        Catcode.ESCAPE, context.escapechar(), s,
                        context.getNamespace());
            if (context.getCode(t) == null) {
                context.setCode(t, new Relax(t), true);
            }
            source.push(t);
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
*      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        String s = scanToEndCsname(context, source, typesetter, getLocalizer());

        try {
            CodeToken t =
                    (CodeToken) context.getTokenFactory().createToken(
                        Catcode.ESCAPE, context.escapechar(), s,
                        context.getNamespace());
            if (context.getCode(t) == null) {
                context.setCode(t, new Relax(t), true);
            }
            source.push(t);
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

}
