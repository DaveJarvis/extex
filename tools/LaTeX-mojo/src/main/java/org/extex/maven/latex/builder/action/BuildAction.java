/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.maven.latex.builder.action;

import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.exception.MakeException;

/**
 * This interface describes an action participating to the build.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface BuildAction {

    /**
     * Run or simulate the commands to be executed.
     * 
     * @param artifact
     * @param context the environment
     * @param logger the logger
     * @param simulate the indicator whether or not to really execute the
     *        commands
     * 
     * @throws MakeException in case of an error
     */
    void execute(Artifact artifact, Map<String, String> context, Logger logger,
            boolean simulate) throws MakeException;

    /**
     * Print the action to a writer.
     * 
     * @param w the writer
     * @param pre the prefix inserted at the beginning of each line
     */
    void print(PrintWriter w, String pre);

}
