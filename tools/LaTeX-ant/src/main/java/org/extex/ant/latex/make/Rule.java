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

package org.extex.ant.latex.make;

import java.io.File;
import java.util.List;

import org.extex.ant.latex.command.Command;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Rule {

    /**
     * Creates a new object.
     * 
     */
    public Rule(File target, List<File> preconditions, List<Command> commands) {

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param target
     * @param net
     * 
     * @return
     */
    public boolean apply(File target, Net net) {

        return false;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param target
     * @param net
     * 
     * @return
     */
    public boolean isUpToDate(File target, Net net) {

        return false;
    }
}
