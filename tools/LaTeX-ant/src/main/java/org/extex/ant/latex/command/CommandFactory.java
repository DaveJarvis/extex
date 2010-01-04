/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.ant.latex.command;

import org.extex.ant.latex.Settings;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class CommandFactory {

    /**
     * TODO gene: missing JavaDoc.
     * 
     */
    public enum Type {
        /**
         * The field <tt>LATEX</tt> contains the ...
         */
        LATEX,
        /**
         * The field <tt>BIBTEX</tt> contains the ...
         */
        BIBTEX,
        /**
         * The field <tt>MAKEINDEX</tt> contains the ...
         */
        MAKEINDEX
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param type
     * @param settings
     * @return
     */
    public static Command create(Type type, Settings settings) {

        switch (type) {
            case LATEX:
                return new LaTeX(settings);
            case BIBTEX:
                String command = settings.get("bibtex.command", "bibtex");
                return (command.equals("&exbib")
                        ? new ExBib(settings)
                        : new BibTeX(settings));
            case MAKEINDEX:
                return new Makeindex(settings);
        }
        throw new RuntimeException("illegal type");
    }
}
