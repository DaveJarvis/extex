/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Test;

/**
 * This is a test suite for the {@link TexConfiguration}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TexConfigurationTest {

    /**
     * Make an input stream reading from a string.
     * 
     * @param s the string
     * 
     * @return the input stream
     */
    private InputStream makeStream(String s) {

        return new ByteArrayInputStream(s.getBytes());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetConfiguration1() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream("  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} "),
                    "");
        assertNotNull(cfg.getConfiguration("x"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationNotFoundException.class)
    public final void testGetConfiguration2() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream("  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} "),
                    "");
        try {
            cfg.getConfiguration("y");
        } catch (ConfigurationNotFoundException e) {
            assertEquals("y", e.getConfigName());
            throw e;
        }
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetValue1() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream("  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} "),
                    "");
        assertEquals(" ", cfg.getValue());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead02() throws Exception {

        TexConfiguration cfg = new TexConfiguration(makeStream("\\x{}"), "");
        assertNotNull(cfg);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead03() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(makeStream("  \\xyz { } "), "");
        assertNotNull(cfg);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead04() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(makeStream("  \\xyz [abc=def] { } "), "");
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead05() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream("  \\xyz [abc=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
        assertEquals("xyz", cfg.getName());
        Iterator<Configuration> iterator = cfg.iterator();
        assertFalse(iterator.hasNext());
        iterator = cfg.iterator("abc");
        assertFalse(iterator.hasNext());
        assertNull(cfg.findConfiguration("x"));
        assertNull(cfg.findConfiguration("x", "y"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead06() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream("  \\xyz [abc=def,xxx={123}] { \\x[a=42]{}} "),
                    "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
        assertEquals("xyz", cfg.getName());
        Iterator<Configuration> iterator = cfg.iterator();
        assertTrue(iterator.hasNext());
        iterator = cfg.iterator("abc");
        assertFalse(iterator.hasNext());
        assertNotNull(cfg.findConfiguration("x"));
        assertNull(cfg.findConfiguration("x", "y"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead07() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream("  \\xyz [abc=def,xxx={123}] { \\x[name=42]{}} "),
                    "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
        assertEquals("xyz", cfg.getName());
        Iterator<Configuration> iterator = cfg.iterator();
        assertTrue(iterator.hasNext());
        iterator = cfg.iterator("abc");
        assertFalse(iterator.hasNext());
        assertNotNull(cfg.findConfiguration("x", "42"));
        assertNull(cfg.findConfiguration("x", "y"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead10() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(makeStream("  \\xyz { abc } "), "");
        assertNotNull(cfg);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead11() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(makeStream("  \\xyz { {abc}} "), "");
        assertNotNull(cfg);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead12() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(makeStream("  \\xyz [a= {{abc}}]{} "), "");
        assertEquals("{abc}", cfg.getAttribute("a"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment01() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream(" % fff\n \\xyz [abc=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment02() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream(" \\xyz % fff\n [abc=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment03() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream(" \\xyz [abc % fff\n=def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment04() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream(" \\xyz [abc = % fff\n def,xxx={123}] { } "), "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment05() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream(" \\xyz [abc = % fff\n def,xxx={123}] { abc } "),
                    "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testReadComment06() throws Exception {

        TexConfiguration cfg =
                new TexConfiguration(
                    makeStream(" \\xyz [abc = % fff\n def,xxx={123}] {\\qwertz {}} "),
                    "");
        assertEquals("123", cfg.getAttribute("xxx"));
        assertEquals("def", cfg.getAttribute("abc"));
    }

    /**
     * <testcase> An empty stream signals a failure in locating the
     * configuration. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationNotFoundException.class)
    public final void testReadError0() throws Exception {

        new TexConfiguration(null, "");
    }

    /**
     * <testcase> The empty stream is not valid. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError01() throws Exception {

        new TexConfiguration(makeStream(""), "");
    }

    /**
     * <testcase> The empty stream with whitespace only is not valid.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError02() throws Exception {

        new TexConfiguration(makeStream("  \t "), "");
    }

    /**
     * <testcase> The initial cs has to be followed by something. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError04() throws Exception {

        new TexConfiguration(makeStream("\\xxx"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [ or {. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError05() throws Exception {

        new TexConfiguration(makeStream("\\xxx("), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [ and an attribute name.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError06() throws Exception {

        new TexConfiguration(makeStream("\\xxx["), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError07() throws Exception {

        new TexConfiguration(makeStream("\\xxx[a"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError08() throws Exception {

        new TexConfiguration(makeStream("\\xxx[a%=\n"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError09() throws Exception {

        new TexConfiguration(makeStream("\\xxx[a= %=\n"), "");
    }

    /**
     * <testcase> The initial cs has to be followed by [, an attribute name and
     * an =. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError10() throws Exception {

        new TexConfiguration(makeStream("\\xxx[a %=\na"), "");
    }

    /**
     * <testcase> Attributes need to be closed by ]. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError11() throws Exception {

        new TexConfiguration(makeStream("\\xxx[a=x\n"), "");
    }

    /**
     * <testcase> Attributes need to be closed by ]. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError12() throws Exception {

        new TexConfiguration(makeStream("\\xxx[a=x b"), "");
    }

    /**
     * <testcase> Attributes need to be unique. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError13() throws Exception {

        new TexConfiguration(makeStream("\\xxx[a=x,a=y]"), "");
    }

    /**
     * <testcase> A lonely backslash raises an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError14() throws Exception {

        new TexConfiguration(makeStream("\\"), "");
    }

    /**
     * <testcase> A lonely backslash in the configuration raises an
     * error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError15() throws Exception {

        new TexConfiguration(makeStream("\\a{\\"), "");
    }

    /**
     * <testcase> A lonely backslash in the configuration raises an
     * error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError16() throws Exception {

        new TexConfiguration(makeStream("\\={\\"), "");
    }

    /**
     * <testcase> A missing opening brace for a sub-configuration raises an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError17() throws Exception {

        new TexConfiguration(makeStream("\\a{\\="), "");
    }

    /**
     * <testcase> An unclosed brace raises an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError18() throws Exception {

        new TexConfiguration(makeStream("\\a{"), "");
    }

    /**
     * <testcase> A missing backslash raises an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError19() throws Exception {

        new TexConfiguration(makeStream("a"), "");
    }

    /**
     * <testcase> A missing closing bracket for the attribute section raises an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError20() throws Exception {

        new TexConfiguration(makeStream("\\a[x=y"), "");
    }

    /**
     * <testcase> A missing closing brace in the attribute section raises an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public final void testReadError21() throws Exception {

        new TexConfiguration(makeStream("\\a[x={y\\"), "");
    }

}
