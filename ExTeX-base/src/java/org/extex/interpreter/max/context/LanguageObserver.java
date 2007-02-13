/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import org.extex.interpreter.context.ContextInternals;
import org.extex.interpreter.context.observer.count.CountObserver;
import org.extex.interpreter.context.observer.tokens.TokensObserver;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This observer is meant for keeping the current typesetting context in sync
 * with the registers <tt>\language</tt> and <tt>\lang</tt>.
 * <p>
 *  The tokens register <tt>\lang</tt> is considered first. Only if this
 *  register is not set or it is empty then the count register <tt>\language</tt>
 *  is taken into account.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class LanguageObserver implements CountObserver, TokensObserver {

    /**
     * Creates a new object.
     */
    public LanguageObserver() {

        super();
    }

    /**
     * @see org.extex.interpreter.context.observer.count.CountObserver#receiveCountChange(
     *      org.extex.interpreter.context.ContextInternals,
     *      java.lang.String,
     *      org.extex.interpreter.type.count.Count)
     */
    public void receiveCountChange(final ContextInternals context,
            final String name, final Count value) throws ConfigurationException {

        if ("language".equals(name)) { // this should never fail; just to be sure
            Tokens lang = context.getToks("lang");
            if (lang == null || lang.length() == 0) {
                context.getTypesettingContextFactory().newInstance(
                    context.getTypesettingContext(), value.toString());
            }
        }
    }

    /**
     * @see org.extex.interpreter.context.observer.tokens.TokensObserver#receiveTokensChange(
     *      org.extex.interpreter.context.ContextInternals,
     *      java.lang.String,
     *      org.extex.interpreter.type.tokens.Tokens)
     */
    public void receiveTokensChange(final ContextInternals context,
            final String name, final Tokens value)
            throws ConfigurationException {

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
