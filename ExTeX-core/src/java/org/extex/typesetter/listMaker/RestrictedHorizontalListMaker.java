/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.listMaker;

import org.extex.type.Locator;
import org.extex.typesetter.Mode;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * This is the derived class for a list maker in restricted horizontal mode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class RestrictedHorizontalListMaker extends HorizontalListMaker {

    /**
     * Creates a new object.
     *
     * @param manager the manager to ask for global changes
     * @param locator the locator
     */
    public RestrictedHorizontalListMaker(final ListManager manager,
            final Locator locator) {

        super(manager, locator);
    }

    /**
     * Getter for the current mode.
     *
     * @return the mode which is one of the values defined in
     * {@link org.extex.typesetter.Mode Mode}.
     *
     * @see org.extex.typesetter.ListMaker#getMode()
     */
    public Mode getMode() {

        return Mode.RESTRICTED_HORIZONTAL;
    }

    /**
     * Close the node list. This means that everything is done to ship the
     * closed node list to the document writer. Nevertheless the invoking
     * application might decide not to modify the node list and continue
     * processing. In the other case some  nodes might be taken from the node
     * list returned by this method. Then the processing has to continue with
     * the reduced node list.
     *
     * @param context the typesetter options mapping a fragment of the
     *  interpreter context
     *
     * @return the node list enclosed in this instance
     *
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of a configuration error
     *
     * @see org.extex.typesetter.ListMaker#complete(TypesetterOptions)
     */
    public NodeList complete(final TypesetterOptions context)
            throws TypesetterException,
                ConfigurationException {

        return getNodes(); //TODO gene: correct?
    }

}
