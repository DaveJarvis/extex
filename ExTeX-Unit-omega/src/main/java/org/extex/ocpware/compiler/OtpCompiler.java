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

package org.extex.ocpware.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.type.Table;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class provides a compiler to translate an otp file into an ocp.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class OtpCompiler {

    /**
     * Compile an input stream into an oc program.
     * 
     * @param stream the input stream
     * 
     * @return the ocp program or <code>null</code> upon EOF
     * 
     * @throws IOException in case of an I/O error
     */
    public static OcpProgram compile(InputStream stream) throws IOException {

        CompilerState cs = new CompilerState();
        cs.parse(stream);
        OcpProgram ocp = new OcpProgram();
        ocp.setInput(cs.getIn());
        ocp.setOutput(cs.getOut());

        List<Table> tables = cs.getTables();
        if (tables != null) {
            for (Table t : tables) {

                // ocp.addTable(null);
            }
        }

        // TODO translate the compiler state into an ocp
        return ocp;
    }

    /**
     * Creates a new object.
     */
    private OtpCompiler() {

        super();
    }

}
