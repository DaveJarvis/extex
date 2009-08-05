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

package org.extex.builder.latex.action;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.extex.builder.latex.Parameters;
import org.extex.builder.latex.artifact.Artifact;

/**
 * This action runs makeindex in one of its variants on the artifact.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:7717 $
 */
public class MakeindexAction extends Action {

    /**
     * Creates a new object.
     * 
     * @param artifact the artifact to run LaTeX on
     */
    public MakeindexAction(Artifact artifact) {

        super("makeindex", artifact);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.builder.latex.action.Action#makeCommandLine(org.extex.builder.latex.Parameters,
     *      Artifact, Artifact, Logger)
     */
    @Override
    protected List<String> makeCommandLine(Parameters parameters,
            Artifact artifact, Artifact target, Logger logger) {

        String ist = parameters.getMakeindexStyle();

        List<String> list = new ArrayList<String>();
        list.add(parameters.getMakeindexCommand());
        if (ist != null) {
            list.add("-s");
            list.add(ist);
        }
        list.add("-o");
        list.add(target.toString());
        list.add("-t");
        list.add(target.toString().replaceAll("....$", ".ilg"));
        list.add(getArtifact().getFile().toString().replace('\\', '/'));
        return list;
    }

}
