/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex;

import org.extex.interpreter.context.Context;
import org.extex.interpreter.interaction.Interaction;
import org.extex.util.resource.InteractionProvider;

/**
 * This internal interface extends the interaction provider by the ability
 * to set a context as final source of information.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ContextawareInteractionProvider implements InteractionProvider {

    /**
     * The field <tt>context</tt> contains the interpreter context.
     */
    private Context context;

    /**
     * Getter for the interaction mode.
     *
     * @return the interaction mode
     *
     * @see org.extex.util.resource.InteractionProvider#getInteraction()
     */
    public Interaction getInteraction() {

        return context.getInteraction();
    }

    /**
     * Setter for the interpreter context.
     *
     * @param context the interpreter context
     */
    public void setContext(final Context context) {

        this.context = context;
    }

}
