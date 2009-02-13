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

package org.extex.maven.latex.make.dependency;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.extex.maven.latex.make.Artifact;
import org.extex.maven.latex.make.Dependency;
import org.extex.maven.latex.make.PlanItem;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Exists implements Dependency {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.Dependency#isUpToDate(org.extex.maven.latex.make.Artifact,
     *      java.util.Map)
     */
    public boolean isUpToDate(Artifact source, Map<String, String> context) {

        return source.getFile().exists();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.Dependency#plan(org.extex.maven.latex.make.Artifact,
     *      java.util.List, java.util.Map)
     */
    public void plan(Artifact artifact, List<PlanItem> plan,
            Map<String, String> context) {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.Dependency#print(java.io.PrintWriter,
     *      java.lang.String, java.lang.String)
     */
    public void print(PrintWriter w, String prefix, String pre) {

        w.print(prefix);
        w.print(pre);
        w.println("[]");
    }

}
