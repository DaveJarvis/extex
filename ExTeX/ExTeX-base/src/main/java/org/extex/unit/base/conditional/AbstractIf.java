/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.base.conditional;

import org.extex.core.Locator;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This is the abstract base class for all ifs.
 * <p>
 * If you want to implement an if-like primitive you should derive it from this
 * class. All you have to do is to implement the method
 * {@link #conditional(org.extex.interpreter.context.Context,org.extex.interpreter.TokenSource,org.extex.typesetter.Typesetter)
 * conditional()}. Here you define the expression evaluated to determine whether
 * the if or the else branch should be taken.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractIf extends AbstractCode
        implements
            ExpandableCode,
            PrefixCode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 24012007L;

    /**
     * Getter for the localizer.
     * 
     * @return the localizer
     */
    protected static Localizer getMyLocalizer() {

        return LocalizerFactory.getLocalizer(AbstractIf.class);
    }

    /**
     * Skip to the next matching {@code \fi} or {@code \else} Token counting
     * the intermediate {@code \if}s and {@code \fi}s.
     * 
     * <p>
     * This method implements the absorption of tokens at high speed.
     * </p>
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param primitive the name of the invoking primitive
     * 
     * @return {@code true} if a matching {@code \else} has been found;
     *         otherwise return {@code false} if a matching {@code \fi}
     *         has been found
     * 
     * @throws HelpingException in case of en error
     */
    public static boolean skipToElseOrFi(Context context, TokenSource source,
            CodeToken primitive) throws HelpingException {

        int depth = 0;
        Locator locator = source.getLocator();

        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {

            if (!(t instanceof CodeToken)) {
                continue;
            }
            Code code = context.getCode((CodeToken) t);
            if (code == null) {
                continue;
            } else if (code instanceof Fi) {
                if (--depth < 0) {
                    return false;
                }
            } else if (code instanceof Else) {
                if (depth <= 0) {
                    return true;
                }
            } else if (code.isIf()) {
                depth++;
            } else if (code.isOuter()) {
                throw new HelpingException(getMyLocalizer(),
                    "TTP.OuterInSkipped", context.esc(primitive),
                    Integer.toString(source.getLocator().getLineNumber()));
            }
        }

        throw new HelpingException(getMyLocalizer(), "TTP.EOFinSkipped",
            context.esc(primitive),
            locator != null ? Integer.toString(locator.getLineNumber()) : "");
    }

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public AbstractIf(CodeToken token) {

        super(token);
    }

    /**
     * This method computes the boolean value of the conditional. If the result
     * is {@code true} then the then branch is expanded and the else branch
     * is skipped. Otherwise the then branch is skipped and the else branch is
     * expanded.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the boolean value
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     * @throws ConfigurationException in case of an configuration error
     */
    public abstract boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException;

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (conditional(context, source, typesetter)) {
            context.pushConditional(source.getLocator(), true, this, 1, false);
        } else if (skipToElseOrFi(context, source, getToken())) {
            context.pushConditional(source.getLocator(), true, this, -1, false);
        }
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        if (conditional(context, source, typesetter)) {
            context.pushConditional(source.getLocator(), true, this, 1, false);
        } else if (skipToElseOrFi(context, source, getToken())) {
            context.pushConditional(source.getLocator(), true, this, -1, false);
        }
    }

    /**
     * The ifs are characterized by the return value {@code true} of this
     * method. Thus the overwritten method returning the constant is provided in
     * this abstract base class.
     * 
*/
    @Override
    public boolean isIf() {

        return true;
    }

}
