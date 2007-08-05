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

package org.extex.ocpware.compiler.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.type.OcpProgram;
import org.extex.ocpware.writer.OcpWriter;

/**
 * This abstract base class provides some utility methods for testing writers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class WriterTester {

    /**
     * Creates a new object.
     */
    public WriterTester() {

        super();
    }

    /**
     * Run a single test case.
     * 
     * @param in the input contents
     * @param writer the writer
     * @param expect the output contents
     * 
     * @throws Exception in case of an error
     */
    protected void run(String in, OcpWriter writer, String expect)
            throws Exception {

        InputStream stream = new ByteArrayInputStream(in.getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocp = cs.compile();

        assertNotNull(ocp);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.write(baos, ocp);
        baos.close();
        assertEquals("Compiler result", expect, //
            baos.toString().replaceAll("\r", ""));
    }

}
