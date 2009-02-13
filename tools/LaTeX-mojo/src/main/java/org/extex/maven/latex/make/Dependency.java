/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.maven.latex.make;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface Dependency {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param source the artifact to check
     * @param context the environment
     * 
     * @return
     */
    boolean isUpToDate(Artifact source, Map<String, String> context);

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param artifact
     * @param plan
     * @param context the environment
     */
    void plan(Artifact artifact, List<PlanItem> plan,
            Map<String, String> context);

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param w
     * @param prefix
     * @param pre
     */
    void print(PrintWriter w, String prefix, String pre);

}
