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

package org.extex.interpreter.observer.command;

import java.util.ArrayList;
import java.util.List;

import org.extex.scanner.type.token.Token;

/**
 * This class provides a type-safe list of observers for the execute event.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class CommandObserverList implements CommandObserver {

    /**
     * Take a list and add an observer. If the list is {@code null} then
     * a new one is created.
     *
     * @param list the input list or {@code null}
     * @param observer the observer to add
     *
     * @return the input list or a new one with the observer added
     */
    public static CommandObserver register(CommandObserver list,
            CommandObserver observer) {

        if (list instanceof CommandObserverList) {
            ((CommandObserverList) list).add(observer);
        } else if (list == null) {
            CommandObserverList result = new CommandObserverList();
            result.add(observer);
            return result;
        } else {
            CommandObserverList result = new CommandObserverList();
            result.add(list);
            result.add(observer);
            return result;
        }
        return list;
    }

    /**
     * The field {@code list} contains the encapsulated list.
     */
    private List<CommandObserver> list = new ArrayList<CommandObserver>();

    /**
     * Add an observer to the list.
     *
     * @param observer the observer to add to the list
     */
    public void add(CommandObserver observer) {

        list.add(observer);
    }

    /**
     * Invoke all observers on the list to inform them of the token to be
     * expanded.
     *
     * @param token the token to be expanded
     */
    public void update(Token token) {

        for (CommandObserver obs : list) {
            obs.update(token);
        }
    }

}
