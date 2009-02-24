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
import java.util.logging.Logger;

import org.extex.maven.latex.builder.Parameters;
import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.exception.MakeException;

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
     * @see org.extex.maven.latex.builder.action.Action#makeCommandLine(org.extex.maven.latex.builder.Parameters,
     *      Artifact, Artifact, Logger)
     */
    @Override
    protected List<String> makeCommandLine(Parameters parameters,
            Artifact artifact, Artifact target, Logger logger)
            throws MakeException {

        if (target.isUpToDate(artifact, logger)) {
            return null;
        }
        // LaTeX Warning: There were undefined references.
        // LaTeX Warning: Citation `abc' on page 1 undefined on input line 3.
        // LaTeX Warning: Label(s) may have changed. Rerun to get
        // cross-references right.
        // No file document2.ind.
        // Output written on target/document2.dvi (1 page, 280 bytes).

        List<String> list = new ArrayList<String>();
        list.add(parameters.getLatexCommand());
        list.add("-output-directory=" + parameters.getOutputDirectory());
        list.add("-nonstopmode");
        list.add(artifact.getFile().toString().replace('\\', '/'));
        return list;
    }

}
