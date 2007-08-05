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
import java.util.Locale;

import junit.framework.TestCase;

import org.extex.ocpware.compiler.OTP;
import org.extex.ocpware.type.OcpProgram;
import org.extex.ocpware.writer.OcpOmegaWriter;
import org.extex.ocpware.writer.OcpOmegaWriter2;
import org.extex.ocpware.writer.OcpWriter;
import org.junit.Test;

/**
 * Just another test suite for CompilerState.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class CompilerState2Test extends TestCase {

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

    /**
     * lunatesigma.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLunatesigma() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(OTP.OMEGA_LUNATESIGMA_OTP,
            new OcpOmegaWriter(), //
            "ctp_length     :  1c( 28)\n"
                    + "ctp_input      :   2(  2)\n"
                    + "ctp_output     :   2(  2)\n"
                    + "ctp_no_tables  :   0(  0)\n"
                    + "ctp_room_tables:   0(  0)\n"
                    + "ctp_no_states  :   1(  1)\n"
                    + "ctp_room_states:  15( 21)\n"
                    + "\n"
                    + "State   0(  0):  15( 21) entries\n"
                    + "\n"
                    + "State   0(  0), entry   0(  0): OTP_LEFT_START      0(0)\n"
                    + "State   0(  0), entry   1(  1): OTP_GOTO_NE         3a3(931)\n"
                    + "State   0(  0), entry   2(  2):                     5(5)\n"
                    + "State   0(  0), entry   3(  3): OTP_RIGHT_NUM       3fe(1022)\n"
                    + "State   0(  0), entry   4(  4): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry   5(  5): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry   6(  6): OTP_GOTO_NE         3c2(962)\n"
                    + "State   0(  0), entry   7(  7):                     a(10)\n"
                    + "State   0(  0), entry   8(  8): OTP_RIGHT_NUM       3f2(1010)\n"
                    + "State   0(  0), entry   9(  9): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry   a( 10): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry   b( 11): OTP_GOTO_NE         3c3(963)\n"
                    + "State   0(  0), entry   c( 12):                     f(15)\n"
                    + "State   0(  0), entry   d( 13): OTP_RIGHT_NUM       3f2(1010)\n"
                    + "State   0(  0), entry   e( 14): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry   f( 15): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  10( 16): OTP_RIGHT_CHAR      1(1)\n"
                    + "State   0(  0), entry  11( 17): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  12( 18): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  13( 19): OTP_RIGHT_CHAR      1(1)\n"
                    + "State   0(  0), entry  14( 20): OTP_STOP            0(0)\n"
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
        run(
            OTP.OMEGA_7IN88593_OTP,
            new OcpOmegaWriter(),
            "ctp_length     :  db(219)\n"
                    + "ctp_input      :   1(  1)\n"
                    + "ctp_output     :   2(  2)\n"
                    + "ctp_no_tables  :   1(  1)\n"
                    + "ctp_room_tables:  60( 96)\n"
                    + "ctp_no_states  :   1(  1)\n"
                    + "ctp_room_states:  73(115)\n"
                    + "\n"
                    + "Table   0(  0):  60( 96) entries\n"
                    + "\n"
                    + "Table   0(  0), entry   0(  0): a0(160)\n"
                    + "Table   0(  0), entry   1(  1): 126(294)\n"
                    + "Table   0(  0), entry   2(  2): 2d8(728)\n"
                    + "Table   0(  0), entry   3(  3): a3(163)\n"
                    + "Table   0(  0), entry   4(  4): a4(164)\n"
                    + "Table   0(  0), entry   5(  5): fffd(65533)\n"
                    + "Table   0(  0), entry   6(  6): 124(292)\n"
                    + "Table   0(  0), entry   7(  7): a7(167)\n"
                    + "Table   0(  0), entry   8(  8): a8(168)\n"
                    + "Table   0(  0), entry   9(  9): 130(304)\n"
                    + "Table   0(  0), entry   a( 10): 15e(350)\n"
                    + "Table   0(  0), entry   b( 11): 11e(286)\n"
                    + "Table   0(  0), entry   c( 12): 134(308)\n"
                    + "Table   0(  0), entry   d( 13): ad(173)\n"
                    + "Table   0(  0), entry   e( 14): fffd(65533)\n"
                    + "Table   0(  0), entry   f( 15): 17b(379)\n"
                    + "Table   0(  0), entry  10( 16): b0(176)\n"
                    + "Table   0(  0), entry  11( 17): 127(295)\n"
                    + "Table   0(  0), entry  12( 18): b2(178)\n"
                    + "Table   0(  0), entry  13( 19): b3(179)\n"
                    + "Table   0(  0), entry  14( 20): b4(180)\n"
                    + "Table   0(  0), entry  15( 21): b5(181)\n"
                    + "Table   0(  0), entry  16( 22): 125(293)\n"
                    + "Table   0(  0), entry  17( 23): b7(183)\n"
                    + "Table   0(  0), entry  18( 24): b8(184)\n"
                    + "Table   0(  0), entry  19( 25): 131(305)\n"
                    + "Table   0(  0), entry  1a( 26): 15f(351)\n"
                    + "Table   0(  0), entry  1b( 27): 11f(287)\n"
                    + "Table   0(  0), entry  1c( 28): 135(309)\n"
                    + "Table   0(  0), entry  1d( 29): bd(189)\n"
                    + "Table   0(  0), entry  1e( 30): fffd(65533)\n"
                    + "Table   0(  0), entry  1f( 31): 17c(380)\n"
                    + "Table   0(  0), entry  20( 32): c0(192)\n"
                    + "Table   0(  0), entry  21( 33): c1(193)\n"
                    + "Table   0(  0), entry  22( 34): c2(194)\n"
                    + "Table   0(  0), entry  23( 35): fffd(65533)\n"
                    + "Table   0(  0), entry  24( 36): c4(196)\n"
                    + "Table   0(  0), entry  25( 37): 10a(266)\n"
                    + "Table   0(  0), entry  26( 38): 108(264)\n"
                    + "Table   0(  0), entry  27( 39): c7(199)\n"
                    + "Table   0(  0), entry  28( 40): c8(200)\n"
                    + "Table   0(  0), entry  29( 41): c9(201)\n"
                    + "Table   0(  0), entry  2a( 42): ca(202)\n"
                    + "Table   0(  0), entry  2b( 43): cb(203)\n"
                    + "Table   0(  0), entry  2c( 44): cc(204)\n"
                    + "Table   0(  0), entry  2d( 45): cd(205)\n"
                    + "Table   0(  0), entry  2e( 46): ce(206)\n"
                    + "Table   0(  0), entry  2f( 47): cf(207)\n"
                    + "Table   0(  0), entry  30( 48): fffd(65533)\n"
                    + "Table   0(  0), entry  31( 49): d1(209)\n"
                    + "Table   0(  0), entry  32( 50): d2(210)\n"
                    + "Table   0(  0), entry  33( 51): d3(211)\n"
                    + "Table   0(  0), entry  34( 52): d4(212)\n"
                    + "Table   0(  0), entry  35( 53): 120(288)\n"
                    + "Table   0(  0), entry  36( 54): d6(214)\n"
                    + "Table   0(  0), entry  37( 55): d7(215)\n"
                    + "Table   0(  0), entry  38( 56): 11c(284)\n"
                    + "Table   0(  0), entry  39( 57): d9(217)\n"
                    + "Table   0(  0), entry  3a( 58): da(218)\n"
                    + "Table   0(  0), entry  3b( 59): db(219)\n"
                    + "Table   0(  0), entry  3c( 60): dc(220)\n"
                    + "Table   0(  0), entry  3d( 61): 16c(364)\n"
                    + "Table   0(  0), entry  3e( 62): 15c(348)\n"
                    + "Table   0(  0), entry  3f( 63): df(223)\n"
                    + "Table   0(  0), entry  40( 64): e0(224)\n"
                    + "Table   0(  0), entry  41( 65): e1(225)\n"
                    + "Table   0(  0), entry  42( 66): e2(226)\n"
                    + "Table   0(  0), entry  43( 67): fffd(65533)\n"
                    + "Table   0(  0), entry  44( 68): e4(228)\n"
                    + "Table   0(  0), entry  45( 69): 10b(267)\n"
                    + "Table   0(  0), entry  46( 70): 109(265)\n"
                    + "Table   0(  0), entry  47( 71): e7(231)\n"
                    + "Table   0(  0), entry  48( 72): e8(232)\n"
                    + "Table   0(  0), entry  49( 73): e9(233)\n"
                    + "Table   0(  0), entry  4a( 74): ea(234)\n"
                    + "Table   0(  0), entry  4b( 75): eb(235)\n"
                    + "Table   0(  0), entry  4c( 76): ec(236)\n"
                    + "Table   0(  0), entry  4d( 77): ed(237)\n"
                    + "Table   0(  0), entry  4e( 78): ee(238)\n"
                    + "Table   0(  0), entry  4f( 79): ef(239)\n"
                    + "Table   0(  0), entry  50( 80): fffd(65533)\n"
                    + "Table   0(  0), entry  51( 81): f1(241)\n"
                    + "Table   0(  0), entry  52( 82): f2(242)\n"
                    + "Table   0(  0), entry  53( 83): f3(243)\n"
                    + "Table   0(  0), entry  54( 84): f4(244)\n"
                    + "Table   0(  0), entry  55( 85): 121(289)\n"
                    + "Table   0(  0), entry  56( 86): f6(246)\n"
                    + "Table   0(  0), entry  57( 87): f7(247)\n"
                    + "Table   0(  0), entry  58( 88): 11d(285)\n"
                    + "Table   0(  0), entry  59( 89): f9(249)\n"
                    + "Table   0(  0), entry  5a( 90): fa(250)\n"
                    + "Table   0(  0), entry  5b( 91): fb(251)\n"
                    + "Table   0(  0), entry  5c( 92): fc(252)\n"
                    + "Table   0(  0), entry  5d( 93): 16d(365)\n"
                    + "Table   0(  0), entry  5e( 94): 15d(349)\n"
                    + "Table   0(  0), entry  5f( 95): 2d9(729)\n"
                    + "\n"
                    + "State   0(  0):  73(115) entries\n"
                    + "\n"
                    + "State   0(  0), entry   0(  0): OTP_LEFT_START      0(0)\n"
                    + "State   0(  0), entry   1(  1): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry   2(  2):                     9(9)\n"
                    + "State   0(  0), entry   3(  3): OTP_GOTO_NO_ADVANCE 9(9)\n"
                    + "State   0(  0), entry   4(  4): OTP_GOTO_NE         43(67,`C\')\n"
                    + "State   0(  0), entry   5(  5):                     9(9)\n"
                    + "State   0(  0), entry   6(  6): OTP_PUSH_NUM        108(264)\n"
                    + "State   0(  0), entry   7(  7): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry   8(  8): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry   9(  9): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry   a( 10): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry   b( 11):                     12(18)\n"
                    + "State   0(  0), entry   c( 12): OTP_GOTO_NO_ADVANCE 12(18)\n"
                    + "State   0(  0), entry   d( 13): OTP_GOTO_NE         63(99,`c\')\n"
                    + "State   0(  0), entry   e( 14):                     12(18)\n"
                    + "State   0(  0), entry   f( 15): OTP_PUSH_NUM        109(265)\n"
                    + "State   0(  0), entry  10( 16): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  11( 17): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  12( 18): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  13( 19): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  14( 20):                     1b(27)\n"
                    + "State   0(  0), entry  15( 21): OTP_GOTO_NO_ADVANCE 1b(27)\n"
                    + "State   0(  0), entry  16( 22): OTP_GOTO_NE         47(71,`G\')\n"
                    + "State   0(  0), entry  17( 23):                     1b(27)\n"
                    + "State   0(  0), entry  18( 24): OTP_PUSH_NUM        11c(284)\n"
                    + "State   0(  0), entry  19( 25): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  1a( 26): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  1b( 27): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  1c( 28): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  1d( 29):                     24(36,`$\')\n"
                    + "State   0(  0), entry  1e( 30): OTP_GOTO_NO_ADVANCE 24(36,`$\')\n"
                    + "State   0(  0), entry  1f( 31): OTP_GOTO_NE         67(103,`g\')\n"
                    + "State   0(  0), entry  20( 32):                     24(36,`$\')\n"
                    + "State   0(  0), entry  21( 33): OTP_PUSH_NUM        11d(285)\n"
                    + "State   0(  0), entry  22( 34): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  23( 35): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  24( 36): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  25( 37): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  26( 38):                     2d(45,`-\')\n"
                    + "State   0(  0), entry  27( 39): OTP_GOTO_NO_ADVANCE 2d(45,`-\')\n"
                    + "State   0(  0), entry  28( 40): OTP_GOTO_NE         48(72,`H\')\n"
                    + "State   0(  0), entry  29( 41):                     2d(45,`-\')\n"
                    + "State   0(  0), entry  2a( 42): OTP_PUSH_NUM        124(292)\n"
                    + "State   0(  0), entry  2b( 43): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  2c( 44): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  2d( 45): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  2e( 46): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  2f( 47):                     36(54,`6\')\n"
                    + "State   0(  0), entry  30( 48): OTP_GOTO_NO_ADVANCE 36(54,`6\')\n"
                    + "State   0(  0), entry  31( 49): OTP_GOTO_NE         68(104,`h\')\n"
                    + "State   0(  0), entry  32( 50):                     36(54,`6\')\n"
                    + "State   0(  0), entry  33( 51): OTP_PUSH_NUM        125(293)\n"
                    + "State   0(  0), entry  34( 52): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  35( 53): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  36( 54): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  37( 55): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  38( 56):                     3f(63,`?\')\n"
                    + "State   0(  0), entry  39( 57): OTP_GOTO_NO_ADVANCE 3f(63,`?\')\n"
                    + "State   0(  0), entry  3a( 58): OTP_GOTO_NE         4a(74,`J\')\n"
                    + "State   0(  0), entry  3b( 59):                     3f(63,`?\')\n"
                    + "State   0(  0), entry  3c( 60): OTP_PUSH_NUM        134(308)\n"
                    + "State   0(  0), entry  3d( 61): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  3e( 62): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  3f( 63): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  40( 64): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  41( 65):                     48(72,`H\')\n"
                    + "State   0(  0), entry  42( 66): OTP_GOTO_NO_ADVANCE 48(72,`H\')\n"
                    + "State   0(  0), entry  43( 67): OTP_GOTO_NE         6a(106,`j\')\n"
                    + "State   0(  0), entry  44( 68):                     48(72,`H\')\n"
                    + "State   0(  0), entry  45( 69): OTP_PUSH_NUM        135(309)\n"
                    + "State   0(  0), entry  46( 70): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  47( 71): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  48( 72): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  49( 73): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  4a( 74):                     51(81,`Q\')\n"
                    + "State   0(  0), entry  4b( 75): OTP_GOTO_NO_ADVANCE 51(81,`Q\')\n"
                    + "State   0(  0), entry  4c( 76): OTP_GOTO_NE         53(83,`S\')\n"
                    + "State   0(  0), entry  4d( 77):                     51(81,`Q\')\n"
                    + "State   0(  0), entry  4e( 78): OTP_PUSH_NUM        15c(348)\n"
                    + "State   0(  0), entry  4f( 79): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  50( 80): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  51( 81): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  52( 82): OTP_GOTO_NE         3c(60,`<\')\n"
                    + "State   0(  0), entry  53( 83):                     5a(90,`Z\')\n"
                    + "State   0(  0), entry  54( 84): OTP_GOTO_NO_ADVANCE 5a(90,`Z\')\n"
                    + "State   0(  0), entry  55( 85): OTP_GOTO_NE         73(115,`s\')\n"
                    + "State   0(  0), entry  56( 86):                     5a(90,`Z\')\n"
                    + "State   0(  0), entry  57( 87): OTP_PUSH_NUM        15d(349)\n"
                    + "State   0(  0), entry  58( 88): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  59( 89): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  5a( 90): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  5b( 91): OTP_GOTO_LT         0(0)\n"
                    + "State   0(  0), entry  5c( 92):                     61(97,`a\')\n"
                    + "State   0(  0), entry  5d( 93): OTP_GOTO_GT         9f(159)\n"
                    + "State   0(  0), entry  5e( 94):                     61(97,`a\')\n"
                    + "State   0(  0), entry  5f( 95): OTP_RIGHT_CHAR      1(1)\n"
                    + "State   0(  0), entry  60( 96): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  61( 97): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  62( 98): OTP_GOTO_LT         a0(160)\n"
                    + "State   0(  0), entry  63( 99):                     6d(109,`m\')\n"
                    + "State   0(  0), entry  64(100): OTP_GOTO_GT         ff(255)\n"
                    + "State   0(  0), entry  65(101):                     6d(109,`m\')\n"
                    + "State   0(  0), entry  66(102): OTP_PUSH_NUM        0(0)\n"
                    + "State   0(  0), entry  67(103): OTP_PUSH_CHAR       1(1)\n"
                    + "State   0(  0), entry  68(104): OTP_PUSH_NUM        a0(160)\n"
                    + "State   0(  0), entry  69(105): OTP_SUB             0(0)\n"
                    + "State   0(  0), entry  6a(106): OTP_LOOKUP          0(0)\n"
                    + "State   0(  0), entry  6b(107): OTP_RIGHT_OUTPUT    0(0)\n"
                    + "State   0(  0), entry  6c(108): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  6d(109): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  6e(110): OTP_RIGHT_CHAR      1(1)\n"
                    + "State   0(  0), entry  6f(111): OTP_STOP            0(0)\n"
                    + "State   0(  0), entry  70(112): OTP_LEFT_RETURN     0(0)\n"
                    + "State   0(  0), entry  71(113): OTP_RIGHT_CHAR      1(1)\n"
                    + "State   0(  0), entry  72(114): OTP_STOP            0(0)\n");
    }

}
