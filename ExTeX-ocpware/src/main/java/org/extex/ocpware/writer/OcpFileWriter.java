/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.ocpware.writer;

import java.io.OutputStream;

import org.extex.ocpware.type.OcpProgram;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpFileWriter implements OcpWriter {

    /**
     * Creates a new object.
     */
    public OcpFileWriter() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.writer.OcpWriter#write(java.io.OutputStream,
     *      org.extex.ocpware.type.OcpProgram)
     */
    public void write(OutputStream out, OcpProgram ocp) {

        // TODO gene: write unimplemented

    }

}
