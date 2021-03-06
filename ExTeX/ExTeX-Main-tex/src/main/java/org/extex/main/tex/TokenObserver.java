/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.main.tex;

import java.util.logging.Logger;

import org.extex.interpreter.observer.pop.PopObserver;
import org.extex.scanner.type.token.Token;

/**
 * Observer for tokens which are read from the token stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TokenObserver implements PopObserver {

    /**
     * The field {@code logger} contains the logger for output
     */
    private final Logger logger;

    /**
     * Creates a new object.
     *
     * @param theLogger the logger for potential output
     */
    public TokenObserver(Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * This method is meant to be invoked just after a pop ocurrs.
     *
     * @param token the token to be expanded
     *
     * @see org.extex.interpreter.observer.pop.PopObserver#update(
     *      org.extex.scanner.type.token.Token)
     */
    public void update(Token token) {

        logger.fine(token.toString() + "\n");
    }

}
