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

package org.extex.main.observer;

import java.util.logging.Handler;
import java.util.logging.Level;

import org.extex.interpreter.context.ContextInternals;
import org.extex.interpreter.context.observer.interaction.InteractionObserver;
import org.extex.interpreter.interaction.Interaction;

/**
 * This observer is used to transport the interaction mode changes to the
 * logger. Thus it is guaranteed that only the appropriate messages are shown.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4445 $
 */
public class InteractionModeObserver implements InteractionObserver {

    /**
     * The field <tt>handler</tt> contains the {@link java.util.logging.Handler
     * Handler} at which the logging should be directed.
     */
    private Handler handler;

    /**
     * Creates a new object.
     *
     * @param theHandler the target handler
     */
    public InteractionModeObserver(Handler theHandler) {

        super();
        this.handler = theHandler;
    }

    /**
     * Receive a notification on a count change.
     *
     * @param context the interpreter context
     * @param mode the new interaction mode.
     *
     * @throws Exception in case of a problem
     *
     * @see org.extex.interpreter.context.observer.interaction.InteractionObserver#receiveInteractionChange(
     *      org.extex.interpreter.context.ContextInternals,
     *      org.extex.interpreter.interaction.Interaction)
     */
    public void receiveInteractionChange(ContextInternals context,
            Interaction mode) throws Exception {

        handler.setLevel(mode == Interaction.BATCHMODE //
                ? Level.SEVERE : Level.INFO);
    }

}