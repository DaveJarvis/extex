/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.maven.latex.builder;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class ContextKey {

    /**
     * The field <tt>BIBTEX_EXTENSIONS</tt> contains the context name for the
     * BibTeX extensions.
     */
    public static final String BIBTEX_EXTENSIONS = "bibtex.extensions";

    /**
     * The field <tt>GRAPHICS_EXTENSIONS</tt> contains the context name for the
     * graphics extensions.
     */
    public static final String GRAPHICS_EXTENSIONS = "graphics.extensions";

    /**
     * The field <tt>LATEX_COMMAND</tt> contains the context name for the LaTeX
     * command.
     */
    public static final String LATEX_COMMAND = "latex.command";

    /**
     * The field <tt>LATEX_EXTENSIONS</tt> contains the context name for the
     * L<span class="la">a</span>T<span class="e">e</span>X extensions.
     */
    public static final String LATEX_EXTENSIONS = "latex.extensions";

    /**
     * Callback method to initialize a net.
     * 
     * @return the initialized map
     */
    protected static Map<String, String> setup() {

        Map<String, String> map = new HashMap<String, String>();
        map.put(LATEX_COMMAND, "latex");
        map.put(LATEX_EXTENSIONS, ".tex:.TeX:.latex:.LaTeX:.ltx:");
        map.put(GRAPHICS_EXTENSIONS, ".png:.jpg:.jpeg:.gif:.tiff:.pdf:");
        map.put(BIBTEX_EXTENSIONS, ".bib:.bibtex:.BibTeX:");
        return map;
    }

    /**
     * Never creates a new object.
     */
    private ContextKey() {

        // no constructor for utility class
    }

}
