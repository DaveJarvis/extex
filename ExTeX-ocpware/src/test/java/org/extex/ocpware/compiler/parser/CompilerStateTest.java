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
import java.util.Locale;

import junit.framework.TestCase;

import org.extex.ocpware.compiler.exception.AliasDefinedException;
import org.extex.ocpware.compiler.exception.AliasNotDefinedException;
import org.extex.ocpware.compiler.exception.MissingExpressionsException;
import org.extex.ocpware.compiler.exception.StateDefinedException;
import org.extex.ocpware.compiler.exception.StateNotDefinedException;
import org.extex.ocpware.compiler.exception.SyntaxException;
import org.extex.ocpware.compiler.exception.TableDefinedException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.junit.Test;

/**
 * This is a test suite for the compiler state.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CompilerStateTest extends TestCase {

    /**
     * Test case showing that an empty file misses an expression section and
     * leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError0() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream = new ByteArrayInputStream("\n".getBytes());

        try {
            new CompilerState(stream);
        } catch (MissingExpressionsException e) {
            assertEquals("syntax error: missing an expressions section", //
                e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a undefined section leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError1() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream = new ByteArrayInputStream("abc\n".getBytes());

        try {
            new CompilerState(stream);
        } catch (SyntaxException e) {
            assertEquals("2: syntax error; unexpected 'abc':\n", //
                e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a missing : after a section name leads to an
     * error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError2() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream = new ByteArrayInputStream("input 2\n".getBytes());

        try {
            new CompilerState(stream);
        } catch (SyntaxException e) {
            assertEquals("1: syntax error; unexpected \'input\':\n" + "input", //
                e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a missing : after a section name leads to an
     * error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError3() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream = new ByteArrayInputStream("output 2\n".getBytes());

        try {
            new CompilerState(stream);
        } catch (SyntaxException e) {
            assertEquals(
                "1: syntax error; unexpected \'output\':\n" + "output", //
                e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a missing : after a section name leads to an
     * error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testError4() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream("input: 2\n output: 1;\n".getBytes());

        try {
            new CompilerState(stream);
        } catch (SyntaxException e) {
            assertEquals("2: syntax error; unexpected 'o' instead of ';':\n"
                    + " o", //
                e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a double definition of a state leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorState1() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("states: X, X;\n" + "expressions:\n"
                        + ". => \\1;\n").getBytes());

        try {
            new CompilerState(stream);
        } catch (StateDefinedException e) {
            assertEquals("state X is already defined", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a reference to a non-defined state leads to an
     * error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorState2() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("expressions:\n" + "<X> . => \\1;\n")
                    .getBytes());

        try {
            new CompilerState(stream).compile();
        } catch (StateNotDefinedException e) {
            assertEquals("state X is not defined", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that an error in the states leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorState3() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("states: X.\n" + "expressions:\n"
                        + ". => \\1;\n").getBytes());

        try {
            new CompilerState(stream);
        } catch (SyntaxException e) {
            assertEquals("1: syntax error; unexpected '.':\n"
                    + "states: X.", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a double definition of a table leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorTable1() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("tables: X[1]={1}; X[2]={2,2};\n"
                        + "expressions:\n" + ". => \\1;\n").getBytes());

        try {
            new CompilerState(stream);
        } catch (TableDefinedException e) {
            assertEquals("table X is already defined", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a reference to a non-defined table leads to an
     * error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorTable2() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("expressions:\n" + ". => #X[1];\n")
                    .getBytes());

        try {
            new CompilerState(stream).compile();
        } catch (TableNotDefinedException e) {
            assertEquals("table X is not defined", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a double definition of an alias leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorAlias1() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("aliases: aa=1; aa=2;\n"
                        + "expressions:\n" + ". => \\1;\n").getBytes());

        try {
            new CompilerState(stream);
        } catch (AliasDefinedException e) {
            assertEquals("alias aa is already defined", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a reference to a non-defined alias leads to an
     * error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorAlias2() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("expressions:\n" + "{X} => \\1;\n")
                    .getBytes());

        try {
            new CompilerState(stream).compile();
        } catch (AliasNotDefinedException e) {
            assertEquals("alias X is not defined", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * Test case showing that a syntax eror min the aliases leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testErrorAlias3() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(("aliases: aa=1.\n" + "expressions:\n"
                        + ". => \\1;\n").getBytes());

        try {
            new CompilerState(stream);
        } catch (SyntaxException e) {
            assertEquals("1: syntax error; unexpected '.' instead of ';':\n"
                    + "aliases: aa=1.", e.getLocalizedMessage());
            return;
        } finally {
            stream.close();
        }
        assertTrue(false);
    }

    /**
     * lat2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse1() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(OTP.OMEGA_LAT2UNI_OTP.getBytes());
        CompilerState cs = new CompilerState(stream);
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
                    + "  <VERBATIM>@\"f001 => <pop:>;\n" + "  . => \\1;\n", //
            cs.toString());
    }

    /**
     * uni2cuni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse2() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(OTP.OMEGA_UNI2CUNI_OTP.getBytes());
        CompilerState cs = new CompilerState(stream);
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
                    + "  BIFORM=@\"622-@\"625 | @\"627 | @\"629 | @\"62f-@\"632 | @\"648 | @\"649 "
                    + "| @\"65d | @\"65e | @\"671-@\"673 | @\"675-@\"677 | @\"688-@\"69a | @\"6ba | "
                    + "@\"6c0-@\"6cb | @\"6cd | @\"6d2 | @\"6d3 | @\"6ff\n"
                    + "  NOT_ARABIC_LETTER=^(@\"621-@\"65f | @\"670-@\"6d3)\n"
                    + "  ARABIC_NUMBER=`0\'-`9\' | @\"660-@\"669 | @\"6f0-@\"6f9\n"
                    + "  ACCENT=@\"64b-@\"652 | @\"670\n"
                    + "  QUADRIFORM=@\"626 | @\"628 | @\"62a-@\"62e | @\"633-@\"63a | @\"640-@\"647 "
                    + "| @\"649 | @\"64a | @\"655-@\"657 | @\"65b | @\"65c | @\"678-@\"687 | "
                    + "@\"69a-@\"6b7 | @\"6bb-@\"6bf | @\"6cc | @\"6ce | @\"6d0 | @\"6d1 | @\"6fe\n"
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
                    + "  <NUMERAL>{NOT_ARABIC_NUMBER} => `}\' <= #(\\1)<pop:>;\n"
                    + "  `+\' | `-\' | `.\'{ARABIC_NUMBER}end: => `{\'`\\\'`t\'"
                    + "`e\'`x\'`t\'`d\'`i\'`r\'` \'`T\'`L\'`T\'`{\'`}\'#(\\1)#(\\2)`}\';\n"
                    + "  `+\' | `-\' | `.\'{ARABIC_NUMBER} => `{\'`\\\'`t\'`e\'"
                    + "`x\'`t\'`d\'`i\'`r\'` \'`T\'`L\'`T\'`{\'`}\'#(\\1)#(\\2)<push: NUMERAL>;\n"
                    + "  {ARABIC_NUMBER}end: => #(\\1);\n"
                    + "  {ARABIC_NUMBER} => `{\'`\\\'`t\'`e\'`x\'`t\'`d\'`i\'"
                    + "`r\'` \'`T\'`L\'`T\'`{\'`}\'#(\\1)<push: NUMERAL>;\n"
                    + "  {NOT_ARABIC_LETTER} => #(\\1);\n"
                    + "  {QUADRIFORM}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"da00) <= \\2;\n"
                    + "  {QUADRIFORM}end: => #(\\1 + @\"da00);\n"
                    + "  {QUADRIFORM}@\"651{ACCENT}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"da00)#(\\3 + @\"da90) <= #(\\4);\n"
                    + "  {QUADRIFORM}{ACCENT}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"da00)#(\\2 + @\"da00) <= #(\\3);\n"
                    + "  {QUADRIFORM}@\"651{ACCENT}end: => #(\\1 + @\"da00)#(\\3 + @\"da90);\n"
                    + "  {QUADRIFORM}{ACCENT}end: => #(\\1 + @\"da00)#(\\2 + @\"da00);\n"
                    + "  {QUADRIFORM}@\"651{ACCENT} => #(\\1 + @\"db00)#(\\3 + @\"da90)@\"620<push: MEDIAL>;\n"
                    + "  {QUADRIFORM}{ACCENT} => #(\\1 + @\"db00)#(\\2 + @\"da00)@\"620<push: MEDIAL>;\n"
                    + "  {QUADRIFORM} => #(\\1 + @\"db00)@\"620<push: MEDIAL>;\n"
                    + "  {BIFORM}@\"651{ACCENT} => #(\\1 + @\"da00)#(\\3 + @\"da90);\n"
                    + "  {BIFORM}{ACCENT} => #(\\1 + @\"da00)#(\\2 + @\"da00);\n"
                    + "  {BIFORM} => #(\\1 + @\"da00);\n"
                    + "  <MEDIAL>{QUADRIFORM}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"dd00) <= #(\\2)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}end: => #(\\1 + @\"dd00)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}@\"651{ACCENT}{NOT_ARABIC_OR_UNI} "
                    + "=> #(\\1 + @\"dd00)#(\\3 + @\"da90) <= #(\\4)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}{ACCENT}{NOT_ARABIC_OR_UNI} => #(\\1 + @\"dd00)#(\\2 + @\"da00) <= #(\\3)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}@\"651{ACCENT}end: => #(\\1 + @\"dd00)#(\\3 + @\"da90)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}{ACCENT}end: => #(\\1 + @\"dd00)#(\\2 + @\"da00)<pop:>;\n"
                    + "  <MEDIAL>{QUADRIFORM}@\"651{ACCENT} => #(\\1 + @\"dc00)#(\\3 + @\"da90)@\"620;\n"
                    + "  <MEDIAL>{QUADRIFORM}{ACCENT} => #(\\1 + @\"dc00)#(\\2 + @\"da00)@\"620;\n"
                    + "  <MEDIAL>{QUADRIFORM} => #(\\1 + @\"dc00)@\"620;\n"
                    + "  <MEDIAL>{BIFORM}@\"651{ACCENT} => #(\\1 + @\"dd00)#(\\3 + @\"da90)<pop:>;\n"
                    + "  <MEDIAL>{BIFORM}{ACCENT} => #(\\1 + @\"dd00)#(\\2 + @\"da00)<pop:>;\n"
                    + "  <MEDIAL>{BIFORM} => #(\\1 + @\"dd00)<pop:>;\n"
                    + "  @\"f000-@\"f07f => \\1;\n", //
            cs.toString());
    }

    /**
     * 7in88593.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testParse3() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(OTP.OMEGA_7IN88593_OTP.getBytes());
        CompilerState cs = new CompilerState(stream);
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
                    + "\n" + "expressions:\n" + "  `<\'`C\' => #(@\"108);\n"
                    + "  `<\'`c\' => #(@\"109);\n"
                    + "  `<\'`G\' => #(@\"11c);\n"
                    + "  `<\'`g\' => #(@\"11d);\n"
                    + "  `<\'`H\' => #(@\"124);\n"
                    + "  `<\'`h\' => #(@\"125);\n"
                    + "  `<\'`J\' => #(@\"134);\n"
                    + "  `<\'`j\' => #(@\"135);\n"
                    + "  `<\'`S\' => #(@\"15c);\n"
                    + "  `<\'`s\' => #(@\"15d);\n" + "  @\"0-@\"9f => \\1;\n"
                    + "  @\"a0-@\"ff => #(tab8859_3[\\1 - @\"a0]);\n"
                    + "  . => \\1;\n", //
            cs.toString());
    }

    /**
     * lowercase.otp
     * 
     * @throws Exception in case of an error
     */
    public final void testParse4() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        InputStream stream =
                new ByteArrayInputStream(OTP.OMEGA_LOWERCASE_OTP.getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        assertEquals(
            "Compiler state", //
            "input:  2;\n"
                    + "output: 2;\n"
                    + "expressions:\n"
                    + "  @\"e100-@\"e17f => \\1;\n"
                    + "  `\"' => `\\'`c'`h'`a'`r'`''`0'`4'`2'` ';\n"
                    + "  `#' => `\\'`c'`h'`a'`r'`''`0'`4'`3'` ';\n"
                    + "  `$' => `\\'`c'`h'`a'`r'`''`0'`4'`4'` ';\n"
                    + "  `%' => `\\'`c'`h'`a'`r'`''`0'`4'`5'` ';\n"
                    + "  `&' => `\\'`c'`h'`a'`r'`''`0'`4'`6'` ';\n"
                    + "  `\\' => `\\'`c'`h'`a'`r'`''`1'`3'`4'` ';\n"
                    + "  `^' => `\\'`c'`h'`a'`r'`''`1'`3'`6'` ';\n"
                    + "  `_' => `\\'`c'`h'`a'`r'`''`1'`3'`7'` ';\n"
                    + "  `{' => `\\'`c'`h'`a'`r'`1'`2'`3'` ';\n"
                    + "  `}' => `\\'`c'`h'`a'`r'`1'`2'`5'` ';\n"
                    + "  @\"7e => `\\'`c'`h'`a'`r'`''`1'`7'`6'` ';\n"
                    + "  ` '-`@' => \\1;\n"
                    + "  `A'-`Z' => #(\\1 + ` ');\n"
                    + "  `['-@\"bf => \\1;\n"
                    + "  @\"c0-@\"d6 => #(\\1 + ` ');\n"
                    + "  @\"d7 => \\1;\n"
                    + "  @\"d8-@\"de => #(\\1 + ` ');\n"
                    + "  @\"df-@\"ff => \\1;\n"
                    + "  @\"100-@\"12f => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"130 => @\"e184;\n"
                    + "  @\"132-@\"137 => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"138 => \\1;\n"
                    + "  @\"139-@\"148 => #(\\1 + \\1 MOD: @\"2);\n"
                    + "  @\"149 => \\1;\n"
                    + "  @\"14a-@\"177 => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"178 => @\"ff;\n"
                    + "  @\"179-@\"17e => #(\\1 + \\1 MOD: @\"2);\n"
                    + "  @\"17f => \\1;\n"
                    + "  @\"180-@\"1c3 => \\1;\n"
                    + "  @\"1c4-@\"1c6 => @\"1c6;\n"
                    + "  @\"1c7-@\"1c9 => @\"1c9;\n"
                    + "  @\"1ca-@\"1cc => @\"1cc;\n"
                    + "  @\"1cd-@\"1dc => #(\\1 + \\1 MOD: @\"2);\n"
                    + "  @\"1dd => \\1;\n"
                    + "  @\"1de-@\"1ef => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"1f0 => @\"e18e;\n"
                    + "  @\"1f1-@\"1f3 => @\"1f3;\n"
                    + "  @\"1f4-@\"217 => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"250-@\"385 => \\1;\n"
                    + "  @\"386 => @\"3ac;\n"
                    + "  @\"388-@\"38a => #(\\1 + `%');\n"
                    + "  @\"38c => @\"e1a5;\n"
                    + "  @\"38e => @\"e1a6;\n"
                    + "  @\"38f => @\"e1a7;\n"
                    + "  @\"390 => \\1;\n"
                    + "  @\"391-@\"3ab => #(\\1 + ` ');\n"
                    + "  @\"3ac-@\"3d1 => \\1;\n"
                    + "  @\"3d2 => @\"386;\n"
                    + "  @\"3d3 => @\"388;\n"
                    + "  @\"3d4 => @\"389;\n"
                    + "  @\"3d5-@\"3d6 => \\1;\n"
                    + "  @\"3da => @\"e1aa;\n"
                    + "  @\"3dc => @\"e1ab;\n"
                    + "  @\"3de => @\"e1ac;\n"
                    + "  @\"3e0 => @\"e1ad;\n"
                    + "  @\"3e2-@\"3ef => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"3f0-@\"3f3 => \\1;\n"
                    + "  @\"401-@\"40f => #(\\1 + `P');\n"
                    + "  @\"410-@\"42f => #(\\1 + ` ');\n"
                    + "  @\"430-@\"45f => \\1;\n"
                    + "  @\"460-@\"4bf => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"4c0 => \\1;\n"
                    + "  @\"4c1-@\"4cc => #(\\1 + \\1 MOD: @\"2);\n"
                    + "  @\"4d0-@\"4f9 => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"531-@\"556 => #(\\1 + `0');\n"
                    + "  @\"559-@\"55f => \\1;\n"
                    + "  @\"561-@\"589 => \\1;\n"
                    + "  @\"1e00-@\"1e95 => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"1e96-@\"1e9a => \\1;\n"
                    + "  @\"1ea0-@\"1ef9 => #(\\1 + @\"1 - \\1 MOD: @\"2);\n"
                    + "  @\"1f00-@\"1f07 => \\1;\n"
                    + "  @\"1f08-@\"1f0f => #(\\1 - @\"8);\n"
                    + "  @\"1f10-@\"1f17 => \\1;\n"
                    + "  @\"1f18-@\"1f1f => #(\\1 - @\"8);\n"
                    + "  @\"1f20-@\"1f27 => \\1;\n"
                    + "  @\"1f28-@\"1f2f => #(\\1 - @\"8);\n"
                    + "  @\"1f30-@\"1f37 => \\1;\n"
                    + "  @\"1f38-@\"1f3f => #(\\1 - @\"8);\n"
                    + "  @\"1f40-@\"1f47 => \\1;\n"
                    + "  @\"1f48-@\"1f4f => #(\\1 - @\"8);\n"
                    + "  @\"1f50-@\"1f57 => \\1;\n"
                    + "  @\"1f59-@\"1f5f => #(\\1 - @\"8);\n"
                    + "  @\"1f60-@\"1f67 => \\1;\n"
                    + "  @\"1f68-@\"1f6f => #(\\1 - @\"8);\n"
                    + "  @\"1f70-@\"1f87 => \\1;\n"
                    + "  @\"1f88-@\"1f8f => #(\\1 - @\"8);\n"
                    + "  @\"1f90-@\"1f97 => \\1;\n"
                    + "  @\"1f98-@\"1f9f => #(\\1 - @\"8);\n"
                    + "  @\"1fa0-@\"1fa7 => \\1;\n"
                    + "  @\"1fa8-@\"1faf => #(\\1 - @\"8);\n"
                    + "  @\"1fb0-@\"1fb7 => \\1;\n"
                    + "  @\"1fb8-@\"1fb9 => #(\\1 - @\"8);\n"
                    + "  @\"1fba => @\"1f70;\n"
                    + "  @\"1fbb => @\"1f71;\n"
                    + "  @\"1fbc => @\"1fb3;\n"
                    + "  @\"1fbd-@\"1fc7 => \\1;\n"
                    + "  @\"1fc8 => @\"1f72;\n"
                    + "  @\"1fc9 => @\"1f73;\n"
                    + "  @\"1fca => @\"1f74;\n"
                    + "  @\"1fcb => @\"1f75;\n"
                    + "  @\"1fcc => @\"1fc3;\n"
                    + "  @\"1fcd-@\"1fd7 => \\1;\n"
                    + "  @\"1fd8 => @\"1fd0;\n"
                    + "  @\"1fd9 => @\"1fd1;\n"
                    + "  @\"1fda => @\"1f76;\n"
                    + "  @\"1fdb => @\"1f77;\n"
                    + "  @\"1fdc-@\"1fe7 => \\1;\n"
                    + "  @\"1fe8 => @\"1fe0;\n"
                    + "  @\"1fe9 => @\"1fe1;\n"
                    + "  @\"1fea => @\"1f7a;\n"
                    + "  @\"1feb => @\"1f7b;\n"
                    + "  @\"1fec => @\"1fe5;\n"
                    + "  @\"1fed-@\"1ff7 => \\1;\n"
                    + "  @\"1ff8 => @\"1f78;\n"
                    + "  @\"1ff9 => @\"1f79;\n"
                    + "  @\"1ffa => @\"1f7c;\n"
                    + "  @\"1ffb => @\"1f7d;\n"
                    + "  @\"1ffc => @\"1ff3;\n"
                    + "  @\"1ffd-@\"215f => \\1;\n"
                    + "  @\"2160-@\"216f => #(\\1 + @\"10);\n"
                    + "  @\"e180 => @\"df;\n"
                    + "  @\"e181-@\"e182 => @\"e181;\n"
                    + "  @\"e183 => @\"131;\n"
                    + "  @\"e184 => \\1;\n"
                    + "  @\"e185 => @\"138;\n"
                    + "  @\"e186 => @\"149;\n"
                    + "  @\"e187 => @\"17f;\n"
                    + "  @\"e188-@\"e18c => #(\\1 - @\"c2f2);\n"
                    + "  @\"e18d-@\"e18e => @\"e18e;\n"
                    + "  @\"e18f => @\"1f0;\n"
                    + "  @\"e191. => \\2;\n"
                    + "  @\"e192. => `\\'`u'`p'`p'`e'`r'`c'`a'`s'`e'`{'\\2`}';\n"
                    + "  @\"e1a5-@\"e1a7 => #(\\1 - @\"dcd3);\n"
                    + "  @\"e1a0 => @\"390;\n" + "  @\"e1a1 => @\"3b0;\n"
                    + "  @\"e1a2 => @\"3c2;\n" + "  @\"e1a3 => @\"3d0;\n"
                    + "  @\"e1a4 => @\"3d1;\n" + "  @\"e1a5-@\"e1a7 => \\1;\n"
                    + "  @\"e1a8 => @\"3d5;\n" + "  @\"e1a9 => @\"3d6;\n"
                    + "  @\"e1aa-@\"e1ad => \\1;\n"
                    + "  @\"e1ae-@\"e1b1 => #(\\1 - @\"ddb1);\n"
                    + "  @\"e1b2 => @\"1f50;\n" + "  @\"e1b3 => @\"1f52;\n"
                    + "  @\"e1b4 => @\"1f54;\n" + "  @\"e1b5 => @\"1f56;\n"
                    + "  @\"e1b6 => @\"1fb2;\n" + "  @\"e1b7 => @\"1fb4;\n"
                    + "  @\"e1b8 => @\"1fb6;\n" + "  @\"e1b9 => @\"1fb7;\n"
                    + "  @\"e1ba => @\"1fc2;\n" + "  @\"e1bb => @\"1fc4;\n"
                    + "  @\"e1bc => @\"1fc6;\n" + "  @\"e1bd => @\"1fc7;\n"
                    + "  @\"e1be => @\"1fd2;\n" + "  @\"e1bf => @\"1fd3;\n"
                    + "  @\"e1c0 => @\"1fd6;\n" + "  @\"e1c1 => @\"1fd7;\n"
                    + "  @\"e1c2 => @\"1fe2;\n" + "  @\"e1c3 => @\"1fe3;\n"
                    + "  @\"e1c4 => @\"1fe6;\n" + "  @\"e1c5 => @\"1fe7;\n"
                    + "  @\"e1c6 => @\"1fe4;\n" + "  @\"e1c7 => @\"1ff2;\n"
                    + "  @\"e1c8 => @\"1ff4;\n" + "  @\"e1c9 => @\"1ff6;\n"
                    + "  @\"e1ca => @\"1ff7;\n" + "  @\"e1cb => @\"587;\n"
                    + "  . => \\1;\n", //
            cs.toString());
    }

}
