/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.font;

import java.util.List;

import org.jdom.Element;

/**
 * This class implements methods for reading fonts from
 * a xml-file.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XMLFont {

    /**
     * Return the Element in the tree or <code>null</code>, if not found.
     * @param e     the Element
     * @param name  the elementname
     * @return the element or <code>null</code>, if not found
     */
    protected Element scanForElement(final Element e, final String name) {

        if (e.getName().equals(name)) {
            return e;
        } else {
            Element element = e.getChild(name);
            if (element != null) {
                return element;
            } else {
                List liste = e.getChildren();
                for (int i = 0; i < liste.size(); i++) {
                    element = scanForElement((Element) liste.get(i), name);
                    if (element != null) {
                        return element;
                    }
                }
            }
        }
        return null;
    }

}
