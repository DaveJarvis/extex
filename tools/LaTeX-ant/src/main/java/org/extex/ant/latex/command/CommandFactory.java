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

package org.extex.ant.latex.command;

import org.extex.ant.latex.Settings;

/**
 * This factory provides the different commands used to process a document.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class CommandFactory {

    /**
     * This enumeration marks the kind od command requested.
     */
    public enum Type {
        /**
         * The field <tt>LATEX</tt> contains the value for the latex command.
         */
        LATEX,
        /**
         * The field <tt>BIBTEX</tt> contains the value for the bibtex command.
         */
        BIBTEX,
        /**
         * The field <tt>MAKEINDEX</tt> contains the value for the makeindex
         * command.
         */
        MAKEINDEX
    }

    /**
     * Create a command.
     * 
     * @param type the type of the command requested
     * @param settings the settings used to initialize the command
     * 
     * @return the command. It can not be <code>null</code>
     * 
     * @throws RuntimeException in case of an illegal type
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
