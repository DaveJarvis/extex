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

import java.util.Locale;

import org.extex.ocpware.compiler.OTP;
import org.extex.ocpware.writer.OcpExTeXWriter;
import org.junit.Test;

/**
 * This is a test suite for the OcpExTeXWriter.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class OcpExTeXWriterTest extends WriterTester {

    /**
     * lunatesigma.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLunatesigma() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_LUNATESIGMA_OTP, new OcpExTeXWriter(), //
            "% length = 29\n" //
                    + "% 0 tables in 0 words\n" //
                    + "% 1 states in 21 words\n\n" //
                    + "input = 2\n" //
                    + "output = 2\n" //
                    + "\n" //
                    + "State _S0: % 21 entries\n\n" //
                    + "                LEFT_START\n" //
                    + "                GOTO_NE         0x3a3, _1\n" //
                    + "                RIGHT_NUM       0x3fe\n" //
                    + "                STOP\n" //
                    + "  _1:           LEFT_RETURN\n" //
                    + "                GOTO_NE         0x3c2, _2\n" //
                    + "                RIGHT_NUM       0x3f2\n" //
                    + "                STOP\n" //
                    + "  _2:           LEFT_RETURN\n" //
                    + "                GOTO_NE         0x3c3, _3\n" //
                    + "                RIGHT_NUM       0x3f2\n" //
                    + "                STOP\n" //
                    + "  _3:           LEFT_RETURN\n" //
                    + "                RIGHT_CHAR      1\n" //
                    + "                STOP\n" //
                    + "                LEFT_RETURN\n" //
                    + "                RIGHT_CHAR      1\n" //
                    + "                STOP\n");
    }

    /**
     * destroy.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParseDestroy() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_DESTROY_OTP, new OcpExTeXWriter(), "% length = 14\n"
                + "% 0 tables in 0 words\n" + "% 1 states in 6 words\n" + "\n"
                + "input = 1\n" + "output = 1\n" + "\n"
                + "State _S0: % 6 entries\n" + "\n"
                + "                LEFT_START\n"
                + "                RIGHT_NUM       0x2a\n"
                + "                STOP\n" + "                LEFT_RETURN\n"
                + "                RIGHT_CHAR      1\n"
                + "                STOP\n");
    }

    /**
     * destroy.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse7in88593() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_7IN88593_OTP, new OcpExTeXWriter(), "% length = 220\n"
                + "% 1 tables in 96 words\n"
                + "% 1 states in 115 words\n"
                + "\n"
                + "input = 1\n"
                + "output = 2\n"
                + "\ntables:\n"
                + "table 0[] = { % 60 entries\n\n" //
                + "  0x00a0, 0x0126, 0x02d8, 0x00a3,\n"
                + "  0x00a4, 0xfffd, 0x0124, 0x00a7,\n"
                + "  0x00a8, 0x0130, 0x015e, 0x011e,\n"
                + "  0x0134, 0x00ad, 0xfffd, 0x017b,\n"
                + "  0x00b0, 0x0127, 0x00b2, 0x00b3,\n"
                + "  0x00b4, 0x00b5, 0x0125, 0x00b7,\n"
                + "  0x00b8, 0x0131, 0x015f, 0x011f,\n"
                + "  0x0135, 0x00bd, 0xfffd, 0x017c,\n"
                + "  0x00c0, 0x00c1, 0x00c2, 0xfffd,\n"
                + "  0x00c4, 0x010a, 0x0108, 0x00c7,\n"
                + "  0x00c8, 0x00c9, 0x00ca, 0x00cb,\n"
                + "  0x00cc, 0x00cd, 0x00ce, 0x00cf,\n"
                + "  0xfffd, 0x00d1, 0x00d2, 0x00d3,\n"
                + "  0x00d4, 0x0120, 0x00d6, 0x00d7,\n"
                + "  0x011c, 0x00d9, 0x00da, 0x00db,\n"
                + "  0x00dc, 0x016c, 0x015c, 0x00df,\n"
                + "  0x00e0, 0x00e1, 0x00e2, 0xfffd,\n"
                + "  0x00e4, 0x010b, 0x0109, 0x00e7,\n"
                + "  0x00e8, 0x00e9, 0x00ea, 0x00eb,\n"
                + "  0x00ec, 0x00ed, 0x00ee, 0x00ef,\n"
                + "  0xfffd, 0x00f1, 0x00f2, 0x00f3,\n"
                + "  0x00f4, 0x0121, 0x00f6, 0x00f7,\n"
                + "  0x011d, 0x00f9, 0x00fa, 0x00fb,\n"
                + "  0x00fc, 0x016d, 0x015d, 0x02d9}\n"
                + "\nState _S0: % 115 entries\n" + "\n"
                + "                LEFT_START\n"
                + "                GOTO_NE         0x3c, _1\n"
                + "                GOTO_NO_ADVANCE _1\n"
                + "                GOTO_NE         0x43, _1\n"
                + "                PUSH_NUM        0x108\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _1:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _2\n"
                + "                GOTO_NO_ADVANCE _2\n"
                + "                GOTO_NE         0x63, _2\n"
                + "                PUSH_NUM        0x109\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _2:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _3\n"
                + "                GOTO_NO_ADVANCE _3\n"
                + "                GOTO_NE         0x47, _3\n"
                + "                PUSH_NUM        0x11c\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _3:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _4\n"
                + "                GOTO_NO_ADVANCE _4\n"
                + "                GOTO_NE         0x67, _4\n"
                + "                PUSH_NUM        0x11d\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _4:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _5\n"
                + "                GOTO_NO_ADVANCE _5\n"
                + "                GOTO_NE         0x48, _5\n"
                + "                PUSH_NUM        0x124\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _5:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _6\n"
                + "                GOTO_NO_ADVANCE _6\n"
                + "                GOTO_NE         0x68, _6\n"
                + "                PUSH_NUM        0x125\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _6:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _7\n"
                + "                GOTO_NO_ADVANCE _7\n"
                + "                GOTO_NE         0x4a, _7\n"
                + "                PUSH_NUM        0x134\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _7:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _8\n"
                + "                GOTO_NO_ADVANCE _8\n"
                + "                GOTO_NE         0x6a, _8\n"
                + "                PUSH_NUM        0x135\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _8:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _9\n"
                + "                GOTO_NO_ADVANCE _9\n"
                + "                GOTO_NE         0x53, _9\n"
                + "                PUSH_NUM        0x15c\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _9:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _10\n"
                + "                GOTO_NO_ADVANCE _10\n"
                + "                GOTO_NE         0x73, _10\n"
                + "                PUSH_NUM        0x15d\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _10:          LEFT_RETURN\n"
                + "                GOTO_LT         0x0, _11\n"
                + "                GOTO_GT         0x9f, _11\n"
                + "                RIGHT_CHAR      1\n"
                + "                STOP\n" + "  _11:          LEFT_RETURN\n"
                + "                GOTO_LT         0xa0, _12\n"
                + "                GOTO_GT         0xff, _12\n"
                + "                PUSH_NUM        0x0\n"
                + "                PUSH_CHAR       1\n"
                + "                PUSH_NUM        0xa0\n"
                + "                SUB\n" + "                LOOKUP\n"
                + "                RIGHT_OUTPUT\n" + "                STOP\n"
                + "  _12:          LEFT_RETURN\n"
                + "                RIGHT_CHAR      1\n"
                + "                STOP\n" + "                LEFT_RETURN\n"
                + "                RIGHT_CHAR      1\n"
                + "                STOP\n");
    }

    /**
     * destroy.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParseLat2uni() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_LAT2UNI_OTP, new OcpExTeXWriter(), "% length = 107\n"
                + "% 0 tables in 0 words\n" + "% 2 states in 98 words\n" + "\n"
                + "input = 1\n" + "output = 2\n" + "\n"
                + "State _S0: % 80 entries\n" + "\n"
                + "                LEFT_START\n"
                + "                GOTO_NE         0x2d, _1\n"
                + "                GOTO_NO_ADVANCE _1\n"
                + "                GOTO_NE         0x2d, _1\n"
                + "                GOTO_NO_ADVANCE _1\n"
                + "                GOTO_NE         0x2d, _1\n"
                + "                RIGHT_NUM       0x2014\n"
                + "                STOP\n" + "  _1:           LEFT_RETURN\n"
                + "                GOTO_NE         0x2d, _2\n"
                + "                GOTO_NO_ADVANCE _2\n"
                + "                GOTO_NE         0x2d, _2\n"
                + "                RIGHT_NUM       0x2013\n"
                + "                STOP\n" + "  _2:           LEFT_RETURN\n"
                + "                GOTO_NE         0x60, _3\n"
                + "                GOTO_NO_ADVANCE _3\n"
                + "                GOTO_NE         0x60, _3\n"
                + "                RIGHT_NUM       0x201c\n"
                + "                STOP\n" + "  _3:           LEFT_RETURN\n"
                + "                GOTO_NE         0x60, _4\n"
                + "                RIGHT_NUM       0x2018\n"
                + "                STOP\n" + "  _4:           LEFT_RETURN\n"
                + "                GOTO_NE         0x27, _5\n"
                + "                GOTO_NO_ADVANCE _5\n"
                + "                GOTO_NE         0x27, _5\n"
                + "                RIGHT_NUM       0x201d\n"
                + "                STOP\n" + "  _5:           LEFT_RETURN\n"
                + "                GOTO_NE         0x27, _6\n"
                + "                RIGHT_NUM       0x2019\n"
                + "                STOP\n" + "  _6:           LEFT_RETURN\n"
                + "                GOTO_NE         0x2c, _7\n"
                + "                GOTO_NO_ADVANCE _7\n"
                + "                GOTO_NE         0x2c, _7\n"
                + "                RIGHT_NUM       0x201e\n"
                + "                STOP\n" + "  _7:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3c, _8\n"
                + "                GOTO_NO_ADVANCE _8\n"
                + "                GOTO_NE         0x3c, _8\n"
                + "                RIGHT_NUM       0xab\n"
                + "                STOP\n" + "  _8:           LEFT_RETURN\n"
                + "                GOTO_NE         0x3e, _9\n"
                + "                GOTO_NO_ADVANCE _9\n"
                + "                GOTO_NE         0x3e, _9\n"
                + "                RIGHT_NUM       0xbb\n"
                + "                STOP\n" + "  _9:           LEFT_RETURN\n"
                + "                GOTO_NE         0xf000, _10\n"
                + "                STATE_PUSH      _S1\n"
                + "                STOP\n" + "  _10:          LEFT_RETURN\n"
                + "                RIGHT_CHAR      1\n"
                + "                STOP\n" + "                LEFT_RETURN\n"
                + "                RIGHT_CHAR      1\n"
                + "                STOP\n" + "\n" + "State _S1: % 18 entries\n"
                + "\n" + "                LEFT_START\n"
                + "                GOTO_LT         0x21, _1\n"
                + "                GOTO_GT         0x7f, _1\n"
                + "                PUSH_CHAR       1\n"
                + "                PUSH_NUM        0xf000\n"
                + "                ADD\n" + "                RIGHT_OUTPUT\n"
                + "                STOP\n" + "  _1:           LEFT_RETURN\n"
                + "                GOTO_NE         0xf001, _2\n"
                + "                STATE_POP\n" + "                STOP\n"
                + "  _2:           LEFT_RETURN\n"
                + "                RIGHT_CHAR      1\n"
                + "                STOP\n");
    }

    /**
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run("input: 1;output: 1;" + "expressions: .. => \\*;\n. => \\*;",
            new OcpExTeXWriter(), "% length = 18\n" + "% 0 tables in 0 words\n"
                    + "% 1 states in 10 words\n" + "\n" + "input = 1\n"
                    + "output = 1\n" + "\n" + "State _S0: % 10 entries\n"
                    + "\n" + "                LEFT_START\n"
                    + "                GOTO_NO_ADVANCE _1\n"
                    + "                RIGHT_SOME      0, 0\n"
                    + "  _1:           LEFT_RETURN\n"
                    + "                RIGHT_SOME      0, 0\n"
                    + "                LEFT_RETURN\n"
                    + "                RIGHT_CHAR      1\n"
                    + "                STOP\n" + "");
    }

}
