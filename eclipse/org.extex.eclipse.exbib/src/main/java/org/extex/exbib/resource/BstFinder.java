/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.resource;

import java.io.IOException;
import java.net.URL;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstFinder {

    /**
     * Creates a new object.
     * 
     */
    public BstFinder() {

        // TODO gene: BstFinder constructor unimplemented
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    public URL[] find(String name) {

        ProcessBuilder pb =
                new ProcessBuilder("kpseawhich", "-expand-path=$TEXMF");
        try {
            Process p = pb.start();
            if (p.exitValue() != 0) {
                // TODO gene: error handling unimplemented
                throw new RuntimeException("unimplemented");
            }

        } catch (IOException e) {
            // TODO gene: error handling unimplemented
            throw new RuntimeException("unimplemented");
        }
        return null;
    }
}
