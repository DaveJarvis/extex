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

package org.extex.interpreter.context.observer.load;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.typesetter.Typesetter;

/**
 * This interface describes the possibility to register an observer for an load
 * event.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface LoadedObservable {

    /**
     * Register an observer for load events. Code change events are triggered
     * when the context is loaded.
     * 
     * @param observer the observer to receive the events
     */
    void registerLoadObserver(LoadedObserver observer);

    /**
     * Remove a registered observer for load events. Code change events are
     * triggered when the context is loaded.
     * 
     * @param observer the observer to receive the events
     */
    void unregisterLoadObserver(LoadedObserver observer);

    /**
     * Receive a notification about a load event.
     * 
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws HelpingException in case of an error
     */
    void receiveLoad(TokenSource source, Typesetter typesetter)
            throws HelpingException;
}
