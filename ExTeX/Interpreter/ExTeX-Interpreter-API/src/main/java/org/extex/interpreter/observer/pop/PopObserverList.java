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

package org.extex.interpreter.observer.pop;

import java.util.ArrayList;
import java.util.List;

import org.extex.scanner.type.token.Token;

/**
 * This class provides a type-safe list of observers for the pop event.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class PopObserverList implements PopObserver {

    /**
     * Take a list and add an observer. If the list is {@code null} then
     * a new one is created.
     *
     * @param list the input list or {@code null}
     * @param observer the observer to add
     *
     * @return the input list or a new one with the observer added
     */
    public static PopObserver register(PopObserver list,
            PopObserver observer) {

        if (list instanceof PopObserverList) {
            ((PopObserverList) list).add(observer);
        } else if (list == null) {
            PopObserverList result = new PopObserverList();
            result.add(observer);
            return result;
        } else {
            PopObserverList result = new PopObserverList();
            result.add(list);
            result.add(observer);
            return result;
        }
        return list;
    }

    /**
     * The field {@code list} contains the encapsulated list.
     */
    private final List<PopObserver> list = new ArrayList<PopObserver>();

    /**
     * Add an observer to the list.
     *
     * @param observer the observer to add to the list
     */
    public void add(PopObserver observer) {

        list.add(observer);
    }

    /**
     * Invoke all observers on the list to inform them of the token to be
     * poped.
     *
     * @param token the token to be poped
     */
    public void update(Token token) {

        for (PopObserver obs : list) {
            obs.update(token);
        }
    }

}
