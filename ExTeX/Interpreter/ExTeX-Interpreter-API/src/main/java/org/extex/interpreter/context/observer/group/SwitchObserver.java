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

package org.extex.interpreter.context.observer.group;

import org.extex.core.Switch;

/**
 * This class provides an observer which sets a
 * {@link org.extex.core.Switch Switch} when an event is received.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SwitchObserver implements AfterGroupObserver {

    /**
     * The field {@code target} contains the target Switch to set upon the
     * event.
     */
    private final Switch target;

    /**
     * The field {@code value} contains the new value for the switch upon the
     * event.
     */
    private final boolean value;

    /**
     * Creates a new object.
     *
     * @param theTarget the target switch
     * @param theValue the new value
     */
    public SwitchObserver(Switch theTarget, boolean theValue) {

        this.target = theTarget;
        this.value = theValue;
    }

    /**
     * @see org.extex.interpreter.context.observer.group.AfterGroupObserver#update()
     */
    public void update() {

        target.set(value);
    }

}
