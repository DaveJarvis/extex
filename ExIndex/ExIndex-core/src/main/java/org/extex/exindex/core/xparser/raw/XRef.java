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

package org.extex.exindex.core.xparser.raw;

import java.util.List;
import java.util.logging.Logger;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class XRef implements RefSpec {

    /**
     * Creates a new object.
     * 
     * @param refs the references
     */
    public XRef(String[] refs) {

        // TODO gene: LocRef constructor unimplemented
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.xparser.raw.RefSpec#check(java.util.List,
     *      java.util.logging.Logger)
     */
    public boolean check(List<OpenLocRef> openPages, Logger logger) {

        // TODO gene: check unimplemented
        return true;
    }

}
