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
import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CompilerStateTest extends TestCase {

    /**
     * lat2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse1() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(//
                    ("input: 1;\n"
                            + "output: 2;\n"
                            + "\n"
                            + "states: VERBATIM;\n"
                            + "\n"
                            + "expressions:\n"
                            + "\n"
                            + "`-'`-'`-' => @\"2014;\n"
                            + "`-'`-' => @\"2013;\n"
                            + "%`-' => @\"2010;\n"
                            + "``'``' => @\"201C;\n"
                            + "``' => @\"2018;\n"
                            + "`''`'' => @\"201D;\n"
                            + "`'' => @\"2019;\n"
                            + "`,'`,' => @\"201E;\n"
                            + "`<'`<' => @\"00AB;\n"
                            + "`>'`>' => @\"00BB;\n"
                            + "\n"
                            + "@\"F000 => <push: VERBATIM> ;\n"
                            + "\n"
                            + "<VERBATIM>@\"0021-@\"007F => #(\\1 + @\"F000) ;\n"
                            + "\n" + "<VERBATIM>@\"F001 => <pop:> ;\n" + "\n"
                            + ". => \\1;\n").getBytes());

        CompilerState cs = new CompilerState();
        cs.parse(stream);
        stream.close();

        assertEquals("Compiler state", //
            "input:  1;\n" + "output: 2;\n" + "states:\n" + "  VERBATIM;\n"
                    + "expressions:\n" + "  `-'`-'`-' => @\"2014;\n"
                    + "  `-'`-' => @\"2013;\n" + "  ``'``' => @\"201c;\n"
                    + "  ``' => @\"2018;\n" + "  `''`'' => @\"201d;\n"
                    + "  `'' => @\"2019;\n" + "  `,'`,' => @\"201e;\n"
                    + "  `<'`<' => @\"ab;\n" + "  `>'`>' => @\"bb;\n"
                    + "  @\"f000 => <push: VERBATIM>;\n"
                    + "  <VERBATIM>`!'-@\"7f => #(\\1 + @\"f000);\n"
                    + "  <VERBATIM>@\"f001 => <pop:>;\n" + "  . => \\1;\n\n", //
            cs.toString());
    }

    /**
     * uni2cuni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse2() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(//
                    ("input:  2;\n"
                            + "output: 2;\n"
                            + "states:\n  MEDIAL,\n  NUMERAL;\n"
                            + "aliases:\n"
                            + "UNIFORM = (@\"0621 | @\"0674 | @\"066E | @\"066F | @\"06EF | @\"063F);\n"
                            + "SPECIAL = (@\"FDF2);\n"
                            + "BIFORM = (@\"0622-@\"0625 | @\"0627 | @\"0629 | @\"062F-@\"0632 | @\"0648 | \n"
                            + "@\"0649 | @\"065D | @\"065E | \n"
                            + "@\"0671-@\"0673 | @\"0675-@\"0677 | @\"0688-@\"069A |\n"
                            + "@\"06BA | @\"06C0-@\"06CB | @\"06CD | @\"06D2 | @\"06D3 |\n"
                            + "@\"06FF);\n"
                            + "QUADRIFORM = (@\"0626 | @\"0628 | @\"062A-@\"062E | @\"0633-@\"063A | \n"
                            + "@\"0640-@\"0647 |\n"
                            + "@\"0649 | @\"064A | \n"
                            + "@\"0655-@\"0657 | @\"065B | @\"065C | \n"
                            + "@\"0678-@\"0687 | @\"069A-@\"06B7 |\n"
                            + "@\"06BB-@\"06BF | @\"06CC | @\"06CE | @\"06D0 | @\"06D1 |\n"
                            + "@\"06FE);\n"
                            + "ACCENT = (@\"064B-@\"0652 | @\"0670);\n"
                            + "ARABIC_LETTER = ({BIFORM} | {QUADRIFORM});\n"
                            + "NOT_ARABIC_LETTER = ^(@\"0621-@\"065F | @\"0670-@\"06D3);\n"
                            + "NOT_ARABIC_OR_UNI = ({NOT_ARABIC_LETTER}|{UNIFORM});\n"
                            + "ARABIC_NUMBER = (@\"0030-@\"0039 | @\"0660-@\"0669 | @\"06F0-@\"06F9);\n"
                            + "NOT_ARABIC_NUMBER = ^(@\"0030-@\"0039 | @\"0660-@\"0669 | @\"06F0-@\"06F9);\n"
                            + "LAM_LIKE = (@\"0644 | @\"06B5-@\"06B7 | @\"06FE);\n"
                            + "ALIF_LIKE = (@\"0622|@\"0623|@\"0625|@\"0627|@\"0671-@\"0673);\n"
                            + "\n"
                            + "expressions:\n"
                            + "\n"
                            + "{UNIFORM}@\"0651{ACCENT}\n"
                            + "    => #(\\1 + @\"DA00) #(\\3 + @\"DA90)\n"
                            + "    ;\n"
                            + "{UNIFORM}{ACCENT}\n"
                            + "    => #(\\1 + @\"DA00) #(\\2 + @\"DA00)\n"
                            + "    ;\n"
                            + "{UNIFORM}\n"
                            + "    => #(\\1 + @\"DA00)\n"
                            + "    ;\n"
                            + "{SPECIAL}@\"0651{ACCENT}\n"
                            + "    => \\1 #(\\3 + @\"DA90)\n"
                            + "    ;\n"
                            + "{SPECIAL}{ACCENT}\n"
                            + "    => \\1 #(\\2 + @\"DA00)\n"
                            + "    ;\n"
                            + "{SPECIAL}\n"
                            + "    => \\1\n"
                            + "    ;\n"
                            + "<NUMERAL>{ARABIC_NUMBER} end:\n"
                            + "    => #(\\1) \"}\"\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<NUMERAL>{ARABIC_NUMBER}\n"
                            + "    => #(\\1)\n"
                            + "    ;\n"
                            + "<NUMERAL>(@\"002B|@\"002D|@\"002E|@\"066B|@\"066C){ARABIC_NUMBER} end:\n"
                            + "    => #(\\1) #(\\2) \"}\"\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<NUMERAL>(@\"002B|@\"002D|@\"002E|@\"066B|@\"066C){ARABIC_NUMBER}\n"
                            + "    => #(\\1) #(\\2)\n"
                            + "    ;\n"
                            + "<NUMERAL>{NOT_ARABIC_NUMBER}\n"
                            + "    => \"}\"\n"
                            + "    <= #(\\1)\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "(@\"002B|@\"002D|@\"002E){ARABIC_NUMBER} end:\n"
                            + "    => \"{\\textdir TLT{}\" #(\\1) #(\\2) \"}\"\n"
                            + "    ;\n"
                            + "(@\"002B|@\"002D|@\"002E){ARABIC_NUMBER}\n"
                            + "    => \"{\\textdir TLT{}\" #(\\1) #(\\2)\n"
                            + "    <push: NUMERAL>\n"
                            + "    ;\n"
                            + "{ARABIC_NUMBER} end:\n"
                            + "    => #(\\1)\n"
                            + "    ;\n"
                            + "{ARABIC_NUMBER}\n"
                            + "    => \"{\\textdir TLT{}\" #(\\1)\n"
                            + "    <push: NUMERAL>\n"
                            + "    ;\n"
                            + "{NOT_ARABIC_LETTER}\n"
                            + "    => #(\\1)\n"
                            + "    ;\n"
                            + "{QUADRIFORM}{NOT_ARABIC_OR_UNI}\n"
                            + "    => #(\\1 + @\"DA00) <= \\2\n"
                            + "    ;\n"
                            + "{QUADRIFORM} end:\n"
                            + "    => #(\\1 + @\"DA00)\n"
                            + "    ;\n"
                            + "{QUADRIFORM}@\"0651{ACCENT}{NOT_ARABIC_OR_UNI}\n"
                            + "    => #(\\1 + @\"DA00) #(\\3 + @\"DA90)\n"
                            + "    <= #(\\4)\n"
                            + "    ;\n"
                            + "{QUADRIFORM}{ACCENT}{NOT_ARABIC_OR_UNI}\n"
                            + "    => #(\\1 + @\"DA00) #(\\2 + @\"DA00)\n"
                            + "    <= #(\\3)\n"
                            + "    ;\n"
                            + "{QUADRIFORM}@\"0651{ACCENT} end:\n"
                            + "    => #(\\1 + @\"DA00) #(\\3 + @\"DA90)\n"
                            + "    ;\n"
                            + "{QUADRIFORM}{ACCENT} end:\n"
                            + "    => #(\\1 + @\"DA00) #(\\2 + @\"DA00)\n"
                            + "    ;\n"
                            + "   \n"
                            + "% @\"0620 is our internal keshideh (not Unicode keshideh which is @\"0640)\n"
                            + "\n"
                            + "{QUADRIFORM}@\"0651{ACCENT}\n"
                            + "    => #(\\1 + @\"DB00) #(\\3 + @\"DA90) @\"0620\n"
                            + "    <push: MEDIAL>\n"
                            + "    ;\n"
                            + "{QUADRIFORM}{ACCENT}\n"
                            + "    => #(\\1 + @\"DB00) #(\\2 + @\"DA00) @\"0620\n"
                            + "    <push: MEDIAL>\n"
                            + "    ;\n"
                            + "{QUADRIFORM}\n"
                            + "    => #(\\1 + @\"DB00) @\"0620\n"
                            + "    <push: MEDIAL>\n"
                            + "    ;\n"
                            + "{BIFORM}@\"0651{ACCENT}\n"
                            + "    => #(\\1 + @\"DA00) #(\\3 + @\"DA90)\n"
                            + "    ;\n"
                            + "{BIFORM}{ACCENT}\n"
                            + "    => #(\\1 + @\"DA00) #(\\2 + @\"DA00)\n"
                            + "    ;\n"
                            + "{BIFORM}\n"
                            + "    => #(\\1 + @\"DA00)\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM}{NOT_ARABIC_OR_UNI}\n"
                            + "    => #(\\1 + @\"DD00)\n"
                            + "    <= #(\\2)\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM} end:\n"
                            + "    => #(\\1 + @\"DD00)\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM}@\"0651{ACCENT}{NOT_ARABIC_OR_UNI}\n"
                            + "    => #(\\1 + @\"DD00) #(\\3 + @\"DA90)\n"
                            + "    <= #(\\4)\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM}{ACCENT}{NOT_ARABIC_OR_UNI}\n"
                            + "    => #(\\1 + @\"DD00) #(\\2 + @\"DA00)\n"
                            + "    <= #(\\3)\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM}@\"0651{ACCENT} end:\n"
                            + "    => #(\\1 + @\"DD00) #(\\3 + @\"DA90)\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM}{ACCENT} end:\n"
                            + "    => #(\\1 + @\"DD00) #(\\2 + @\"DA00)\n"
                            + "    <pop:>\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM}@\"0651{ACCENT}\n"
                            + "    => #(\\1 + @\"DC00) #(\\3 + @\"DA90) @\"0620\n"
                            + "    ;\n"
                            + "<MEDIAL>{QUADRIFORM}{ACCENT}\n"
                            + "    => #(\\1 + @\"DC00) #(\\2 + @\"DA00) @\"0620\n"
                            + "    ;\n" + "<MEDIAL>{QUADRIFORM}\n"
                            + "    => #(\\1 + @\"DC00) @\"0620\n" + "    ;\n"
                            + "<MEDIAL>{BIFORM}@\"0651{ACCENT}\n"
                            + "    => #(\\1 + @\"DD00) #(\\3 + @\"DA90)\n"
                            + "    <pop:>\n" + "    ;\n"
                            + "<MEDIAL>{BIFORM}{ACCENT}\n"
                            + "    => #(\\1 + @\"DD00) #(\\2 + @\"DA00)\n"
                            + "    <pop:>\n" + "    ;\n" + "<MEDIAL>{BIFORM}\n"
                            + "    => #(\\1 + @\"DD00)\n" + "    <pop:>\n"
                            + "    ;\n" + "   \n"
                            + "@\"F000-@\"F07F => \\1 ;\n" + "").getBytes());

        CompilerState cs = new CompilerState();
        cs.parse(stream);
        stream.close();

        assertEquals(
            "Compiler state", //
            "input:  2;\n"
                    + "output: 2;\n"
                    + "states:\n  MEDIAL,\n  NUMERAL;\n"
                    + "aliases:\n"
                    + "  NOT_ARABIC_NUMBER=^(`0\'-`9\' | @\"660-@\"669 | @\"6f0-@\"6f9)\n"
                    + "  ARABIC_LETTER={BIFORM} | {QUADRIFORM}\n"
                    + "  SPECIAL=([@\"fdf2])\n"
                    + "  UNIFORM=@\"621 | @\"674 | @\"66e | @\"66f | @\"6ef | @\"63f\n"
                    + "  ALIF_LIKE=@\"622 | @\"623 | @\"625 | @\"627 | @\"671-@\"673\n"
                    + "  LAM_LIKE=@\"644 | @\"6b5-@\"6b7 | @\"6fe\n"
                    + "  BIFORM=@\"622-@\"625 | @\"627 | @\"629 | @\"62f-@\"632 | @\"648 | @\"649 | @\"65d | @\"65e | @\"671-@\"673 | @\"675-@\"677 | @\"688-@\"69a | @\"6ba | @\"6c0-@\"6cb | @\"6cd | @\"6d2 | @\"6d3 | @\"6ff\n"
                    + "  NOT_ARABIC_LETTER=^(@\"621-@\"65f | @\"670-@\"6d3)\n"
                    + "  ARABIC_NUMBER=`0\'-`9\' | @\"660-@\"669 | @\"6f0-@\"6f9\n"
                    + "  ACCENT=@\"64b-@\"652 | @\"670\n"
                    + "  QUADRIFORM=@\"626 | @\"628 | @\"62a-@\"62e | @\"633-@\"63a | @\"640-@\"647 | @\"649 | @\"64a | @\"655-@\"657 | @\"65b | @\"65c | @\"678-@\"687 | @\"69a-@\"6b7 | @\"6bb-@\"6bf | @\"6cc | @\"6ce | @\"6d0 | @\"6d1 | @\"6fe\n"
                    + "  NOT_ARABIC_OR_UNI={NOT_ARABIC_LETTER} | {UNIFORM}\n"
                    + "\n"
                    + "expressions:\n"
                    + "  {UNIFORM}@\"651{ACCENT} => #(\\1 + @\"da00)#(\\3 + @\"da90);\n"
                    + "  {UNIFORM}{ACCENT} => #(\\1 + @\"da00)#(\\2 + @\"da00);\n"
                    + "  {UNIFORM} => #(\\1 + @\"da00);\n"
                    + "  {SPECIAL}@\"651{ACCENT} => \\1#(\\3 + @\"da90);\n"
                    + "  {SPECIAL}{ACCENT} => \\1#(\\2 + @\"da00);\n"
                    + "  {SPECIAL} => \\1;\n"
                    + "  <NUMERAL>{ARABIC_NUMBER}end: => #(\\1)`}\'<pop:>;\n"
                    + "  <NUMERAL>{ARABIC_NUMBER} => #(\\1);\n"
                    + "  <NUMERAL>`+\' | `-\' | `.\' | @\"66b | @\"66c{ARABIC_NUMBER}end: => #(\\1)#(\\2)`}\'<pop:>;\n"
                    + "  <NUMERAL>`+\' | `-\' | `.\' | @\"66b | @\"66c{ARABIC_NUMBER} => #(\\1)#(\\2);\n"
                    + "  <NUMERAL>{NOT_ARABIC_NUMBER} => `}\' <= \\1<pop:>;\n"
                    + "  `+\' | `-\' | `.\'{ARABIC_NUMBER}end: => `{\'`\\\'`t\'`e\'`x\'`t\'`d\'`i\'`r\'` \'`T\'`L\'`T\'`{\'`}\'#(\\1)#(\\2)`}\';\n"
                    + "  `+\' | `-\' | `.\'{ARABIC_NUMBER} => `{\'`\\\'`t\'`e\'`x\'`t\'`d\'`i\'`r\'` \'`T\'`L\'`T\'`{\'`}\'#(\\1)#(\\2)<push: NUMERAL>;\n"
                    + "  {ARABIC_NUMBER}end: => #(\\1);\n"
                    + "  {ARABIC_NUMBER} => `{\'`\\\'`t\'`e\'`x\'`t\'`d\'`i\'`r\'` \'`T\'`L\'`T\'`{\'`}\'#(\\1)<push: NUMERAL>;\n"
                    + "  {NOT_ARABIC_LETTER} => #(\\1);\n"
                    + "  {QUADRIFORM}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"da00) <= \\2;\n"
                    + "  {QUADRIFORM}end: => #(\\1 + @\"da00);\n"
                    + "  {QUADRIFORM}@\"651{ACCENT}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"da00)#(\\3 + @\"da90) <= \\4;\n"
                    + "  {QUADRIFORM}{ACCENT}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"da00)#(\\2 + @\"da00) <= \\3;\n"
                    + "  {QUADRIFORM}@\"651{ACCENT}end: => #(\\1 + @\"da00)#(\\3 + @\"da90);\n"
                    + "  {QUADRIFORM}{ACCENT}end: => #(\\1 + @\"da00)#(\\2 + @\"da00);\n"
                    + "  {QUADRIFORM}@\"651{ACCENT} => #(\\1 + @\"db00)#(\\3 + @\"da90)@\"620<push: MEDIAL>;\n"
                    + "  {QUADRIFORM}{ACCENT} => #(\\1 + @\"db00)#(\\2 + @\"da00)@\"620<push: MEDIAL>;\n"
                    + "  {QUADRIFORM} => #(\\1 + @\"db00)@\"620<push: MEDIAL>;\n"
                    + "  {BIFORM}@\"651{ACCENT} => #(\\1 + @\"da00)#(\\3 + @\"da90);\n"
                    + "  {BIFORM}{ACCENT} => #(\\1 + @\"da00)#(\\2 + @\"da00);\n"
                    + "  {BIFORM} => #(\\1 + @\"da00);\n"
                    + "  <MEDIAL>{QUADRIFORM}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"dd00) <= \\2<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}end: => #(\\1 + @\"dd00)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}@\"651{ACCENT}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"dd00)#(\\3 + @\"da90) <= \\4<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}{ACCENT}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"dd00)#(\\2 + @\"da00) <= \\3<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}@\"651{ACCENT}end: => #(\\1 + @\"dd00)#(\\3 + @\"da90)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}{ACCENT}end: => #(\\1 + @\"dd00)#(\\2 + @\"da00)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}@\"651{ACCENT} => #(\\1 + @\"dc00)#(\\3 + @\"da90)@\"620;\n"
                    + "  <MEDIAL>{QUADRIFORM}{ACCENT} => #(\\1 + @\"dc00)#(\\2 + @\"da00)@\"620;\n"
                    + "  <MEDIAL>{QUADRIFORM} => #(\\1 + @\"dc00)@\"620;\n"
                    + "  <MEDIAL>{BIFORM}@\"651{ACCENT} => #(\\1 + @\"dd00)#(\\3 + @\"da90)<pop:>;\n"
                    + "  <MEDIAL>{BIFORM}{ACCENT} => #(\\1 + @\"dd00)#(\\2 + @\"da00)<pop:>;\n"
                    + "  <MEDIAL>{BIFORM} => #(\\1 + @\"dd00)<pop:>;\n"
                    + "  @\"f000-@\"f07f => \\1;\n\n", //
            cs.toString());
    }

    /**
     * 7in88593.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse3() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(//
                    ("input:    1;\n"
                            + "output: 2;\n"
                            + "\n"
                            + "tables:\n"
                            + " \n"
                            + "tab8859_3[@\"60] = {\n"
                            + " @\"00A0, @\"0126, @\"02D8, @\"00A3, @\"00A4, @\"FFFD, @\"0124, @\"00A7,\n"
                            + " @\"00A8, @\"0130, @\"015E, @\"011E, @\"0134, @\"00AD, @\"FFFD, @\"017B,\n"
                            + " @\"00B0, @\"0127, @\"00B2, @\"00B3, @\"00B4, @\"00B5, @\"0125, @\"00B7,\n"
                            + " @\"00B8, @\"0131, @\"015F, @\"011F, @\"0135, @\"00BD, @\"FFFD, @\"017C,\n"
                            + " @\"00C0, @\"00C1, @\"00C2, @\"FFFD, @\"00C4, @\"010A, @\"0108, @\"00C7,\n"
                            + " @\"00C8, @\"00C9, @\"00CA, @\"00CB, @\"00CC, @\"00CD, @\"00CE, @\"00CF,\n"
                            + " @\"FFFD, @\"00D1, @\"00D2, @\"00D3, @\"00D4, @\"0120, @\"00D6, @\"00D7,\n"
                            + " @\"011C, @\"00D9, @\"00DA, @\"00DB, @\"00DC, @\"016C, @\"015C, @\"00DF,\n"
                            + " @\"00E0, @\"00E1, @\"00E2, @\"FFFD, @\"00E4, @\"010B, @\"0109, @\"00E7,\n"
                            + " @\"00E8, @\"00E9, @\"00EA, @\"00EB, @\"00EC, @\"00ED, @\"00EE, @\"00EF,\n"
                            + " @\"FFFD, @\"00F1, @\"00F2, @\"00F3, @\"00F4, @\"0121, @\"00F6, @\"00F7,\n"
                            + " @\"011D, @\"00F9, @\"00FA, @\"00FB, @\"00FC, @\"016D, @\"015D, @\"02D9\n"
                            + "};\n" + "\n" + "expressions:\n"
                            + "`<\'`C\'  => #(@\"0108) ;\n"
                            + "`<\'`c\'  => #(@\"0109) ;\n"
                            + "`<\'`G\'  => #(@\"011C) ;\n"
                            + "`<\'`g\'  => #(@\"011D) ;\n"
                            + "`<\'`H\'  => #(@\"0124) ;\n"
                            + "`<\'`h\'  => #(@\"0125) ;\n"
                            + "`<\'`J\'  => #(@\"0134) ;\n"
                            + "`<\'`j\'  => #(@\"0135) ;\n"
                            + "`<\'`S\'  => #(@\"015C) ;\n"
                            + "`<\'`s\'  => #(@\"015D) ;\n" + "\n"
                            + "@\"00-@\"9F   => \\1;\n"
                            + "@\"A0-@\"FF   => #(tab8859_3[\\1-@\"A0]);\n"
                            + "%.      => @\"FFFD;\n" + ". => \\1;\n")
                        .getBytes());

        CompilerState cs = new CompilerState();
        cs.parse(stream);
        stream.close();

        assertEquals(
            "Compiler state", //
            "input:  1;\n"
                    + "output: 2;\n"
                    + "tables:\n"
                    + "  tab8859_3[@\"60] = {\n"
                    + "     @\"a0, @\"126, @\"2d8, @\"a3, @\"a4, @\"fffd, @\"124, @\"a7,\n"
                    + "     @\"a8, @\"130, @\"15e, @\"11e, @\"134, @\"ad, @\"fffd, @\"17b,\n"
                    + "     @\"b0, @\"127, @\"b2, @\"b3, @\"b4, @\"b5, @\"125, @\"b7,\n"
                    + "     @\"b8, @\"131, @\"15f, @\"11f, @\"135, @\"bd, @\"fffd, @\"17c,\n"
                    + "     @\"c0, @\"c1, @\"c2, @\"fffd, @\"c4, @\"10a, @\"108, @\"c7,\n"
                    + "     @\"c8, @\"c9, @\"ca, @\"cb, @\"cc, @\"cd, @\"ce, @\"cf,\n"
                    + "     @\"fffd, @\"d1, @\"d2, @\"d3, @\"d4, @\"120, @\"d6, @\"d7,\n"
                    + "     @\"11c, @\"d9, @\"da, @\"db, @\"dc, @\"16c, @\"15c, @\"df,\n"
                    + "     @\"e0, @\"e1, @\"e2, @\"fffd, @\"e4, @\"10b, @\"109, @\"e7,\n"
                    + "     @\"e8, @\"e9, @\"ea, @\"eb, @\"ec, @\"ed, @\"ee, @\"ef,\n"
                    + "     @\"fffd, @\"f1, @\"f2, @\"f3, @\"f4, @\"121, @\"f6, @\"f7,\n"
                    + "     @\"11d, @\"f9, @\"fa, @\"fb, @\"fc, @\"16d, @\"15d, @\"2d9};\n"
                    + "\n" + "expressions:\n"
                    + "  `<\'`C\' => #(@\"108);\n"
                    + "  `<\'`c\' => #(@\"109);\n"
                    + "  `<\'`G\' => #(@\"11c);\n"
                    + "  `<\'`g\' => #(@\"11d);\n"
                    + "  `<\'`H\' => #(@\"124);\n"
                    + "  `<\'`h\' => #(@\"125);\n"
                    + "  `<\'`J\' => #(@\"134);\n"
                    + "  `<\'`j\' => #(@\"135);\n"
                    + "  `<\'`S\' => #(@\"15c);\n"
                    + "  `<\'`s\' => #(@\"15d);\n" + "  @\"0-@\"9f => \\1;\n"
                    + "  @\"a0-@\"ff => #(ab8859_3[\\1 - @\"a0]);\n"
                    + "  . => \\1;\n\n", //
            cs.toString());
    }

}
