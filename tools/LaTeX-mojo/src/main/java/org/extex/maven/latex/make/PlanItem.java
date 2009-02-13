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

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PlanItem {

    /**
     * The field <tt>artifact</tt> contains the ...
     */
    private Artifact artifact;

    /**
     * The field <tt>action</tt> contains the ...
     */
    private BuildAction action;

    /**
     * Creates a new object.
     * 
     * @param action
     * @param artifact
     */
    public PlanItem(BuildAction action, Artifact artifact) {

        super();
        this.action = action;
        this.artifact = artifact;
    }

    /**
     * Getter for the action.
     * 
     * @return the action
     */
    public BuildAction getAction() {

        return action;
    }

    /**
     * Getter for the artifact.
     * 
     * @return the artifact
     */
    public Artifact getArtifact() {

        return artifact;
    }

}
