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
public class ConfigurationInfo {

    private String name;

    private String banner;

    private List units = new ArrayList();

    /**
     * Creates a new object.
     *
     * @param name ...
     * @param banner ...
     */
    public ConfigurationInfo(final String name, final String banner) {

        super();
        this.name = name;
        this.banner = banner;
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param src
     */
    public void addUnit(final String src) {

        System.err.println(src);
        units.add(src);
    }

}
