/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.max.context;

import org.extex.core.count.Count;
import org.extex.interpreter.context.ContextInternals;
import org.extex.interpreter.context.observer.count.CountObserver;
import org.extex.interpreter.context.observer.tokens.TokensObserver;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This observer is meant for keeping the current typesetting context in sync
 * with the registers {@code \language} and {@code \lang}.
 * <p>
 * The tokens register {@code \lang} is considered first. Only if this
 * register is not set or it is empty then the count register {@code \language}
 * is taken into account.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LanguageObserver implements CountObserver, TokensObserver {


    public LanguageObserver() {

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.observer.count.CountObserver#receiveCountChange(
     *      org.extex.interpreter.context.ContextInternals, java.lang.String,
     *      org.extex.core.count.Count)
     */
    public void receiveCountChange(ContextInternals context, String name,
            Count value) {

        if ("language".equals(name)) { // this should never fail; just to be
                                        // sure
            Tokens lang = context.getToks("lang");
            if (lang == null || lang.length() == 0) {
                context.getTypesettingContextFactory().newInstance(
                    context.getTypesettingContext(), value.toString());
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.context.observer.tokens.TokensObserver#receiveTokensChange(
     *      org.extex.interpreter.context.ContextInternals, java.lang.String,
     *      org.extex.scanner.type.tokens.Tokens)
     */
    public void receiveTokensChange(ContextInternals context, String name,
            Tokens value) {

        if ("lang".equals(name)) { // this should never fail; just to be sure

            String s = value.toText();
            if (s.equals("")) {
                s = "0";
            }
            context.getTypesettingContextFactory().newInstance(
                context.getTypesettingContext(), s);
        }
    }

}
