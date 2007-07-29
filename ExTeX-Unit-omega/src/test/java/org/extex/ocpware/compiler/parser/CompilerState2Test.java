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

package org.extex.ocpware.compiler.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.extex.ocpware.type.OcpProgram;
import org.extex.ocpware.writer.OcpExTeXWriter;
import org.extex.ocpware.writer.OcpOmegaWriter;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CompilerState2Test extends TestCase {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param in the input contents
     * @param orig the indicator for the output format
     * @param expect the output contents
     * 
     * @throws Exception in case of an error
     */
    private void run(String in, boolean orig, String expect) throws Exception {

        InputStream stream = new ByteArrayInputStream(in.getBytes());

        CompilerState cs = new CompilerState();
        cs.parse(stream);
        stream.close();
        OcpProgram ocp = cs.compile();

        assertNotNull(ocp);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (orig) {
            new OcpOmegaWriter().write(baos, ocp);
        } else {
            new OcpExTeXWriter().write(baos, ocp);
        }
        baos.close();
        assertEquals("Compiler result", expect, baos.toString());
    }

    /**
     * lunatesigma.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse1() throws Exception {

        run(
            OTP.OMEGA_LUNATESIGMA_OTP,
            true,
            "ctp_input      : 2 (2)\n" //
                    + "ctp_output     : 2 (2)\n"
                    + "ctp_no_tables  : 0 (0)\n"
                    + "ctp_room_tables: 0 (0)\n"
                    + "ctp_no_states  : 1 (1)\n"
                    + "ctp_room_states: 15 (21)\n"
                    + "\n"
                    + "State 0 (0): 15 (21) entries\n"
                    + "\n"
                    + "State 0 (0), entry 0 (0): OTP_LEFT_START        0 (0)\n"
                    + "State 0 (0), entry 1 (1): OTP_GOTO_NE           3a3 (931)\n"
                    + "State 0 (0), entry 2 (2):                       5 (5)\n"
                    + "State 0 (0), entry 3 (3): OTP_RIGHT_NUM         3fe (1022)\n"
                    + "State 0 (0), entry 4 (4): OTP_STOP              0 (0)\n"
                    + "State 0 (0), entry 5 (5): OTP_LEFT_RETURN       0 (0)\n"
                    + "State 0 (0), entry 6 (6): OTP_GOTO_NE           3c2 (962)\n"
                    + "State 0 (0), entry 7 (7):                       a (10)\n"
                    + "State 0 (0), entry 8 (8): OTP_RIGHT_NUM         3f2 (1010)\n"
                    + "State 0 (0), entry 9 (9): OTP_STOP              0 (0)\n"
                    + "State 0 (0), entry a (10): OTP_LEFT_RETURN      0 (0)\n"
                    + "State 0 (0), entry b (11): OTP_GOTO_NE          3c3 (963)\n"
                    + "State 0 (0), entry c (12):                      f (15)\n"
                    + "State 0 (0), entry d (13): OTP_RIGHT_NUM        3f2 (1010)\n"
                    + "State 0 (0), entry e (14): OTP_STOP             0 (0)\n"
                    + "State 0 (0), entry f (15): OTP_LEFT_RETURN      0 (0)\n"
                    + "State 0 (0), entry 10 (16): OTP_RIGHT_CHAR      1 (1)\n"
                    + "State 0 (0), entry 11 (17): OTP_STOP            0 (0)\n"
                    + "State 0 (0), entry 12 (18): OTP_LEFT_RETURN     0 (0)\n"
                    + "State 0 (0), entry 13 (19): OTP_RIGHT_CHAR      1 (1)\n"
                    + "State 0 (0), entry 14 (20): OTP_STOP            0 (0)\n"
        // + "\n"
        // + "file length should be: 1d( 29)\n"
        // + "number words read : 1d( 29)\n"
        );
    }

    /**
     * lunatesigma.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse1n() throws Exception {

        run(OTP.OMEGA_LUNATESIGMA_OTP, false, "ctp_input      : 2 (2)\n" //
                + "ctp_output     : 2 (2)\n"
                + "ctp_no_tables  : 0 (0)\n"
                + "ctp_room_tables: 0 (0)\n"
                + "ctp_no_states  : 1 (1)\n"
                + "ctp_room_states: 15 (21)\n"
                + "\n"
                + "State 0 (0): 15 (21) entries\n"
                + "\n"
                + "          0  LEFT_START      0 (0)\n"
                + "          1  GOTO_NE         3a3 (931)\n"
                + "          2                  5 (5)\n"
                + "          3  RIGHT_NUM       3fe (1022)\n"
                + "          4  STOP            0 (0)\n"
                + "          5  LEFT_RETURN     0 (0)\n"
                + "          6  GOTO_NE         3c2 (962)\n"
                + "          7                  a (10)\n"
                + "          8  RIGHT_NUM       3f2 (1010)\n"
                + "          9  STOP            0 (0)\n"
                + "          a  LEFT_RETURN     0 (0)\n"
                + "          b  GOTO_NE         3c3 (963)\n"
                + "          c                  f (15)\n"
                + "          d  RIGHT_NUM       3f2 (1010)\n"
                + "          e  STOP            0 (0)\n"
                + "          f  LEFT_RETURN     0 (0)\n"
                + "         10  RIGHT_CHAR      1 (1)\n"
                + "         11  STOP            0 (0)\n"
                + "         12  LEFT_RETURN     0 (0)\n"
                + "         13  RIGHT_CHAR      1 (1)\n"
                + "         14  STOP            0 (0)\n");
    }

}
