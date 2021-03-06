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

package org.extex.builder.latex;

import java.io.File;
import java.io.IOException;

import org.extex.builder.latex.action.LaTeXAction;
import org.extex.builder.latex.artifact.Artifact;

/**
 * This enumeration lists all supported file formats for the output.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public enum FileFormat {

    /**
     * The field {@code DVI} contains the value for the DVI file format.
     */
    DVI {

        /**
    *      java.lang.String, DependencyNet)
         */
        @Override
        Artifact makeTarget(File directory, String name, DependencyNet net)
                throws IOException {

            return make(directory, name, ".dvi", net);
        }

    @Override
        public String toString() {

            return "dvi";
        }
    },

    /**
     * The field {@code PDF} contains the value for the PDF file format.
     */
    PDF {

        /**
    *      java.lang.String, DependencyNet)
         */
        @Override
        Artifact makeTarget(File directory, String name, DependencyNet net)
                throws IOException {

            return make(directory, name, ".pdf", net);
        }

    @Override
        public String toString() {

            return "pdf";
        }
    },

    /**
     * The field {@code PS} contains the value for the PS file format.
     */
    PS {

        /**
    *      java.lang.String, DependencyNet)
         */
        @Override
        Artifact makeTarget(File directory, String name, DependencyNet net)
                throws IOException {

            return make(directory, name, ".ps", net);
        }

    @Override
        public String toString() {

            return "ps";
        }
    };

    /**
     * Make an artifact for the file format.
     * 
     * @param directory the directory
     * @param name the base file
     * @param ext the extension
     * @param net the dependency net
     * 
     * @return the artifact
     * 
     * @throws IOException in case of an I/O error
     */
    private static Artifact make(File directory, String name, String ext,
            DependencyNet net) throws IOException {

        Artifact artifact = new Artifact(directory,
            name.replaceAll("\\.[a-zA-Z0-9_]*$", "") + ext);
        Artifact master = net.getMaster();
        artifact.provideActions(new LaTeXAction(master));
        net.addArtifact(artifact);
        artifact.dependsOn(master);
        return artifact;
    }

    /**
     * Make an artifact for the file format.
     * 
     * @param directory the directory
     * @param name the base file
     * @param net the dependency net
     * 
     * @return the artifact
     * 
     * @throws IOException in case of an I/O error
     */
    abstract Artifact makeTarget(File directory, String name, DependencyNet net)
            throws IOException;

}
