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

package org.extex.typesetter.type.page;

import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.NodeList;
import org.extex.util.exception.GeneralException;


/**
 * This class provides a factory for page instances.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4399 $
 */
public interface PageFactory {

    /**
     * Get a new instance of a page.
     * 
     * @param nodes the nodes contained
     * @param context the interpreter context
     * @param typesetter the typesetter
     * 
     * @return the new instance or <code>null</code> if the page would be
     *         empty
     * 
     * @throws GeneralException in case of an error
     */
    public Page newInstance(final NodeList nodes, final Context context,
            final Typesetter typesetter) throws GeneralException;

}
