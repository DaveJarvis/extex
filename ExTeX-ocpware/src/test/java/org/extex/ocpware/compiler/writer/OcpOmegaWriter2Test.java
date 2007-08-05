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
import org.extex.ocpware.writer.OcpOmegaWriter2;
import org.junit.Test;

/**
 * This is a test suite for OcpOmegaWriter2.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class OcpOmegaWriter2Test extends WriterTester {

    /**
     * lunatesigma.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLunatesigma() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_LUNATESIGMA_OTP, new OcpOmegaWriter2(),
            "ctp_length     : 28\n"
                    + "ctp_input      : 2\n" //
                    + "ctp_output     : 2\n" + "ctp_no_tables  : 0\n"
                    + "ctp_room_tables: 0\n" + "ctp_no_states  : 1\n"
                    + "ctp_room_states: 21\n" + "\n" + "State 0: 15 entries\n"
                    + "\n" + "          0  LEFT_START      0x0 (0)\n"
                    + "          1  GOTO_NE         0x3a3 (931)\n"
                    + "          2                  0x5 (5)\n"
                    + "          3  RIGHT_NUM       0x3fe (1022)\n"
                    + "          4  STOP            0x0 (0)\n"
                    + "          5  LEFT_RETURN     0x0 (0)\n"
                    + "          6  GOTO_NE         0x3c2 (962)\n"
                    + "          7                  0xa (10)\n"
                    + "          8  RIGHT_NUM       0x3f2 (1010)\n"
                    + "          9  STOP            0x0 (0)\n"
                    + "          a  LEFT_RETURN     0x0 (0)\n"
                    + "          b  GOTO_NE         0x3c3 (963)\n"
                    + "          c                  0xf (15)\n"
                    + "          d  RIGHT_NUM       0x3f2 (1010)\n"
                    + "          e  STOP            0x0 (0)\n"
                    + "          f  LEFT_RETURN     0x0 (0)\n"
                    + "         10  RIGHT_CHAR      0x1 (1)\n"
                    + "         11  STOP            0x0 (0)\n"
                    + "         12  LEFT_RETURN     0x0 (0)\n"
                    + "         13  RIGHT_CHAR      0x1 (1)\n"
                    + "         14  STOP            0x0 (0)\n");
    }

    /**
     * destroy.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParseDestroyN() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_DESTROY_OTP, new OcpOmegaWriter2(),
            "ctp_length     : 13\n"
                    + "ctp_input      : 1\n" //
                    + "ctp_output     : 1\n" + "ctp_no_tables  : 0\n"
                    + "ctp_room_tables: 0\n" + "ctp_no_states  : 1\n"
                    + "ctp_room_states: 6\n" + "\n" + "State 0: 6 entries\n"
                    + "\n" + "          0  LEFT_START      0x0 (0)\n"
                    + "          1  RIGHT_NUM       0x2a (42,`*\')\n"
                    + "          2  STOP            0x0 (0)\n"
                    + "          3  LEFT_RETURN     0x0 (0)\n"
                    + "          4  RIGHT_CHAR      0x1 (1)\n"
                    + "          5  STOP            0x0 (0)\n");
    }

    /**
     * destroy.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse7in88593() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_7IN88593_OTP, new OcpOmegaWriter2(),
            "ctp_length     : 219\n" + "ctp_input      : 1\n"
                    + "ctp_output     : 2\n" + "ctp_no_tables  : 1\n"
                    + "ctp_room_tables: 96\n" + "ctp_no_states  : 1\n"
                    + "ctp_room_states: 115\n" + "\n"
                    + "Table   0:  60 entries\n" + "\n" + "    a0(160),\n"
                    + "    126(294),\n" + "    2d8(728),\n" + "    a3(163),\n"
                    + "    a4(164),\n" + "    fffd(65533),\n"
                    + "    124(292),\n" + "    a7(167),\n" + "    a8(168),\n"
                    + "    130(304),\n" + "    15e(350),\n" + "    11e(286),\n"
                    + "    134(308),\n" + "    ad(173),\n"
                    + "    fffd(65533),\n" + "    17b(379),\n"
                    + "    b0(176),\n" + "    127(295),\n" + "    b2(178),\n"
                    + "    b3(179),\n" + "    b4(180),\n" + "    b5(181),\n"
                    + "    125(293),\n" + "    b7(183),\n" + "    b8(184),\n"
                    + "    131(305),\n" + "    15f(351),\n" + "    11f(287),\n"
                    + "    135(309),\n" + "    bd(189),\n"
                    + "    fffd(65533),\n" + "    17c(380),\n"
                    + "    c0(192),\n" + "    c1(193),\n" + "    c2(194),\n"
                    + "    fffd(65533),\n" + "    c4(196),\n"
                    + "    10a(266),\n" + "    108(264),\n" + "    c7(199),\n"
                    + "    c8(200),\n" + "    c9(201),\n" + "    ca(202),\n"
                    + "    cb(203),\n" + "    cc(204),\n" + "    cd(205),\n"
                    + "    ce(206),\n" + "    cf(207),\n"
                    + "    fffd(65533),\n" + "    d1(209),\n"
                    + "    d2(210),\n" + "    d3(211),\n" + "    d4(212),\n"
                    + "    120(288),\n" + "    d6(214),\n" + "    d7(215),\n"
                    + "    11c(284),\n" + "    d9(217),\n" + "    da(218),\n"
                    + "    db(219),\n" + "    dc(220),\n" + "    16c(364),\n"
                    + "    15c(348),\n" + "    df(223),\n" + "    e0(224),\n"
                    + "    e1(225),\n" + "    e2(226),\n"
                    + "    fffd(65533),\n" + "    e4(228),\n"
                    + "    10b(267),\n" + "    109(265),\n" + "    e7(231),\n"
                    + "    e8(232),\n" + "    e9(233),\n" + "    ea(234),\n"
                    + "    eb(235),\n" + "    ec(236),\n" + "    ed(237),\n"
                    + "    ee(238),\n" + "    ef(239),\n"
                    + "    fffd(65533),\n" + "    f1(241),\n"
                    + "    f2(242),\n" + "    f3(243),\n" + "    f4(244),\n"
                    + "    121(289),\n" + "    f6(246),\n" + "    f7(247),\n"
                    + "    11d(285),\n" + "    f9(249),\n" + "    fa(250),\n"
                    + "    fb(251),\n" + "    fc(252),\n" + "    16d(365),\n"
                    + "    15d(349),\n" + "    2d9(729),\n" + "\n"
                    + "State 0: 73 entries\n" + "\n"
                    + "          0  LEFT_START      0x0 (0)\n"
                    + "          1  GOTO_NE         0x3c (60,`<\')\n"
                    + "          2                  0x9 (9)\n"
                    + "          3  GOTO_NO_ADVANCE 0x9 (9)\n"
                    + "          4  GOTO_NE         0x43 (67,`C\')\n"
                    + "          5                  0x9 (9)\n"
                    + "          6  PUSH_NUM        0x108 (264)\n"
                    + "          7  RIGHT_OUTPUT    0x0 (0)\n"
                    + "          8  STOP            0x0 (0)\n"
                    + "          9  LEFT_RETURN     0x0 (0)\n"
                    + "          a  GOTO_NE         0x3c (60,`<\')\n"
                    + "          b                  0x12 (18)\n"
                    + "          c  GOTO_NO_ADVANCE 0x12 (18)\n"
                    + "          d  GOTO_NE         0x63 (99,`c\')\n"
                    + "          e                  0x12 (18)\n"
                    + "          f  PUSH_NUM        0x109 (265)\n"
                    + "         10  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         11  STOP            0x0 (0)\n"
                    + "         12  LEFT_RETURN     0x0 (0)\n"
                    + "         13  GOTO_NE         0x3c (60,`<\')\n"
                    + "         14                  0x1b (27)\n"
                    + "         15  GOTO_NO_ADVANCE 0x1b (27)\n"
                    + "         16  GOTO_NE         0x47 (71,`G\')\n"
                    + "         17                  0x1b (27)\n"
                    + "         18  PUSH_NUM        0x11c (284)\n"
                    + "         19  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         1a  STOP            0x0 (0)\n"
                    + "         1b  LEFT_RETURN     0x0 (0)\n"
                    + "         1c  GOTO_NE         0x3c (60,`<\')\n"
                    + "         1d                  0x24 (36,`$\')\n"
                    + "         1e  GOTO_NO_ADVANCE 0x24 (36,`$\')\n"
                    + "         1f  GOTO_NE         0x67 (103,`g\')\n"
                    + "         20                  0x24 (36,`$\')\n"
                    + "         21  PUSH_NUM        0x11d (285)\n"
                    + "         22  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         23  STOP            0x0 (0)\n"
                    + "         24  LEFT_RETURN     0x0 (0)\n"
                    + "         25  GOTO_NE         0x3c (60,`<\')\n"
                    + "         26                  0x2d (45,`-\')\n"
                    + "         27  GOTO_NO_ADVANCE 0x2d (45,`-\')\n"
                    + "         28  GOTO_NE         0x48 (72,`H\')\n"
                    + "         29                  0x2d (45,`-\')\n"
                    + "         2a  PUSH_NUM        0x124 (292)\n"
                    + "         2b  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         2c  STOP            0x0 (0)\n"
                    + "         2d  LEFT_RETURN     0x0 (0)\n"
                    + "         2e  GOTO_NE         0x3c (60,`<\')\n"
                    + "         2f                  0x36 (54,`6\')\n"
                    + "         30  GOTO_NO_ADVANCE 0x36 (54,`6\')\n"
                    + "         31  GOTO_NE         0x68 (104,`h\')\n"
                    + "         32                  0x36 (54,`6\')\n"
                    + "         33  PUSH_NUM        0x125 (293)\n"
                    + "         34  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         35  STOP            0x0 (0)\n"
                    + "         36  LEFT_RETURN     0x0 (0)\n"
                    + "         37  GOTO_NE         0x3c (60,`<\')\n"
                    + "         38                  0x3f (63,`?\')\n"
                    + "         39  GOTO_NO_ADVANCE 0x3f (63,`?\')\n"
                    + "         3a  GOTO_NE         0x4a (74,`J\')\n"
                    + "         3b                  0x3f (63,`?\')\n"
                    + "         3c  PUSH_NUM        0x134 (308)\n"
                    + "         3d  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         3e  STOP            0x0 (0)\n"
                    + "         3f  LEFT_RETURN     0x0 (0)\n"
                    + "         40  GOTO_NE         0x3c (60,`<\')\n"
                    + "         41                  0x48 (72,`H\')\n"
                    + "         42  GOTO_NO_ADVANCE 0x48 (72,`H\')\n"
                    + "         43  GOTO_NE         0x6a (106,`j\')\n"
                    + "         44                  0x48 (72,`H\')\n"
                    + "         45  PUSH_NUM        0x135 (309)\n"
                    + "         46  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         47  STOP            0x0 (0)\n"
                    + "         48  LEFT_RETURN     0x0 (0)\n"
                    + "         49  GOTO_NE         0x3c (60,`<\')\n"
                    + "         4a                  0x51 (81,`Q\')\n"
                    + "         4b  GOTO_NO_ADVANCE 0x51 (81,`Q\')\n"
                    + "         4c  GOTO_NE         0x53 (83,`S\')\n"
                    + "         4d                  0x51 (81,`Q\')\n"
                    + "         4e  PUSH_NUM        0x15c (348)\n"
                    + "         4f  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         50  STOP            0x0 (0)\n"
                    + "         51  LEFT_RETURN     0x0 (0)\n"
                    + "         52  GOTO_NE         0x3c (60,`<\')\n"
                    + "         53                  0x5a (90,`Z\')\n"
                    + "         54  GOTO_NO_ADVANCE 0x5a (90,`Z\')\n"
                    + "         55  GOTO_NE         0x73 (115,`s\')\n"
                    + "         56                  0x5a (90,`Z\')\n"
                    + "         57  PUSH_NUM        0x15d (349)\n"
                    + "         58  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         59  STOP            0x0 (0)\n"
                    + "         5a  LEFT_RETURN     0x0 (0)\n"
                    + "         5b  GOTO_LT         0x0 (0)\n"
                    + "         5c                  0x61 (97,`a\')\n"
                    + "         5d  GOTO_GT         0x9f (159)\n"
                    + "         5e                  0x61 (97,`a\')\n"
                    + "         5f  RIGHT_CHAR      0x1 (1)\n"
                    + "         60  STOP            0x0 (0)\n"
                    + "         61  LEFT_RETURN     0x0 (0)\n"
                    + "         62  GOTO_LT         0xa0 (160)\n"
                    + "         63                  0x6d (109,`m\')\n"
                    + "         64  GOTO_GT         0xff (255)\n"
                    + "         65                  0x6d (109,`m\')\n"
                    + "         66  PUSH_NUM        0x0 (0)\n"
                    + "         67  PUSH_CHAR       0x1 (1)\n"
                    + "         68  PUSH_NUM        0xa0 (160)\n"
                    + "         69  SUB             0x0 (0)\n"
                    + "         6a  LOOKUP          0x0 (0)\n"
                    + "         6b  RIGHT_OUTPUT    0x0 (0)\n"
                    + "         6c  STOP            0x0 (0)\n"
                    + "         6d  LEFT_RETURN     0x0 (0)\n"
                    + "         6e  RIGHT_CHAR      0x1 (1)\n"
                    + "         6f  STOP            0x0 (0)\n"
                    + "         70  LEFT_RETURN     0x0 (0)\n"
                    + "         71  RIGHT_CHAR      0x1 (1)\n"
                    + "         72  STOP            0x0 (0)\n");
    }

}
