/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.util.observer;

import de.dante.util.GeneralException;


/**
 * An Observer is a object which is able to receive a notification from an
 * {@link de.dante.util.observer.Observable Observable}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface Observer {

    /**
     * The callback method which is invoked when the Observable has some
     * action to be monitored.
     *
     * @param observable the object which initiates the method call
     * @param item some arbitrary parameter passed to the Observer
     *
     * @throws GeneralException in case of an error
     */
    void update(Observable observable, Object item) throws GeneralException;

}
