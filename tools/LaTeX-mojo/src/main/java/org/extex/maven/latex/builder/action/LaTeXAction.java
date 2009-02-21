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

import java.util.ArrayList;
import java.util.List;

import org.extex.maven.latex.builder.Parameters;
import org.extex.maven.latex.builder.artifact.Artifact;

/**
 * This action runs L<span class="la">a</span>T<span class="e">e</span>X in one
 * of its variants on the artifact.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LaTeXAction extends Action {

    /**
     * Creates a new object.
     * 
     * @param artifact the artifact to run L<span class="la">a</span>T<span
     *        class="e">e</span>X on
     */
    public LaTeXAction(Artifact artifact) {

        super("latex", artifact);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.builder.action.Action#makeCommandLine(org.extex.maven.latex.builder.Parameters)
     */
    @Override
    protected List<String> makeCommandLine(Parameters parameters) {

        List<String> list = new ArrayList<String>();
        list.add(parameters.getLatexCommand());
        list.add("-output-directory=" + parameters.getOutputDirectory());
        list.add("-nonstopmode");
        list.add(getArtifact().getFile().toString().replace('\\', '/'));
        return list;
    }

}
