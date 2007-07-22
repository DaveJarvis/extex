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

import org.extex.ocpware.type.OcpProgram;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OtpCompilerTest extends TestCase {

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCompile1() throws Exception {

        String s =
                "input: 1;\r\n" + "output: 2;\r\n" + "\r\n"
                        + "states: VERBATIM;\r\n" + "\r\n" + "expressions:\r\n"
                        + "\r\n" + "`-'`-'`-' => @\"2014;\r\n"
                        + "`-'`-' => @\"2013;\r\n" + "%`-' => @\"2010;\r\n"
                        + "``'``' => @\"201C;\r\n" + "``' => @\"2018;\r\n"
                        + "`''`'' => @\"201D;\r\n" + "`'' => @\"2019;\r\n"
                        + "`,'`,' => @\"201E;\r\n" + "`<'`<' => @\"00AB;\r\n"
                        + "`>'`>' => @\"00BB;\r\n" + "\r\n"
                        + "@\"F000 => <push: VERBATIM> ;\r\n" + "\r\n"
                        + "<VERBATIM>@\"0021-@\"007F => #(\\1 + @\"F000) ;\r\n"
                        + "\r\n" + "<VERBATIM>@\"F001 => <pop:> ;\r\n" + "\r\n"
                        + ". => \\1;\r\n" + "";
        InputStream stream = new ByteArrayInputStream(s.getBytes());

        OcpProgram ocp = OtpCompiler.compile(stream);
        assertNotNull(ocp);
        assertEquals(1, ocp.getInput());
        assertEquals(2, ocp.getOutput());
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCompile2() throws Exception {

        String s =
                "input: 2;\r\n"
                        + "output: 2;\r\n"
                        + "\r\n"
                        + "expressions:\r\n"
                        + "\r\n"
                        + "%`f\'`f\'`i\'`j\' => @\"022C; % for Dutch only\r\n"
                        + "`f\'`f\'`i\' => @\"0223;\r\n"
                        + "`f\'`f\'`l\' => @\"0224;\r\n"
                        + "`f\'`f\'`j\' => @\"022A;\r\n"
                        + "`f\'`f\'@\"012F => @\"0226;\r\n"
                        + "`f\'`f\'@\"0142 => @\"0228;\r\n"
                        + "`f\'`f\'@\"013A => @\"022E;\r\n"
                        + "`f\'`f\'@\"013C => @\"0230;\r\n"
                        + "`f\'`f\'@\"013E => @\"0232;\r\n"
                        + "`f\'`f\'@\"0140 => @\"0234;\r\n"
                        + "`f\'`f\' => @\"0220;\r\n"
                        + "%`f\'`i\'`j\' => @\"022B; % for Dutch only\r\n"
                        + "`f\'`i\' => @\"0221;\r\n"
                        + "`f\'`l\' => @\"0222;\r\n"
                        + "`f\'`j\' => @\"0229;\r\n"
                        + "`f\'@\"012F => @\"0225;\r\n"
                        + "`f\'@\"0142 => @\"0227;\r\n"
                        + "`f\'@\"013A => @\"022D;\r\n"
                        + "`f\'@\"013C => @\"022F;\r\n"
                        + "`f\'@\"013E => @\"0231;\r\n"
                        + "`f\'@\"0140 => @\"0233;\r\n"
                        + "%\r\n"
                        + "% s-long ligatures\r\n"
                        + "%\r\n"
                        + "@\"017F@\"017F`i\' => @\"0238;\r\n"
                        + "@\"017F@\"017F`l\' => @\"0239;\r\n"
                        + "@\"017F@\"017F => @\"0235;\r\n"
                        + "@\"017F`i\' => @\"0236;\r\n"
                        + "@\"017F`l\' => @\"0237;\r\n"
                        + "% French calligraphic ones\r\n"
                        + "%`s\'`t\' => @\"023A;\r\n"
                        + "%`c\'`t\' => @\"023B;\r\n"
                        + "\r\n"
                        + "%\r\n"
                        + "% Tifinagh ligatures\r\n"
                        + "%\r\n"
                        + "% slanted l and n\r\n"
                        + "(@\"0C4E|@\"0C4C)(@\"0C45|@\"0C4F)@\"0C4E => #(\\1) @\"0C82 ;\r\n"
                        + "(@\"0C4E|@\"0C4C)@\"0C4E => #(\\1) @\"0C82 ;\r\n"
                        + "(@\"0C4E|@\"0C4C)(@\"0C45|@\"0C4F)@\"0C4C => #(\\1) @\"0C81 ;\r\n"
                        + "(@\"0C4E|@\"0C4C)@\"0C4C => #(\\1) @\"0C81 ;\r\n"
                        + "% ng\r\n"
                        + "@\"0C4E(@\"0C45|@\"0C4F)@\"0C47 => @\"0C80 ;\r\n"
                        + "@\"0C4E@\"0C47 => @\"0C80 ;\r\n"
                        + "% bt, ct, etc.\r\n"
                        + "@\"0C42(@\"0C45|@\"0C4F)@\"0C54 => @\"0C83 ;\r\n"
                        + "@\"0C42@\"0C54 => @\"0C83 ;\r\n"
                        + "@\"0C43(@\"0C45|@\"0C4F)@\"0C54 => @\"0C84 ;\r\n"
                        + "@\"0C43@\"0C54 => @\"0C84 ;\r\n"
                        + "@\"0C47(@\"0C45|@\"0C4F)@\"0C54 => @\"0C86 ;\r\n"
                        + "@\"0C47@\"0C54 => @\"0C86 ;\r\n"
                        + "@\"0C4D(@\"0C45|@\"0C4F)@\"0C54 => @\"0C87 ;\r\n"
                        + "@\"0C4D@\"0C54 => @\"0C87 ;\r\n"
                        + "@\"0C52(@\"0C45|@\"0C4F)@\"0C54 => @\"0C88 ;\r\n"
                        + "@\"0C52@\"0C54 => @\"0C88 ;\r\n"
                        + "@\"0C53(@\"0C45|@\"0C4F)@\"0C54 => @\"0C89 ;\r\n"
                        + "@\"0C53@\"0C54 => @\"0C89 ;\r\n"
                        + "@\"0C7A(@\"0C45|@\"0C4F)@\"0C54 => @\"0C8A ;\r\n"
                        + "@\"0C7A@\"0C54 => @\"0C8A ;\r\n"
                        + "\r\n"
                        + "@\"2010-@\"2046 => #(\\1 - @\"1000);\r\n"
                        + "\r\n"
                        + "%\r\n"
                        + "% Latin extended additional (0600-06FF)\r\n"
                        + "%\r\n"
                        + "@\"1E00-@\"1EFF => #(\\1 - @\"1800);\r\n"
                        + "\r\n"
                        + "%\r\n"
                        + "% Verbatim mode\r\n"
                        + "@\"F023 => @\"0083;\r\n"
                        + "@\"F024 => @\"0084;\r\n"
                        + "@\"F025 => @\"0085;\r\n"
                        + "@\"F026 => @\"0086;\r\n"
                        + "@\"F07B => @\"008B;\r\n"
                        + "@\"F05C => @\"008C;\r\n"
                        + "@\"F07D => @\"008D;\r\n"
                        + "@\"F05E => @\"008E;\r\n"
                        + "@\"F05F => @\"008F;\r\n"
                        + "@\"F07E => @\"0080;\r\n"
                        + "@\"F000-@\"F07F => \"\\string\" #(\\1 - @\"F000) ; \r\n"
                        + "\r\n" + ". => \\1;\r\n" + "\r\n" + "\r\n" + "";
        InputStream stream = new ByteArrayInputStream(s.getBytes());

        OcpProgram ocp = OtpCompiler.compile(stream);
        assertNotNull(ocp);
        assertEquals(2, ocp.getInput());
        assertEquals(2, ocp.getOutput());
    }

    /**
     * ...
     * 
     * 7in88593.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCompile3() throws Exception {

        String s =
                "input: 1;\r\n"
                        + "output: 2;\r\n"
                        + "\r\n"
                        + "tables:\r\n"
                        + " \r\n"
                        + "tab8859_3[@\"60] = {\r\n"
                        + " @\"00A0, @\"0126, @\"02D8, @\"00A3, @\"00A4, @\"FFFD, @\"0124, @\"00A7,\r\n"
                        + " @\"00A8, @\"0130, @\"015E, @\"011E, @\"0134, @\"00AD, @\"FFFD, @\"017B,\r\n"
                        + " @\"00B0, @\"0127, @\"00B2, @\"00B3, @\"00B4, @\"00B5, @\"0125, @\"00B7,\r\n"
                        + " @\"00B8, @\"0131, @\"015F, @\"011F, @\"0135, @\"00BD, @\"FFFD, @\"017C,\r\n"
                        + " @\"00C0, @\"00C1, @\"00C2, @\"FFFD, @\"00C4, @\"010A, @\"0108, @\"00C7,\r\n"
                        + " @\"00C8, @\"00C9, @\"00CA, @\"00CB, @\"00CC, @\"00CD, @\"00CE, @\"00CF,\r\n"
                        + " @\"FFFD, @\"00D1, @\"00D2, @\"00D3, @\"00D4, @\"0120, @\"00D6, @\"00D7,\r\n"
                        + " @\"011C, @\"00D9, @\"00DA, @\"00DB, @\"00DC, @\"016C, @\"015C, @\"00DF,\r\n"
                        + " @\"00E0, @\"00E1, @\"00E2, @\"FFFD, @\"00E4, @\"010B, @\"0109, @\"00E7,\r\n"
                        + " @\"00E8, @\"00E9, @\"00EA, @\"00EB, @\"00EC, @\"00ED, @\"00EE, @\"00EF,\r\n"
                        + " @\"FFFD, @\"00F1, @\"00F2, @\"00F3, @\"00F4, @\"0121, @\"00F6, @\"00F7,\r\n"
                        + " @\"011D, @\"00F9, @\"00FA, @\"00FB, @\"00FC, @\"016D, @\"015D, @\"02D9\r\n"
                        + "};\r\n" + "\r\n" + "expressions:\r\n"
                        + "`<\'`C\'  => #(@\"0108) ;\r\n"
                        + "`<\'`c\'  => #(@\"0109) ;\r\n"
                        + "`<\'`G\'  => #(@\"011C) ;\r\n"
                        + "`<\'`g\'  => #(@\"011D) ;\r\n"
                        + "`<\'`H\'  => #(@\"0124) ;\r\n"
                        + "`<\'`h\'  => #(@\"0125) ;\r\n"
                        + "`<\'`J\'  => #(@\"0134) ;\r\n"
                        + "`<\'`j\'  => #(@\"0135) ;\r\n"
                        + "`<\'`S\'  => #(@\"015C) ;\r\n"
                        + "`<\'`s\'  => #(@\"015D) ;\r\n" + "\r\n"
                        + "@\"00-@\"9F   => \\1;\r\n"
                        + "@\"A0-@\"FF   => #(tab8859_3[\\1-@\"A0]);\r\n"
                        + "%.      => @\"FFFD;\r\n" + ". => \\1;\r\n" + "\r\n";
        InputStream stream = new ByteArrayInputStream(s.getBytes());

        OcpProgram ocp = OtpCompiler.compile(stream);
        assertNotNull(ocp);
        assertEquals(1, ocp.getInput());
        assertEquals(2, ocp.getOutput());
    }

}
