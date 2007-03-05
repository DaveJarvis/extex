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

package org.extex.exdoc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UnitInfo {

    /**
     * The field <tt>name</tt> contains the ...
     */
    private String name;

    /**
     * The field <tt>primitives</tt> contains the ...
     */
    private List primitives = new ArrayList();

    /**
     * Creates a new object.
     *
     * @param name ...
     */
    public UnitInfo(final String name) {

        super();
        this.name = name;
    }

    public void add(final String def, final String c) {

        // TODO gene: add unimplemented
        
    }

}
