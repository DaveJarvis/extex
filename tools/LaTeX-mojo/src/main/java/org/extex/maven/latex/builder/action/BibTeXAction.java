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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.extex.maven.latex.builder.artifact.Artifact;

/**
 * This action runs BibTeX in one of its variants on the artifact.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibTeXAction extends Action {

    /**
     * Creates a new object.
     * 
     * @param artifact the artifact to run LaTeX on
     */
    public BibTeXAction(Artifact artifact) {

        super("bibtex", artifact);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.builder.action.Action#makeCommandLine(java.util.Map)
     */
    @Override
    protected List<String> makeCommandLine(Map<String, String> context) {

        List<String> list = new ArrayList<String>();
        list.add(get(context, "bibtex.command", "makeindex"));
        list.add(getArtifact().getFile().toString().replace('\\', '/'));
        return list;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.builder.action.Action#makeWorkingDirectory(java.util.Map)
     */
    @Override
    protected File makeWorkingDirectory(Map<String, String> context) {

        String workingDirectory = get(context, "bibtex.working.directory", ".");
        return new File(workingDirectory);
    }

}
