/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.editor.bst;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class ColorManager {

    /**
     * The field <tt>colorTable</tt> contains the ...
     */
    protected Map<String, Color> colorTable = new HashMap<String, Color>();

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    public void dispose() {

        for (Color color : colorTable.values()) {
            color.dispose();
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name the reference key
     * @return
     */
    public Color getColor(String name) {

        Color color = colorTable.get(name);
        if (color == null) {
            color =
                    new Color(Display.getCurrent(), 
                        PreferenceConverter.getColor(Activator.getDefault()
                            .getPreferenceStore(), name));
            colorTable.put(name, color);
        }
        return color;
    }
}
