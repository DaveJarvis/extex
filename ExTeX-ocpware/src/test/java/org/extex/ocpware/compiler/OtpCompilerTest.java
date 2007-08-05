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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.type.OcpProgram;
import org.junit.Test;

/**
 * This is a test suite for the OcpCompiler.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class OtpCompilerTest extends TestCase {

    /**
     * lat2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCompileLat2uni() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(OTP.OMEGA_LAT2UNI_OTP.getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocp = cs.compile();

        assertNotNull(ocp);
        assertEquals(1, ocp.getInput());
        assertEquals(2, ocp.getOutput());
    }

    /**
     * uni2lat.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCompileUni2Lat() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(OTP.OMEGA_UNI2LAT_OTP.getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocp = cs.compile();

        assertNotNull(ocp);
        assertEquals(2, ocp.getInput());
        assertEquals(2, ocp.getOutput());
    }

    /**
     * 7in88593.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCompile7in88593() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(OTP.OMEGA_7IN88593_OTP.getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocp = cs.compile();

        assertNotNull(ocp);
        assertEquals(1, ocp.getInput());
        assertEquals(2, ocp.getOutput());
    }

}
