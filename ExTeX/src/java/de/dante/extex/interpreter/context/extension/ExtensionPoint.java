/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.context.extension;

import java.io.Serializable;

import de.dante.extex.interpreter.context.Context;

/**
 * This interface describes the callbacks used for an extension point.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface ExtensionPoint extends Serializable {

    /**
     * This method is invoked upon initialization to prepare everything for
     * proper operation.
     *
     * @param context the interpreter context
     */
    void init(Context context);

    /**
     * This method is invoked when a group is opened.
     *
     * @param context the interpreter context
     */
    void beginGroup(Context context);

    /**
     * This method is invoked when a group is closed.
     *
     * @param context the interpreter context
     */
    void endGroup(Context context);

}
